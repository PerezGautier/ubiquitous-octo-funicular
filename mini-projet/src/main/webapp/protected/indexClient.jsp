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
        <title>TODO supply a title</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/0.8.1/mustache.min.js"></script>
        <link rel="stylesheet" href="css/config.css" media="screen" type="text/css" />
        <link rel="stylesheet" href="css/header.css" media="screen" type="text/css" />

        <script>
            $(document).ready(// Exécuté à la fin du chargement de la page
                    function () {
                        // On montre la liste des codes
                        showCode();
                    }
            );

            function showCode() {
                // On fait un appel AJAX pour chercher les codes
                $.ajax({
                    url: "showAllProduct",
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
                                
                                var template2 = $('#categoriesTemplate').html();
                                var processedTemplate2 = Mustache.to_html(template2, result);
                                $('#categories').html(processedTemplate2);
                            }
                });
            }
            
            // Montre les produits d'une catégorie
            function selectCat() {
                $.ajax({
                    url: "showProductFromCategory",
                    // serialize() renvoie tous les paramètres saisis dans le formulaire
                    data: $("#codeForm").serialize(),
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
                <a class="active" href="protected/infoClient.jsp">${userName}</a>
                <form action="<c:url value="LoginController"/>" method="POST"> 
                    <input type='submit' name='action' value='logout'>
                </form>  
            </div>
        </div> 
        <div style="color:red">${errorMessage}</div>
        <div id="containerProduits">
        <!-- On montre le formulaire de saisie --> 
        <!-- Le template qui sert à afficher la liste des categories -->
        <script id="categoriesTemplate" type="text/template">
            <form id="codeForm" onsubmit="event.preventDefault(); selectCat();">
                <select name="cat">
                    {{#categoriesList}}
                        <option value="{{.}}">{{.}}</option>
                    {{/categoriesList}}
                </select>
                <input type="submit" value="Chercher">
            </form>
        </script>
        
        
        
        <!-- La zone où la combobox de liste des categories va s'afficher -->
        <div id="categories"></div>
        
        
         <!-- La zone où les résultats vont s'afficher -->
        <div id="codes"></div>
        
        <!-- Le template qui sert à formatter la liste des codes -->
        <script id="codesTemplate" type="text/template">
            <TABLE>
            <tr><th>Nom</th><th>Prix</th></tr>
            {{! Pour chaque enregistrement }}
            {{#records}}
                {{! Une ligne dans la table }}
                <TR>
                    <TD>{{nom}} </TD>
                    <TD>{{prix}} €</TD>
                    <TD><button onclick="addProduct('{{produitId}}')">Acheter</button></TD>
                </TR>
            {{/records}}
            </TABLE>
        </script>
        
        </div>
        
    </body>
</html>
