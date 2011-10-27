package app.command;

import org.dresign.command.Command;

@Command
public class MettreEnVenteLegume {

	public Long agriculteurId;
	public Long legumeId;
	public String prix;

	public MettreEnVenteLegume(Long agriculteurId, Long legumeId, String prix) {
		this.agriculteurId = agriculteurId;
		this.legumeId = legumeId;
		this.prix = prix;
	}

}
