package server;

import Utilities.UtilidadesApp;
import java.net.InetSocketAddress;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import server.handlers.EquiposPipelineFactory;

public class initServer {

    private ServerBootstrap bootstrap;
    private ChannelFactory factory;
    public static final ChannelGroup groupChannel = new DefaultChannelGroup("myServer");
    Timer tmPrincipal = new Timer();

    public boolean runServers(int portEquipos) {
        /**
         * Servidor de Equipos
         */
        factory =
                new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool());


        bootstrap = new ServerBootstrap(factory);
        bootstrap.setPipelineFactory(new EquiposPipelineFactory(tmPrincipal));

        bootstrap.setOption("child.tcpNoDelay", true);

        Channel principal;
        try {
            principal = bootstrap.bind(new InetSocketAddress(portEquipos));
        } catch (ChannelException ce) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("No se puede abrir el PUERTO equipos[" + portEquipos + "]. Esta ocupado? " + ce);
            } else {
                UtilidadesApp.logError.info("No se puede abrir el PUERTO equipos [" + portEquipos + "]. Esta ocupado? " + ce);
            }
            return false;
        }

        groupChannel.add(principal);

        if (UtilidadesApp.getDebugMode()) {
            System.out.println("Servidores Iniciados. [" + new GregorianCalendar().getTime() + "]");
        } else {
            UtilidadesApp.logInfo.info("Servidores Iniciados. [" + new GregorianCalendar().getTime() + "]");
        }
        return true;
    }

    /**
     * Detener los servidores y
     * Liberar Recursos
     */
    public void detenerServidores() {
        groupChannel.close();
        factory.releaseExternalResources();

        if (UtilidadesApp.getDebugMode()) {
            System.out.println("Servidores Detenidos. ");
        } else {
            UtilidadesApp.logInfo.info("Servidores Detenidos. ");
        }

    }
}
