import com.ddd.pojo.Couchette;
import com.ddd.pojo.Ticket;
import com.ddd.repostitories.TicketRepostitory;
import com.ddd.services.BookingService;
import com.ddd.utils.JdbcUtils;
//import com.ddd.services.TicketService;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
public class BookingTester {

    private static Connection conn;
    private static BookingService bs;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        bs = new BookingService();
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

    @Test // Hàm kiểm tra xem thêm vé có thành công không?
    public void TestAddTicketSuccesfuly() {
        try {
            // Các tham số để kiểm tra hàm đặt vé
            Ticket ticket = new Ticket();// 	ID_Ghe= 1	ID_KhachHang = 1	ID_ChuyenXe = 1     ID_NhanVien = 3     NgayIn = 2023-04-17 15:36:12	
            ticket.setTicketId(1);
            ticket.setCustomerId(1); // Mã của khách
            ticket.setRouteId(1); // Mã chuyến đi 1: Bình Thuận - Hồ Chí Minh
            ticket.setStaffId(3); // Mã nhân viên
            ticket.setPrintingDate(Timestamp.valueOf(LocalDateTime.now().format(DTF)));
            ticket.setIsConfirm(false); // Chưa lấy vé

            Assertions.assertTrue(bs.AddTicket(ticket, 1)); //pass 100%
            String sql = "SELECT * FROM vexe WHERE ID_VeXe=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, ticket.getTicketId());

            ResultSet rs = stm.executeQuery();
            Assertions.assertNotNull(rs.next());
            Assertions.assertEquals("1", rs.getString("ID_Ghe")); // pass
            Assertions.assertEquals("1", rs.getString("ID_KhachHang")); // pass
            Assertions.assertEquals("1", rs.getString("ID_ChuyenXe")); // pass
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test // Hàm kiểm tra có lấy đủ vé theo ID khách không?
    public void TestGetAllTicketByCustomerId() {
        try {
            List<Ticket> tickets = bs.getAllTicketByCustomerId(1);
            String sql = "SELECT * FROM vexe WHERE ID_KhachHang=?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, 1);

            ResultSet rs = stm.executeQuery();
            Assertions.assertEquals(2, tickets.size());
            while (rs.next()) {
                Assertions.assertEquals("1", rs.getString("ID_KhachHang")); // pass
                Assertions.assertEquals("1", rs.getString("ID_ChuyenXe")); // pass
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
