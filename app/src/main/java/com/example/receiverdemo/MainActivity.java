package com.example.receiverdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AirplaneModeReceiver airplaneModeReceiver;
    private boolean isMonitoringAirplaneMode = false;
    
    private TextView tvStatusDisplay;
    private Button btnToggleMonitoring;
    private Button btnEmitCustomEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des composants
        airplaneModeReceiver = new AirplaneModeReceiver();
        tvStatusDisplay = findViewById(R.id.tv_status_info);
        btnToggleMonitoring = findViewById(R.id.btn_toggle_receiver);
        btnEmitCustomEvent = findViewById(R.id.btn_send_broadcast);

        // Gestionnaire pour activer/désactiver le receiver dynamique
        btnToggleMonitoring.setOnClickListener(v -> handleReceiverState());

        // Gestionnaire pour envoyer le broadcast personnalisé
        btnEmitCustomEvent.setOnClickListener(v -> triggerCustomBroadcast());
    }

    private void handleReceiverState() {
        if (!isMonitoringAirplaneMode) {
            // Définition du filtre pour intercepter le changement de mode avion
            IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
            
            // Enregistrement au niveau du système
            registerReceiver(airplaneModeReceiver, filter);
            
            isMonitoringAirplaneMode = true;
            updateUIState("Écoute du Mode Avion : ACTIVE");
        } else {
            // Désenregistrement pour libérer les ressources
            unregisterReceiver(airplaneModeReceiver);
            
            isMonitoringAirplaneMode = false;
            updateUIState("Écoute du Mode Avion : INACTIVE");
        }
    }

    private void updateUIState(String statusText) {
        tvStatusDisplay.setText(statusText);
        btnToggleMonitoring.setText(isMonitoringAirplaneMode ? "Arrêter l'écoute" : "Démarrer l'écoute (Mode Avion)");
    }

    private void triggerCustomBroadcast() {
        // Création d'une intention avec notre action personnalisée
        Intent intent = new Intent(CustomEventReceiver.ACTION_CUSTOM_MSG);
        intent.putExtra("payload", "Message envoyé depuis l'interface utilisateur !");
        
        // Diffusion de l'événement
        sendBroadcast(intent);
        
        Toast.makeText(this, "Événement personnalisé diffusé avec succès", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Sécurité : on s'assure de désenregistrer le receiver si l'activité s'arrête
        if (isMonitoringAirplaneMode) {
            unregisterReceiver(airplaneModeReceiver);
            isMonitoringAirplaneMode = false;
            updateUIState("Écoute désactivée (onStop)");
        }
    }
}
