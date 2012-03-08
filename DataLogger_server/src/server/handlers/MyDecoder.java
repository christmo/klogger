/**
 * Lectura de paquetes en base a
 * la cabecera del paquete longitud.
 * La longitud tiene 2 bits
 * que son siempre los iniciales.
 */
package server.handlers;

import Utilities.UtilidadesApp;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * @author qmarqeva
 */
public class MyDecoder extends FrameDecoder {

    protected Object decode(ChannelHandlerContext ctx,
            Channel channel,
            ChannelBuffer buf) throws Exception {

        if (buf.readableBytes() < 2) {
            return null;
        }

        buf.markReaderIndex();
        int length = buf.readChar();

        if (length > UtilidadesApp.longTrama) {
            String auxMsj = "Trama mayor al tamaño permitido";
            if (UtilidadesApp.getDebugMode()) {
                System.out.println(auxMsj);
            } else {
                UtilidadesApp.logError.info(auxMsj);
            }
            ctx.getChannel().disconnect();
        }

        if (buf.readableBytes() < length - 2) {
            buf.resetReaderIndex();
            return null;
        }
        ChannelBuffer frame = buf.readBytes(length - 2);
        return frame;
    }
}
