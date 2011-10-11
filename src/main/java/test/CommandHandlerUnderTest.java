package test;

import org.dresign.command.CommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CommandHandlerUnderTest {

	@CommandHandler
	public void gereLesCommandes(CommandUnderTest command){
		System.out.println(command.getContenu());
		ObjectUnderTest objet = new ObjectUnderTest();
		objet.doThingsAndThrowEvent(command.getContenu());
	}
}
