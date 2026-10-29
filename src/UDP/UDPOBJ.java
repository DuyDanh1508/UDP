package UDP;

import java.net.*;
import java.io.*;

public class UDPOBJ {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        String code = ";B23DCCN121;8Eq98BDv";

        DatagramPacket goiGui = new DatagramPacket(code.getBytes(), code.length(), server, port);
        socket.send(goiGui);

        byte[] buffer = new byte[1024];
        DatagramPacket goiNhan = new DatagramPacket(buffer, buffer.length);
        socket.receive(goiNhan);

        byte[] data = goiNhan.getData();
        int length = goiNhan.getLength();

        String requestId = new String(data, 0, 8);

        ByteArrayInputStream bais = new ByteArrayInputStream(data, 8, length - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);

        Student st = (Student) ois.readObject();

        String ten = st.getName().trim().toLowerCase();
        String[] tach = ten.split("\\s+");


        // Loai bo khoang trang thua trong ten
        String tenChuanHoa = "";
        for (int i = 0; i < tach.length; i++) {
            if (i > 0) {
                tenChuanHoa += " ";
            }
            tenChuanHoa += tach[i].substring(0, 1).toUpperCase()
                    + tach[i].substring(1);
        }

        st.setName(tenChuanHoa);

        String email = tach[tach.length - 1];
        for (int i = 0; i < tach.length - 1; i++) {
            email += tach[i].charAt(0);
        }
        email += "@ptit.edu.vn";

        st.setEmail(email);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(st);
        oos.flush();

        byte[] objectData = baos.toByteArray();
        byte[] ketQua = new byte[8 + objectData.length];

        byte[] requestData = requestId.getBytes();
        System.arraycopy(requestData, 0, ketQua, 0, 8);
        System.arraycopy(objectData, 0, ketQua, 8, objectData.length);

        DatagramPacket goiTraLoi = new DatagramPacket(
                ketQua, ketQua.length, server, port
        );
        socket.send(goiTraLoi);

        ois.close();
        oos.close();
        socket.close();
    }
}