/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package UDP;
import java.util.*;
import java.net.*;
import java.io.*;
/**
 *
 * @author duydanh
 */
public class UDPDATA2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        DatagramSocket socket= new DatagramSocket();
        InetAddress host= InetAddress.getByName("36.50.135.242");
        int port=2207;
        
        String code=";B23DCCN121;kpx6s5zI";
        DatagramPacket gui= new DatagramPacket(code.getBytes(),code.length(),host,port);
        socket.send(gui);
        byte[] buffer= new byte[1024];
        DatagramPacket nhan= new DatagramPacket(buffer, buffer.length);
        socket.receive(nhan);
        
        String[] s=new String(nhan.getData()).split(";");
        String rid=s[0];
        String[] data=s[1].split(",");
        int[] a= new int[data.length];
        for(int i=0;i<data.length;i++){
            a[i]=Integer.parseInt(data[i].trim());
        }
        int max=a[0];
        int min=a[0];
        for (int i = 1; i < data.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }

            if (a[i] < min) {
                min = a[i];
            }
        }
        String kq=max+","+min;
        String res=rid+";"+kq;
        System.out.println("Ket qua: "+res);
        
        DatagramPacket gui1= new DatagramPacket(res.getBytes(),res.length(),host,port);
        socket.send(gui1);
        
    }
    
}
