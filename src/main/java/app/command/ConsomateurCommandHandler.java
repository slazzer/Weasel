package app.command;

import org.dresign.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import test.BusAccessor;
import app.domain.Consomateur;
import app.domain.ConsomateurRepository;
import app.domain.event.CreationConsomateur;

@Component("ConsoHandler")
public class ConsomateurCommandHandler {

	@Autowired
	ConsomateurRepository repository;
	
	@CommandHandler
	public void handleAjouter(AjouterConsomateur command){
		Consomateur consomateur = new Consomateur(command.name,command.email);
		repository.add(consomateur);
		BusAccessor.bus().dispatch(new CreationConsomateur(consomateur));
	}
}
