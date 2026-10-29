package UDP;

import java.net.*;
import java.io.*;

public class UDPOBJ3 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        // Gui ma sinh vien va ma cau hoi
        String code = ";B23DCCN121;Eubtqpu1";
        byte[] gui = code.getBytes();

        DatagramPacket goiGui = new DatagramPacket(
                gui, gui.length, server, port
        );
        socket.send(goiGui);

        // Nhan du lieu tu server
        byte[] buffer = new byte[4096];
        DatagramPacket goiNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(goiNhan);

        // Lay requestId trong 8 byte dau
        byte[] data = goiNhan.getData();
        int length = goiNhan.getLength();
        String requestId = new String(data, 0, 8);

        // Doc doi tuong Employee tu cac byte con lai
        ByteArrayInputStream bais = new ByteArrayInputStream(
                data, 8, length - 8
        );
        ObjectInputStream ois = new ObjectInputStream(bais);

        Employee e = (Employee) ois.readObject();

        // 1. Chuan hoa ten: viet hoa chu cai dau moi tu
        String ten = e.getName().trim().toLowerCase();
        String[] tach = ten.split("\\s+");

        String tenMoi = "";

        for (int i = 0; i < tach.length; i++) {
            if (i > 0) {
                tenMoi += " ";
            }

            tenMoi += tach[i].substring(0, 1).toUpperCase()
                    + tach[i].substring(1);
        }

        e.setName(tenMoi);

        // 2. Tinh tong cac chu so cua nam trong hireDate
        // Vi du: 2023-07-15 -> 2 + 0 + 2 + 3 = 7
        String hireDate = e.getHireDate();
        String[] ngay = hireDate.split("-");

        String nam = ngay[0];
        int tong = 0;

        for (int i = 0; i < nam.length(); i++) {
            tong += nam.charAt(i) - '0';
        }

        // Tang luong theo phan tram cua tong cac chu so nam
        double luongMoi = e.getSalary() * (1 + tong / 100.0);

        // Lam tron den 2 chu so thap phan
        luongMoi = Math.round(luongMoi * 100.0) / 100.0;

        e.setSalary(luongMoi);

        // 3. Chuyen hireDate tu yyyy-mm-dd sang dd/mm/yyyy
        String hireDateMoi = ngay[2] + "/" + ngay[1] + "/" + ngay[0];

        e.setHireDate(hireDateMoi);

        // Chuyen doi tuong Employee thanh mang byte
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(e);
        oos.flush();

        byte[] objectData = baos.toByteArray();

        // Ghep requestId va doi tuong Employee
        byte[] ketQua = new byte[8 + objectData.length];
        byte[] requestData = requestId.getBytes();

        System.arraycopy(requestData, 0, ketQua, 0, 8);
        System.arraycopy(objectData, 0, ketQua, 8, objectData.length);

        // Gui ket qua ve server
        DatagramPacket goiTraLoi = new DatagramPacket(
                ketQua, ketQua.length, server, port
        );

        socket.send(goiTraLoi);

        ois.close();
        oos.close();
        socket.close();
    }
}