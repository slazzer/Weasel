package app.query.beans;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AgriculteurComplexeView  {
	@Id
    private Long db_identifier;
	
	@Basic
	private String Nom;
	
	@Basic
	private String ListeDeLegumeALaVente;

	public Long getDb_identifier() {
		return db_identifier;
	}

	public String getNom() {
		return Nom;
	}

	public AgriculteurComplexeView() {
	}

	public AgriculteurComplexeView(Long db_identifier, String nom,
			String listeDeLegumeALaVente) {
		super();
		this.db_identifier = db_identifier;
		Nom = nom;
		ListeDeLegumeALaVente = listeDeLegumeALaVente;
	}

	public String getListeDeLegumeALaVente() {
		return ListeDeLegumeALaVente;
	}

	public void setListeDeLegumeALaVente(String listeDeLegumeLegumeEnVente) {
		this.ListeDeLegumeALaVente=listeDeLegumeLegumeEnVente;
	}
}
