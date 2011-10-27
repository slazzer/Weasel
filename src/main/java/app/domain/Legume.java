package app.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Legume {

	@Id
	@GeneratedValue
	private Long db_identifier;

	@Basic
	private String nom;

	public Legume() {
	}

	public Long getDb_identifier() {
		return db_identifier;
	}

	public Legume(String nom) {
		super();
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

}
