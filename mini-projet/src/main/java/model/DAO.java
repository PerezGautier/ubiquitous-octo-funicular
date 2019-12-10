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
                    PreparedStatement stmtIdProd = myConnectionIdProd.prepareStatement(sqlIdProd)) {
                    
                    myConnectionIdProd.setAutoCommit(false);
                    stmtIdProd.setInt(1, cleID);
                    ResultSet rs = stmtIdProd.executeQuery();
                    resultIdProd = rs.getInt("Reference");
                    
                    /**MODIF DU NB D UNITES COMMANDEES DS PROSUIT*/
                    try (Connection myConnectionMAJ = myDataSource.getConnection();
                        PreparedStatement stmtMAJ = myConnectionMAJ.prepareStatement(sqlUnites)) {
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
     * @param Code
     * @param Societe
     * @param Contact
     * @param Fonction
     * @param Adresse
     * @param Ville
     * @param Region
     * @param Code_postal
     * @param Pays
     * @param Telephone
     * @param Fax
     * @return 
     * @throws SQLException renvoyées par JDBC
     */
    public int modifClient(String Code, String Societe, String Contact, String Fonction, String Adresse, String Ville,
            String Region, String Code_postal, String Pays, String Telephone, String Fax) throws SQLException {
		String sql = "UPDATE CLIENT SET Societe=?, Contact=?, Fonction=?, Adresse=?, Ville=?, Region=?, Code_postal=?, Pays=?, Telephone=?, Fax=?,  WHERE Code = ?";
		int result =0;
		try (Connection myConnection = myDataSource.getConnection();
                    PreparedStatement stmt = myConnection.prepareStatement(sql)) {

                    stmt.setString(1, Societe);
                    stmt.setString(2, Contact);
                    stmt.setString(3, Fonction);
                    stmt.setString(4, Adresse);
                    stmt.setString(5, Ville);
                    stmt.setString(6, Region);
                    stmt.setString(7, Code_postal);
                    stmt.setString(8, Pays);
                    stmt.setString(9, Telephone);
                    stmt.setString(10, Fax);
                    stmt.setString(11, Code);

                    result = stmt.executeUpdate();

                    myConnection.commit();
		}
        return result;
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
	
	public float caCategoriePeriode(String libCategorie, Date deb, Date fin) throws SQLException {
		float chiffreAffaires = 0;
		String sql = "SELECT SUM Prix_unitaire AS CA FROM Produit" +
                            "JOIN Ligne ON Reference=Produit JOIN Commande ON Commande=numero WHERE Libelle = ?" +
                            "WHERE Categorie = ? AND Saisie_le > ? AND Saisie_le < ?";
		try (Connection connection = myDataSource.getConnection(); 
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, libCategorie);
			stmt.setDate(2, deb);
			stmt.setDate(3, fin);
			ResultSet rs = stmt.executeQuery();
			chiffreAffaires = rs.getFloat("CA");
		}
		return chiffreAffaires;
		
	}
}