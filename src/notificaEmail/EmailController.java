package notificaEmail;

import java.util.Calendar;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class EmailController {

    private static final String USERNAME = "lazylistnoreply@gmail.com";
    private static final String PASSWORD = "progettoINGSW";

    private static final String EMAIL_FROM = "lazylistnoreply@gmail.com";
    private String EMAIL_TO;

    private String EMAIL_SUBJECT;
    private String EMAIL_TEXT;
    
    private boolean enabled;
    
    //nel prossimo attributo mettiamo i nostri indirizzi email, e lui controllera' quando manda la mail di non inviarla a persone
    //che non sono qua dentro
    //altrimenti mariorossi@gmail.com ci viene a bussare alla porta con la FBI
    private Set<String> whiteList;
    
    public EmailController(String EMAIL_TO, Set<String> whiteList) {
    	this.EMAIL_TO = EMAIL_TO;
    	this.enabled = false;
    	this.whiteList = whiteList;
    }
    
    //Per testing
    
	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public void mandaEmail() {
       
		if (!this.isEnabled()) {
			System.out.println("EmailController disabilitato! abilitarlo con setEnabled(true)");
			return;
		}
		else {
			//Controllo prima che l'indirizzo email e' appartenente alla whitelist senno' non invio
			if (!whiteList.contains(EMAIL_TO)) { //errore
				System.out.println("L'email inserita non fa parte della whitelist");
				System.out.println("WhiteList:");
				for (String s : this.whiteList) {
					System.out.println(s);
				}
				System.out.println();
				return;
			}
			
			else {
				Properties prop = new Properties();
				prop.put("mail.smtp.host", "smtp.gmail.com");
			    prop.put("mail.smtp.port", "587");
			    prop.put("mail.smtp.auth", "true");
			    prop.put("mail.smtp.starttls.enable", "true"); //TLS
			    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			    prop.put("mail.debug", "true");
			     
			    javax.mail.Session session = javax.mail.Session.getInstance(prop,
			            new javax.mail.Authenticator() {
			                protected PasswordAuthentication getPasswordAuthentication() {
			                    return new PasswordAuthentication(USERNAME, PASSWORD);
			                }
			            }
			    );
		
			    try {
			
			        Message message = new MimeMessage(session);
			        message.setFrom(new InternetAddress(EMAIL_FROM));
			        message.setRecipients(
			                 Message.RecipientType.TO,
			                 InternetAddress.parse(EMAIL_TO)
			        );
			        message.setSubject(EMAIL_SUBJECT);
			        message.setText(EMAIL_TEXT);
			
			        Transport.send(message);
			
			        System.out.println("Done");
			
			    } catch (MessagingException e) {
			        e.printStackTrace();
			    }
			}
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
