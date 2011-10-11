package test;
import org.dresign.event.AsyncDomainEventHandler;
import org.dresign.event.DomainEventHandler;
import org.springframework.stereotype.Component;

@Component
public class DomainHandlerUnderTest {
	
	@AsyncDomainEventHandler
	public void doHandlerAsync(DomainEventUnderTest event) throws Throwable{
		System.out.println("handleAsynchronously");
		Thread.sleep(1000);
	};
	
	@DomainEventHandler
	public void doHandler(DomainEventUnderTest event) throws Throwable{
		System.out.println("handle");
		
	};
	
	@DomainEventHandler
	public void doHandlerFromCommand(DomainEventUnderTestWithContenu event) throws Throwable{
		System.out.println("handle of : "+event.getEventContenu());
		
	};
}
