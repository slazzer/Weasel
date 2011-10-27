package app.query.view;

public class LegumeView {
	public Long id;
	public String nom;
	
	public LegumeView (Long id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
}
