package app.infrastructure.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import app.domain.Legume;

@Repository
public class LegumeDAO {
	@PersistenceContext
	private EntityManager entityManager;

	public void add(Legume legume) {
		entityManager.persist(legume);
		entityManager.flush();
	}

	public Legume findById(Long id) {
		return entityManager.find(Legume.class, id);
	}

	public List<Legume> findAll() {
		return entityManager.createQuery("SELECT l from Legume l")
				.getResultList();
	}
}
