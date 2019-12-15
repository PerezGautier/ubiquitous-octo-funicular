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
                    url: "modificationsClient",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: $("#modifInfos").serialize(),
                    dataType: "json",
                    success: // La fonction qui traite les résultats
                            function (result) {
                                var templ = $('#resultTemplate').html();
                                var processedTemplate = Mustache.to_html(templ, result);
                                $('#res').html(processedTemplate);
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
                <a href="historiqueClient.jsp">Historique</a>
                <a class="active" href="infoClient.jsp">${userName}</a>
                <form action="<c:url value="LoginController"/>" method="POST"> 
                    <input type='submit' name='action' value='logout'>
                </form>  
            </div>
        </div>        
        
        <div id="res"></div>
        <script id="resultTemplate" type="text/template">
            {{#message}}
                <h1>{{.}}</h1>
            {{/message}}
        </script>    
         <!-- La zone où les résultats vont s'afficher -->
        <div id="codes"></div>
        
        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
            <div id="container">
                <form id="modifInfos" onsubmit="event.preventDefault(); modifierInfos();">
                    {{! Pour chaque enregistrement }}
                    {{#infoClient}}
                        {{! Une ligne dans la table }}

                        <h1>Informations</h1>
                        <label><b>Identifiant</b></label>
                        <input name="clientId" type="text" value="{{Code}}"/>

                        <label><b>Societe</b></label>
                        <td><input name="societe" type="text" value="{{Societe}}"/>

                        <label><b>Contact</b></label>
                        <input name="contact" type="text" value="{{Contact}}"/>

                        <label><b>Fonction</b></label>
                        <input name="fonction" type="text" value="{{Fonction}}"/>

                        <label><b>Adresse</b></label>
                        <input name="add" type="text" value="{{Adresse}}"/>

                        <label><b>Ville</b></label>
                        <input name="ville" type="text" value="{{Ville}}"/>

                        <label><b>Region</b></label>
                        <input name="region" type="text" value="{{Region}}"/>

                        <label><b>Code Postal</b></label>
                        <input name="cp" type="text" value="{{Code_postal}}"/>

                        <label><b>Pays</b></label>
                        <input name="pays" type="text" value="{{Pays}}"/>

                        <label><b>Telephone</b></label>
                        <input name="tel" type="text" value="{{Telephone}}"/>

                        <label><b>FAX</b></label>
                        <input name="fax" type="text" value="{{Fax}}"/>
                    {{/infoClient}}
                    </TABLE>
                    <input type="submit" value ="MODIFIER">
                </form>
            </div>
        </script>
    </body>
</html>
