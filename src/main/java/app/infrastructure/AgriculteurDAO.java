package app.infrastructure;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import app.domain.Agriculteur;

@Repository
public class AgriculteurDAO {
	@PersistenceContext
	private EntityManager entityManager;

	public void add(Agriculteur agriculteur) {
		entityManager.persist(agriculteur);
		entityManager.flush();
	}

	public Agriculteur findById(Long id) {
		return entityManager.find(Agriculteur.class, id);
	}

	public void update(Agriculteur agriculteur) {
		entityManager.persist(agriculteur);
		entityManager.flush();
	}
}
