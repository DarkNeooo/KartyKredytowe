package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Controller {

    @FXML
    public TextField cardNumberField;

    @FXML
    public Label fileNameLabel;

    @FXML
    public Label confirmNumberLabel;

    @FXML
    public ListView validNumbersList;

    private ObservableList<String> validNumbers;

    private FileChooser fileChooser;

    private List<String> numbersFromFile;

    public void initialize(){

        validNumbers = FXCollections.observableArrayList();
        validNumbersList.setItems(validNumbers);

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki tekstowe *.txt","*txt"));
        fileChooser.setTitle("Znajdź plik");

        cardNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(cardNumberField.getTooltip() != null){
                cardNumberField.getStyleClass().remove("error");
                cardNumberField.setTooltip(null);
            }

            if(confirmNumberLabel.isVisible()){
                confirmNumberLabel.setVisible(false);
                if(confirmNumberLabel.getStyleClass().toString().equals("valid")) {
                    confirmNumberLabel.getStyleClass().remove("valid");
                }else{
                    confirmNumberLabel.getStyleClass().remove("invalid");
                }
            }
        });
    }

    @FXML
    public void checkNumberBtnReleased(MouseEvent mouseEvent) {

        validate();

    }

    @FXML
    public void chooseFileBtnReleased(MouseEvent mouseEvent){
        File file = fileChooser.showOpenDialog(fileNameLabel.getScene().getWindow());
        try {
            if(file != null) {
                numbersFromFile = Files.readAllLines(Paths.get(file.toURI()));
                fileNameLabel.setText(file.getName());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void checkFileBtnReleased(MouseEvent mouseEvent){
        for (String item : numbersFromFile ) {
            if(Validator.numberPreValidation(item)) {
                if(Validator.numberValidation(item)){
                    validNumbers.add(item);
                }
            }
        }

    }

    @FXML
    public void onEnter(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            validate();
        }
    }

    private void validate() {

        if(Validator.numberPreValidation(cardNumberField.getText())){
            if(Validator.numberValidation(cardNumberField.getText())){
                confirmNumberLabel.getStyleClass().add("valid");
                confirmNumberLabel.setText("Numer karty jest poprawny!");
                confirmNumberLabel.setVisible(true);

                validNumbers.add(cardNumberField.getText());
            }else{
                confirmNumberLabel.getStyleClass().add("invalid");
                confirmNumberLabel.setText("Numer karty jest niepoprawny!");
                confirmNumberLabel.setVisible(true);
            }
        }else{
            cardNumberField.getStyleClass().add("error");
            cardNumberField.setTooltip(new Tooltip("Numer karty powinien składać się z 16 cyfr i nie może zaczynać się od 0"));
        }

    }
}
