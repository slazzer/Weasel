package app.query.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.infrastructure.dao.AgiculteurReadDAO;
import app.query.view.AgriculteurComplexeView;
import app.query.view.AgriculteurSimpleView;

@Service
public class LightAgriculteurQueryService {
	@Autowired
	private AgiculteurReadDAO dao;

	public List<AgriculteurSimpleView> findAll() {
		return dao.findAll();
	}

	public AgriculteurComplexeView findDetail(String parameter) {
		return dao.findComplexById(Long.parseLong(parameter));
	}
}
