package server.handlers;

import java.util.Timer;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * @author qmarqeva
 */
public class EquiposPipelineFactory implements ChannelPipelineFactory {

    Timer tm;

    public EquiposPipelineFactory(Timer stm) {
        this.tm = stm;
    }

    public ChannelPipeline getPipeline() {

        ChannelPipeline channelInit = Channels.pipeline();

        /*
            channelInit.addLast("framer1", new DelimiterBasedFrameDecoder(
            8192, new ChannelBuffer[]{
            ChannelBuffers.wrappedBuffer("?".getBytes())
            }));
         */
        channelInit.addLast("framer1", new MyDecoder());  //delimitador
        channelInit.addLast("process", new Procesamiento(tm));  //procesamiento
        channelInit.addLast("encoder", new MyEncoder()); //envio-escritura

        return channelInit;

    }
}
