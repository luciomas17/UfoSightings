/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.ufo.model.AnnoAvvistamenti;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {
	
	Model model = new Model();

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<AnnoAvvistamenti> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleAnalizza(ActionEvent event) {
    	txtResult.clear();
    	
    	if(model.getVertexList().isEmpty()) {
    		txtResult.appendText("Analizzare un anno.\n");
    		return;
    	}
    	
    	if(boxStato.getSelectionModel().isEmpty()) {
    		txtResult.appendText("Selezionare uno stato.\n");
    		return;
    	}

    	String stato = boxStato.getSelectionModel().getSelectedItem();
    	
    	List<String> predecessori = model.getPredecessori(stato.toLowerCase());
    	List<String> successori = model.getSuccessori(stato.toLowerCase());
    	List<String> raggiungibili = model.getRaggiungibili(stato.toLowerCase());
    	
    	txtResult.appendText("Stati predecessori di " + stato + ":\n");
    	for(String p : predecessori)
    		txtResult.appendText(p.toUpperCase() + "\n");
    	txtResult.appendText("\nStati successori di " + stato + ":\n");
    	for(String s : successori)
    		txtResult.appendText(s.toUpperCase() + "\n");
    	txtResult.appendText("\nStati raggiungibili da " + stato + ":\n");
    	for(String r : raggiungibili)
    		txtResult.appendText(r.toUpperCase() + "\n");
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	txtResult.clear();
    	
    	if(boxAnno.getSelectionModel().isEmpty()) {
    		txtResult.appendText("Selezionare un anno.\n");
    		return;
    	}

    	int anno = boxAnno.getSelectionModel().getSelectedItem().getAnno();
    	
    	model.creaGrafo(anno);
    	List<String> vertici = model.getVertexList();
    	txtResult.appendText(vertici.size() + " stati trovati.");
    	
    	addItemsToBoxStato(vertici);
    }

	@FXML
    void handleSequenza(ActionEvent event) {
		txtResult.clear();
		
		if(model.getVertexList().isEmpty()) {
    		txtResult.appendText("Analizzare un anno.\n");
    		return;
    	}
    	
    	if(boxStato.getSelectionModel().isEmpty()) {
    		txtResult.appendText("Selezionare uno stato.\n");
    		return;
    	}
    	
    	String stato = boxStato.getSelectionModel().getSelectedItem();
    	
    	txtResult.appendText("Sequenza migliore a partire da: " + stato + "\n");
    	List<String> cammino = model.getCammino(stato.toLowerCase());
    	for(String s : cammino)
    		txtResult.appendText(s.toUpperCase() + "\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	addItemsToBoxAnno();
    }
    
    public void addItemsToBoxAnno() {
    	List<AnnoAvvistamenti> temp = model.getAnniEAvvistamenti();
    	
    	boxAnno.getItems().addAll(temp);
    }
    
    private void addItemsToBoxStato(List<String> vertici) {
    	boxStato.getItems().clear();
    	for(String v : vertici)
    		boxStato.getItems().add(v.toUpperCase());
	}
}
