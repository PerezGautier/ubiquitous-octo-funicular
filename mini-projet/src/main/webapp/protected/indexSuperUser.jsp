<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    La servlet fait : session.setAttribute("customer", customer)
    La JSP récupère cette valeur dans ${customer}
--%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Vous êtes connexté en tant que Client</title>
	</head>
	<body>
		<h1>Bienvenue ${userName}</h1>
                <a href="index.html">index</a>
		<form action="<c:url value="/"/>" method="POST"> 
			<input type='submit' name='action' value='logout'>
		</form>
		<hr/>
		<h3>Il y a actuellement ${applicationScope.numberConnected} utilisateurs connectés</h3>
	</body>
</html>
