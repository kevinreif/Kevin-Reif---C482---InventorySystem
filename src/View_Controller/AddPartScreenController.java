/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author butte
 */
public class AddPartScreenController implements Initializable {

    @FXML
    private RadioButton AddPartInHouse;
    @FXML
    private RadioButton AddPartOutsourced;
    @FXML
    private TextField AddPartNameField;
    @FXML
    private TextField AddPartInvField;
    @FXML
    private TextField AddPartPriceField;
    @FXML
    private TextField AddPartMaxField;
    @FXML
    private Button AddPartSave;
    @FXML
    private Button AddPartCancel;
    @FXML
    private TextField AddPartMinField;
    @FXML
    private Label AddPartMachineID;
    @FXML
    private TextField AddPartMachineIDField;
    @FXML
    private ToggleGroup AddPart;

    private boolean isInHouse;

    private int partID;

    private String errorMessage = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //Save new part
    @FXML
    private void saveHandler(MouseEvent event) throws IOException {
        if (!Inventory.getAllParts().isEmpty()){
             partID = Inventory.getAllParts().get(Inventory.getAllParts().size() - 1).getId();
        } else {
            partID = 0;
        }
        
        try {
            int id = partID + 1;
            String name = AddPartNameField.getText();
            double price = Double.valueOf(AddPartPriceField.getText());
            int inv = Integer.valueOf(AddPartInvField.getText());
            int min = Integer.valueOf(AddPartMinField.getText());
            int max = Integer.valueOf(AddPartMaxField.getText());

            errorMessage = Part.validation(name, price, inv, min, max, errorMessage);
            if (!errorMessage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
                //alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {

                if (AddPartOutsourced.isSelected()) {
                    String companyName = AddPartMachineIDField.getText();
                    Inventory.addPart(new OutsourcedPart(id, name, price, inv, min, max, companyName));
                } else if (AddPartInHouse.isSelected()) {
                    int machinePart = Integer.valueOf(AddPartMachineIDField.getText());
                    Inventory.addPart(new InhousePart(id, name, price, inv, min, max, machinePart));
                }

                Parent save = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene saveScene = new Scene(save);
                Stage saveStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                saveStage.setScene(saveScene);
                saveStage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part cannot be added. Please enter a valid value for each text field!");
            alert.showAndWait();

        }

    }

    //cancel add part
    @FXML
    private void cancelHandler(MouseEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part will not be added. Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent cancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene cancelScene = new Scene(cancel);
            Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelStage.setScene(cancelScene);
            cancelStage.show();
        }
    }

    @FXML
    private void addPartInHouseHandler(MouseEvent event) {
        isInHouse = true;
        AddPartMachineID.setText("Machine ID");
        AddPartMachineIDField.setPromptText("Machine ID");
    }

    @FXML
    private void addPartOutsourcedHandler(MouseEvent event) {
        isInHouse = false;
        AddPartMachineID.setText("Company Name");
        AddPartMachineIDField.setPromptText("Company Name");
    }
    
    

}
