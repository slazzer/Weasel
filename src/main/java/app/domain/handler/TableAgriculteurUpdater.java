package app.domain.handler;


import org.dresign.event.AsyncDomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.domain.event.AgriculteurInscrit;
import app.domain.event.LegumeMisEnVente;
import app.infrastructure.dao.AgiculteurReadDAO;
import app.query.view.AgriculteurComplexeView;
import app.query.view.AgriculteurSimpleView;

@Service("DomainTableAgriculteurUpdater")
@Transactional
public class TableAgriculteurUpdater {

	@Autowired
	private AgiculteurReadDAO dao;
	
	@AsyncDomainEventHandler
	public void updateTable(AgriculteurInscrit event) throws InterruptedException{
			Thread.sleep(300);
		Long id = event.getAgriculteur().getDb_identifier();
		String nom = event.getAgriculteur().getNom();
		dao.add(new AgriculteurSimpleView(id,nom));
		dao.add(new AgriculteurComplexeView(id,nom,""));
	}
	
	@AsyncDomainEventHandler
	public void updateTable(LegumeMisEnVente event) throws InterruptedException{
			Thread.sleep(100);
		AgriculteurComplexeView agriculteurBean = dao.findComplexById(event.getAgriculteur().getDb_identifier());
		String legumeDejEnVente = agriculteurBean.legumesALaVente;
		if(legumeDejEnVente.length()>0){
			legumeDejEnVente+=", ";
		}
		agriculteurBean.legumesALaVente = legumeDejEnVente+event.getLegume().getNom();
		dao.update(agriculteurBean);
	}
}
