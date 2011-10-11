package app.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Legume {
	
	public Long getDb_identifier() {
		return db_identifier;
	}

	public Legume() {
	}

	@Id
    @GeneratedValue
    private Long db_identifier;
	
	@Basic
private String nom;

public String getNom() {
	return nom;
}

public void setNom(String nom) {
	this.nom = nom;
}

public Legume(String nom) {
	super();
	this.nom = nom;
}
}
