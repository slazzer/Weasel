package app.infrastructure.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import app.query.view.AgriculteurComplexeView;
import app.query.view.AgriculteurSimpleView;

@Repository
public class AgiculteurReadDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void add(AgriculteurSimpleView element) {
		entityManager.persist(element);
		entityManager.flush();
	}

	public List<AgriculteurSimpleView> findAll() {
		return entityManager.createQuery(
				"SELECT x FROM AgriculteurSimpleView x").getResultList();
	}

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
