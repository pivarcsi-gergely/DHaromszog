package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Label LabelKerulet;
    public Label LabelTerulet;
    private List<DHaromszog> haromszogLista;

    @FXML
    public Button buttonAdatokBetoltese;
    @FXML
    public ListView<DHaromszog> listViewDerekszoguHaromszogek;
    @FXML
    public ListView<String> listViewHibak;

    @FXML
    public void onButtonAdatokBetolteseClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Szöveges állomány megnyitása");
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Szöveges állományok (*.txt)", "*.txt");
        FileChooser.ExtensionFilter extensionFilter2 = new FileChooser.ExtensionFilter("Any", "*");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.getExtensionFilters().add(extensionFilter2);
        File fajl = fileChooser.showOpenDialog(null);
        fajlBeolvas(fajl.toString());
    }

    private void fajlBeolvas(String fajl) {
        haromszogLista = new ArrayList<>();
        listViewHibak.getItems().clear();
        listViewDerekszoguHaromszogek.getItems().clear();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fajl));
            String sor = br.readLine();
            int i = 1;
            while (sor != null) {
                try {
                    DHaromszog dh = new DHaromszog(sor, i++);
                    listViewDerekszoguHaromszogek.getItems().add(dh);
                }
                catch (Exception e) {
                    listViewHibak.getItems().add(e.getMessage());
                }
                finally {
                    sor = br.readLine();
                }
            }
            br.close();
        }
        catch (IOException IOe){
            System.err.println(IOe.getMessage());
        }
    }

    public void onListViewClick(MouseEvent mouseEvent) {
        DHaromszog dh = listViewDerekszoguHaromszogek.getSelectionModel().getSelectedItem();
        LabelKerulet.setText(String.format("Kerület = %.2f", dh.getKerulet()));
        LabelTerulet.setText(String.format("Terület = %.2f", dh.getTerulet()));
    }
}
