/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Station;
import com.ddd.repostitories.StationRepository;
import com.ddd.utils.MessageBox;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class BookingController implements Initializable {

    @FXML
    private TextField txtSearchDestination;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TextFields.bindAutoCompletion(txtSearchDestination, getAllNameStation());
    }

    private List<String> getAllNameStation() {
        List<String> name = new ArrayList<>();
        List<Station> listStation = new ArrayList<>();
        StationRepository sr = new StationRepository();
        try {
            listStation = sr.getAllStation();
        } catch (SQLException ex) {
            Logger.getLogger(BookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
       listStation.forEach(ls -> {
           name.add(ls.getStationName());
       });
         
         return name;
    }
}
