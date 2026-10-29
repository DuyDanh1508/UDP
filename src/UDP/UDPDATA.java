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
public class UDPDATA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        DatagramSocket socket= new DatagramSocket();
        InetAddress host= InetAddress.getByName("36.50.135.242");
        int port=2207;
        
        String code=";B23DCCN121;RP3OlDo9";
        DatagramPacket gui= new DatagramPacket(code.getBytes(),code.length(),host,port);
        socket.send(gui);
        
        byte[] buffer= new byte[1024];
        DatagramPacket nhan= new DatagramPacket(buffer,buffer.length);
        socket.receive(nhan);
        
        String s=new String(nhan.getData());
        System.out.println("Nhan: "+s);
        String[] chuoi=s.split(";");
        String rid=chuoi[0];
        int n=Integer.parseInt(chuoi[1]);
        String[] data=chuoi[2].split(",");
        int[] a=new int[n];
        boolean[] tt= new boolean[n+1];
        for(int i=0;i<n;i++){
            a[i]=Integer.parseInt(data[i].trim());
            tt[a[i]]=true;
        }
        String kq="";
        for(int i=1;i<=n;i++){
            if(tt[i]==false){
                if(kq.length()>0){
                    kq=kq+",";
                }
                kq=kq+i;
            }
        }
        String res=rid+";"+kq;
        System.out.println("Ket qua: "+res);
        
        DatagramPacket gui1= new DatagramPacket(res.getBytes(),res.length(),host,port);
        socket.send(gui1);
    }
    
}
