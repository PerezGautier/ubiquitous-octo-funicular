/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
    
    
    
    private void executeSQLScript(Connection connexion, String filename)  throws IOException, SqlToolError, SQLException {
        // On initialise la base avec le contenu d'un fichier de test
        String sqlFilePath = DAOTest.class.getResource(filename).getFile();
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
            List<ProduitEntity> result = dao.produitsDuneCategorie(1);
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
    
    
    
}