package app.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.infrastructure.ConsomateurDAO;

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
