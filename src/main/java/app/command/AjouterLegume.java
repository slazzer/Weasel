package app.command;

import org.dresign.command.Command;

@Command
public class AjouterLegume {
	public String nom;
	public AjouterLegume(String nom) {
		this.nom = nom;
	}
}
