package app.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.infrastructure.dao.LegumeDAO;
import app.query.view.LegumeView;

@Service
public class LightLegumeQueryService {

	@Autowired
	private LegumeViewFactory factory;

	@Autowired
	private LegumeDAO legumeDAO;

	public List<LegumeView> findAll() {
		return factory.convert(legumeDAO.findAll());
	}

}
