package app.domain.event;

import org.dresign.event.DomainEvent;

import app.domain.Agriculteur;
import app.domain.Legume;
import app.domain.Prix;

@DomainEvent
public class LegumeMisEnVente {

	private Legume legume;
	private Agriculteur agriculteur;
	private Prix prix;

	public LegumeMisEnVente(Agriculteur agriculteur, Legume legume, Prix prix) {
		this.agriculteur=agriculteur;
		this.legume=legume;
		this.prix=prix;
	}
	
	public Prix getPrix() {
		return prix;
	}

	public Legume getLegume() {
		return legume;
	}

	public Agriculteur getAgriculteur() {
		return agriculteur;
	}

}
