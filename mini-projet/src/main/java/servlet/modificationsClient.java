/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DAO;
import model.DataSourceFactory;

/**
 *
 * @author pedago
 */
@WebServlet(name = "modificationsClient", urlPatterns = {"/modifClient"})
public class modificationsClient extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        DAO dao = new DAO(DataSourceFactory.getDataSource());
	String message;
        
        // récupération et vérification de toutes les informations sur le client
        String Code = request.getParameter("clientId");
        if(Code.equals("")){
            Code = "NULL";
        }
        
        String Societe = request.getParameter("societe");
        if(Societe.equals("")){
            Societe = "NULL";
        }
        
        String Contact = request.getParameter("contact");
        if(Contact.equals("")){
            Contact = "NULL";
        }
        
        String Fonction = request.getParameter("fonction");
        if(Fonction.equals("")){
            Fonction = "NULL";
        }
        
        String Adresse = request.getParameter("add");
        if(Adresse.equals("")){
            Adresse = "NULL";
        }
        
        String Ville = request.getParameter("ville");
        if(Ville.equals("")){
            Ville = "NULL";
        }
        
        String Region = request.getParameter("region");
        if(Region.equals("")){
            Region = "NULL";
        }
        
        String Code_postal = request.getParameter("cp");
        if(Code_postal.equals("")){
            Code_postal = "NULL";
        }
        
        String Pays = request.getParameter("pays");
        if(Pays.equals("")){
            Pays = "NULL";
        }
        
        String Telephone = request.getParameter("tel");
        if(Telephone.equals("")){
            Telephone = "NULL";
        }
        
        String Fax = request.getParameter("fax");
        if(Fax.equals("")){
            Fax = "NULL";
        }
        
        
	try {
            dao.modifClient(Code,Societe, Contact, Fonction, Adresse, 
                    Ville, Region, Code_postal, Pays, Telephone, Fax);
            message = String.format("informations du cliet %s modifiées", Code);
            
	} catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message = ex.getMessage();
	}
		
	Properties resultat = new Properties();
	resultat.put("message", message);

	try (PrintWriter out = response.getWriter()) {
            // On spécifie que la servlet va générer du JSON
            response.setContentType("application/json;charset=UTF-8");
            // Générer du JSON
            Gson gson = new Gson();
            out.println(gson.toJson(resultat));
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
