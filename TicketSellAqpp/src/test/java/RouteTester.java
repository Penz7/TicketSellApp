/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddd.pojo.Route;
import com.ddd.repostitories.RouteRepostitory;
import com.ddd.services.RouteService;
import java.sql.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author admin
 */
public class RouteTester {

    private final static RouteService r;

    static {
        r = new RouteService();
    }

    @Test
    void testGetRoutesById() throws SQLException {
        // Set up test data
        int id = 1;
        Route expectedRoute = new Route(id, "Bình Thuận - Hồ Chí Minh", 560000.00, 3, 1);

        // Call the method to be tested
        List<Route> routes = r.getRoutesById(id);

        // Verify the results
        assertEquals(1, routes.size(), "Expected exactly one route to be returned");
        Route actualRoute = routes.get(0);
        assertEquals(expectedRoute.getRouteId(), actualRoute.getRouteId(), "Route ID mismatch");
        assertEquals(expectedRoute.getNameRoute(), actualRoute.getNameRoute(), "Route name mismatch");
        assertEquals(expectedRoute.getFare(), actualRoute.getFare(), 0.001, "Route price mismatch");
        assertEquals(expectedRoute.getDestinationID(), actualRoute.getDestinationID(), "Route destination mismatch");
        assertEquals(expectedRoute.getDepartureID(), actualRoute.getDepartureID(), "Route origin mismatch");
    }

    @Test
    void testCheckRouteExists() throws SQLException {
        // Set up test data
        int diID = 1;
        int denID = 2;

        // Call the method to be tested
        RouteRepostitory r = new RouteRepostitory();
        boolean result = r.checkRouteExists(diID, denID);

        // Verify the results
        Assertions.assertTrue(result, "Expected route to exist");

        // Test with non-existing route
        diID = 2;
        denID = 3;
        result = r.checkRouteExists(diID, denID);
        Assertions.assertFalse(result, "Expected route to not exist");
    }

    @Test
    void testGetRoute() throws SQLException {
        // Set up test data
        String kw = "Bình Thuận - Hồ Chí Minh";

        RouteRepostitory r = new RouteRepostitory();
        // Call the method to be tested
        List<Route> result = r.getRoute(kw);

        // Verify the results
        Assertions.assertNotNull(result, "Expected non-null result");
        Assertions.assertEquals(1, result.size(), "Expected two routes matching keyword");

        // Test with no keyword
        kw = null;
        result = r.getRoute(kw);
        Assertions.assertNotNull(result, "Expected non-null result");
        Assertions.assertEquals(6, result.size(), "Expected all routes");
    }

    @Test
    void testGetRouteByDesIdByDepId() throws SQLException {
        // Set up test data
        String desId = "3";
        String depId = "1";
        Date orderDate = Date.valueOf("2023-04-13");

        // Call the method to be tested
        RouteService routeService = new RouteService();
        List<Route> result = routeService.getRouteByDesIdByDepId(desId, depId, orderDate);

        // Verify the results
        Assertions.assertNotNull(result, "Expected non-null result");
        Assertions.assertEquals(1, result.size(), "Expected one route to match the criteria");
        Route route = result.get(0);
        Assertions.assertEquals(1, route.getRouteId(), "Expected ID of the route to be 1");
        Assertions.assertEquals("Bình Thuận - Hồ Chí Minh", route.getNameRoute(), "Expected name of the route to be Bình Thuận - Hồ Chí Minh");
        Assertions.assertEquals(560000.0, route.getFare(), "Expected price of the route to be 560000.0");
        Assertions.assertEquals(3, route.getDepartureID(), "Expected departure ID of the route to be 2");
        Assertions.assertEquals(1, route.getDestinationID(), "Expected destination ID of the route to be 1");
    }

    @Test
    void testAddRoute() throws SQLException {
        // Set up test data
        String name = "Test Route";
        int depId = 1;
        int desId = 2;
        double fare = 100000.0;

        // Call the method to be tested
        RouteRepostitory r = new RouteRepostitory();
        boolean result = r.addRoute(name, depId, desId, fare);

        // Verify the result
        assertTrue(result, "Expected the route to be added successfully");
    }

    @Test
    void testGetNameStation() throws SQLException {
        // Set up test data
        List<String> expected = Arrays.asList("Bình Thuận", "Đà lạt", "Hà Nội", "Hồ Chí Minh", "Kon Tum", "Ninh Bình");

        // Call the method to be tested
        RouteRepostitory r = new RouteRepostitory();
        List<String> result = r.getNameStation();

        // Verify the results
        Assertions.assertNotNull(result, "Expected non-null result");
        Assertions.assertEquals(expected.size(), result.size(), "Expected same number of stations");
        for (int i = 0; i < expected.size(); i++) {
            Assertions.assertEquals(expected.get(i), result.get(i), "Expected name of station to match");
        }
    }

    @Test
    void testGetStationIDbyname() throws SQLException {
        // Set up test data
        String name = "Hà nội";

        // Call the method to be tested
        RouteRepostitory r = new RouteRepostitory();
        Integer result = r.getStationIDbyname(name);

        // Verify the results
        Assertions.assertNotNull(result, "Expected non-null result");
        Assertions.assertEquals(4, result.intValue(), "Expected ID of Hanoi station to be 1");
    }

    @Test
    void testGetRouteIDbyname() throws SQLException {
        // Set up test data
        String name = "Test Route 1";

        // Call the method to be tested
        RouteRepostitory r = new RouteRepostitory();
        Integer result = r.getRouteIDbyname(name);

        // Verify the results
        Assertions.assertNotNull(result, "Expected non-null result");
        Assertions.assertEquals(1, result, "Expected ID of the route to be 1");
    }

    @Test
    public void testGetOneRouteByID() throws SQLException {
        // Get the ID of the newly inserted Route
        Integer id = 1;

        // Call the method being tested
        Route retrievedRoute = r.getOneRouteByID(id);

        // Check that the retrieved Route matches the original Route
        assertEquals("Bình Thuận - Hồ Chí Minh", retrievedRoute.getNameRoute());
        assertEquals(1, retrievedRoute.getDepartureID());
        assertEquals(3, retrievedRoute.getDestinationID());
        assertEquals(560000.00, retrievedRoute.getFare());

    }
    
    
    @Test
    void testGetDepartureTimeByIdRoute() throws SQLException {
        RouteService r = new RouteService();
        Timestamp expectedTime = Timestamp.valueOf(LocalDateTime.of(2023, 4, 13, 17, 30, 0));
        Timestamp actualTime = r.getDepartureTimeByIdRoute(1);
        assertEquals(expectedTime, actualTime);
    }

    @Test
    public void testGetIdRoute() throws SQLException {
        RouteService routeService = new RouteService();
        List<Integer> idList = routeService.getIdRoute();

        Assertions.assertNotNull(idList);
        Assertions.assertFalse(idList.isEmpty());
        Assertions.assertTrue(idList.contains(1)); // assuming there's a route with ID 1 in the database
        // add more assertions based on your database data
    }
}
