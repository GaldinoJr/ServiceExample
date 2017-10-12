package com.example.galdino.serviceexample.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Galdino on 12/10/2017.
 */

public class ServiceExample extends Service
{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
