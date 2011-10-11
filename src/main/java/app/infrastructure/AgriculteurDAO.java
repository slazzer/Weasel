package app.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Agriculteur;

@Repository
@Transactional
public class AgriculteurDAO {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void add(Agriculteur agriculteur){
    	entityManager.persist(agriculteur);
    	entityManager.flush();
    }
    
    public Agriculteur findById(Long id){
    	return entityManager.find(Agriculteur.class, id);
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
	public void update(Agriculteur agriculteur) {
		entityManager.merge(agriculteur);
    	entityManager.flush();
	}
}
