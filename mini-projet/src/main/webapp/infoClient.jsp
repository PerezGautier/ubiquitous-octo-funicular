<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>Info du client</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- On charge jQuery -->
        <script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <!-- On charge le moteur de template mustache https://mustache.github.io/ -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <link rel="stylesheet" href="css/config.css" media="screen" type="text/css" />
        <link rel="stylesheet" href="css/header.css" media="screen" type="text/css" />
        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showInfo();
                    }
            );

            function showInfo() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "showAllInfoClient",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: 'code='+'${codeName}',
                    //data: $("#codeForm").serialize(),
                    dataType: "json",
                    error: showError,
                    success: // La fonction qui traite les résultats
                            function (result) {
                                // Le code source du template est dans la page
                                var template = $('#codesTemplate').html();
                                // On combine le template avec le résultat de la requête
                                var processedTemplate = Mustache.to_html(template, result);
                                // On affiche la liste des options dans le select
                                $('#codes').html(processedTemplate);
                            }
                });
            }
            
            
            
            function modifierInfos() {
                // On fait un appel AJAX pour modifier les informations et les 
                // récupérer par la suite
                $.ajax({
                    url: "modifClient",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: $("#modifInfos").serialize(),
                    dataType: "json",
                    success: // La fonction qui traite les résultats
                            function (result) {
                                showInfo();
                                console.log(result);
                            },
                    error: showError
                });
                return false;
            }
            
            
            // Fonction qui traite les erreurs de la requête
            function showError(xhr, status, message) {
                alert(JSON.parse(xhr.responseText).message);
            }

        </script>
    </head>
    <body>
        <div class="header">
            <a href="indexClient.jsp" class="logo">CompanyLogo</a>
            <div class="header-right">   
                <a class="active" href="infoClient.jsp">${userName}</a>
                <form action="<c:url value="LoginController"/>" method="POST"> 
                    <input type='submit' name='action' value='logout'>
                </form>  
            </div>
        </div>        
        
         <!-- La zone où les résultats vont s'afficher -->
        <div id="codes"></div>
        
        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
            <form id="modifInfos" onsubmit="modifierInfos();">
                <TABLE>
                {{! Pour chaque enregistrement }}
                {{#infoClient}}
                    {{! Une ligne dans la table }}
                    <TR>
                        <th>Identifiant</th>
                        <td><input id="clientId" type="text" value="{{Code}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Societe</th>
                        <td><input id="societe" type="text" value="{{Societe}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Addresse mail</th>
                        <td><input id="contact" type="text" value="{{Contact}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Fonction</th>
                        <td><input id="fonction" type="text" value="{{Fonction}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Adresse</th>
                        <td><input id="add" type="text" value="{{Adresse}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Ville</th>
                        <td><input id="ville" type="text" value="{{Ville}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Region</th>
                        <td><input id="region" type="text" value="{{Region}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Code Postal</th>
                        <td><input id="cp" type="text" value="{{Code_postal}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Pays</th>
                        <td><input id="pays" type="text" value="{{Pays}}"/></td>
                    </tr>
        
                    <tr>
                        <th>Telephone</th>
                        <td><input id="tel" type="text" value="{{Telephone}}"/></td>
                    </tr>
        
                    <tr>
                        <th>FAX</th>
                        <td><input id="fax" type="text" value="{{Fax}}"/></td>
                    </tr>
                {{/infoClient}}
                </TABLE>
                <input type="submit" value ="MODIFIER">
            </form>
        </script>
    </body>
</html>
