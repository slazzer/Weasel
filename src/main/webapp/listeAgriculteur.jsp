<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<HTML><HEAD>
<TITLE>Domain Event Veggies</TITLE>
<style type="text/css">
.titre {
    font: 1.4em "Arial Narrow", Helvetica, Arial, Geneva, sans-serif;
    font-weight: bold;
    text-align: left;
    color: #9cf;
}

.erreur {
    font: 1.4em "Arial Narrow", Helvetica, Arial, Geneva, sans-serif;
    font-weight: bold;
    text-align: left;
    color: red;
}

.info {
    font: 1.4em "Arial Narrow", Helvetica, Arial, Geneva, sans-serif;
    font-weight: bold;
    text-align: left;
    color: green;
}

ul {
   font: 1em "Arial Narrow", Helvetica, Arial, Geneva, sans-serif;
   list-style: none;
}
ul a{
   font: 0.7em "Arial Narrow", Helvetica, Arial, Geneva, sans-serif;
}
</style>
</HEAD>
<BODY>
<H3>Veggie World...</H3><a href="listAgriculteur" >refresh</a>
<c:if test="${message != null}">
<h1 class="info">${message}</h1>
</c:if>
<c:if test="${erreur != null}">
<h1 class="erreur">${erreur}</h1>
</c:if>
<h2 class="titre">Les agriculteurs</h2>
<ul>
<c:forEach items="${requestScope['listeDAgriculteur']}" var="agriculteur">
<c:url var="detailUrl" value="showDetail">
<c:param name="id" value="${agriculteur.db_identifier}"></c:param>
</c:url>
	<li><c:out value="${agriculteur.nom}"/><a href="${detailUrl}" target="_blank"> [voir en détail] </a></li>
</c:forEach>
</ul>

<c:import url="lesActions.jsp"></c:import>
		

</BODY></HTML>