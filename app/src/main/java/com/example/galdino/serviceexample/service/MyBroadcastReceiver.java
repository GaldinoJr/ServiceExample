package com.example.galdino.serviceexample.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Galdino on 12/10/2017.
 */
// UTILIZAR O BROADCAST RECEIVER NA ACTIVITY/FRAGMENT QUANDO TIVER QUE NOTIFICAR A UI
// UTILIZAR O BROADCAST RECEIVER FORA DA ACTIVITY/UI QUANDO NÃO PRECISAR NOTIFICAR A UI
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentStartService = new Intent(context,ServiceAtualizarAutomatico.class);
        context.startService(intentStartService);
    }
}
