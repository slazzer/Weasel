package app.infrastructure;

import org.springframework.stereotype.Service;

@Service
public class Mailer {

	public void sendMessage(String dest, String message){
		System.out.println(">>Send an email to @["+dest+"] :"+message+"<<");
	}
}
