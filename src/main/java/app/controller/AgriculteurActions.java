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

import app.command.AjouterConsomateur;
import app.command.AjouterLegume;
import app.command.InscrireAgriculteur;
import app.command.MettreEnVenteLegume;
import app.domain.specification.LegumeDejaEnVente;
import app.domain.specification.PrixInvalide;
import app.infrastructure.bus.CommandBus;
import app.query.service.LightAgriculteurQueryService;
import app.query.service.LightLegumeQueryService;
import app.query.view.AgriculteurComplexeView;
import app.query.view.AgriculteurSimpleView;
import app.query.view.LegumeView;

public class AgriculteurActions extends HttpServlet {

	private static final long serialVersionUID = -8472175542644820655L;

	WebApplicationContext context = null;

	private void listAgriculteurs(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		LightAgriculteurQueryService agriculteurQueryService = (LightAgriculteurQueryService) context
				.getBean(LightAgriculteurQueryService.class);
		List<AgriculteurSimpleView> agriculteurSimpleViews = agriculteurQueryService
				.findAll();
		req.setAttribute("listeDAgriculteur", agriculteurSimpleViews);

		LightLegumeQueryService legumeQueryService = (LightLegumeQueryService) context
				.getBean(LightLegumeQueryService.class);
		List<LegumeView> legumesViews = legumeQueryService.findAll();
		req.setAttribute("listeDeLegume", legumesViews);

		forwardToJsp(req, resp, "listeAgriculteur.jsp");
	}

	@SuppressWarnings("unused")
	private void showDetail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LightAgriculteurQueryService queryService = (LightAgriculteurQueryService) context
				.getBean(LightAgriculteurQueryService.class);
		AgriculteurComplexeView detail = queryService.findDetail(req
				.getParameter("id"));
		req.setAttribute("detailAgriculteur", detail);

		forwardToJsp(req, resp, "detailAgriculteur.jsp");
	}

	@SuppressWarnings("unused")
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
		listAgriculteurs(req, resp);
	}

	@SuppressWarnings("unused")
	private void addConsommateur(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String nom = req.getParameter("nom");
		String mail = req.getParameter("mail");

		AjouterConsomateur command = new AjouterConsomateur(nom, mail);
		CommandBus.bus().dispatch(command);

		req.setAttribute("message",
				"C'est bon j'ai pris en compte l'ajout du consommateur " + nom
						+ " a l'adresse " + mail);

		listAgriculteurs(req, resp);
	}

	@SuppressWarnings("unused")
	private void addLegume(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nom = req.getParameter("nom");

		AjouterLegume command = new AjouterLegume(nom);
		CommandBus.bus().dispatch(command);

		req.setAttribute("message", "C'est bon j'ai pris en compte l'ajout de "
				+ nom);

		listAgriculteurs(req, resp);
	}

	@SuppressWarnings("unused")
	private void addLegumeEnVente(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		Long agriculteurId = Long.parseLong(req.getParameter("agriculteurId"));
		Long legumeId = Long.parseLong(req.getParameter("legumeId"));
		String prix = req.getParameter("prix");

		MettreEnVenteLegume command = new MettreEnVenteLegume(agriculteurId,
				legumeId, prix);
		CommandBus.bus().dispatch(command);

		req.setAttribute(
				"message",
				"C'est bon c'est pris en compte je l'ai mis en vente et sur la console on voit les mails qui defillent");
		listAgriculteurs(req, resp);
	}

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
					} else if (e.getCause() != null
							&& e.getCause().getCause() != null
							&& e.getCause().getCause().getCause() instanceof LegumeDejaEnVente) {
						req.setAttribute("erreur",
								"Le legume est deja en vente !");
					} else {
						e.printStackTrace();
						req.setAttribute("erreur",
								"Une erreur est survenue !!! et je ne sais pas en dire plus");
					}
					listAgriculteurs(req, resp);
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
