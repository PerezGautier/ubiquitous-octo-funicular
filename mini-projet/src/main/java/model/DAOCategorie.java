/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Gauti
 */
public class DAOCategorie {
    
    private final DataSource myDataSource;

    /**
     * Construit le DAO avec sa source de données
     * @param dataSource la source de données à utiliser
     */
    public DAOCategorie(DataSource dataSource) {
            this.myDataSource = dataSource;
    }


    public CategorieEntity uneCategorie(int codeCategorie) throws SQLException{
        CategorieEntity result = null;
        
        String sql = "select * from categorie where code=?";
        
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codeCategorie);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int code = rs.getInt("Code");
                String libelle = rs.getString("Libelle");
                String description = rs.getString("Description");
                result = new CategorieEntity(code,libelle,description);
            }
            
        }
        return result;
    }
    

    public int ajoutCategorie(int codeCategorie, String LibelleCategorie, String descriptionCategorie) throws SQLException{
        int result = 0;
        
        String sql = "INSERT INTO Categorie VALUES ( ?, ?, ?) ";
        
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codeCategorie);
            stmt.setString(2, LibelleCategorie);
            stmt.setString(3, descriptionCategorie);
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    public int supprimerCategorie(int codeCategorie) throws SQLException{
        int result = 0;
        
        String sql = "delete from categorie where Code=?";
        
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codeCategorie);
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    public int modifierCategorie(int codeCategorie, String LibelleCategorie, String descriptionCategorie) throws SQLException{
        int result = 0;
        
        String sql = "update categorie set Code = ?, Libelle=?, description=? where code = ?";
        
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codeCategorie);
            stmt.setString(2, LibelleCategorie);
            stmt.setString(3, descriptionCategorie);
            stmt.setInt(4, codeCategorie);
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    /**
     * @return Liste de toutes les catégories
     * @throws SQLException renvoyées par JDBC
     */
    public List<String> toutesCategories() throws SQLException {

	List<String> result = new LinkedList<>();

	String sql = "SELECT Libelle FROM Categorie";
	try (Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			String libelle = rs.getString("Libelle");
			result.add(libelle);
		}
	}
	return result;
    }
}
