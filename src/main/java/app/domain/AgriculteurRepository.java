package app.domain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.infrastructure.AgriculteurDAO;

@Service
public class AgriculteurRepository {
	
	@Autowired
	private AgriculteurDAO agriculteurDAO; 
	
	public void add(Agriculteur agriculteur){
		agriculteurDAO.add(agriculteur);
	}

	public Agriculteur getById(Long agriculteurId) {
		return agriculteurDAO.findById(agriculteurId);
	}
}
