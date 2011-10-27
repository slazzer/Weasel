package app.query.view;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AgriculteurComplexeView {
	
	@Id
	private Long db_identifier;

	@Basic
	public String nom;

	@Basic
	public String legumesALaVente;

	public Long getDb_identifier() {
		return db_identifier;
	}

	public AgriculteurComplexeView() {
	}

	public AgriculteurComplexeView(Long db_identifier, String nom,
			String legumesALaVente) {
		super();
		this.db_identifier = db_identifier;
		this.nom = nom;
		this.legumesALaVente = legumesALaVente;
	}

	public String getNom() {
		return nom;
	}
	
	public String getLegumesALaVente() {
		return legumesALaVente;
	}

}
