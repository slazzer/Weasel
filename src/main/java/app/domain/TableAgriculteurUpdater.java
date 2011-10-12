package app.domain;


import org.dresign.event.AsyncDomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.domain.event.CreationAgriculteur;
import app.domain.event.MiseEnVente;
import app.infrastructure.AgiculteurLightRepository;
import app.query.beans.AgriculteurComplexeView;
import app.query.beans.AgriculteurSimpleView;

@Service
public class TableAgriculteurUpdater {

	@Autowired
	private AgiculteurLightRepository repository;
	
	@AsyncDomainEventHandler
	@Transactional
	public void updateTable(CreationAgriculteur event) throws InterruptedException{
			Thread.sleep(300);
		Long id = event.getAgriculteur().getDb_identifier();
		String nom = event.getAgriculteur().getNom();
		repository.add(new AgriculteurSimpleView(id,nom));
		repository.add(new AgriculteurComplexeView(id,nom,""));
	}
	
	@AsyncDomainEventHandler
	@Transactional
	public void updateTable(MiseEnVente event) throws InterruptedException{
			Thread.sleep(100);
		AgriculteurComplexeView agriculteurBean = repository.findComplexById(event.getAgriculteur().getDb_identifier());
		agriculteurBean.setListeDeLegumeALaVente(agriculteurBean.getListeDeLegumeALaVente()+","+event.getLegume().getNom());
		repository.update(agriculteurBean);
	}
}
