package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jboss.netty.util.internal.ConcurrentHashMap;

/**
 * @author Cumar C.
 */
public class UtilidadesApp {

    //Directorio Actual
    private static File WORKING_DIRECTORY;
    //Variables para Log
    public static final Logger logError = Logger.getLogger("ErrorLogger");
    public static final Logger logDataBase = Logger.getLogger("DatabaseLogger");
    public static final Logger logInfo = Logger.getLogger("InfoLogger");
    public static final Logger logTrama = Logger.getLogger("TramasLogger");
    public static final Logger logMsj = Logger.getLogger("MensajesLogger");
    //Estado de Depuración
    private static boolean isDebugMode = false;
    // Parametros del Sistema
    public static String bd_ip;
    public static String bd_user;
    public static String bd_pass;
    public static String bd_schema;
    public static int timeout;
    public static int timeoutCoop;
    //Correspondencia Equipos - Nombres
    public static ConcurrentHashMap<String, String> comandosAT;

    //longitud de trama
    public static int longTrama;

    /**
     * Devuelve el path actual del JAR
     * @return
     */
    public static File get() {
        if (WORKING_DIRECTORY == null) {

            String Recurso = UtilidadesApp.class.getSimpleName() + ".class";
            try {
                URL url = UtilidadesApp.class.getResource(Recurso);
                if (url.getProtocol().equals("file")) {
                    File f = new File(url.toURI());
                    do {
                        f = f.getParentFile();
                    } while (!f.isDirectory());
                    WORKING_DIRECTORY = f;
                } else if (url.getProtocol().equals("jar")) {
                    String expected = "!/" + Recurso;
                    String s = url.toString();
                    s = s.substring(4);
                    s = s.substring(0, s.length() - expected.length());
                    File f = new File(new URL(s).toURI());
                    do {
                        f = f.getParentFile();
                    } while (!f.isDirectory());
                    WORKING_DIRECTORY = f;
                }
            } catch (Exception e) {
                WORKING_DIRECTORY = new File(".");
            }
        }
        return WORKING_DIRECTORY;
    }

    /**
     * Activa (desactiva) el modo de Depuración
     * @param state
     */
    public static void setDebugMode(boolean state) {
        isDebugMode = state;
    }

    /**
     * Devuelve el valor que determina si el estado
     * Debug está activado.
     * @return
     */
    public static boolean getDebugMode() {
        return isDebugMode;
    }

    /**
     * Carga las configuraciones del Sistema
     */
    public static void cargaConfiguraciones() {

        try {
            //Configuraciones Generales
            ResourceBundle rb = new PropertyResourceBundle(new FileInputStream(
                    UtilidadesApp.get() + "/configFiles/configsystem.properties"));

            bd_ip = rb.getString("bd_ip");
            bd_schema = rb.getString("bd_schema");
            bd_user = rb.getString("bd_user");
            bd_pass = rb.getString("bd_pass");


            longTrama = Integer.parseInt(rb.getString("longTrama"));

            try {
                timeout = Integer.parseInt(rb.getString("timeout"));
            } catch (NumberFormatException nfe) {

                if (getDebugMode()) {
                    System.out.println("Problemas al determinar el valor de timeout. ");
                } else {
                    UtilidadesApp.logError.info("Problemas al determinar el valor de timeout. Cargado Valor por defecto [3]");
                }
                //valor por defecto
                timeout = 3;
            }
            setDebugMode(Boolean.parseBoolean(rb.getString("debug_mode")));

            //Configuración de Logs
            PropertyConfigurator.configure(UtilidadesApp.get() + "/configFiles/log.properties");

            if (getDebugMode()) {
                System.out.println("Carga exitosa de Configuraciones");
            } else {
                UtilidadesApp.logInfo.info("Carga exitosa de Configuraciones");
            }

        } catch (IOException ex) {
            if (getDebugMode()) {
                System.out.println("Problemas al cargar las configuraciones del Sistema." + ex);
            } else {
                UtilidadesApp.logError.info("Problemas al cargar las configuraciones del Sistema." + ex);
            }
            System.exit(1);
        }
    }

    /**
     * Devuelve el texto cifrado mediante
     * el algoritmo MD5.
     *
     * @param in
     * @return cifrado | null si no fue posible
     */
    public static String encript(String in) {
        String md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(in.getBytes("UTF-8"), 0, in.length());
            byte[] bt = md.digest();
            BigInteger bi = new BigInteger(1, bt);
            md5 = bi.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            if (getDebugMode()) {
                System.out.println("encript error: " + ex);
            } else {
                UtilidadesApp.logError.info("encript error: " + ex);
            }
            return null;
        } catch (UnsupportedEncodingException ex) {
            if (getDebugMode()) {
                System.out.println("encript error: " + ex);
            } else {
                UtilidadesApp.logError.info("encript error: " + ex);
            }
            return null;
        }
        return md5;
    }

    public static String eliminarEspacios(String in) {
        in = in.substring(4, in.length());
        Pattern patron = Pattern.compile("[ ]+");
        Matcher encaja = patron.matcher(in);
        return encaja.replaceAll("");
    }
}
