package connection.wifi;

import android.provider.ContactsContract;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import connection.ConnectionSocket;

/**
 * Created by zscse on 2015. 10. 12..
 */
public class WifiSocket implements ConnectionSocket {

    private DatagramSocket socket;
    private InetAddress broadcastAddress;

    public WifiSocket(DatagramSocket socket, InetAddress broadcastAddress) {
        this.socket = socket;
        this.broadcastAddress = broadcastAddress;
    }

    @Override
    public void send(byte[] packet) throws IOException {
//        String message = "Teszt";
//        byte[] bytes = message.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length, broadcastAddress, 8888);
        socket.send(datagramPacket);
        Log.v("udp", "SENT - WifiSocket");
    }

    @Override
    public int receive(byte[] packet) throws IOException {
//        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(packet, packet.length);
        socket.receive(datagramPacket);

        String message = new String(packet, 0, datagramPacket.getLength());
        Log.v("udp", "RECEIVE" + message);
        return datagramPacket.getLength();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
