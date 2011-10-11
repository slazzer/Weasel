package infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
	  
}
