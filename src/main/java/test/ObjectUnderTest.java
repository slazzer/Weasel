package test;


public class ObjectUnderTest {
	public void doThingsAndThrowEvent(){
		System.out.println("DoThingsInObject");
		BusAccessor.bus().dispatch(new DomainEventUnderTest());
	}

	public void doThingsAndThrowEvent(String contenu) {
		System.out.println("Object Under Test Do things and throw an event");
		BusAccessor.bus().dispatch(new DomainEventUnderTestWithContenu(contenu));
		
	}
}
