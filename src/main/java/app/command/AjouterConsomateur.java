package app.command;

import org.dresign.command.Command;

@Command
public class AjouterConsomateur {
	public String nom;
	public String email;
	
	public AjouterConsomateur(String nom, String email) {
		this.nom = nom;
		this.email = email;
	}
}
