package com.example.myapplication;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class InterceptCall extends BroadcastReceiver{
    BluetoothSocket Socket;
    OutputStream disari;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device2;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    String deviceHardwareAddress;
    Vibrator v;
    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {



        try {

            Toast.makeText(context, "Incoming Call!", Toast.LENGTH_SHORT).show();
            bluetoothAc(context);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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
                            Socket.getOutputStream().write("1".toString().getBytes());//dışarı ile bağlantı sağlandı
                            //titreşim
                            //v=(Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            //v.vibrate(1000);

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


