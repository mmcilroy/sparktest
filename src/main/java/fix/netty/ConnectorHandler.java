package fix.netty;

import fix.Field;
import fix.Session;
import fix.SessionId;
import fix.Transport;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ConnectorHandler extends ChannelInboundHandlerAdapter {

    private class ConnectorTransport implements Transport {

        private ChannelHandlerContext ctx;

        public ConnectorTransport(ChannelHandlerContext ctx) {
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

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Session session = new Session(new SessionId("FIX.4.4", "S", "T"));
        session.setTransport(new ConnectorTransport(ctx));
        Field[] body = new Field[2];
        body[0] = new Field(98, "0");
        body[1] = new Field(108, "30");
        session.send("A", body);
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
