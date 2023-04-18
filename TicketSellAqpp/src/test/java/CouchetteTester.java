
import com.ddd.pojo.Couchette;
import com.ddd.services.CouchetteService;
import com.ddd.utils.JdbcUtils;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
 * @author Admin
 */
public class CouchetteTester {

    private static Connection conn;
    private static CouchetteService cs;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        cs = new CouchetteService();
    }

    @AfterAll
    public static void afterAll() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm kiểm tra lấy các ghế từ ID của xe
    @Test
    public void TestgetCouchettesByCoachId() {
        try {
            List<Couchette> couchettes = cs.getCouchettesByCoachId(1); // 5 ghế của xe có ID là 1 (Xe Đông Hưng)

            Assertions.assertNotNull(couchettes);
            Assertions.assertEquals(5, couchettes.size()); // Mỗi xe có 5 ghế
            for (Couchette c : couchettes) {
                Assertions.assertEquals(1, c.getCouchId()); // 5 Ghế đều thuộc xe có ID là 1
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm kiểm tra lấy 1 ghế từ ID ghế
    @Test
    public void TestgetOneCouchetteByID() {
        try {
            Couchette couchette = cs.getOneCouchetteByID(1); // ID_Ghe = 1 ; TinhTrangGhe = 0; ID_Xe = 1; ThuTuGhe = 1
            Assertions.assertNotNull(couchette);
            Assertions.assertEquals(1, couchette.getCouchetteId()); // so sánh ID
            Assertions.assertEquals(false, couchette.getStatus()); // so sánh tình trạng ghế
            Assertions.assertEquals(1, couchette.getCouchId()); // so sánh ID xe
            Assertions.assertEquals(1, couchette.getOrderOfCouchette()); // so sánh thứ tự ghế
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm kiểm tra lấy tấy cả ghế có thành công không?
    @Test
    public void TestGetAllCouchette() {
        try {
            List<Couchette> couchettes = cs.getAllCouchette(); // 10 ghế thuộc 2 xe
            Assertions.assertNotNull(couchettes);
            Assertions.assertEquals(10, couchettes.size()); // Mỗi xe có 5 ghế -> 2 xe = 10 ghế
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm kiểm tra thay đổi trạng thái của ghế
    public void TestUpdateStatusCouchette() {
        try {
            Assertions.assertTrue(cs.updateStatusCouchette(1, true)); // Thay đổi trạng thái của ghế có ID là 1
            Couchette c = cs.getOneCouchetteByID(1);
            Assertions.assertEquals(true, c.getStatus());
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Hàm kiểm tra hàm lấy các ID ghế thuộc ID chuyến xe
    public void TestGetIdCouchetteByIdRoute() {
        try {
            List<Integer> IdCouchettes = cs.getIdCouchetteByIdRoute(1); // ID ghế thuộc chuyến 1 sẽ là 1,2,3,4,5
            Assertions.assertEquals(5, IdCouchettes.size());
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Hàm kiểm tra lấy trạng thái của tất cả ghế thuộc ID route truyền vào
    public void TestGetStatusCouchettebyIDRoute() {
        try {
            List<Boolean> statuses = cs.getStatusCouchettebyIDRoute(1); // ID ghế thuộc chuyến 1 sẽ là 1,2,3,4,5 và trạng thái của 5 ghế là false
            Assertions.assertEquals(5, statuses.size());
            for(Boolean b : statuses){
                Assertions.assertFalse(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
