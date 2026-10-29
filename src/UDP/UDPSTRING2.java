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
public class UDPSTRING2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        DatagramSocket socket= new DatagramSocket();
        InetAddress host= InetAddress.getByName("36.50.135.242");
        int port=2208;
        
        String code=";B23DCCN121;QIB3Ysay";
        DatagramPacket gui= new DatagramPacket(code.getBytes(),code.length(),host,port);
        socket.send(gui);
        
        byte[] buffer= new byte[1024];
        DatagramPacket nhan= new DatagramPacket(buffer,buffer.length);
        socket.receive(nhan);
        
        String chuoi=new String(nhan.getData());
        System.out.println("Nhan: "+chuoi);
        String[] s=chuoi.trim().split(";");
        String rid=s[0];
        String data=s[1];
        String[] a=data.split(" ");
        
        String kq="";
        for(int i=0;i<a.length;i++){
            String tu=a[i];
            tu=tu.substring(0,1).toUpperCase()+tu.substring(1).toLowerCase();
            kq=kq+tu+" ";
        }
        String res=rid+";"+kq.trim();
        System.out.println("Ket qua: "+res);
        
        DatagramPacket gui1= new DatagramPacket(res.getBytes(),res.length(),host,port);
        socket.send(gui1);
    }
    
}
