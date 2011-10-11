package app.command;

import java.math.BigDecimal;

import org.dresign.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import test.BusAccessor;
import app.domain.Agriculteur;
import app.domain.AgriculteurRepository;
import app.domain.Legume;
import app.domain.LegumeRepository;
import app.domain.Prix;
import app.domain.event.CreationAgriculteur;
import app.domain.specification.PrixInvalide;

@Component("AgriHandler")
public class AgriculteurCommandHandler {

	@Autowired
	private AgriculteurRepository repository;
	
	@Autowired
	private LegumeRepository legumeRepository;
	
	@CommandHandler
	public void handleAjouter(AjouterAgriculteur command){
		Agriculteur agriculteur = new Agriculteur(command.name,command.email);
		repository.add(agriculteur);
		BusAccessor.bus().dispatch(new CreationAgriculteur(agriculteur));
	}
	
	@CommandHandler
	public void handleMettreEnvente(MettreEnVente command) throws PrixInvalide{
		Agriculteur agriculteur = repository.getById(command.agriculteurId);
		Legume legume = legumeRepository.getById(command.LegumeId);
		Prix prix = new Prix(BigDecimal.valueOf(Long.parseLong(command.prix)));
		agriculteur.metEnVente(legume, prix );
	}
}
