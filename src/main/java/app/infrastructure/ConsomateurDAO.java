package app.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import app.domain.Consomateur;

@Repository
public class ConsomateurDAO {
	@PersistenceContext
	private EntityManager entityManager;

	public void add(Consomateur consomateur) {
		entityManager.persist(consomateur);
		entityManager.flush();
	}

	public Consomateur findById(Long id) {
		return entityManager.find(Consomateur.class, id);
	}

	public List<Consomateur> findAll() {
		return entityManager.createQuery("SELECT c from Consomateur c")
				.getResultList();
	}
}
