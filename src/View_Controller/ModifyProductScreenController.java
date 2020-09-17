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
public class ModifyProductScreenController implements Initializable {

    @FXML
    private TextField ModifyProductIDField;
    @FXML
    private TextField ModifyProductNameField;
    @FXML
    private TextField ModifyProductInvField;
    @FXML
    private TextField ModifyProductPriceField;
    @FXML
    private TextField ModifyProductMaxField;
    @FXML
    private TextField ModifyProductMinField;
    @FXML
    private TableColumn<Part, Integer> ModifyProductPartIDAdd;
    @FXML
    private TableColumn<Part, String> ModifyProductPartNameAdd;
    @FXML
    private TableColumn<Part, Integer> ModifyProductInventoryLevelAdd;
    @FXML
    private TableColumn<Part, Double> ModifyProductPriceperUnitAdd;
    @FXML
    private TableColumn<Part, Integer> ModifyProductPartIDDelete;
    @FXML
    private TableColumn<Part, String> ModifyProductPartNameDelete;
    @FXML
    private TableColumn<Part, Integer> ModifyProductInventoryLevelDelete;
    @FXML
    private TableColumn<Part, Double> ModifyProductPriceperUnitDelete;
    @FXML
    private Button ModifyProductAdd;
    @FXML
    private Button ModifyProductSearch;
    @FXML
    private TextField ModifyProductSearchField;
    @FXML
    private Button ModifyProductDelete;
    @FXML
    private Button ModifyProductCancel;
    @FXML
    private Button ModifyProductSave;
    @FXML
    private TableView<Part> PartAddTable;
    @FXML
    private TableView<Part> PartDeleteTable;

    private static Product selectedProduct = null;

    //gets selected product
    public static void sendProduct(Product product) {
        selectedProduct = product;
    }

    private ObservableList<Part> currentParts = FXCollections.observableArrayList();

    private String errorMessage = "";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Sets tables
        PartAddTable.setItems(Inventory.getAllParts());

        ModifyProductPartIDAdd.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartNameAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductInventoryLevelAdd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPriceperUnitAdd.setCellValueFactory(new PropertyValueFactory<>("price"));

        PartDeleteTable.setItems(currentParts);

        ModifyProductPartIDDelete.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartNameDelete.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductInventoryLevelDelete.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPriceperUnitDelete.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    //Add parts to product
    @FXML
    private void addHandler(MouseEvent event) {
        Part part = PartAddTable.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        PartDeleteTable.setItems(currentParts);
    }

    //Search part list
    @FXML
    private void searchHandler(MouseEvent event) {
        String searchString = ModifyProductSearchField.getText();
        if (searchString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a part name or part ID.");
            alert.showAndWait();
        }
        PartAddTable.setItems(Inventory.lookupPart(searchString));
    }

    //Remove part from product
    @FXML
    private void deleteHandler(MouseEvent event) {
        Part part = PartDeleteTable.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to remove.");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part from the product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                currentParts.remove(part);
                PartDeleteTable.setItems(currentParts);
            }
        }
    }

    //Cancel modify product
    @FXML
    private void cancelHandler(MouseEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Product will not be modifed. Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            Parent cancel = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene cancelScene = new Scene(cancel);
            Stage cancelStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelStage.setScene(cancelScene);
            cancelStage.show();
        }
    }

    //Save modify product
    @FXML
    private void saveHandler(MouseEvent event) throws IOException {
        try {
            int index = Inventory.getAllProducts().indexOf(selectedProduct);
            int id = Integer.valueOf(ModifyProductIDField.getText());
            String name = ModifyProductNameField.getText();
            int inv = Integer.valueOf(ModifyProductInvField.getText());
            double price = Double.valueOf(ModifyProductPriceField.getText());
            int max = Integer.valueOf(ModifyProductMaxField.getText());
            int min = Integer.valueOf(ModifyProductMinField.getText());

            errorMessage = Product.validation(name, price, inv, min, max, errorMessage, currentParts);
            if (!errorMessage.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {

                Product newProduct = new Product(id, name, price, inv, min, max);
                Inventory.updateProduct(index, newProduct);

                for (Part part : currentParts) {
                    newProduct.addAssociatedPart(part);
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

    //Displays information of selected product
    public void setProduct(Product product) {

        ModifyProductIDField.setText(String.valueOf(product.getId()));
        ModifyProductNameField.setText(product.getName());
        ModifyProductInvField.setText(String.valueOf(product.getStock()));
        ModifyProductPriceField.setText(String.valueOf(product.getPrice()));
        ModifyProductMaxField.setText(String.valueOf(product.getMax()));
        ModifyProductMinField.setText(String.valueOf(product.getMin()));
    }

    //builds temporary list part add
    public void currentPart(Product product) {
        for (Part part : product.getAllAssociatedParts()) {
            currentParts.add(part);
        }
    }

}
