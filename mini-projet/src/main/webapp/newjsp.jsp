<%-- 
    Document   : newjsp
    Created on : 12 déc. 2019, 22:29:02
    Author     : Gauti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/config.css" media="screen" type="text/css" />
        <title>JSP Page</title>
    </head>
    <body>
        <div id="container">
                <!-- zone d'inscription -->

                <form action="<c:url value="LoginController" />" method="POST"> <!-- l'action par défaut est l'URL courant, qui va rappeler la servlet -->
                    <h1>Inscription</h1>
                    <div style="color:red">${errorMessage}</div>
                    
                    <label><b>Mot de passe</b></label>
                    <input type="password" name="passwordParam" required>
                    
                    <label><b>Société</b></label>
                    <input type="text" name="societeParam" required>

                    <label><b>Contact</b></label>
                    <input type="password" name="contactParam" required>
                    
                    <label><b>Fonction</b></label>
                    <input type="password" name="fonctionParam" required>
                    
                    <label><b>Adresse</b></label>
                    <input type="password" name="adresseParam" required>
                    
                    <label><b>Ville</b></label>
                    <input type="password" name="villeParam" required>
                    
                    <label><b>Région</b></label>
                    <input type="password" name="regionParam" required>
                    
                    <label><b>Code Postal</b></label>
                    <input type="password" name="cpParam" required>
                    
                    <label><b>Pays</b></label>
                    <input type="password" name="paysParam" required>
                    
                    <label><b>Téléphone</b></label>
                    <input type="password" name="telParam" required>
                   
                    <label><b>FAX</b></label>
                    <input type="password" name="faxParam" required>                 
                    
                    <input type="submit" name='action' id='submit' value='subscribe' >

                </form>
            </div>
    </body>
</html>
