package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.sql.DataSource;



public class DAO {
    private final DataSource myDataSource;

    /**
     * Construit le DAO avec sa source de données
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
            this.myDataSource = dataSource;
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
    
    /**
    * @return Liste de toutes les produits d'une catégorie donnée
    * @throws SQLException renvoyées par JDBC
    */
    public List<ProduitEntity> produitsDuneCategorie(int codeCategorie) throws SQLException {

	List<ProduitEntity> result = new LinkedList<>();

	String sql = "SELECT * FROM Produit WHERE Categorie = ?";
	try (Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
		stmt.setInt(1, codeCategorie);
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
	}
	return result;
    }

    
    

}
