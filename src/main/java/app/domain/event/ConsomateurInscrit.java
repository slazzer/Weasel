package app.domain.event;

import org.dresign.event.DomainEvent;

import app.domain.Consomateur;

@DomainEvent
public class ConsomateurInscrit {

	private Consomateur consomateur;

	public Consomateur getConsomateur() {
		return consomateur;
	}

	public ConsomateurInscrit(Consomateur consomateur) {
		this.consomateur = consomateur;
	}

}
