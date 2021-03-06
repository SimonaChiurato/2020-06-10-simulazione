/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.ComparatorOrdineAlfabetico;
import it.polito.tdp.imdb.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {
    	Actor actor= this.boxAttore.getValue();
    	if(actor==null) {
    		this.txtResult.appendText("Devi prima creare un grafo!");
    		return;
    	}
    	List<Actor> connessi= this.model.connessi(actor);
    	for(Actor a: connessi) {
    		this.txtResult.appendText(a+"\n");
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	String genere= this.boxGenere.getValue();
    	this.model.creaGrafo(genere);
    	txtResult.appendText("Arco creato con # vertici: "+this.model.vertici().size()+" # archi: "+this.model.archi().size()+"\n");
    	List<Actor> vertici= new ArrayList<>(this.model.vertici());
    	Collections.sort(vertici, new ComparatorOrdineAlfabetico());
    	this.boxAttore.getItems().addAll(vertici);
    
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	this.txtResult.clear();
    	String input = this.txtGiorni.getText();
		int giorni=0;
		if (!input.matches("[0-9]+")) {
			txtResult.appendText("Devi inserire un valore numerico intero");
			return;
		}else {
			giorni= Integer.parseInt(input);
		}
		this.model.simula(giorni);
		txtResult.appendText("Attori intervistati in "+giorni+":\n");
		for(Actor a: this.model.intervistati()) {
			this.txtResult.appendText(a+"\n");
		}
		txtResult.appendText("Giorni di pausa presi: "+this.model.giorniPausa());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxGenere.getItems().addAll(this.model.listAllGeneri());
    }
}
