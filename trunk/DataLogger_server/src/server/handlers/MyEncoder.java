/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import Utilities.UtilidadesApp;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 *
 * @author qmarqeva
 */
public class MyEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {

        if (msg instanceof String) {
            String msj = String.valueOf(msg);           
            ChannelBuffer buf = ChannelBuffers.dynamicBuffer();

            buf.writeByte(0x00);
            buf.writeByte(msj.length() + 4 + 2);
            buf.writeByte(0x00);
            buf.writeByte(0x01);
            buf.writeByte(0x04);
            buf.writeByte(0x00);
            buf.writeBytes(msj.getBytes());                        
            return buf;
        } else {

            String msj = "Solo se puede enviar texto ["+msg+"]";
                if (UtilidadesApp.getDebugMode()) {
                    System.out.println(msj);
                } else {
                    UtilidadesApp.logError.info(msj);
                }

            return null;
        }

    }
}
