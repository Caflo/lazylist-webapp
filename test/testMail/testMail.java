package testMail;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import notificaEmail.EmailController;

public class testMail {

	@Test
	public void test() {
		
		Set<String> whiteList = new HashSet<>();
		
		//se vuoi aggiungere il tuo indirizzo email, aggiungi una riga qui
		//non fare casini
		whiteList.add("caflo1997@gmail.com");
		
		try {
			EmailController emailController = new EmailController("aaaaaaa.com", whiteList);
			
			emailController.setEMAIL_TEXT("PROVA");
			emailController.setEnabled(true);
			emailController.mandaEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
