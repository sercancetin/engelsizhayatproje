package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class AlertDemo extends DialogFragment {
    BluetoothSocket Socket;
    OutputStream disari;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device2;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    String deviceHardwareAddress;
    Vibrator v;
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** Turn Screen On and Unlock the keypad when this alert dialog is displayed */
        getActivity().getWindow().addFlags(LayoutParams.FLAG_TURN_SCREEN_ON | LayoutParams.FLAG_DISMISS_KEYGUARD);
        bluetoothAc(getContext());
        /** Creating a alert dialog builder */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Setting title for the alert dialog */
        builder.setTitle("Alarm");

        /** Setting the content for the alert dialog */
        builder.setMessage("An Alarm by AlarmManager");

        /** Defining an OK button event listener */
        builder.setPositiveButton("OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /** Exit application on click OK */
                getActivity().finish();
            }
        });

        /** Creating the alert dialog window */
        //Toast.makeText(getContext(),"evet",Toast.LENGTH_SHORT).show();
        return builder.create();
    }

    /** The application should be exit, if the user presses the back button */
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
    private void bluetoothAc(Context context){
        Toast.makeText(context, "Bluetooth opened", Toast.LENGTH_SHORT).show();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent e = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            mBluetoothAdapter.enable();
            try {
                Thread.sleep(3500);                 //4 second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

//Bekletme

        //bekletme
//sonra yazdıklarım
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        String deviceName;

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device1 : pairedDevices) {
                deviceName = device1.getName();
                String deviceHardwareAddress = device1.getAddress(); // MAC address
                if (deviceName.equals("HC-06")) {

                    Toast.makeText(context, "HC-06 Aygıtına Bağlanıyor.", Toast.LENGTH_SHORT).show();
                    //Buradan sonrası
                    device2 = device1;
                    boolean kont=true;

                    try {
                        Socket=device2.createInsecureRfcommSocketToServiceRecord(myUUID);//soket ile cihazı bağlıyoruz
                        Socket.connect();//bağlantı...

                    }  catch (IOException e) {
                        e.printStackTrace();
                        kont = false;
                    }
                    if(kont)//eğer bir sorun ile karşılaşmadıysak dışarıdan gelen verileri ve gidecek verileri global değişkenlere aktarıyoruz
                    {       //istediğimiz yerden ulaşabilelim diye
                        try
                        {
                            Socket.getOutputStream().write("2".toString().getBytes());//dışarı ile bağlantı sağlandı
                            //titreşim
                            //v=(Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                           // v.vibrate(0002);

                            //titreşim
                        } catch (IOException e) {//disari değişkeni ile artık istediğimiz veriyi gönderebiliriz.
                            e.printStackTrace();
                        }
                    }
                    //Baglantıyı Sonlandırma
                    //Bekletme
                    try {
                        Thread.sleep(2100);                 //4 second.
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    //bekletme
                    if(Socket!=null){
                        try {
                            Socket.close();
                        } catch (IOException e){

                        }
                    }


                    //Buradan önceki
                }
            }

        }
        //sonra yadıklarım
    }
}