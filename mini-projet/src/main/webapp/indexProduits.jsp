<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>Produits</title>
			<!-- Formatage d'une table par des CSS, inspiré de : https://stackoverflow.com/questions/5967564/form-inside-a-table -->
			<style>
				.table
				{
					display:table;
					border-collapse:separate;
					border-spacing:2px;
				}
				.thead
				{
					display:table-header-group;
					color:white;
					font-weight:bold;
					background-color:grey;
				}
				.tbody
				{
					display:table-row-group;
				}
				.tr
				{
					display:table-row;
				}
				.td
				{
					display:table-cell;
					border:1px solid black;
					padding:1px;
				}			
		</style>		
	</head>
	<body>
            
            <!-- Titre de la page -->
            <h3>Produits proposés : </h3>
                
            
            <%-- On montre la liste de sélection des categories --%>
            <form method="get">
                categorie de produits :
                <select id="cat">
                    <c:forEach var="record" items="${categoriesList}">
                        <option>${record}</option>	  		    
                    </c:forEach> 
                </select>
            </form>
            <br/><br/><br/><br/><br/>
            
            
            
            <%-- On montre les produits de la categorie  --%>
            <div class="table">
		<div class="thead"><div class="td">Produits :</div></div>
                <div class="tbody">
                    <c:forEach var="record" items="${produits}">
                        <form method="get">
                            <div class="tr">
                                <div class="td"><input type="text" name="nom" value="${record.produitId}" readonly/></div>
                                <div class="td"><input type="text" name="nom" value="${record.nom}" readonly/></div>
                                <div class="td"><input type="text" name="nom" value="${record.prix}" readonly/></div>
                            </div>
                        </form>	  		    
                    </c:forEach>  
                </div>
            </div>
            
            
            
            
            <%-- Partie d'identification du client --%>
            <br/><br/><br/>
            <form method="get">
                <table>
                    <tr>
                        <td>
                            Identifiant :
                        </td>
                        <td>
                            <input type="text" id="id" name="id"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Mot de Passe :          
                        </td>
                        <td>
                            <input type="password" id="mdp" name="mdp"/>
                        </td>
                    </tr>
                </table>
            </form>
            
            
            
	</body>        
</html>
