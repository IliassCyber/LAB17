package com.example.receiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AirplaneModeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Verification de l'action recue
        if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
            
            // Extraction de l'état actuel du mode avion
            boolean isEnabled = intent.getBooleanExtra("state", false);
            
            String statusMessage = isEnabled 
                ? "Mode Avion : ACTIVÉ. Le réseau est coupé." 
                : "Mode Avion : DÉSACTIVÉ. Réseau disponible.";
            
            // Affichage d'une notification rapide
            Toast.makeText(context, statusMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
