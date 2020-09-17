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
import javafx.event.ActionEvent;
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
public class ModifyPartScreenController implements Initializable {

    @FXML
    private RadioButton ModifyPartInHouse;
    @FXML
    private RadioButton ModifyPartOutsourced;
    @FXML
    private Label ModifyPartMachineID;
    @FXML
    private TextField ModifyPartIDField;
    @FXML
    private TextField ModifyPartNameField;
    @FXML
    private TextField ModifyPartInvField;
    @FXML
    private TextField ModifyPartPriceField;
    @FXML
    private TextField ModifyPartMaxField;
    @FXML
    private Button ModifyPartSave;
    @FXML
    private TextField ModifyPartMinField;
    @FXML
    private TextField ModifyPartMachineIDField;
    @FXML
    private ToggleGroup modifyPart;
    @FXML
    private Button ModifyPartCancel;

    private boolean isInHouse;

    private static Part part;

    //get selected part
    public static void getPart(Part selectedPart) {
        part = selectedPart;
    }

    private String errorMessage = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void modifyPartInHouseHandler(MouseEvent event) {
        isInHouse = true;
        ModifyPartMachineID.setText("Machine ID");
    }

    @FXML
    private void modifyPartOutsourcedHandler(MouseEvent event) {
        isInHouse = false;
        ModifyPartMachineID.setText("Company Name");
    }

    //Modify part
    @FXML
    private void saveHandler(MouseEvent event) throws IOException {
        try {
            int index = Inventory.getAllParts().indexOf(part);
            int id = Integer.valueOf(ModifyPartIDField.getText());
            String name = ModifyPartNameField.getText();
            int inv = Integer.valueOf(ModifyPartInvField.getText());
            double price = Double.valueOf(ModifyPartPriceField.getText());
            int max = Integer.valueOf(ModifyPartMaxField.getText());
            int min = Integer.valueOf(ModifyPartMinField.getText());

            errorMessage = Part.validation(name, price, inv, min, max, errorMessage);
            if (!errorMessage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
                alert.showAndWait();
                errorMessage = "";

            } else {
                if (!isInHouse) {
                    String companyName = ModifyPartMachineIDField.getText();
                    OutsourcedPart opart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                    Inventory.updatePart(index, opart);

                } else if (isInHouse) {
                    int machinePart = Integer.valueOf(ModifyPartMachineIDField.getText());
                    InhousePart ipart = new InhousePart(id, name, price, inv, min, max, machinePart);
                    Inventory.updatePart(index, ipart);

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

    //Cancel modify part
    @FXML
    private void cancelHandler(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Part will not be modified. Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent cancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene cancelScene = new Scene(cancel);
            Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelStage.setScene(cancelScene);
            cancelStage.show();
        }
    }

    //Displays selected part information
    public void setPart(Part part) {

        ModifyPartIDField.setText(String.valueOf(part.getId()));
        ModifyPartNameField.setText(part.getName());
        ModifyPartInvField.setText(String.valueOf(part.getStock()));
        ModifyPartPriceField.setText(String.valueOf(part.getPrice()));
        ModifyPartMaxField.setText(String.valueOf(part.getMax()));
        ModifyPartMinField.setText(String.valueOf(part.getMin()));

        if (part instanceof InhousePart) {
            isInHouse = true;
            ModifyPartInHouse.setSelected(true);
            ModifyPartMachineID.setText("Machine ID");
            ModifyPartMachineIDField.setText(String.valueOf(((InhousePart) part).getMachineID()));
        }

        if (part instanceof OutsourcedPart) {
            isInHouse = false;
            ModifyPartOutsourced.setSelected(true);
            ModifyPartMachineID.setText("Company Name");
            ModifyPartMachineIDField.setText(((OutsourcedPart) part).getCompanyName());
        }

    }

}
