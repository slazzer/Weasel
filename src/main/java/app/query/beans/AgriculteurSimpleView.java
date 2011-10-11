package app.query.beans;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AgriculteurSimpleView {
	public AgriculteurSimpleView(Long db_identifier, String nom) {
		this.db_identifier = db_identifier;
		Nom = nom;
	}

	public AgriculteurSimpleView() {
	}

	@Id
    private Long db_identifier;
	
	@Basic
	private String Nom;

	public Long getDb_identifier() {
		return db_identifier;
	}

	public String getNom() {
		return Nom;
	}
}
