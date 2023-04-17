
import com.ddd.pojo.Ticket;
import com.ddd.repostitories.TicketRepostitory;
import com.ddd.services.BookingService;
import com.ddd.utils.JdbcUtils;
import com.ddd.services.TicketService;
import java.lang.annotation.Target;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
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
    private static TicketRepostitory tr;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bs = new BookingService();
        tr = new TicketRepostitory();
    }
    
    @AfterAll
    public static void afterAll() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test // Hàm kiểm tra xem thêm vé có thành công không?
    public void TestAddTicketSuccesfuly(){
        try {
             Ticket ticket = new Ticket();// 	ID_Ghe= 1	ID_KhachHang = 1	ID_ChuyenXe = 1     ID_NhanVien = 3     NgayIn = 2023-04-17 15:36:12	
                ticket.se(1);
                ticket.setCustomer(rs.getInt("ID_KhachHang"));
                ticket.setRoute(rs.getInt("ID_ChuyenXe"));
                ticket.setStaff(rs.getInt("ID_NhanVien"));
                ticket.setPrintingDate(rs.getTimestamp("NgayIn"));
                ticket.setIsConfirm(rs.getBoolean("isConfirm")); 
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        Couchette
    }
}
