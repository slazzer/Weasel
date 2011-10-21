package app.command.handler;

import org.dresign.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import app.command.AjouterConsomateur;
import app.domain.Consomateur;
import app.domain.ConsomateurRepository;
import app.domain.event.ConsomateurInscrit;
import app.infrastructure.bus.DomainBus;

@Component("ConsoHandler")
@Transactional
public class ConsomateurCommandHandler {

	@Autowired
	ConsomateurRepository repository;
	
	@CommandHandler
	public void handleAjouter(AjouterConsomateur command){
		Consomateur consomateur = new Consomateur(command.nom,command.email);
		repository.add(consomateur);
		DomainBus.bus().dispatch(new ConsomateurInscrit(consomateur));
	}
}
