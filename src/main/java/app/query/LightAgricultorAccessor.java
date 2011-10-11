package app.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.infrastructure.AgiculteurLightRepository;
import app.query.beans.AgriculteurComplexeView;
import app.query.beans.AgriculteurSimpleView;

@Service("lightAgricultorAccessor")
public class LightAgricultorAccessor {
	 @Autowired
	private AgiculteurLightRepository lightrepo;
	 
	 public List<AgriculteurSimpleView>findAll(){
		 return lightrepo.findAll();
	 }

	public AgriculteurComplexeView findDetail(String parameter) {
		return lightrepo.findComplexById(Long.parseLong(parameter));
	}
}
