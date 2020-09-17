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
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
public class MainScreenController implements Initializable {

    @FXML
    private Button PartSearch;
    @FXML
    private TextField PartSearchText;
    @FXML
    private TableColumn<Part, Integer> PartID;
    @FXML
    private TableColumn<Part, String> PartName;
    @FXML
    private TableColumn<Part, Integer> PartInStock;
    @FXML
    private TableColumn<Part, Double> PartPrice;
    @FXML
    private Button PartDelete;
    @FXML
    private Button PartModify;
    @FXML
    private Button PartAdd;
    @FXML
    private Button SearchProduct;
    @FXML
    private TextField SearchProductText;
    @FXML
    private TableColumn<Product, Integer> ProductID;
    @FXML
    private TableColumn<Product, String> ProductName;
    @FXML
    private TableColumn<Product, Integer> ProductInStock;
    @FXML
    private TableColumn<Product, Double> ProductPrice;
    @FXML
    private Button ProductDelete;
    @FXML
    private Button ProductModify;
    @FXML
    private Button ProductAdd;
    @FXML
    private Button Exit;
    @FXML
    private TableView<Part> PartTable;
    @FXML
    private TableView<Product> ProductTable;

    Stage stage;
    Parent scene;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        //Set tableviews
        PartTable.setItems(Inventory.getAllParts());

        PartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductTable.setItems(Inventory.getAllProducts());

        ProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    //Exit application
    @FXML
    private void exitHandler(MouseEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Search part list
    @FXML
    private void partSearchHandler(MouseEvent event) {
        String searchString = PartSearchText.getText();
        if (searchString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a part name or part ID.");
            alert.showAndWait();
        }
        PartTable.setItems(Inventory.lookupPart(searchString));

    }

    //Delete part
    @FXML
    private void partDeleteHandler(MouseEvent event) {
        Part part = PartTable.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete.");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                Inventory.deletePart(part);
                PartTable.setItems(Inventory.getAllParts());
            }
        }
    }

    //Go to modify part screen
    @FXML
    private void partModifyHandler(MouseEvent event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyPartScreen.fxml"));
            loader.load();

            ModifyPartScreenController modifyPartController = loader.getController();
            ModifyPartScreenController.getPart(PartTable.getSelectionModel().getSelectedItem());
            modifyPartController.setPart(PartTable.getSelectionModel().getSelectedItem());

            Parent partModify = loader.getRoot();
            Scene partModifyScene = new Scene(partModify);
            Stage partModifyStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            partModifyStage.setScene(partModifyScene);
            partModifyStage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a part to modify.");
            alert.showAndWait();
        }
    }

    //Go to add part screen
    @FXML
    private void partAddHandler(MouseEvent event) throws Exception {
        Parent partAdd = FXMLLoader.load(getClass().getResource("AddPartScreen.fxml"));
        Scene partAddScene = new Scene(partAdd);
        Stage partAddStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        partAddStage.setScene(partAddScene);
        partAddStage.show();
    }

    //Search product list
    @FXML
    private void productSearchHandler(MouseEvent event) {
        String searchString = SearchProductText.getText();
        if (searchString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a product name or product ID.");
            alert.showAndWait();
        }
        ProductTable.setItems(Inventory.lookupProduct(searchString));
    }

    //Delete product
    @FXML
    private void productDeleteHandler(MouseEvent event) {
        Product product = ProductTable.getSelectionModel().getSelectedItem();

        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to delete.");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                Inventory.deleteProduct(product);
                ProductTable.setItems(Inventory.getAllProducts());
            }
        }
    }

    //Go to modify product screen
    @FXML
    private void productModifyHandler(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ModifyProductScreen.fxml"));
            loader.load();

            ModifyProductScreenController modifyProductController = loader.getController();
            ModifyProductScreenController.sendProduct(ProductTable.getSelectionModel().getSelectedItem());
            modifyProductController.setProduct(ProductTable.getSelectionModel().getSelectedItem());
            modifyProductController.currentPart(ProductTable.getSelectionModel().getSelectedItem());

            Parent productModify = loader.getRoot();
            Scene productModifyScene = new Scene(productModify);
            Stage productModifyStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            productModifyStage.setScene(productModifyScene);
            productModifyStage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a part to modify.");
            alert.showAndWait();
        }
    }

    //Go to add product screen
    @FXML
    private void productAddHandler(MouseEvent event) throws Exception {
        Parent productAdd = FXMLLoader.load(getClass().getResource("AddProductScreen.fxml"));
        Scene productAddScene = new Scene(productAdd);
        Stage productAddStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        productAddStage.setScene(productAddScene);
        productAddStage.show();
    }

}
