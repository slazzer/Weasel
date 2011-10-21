package app.domain.event;

import org.dresign.event.DomainEvent;

import app.domain.Agriculteur;

@DomainEvent
public class AgriculteurInscrit {

	private Agriculteur agriculteur;

	public Agriculteur getAgriculteur() {
		return agriculteur;
	}

	public AgriculteurInscrit(Agriculteur agriculteur) {
		this.agriculteur = agriculteur;
	}

}
