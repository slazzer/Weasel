package test;
import java.util.List;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.command.AjouterAgriculteur;
import app.command.AjouterConsomateur;
import app.command.AjouterLegume;
import app.command.MettreEnVente;
import app.infrastructure.AgiculteurLightRepository;
import app.query.LightLegumeAccessor;
import app.query.beans.AgriculteurSimpleView;
import app.query.beans.LegumeBean;



public class AppTest {
	public static void main(String []args) throws InterruptedException{

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

		AjouterAgriculteur command = new AjouterAgriculteur();
		command.name="TOTO:"+System.currentTimeMillis();
		command.email="email@email.com";
		CommandBusAccessor.bus().dispatch(command);
		AjouterLegume event = new AjouterLegume();
		event.nom="patate";
	//	CommandBusAccessor.bus().dispatch(event);
		AjouterConsomateur commandz = new AjouterConsomateur();
		commandz.name="Conso:"+System.currentTimeMillis();
		commandz.email="email@email.com";
		CommandBusAccessor.bus().dispatch(commandz);
		MettreEnVente commEnVente = new MettreEnVente();
		commEnVente.agriculteurId=Long.parseLong("22");
		commEnVente.LegumeId=Long.parseLong("1");
		commEnVente.prix="2";
		CommandBusAccessor.bus().dispatch(commEnVente);
		AgiculteurLightRepository repo = (AgiculteurLightRepository) context.getBean("agiculteurLightRepository");
		
		List<AgriculteurSimpleView> liste = repo.findAll();
		for(AgriculteurSimpleView elt: liste){
			System.out.println(elt.getDb_identifier()+"/"+elt.getNom());
		}
		Thread.sleep(2000);
		liste = repo.findAll();
		for(AgriculteurSimpleView elt: liste){
			System.out.println(elt.getDb_identifier()+"/"+elt.getNom());
		}
		
	}

		
	}
