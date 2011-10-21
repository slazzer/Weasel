package app.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.infrastructure.LegumeDAO;
import app.query.beans.LegumeBean;

@Service("lightLegumeAccessor")
public class LightLegumeQueryHandler {
	@Autowired
	private LegumeBeanFactory factory;

	@Autowired
	private LegumeDAO legumeDAO;

	public List<LegumeBean> findAll() {
		return factory.convert(legumeDAO.findAll());
	}

}
