package Utilities;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Cumar C.
 */
public class SendMail {


    public static boolean sendMail(
            String destination,
            String subject,
            String parte1) {

        try {
            String cuenta = "soporte@kradac.com";
            String clave = "kradacloja";

            Properties props = new Properties();
            props.setProperty("mail.smtp.auth", "true");
            //Activar para el resto de máquinas
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            // props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.user", cuenta);

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(destination));
            message.setSubject(subject);

            // Setting the Subject and Content Type
            message.setSubject(subject);
            message.setContent(parte1, "text/plain");


            Transport t = session.getTransport("smtp");
            t.connect(cuenta, clave);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
            if (UtilidadesApp.getDebugMode()) {
                System.out.println("No se ha podido enviar el mail a ["+destination+"] " + e.getMessage());
                e.printStackTrace();
            } else {
                UtilidadesApp.logError.info("No se ha podido enviar el mail a ["+destination+"] " + e.getMessage());
            }
            return false;
        }
        return true;
    }
}
