/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniprojetTest;

import model.DAO;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import static java.sql.Types.NULL;
import javax.sql.DataSource;
import model.ClientEntity;
import static model.DataSourceFactory.getDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author pedago
 */
public class DAOTest {
    
    public DAOTest() {
    }


    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    private static DataSource myDataSource; // La source de données à utiliser

    private static Connection myConnection ;

    private DAO myDAO; // L'objet à tester
    private ClientEntity myCustomer;

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    @Before
    public  void setUp() throws IOException, SQLException {
            // On crée la connection vers la base de test "in memory"
            myDataSource = getDataSource();
            myConnection = myDataSource.getConnection();	

            myDAO = new DAO(myDataSource);
            myCustomer = new ClientEntity( "ALFKI", "Alfreds Futterkiste", "Maria Anders", "Représentant(e)", "Obere Str. 57", "Berlin", null, "12209", "Allemagne", "030-0074321", "030-0076545");
    }

    @After
    public void tearDown() throws SQLException {
            myConnection.close();
            myDAO = null; // Pas vraiment utile
    }
/*
    @Test
    public void canModifClient() throws Exception {
            // On calcule combien le client a de factures
            String id = myCustomer.getCode();
            int before = myDAO.modifClient("ALFKI",["Fonction"], ["Rep"] );
            // Un tableau d'un attribut à modifier
            String[] atts = new String['Fonction'];
            // Un tableau d'une valeur
            String[] valeurs = new String['Rep'];
            // On exécute la transaction
            myDAO.modifClient(myCustomer, atts, valeurs);
            int after = myDAO.modifClient( myCustomer.getCustomerId() );
            // Le client a maintenant une facture de plus
            assertEquals(before + 1, after);		
    }
/*
    // On vérifie que la création d'une facture met à jour le chiffre d'affaire du client (Trigger)
    @Test
    public void createInvoiceUpdatesTotal() throws Exception {
            // On calcule le chiffre d'aafaire du client
            int id = myCustomer.getCustomerId();
            float before = myDAO.totalForCustomer(id);
            System.out.printf("Before: %f %n", before);

            // Un tableau de 1 productID
            int[] productIds = new int[]{0}; // Le produit 0 vaut 10 €
            // Un tableau de 1 quantites
            int[] quantities = new int[]{2};
            // On exécute la transaction
            myDAO.createInvoice(myCustomer, productIds, quantities);

            float after = myDAO.totalForCustomer(id);
            System.out.printf("After: %f %n", after);

            // Le client a maintenant 2*10€ de plus

            assertEquals(before + 2f * 10f, after, 0.001f);		
    }



    public static DataSource getDataSource() throws SQLException {
            org.hsqldb.jdbc.JDBCDataSource ds = new org.hsqldb.jdbc.JDBCDataSource();
            ds.setDatabase("jdbc:hsqldb:mem:testcase;shutdown=true");
            ds.setUser("sa");
            ds.setPassword("sa");
            return ds;
    }
*/
}