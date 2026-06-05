package com.example.receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomEventReceiver extends BroadcastReceiver {
    public static final String ACTION_CUSTOM_MSG = "com.example.receiverdemo.ACTION_MSG_CUSTOM";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_CUSTOM_MSG.equals(intent.getAction())) {
            String payload = intent.getStringExtra("payload");
            if (payload == null) payload = "Pas de contenu";
            
            Toast.makeText(context, "Evénement personnalisé reçu : " + payload, Toast.LENGTH_SHORT).show();
        }
    }
}
