<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form action="addAgriculteur">Inscrire un agriculteur nom<input type="text" name="nom">mail<input type="text" name="mail"><input type="submit"></form>
<form action="addConsommateur">Inscrire un consommateur nom<input type="text" name="nom">mail<input type="text" name="mail"><input type="submit"></form>
<form action="addLegume">Ajouter un legume dans les légumes "vendables"<input type="text" name="nom"><input type="submit"></form>

<form action="addLegumeEnVente">
Qui met en vente :<select name="agriculteurId">
	<c:forEach items="${requestScope['listeDAgriculteur']}" var="agriculteur">
		<option id="${agriculteur.db_identifier}" value="${agriculteur.db_identifier}"><c:out value="${agriculteur.nom}"/></option>
	</c:forEach>
</select>
Oui mais quoi en vente : <select name="legumeId">
	<option id="65454" value="65465">LEGUME INEXISTANT</option>
	<c:forEach items="${requestScope['listeDeLegume']}" var="legume">
		<option id="${legume.id}" value="${legume.id}">${legume.nom}</option>
	</c:forEach>
</select>
Oui mais à quel prix : <input type="text" name="prix"/>
<input type="submit"></form>
		
		