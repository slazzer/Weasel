package app.domain.handler;


import org.dresign.event.DomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.event.LegumeMisEnVente;
import app.infrastructure.dao.AgriculteurDAO;

@Service
public class AgriculteurUpdater {

	@Autowired
	private AgriculteurDAO agriculteurDAO;
	
	@DomainEventHandler
	public void updateTable(LegumeMisEnVente event){
		agriculteurDAO.update(event.getAgriculteur());
	}
}
