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
import app.infrastructure.bus.DomainBus;

@Entity
public class Agriculteur {
	@Id
	@GeneratedValue
	private Long db_identifier;

	public Agriculteur(String nom, String email) {
		this.nom = nom;
		this.email = email;
	}

	public Long getDb_identifier() {
		return db_identifier;
	}

	@OneToMany(cascade = CascadeType.ALL)
	private Map<Legume, Prix> aLaVente;

	@Basic
	private String nom;

	@Basic
	private String email;

	public String getEmail() {
		return email;
	}

	public String getNom() {
		return nom;
	}

	public Map<Legume, Prix> getALaVente() {
		return aLaVente;
	}

	public void metEnVente(Legume legume, Prix prix) throws PrixInvalide,
			LegumeDejaEnVente {
		if (!PrixValide.isSatisfiedBy(prix)) {
			throw new PrixInvalide();
		}
		if (!vendDeja(legume)) {
			if (aLaVente == null) {
				aLaVente = new HashMap<Legume, Prix>();
			}
			aLaVente.put(legume, prix);
			DomainBus.bus().dispatch(new LegumeMisEnVente(this, legume, prix));
		} else {
			throw new LegumeDejaEnVente();
		}
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

	public Agriculteur() {
	}
}
