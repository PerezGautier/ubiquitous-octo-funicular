<%-- 
    Document   : ProduitsFromCategory
    Created on : 27 nov. 2019, 14:51:07
    Author     : pedago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%-- On on montre la liste des catégories --%>
            <div class="table">
		<div class="thead"><div class="td">categories</div></div>
                <div class="tbody">
                    <c:forEach var="record" items="${param}">
                        <form class="tr" method="get">
                            <div class="td"><input type="text" name="code" value="${record}" readonly/></div>
                        </form>	  		    
                    </c:forEach>  
                </div>
            </div>
    </body>
</html>
