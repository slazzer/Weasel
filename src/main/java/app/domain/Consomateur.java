package app.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Consomateur {
	@Id
	@GeneratedValue
	private Long db_identifier;

	public Consomateur(String nom, String email) {
		this.nom = nom;
		this.email = email;
	}

	public Long getDb_identifier() {
		return db_identifier;
	}

	@Basic
	private String nom;

	@Basic
	private String email;

	public String getEmail() {
		return email;
	}

	public Consomateur() {
	}

	public String getNom() {
		return nom;
	}
}
