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
public class UPDSTRING1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        DatagramSocket socket= new DatagramSocket();
        InetAddress sp= InetAddress.getByName("36.50.135.242");
        int port=2208;
        
        String code=";B23DCCN121;oOG0IylA";
        DatagramPacket gui= new DatagramPacket(code.getBytes(),code.length(),sp,port);
        socket.send(gui);
        byte[] buffer= new byte[1024];
        DatagramPacket nhan= new DatagramPacket(buffer,buffer.length);
        socket.receive(nhan);
        
        String s1= new String(nhan.getData());
        System.out.println("Nhan chuoi la: "+s1);
        String[] data= s1.trim().split(";");
        String rid=data[0];
        String chuoi=data[1];
        // c. Tim ky tu xuat hien nhieu nhat
        char kytumax=chuoi.charAt(0);
        int max=0;
        for(int i=0;i<chuoi.length();i++){
            int dem=0;
            for(int j=0;j<chuoi.length();j++){
                if(chuoi.charAt(i)==chuoi.charAt(j)){
                    dem++;
                }
            }
            if(dem>max){
                max=dem;
                kytumax=chuoi.charAt(i);
            }
        }
        
        String vt="";
        for(int i=0;i<chuoi.length();i++){
            if(chuoi.charAt(i)== kytumax){
                vt=vt+(i+1)+",";
            }
        }
        String kq=rid+";"+kytumax+":"+vt;
        System.out.println("Ket qua: "+kq);
        DatagramPacket guidi= new DatagramPacket(kq.getBytes(),kq.length(),sp,port);
        socket.send(guidi);
    }
}
