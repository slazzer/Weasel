package app.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.query.beans.AgriculteurComplexeView;
import app.query.beans.AgriculteurSimpleView;

@Repository
@Transactional
public class AgiculteurLightRepository {

	  @PersistenceContext
	  private EntityManager entityManager;
	  
	  @Transactional(propagation=Propagation.REQUIRES_NEW)
	  public void add(AgriculteurSimpleView element){
		  entityManager.persist(element);
		  entityManager.flush();
	  }
	  
	  public List<AgriculteurSimpleView>findAll(){
		  return entityManager.createQuery("SELECT x FROM AgriculteurSimpleView x").getResultList();
	  }

	  @Transactional(propagation=Propagation.REQUIRES_NEW)
	public void add(AgriculteurComplexeView agriculteurComplexeView) {
		 entityManager.persist(agriculteurComplexeView);
		  entityManager.flush();
	}

	public AgriculteurComplexeView findComplexById(Long db_identifier) {
		return entityManager.find(AgriculteurComplexeView.class, db_identifier);
	}

	public void update(AgriculteurComplexeView agriculteurBean) {
		entityManager.merge(agriculteurBean);
		  entityManager.flush();
	}
	  
}
