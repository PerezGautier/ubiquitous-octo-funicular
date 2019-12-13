<%-- 
    Document   : inscription
    Created on : 13 déc. 2019, 17:40:26
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
                <!-- zone de connexion -->

                <form action="<c:url value="inscriptionClient" />" method="POST"> <!-- l'action par défaut est l'URL courant, qui va rappeler la servlet -->
                    <h1>Connexion</h1>

                    <label><b>Nom d'utilisateur</b></label>
                    <input type="text" placeholder="contact" name="loginParam" required>

                    <label><b>Mot de passe</b></label>
                    <input type="text" placeholder="code" name="passwordParam" required>
                    
                    <label><b>Société</b></label>
                    <input type="text" placeholder="beercraft" name="societeParam" required>
                    
                    <label><b>Fonction</b></label>
                    <input type="text" placeholder="Directeur" name="fonctionParam" required>
                    
                    <label><b>Adresse</b></label>
                    <input type="text" placeholder="ici ou là" name="adresseParam" required>
                    
                    <label><b>Ville</b></label>
                    <input type="text" placeholder="Albi" name="villeParam" required>
                    
                    <label><b>Région</b></label>
                    <input type="text" placeholder="Occitanie" name="regionParam" required>
                    
                    <label><b>Code Postal</b></label>
                    <input type="text" placeholder="81000" name="codePostalParam" required>
                    
                    <label><b>Pays</b></label>
                    <input type="text" placeholder="France" name="paysParam" required>
                    
                    <label><b>Téléphone</b></label>
                    <input type="text" placeholder="98.89.74.47" name="telParam" required>
                    
                    <label><b>Fax</b></label>
                    <input type="text" placeholder="78.65.12.58" name="faxParam" required>

                    <input type="submit" name='action' id='submit' value='subscribe' >

                </form>
            </div>
    </body>
</html>
