package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

}
