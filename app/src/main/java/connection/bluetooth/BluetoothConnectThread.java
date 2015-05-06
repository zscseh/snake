package connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import connection.ConnectionManager;
import connection.enumeration.DeviceType;
import zsolt.cseh.snake.BluetoothActivity;

/**
 * Created by Zsolt on 2015.03.21..
 *
 * Connects to another device, which run a BluetoothAcceptThread
 */
public class BluetoothConnectThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothActivity activity;

    public BluetoothConnectThread(BluetoothAdapter bluetoothAdapter, UUID uuid, BluetoothDevice bluetoothDevice, BluetoothActivity activity) {
        BluetoothSocket tmp = null;
        this.bluetoothAdapter = bluetoothAdapter;
        this.activity = activity;

        try {
            tmp = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {

        }
        bluetoothSocket = tmp;
    }

    /** Connects another device via the socket */
    @Override
    public void run() {
        bluetoothAdapter.cancelDiscovery();

        try {
            bluetoothSocket.connect();
        } catch (IOException connectException) {
            try {
                bluetoothSocket.close();
            } catch (IOException closeException) {

            }
            return;
        }

        ConnectionManager.getInstance().setSocket(new BluetoothConnectionSocket(bluetoothSocket));
        ConnectionManager.getInstance().setDeviceType(DeviceType.SLAVE);
        activity.startGame();
    }
}