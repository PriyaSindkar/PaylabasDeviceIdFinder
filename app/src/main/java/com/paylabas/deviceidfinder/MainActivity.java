package com.paylabas.deviceidfinder;

import android.content.ClipData;
import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private TextView txtCopyDeviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linearDeviceId = (LinearLayout) findViewById(R.id.linearDeviceId);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        final TextView txtDeviceId = (TextView) findViewById(R.id.txtDeviceId);
        txtCopyDeviceId = (TextView) findViewById(R.id.txtCopyDeviceId);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                if (deviceId != null) {
                    if (deviceId.length() != 0) {
                        linearDeviceId.setVisibility(View.VISIBLE);
                        txtDeviceId.setText(deviceId);

                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.device_id_not_found), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.device_id_not_found), Toast.LENGTH_LONG).show();
                }
            }
        });

        txtCopyDeviceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(txtDeviceId.getText().toString().trim());
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("", txtDeviceId.getText().toString().trim());
                    clipboard.setPrimaryClip(clip);
                }

                Toast.makeText(MainActivity.this, getString(R.string.device_id_copied_msg), Toast.LENGTH_LONG).show();
            }
        });

    }
}
