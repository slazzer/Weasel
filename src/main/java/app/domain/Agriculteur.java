package app.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import app.domain.event.LegumeMisEnVente;
import app.domain.specification.LegumeDejaEnVente;
import app.domain.specification.PrixInvalide;
import app.domain.specification.PrixValide;

import static app.infrastructure.bus.EventBus.notifyEvent;

@Entity
public class Agriculteur {
	
	@Id
	@GeneratedValue
	private Long db_identifier;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Map<Legume, Prix> aLaVente;

	@Basic
	private String nom;

	public Agriculteur() {
	}
	
	public Agriculteur(String nom) {
		this.nom = nom;
	}

	public Long getDb_identifier() {
		return db_identifier;
	}

	public String getNom() {
		return nom;
	}

	public Map<Legume, Prix> getALaVente() {
		return aLaVente;
	}

	public void metEnVente(Legume legume, Prix prix) throws PrixInvalide,
			LegumeDejaEnVente {
		checkMiseEnVenteValide(legume, prix);

		if (aLaVente == null) {
			aLaVente = new HashMap<Legume, Prix>();
		}
		aLaVente.put(legume, prix);
		
		notifyEvent(new LegumeMisEnVente(this, legume, prix));
	}

	public boolean vendDeja(Legume legume) {
		if (aLaVente != null) {
			for (Legume legumeDeLaGriculteur : aLaVente.keySet()) {
				if (legume.getNom().equals(legumeDeLaGriculteur.getNom())) {
					return true;
				}
			}
		}
		return false;
	}

	private void checkMiseEnVenteValide(Legume legume, Prix prix)
			throws PrixInvalide, LegumeDejaEnVente {
		if (!PrixValide.isSatisfiedBy(prix)) {
			throw new PrixInvalide();
		}
		if (vendDeja(legume)) {
			throw new LegumeDejaEnVente();
		}
	}

}
