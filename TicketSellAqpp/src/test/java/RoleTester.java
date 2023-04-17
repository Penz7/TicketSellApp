/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddd.pojo.Role;
import com.ddd.services.RoleService;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author admin
 */
public class RoleTester {
    
    @Test
    void testGetRoles() throws SQLException {
        RoleService r = new RoleService();
        // Arrange
        String kw = "admin";

        // Act
        List<Role> roles = r.getRoles(kw);

        // Assert
        Assertions.assertEquals(1, roles.size());
        Role role = roles.get(0);
        Assertions.assertEquals(1, role.getId_role());
        Assertions.assertEquals("admin", role.getName_role());
    }
    
    @Test
    void testGetRoleById() throws SQLException {
        // Arrange
        RoleService roleService = new RoleService();
        int roleId = 1;

        // Act
        Role role = roleService.getRoleById(roleId);

        // Assert
        Assertions.assertNotNull(role);
        Assertions.assertEquals(roleId, role.getId_role());
        Assertions.assertEquals("admin", role.getName_role());
    }
}
