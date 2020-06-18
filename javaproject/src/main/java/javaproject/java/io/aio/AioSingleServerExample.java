package javaproject.java.io.aio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * aio 单线程模型
 */
public class AioSingleServerExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8888));

        /**
         * 这个accept方法是个非阻塞的，所以服务启动后，没有client连上来，就会走到下面的while(true)，这个循环
         * 代表没有client连上来的时候，不能就让他直接过去了，往后应该做些什么，
         *
         * CompletionHandler 处理client来的连接，这是观察者模式
         */
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel client, Object attachment) {
                try {
                    serverChannel.accept(null,this);
                    System.out.println(client.getRemoteAddress());

                    ByteBuffer buffer = ByteBuffer.allocate(0124);
                    client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            System.out.println(new String(attachment.array(),0,result));
                            client.write(ByteBuffer.wrap("helloclient".getBytes()));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {

                            exc.printStackTrace();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        while(true){
            Thread.sleep(1000);
        }

    }
}
