/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package UDP;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author duydanh
 */
public class UDPOBJ2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String code = ";B23DCCN121;dj1JKD5O";

        DatagramPacket gui = new DatagramPacket(code.getBytes(), code.length(), server, port);
        socket.send(gui);

        byte[] buffer = new byte[1024];
        DatagramPacket nhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(nhan);
        
        String rid=new String(nhan.getData(),0,8);
        ByteArrayInputStream bais= new ByteArrayInputStream(nhan.getData(),8,nhan.getLength()-8);
        ObjectInputStream ois= new ObjectInputStream(bais);
        
        Customer cs= (Customer) ois.readObject();
        String tengoc=cs.getName().trim().toLowerCase();
        String[] tach=tengoc.split("\\s+");
        String tenchuanhoa=tach[tach.length-1].toUpperCase()+", ";
        for(int i=0;i<tach.length-1;i++){
            if (i > 0) {
                tenchuanhoa += " ";
            }
            tenchuanhoa += tach[i].substring(0, 1).toUpperCase()+tach[i].substring(1);
        }
        cs.setName(tenchuanhoa);
        String ngay[]=cs.getDayOfBirth().split("-");
        String chuanhoangay=ngay[1]+"/"+ngay[0]+"/"+ngay[2];
        cs.setDayOfBirth(chuanhoangay);
        
        String taikhoan="";
        for(int i=0;i<tach.length-1;i++){
            taikhoan += tach[i].charAt(0);
        }
        taikhoan += tach[tach.length-1];
        cs.setUserName(taikhoan);
        
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        ObjectOutputStream oos= new ObjectOutputStream(baos);
        oos.writeObject(cs);
        oos.flush();
        
        byte[] objectdata=baos.toByteArray();
        byte[] kq= new byte[8+objectdata.length];
        byte[] requestid= rid.getBytes();
        
        System.arraycopy(requestid, 0,kq, 0, 8);
        System.arraycopy(objectdata, 0,kq, 8, objectdata.length);
        
        DatagramPacket gui1 = new DatagramPacket(kq, kq.length, server, port);
        socket.send(gui1);
        socket.close();
    }
    
}
