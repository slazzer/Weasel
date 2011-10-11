package app.domain.event;

import org.dresign.event.DomainEvent;

import app.domain.Agriculteur;

@DomainEvent
public class CreationAgriculteur {

	private Agriculteur agriculteur;

	public Agriculteur getAgriculteur() {
		return agriculteur;
	}

	public CreationAgriculteur(Agriculteur agriculteur) {
		this.agriculteur = agriculteur;
	}

}
