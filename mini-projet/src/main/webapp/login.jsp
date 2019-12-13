<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <!-- importer le fichier de style -->
                <link rel="stylesheet" href="css/config.css" media="screen" type="text/css" />
		<title>Please login</title>
	</head>
	<body>

            <div id="container">
                <!-- zone de connexion -->

                <form action="<c:url value="LoginController" />" method="POST"> <!-- l'action par défaut est l'URL courant, qui va rappeler la servlet -->
                    <h1>Connexion</h1>
                    <div style="color:red">${errorMessage}</div>
                    <label><b>Nom d'utilisateur</b></label>
                    <input type="text" placeholder="untel" name="loginParam" required>

                    <label><b>Mot de passe</b></label>
                    <input type="password" placeholder="ABCD" name="passwordParam" required>

                    <input type="submit" name='action' id='submit' value='login' >

                </form>
            </div>

	</body>
</html>
