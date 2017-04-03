package fix.netty;

import fix.*;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class AcceptorHandler extends ChannelInboundHandlerAdapter {

    private class AcceptorTransport implements Transport {

        private ChannelHandlerContext ctx;

        public AcceptorTransport(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void send(String message) {
            System.out.println("Send: " + message.toString());
            ChannelFuture cf = ctx.writeAndFlush(Unpooled.wrappedBuffer(message.getBytes()));
            if (!cf.isSuccess()) {
                System.out.println("Send failed: " + cf.cause());
            }
        }
    }

    private Acceptor.ConnectHandler connectHandler;
    private Acceptor.ReceiveHandler receiveHandler;
    private SessionFactory factory;
    private Session session;

    AcceptorHandler(SessionFactory factory, Acceptor.ConnectHandler connectHandler, Acceptor.ReceiveHandler receiveHandler) {
        this.connectHandler = connectHandler;
        this.receiveHandler = receiveHandler;
        this.factory = factory;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) {
        Message msg = new Message((String) obj);
        if (session == null) {
            SessionId id = new SessionId(msg);
            session = factory.getSession(id);
            if (session.getTransport() == null) {
                session.setTransport(new AcceptorTransport(ctx));
                connectHandler.handle(session);
            } else {
                // exception - already connected
            }
        }
        if (session != null) {
            receiveHandler.handle(session, msg);
        } else {
            // exception - wtf?
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
