package UDP;

import java.net.*;
import java.util.*;

public class UDPBai2 {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2207;

        // a. Gui ma sinh vien va ma cau hoi
        String code = ";MASINHVIEN;MACAUHOI";

        byte[] guiData = code.getBytes();

        DatagramPacket gui = new DatagramPacket(guiData, guiData.length, server, port);

        socket.send(gui);

        // b. Nhan du lieu
        byte[] buffer = new byte[1024];

        DatagramPacket nhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(nhan);

        String s = new String(nhan.getData(), 0, nhan.getLength()).trim();

        System.out.println("Du lieu nhan: " + s);

        String[] tach = s.split(";", 2);
        String rid = tach[0];
        String data = tach[1];

        // Tach day so
        String[] a = data.split(",");
        int n = a.length;
        int[] b = new int[n];

        double tong = 0;

        for (int i = 0; i < n; i++) {
            b[i] = Integer.parseInt(a[i].trim());
            tong = tong + b[i];
        }

        // c. Sap xep day so tang dan
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (b[i] > b[j]) {
                    int tam = b[i];
                    b[i] = b[j];
                    b[j] = tam;
                }
            }
        }

        // Tim p90
        int vt = (int) Math.ceil(n * 0.9) - 1;
        int p90 = b[vt];

        // Dem so phan tu lon hon trung binh day ban dau
        double trungBinh = tong / n;
        int dem = 0;

        for (int i = 0; i < n; i++) {
            if (Integer.parseInt(a[i].trim()) > trungBinh) {
                dem++;
            }
        }

        // d. Gui ket qua
        String kq = rid + ";p90=" + p90 + ";aboveAvg=" + dem;

        System.out.println("Ket qua: " + kq);

        byte[] kqData = kq.getBytes();

        DatagramPacket traLoi = new DatagramPacket(
                kqData, kqData.length, server, port
        );

        socket.send(traLoi);

        // e. Dong socket
        socket.close();
    }
}