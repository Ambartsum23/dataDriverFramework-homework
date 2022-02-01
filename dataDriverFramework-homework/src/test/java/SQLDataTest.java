import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static com.codeborne.selenide.Selenide.*;

public class SQLDataTest {
    public SQLDataTest() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
    }

    @Test
    public void getDBValues() throws SQLException {
        try {
            Connection conn = SQLconnect.getConnection();
            String query = "select *  from studentsInfo;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            open("https://demoqa.com/automation-practice-form");
            SelenideElement firstNameInput = $("#firstName");
            SelenideElement lastNameInput = $("#lastName");
            SelenideElement numberInput = $("#userNumber");

            while (rs.next()) {

                open("https://demoqa.com/automation-practice-form");
                firstNameInput.sendKeys(rs.getString("firstName"));
                lastNameInput.sendKeys(rs.getString("lastName"));
                numberInput.sendKeys(rs.getString("phone"));
                Assert.assertEquals(rs.getString("firstName"), $("#firstName").getValue());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}