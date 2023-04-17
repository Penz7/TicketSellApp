/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddd.services.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author admin
 */
public class RegisterTester {

    @Test
    void testAddAccount() {
        RegisterService r = new RegisterService();
        // Arrange
        String fullName = "John Doe";
        String idCard = "123456789";
        String phoneNumber = "1234567890";
        String username = "johndoe";
        String password = "password";
        String address = "123 Main St.";

        // Act
        boolean result = r.addAccount(fullName, idCard, phoneNumber, username, password, address);

        // Assert
         Assertions.assertTrue(result, "Failed to add account.");
         Assertions.assertTrue(r.checkAccountUsername(username), "Account was not added successfully.");
    }
    
    @Test
    void checkAccountExist() {
        RegisterService registerService = new RegisterService();
        String username = "newusername";
        // Act
        boolean result = registerService.checkAccountUsername(username);
        // Assert
         Assertions.assertTrue(result, "Account exist");
    }

}
