import org.junit.Test;

import notificaEmail.EmailController;

public class testMail {

	@Test
	public void test() {
		
		try {
			EmailController emailController = new EmailController("caflo1997@gmail.com"
					, "IN ATTESA CONFERMA");
			
			emailController.setEMAIL_TEXT("PROVA");
			emailController.mandaEmail();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
