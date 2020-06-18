package javaproject.java.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * nio 实际使用中也不多了
 */
public class NioServerExample {
    public static void main(String[] args) throws IOException {
        //这个通道是双向的，可以读可以写
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("127.0.0.1",8888));
        //设置为非阻塞模型
        ssc.configureBlocking(false);

        System.out.println("server started , listening on :" +ssc.getLocalAddress());
        Selector selector = Selector.open();
        //注册selector，SelectionKey.OP_ACCEPT表示，这个selector的功能是：监听有没有客户端连上来了
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while(it.hasNext()){
                SelectionKey key = it.next();
                //一定要remove
                it.remove();
                handle(key);
            }
        }
    }


    private static void handle(SelectionKey key) throws IOException {
        if(key.isAcceptable()){
            try {
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                SocketChannel sc = ssc.accept();
                //要设置为非阻塞，不然和bio就没什么差别了
                sc.configureBlocking(false);

                sc.register(key.selector(),SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

            }
        }else if (key.isReadable()){
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(512);
                buffer.clear();
                int len = sc.read(buffer);

                if(len != -1){
                    System.out.println(new String(buffer.array(),0,len));
                }

                ByteBuffer bufferTowrite = ByteBuffer.wrap("helloClient".getBytes());
                sc.write(bufferTowrite);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(sc != null){
                    try {
                        sc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
