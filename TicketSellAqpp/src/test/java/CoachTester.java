/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddd.pojo.Coach;
import com.ddd.services.CoachService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author admin
 */
public class CoachTester {
    
    @Test
    public void testGetCoach() throws SQLException {
        // Arrange
        CoachService coachService = new CoachService();
        String keyword = "Tấn Đạt";

        // Act
        List<Coach> coaches = coachService.getCoach(keyword);

        // Assert
        Assertions.assertNotNull(coaches);
        Assertions.assertFalse(coaches.isEmpty());
        for (Coach coach : coaches) {
            Assertions.assertTrue(coach.getCoachName().contains(keyword));
        }
    }

    @Test
    public void testDeleteCoach() throws SQLException {
        // Arrange
        CoachService coachService = new CoachService();

        // Insert the coach into the database
        coachService.addCoach("Test Coach", "ABC123");
        Integer id = coachService.getCoachIDbyname("Test Coach");
        // Act
        boolean success = coachService.deleteCoach(id);

        // Assert
        Assertions.assertTrue(success);
    }

    @Test
    public void testUpdateCoachById() throws SQLException {
        // Arrange
        CoachService coachService = new CoachService();
        // Insert the coach into the database
        coachService.addCoach("Test Coach", "ABC123");
        int id = coachService.getCoachIDbyname("Test Coach");
        // Act
        boolean success = coachService.updateCoachById("Updated Coach", "XYZ789", id);

        // Assert
        Assertions.assertTrue(success);
        Coach updatedCoach = coachService.getCoachByID(id);
        Assertions.assertNotNull(updatedCoach);
        Assertions.assertEquals("Updated Coach", updatedCoach.getCoachName());
        Assertions.assertEquals("XYZ789", updatedCoach.getLicensePlates());
    }

    @Test
    public void testAddCoach() throws SQLException {
        CoachService coachService = new CoachService();
        // Setup
        String TenXe = "Test Coach";
        String BienSo = "TS-12345";

        // Execute
        boolean result = coachService.addCoach(TenXe, BienSo);

        // Verify
        Assertions.assertTrue(result);
        int id = coachService.getCoachIDbyname("Test Coach");
        coachService.deleteCoach(id);

    }

    @Test
    public void testGetCoachIDbyname() throws SQLException {
        CoachService coachService = new CoachService();
        // Set up test data
        String name = "Tấn Đạt";
        Integer expectedID = 1;

        // Call the method being tested
        Integer actualID = coachService.getCoachIDbyname(name);

        // Verify the result
        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    public void testGetCoachByID() throws SQLException {
        CoachService coachService = new CoachService();
        // Create a new coach and add it to the database
        String tenXe = "Test Coach";
        String bienSo = "1234";
        coachService.addCoach(tenXe, bienSo);
        int id = coachService.getCoachIDbyname(tenXe);

        // Get the coach by ID and verify its properties
        Coach coach = coachService.getCoachByID(id);
        Assertions.assertEquals(id, coach.getCoachId());
        Assertions.assertEquals(tenXe, coach.getCoachName());
        Assertions.assertEquals(bienSo, coach.getLicensePlates());
    }
}
