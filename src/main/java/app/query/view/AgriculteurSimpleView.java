package app.query.view;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AgriculteurSimpleView {

	@Id
	private Long db_identifier;

	@Basic
	public String nom;

	public AgriculteurSimpleView() {
	}

	public AgriculteurSimpleView(Long db_identifier, String nom) {
		this.db_identifier = db_identifier;
		this.nom = nom;
	}

	public Long getDb_identifier() {
		return db_identifier;
	}

	public String getNom() {
		return this.nom;
	}

}
