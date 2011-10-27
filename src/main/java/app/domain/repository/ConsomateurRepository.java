package app.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Consomateur;
import app.infrastructure.dao.ConsomateurDAO;

@Service
public class ConsomateurRepository {

	@Autowired
	private ConsomateurDAO consomateurDAO;

	public void add(Consomateur consomateur) {
		consomateurDAO.add(consomateur);
	}

	public List<Consomateur> getAll() {
		return consomateurDAO.findAll();
	}
	
}
