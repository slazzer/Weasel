package app.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import app.command.InscrireAgriculteur;
import app.command.AjouterConsomateur;
import app.command.AjouterLegume;
import app.command.MettreEnVenteLegume;
import app.domain.specification.LegumeDejaEnVente;
import app.domain.specification.PrixInvalide;
import app.infrastructure.bus.CommandBus;
import app.query.LightAgriculteurQueryHandler;
import app.query.LightLegumeQueryHandler;
import app.query.beans.AgriculteurComplexeView;
import app.query.beans.AgriculteurSimpleView;
import app.query.beans.LegumeBean;

public class AgriculteurActions extends HttpServlet {

	private void listAgriculteur(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		LightAgriculteurQueryHandler agriculteurAccessor = (LightAgriculteurQueryHandler) context
				.getBean("lightAgricultorAccessor");
		List<AgriculteurSimpleView> liste = agriculteurAccessor.findAll();
		req.setAttribute("listeDAgriculteur", liste);
		LightLegumeQueryHandler legumeAccessor = (LightLegumeQueryHandler) context
				.getBean("lightLegumeAccessor");
		List<LegumeBean> list = legumeAccessor.findAll();
		req.setAttribute("listeDeLegume", list);
		forwardToJsp(req, resp, "listeAgriculteur.jsp");
	}

	private void showDetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LightAgriculteurQueryHandler accessor = (LightAgriculteurQueryHandler) context
				.getBean("lightAgricultorAccessor");
		AgriculteurComplexeView detail = accessor.findDetail(req
				.getParameter("id"));
		req.setAttribute("detailAgriculteur", detail);
		forwardToJsp(req, resp, "detailAgriculteur.jsp");

	}

	private void addAgriculteur(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nom = req.getParameter("nom");
		String mail = req.getParameter("mail");
		InscrireAgriculteur command = new InscrireAgriculteur();
		command.nom = nom;
		command.email = mail;
		CommandBus.bus().dispatch(command);
		req.setAttribute("message",
				"C'est bon j'ai pris en compte l'ajout de l'agriculteur " + nom
						+ " a l'adresse " + mail);
		listAgriculteur(req, resp);
	}

	private void addConsommateur(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String nom = req.getParameter("nom");
		String mail = req.getParameter("mail");
		AjouterConsomateur command = new AjouterConsomateur();
		command.nom = nom;
		command.email = mail;
		CommandBus.bus().dispatch(command);
		req.setAttribute("message",
				"C'est bon j'ai pris en compte l'ajout du consommateur " + nom
						+ " a l'adresse " + mail);
		listAgriculteur(req, resp);
	}

	private void addLegume(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nom = req.getParameter("nom");
		AjouterLegume command = new AjouterLegume();
		command.nom = nom;
		CommandBus.bus().dispatch(command);
		req.setAttribute("message", "C'est bon j'ai pris en compte l'ajout de "
				+ nom);
		listAgriculteur(req, resp);
	}

	private void addLegumeEnVente(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String agriId = req.getParameter("agriculteurId");
		String legId = req.getParameter("legumeId");
		String prix = req.getParameter("prix");
		MettreEnVenteLegume command = new MettreEnVenteLegume();
		command.agriculteurId = Long.parseLong(agriId);
		command.legumeId = Long.parseLong(legId);
		command.prix = prix;
		CommandBus.bus().dispatch(command);
		req.setAttribute(
				"message",
				"C'est bon c'est pris en compte je l'ai mis en vente et sur la console on voit les mails qui defillent");
		listAgriculteur(req, resp);
	}

	WebApplicationContext context = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (context == null) {
			context = WebApplicationContextUtils.getWebApplicationContext(req
					.getSession().getServletContext());
		}
		// Ecriture de la page reponse
		String requested = req.getRequestURI().replace("/ddd/action/", "");
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method m : methods) {
			if (requested.equals(m.getName())) {
				try {
					m.invoke(this, req, resp);
				} catch (Throwable e) {
					if (e.getCause() != null
							&& e.getCause().getCause() != null
							&& e.getCause().getCause().getCause() instanceof PrixInvalide) {
						req.setAttribute("erreur",
								"Le Prix de mise en vente est invalide !");
					}else if (e.getCause() != null
							&& e.getCause().getCause() != null
							&& e.getCause().getCause().getCause() instanceof LegumeDejaEnVente) {
						req.setAttribute("erreur","Le legume est deja en vente !");
					} else {
						e.printStackTrace();
						req.setAttribute("erreur",
								"Une erreur est survenue !!! et je ne sais pas en dire plus");
					}
					listAgriculteur(req, resp);
				}
			}
		}
	}

	private void forwardToJsp(HttpServletRequest req, HttpServletResponse resp,
			String jsp) {
		RequestDispatcher dispatcher = this.getServletContext()
				.getRequestDispatcher("/" + jsp);
		try {
			dispatcher.forward(req, resp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
