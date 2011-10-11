package app.infrastructure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import app.domain.Legume;

@Repository
@Transactional
public class LegumeDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void add(Legume legume) {
		entityManager.persist(legume);
		entityManager.flush();
	}

	public Legume findById(Long id) {
		return entityManager.find(Legume.class, id);
	}

	public List<Legume> findAll() {
		return entityManager.createQuery("SELECT l from Legume l").getResultList();
	}
}
