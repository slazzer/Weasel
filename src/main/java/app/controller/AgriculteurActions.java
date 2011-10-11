package app.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import test.CommandBusAccessor;
import app.command.AjouterAgriculteur;
import app.command.AjouterConsomateur;
import app.command.AjouterLegume;
import app.command.MettreEnVente;
import app.query.LightAgricultorAccessor;
import app.query.LightLegumeAccessor;
import app.query.beans.AgriculteurComplexeView;
import app.query.beans.AgriculteurSimpleView;
import app.query.beans.LegumeBean;

public class AgriculteurActions extends HttpServlet{

	WebApplicationContext context = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
if(context==null){
	context = WebApplicationContextUtils
	.getWebApplicationContext(req.getSession().getServletContext());
}
		// Recherche du flux d'ecriture   
		ServletOutputStream out = resp.getOutputStream() ;
		resp.setContentType("text/html");

		// Ecriture de la page reponse   
		out.println("<HTML><HEAD>") ;
		out.println("<TITLE>hello_2.java</TITLE>") ;
		out.println("</HEAD>") ;
		out.println("<BODY>") ;

		out.println("<H3>Veggie World...</H3>") ;
		String requested = req.getRequestURI().replace("/ddd/", "");

		
		Method[] methods = this.getClass().getDeclaredMethods();
		for(Method m:methods){
			if(requested.equals(m.getName())){
				try {
					m.invoke(this, req,resp);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(!	"showDetail".equals(requested)){
		out.println("<a href=\"listAgriculteur\" >refresh</a>");
		out.println("Ajouter un agriculteur <form action=\"addAgriculteur\"><input type=\"text\" name=\"nom\"><input type=\"text\" name=\"mail\"><input type=\"submit\"></form>");
		out.println("Ajouter un consommateur <form action=\"addConsommateur\"><input type=\"text\" name=\"nom\"><input type=\"text\" name=\"mail\"><input type=\"submit\"></form>");
		out.println("Ajouter un legume <form action=\"addLegume\"><input type=\"text\" name=\"nom\"><input type=\"submit\"></form>");
		preparerPourmettreEnvente(out);
		}
		out.println("</BODY></HTML>") ;
		out.close();
	}
	


	private void listAgriculteur(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream() ;
		LightAgricultorAccessor accessor= (LightAgricultorAccessor) context.getBean("lightAgricultorAccessor");
		List<AgriculteurSimpleView> liste = accessor.findAll();
		out.print("<ul>");
		for(AgriculteurSimpleView bean : liste){
			out.print("<li>"+bean.getNom()+"<a href=\"showDetail?id="+bean.getDb_identifier()+"\" target=\"_blank\">[+]</a></li>");
		}
		out.print("</ul>");
	}
	
	private void showDetail(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream() ;
		LightAgricultorAccessor accessor= (LightAgricultorAccessor) context.getBean("lightAgricultorAccessor");
		AgriculteurComplexeView detail = accessor.findDetail(req.getParameter("id"));
		out.print(detail.getNom()+" a à la vente "+detail.getListeDeLegumeALaVente());
		
	}

	private void addAgriculteur(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream() ;
		String nom = req.getParameter("nom");
		String mail = req.getParameter("mail");
		AjouterAgriculteur command= new AjouterAgriculteur();
		command.name=nom;
		command.email=mail;
		CommandBusAccessor.bus().dispatch(command);
		out.print("<h2>c'est bon c'est pris en compte</h2>");
		listAgriculteur(req, resp);
	}
	private void addConsommateur(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream() ;
		String nom = req.getParameter("nom");
		String mail = req.getParameter("mail");
		AjouterConsomateur command= new AjouterConsomateur();
		command.name=nom;
		command.email=mail;
		CommandBusAccessor.bus().dispatch(command);
		out.print("<h2>c'est bon c'est pris en compte j'ai Ajouté un consomateur</h2>");
		listAgriculteur(req, resp);
	}
	
	private void addLegume(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream() ;
		String nom = req.getParameter("nom");
		AjouterLegume command= new AjouterLegume();
		command.nom=nom;
		CommandBusAccessor.bus().dispatch(command);
		out.print("<h2>c'est bon c'est pris en compte</h2>");
		listAgriculteur(req, resp);
	}
	
	
	private void preparerPourmettreEnvente(ServletOutputStream out) throws IOException {
		LightAgricultorAccessor agriculteurAccessor= (LightAgricultorAccessor) context.getBean("lightAgricultorAccessor");
		List<AgriculteurSimpleView> liste = agriculteurAccessor.findAll();
		out.print("<form action=\"addLegumeEnVente\">");
		out.print("Qui met en vente :<select name=\"agriculteurId\">");
		for(AgriculteurSimpleView bean : liste){
			out.print("<option id=\""+bean.getDb_identifier()+"\" value=\""+bean.getDb_identifier()+"\">"+bean.getNom()+"</option>");
		}
		out.print("</select>");
		out.print("Oui mais quoi en vente : <select name=\"legumeId\">");
		LightLegumeAccessor legumeAccessor = (LightLegumeAccessor) context.getBean("lightLegumeAccessor");
		List<LegumeBean> list = legumeAccessor.findAll();
		for(LegumeBean legume:list){
			out.print("<option id=\""+legume.getId()+"\" value=\""+legume.getId()+"\">"+legume.getNom()+"</option>");
		}
		out.print("</select>");
		out.print("Oui mais à quel prix : <input type=\"text\" name=\"prix\"/>");
		out.print("<input type=\"submit\"></form>");
	}
	private void addLegumeEnVente(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		ServletOutputStream out = resp.getOutputStream() ;
		String agriId = req.getParameter("agriculteurId");
		String legId = req.getParameter("legumeId");
		String prix = req.getParameter("prix");
		MettreEnVente commEnVente = new MettreEnVente();
		commEnVente.agriculteurId=Long.parseLong(agriId);
		commEnVente.LegumeId=Long.parseLong(legId);
		commEnVente.prix=prix;
		CommandBusAccessor.bus().dispatch(commEnVente);
		out.print("<h2>c'est bon c'est pris en compte je l'ai mis en vente et sur la console on voit les mails qui défilent</h2>");
		listAgriculteur(req, resp);
	}
	
}
