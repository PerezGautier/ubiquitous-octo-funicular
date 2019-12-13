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

import model.DAOProduit;
import model.DAOCategorie;

public class DAO {
    private final DataSource myDataSource;

    /**
     * Construit le DAO avec sa source de données
     * @param dataSource la source de données à utiliser
     */
    public DAO(DataSource dataSource) {
            this.myDataSource = dataSource;
    }
    
    public int ajoutLigne(int idCmd, int ref, int qtt)throws SQLException {
        int result = 0;
        String sql = "INSERT INTO Ligne(Commande, Produit, Quantite) VALUES (?,?,?)";
      
        try (Connection myConnectionMAJ = myDataSource.getConnection();
            PreparedStatement stmtMAJ = myConnectionMAJ.prepareStatement(sql)) {
            // mise à jour dunb d'unités commandées
            myConnectionMAJ.setAutoCommit(false);
            stmtMAJ.setInt(1, idCmd);
            stmtMAJ.setInt(2, ref);
            stmtMAJ.setInt(3, qtt);
            
            result = stmtMAJ.executeUpdate();
        }
        return result;
    }
    
    
    public int[] uneLigne(int idCmd, int ref)throws SQLException {
        int result[]= new int [3]; //PAS NULL ?
        String sql = "SELECT * FROM Ligne WHERE Commande = ? AND Produit=?";
        try (Connection connection = myDataSource.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCmd);
            stmt.setInt(2, ref);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int commande = rs.getInt("Commande");
                int produit = rs.getInt("Produit");
                int quantite = rs.getInt("Quantite");
                result[0]=commande;
                result[1]=produit;
                result[2]=quantite;
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
    public int ajoutCommande(int[] tabIdProds, int[] tabQttsProd, String client, Date dateSaisie, Date dateEnvoi, float port, String dest, String add, String ville, String region, String cp, String pays, float remise) throws SQLException {
        int result = 0;
        String sql = "INSERT INTO Commande(Client, Saisie_le, Envoyee_le, Port, Destinataire, Adresse_livraison, Ville_livraison,"
                + " Region_livraison, Code_Postal_livrais, Pays_Livraison, Remise) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
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
                int cleID = generatedKeys.getInt("Numero");
                Logger.getLogger("DAO").log(Level.INFO, "Nouvelle clé générée pour INVOICE : {0}", cleID);
                
                //Ajout ligne(s)et modif qtt pr chaque produit
                for(int i=0; i<=tabIdProds.length;i++){
                    ajoutLigne(cleID, tabIdProds[i], tabQttsProd[i]);
                    DAOProduit.modifQttProd(tabIdProds[i], tabQttsProd[i],false);
                }
                /**MODIF DU NB D UNITES COMMANDEES DS PRODUIT*/
                //récuperer les id du produit
                //List<Integer> idProd = donneIdsProd(cleID);
                                    
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
        return result;
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
    
    public boolean isClient(String code, String contact) throws SQLException {

        String sql = "SELECT Contact FROM CLIENT WHERE Code=? and Contact=?";
        List<String> result = new LinkedList<>();

	try (Connection connection = myDataSource.getConnection(); 
		PreparedStatement stmt = connection.prepareStatement(sql)) {
		stmt.setString(1, code);
                stmt.setString(2, contact);
                ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			String cont = rs.getString("CONTACT");
			result.add(cont);
		}
	} catch(Exception ex) { // Une erreur s'est produite
            Logger.getLogger("DAO").log(Level.SEVERE, "Transaction en erreur", ex);
        }
	return !result.isEmpty();
    }
    
    public ClientEntity unClient(String codeClient) throws SQLException {
        ClientEntity result = null;
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

                            result = new ClientEntity(clientId, societe, contact, fonction, add, ville, region, cp, pays, tel, fax);
                            
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
        
        public CommandeEntity uneCommande(int numero) throws SQLException{
        CommandeEntity result = null;
        
        String sql = "select * from Commande where Numero=?";
        
        try(Connection connection = myDataSource.getConnection(); 
		 PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int num = rs.getInt("Numero");
                String client = rs.getString("Client");
                Date saisie = rs.getDate("Saisie_le");
                Date envoi = rs.getDate("Envoyee_le");
                float port = rs.getFloat("Port");
                String dest = rs.getString("Destinataire");
                String add = rs.getString("Adresse_livraison");
                String ville = rs.getString("Ville_livraison");
                String region = rs.getString("Region_livraison");
                String cp = rs.getString("Code_Postal_livrais");
                String pays = rs.getString("Pays_Livraison");
                float remise = rs.getFloat("Remise");
                result = new CommandeEntity(num,client,saisie,envoi,port,dest,add,ville,region,cp,pays,remise);
            } 
        }
        return result;
    }
}