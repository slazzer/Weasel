package app.domain.event;

import org.dresign.event.DomainEvent;

import app.domain.Consomateur;

@DomainEvent
public class CreationConsomateur {

	private Consomateur consomateur;

	public Consomateur getConsomateur() {
		return consomateur;
	}

	public CreationConsomateur(Consomateur consomateur) {
		this.consomateur = consomateur;
	}

}
