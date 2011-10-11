package test;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;



public class SpringTest {
	public static void main(String []args){

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");


		List<ObjectUnderTest> objects=new ArrayList<ObjectUnderTest>();
		for(int i=1;i<10;i++){
			objects.add(new ObjectUnderTest());
		}
		for(ObjectUnderTest o : objects){
			o.doThingsAndThrowEvent();
		}
		
		CommandUnderTest command =new CommandUnderTest();
		command.setContenu("contenu de la commande");
		CommandBusAccessor.bus().dispatch(command);
		
	}

		
	}
