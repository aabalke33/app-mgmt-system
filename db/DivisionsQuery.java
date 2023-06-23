package balke.c195.db;

import balke.c195.model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
/**
 * This class supports the connection between divisions and the SQL Server
 */
public abstract class DivisionsQuery {
    /**
     * This method gets all divisions of a specified country in the SQL Server and returns them to the application
     * @param countryIdIn
     * @return
     * @throws SQLException
     */
    public static ObservableList<Division> selectByCountry(int countryIdIn) throws SQLException {

        ObservableList<Division> divisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryIdIn);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            Timestamp createDate = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_by");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdateBy = rs.getString("Last_Updated_By");
            int countryId = rs.getInt("Country_ID");

            Division division = new Division(divisionId,divisionName,createDate,createdBy,lastUpdate,lastUpdateBy,countryId);
            divisions.addAll(division);
        }
        return divisions;
    }

    /**
     * This method gets the ID of the Division with a specified name in the SQL Server and returns it to the application
     * @param divisionName
     * @return
     * @throws SQLException
     */
    public static int getId(String divisionName) throws SQLException {

        String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, divisionName);
        ResultSet rs = ps.executeQuery();

        int divisionId = 0;

        while (rs.next()) {
            divisionId = rs.getInt("Division_ID");
        }
        return divisionId;
    }
}
