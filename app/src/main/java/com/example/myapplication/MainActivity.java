package com.example.myapplication;

import android.Manifest;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.Set;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    Button btnbluetoothac,btnalarm,btneslesmislistele;
    ListView listView;
    int ackapa=0;
    //bluetooth for
    BluetoothSocket Socket;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothDevice device2;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    Vibrator v;
    static int cihazBagli=0;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnbluetoothac = findViewById(R.id.btnbluetoothac);
        btneslesmislistele = findViewById(R.id.btneslesmisgoster);
        btnalarm = findViewById(R.id.btnalarm);
        listView = findViewById(R.id.device_list);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            }
        } else {

        }

        btnbluetoothac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ackapa==0){
                    connect1();
                    ackapa = 1;
                } else {
                    ackapa =0;
                    disconnect();
                }

            }
        });
        btnalarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent into = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(into);
            }
        });
        btneslesmislistele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eslesencihazlarigoster();
            }
        });

    }

    public void connect1() {
        if (!mBluetoothAdapter.isEnabled()) {
            new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mBluetoothAdapter.enable();
        }
        Toast.makeText(this,"Bluetooth açıldı",Toast.LENGTH_LONG).show();
    }
    public void disconnect() {
        mBluetoothAdapter.disable();
        Toast.makeText(this, "Bluetooth Kapandı", Toast.LENGTH_SHORT).show();
    }

    public String[] pairedDevices() { //daha önceden eşleşen bluetootları stringe atma
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        String[] deviceName = new String[pairedDevices.size()];
        int index = 0;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                deviceName[index] = device.getName();
                index++;
            }
            return deviceName;
        } else {
            return deviceName;
        }
    }
    private void eslesencihazlarigoster(){
        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,pairedDevices());
        listView.setAdapter(arrayAdapter);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}