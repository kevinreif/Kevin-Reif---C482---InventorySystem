/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author butte
 */
public class AddProductScreenController implements Initializable {

    @FXML
    private TextField AddProductIDField;
    @FXML
    private TextField AddProductNameField;
    @FXML
    private TextField AddProductInvField;
    @FXML
    private TextField AddProductPriceField;
    @FXML
    private TextField AddProductMaxField;
    @FXML
    private TextField AddProductMinField;
    @FXML
    private TableColumn<Part, Integer> AddProductPartIDAdd;
    @FXML
    private TableColumn<Part, String> AddProductPartNameAdd;
    @FXML
    private TableColumn<Part, Integer> AddProductInventoryLevelAdd;
    @FXML
    private TableColumn<Part, Double> AddProductPriceperUnitAdd;
    @FXML
    private TableColumn<Part, Integer> AddProductPartIDDelete;
    @FXML
    private TableColumn<Part, String> AddProductPartNameDelete;
    @FXML
    private TableColumn<Part, Integer> AddProductInventoryLevelDelete;
    @FXML
    private TableColumn<Part, Double> AddProductPriceperUnitDelete;
    @FXML
    private Button AddProductAdd;
    @FXML
    private Button AddProductSearch;
    @FXML
    private TextField AddProductSearchField;
    @FXML
    private Button AddProductDelete;
    @FXML
    private Button AddProductCancel;
    @FXML
    private Button AddProductSave;
    @FXML
    private TableView<Part> AddPartTable;
    @FXML
    private TableView<Part> DeletePartTable;

    private ObservableList<Part> tempList = FXCollections.observableArrayList();

    private int productID;

    private String errorMessage = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Setting tables
        AddPartTable.setItems(Inventory.getAllParts());

        AddProductPartIDAdd.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductPartNameAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInventoryLevelAdd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceperUnitAdd.setCellValueFactory(new PropertyValueFactory<>("price"));

        DeletePartTable.setItems(tempList);

        AddProductPartIDDelete.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductPartNameDelete.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductInventoryLevelDelete.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductPriceperUnitDelete.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    //Add part to product
    @FXML
    private void addHandler(MouseEvent event) {
        Part part = AddPartTable.getSelectionModel().getSelectedItem();
        tempList.add(part);
        DeletePartTable.setItems(tempList);
    }

    //Search for part from part list
    @FXML
    private void searchHandler(MouseEvent event) {
        String searchString = AddProductSearchField.getText();
        if (searchString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a part name or part ID.");
            alert.showAndWait();
        }
        AddPartTable.setItems(Inventory.lookupPart(searchString));
    }

    //Remove part from product
    @FXML
    private void deleteHandler(MouseEvent event) {        
        Part part = DeletePartTable.getSelectionModel().getSelectedItem();
       
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to remove.");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part from the product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                tempList.remove(part);
                DeletePartTable.setItems(tempList);
            }
        }
    }

    //Cancel adding product
    @FXML
    private void cancelHandler(MouseEvent event) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product will not be added. Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent cancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene cancelScene = new Scene(cancel);
            Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelStage.setScene(cancelScene);
            cancelStage.show();
        }
    }

    //Add new product
    @FXML
    private void saveHandler(MouseEvent event) throws IOException {
        if (!Inventory.getAllProducts().isEmpty()){
             productID = Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).getId();
        } else {
            productID = 0;
        }
        
        try {
            int id = productID + 1;
            String name = AddProductNameField.getText();
            double price = Double.valueOf(AddProductPriceField.getText());
            int inv = Integer.valueOf(AddProductInvField.getText());
            int min = Integer.valueOf(AddProductMinField.getText());
            int max = Integer.valueOf(AddProductMaxField.getText());

            errorMessage = Product.validation(name, price, inv, min, max, errorMessage, tempList);
            if (!errorMessage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {

                Inventory.addProduct(new Product(id, name, price, inv, min, max));

                for (Part part : tempList) {
                    Inventory.getAllProducts().get(Inventory.getAllProducts().size() - 1).addAssociatedPart(part);
                }

                
                Parent save = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene saveScene = new Scene(save);
                Stage saveStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                saveStage.setScene(saveScene);
                saveStage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product cannot be added. Please enter a valid value for each text field!");
            alert.showAndWait();

        }

    }
}
