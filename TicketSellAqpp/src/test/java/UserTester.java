
import com.ddd.pojo.User;
import com.ddd.repostitories.UserRepostitory;
import com.ddd.services.UserService;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author admin
 */
public class UserTester {

    @Test
    public void testGetUser() throws SQLException {
        UserService userDao = new UserService();
        List<User> results = userDao.getUser("username2");

        Assertions.assertEquals(1, results.size());
        User user = results.get(0);
        Assertions.assertEquals("Trần Đatk", user.getUser_fullname());
        Assertions.assertEquals("username2", user.getUsername());
        Assertions.assertEquals("84 Nguyễn Kiệm", user.getUser_address());
    }

    @Test
    public void testGetStaff() throws SQLException {
        UserService userService = new UserService();

        // Test case 1: no search keyword
        List<User> staffList1 = userService.getStaff(null);
        Assertions.assertEquals(3, staffList1.size());
        User staff1 = staffList1.get(0);
        Assertions.assertEquals(2, staff1.getRole_id());

        // Test case 2: search with a keyword that matches one staff
        List<User> staffList2 = userService.getStaff("Duy nến");
        Assertions.assertEquals(1, staffList2.size());
        User staff2 = staffList2.get(0);
        Assertions.assertEquals(2, staff2.getRole_id());
        Assertions.assertEquals("Duy nến", staff2.getUser_fullname());
    }

    @Test
    public void testUpdateUserById() throws ParseException {
        String dateString = "2002-01-04";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try {
            UserService userDao = new UserService();
            boolean updated = userDao.updateUserById("Cáp Tấn Đạt", "0123456", "0961414818", sqlDate, "username1", "123456", "126 Bình Thạnh", 1);
            Assertions.assertTrue(updated);

            List<User> results = userDao.getUser("Cáp Tấn Đạt");
            Assertions.assertEquals(1, results.size());
            User user = results.get(0);
            Assertions.assertEquals("Cáp Tấn Đạt", user.getUser_fullname());
            Assertions.assertEquals("0123456", user.getUser_id_card());
            Assertions.assertEquals("0961414818", user.getUser_phone_number());
            Assertions.assertEquals("username1", user.getUsername());
            Assertions.assertEquals("123456", user.getPassword());
            Assertions.assertEquals("126 Bình Thạnh", user.getUser_address());
            Assertions.assertEquals(1, user.getUser_id());
        } catch (SQLException ex) {
            Logger.getLogger(UserTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testDeleteStaff() throws ParseException, SQLException {
        // Set up test data
        String dateString = "2002-01-04";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        UserService userService = new UserService();

        // Get initial size of user list
        int initialSize = userService.getAllUser().size();

        // Add a new user
        userService.addUser("John Doe", "56541", "054513487", sqlDate, "johndoe", "password", "123 Main St, Anytown USA");

        // Get the ID of the new user
        List<User> results = userService.getUser("John Doe");
        int newStaffId = results.get(0).getUser_id();

        // Delete the new user and check that they were deleted
        boolean deleted = userService.deleteStaff(String.valueOf(newStaffId));
        Assertions.assertTrue(deleted);

        results = userService.getUser("John Doe");
        Assertions.assertEquals(0, results.size());

        // Check that the number of users in the database has decreased by 1
        int finalSize = userService.getAllUser().size();
        Assertions.assertEquals(initialSize, finalSize);
    }

    @Test
    public void testAddUser() throws ParseException {
        String dateString = "2002-01-04";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        UserService userService = new UserService();
        String user_fullname = "John Doe";
        String user_id_card = "123456789";
        String user_phone_number = "1234567890";
        String username = "johndoe";
        String password = "password";
        String user_address = "123 Main St.";

        boolean actual = userService.addUser(user_fullname, user_id_card, user_phone_number, sqlDate, username, password, user_address);

        Assertions.assertTrue(actual);
    }

    @Test
    public void testAddCustomer() throws ParseException {
          String dateString = "2002-01-04";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dateString);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        UserRepostitory s = new UserRepostitory();
        String user_fullname = "John Doe";
        String user_id_card = "123456789";
        String user_phone_number = "1234567890";
        String username = "johndoe";
        String password = "password";
        String user_address = "123 Main St.";

        boolean actual = s.addCustomer(user_fullname, user_id_card, user_phone_number, sqlDate, username, password, user_address);

        Assertions.assertTrue(actual);
    }
    @Test
    void testGetOneUserByID() throws SQLException {
        int id = 1;
        UserService userDao = new UserService();
        User result = userDao.getOneUserByID(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(id, result.getUser_id());
    }
}
