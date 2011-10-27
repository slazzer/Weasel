package app.command.handler;

import java.math.BigDecimal;

import org.dresign.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import app.command.InscrireAgriculteur;
import app.command.MettreEnVenteLegume;
import app.domain.Agriculteur;
import app.domain.Legume;
import app.domain.Prix;
import app.domain.event.AgriculteurInscrit;
import app.domain.repository.AgriculteurRepository;
import app.domain.repository.LegumeRepository;
import app.domain.specification.LegumeDejaEnVente;
import app.domain.specification.PrixInvalide;
import app.infrastructure.bus.EventBus;

@Component("AgriHandler")
@Transactional
public class AgriculteurCommandHandler {

	@Autowired
	private AgriculteurRepository repository;
	
	@Autowired
	private LegumeRepository legumeRepository;
	
	@CommandHandler
	public void handleInscrire(InscrireAgriculteur command){
		Agriculteur agriculteur = new Agriculteur(command.nom);
		repository.add(agriculteur);
		EventBus.bus().dispatch(new AgriculteurInscrit(agriculteur));
	}
	
	@CommandHandler
	public void handleMettreEnvente(MettreEnVenteLegume command) throws PrixInvalide, LegumeDejaEnVente{
		Agriculteur agriculteur = repository.getById(command.agriculteurId);
		Legume legume = legumeRepository.getById(command.legumeId);
		Prix prix = new Prix(BigDecimal.valueOf(Long.parseLong(command.prix)));
		agriculteur.metEnVente(legume, prix );
	}
}
