package app.domain.event;

import org.dresign.event.DomainEvent;

import app.domain.Agriculteur;
import app.domain.Legume;
import app.domain.Prix;

@DomainEvent
public class MiseEnVente {

	private Prix prix;
	public Prix getPrix() {
		return prix;
	}

	public Legume getLegume() {
		return legume;
	}

	public Agriculteur getAgriculteur() {
		return agriculteur;
	}

	private Legume legume;
	private Agriculteur agriculteur;

	public MiseEnVente(Agriculteur agriculteur, Legume legume, Prix prix) {
		this.agriculteur=agriculteur;
		this.legume=legume;
		this.prix=prix;
	}

}
