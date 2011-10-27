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
.info {
    font: 1em "Arial Narrow", Helvetica, Arial, Geneva, sans-serif;
    text-align: left;
    color: gray;
}

</style>
</HEAD>
<BODY>
<H3 class="titre">L'agriculteur ${detailAgriculteur.nom}</H3>
<div class="info">
<c:choose>
<c:when test="${detailAgriculteur.legumesALaVente ==null}">
Ne vend aucun legume
</c:when>
<c:otherwise>
Vend les légumes : ${detailAgriculteur.legumesALaVente}
 </c:otherwise>		
</c:choose>
</div>
</BODY></HTML>