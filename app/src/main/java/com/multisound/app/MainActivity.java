package com.multisound.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.provider.Settings;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    private AudioManager audio;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        audio = (AudioManager)
            getSystemService(Context.AUDIO_SERVICE);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(30, 45, 30, 30);
        root.setBackgroundColor(Color.rgb(17,17,34));

        ScrollView scroll = new ScrollView(this);
        scroll.addView(root);

        TextView title = new TextView(this);
        title.setText("MultiSound");
        title.setTextSize(34);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, 1);
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("SUA CENTRAL INTELIGENTE DE ÁUDIO");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle);

        addSpace(root, 35);
        addTitle(root, "CONTROLE GERAL");

        int max = audio.getStreamMaxVolume(
            AudioManager.STREAM_MUSIC);

        int current = audio.getStreamVolume(
            AudioManager.STREAM_MUSIC);

        TextView label = new TextView(this);
        label.setTextColor(Color.WHITE);
        label.setText("Volume: " + current + "/" + max);
        root.addView(label);

        SeekBar volume = new SeekBar(this);
        volume.setMax(max);
        volume.setProgress(current);
        volume.setProgressTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(115,87,255)));

        volume.setOnSeekBarChangeListener(
            new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(
                    SeekBar bar, int value, boolean user) {
                    if (user) {
                        audio.setStreamVolume(
                            AudioManager.STREAM_MUSIC,
                            value, 0);
                    }
                    label.setText(
                        "Volume: " + value + "/" + max);
                }

                public void onStartTrackingTouch(SeekBar bar) {}
                public void onStopTrackingTouch(SeekBar bar) {}
            });

        root.addView(volume);

        addSpace(root, 30);
        addTitle(root, "DISPOSITIVOS DE ÁUDIO");

        TextView status = new TextView(this);
        status.setText(
            "Nenhum receptor MultiSound conectado.");
        status.setTextColor(Color.LTGRAY);
        root.addView(status);

        addButton(root, "CONFIGURAR BLUETOOTH",
            Settings.ACTION_BLUETOOTH_SETTINGS);

        addButton(root, "CONFIGURAR WI-FI",
            Settings.ACTION_WIFI_SETTINGS);

        addSpace(root, 25);
        addTitle(root, "SOBRE O MULTISOUND");

        TextView info = new TextView(this);
        info.setTextColor(Color.LTGRAY);
        info.setText(
            "Central de controle de áudio.\n\n" +
            "Próximas etapas: descoberta de dispositivos, " +
            "receptores Wi-Fi próprios e sincronização.");
        root.addView(info);

        setContentView(scroll);
    }

    private void addTitle(LinearLayout root, String value) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(16);
        t.setTextColor(Color.WHITE);
        t.setTypeface(null, 1);
        t.setPadding(0, 12, 0, 12);
        root.addView(t);
    }

    private void addButton(
        LinearLayout root, String label, String action) {

        Button b = new Button(this);
        b.setText(label);
        b.setTextColor(Color.WHITE);
        b.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(115,87,255)));

        b.setOnClickListener(v -> {
            try {
                startActivity(new Intent(action));
            } catch (Exception e) {
                Toast.makeText(this,
                    "Não foi possível abrir as configurações.",
                    Toast.LENGTH_SHORT).show();
            }
        });

        root.addView(b);
    }

    private void addSpace(LinearLayout root, int height) {
        View v = new View(this);
        root.addView(v,
            new LinearLayout.LayoutParams(1, height));
    }
}
