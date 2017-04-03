package fix.netty;

import fix.Message;
import fix.Session;
import fix.SessionFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Acceptor {

    @FunctionalInterface
    public interface ConnectHandler {
        void handle(Session session);
    }

    @FunctionalInterface
    public interface ReceiveHandler {
        void handle(Session session, Message message);
    }

    private final SessionFactory factory;
    private final String host;
    private final Integer port;

    private ConnectHandler connectHandler;
    private ReceiveHandler receiveHandler;

    public Acceptor(SessionFactory factory, String host, Integer port) {
        this.factory = factory;
        this.host = host;
        this.port = port;
    }

    public void onConnect(ConnectHandler handler) {
        this.connectHandler = handler;
    }

    public void onReceive(ReceiveHandler handler) {
        this.receiveHandler = handler;
    }

    public void run() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new Decoder(), new AcceptorHandler(factory, connectHandler, receiveHandler));
                        }
                    });

            ChannelFuture f = b.bind(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
