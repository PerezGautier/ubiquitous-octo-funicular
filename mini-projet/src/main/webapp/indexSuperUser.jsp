<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--
    La servlet fait : session.setAttribute("customer", customer)
    La JSP récupère cette valeur dans ${customer}
--%>

<html>
    <head>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <link rel="stylesheet" href="css/config.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="css/header.css" media="screen" type="text/css" />
    <title>Administrateur</title>
    <script type="text/javascript">
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);
        
        
        $(document).ready(// Exécuté à la fin du chargement de la page
            function () {
                // On met à jour les possibilités des formulaires
                showForms();
            }
        );
        
        
        
        // Fonction qui traite les erreurs de la requête
        function showError(xhr, status, message) {
            alert(JSON.parse(xhr.responseText).message);
        }



        // Montre les formulaires pour préciser les graphiques voulus
        function showForms() {
            $.ajax({
                url: "showInformationsAdmin",
                dataType: "json",
                error: showError,
                success: // La fonction qui traite les résultats
                    function (result) {
                        // on affiche dans un premier temps les categories
                        // Le code source du template est dans la page
                        var template = $('#categoriesTemplate').html();
                        // On combine le template avec le résultat de la requête
                        var processedTemplate = Mustache.to_html(template, result);
                        // On affiche la liste des options dans le select
                        $('#categories').html(processedTemplate);
                        
                        
                        //puis on affiche le formulaire sur les pays
                        // Le code source du template est dans la page
                        template = $('#paysTemplate').html();
                        // On combine le template avec le résultat de la requête
                        processedTemplate = Mustache.to_html(template, result);
                        // On affiche la liste des options dans le select
                        $('#pays').html(processedTemplate);
                        
                        
                        //Le formulaire sur les clients n'a pas besoin d'être
                        // chargé puisqu'il fonctionne avec les codes des 
                        // clients
                    }
            });
            return false;
        }
        
        
        
        
        // convertit en un tableau pouvant être exploité les données passées en
        // paramètre.
        // donnees : tableau de données dont les deux premières sont la légende :
        //           1 --> données évaluées
        //           2 --> type de valeurs (cf dans l'exemple :
        //                                  Year est l'unité de mesure
        //                                  Sales est ce qui est mesuré
        // titre : titre du graphique en fonction du graphique évalué
        // typeGraph : origine du graphe et l'endroit où il doit être déployé
        //             Il peut prendre les valeurs :
        //                   'graphique_produits'  --> le graphique est déterminé
        //                                             en fonction des produits
        //                   'graphique_Pays' --> en fonction du Pays
        //                   'graphique_clients' --> en fonction du client
        function drawChart(donnees, titre, typeGraph) {
            var data = google.visualization.arrayToDataTable(donnees);
                    /*[
                ['Year', 'Sales'],
                ['2004',  1000],
                ['2005',  1170],
                ['2006',  660],
                ['2007',  1030]
            ]);*/

            var options = {
                title: titre,
                curveType: 'function',
                legend: { position: 'bottom' }
            };

            var chart = new google.visualization.LineChart(document.getElementById(typeGraph));
            
            chart.draw(data, options);
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // Va chercher les informations pour le graphique basé sur les listes de 
        // categories de produits
        function graphiqueCat(){
            $.ajax({
                url: "showInformationsAdmin",
                data: $("#codeFormCat").serialize(),
                dataType: "json",
                error: showError,
                success: // La fonction qui traite les résultats
                    function(result){
                        // décomposer result en les trois résultats renvoyés
                        // ou le remplacer par données, titre et typeGraph
                        // ou utiliser un moyen dans du code plus loin pour ce 
                        // faire
                    }
            });
        }
        
        
        
        
        // Va chercher les informations pour le graphique basé sur les Pays
        function graphiquePays(){
            $.ajax({
                url: "showInformationsAdmin",
                data: $("#codeFormPays").serialize(),
                dataType: "json",
                error: showError,
                success: // La fonction qui traite les résultats
                    function(result){
                        // décomposer result en les trois résultats renvoyés
                        // ou le remplacer par données, titre et typeGraph
                        // ou utiliser un moyen dans du code plus loin pour ce 
                        // faire
                    }
            });
        }
        
        
        
        
        // Va chercher les informations pour le graphique basé sur les Pays
        function graphiqueClient(){
            $.ajax({
                url: "showInformationsAdmin",
                data: $("#codeFormPays").serialize(),
                dataType: "json",
                error: showError,
                success: // La fonction qui traite les résultats
                    function(result){
                        // décomposer result en les trois résultats renvoyés
                        // ou le remplacer par données, titre et typeGraph
                        // ou utiliser un moyen dans du code plus loin pour ce 
                        // faire
                    }
            });
        }
    </script>
    </head>
  
    <body>
        <!-- En-tete de la page -->
        <div class="header">
            <a href="indexClient.jsp" class="logo">CompanyLogo</a>
            <div class="header-right">   
                <a class="active" href="infoClient.jsp">${userName}</a>
                <form action="<c:url value="LoginController"/>" method="POST"> 
                    <input type='submit' name='action' value='logout'>
                </form>  
            </div>
        </div> 
                    
                    
        <!-- Corps de la page  TITRES-->      
        <div id="containerAdmin">
            <h1>Partie Administrateur :</h1>
            <br/><br/>
            <h2>
                Graphiques
            </h2>
            <br/><br/>
            
            
            <!-- Corps de la page  GRAPHIQUES -->   
            
            
            <!---------- Chiffre d'affaire par categorie d'articles ----------->
            <h3>Chiffre d'affaire par categorie d'articles : </h3>
            
            <!-- Script Categorie articles -->
            <script id="categoriesTemplate" type="text/template">
                <form id="codeFormCat" onsubmit="event.preventDefault(); graphiqueCat();">
                    <select name="cat">
                        {{#categoriesList}}
                            <option value="{{.}}">{{.}}</option>
                        {{/categoriesList}}
                    </select>  
                    début : <input type='date' name='dateDebut'/>  
                    fin : <input type='date' name='dateFin'/><br/>
                    <input type="submit" value="Chercher">
                </form>
            </script>

            <!-- Affichage du formulaire -->
            <div id="categories"></div>

            <!-- Affichage du graphique -->
            <div id="graphique_produits" style="width: 100%; height: 500px"></div>
            
            
            


            <!--------------- Chiffre d'affaire par pays ---------------------->
            <h3>Chiffre d'affaire par pays : </h3>

            <!-- Script de formulaire -->
            <script id="paysTemplate" type="text/template">
                <form id="codeFormPays" onsubmit="event.preventDefault(); graphiquePays();">
                    <select name="pays">
                        {{#paysList}}
                            <option value="{{.}}">{{.}}</option>
                        {{/paysList}}
                    </select>  
                    début : <input type='date' name='dateDebut'/>  
                    fin : <input type='date' name='dateFin'/><br/>
                    <input type="submit" value="Chercher">
                </form>
            </script>

            <!-- Affichage du formulaire -->
            <div id="pays"></div>

            <!-- Affichage du graphique -->
            <div id="graphique_Pays" style="width: 100%; height: 500px"></div>


            
            <!----------------- Chiffre d'affaire par client ------------------>
            <h3>Chiffre d'affaire par client : </h3>


            <!-- Script -->
            <form id="codeClientForm" onsubmit="event.preventDefault(); graphiqueClient();">
                Code du client : <input type='text' name='codeCli' /> 
                début : <input type='date' name='dateDebut'/>  
                fin : <input type='date' name='dateFin'/><br/>
                <input type="submit" value="Chercher">
            </form>

            <!-- Affichage du graphique -->
            <div id="graphique_clients" style="width: 100%; height: 500px"></div>
            
        </div>
    </body>
</html>
