import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.asserts.SoftAssert;
import java.sql.*;

public class AutoCommit {

    public static void executeWithAutoCommitFalse()  {
        String sqlUpdate = "INSERT INTO studentsInfo(id, firstName, lastName, phone)" +
                " Values ('1004', 'Ambartsum', 'Karapetyan', '555666777')";
        try (
                Connection conn = SQLconnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            conn.setAutoCommit(false);
            int rowAffected = pstmt.executeUpdate();
            SoftAssert softAssertion= new SoftAssert();
            String query = "select *  from studentsInfo;";
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery(query);
            while (rs.next()) {
                softAssertion.assertTrue(rs.getString("firstName").contains("Ambartsum"));}
            conn.commit();
            softAssertion.assertEquals(rowAffected, 1);
            softAssertion.assertAll();} catch (
                SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args) {
        executeWithAutoCommitFalse();
    }
}