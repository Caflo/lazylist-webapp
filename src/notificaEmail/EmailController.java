package notificaEmail;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class EmailController {

	private static final String SMTP_SERVER = "smtp server ";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private static final String EMAIL_FROM = "From@gmail.com";
    private String EMAIL_TO;

    private String EMAIL_SUBJECT;
    private String EMAIL_TEXT;
    
    public EmailController(String EMAIL_TO, String EMAIL_SUBJECT) {
    	this.EMAIL_TO = EMAIL_TO;
    	this.EMAIL_SUBJECT = EMAIL_SUBJECT;
    }
    
	public void mandaEmail() {
		Properties prop = System.getProperties();
		System.out.println("TLSEmail Start");
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        javax.mail.Session sess = javax.mail.Session.getInstance(prop, null);
        Message msg = new MimeMessage(sess);

        try {
		
			// from
            msg.setFrom(new InternetAddress(EMAIL_FROM));
			// to 
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(EMAIL_TO, false));
			// subject
            msg.setSubject(EMAIL_SUBJECT);
			// content 
            msg.setText(EMAIL_TEXT);			
            msg.setSentDate(Calendar.getInstance().getTime());
			// Get SMTPTransport
            SMTPTransport t = (SMTPTransport) sess.getTransport("smtp");			
			// connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);	
			// send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());
            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
	
	public String getEMAIL_TO() {
		return EMAIL_TO;
	}

	public void setEMAIL_TO(String eMAIL_TO) {
		EMAIL_TO = eMAIL_TO;
	}

	public String getEMAIL_SUBJECT() {
		return EMAIL_SUBJECT;
	}

	public void setEMAIL_SUBJECT(String eMAIL_SUBJECT) {
		EMAIL_SUBJECT = eMAIL_SUBJECT;
	}

	public String getEMAIL_TEXT() {
		return EMAIL_TEXT;
	}

	public void setEMAIL_TEXT(String eMAIL_TEXT) {
		EMAIL_TEXT = eMAIL_TEXT;
	}

	public static String getSmtpServer() {
		return SMTP_SERVER;
	}

	public static String getUsername() {
		return USERNAME;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public static String getEmailFrom() {
		return EMAIL_FROM;
	}
}
