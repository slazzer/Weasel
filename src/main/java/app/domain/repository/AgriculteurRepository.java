package app.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Agriculteur;
import app.infrastructure.dao.AgriculteurDAO;

@Service
public class AgriculteurRepository {

	@Autowired
	private AgriculteurDAO agriculteurDAO;

	public void add(Agriculteur agriculteur) {
		agriculteurDAO.add(agriculteur);
	}

	public Agriculteur getById(Long agriculteurId) {
		return agriculteurDAO.findById(agriculteurId);
	}
}
