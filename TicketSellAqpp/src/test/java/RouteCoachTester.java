
import com.ddd.pojo.Couchette;
import com.ddd.pojo.RouteCoach;
import com.ddd.services.RouteCoachService;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class RouteCoachTester {
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static Connection conn;
    private static RouteCoachService rcs;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        rcs = new RouteCoachService();
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

    // Kiểm tra lấy Chuyenxe_Xe bằng ID route và ID coach
    @Test
    public void TestGetOneRouteCoachById() {
        try {
            RouteCoach rc = rcs.getOneRouteCoachById(1, 1);
            Assertions.assertEquals(Timestamp.valueOf("2023-04-19 19:30:00"), rc.getDepartureTime());
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
