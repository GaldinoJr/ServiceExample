package com.example.galdino.serviceexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.galdino.serviceexample.databinding.ActivityMainBinding;
import com.example.galdino.serviceexample.service.ServiceAtualizarAutomatico;

import static com.example.galdino.serviceexample.service.ServiceAtualizarAutomatico.EXTRA_POWER_ON_SERVICE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mBinding;
    private Intent mIntentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        load();
    }

    private void load()
    {
        mIntentService = new Intent(this,ServiceAtualizarAutomatico.class);
        mBinding.btLigar.setOnClickListener(this);
        mBinding.btDesligar.setOnClickListener(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int number = intent.getIntExtra(ServiceAtualizarAutomatico.EXTRA_NUMBER, 0);
                        mBinding.tvValor.setText("Number: " + number);
                    }
                }, new IntentFilter(ServiceAtualizarAutomatico.ACTION_LOCATION_BROADCAST)
        );
    }

    @Override
    public void onClick(View v)
    {
        if(v == mBinding.btLigar)
        {
            powerOnService();
        }
        else
        {
            powerOffService();
        }
    }

    private void powerOffService()
    {
        mIntentService.putExtra(EXTRA_POWER_ON_SERVICE,false);
        startService(mIntentService);
    }

    private void powerOnService()
    {
        mIntentService.putExtra(EXTRA_POWER_ON_SERVICE,true);
        startService(mIntentService);
    }
}
