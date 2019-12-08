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



public class DAO {
    private final DataSource myDataSource;

    /**
     * Construit le DAO avec sa source de données
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
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
        int result;
        
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
        int result;
        
        String sql = "delete from categorie where Code=?";
        
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, codeCategorie);
            result = stmt.executeUpdate();
        }
        return result;
    }
    
    public int modifierCategorie(int codeCategorie, String LibelleCategorie, String descriptionCategorie) throws SQLException{
        int result;
        
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
    
    /**
     * Ajoute une commande en créanr une clé automatiquement
     * @param client
     * @param dateSaisie
     * @param dateEnvoi
     * @param port
     * @param dest
     * @param add
     * @param ville
     * @param region
     * @param cp
     * @param pays
     * @param remise
     * @throws SQLException 
     */
    public void ajoutCommande(String client, Date dateSaisie, Date dateEnvoi, float port, String dest, String add, String ville, String region, String cp, String pays, float remise) throws SQLException {
        String sql = "INSERT INTO Commande(Client, Saisie_le, Envoyee_le, Port, Destinataire, Adresse_livraison, Ville_livraison,"
                + " Region_livraison, Code_Postal_livrais, Pays_Livraison, Remise) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String sqlIdProd = "SELECT Reference FROM PRODUIT JOIN LIGNE ON Reference=Produit"
                + "JOIN COMMANDE ON Commande=Numero WHERE Numero=?";
        String sqlUnites = "UPDATE PRODUIT SET Unites_commandees = Unites_commandees-1 WHERE Reference=?";
        
        try (Connection myConnection = myDataSource.getConnection();
            PreparedStatement stmt = myConnection.prepareStatement(sql)) {
            // On démarre la transaction
            myConnection.setAutoCommit(false);
            stmt.setString(1, client);
            stmt.setDate(2, dateSaisie);
            stmt.setDate(3, dateEnvoi);
            stmt.setFloat(4, port);
            stmt.setString(5, dest);
            stmt.setString(6, add);
            stmt.setString(7, ville);
            stmt.setString(8, region);
            stmt.setString(9, cp);
            stmt.setString(10, pays);
            stmt.setFloat(11, remise);
            
            try {
                stmt.executeUpdate();

                // On a bien créé la commande, cherchons son ID	
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                generatedKeys.next();
                int cleID = generatedKeys.getInt("ID");
                Logger.getLogger("DAO").log(Level.INFO, "Nouvelle clé générée pour INVOICE : {0}", cleID);
                
                
                /**MODIF DU NB D UNITES COMMANDEES DS PRODUIT*/
                //récuperer l'id du produit
                int resultIdProd = 0;
                try (Connection myConnectionIdProd = myDataSource.getConnection();
                    PreparedStatement stmtIdProd = myConnectionIdProd.prepareStatement(sql)) {
                    
                    myConnectionIdProd.setAutoCommit(false);
                    stmtIdProd.setInt(1, cleID);
                    ResultSet rs = stmtIdProd.executeQuery();
                    resultIdProd = rs.getInt("Reference");
                    
                    /**MODIF DU NB D UNITES COMMANDEES DS PROSUIT*/
                    try (Connection myConnectionMAJ = myDataSource.getConnection();
                        PreparedStatement stmtMAJ = myConnectionMAJ.prepareStatement(sql)) {
                        // mise à jour dunb d'unités commandées
                        myConnectionMAJ.setAutoCommit(false);
                        stmtMAJ.setInt(1, resultIdProd);
                        
                        stmtMAJ.executeUpdate();
                        
                    }
                }
                // Tout s'est bien passé, on peut valider la transaction
                myConnection.commit();
            } catch (Exception ex) { // Une erreur s'est produite
                    // On logge le message d'erreur
                    Logger.getLogger("DAO").log(Level.SEVERE, "Transaction en erreur", ex);
                    myConnection.rollback(); // On annule la transaction
                    throw(ex); // On relève l'exception pour l'appelant
            } finally {
                    myConnection.setAutoCommit(true); // On revient au mode de fonctionnement sans transaction
            }
        }
    }
    
    /**
     * @param codeClient
     * @param attributsModifies
     * @param valeursModifiees
     * @throws SQLException renvoyées par JDBC
     */
    public void modifClient(String codeClient, String[] attributsModifies, String[] valeursModifiees) throws SQLException {
	String sqlBase = "UPDATE CLIENT SET ";
        String sqlFin = " WHERE Code = ?";
        // Construction de la requete
        for (int i = 0; i <= attributsModifies.length; i++) {
            sqlBase += attributsModifies[i] + "=" + valeursModifiees[i];
            if(i!=attributsModifies.length) {
                sqlBase += ", ";
            }
        }    
        String sql = sqlBase + sqlFin;
	try (Connection myConnection = myDataSource.getConnection();
            PreparedStatement stmt = myConnection.prepareStatement(sql)) {
            
            stmt.setString(1, codeClient);

            int result = stmt.executeUpdate();
            
            myConnection.commit();
	}
        
    }
    
    public ClientEntity unClient(String codeClient) throws SQLException {
        ClientEntity result = new ClientEntity("","","","","","","","","","","");
	String sql = "SELECT * FROM Client WHERE Code = ?";
	try (Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
		stmt.setString(1, codeClient);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
                    String clientId = rs.getString("Code");
                    String societe = rs.getString("Societe");
                    String contact = rs.getString("Contact");
                    String fonction = rs.getString("Fonction");
                    String add = rs.getString("Adresse");
                    String ville = rs.getString("Ville");
                    String region = rs.getString("Region");
                    String cp = rs.getString("Code_postal");
                    String pays = rs.getString("Pays");
                    String tel = rs.getString("Telephone");
                    String fax = rs.getString("Fax");
                    
                    ClientEntity client = new ClientEntity(clientId, societe, contact, fonction, add, ville, region, cp, pays, tel, fax);
                    result = client;
		}
	}
	return result;
    }
}
