package app.domain;


import java.util.List;

import org.dresign.event.AsyncDomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.event.MiseEnVente;
import app.infrastructure.Mailer;

@Service
public class ConsommateurNotifier {

	@Autowired
	private Mailer mailer;
	
	@Autowired
	private ConsomateurRepository consomateurRepository;
	
	@AsyncDomainEventHandler
	public void notifyEveryConsommateur(MiseEnVente event) throws InterruptedException{
		List<Consomateur> consomateurs=consomateurRepository.getAll();
		Thread.sleep(1000);
		System.out.println("Comment c'est long dedémarrer ma campagne de mailing...");
		for(Consomateur consommateur:consomateurs){
			mailer.sendMessage(consommateur.getEmail(), "le paysan "+event.getAgriculteur().getNom()+" a mis en vente "+event.getLegume().getNom()+" au prix de "+event.getPrix().getValeur().toPlainString());
		}
		
	}
}
