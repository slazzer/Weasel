package app.domain.handler;


import java.util.List;

import org.dresign.event.AsyncDomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Consomateur;
import app.domain.ConsomateurRepository;
import app.domain.event.LegumeMisEnVente;
import app.infrastructure.Mailer;

@Service
public class ConsommateurNotifier {

	@Autowired
	private Mailer mailer;
	
	@Autowired
	private ConsomateurRepository consomateurRepository;
	
	@AsyncDomainEventHandler
	public void notifyEveryConsommateur(LegumeMisEnVente event) throws InterruptedException{
		List<Consomateur> consomateurs = consomateurRepository.getAll();
		Thread.sleep(2000);
		System.out.println("Comment c'est long demarrer ma campagne de mailing...");
		for(Consomateur consommateur : consomateurs){
			Thread.sleep(1000);
			mailer.sendMessage(consommateur.getEmail(), "le paysan "+event.getAgriculteur().getNom()+" a mis en vente "+event.getLegume().getNom()+" au prix de "+event.getPrix().getValeur().toPlainString());
		}
		
	}
}
