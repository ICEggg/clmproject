package javaproject.java.io.bio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * blocking io
 *
 *
 */
public class BioServerExample {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress("127.0.0.1",8888));
        while(true){
            //accept是阻塞的方法
            Socket s = socket.accept();
            new Thread(()->{
                handle(s);
            }).start();

        }

    }

    static void handle(Socket s){

        try {
            byte[] bytes = new byte[1024];
            int len = s.getInputStream().read(bytes);
            System.out.println(new String(bytes,0,len));

            s.getOutputStream().write(bytes,0,len);
            s.getOutputStream().flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
