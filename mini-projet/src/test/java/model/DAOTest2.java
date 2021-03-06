/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.sql.DataSource;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Gauti
 */
public class DAOTest2 {
    private DAO dao; // L'objet à tester
    private DataSource myDataSource; // La source de données à utiliser
    private static Connection myConnection ; // La connection à la BD de test
    
    
    //gautier
    private void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
        // On initialise la base avec le contenu d'un fichier de test
        String sqlFilePath = DAOTest2.class.getResource(filename).getFile();
        SqlFile sqlFile = new SqlFile(new File(sqlFilePath));

        sqlFile.setConnection(connexion);
        sqlFile.execute();
        sqlFile.closeReader();		
    }
    public static DataSource getTestDataSource() {
        org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
        ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }
    
    @Before
    public void setUp() throws SQLException, IOException, SqlToolError {
        // On utilise la base de données de test
        myDataSource = getTestDataSource();
        myConnection = myDataSource.getConnection();
        // On crée le schema de la base de test
        executeSQLScript(myConnection, "schema.sql");
        // On y met des données
        executeSQLScript(myConnection, "donnees.sql");		

        dao = new DAO(myDataSource);
    }

    @After
    public void tearDown() throws IOException, SqlToolError, SQLException {
        myConnection.close(); // La base de données de test est détruite ici
        dao = null; // Pas vraiment utile
    }
    
    @Test
    public void testCategorieExist() throws SQLException{
        CategorieEntity cat = dao.uneCategorie(1);
        assertNotNull("La categorie existe", cat);
        assertEquals(cat.getLibelle(),"Boissons");
    }
    
    @Test
    public void testCategorieNotExist() throws SQLException{
        CategorieEntity cat = dao.uneCategorie(12);
        assertNull("La catégorie n'existe pas encore",cat);
    }
    
    @Test
    public void testAjoutCategorie() throws SQLException{
        CategorieEntity cat = dao.uneCategorie(12);
        assertNull("La catégorie n'existe pas encore",cat);
        int result = dao.ajoutCategorie(12,"b","un beau b");
        assertEquals(result,1);
        CategorieEntity cat12 = dao.uneCategorie(12);
        assertNotNull("La categorie existe", cat12);
    }
    
    @Test
    public void testSupprimerCategorie() throws SQLException{
        dao.ajoutCategorie(12,"b","un beau b");
        int result = dao.supprimerCategorie(12);
        assertEquals(result,1);
        CategorieEntity cat12 = dao.uneCategorie(12);
        assertNull("La catégorie n'existe pas",cat12);
    }
    
    @Test
    public void testModifierCategorie() throws SQLException{
        dao.ajoutCategorie(12,"b","un beau b");
        int result = dao.modifierCategorie(12,"a","un beau a");
        assertEquals(result,1);
        CategorieEntity cat12 = dao.uneCategorie(12);
        assertEquals(cat12.getLibelle(),"a");
        assertEquals(cat12.getDescription(),"un beau a");
    }
    
    
    @Test
    public void findAllCategorie() throws SQLException {
            List<String> result = dao.toutesCategories();
            assertNotNull("Categorie exists, result should not be null", result);
            assertEquals(result.get(0),"Boissons");
            assertEquals(result.get(1),"Condiments");
            assertEquals(result.get(2),"Desserts");
            assertEquals(result.get(3),"Produit laitiers");
            assertEquals(result.get(4),"Pâtes et céréales");
            assertEquals(result.get(5),"Viandes");
            assertEquals(result.get(6),"Produit secs");
            assertEquals(result.get(7),"Poissons et fruits de mer");  
            assertEquals(result.size(),8);
    }
    
    @Test
    public void findProductFromCategorie() throws SQLException {
            List<ProduitEntity> result = dao.produitsDuneCategorie("Boissons");
            assertNotNull("Product exists, result should not be null", result);
            assertEquals(result.get(0).getNom(),"Chai");
            assertEquals(result.get(11).getNom(),"Lakkalikööri");
            assertEquals(result.size(),12);
    }
   
    @Test
    public void findAllProduct() throws SQLException {
            List<ProduitEntity> result = dao.tousProduits();
            assertNotNull("Product exists, result should not be null", result);
            assertEquals(result.get(0).getNom(),"Chai");
            assertEquals(result.get(10).getNom(),"Queso Cabrales");
            assertEquals(result.size(),77);
    }
    //laura
    @Test
    public void testProduitExist() throws SQLException{
        ProduitEntity prod = dao.unProduit(1);
        assertNotNull("Le produit existe", prod);
        assertEquals(prod.getNom(),"Chai");
    }
    
    @Test
    public void testProduitNotExist() throws SQLException{
        ProduitEntity prod = dao.unProduit(100);
        assertNull("Le produit n'existe pas encore",prod);
    }
    
    @Test
    public void testAjoutProduit() throws SQLException{
        ProduitEntity prod = dao.unProduit(80);
        assertNull("Le produit n'existe pas encore",prod);
        int result = dao.ajoutProduit(80,"Tablette chocolat",29,2,"20carrés par tablette", (float) 8.00,49,0,10,0);
        assertEquals(result,1);
        ProduitEntity prod80 = dao.unProduit(80);
        assertNotNull("Le produit existe", prod80);
    }
    
    @Test
    public void testSupprimerProduit() throws SQLException{
        dao.ajoutProduit(80,"Tablette chocolat",29,2,"20carrés par tablette", (float) 8.00,49,0,10,0);
        int result = dao.supprimerProduit(80);
        assertEquals(result,1);
        ProduitEntity prod80 = dao.unProduit(80);
        assertNull("Le produit n'existe pas",prod80);
    }
    
    @Test
    public void testModifierProduit() throws SQLException{
        dao.ajoutProduit(80,"Tablette chocolat",29,2,"20carrés par tablette", (float) 8.00,49,0,10,0);
        int result = dao.modifierProduit(80,"Tablette de chocolat noir",29,2,"20 carrés par tablette", 8.00f,49,0,10,0);
        assertEquals(result,1);
        ProduitEntity prod80 = dao.unProduit(80);
        assertEquals(prod80.getNom(),"Tablette de chocolat noir");
        assertEquals(prod80.getFournisseur(),29);
        assertEquals(prod80.getCategorie(),2);
        assertEquals(prod80.getQuantiteUnite(),"20 carrés par tablette");
        assertEquals(prod80.getPrix(),8.00f,0.001); //3e param est l'écart de valeur accepté pour les float
        assertEquals(prod80.getQttStock(),49);
        assertEquals(prod80.getUnitesCmdees(),0);
        assertEquals(prod80.getReapro(),10);
        assertEquals(prod80.getDispo(),0);
    }
    
   /*
    @Test
    public void testProduitCmdExist() throws SQLException{
        int[] idProd = dao.donneIdsProd(10248);
        assertNotNull("Le produit de la commande existe", idProd);
        assertEquals(idsProd,10248);
    }
    */
    /*
    @Test
    public void testProduitCmdNotExist() throws SQLException{
        CommandeEntity cmd = dao.uneCommande(11100);
        assertNull("Le produit de la commande n'existe pas encore",cmd);
    }
    
    @Test
    public void testCommandeExist() throws SQLException{
        CommandeEntity cmd = dao.uneCommande(10248);
        assertNotNull("La commande existe", cmd);
        assertEquals(cmd.getClient(),"VINET");
    }
    
    @Test
    public void testCommandeNotExist() throws SQLException{
        CommandeEntity cmd = dao.uneCommande(11100);
        assertNull("La commande n'existe pas encore",cmd);
    }
    
    @Test
    public void testQttProdExist() throws SQLException{
        float qtt = dao.donneQttProd(1);
        assertNotNull("La quantité du produit existe", qtt);
        assertEquals(0,qtt,0.0);
    }
    
    @Test
    public void testQttProdNotExist() throws SQLException{
        int qtt = dao.donneQttProd(100);
        assertEquals(-1,qtt);
    }
    
    @Test
    public void testModifierQttProduit() throws SQLException{
        dao.ajoutProduit(80,"Tablette chocolat",29,2,"20carrés par tablette", (float) 8.00,49,0,10,0);
        int result = dao.modifQttProd(80,3,true);
        assertEquals(result,1);
        int qtt = dao.donneQttProd(80);
        assertEquals(3,qtt);
    }
    
    @Test
    public void testLigneExist() throws SQLException{
        int[] ligne = dao.uneLigne(10248,11);
        assertNotNull("La ligne existe", ligne);
        assertEquals(ligne[2],12);
    }
    
    @Test
    public void testLigneNotExist() throws SQLException{
        int[] ligne = dao.uneLigne(10248,60);
        assertEquals(0,ligne[0]);
    }
    
    
    @Test
    public void testAjoutLigne() throws SQLException{
        int[] ligne = dao.uneLigne(10248,60);
        assertEquals(0,ligne[0]);
        int result = dao.ajoutLigne(10248,60,4);
        assertEquals(result,1);
        int[] nvLigne = dao.uneLigne(10248,60);
        assertNotNull("La ligne existe", ligne);
    }
    
    @Test
    public void testAjoutCommande() throws SQLException, ParseException{
        CommandeEntity cmd = dao.uneCommande(11100);
        assertNull("La commande n'existe pas encore",cmd);
        int[] idProd = {1};
        int[] qtt = {1};
        
        int result = dao.ajoutCommande(idProd,qtt,"HILAA","2014-12-31","2014-12-31",69.00f,"Le dest","L'adresse","Leguevin","","31490","France",0.00f);
        assertEquals(result,1);
        CommandeEntity cmd11100 = dao.uneCommande(11078);
        assertNotNull("La commande existe", cmd11100);
        //vérifier si les lignes ont été ajoutées
        int[] ligne = dao.uneLigne(11078,1);
        assertNotNull("La ligne existe", ligne);
        //vérifier si la quantité commandée a été changée
        assertEquals(1,dao.donneQttProd(22));
    }
    */
    @Test
    public void testClientExist() throws SQLException{
        List<ClientEntity> result = dao.unClient("ALFKI");
        assertNotNull("Le client existe", result);
        assertEquals(result.get(0).getSociete(),"Alfreds Futterkiste");
    }
    
    @Test
    public void testClientNotExist() throws SQLException{
        List<ClientEntity> result = dao.unClient("ATEST");
        assertNull("Le client n'existe pas encore",result);
    }
    
    @Test
    public void testSuppClient()throws SQLException{
        List<ClientEntity> result = dao.unClient("ALFKI");
        assertNotNull("Le client existe", result);
        assertEquals(result.get(0).getSociete(),"Alfreds Futterkiste");
        int nbSuppressions = dao.supprimerClient("ALFKI");
        assertEquals(nbSuppressions,1);
        List<ClientEntity> result2 = dao.unClient("ALFKI");
        assertNull("Le client n'existe pas encore",result2);
    }
    
    @Test
    public void testModifierClient() throws SQLException{
        int result = dao.modifClient("ALFKI", "Une société", "Maria Anders", "Représentant(e)", "Obere Str. 57", "Berlin", "Region", "12209", "Allemagne", "030-0074321", "030-0076545");
        assertEquals(result,1);
        List<ClientEntity> result2 = dao.unClient("ALFKI");
        assertEquals(result2.get(0).getSociete(),"Une société");
        assertEquals(result2.get(0).getContact(),"Maria Anders");
        assertEquals(result2.get(0).getFonction(),"Représentant(e)");
        assertEquals(result2.get(0).getVille(),"Berlin");
        assertEquals(result2.get(0).getAdresse(),"Obere Str. 57");
        assertEquals(result2.get(0).getCode_postal().trim(),"12209");
        assertEquals(result2.get(0).getRegion(),"Region");
        assertEquals(result2.get(0).getPays(),"Allemagne");
        assertEquals(result2.get(0).getTelephone(),"030-0074321");
        assertEquals(result2.get(0).getFax(),"030-0076545");
    }

    @Test
    public void testAjoutClient() throws SQLException{
        List<ClientEntity> result2 = dao.unClient("AAAAA");
        assertNull("Le client n'existe pas encore",result2);
        int result = dao.ajoutClient("AAAAA", "Une société", "Maria Anders", "Représentant(e)", "Obere Str. 57", "Berlin", "Region", "12209", "Allemagne", "030-0074321", "030-0076545");
        assertEquals(result,1);
        result2 = dao.unClient("AAAAA");
        assertEquals(result2.get(0).getSociete(),"Une société");
    }
    
    @Test
    public void testCaCategoriePeriode() throws SQLException, ParseException{
        //commande 1170 à 1173 (1seul jour)
        float ca = dao.caCategoriePeriode(1,"1996-06-04","1996-06-04");
        assertEquals(6700, ca,0.1);//
    }
    
    @Test
    public void testCaPaysPeriode() throws SQLException, ParseException{
        //commande 1170 à 1173 (1seul jour)
        float ca = dao.caPaysPeriode("Mexique","1996-06-01","1996-06-10");
        assertEquals(3290, ca,0.1);//
    }
    
    @Test
    public void testCaClientPeriode() throws SQLException, ParseException{
        //commande 1170 à 1173 (1seul jour)
        float ca = dao.caClientPeriode("LILAS","1996-05-29","1996-06-10");
        assertEquals(3806, ca,0.1);//
    }
    
}
