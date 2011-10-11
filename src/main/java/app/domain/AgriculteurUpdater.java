package app.domain;


import org.dresign.event.DomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.event.MiseEnVente;
import app.infrastructure.AgriculteurDAO;

@Service
public class AgriculteurUpdater {

	@Autowired
	private AgriculteurDAO agriculteurDAO;
	
	@DomainEventHandler
	public void updateTable(MiseEnVente event){
		agriculteurDAO.update(event.getAgriculteur());
	}
}
