package app.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Consomateur;

@Repository
@Transactional
public class ConsomateurDAO {
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void add(Consomateur consomateur){
    	entityManager.persist(consomateur);
    	entityManager.flush();
    }
    
    public Consomateur findById(Long id){
    	return entityManager.find(Consomateur.class, id);
    }

	public List<Consomateur> findAll() {
		return entityManager.createQuery("SELECT c from Consomateur c").getResultList();
	}
}
