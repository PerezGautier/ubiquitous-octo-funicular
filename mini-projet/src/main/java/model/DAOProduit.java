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
public class DAOProduit {
    private final DataSource myDataSource;

    /**
     * Construit le DAO avec sa source de données
     * @param dataSource la source de données à utiliser
     */
    public DAOProduit(DataSource dataSource) {
            this.myDataSource = dataSource;
    }

    public ProduitEntity unProduit(int Reference) throws SQLException{
        ProduitEntity result = null;
        
        String sql = "select * from Produit where Reference=?";
        
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Reference);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int reference = rs.getInt("Reference");
                String nom = rs.getString("Nom");
                int fournisseur = rs.getInt("Fournisseur");
                int categorie = rs.getInt("Categorie");
                String quantite_par_unite = rs.getString("Quantite_par_unite");
                float prix_unitaire = rs.getFloat("Prix_unitaire");
                int unites_en_stock = rs.getInt("Unites_en_stock");
                int unites_commandees = rs.getInt("Unites_commandees");
                int niveau_de_reappro = rs.getInt("Niveau_de_reappro");
                int indisponible = rs.getInt("Indisponible");
                result = new ProduitEntity(reference,nom,fournisseur,categorie,quantite_par_unite,prix_unitaire,unites_en_stock,unites_commandees,niveau_de_reappro,indisponible);
            } 
        }
        return result;
    }
    
    
    public int ajoutProduit(int reference,String nom, int fournisseur, int categorie, String quantite_par_unite, float prix_unitaire, int unites_en_stock, int unites_commandees, int niveau_de_reappro, int indisponible) throws SQLException{
        int result = 0;
        String sql = "INSERT INTO Produit VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reference);
            stmt.setString(2, nom);
            stmt.setInt(3, fournisseur);
            stmt.setInt(4, categorie);
            stmt.setString(5, quantite_par_unite);
            stmt.setFloat(6, prix_unitaire);
            stmt.setInt(7, unites_en_stock);
            stmt.setInt(8, unites_commandees);
            stmt.setInt(9, niveau_de_reappro);
            stmt.setInt(10, indisponible);
            
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    public int supprimerProduit(int reference) throws SQLException{
        int result = 0;
        String sql = "delete from produit where reference=?";
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reference);
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    public int modifierProduit(int reference,String nom, int fournisseur, int categorie, String quantite_par_unite, float prix_unitaire, int unites_en_stock, int unites_commandees, int niveau_de_reappro, int indisponible) throws SQLException{
        int result = 0;
        String sql = "update produit set reference=?, nom=?,fournisseur=?, categorie=?, quantite_par_unite=?, prix_unitaire=?, unites_en_stock=?, unites_commandees=?, niveau_de_reappro=?, indisponible=? where reference=?";
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reference);
            stmt.setString(2, nom);
            stmt.setInt(3, fournisseur);
            stmt.setInt(4, categorie);
            stmt.setString(5, quantite_par_unite);
            stmt.setFloat(6, prix_unitaire);
            stmt.setInt(7, unites_en_stock);
            stmt.setInt(8, unites_commandees);
            stmt.setInt(9, niveau_de_reappro);
            stmt.setInt(10, indisponible);
            stmt.setInt(11, reference);
            
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    /**
    * @return Liste de toutes les produits d'une catégorie donnée
    * @throws SQLException renvoyées par JDBC
    */
    public List<ProduitEntity> produitsDuneCategorie(String libCategorie) throws SQLException {

	List<ProduitEntity> result = new LinkedList<>();

	String sql = "SELECT * FROM Produit JOIN Categorie ON Categorie=Code WHERE Libelle = ?";
	try (Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
		stmt.setString(1, libCategorie);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
                    int produitId = rs.getInt("Reference");
                    String nom = rs.getString("Nom");
                    String qtt = rs.getString("Quantite_par_unite");
                    float prix = rs.getFloat("Prix_unitaire");
                    int dispo = rs.getInt("Indisponible");
                    int fournisseur = rs.getInt("Fournisseur");
                    int qttStock = rs.getInt("Unites_en_stock");
                    int reapro = rs.getInt("Prix_unitaire");
                    int categorie = rs.getInt("Categorie");
                    int unitesCmdees = rs.getInt("Unites_commandees");
                    
                    ProduitEntity ligne = new ProduitEntity(produitId, nom, fournisseur, categorie, qtt, prix, qttStock, reapro, unitesCmdees, dispo);
                    result.add(ligne);
		}
	}
	return result;
    }

    /**
    * @return Liste de toutes les produits
    * @throws SQLException renvoyées par JDBC
    */
    public List<ProduitEntity> tousProduits() throws SQLException {

	List<ProduitEntity> result = new LinkedList<>();

	String sql = "SELECT * FROM Produit";
	try (Connection connection = myDataSource.getConnection(); 
            Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int produitId = rs.getInt("Reference");
                String nom = rs.getString("Nom");
                String qtt = rs.getString("Quantite_par_unite");
                float prix = rs.getFloat("Prix_unitaire");
                int dispo = rs.getInt("Indisponible");
                int fournisseur = rs.getInt("Fournisseur");
                int qttStock = rs.getInt("Unites_en_stock");
                int reapro = rs.getInt("Prix_unitaire");
                int categorie = rs.getInt("Categorie");
                int unitesCmdees = rs.getInt("Unites_commandees");

                ProduitEntity ligne = new ProduitEntity(produitId, nom, fournisseur, categorie, qtt, prix, qttStock, reapro, unitesCmdees, dispo);
                result.add(ligne);
            }
            connection.setAutoCommit(false);
	}
	return result;
    }
    
    //INUTILE ?
    public List<Integer> donneIdsProd(int num)throws SQLException {
        List<Integer> result= new LinkedList<>();
        String sqlIdProd = "SELECT Reference FROM PRODUIT JOIN LIGNE ON Reference=Produit"
                + "JOIN COMMANDE ON Commande=Numero WHERE Numero=?";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmtIdProd = connection.prepareStatement(sqlIdProd)) {
            stmtIdProd.setInt(1, num);
            ResultSet rs = stmtIdProd.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Reference");
                result.add(id);
            }
        }   
        return result;
    }
    
    public int donneQttProd(int ref)throws SQLException {
        int result= -1; //PAS NULL ?
        String sqlIdProd = "SELECT Unites_commandees FROM PRODUIT WHERE Reference=?";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmtIdProd = connection.prepareStatement(sqlIdProd)) {
            stmtIdProd.setInt(1, ref);
            ResultSet rs = stmtIdProd.executeQuery();
            while (rs.next()) {
                result = rs.getInt("Unites_commandees");
            }
        }   
        return result;
    }
    
    public int modifQttProd(int ref, int qtt, boolean autoCommit)throws SQLException {
        int result = 0;
        String sql = "UPDATE PRODUIT SET Unites_commandees = Unites_commandees+" +qtt+ " WHERE Reference=?";
      
        try (Connection myConnectionMAJ = myDataSource.getConnection();
            PreparedStatement stmtMAJ = myConnectionMAJ.prepareStatement(sql)) {
            // mise à jour dunb d'unités commandées
            if(autoCommit==false) {
                myConnectionMAJ.setAutoCommit(false); // pas pour les tests
            }
            stmtMAJ.setInt(1, ref);
            
            result = stmtMAJ.executeUpdate();
        }
        return result;
    }



}
