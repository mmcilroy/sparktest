package fix.netty;

import fix.MessageReassembler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class Decoder extends ByteToMessageDecoder {

    private MessageReassembler reassembler = new MessageReassembler();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        System.out.println("Read: " + bytes.length + " bytes");
        reassembler.reassemble(new String(bytes), message -> {
            if (message != null) {
                out.add(message);
            }
        });
    }
}
