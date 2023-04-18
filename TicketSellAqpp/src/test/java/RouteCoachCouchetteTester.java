
import com.ddd.pojo.Coach;
import com.ddd.pojo.RouteCoachCouchette;
import com.ddd.services.CoachService;
import com.ddd.services.RouteCoachCouchetteService;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
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
public class RouteCoachCouchetteTester {

    private static Connection conn;
    private static RouteCoachCouchetteService rccs;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        rccs = new RouteCoachCouchetteService();
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
    
    // Hàm kiểm tra lấy dữ liệu cho bảng tableview
    @Test
    public void TestGetDataForTableViewBooking() {
        try {
            List<RouteCoachCouchette> lrcc = rccs.getDataForTableViewBooking(1, Date.valueOf(LocalDate.parse("2023-04-19", DTF))); // Gồm 5 phần từ có ID chuyến xe là 1, Mã ghế lần lượt là 1,2,3,4,5 - Tên Chuyến xe là Bình Thuận - Hồ Chí Minh
            Assertions.assertNotNull(lrcc);
            Assertions.assertEquals(5, lrcc.size()); // Mỗi xe có 5 ghế
            for (RouteCoachCouchette rcc : lrcc) {
                Assertions.assertEquals(1, rcc.getRouteID()); // // Mã Chuyến xe đều là 1
                Assertions.assertEquals("Bình Thuận - Hồ Chí Minh", rcc.getRouteName()); // Tên chuyến xe là Bình Thuận - Hồ Chí Minh
                Assertions.assertEquals("Đông Hưng", rcc.getCoachName()); // Tên xe là Đông Hưng
                Assertions.assertEquals(560000.0, rcc.getFare());
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
