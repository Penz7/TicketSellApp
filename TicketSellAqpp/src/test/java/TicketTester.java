
import com.ddd.pojo.RouteCoachCouchette;
import com.ddd.pojo.Ticket;
import com.ddd.services.TicketService;
import com.ddd.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
public class TicketTester {

    private static Connection conn;
    private static TicketService ts;
    private final static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        ts = new TicketService();
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

    // Hàm kiểm tra lấy 1 vé bởi ID vé
    @Test
    public void TestGetTicketById() {
        try {
            Ticket t = ts.getTicketById(42); // ID = 42; ID_Ghe=1; ID_KhachHang= 1;	ID_ChuyenXe=1; ID_NhanVien=3;NgayIn = 2023-04-17 22:42:59; IsConfirm=0
            Assertions.assertEquals(42, t.getTicketId());
            Assertions.assertEquals(1, t.getCouchetteId());
            Assertions.assertEquals(1, t.getCustomerId());
            Assertions.assertEquals(1, t.getRouteId());
            Assertions.assertEquals(3, t.getStaffId());
            Assertions.assertEquals(Timestamp.valueOf("2023-04-17 22:42:59"), t.getPrintingDate());
            Assertions.assertFalse(t.isIsConfirm());
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm kiểm tra xem id user truyền vào có phải khách hàng không?
    @Test
    public void TestCheckUserCustomer() {
        try {
            Assertions.assertTrue(ts.checkUserCustomer(1)); // ID_user = 3 là khách hàng
            Assertions.assertFalse(ts.checkUserCustomer(2)); // ID_user = 1 là Admin
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm kiểm tra xem xóa vé có thành công không?
    @Test
    public void TestDeleteTicket() {
        try {
            Assertions.assertTrue(ts.deleteTicket(43)); // Xóa vé có ID là 43
            Assertions.assertNull(ts.getTicketById(43)); // Sau khi xóa thì sẽ không có vé ID = 43 nữa
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm kiểm tra xem đổi vé có thành công không?
    @Test
    public void TestUpdateTicketSeat() {
        try {
            //Ghế ban đầu: ID = 42; ID_Ghe=1; ID_KhachHang= 1;	ID_ChuyenXe=1; ID_NhanVien=3;NgayIn = 2023-04-17 22:42:59; IsConfirm=0
            Assertions.assertTrue(ts.updateTicketSeat(42, 6, 2)); // đổi vé lại thành Mã ghế = 2 , Mã chuyến xe = 2

            // Xem thử vé đã đổi được chưa
            Ticket t = ts.getTicketById(42); // ID = 42; ID_Ghe=2; ID_KhachHang= 1;	ID_ChuyenXe=2; ID_NhanVien=3;NgayIn = 2023-04-17 22:42:59; IsConfirm=0
            Assertions.assertEquals(42, t.getTicketId());
            Assertions.assertEquals(2, t.getCouchetteId());
            Assertions.assertEquals(1, t.getCustomerId());
            Assertions.assertEquals(2, t.getRouteId());
            Assertions.assertEquals(3, t.getStaffId());
            Assertions.assertEquals(Timestamp.valueOf("2023-04-17 22:42:59"), t.getPrintingDate());
            Assertions.assertFalse(t.isIsConfirm());
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Hàm này giống hàm TestGetTicketById() {
    @Test
    public void TestGetAllTickets() {

    }

    // Hàm kiểm tra cập nhật trạng thái của vé
    @Test
    public void TestCheckConfirmOrder() {
        try {
            Assertions.assertTrue(ts.checkConfirmOrder(42)); // Cập nhật trạng thái vé có ID = 42 về True 
            Assertions.assertTrue(ts.getTicketById(42).isIsConfirm()); // kiểm tra dưới database đã đổi trạng thái vé chưa
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Hàm kiểm tra cập nhật ngày in và nhân viên xác nhận vé
    @Test
    public void TestUpdatePrintingDateAndStaffConfirm() {
        try {
            Timestamp now = Timestamp.valueOf(LocalDateTime.now().format(DTF));
            Assertions.assertTrue(ts.updatePrintingDateAndStaffConfirm(20, now ,42)); // Cập nhật ngày in về giờ hiện tại và nhân viên 
            Assertions.assertEquals(now,ts.getTicketById(42).getPrintingDate()); // kiểm tra dưới database đã đổi trạng thái vé chưa
            Assertions.assertEquals(20, ts.getTicketById(42).getStaffId()); // Kiểm tra có phải ID nhân viên đã đổi thành 20 chưa
        } catch (SQLException ex) {
            Logger.getLogger(BookingTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
