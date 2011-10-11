package test;

import org.dresign.event.DomainEvent;

@DomainEvent
public class DomainEventUnderTestWithContenu {

	private String eventContenu;

	public DomainEventUnderTestWithContenu(String contenu) {
		this.eventContenu=contenu;
	}

	public String getEventContenu() {
		return eventContenu;
	}

	public void setEventContenu(String eventContenu) {
		this.eventContenu = eventContenu;
	}

}
