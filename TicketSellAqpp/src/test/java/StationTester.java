/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.ddd.pojo.Station;
import com.ddd.services.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author admin
 */
public class StationTester {

    @Test
    public void testGetStationByName() throws SQLException {
        StationService s = new StationService();
        // Arrange
        String stationName = "Bình Thuận";
        Station expectedStation = new Station(1, stationName);

        // Act
        Station actualStation = s.getStationByName(stationName);

        // Assert
        Assertions.assertNotNull(actualStation);
        Assertions.assertEquals(expectedStation.getStationId(), actualStation.getStationId());
        Assertions.assertEquals(expectedStation.getStationName(), actualStation.getStationName());
    }

    @Test
    public void testAddStation() throws SQLException {
        StationService s = new StationService();
        // Arrange
        String stationName = "Station A";

        // Act
        boolean actual = s.addStation(stationName);

        // Assert
        Assertions.assertTrue(actual);
    }

    @Test
    public void testGetAllStation() throws SQLException {
        StationService s = new StationService();

        // Arrange
        String stationName = "Station A";

        // Act
        s.addStation(stationName);
        List<Station> actual = s.getAllStation();

        // Assert
        Assertions.assertEquals(7, actual.size());

    }

    @Test
    public void testGetStations() throws SQLException {
        // Arrange
        StationService service = new StationService();
        String keyword = "ABC";
        Station station2 = new Station(33, "ABC Station");
        List<Station> expected = Arrays.asList(station2);

        // Add some stations to the database
        service.addStation(station2.getStationName());

        // Act
        List<Station> actual = service.getStations(keyword);

        // Assert
        Assertions.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            Assertions.assertEquals(expected.get(i).getStationName(), actual.get(i).getStationName());
        }
    }

    @Test
    public void testDeleteStation() throws SQLException {
        // Arrange
        StationService service = new StationService();
        Station station = new Station(31, "Station A");   
        service.addStation(station.getStationName());

        // Act
        boolean result = service.deleteStation(Integer.toString(station.getStationId()));

        // Assert
        Assertions.assertTrue(result);
        List<Station> stations = service.getAllStation();
        Assertions.assertEquals(6, stations.size());
    }

    @Test
    public void testUpdateStationNameById() throws SQLException {
        // Arrange
        StationService service = new StationService();
        String newStationName = "A";

        // Act
        boolean result = service.updateStationNameById(Integer.toString(1), newStationName);

        // Assert
        List<Station> actual = service.getAllStation();
        Assertions.assertTrue(result);
        Assertions.assertEquals("A", actual.get(0).getStationName());
    }

    @Test
    public void testIsExistStationByName() throws SQLException {
        // Arrange
        StationService service = new StationService();
        String stationName = "Station A";
        service.addStation(stationName);

        // Act
        boolean result = service.isExistStationByName(stationName);

        // Assert
        Assertions.assertTrue(result);
    }
}
