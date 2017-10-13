package com.example.galdino.serviceexample.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.galdino.serviceexample.GenerateRandomNumber;

import java.util.concurrent.TimeUnit;

import static com.example.galdino.serviceexample.GenerateRandomNumber.get;

/**
 * Created by Galdino on 12/10/2017.
 */

public class ServiceAtualizarAutomatico extends Service
{
    private static final String TAG = "Service_actualize";
    public static final String EXTRA_POWER_ON_SERVICE = "EXTRA_POWER_ON_SERVICE";
    public static final String EXTRA_NUMBER = "EXTRA_NUMBER";
    public static final String ACTION_LOCATION_BROADCAST = ServiceAtualizarAutomatico.class.getName() + "MyBroadcastReceiver";

    private static ServiceAtualizarAutomatico mServiceInstance = null;
    private boolean mIsRunning  = false;
    private long mTimeToActualize;

    public static boolean isServiceInstanceCreated() {
        return mServiceInstance != null;
    }//met

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gravarLog("onCreate service");
        if(!isServiceInstanceCreated()) // Não esta criado o serviço?
        {
            mServiceInstance = this;
            mIsRunning = true;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        gravarLog("onStartCommand");
        if(intent == null)
        {
            stopSelf(); // desliga
            return START_NOT_STICKY;
        }

        final boolean isPowerOn = (boolean) intent.getExtras().get(EXTRA_POWER_ON_SERVICE);

        if (!isPowerOn) // Vai desligar o serviço?
        {
            stopSelf(); // desliga
            return START_NOT_STICKY;
        }
        if(mIsRunning)
        {
            gravarLog("Serviço ligado");
            mTimeToActualize = getTimeToActualize();
            if(mTimeToActualize > 0)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!Thread.currentThread().isInterrupted())
                        {
                            if(mIsRunning)
                            {
                                gravarLog("Serviço rodando");
                                try
                                {
                                    int number = GenerateRandomNumber.get();

                                    sendBroadcastMessage(number);

                                    Thread.sleep(mTimeToActualize);
                                }
                                catch (InterruptedException e)
                                {
                                    gravarLog("Erro na execução do serviço: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                gravarLog("Serviço encerrado");
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
            }
        }
        else
        {
            gravarLog("Serviço pausado");
        }

        return Service.START_REDELIVER_INTENT; // Abre o serviço novamente assim que o app for aberto
    }

    // Notificar a activity com o resultado
    private void sendBroadcastMessage(int number) {
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(EXTRA_NUMBER, number);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void gravarLog(String log)
    {
        Log.i(TAG,log);
    }

    public long getTimeToActualize()
    {
        // 5 segundos de delay para a próxima atualização
        return TimeUnit.MILLISECONDS.convert(5,TimeUnit.SECONDS);
    }

    @Override
    public void onDestroy() {
        gravarLog("Service onDestroy");
        mIsRunning = false;
        mServiceInstance = null;
    }
}
