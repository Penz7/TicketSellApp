/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddd.pojo.User;
import com.ddd.services.SignInService;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class SignInTester {

    @Test
    public void testGetAccount() throws SQLException, NoSuchAlgorithmException {
        // Arrange
        String username = "username2";
        String password = "123456";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        expectedUser.setPassword(password);
        expectedUser.setRole_id(1);
        expectedUser.setUser_fullname("Trần Đatk");
        expectedUser.setUser_id_card("1202345");
        expectedUser.setUser_date_join(Date.valueOf("2023-03-21"));
        expectedUser.setUser_address("84 Nguyễn Kiệm");
        expectedUser.setUser_phone_number("0123456789");
        expectedUser.setUser_id(2);

        // Act
        SignInService userDao = new SignInService();
        User actualUser = userDao.getAccountMD5(username, password);

        // Assert
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getRole_id(), actualUser.getRole_id());
        Assertions.assertEquals(expectedUser.getUser_fullname(), actualUser.getUser_fullname());
        Assertions.assertEquals(expectedUser.getUser_id_card(), actualUser.getUser_id_card());
        Assertions.assertEquals(expectedUser.getUser_date_join(), actualUser.getUser_date_join());
        Assertions.assertEquals(expectedUser.getUser_address(), actualUser.getUser_address());
        Assertions.assertEquals(expectedUser.getUser_phone_number(), actualUser.getUser_phone_number());
        Assertions.assertEquals(expectedUser.getUser_id(), actualUser.getUser_id());
    }
}
