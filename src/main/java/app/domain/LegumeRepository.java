package app.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.infrastructure.LegumeDAO;

@Service
public class LegumeRepository {

	@Autowired
	private LegumeDAO legumeDAO;

	public void add(Legume legume) {
		legumeDAO.add(legume);
	}

	public Legume getById(Long legumeId) {
		return legumeDAO.findById(legumeId);
	}
}
