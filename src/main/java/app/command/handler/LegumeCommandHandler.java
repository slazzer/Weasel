package app.command.handler;

import org.dresign.command.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import app.command.AjouterLegume;
import app.domain.Legume;
import app.domain.repository.LegumeRepository;

@Component("LegumeHandler")
@Transactional
public class LegumeCommandHandler {

	@Autowired
	LegumeRepository repository;
	
	@CommandHandler
	public void handleAjouter(AjouterLegume command){
		Legume legume = new Legume(command.nom);
		repository.add(legume);
	}
}
