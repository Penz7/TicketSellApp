/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ddd.ticketsellaqpp;

import com.ddd.pojo.Staff;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;



/**
 *
 * @author admin
 */
public class StaffManagementController {

    private static Staff currentStaff;
   @FXML
   private Button btnBack;
   
   @FXML
   private void backMenu() {
        try {
            App.setRoot("home-admin");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    
}
