package app.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import test.BusAccessor;
import app.domain.event.MiseEnVente;
import app.domain.specification.AALaVente;
import app.domain.specification.PrixInvalide;
import app.domain.specification.PrixValide;

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

	@OneToMany(cascade=CascadeType.ALL)
	private Map<Legume, Prix> aLaVente ;

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

	public void metEnVente(Legume legume, Prix prix) throws PrixInvalide {
		if (PrixValide.isSatisfiedBy(prix)
				&& !AALaVente.isSatisfiedBy(this, legume)) {
			if (aLaVente == null) {
				aLaVente = new HashMap<Legume, Prix>();
			}
			aLaVente = new HashMap<Legume, Prix>(aLaVente);
			aLaVente.put(legume, prix);
			BusAccessor.bus().dispatch(new MiseEnVente(this,legume,prix));
		} else {
			throw new PrixInvalide();
		}
	}

	public Agriculteur() {
	}
}
