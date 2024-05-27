package view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entity.Employe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import service.EmployeService;
import  view.EmployeeController;

public class AjoutController implements Initializable{
	private String codeEmp;

    @FXML
    private TextField nom;

    @FXML
    private TextField poste;

    @FXML
    private TextField prenoms;

	private boolean update;
	
	private EmployeeController mainController;

	
    public EmployeeController getMainController() {
		return mainController;
	}

	public void setMainController(EmployeeController mainController) {
		this.mainController = mainController;
	}

	@FXML
    private void annuler(MouseEvent event) {
    	Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    private void valider(MouseEvent event) {
    	String nomText= nom.getText();
    	String prenomsText= prenoms.getText();
    	String posteText= poste.getText();
    	
    	String message = "";
    	boolean[] okay= {true};
    	if(nomText.isEmpty() || prenomsText.isEmpty() || posteText.isEmpty()) {
    		Alert alert= new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("formulaire incomplet");
    		alert.showAndWait();
    	}else {
    		EmployeService servEmpl = new EmployeService();
    		Employe currEmploye = servEmpl.selectionner(codeEmp); 
    		
    		if(servEmpl.existe(nomText, prenomsText)) {
				if(!update||(update&&!nomText.equals(currEmploye.getNom())&&!prenomsText.equals(currEmploye.getPrenom()))) {
					Alert confirmationDialog= new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");
					confirmationDialog.setHeaderText("Un employé possèdant le même nom et prénom existe dejà.");
					confirmationDialog.setContentText("Voulez-vous quand meme continuer?");
					
					confirmationDialog.showAndWait().ifPresent(response ->{
						if(response != ButtonType.OK) {
							okay[0]=false;
						}
					});
				}
			}
    		if(okay[0]) {
    			if(update) {
        			servEmpl.modifier(codeEmp, nomText, prenomsText, posteText);
            		message="Employé bien modifié";
        			
        		}else {
        			servEmpl.ajouter(nomText,prenomsText,posteText);
        			message="Nouvel employé ajouté avec succès";
        		}
        		Alert alert= new Alert(Alert.AlertType.INFORMATION);
        		alert.setHeaderText(null);
        		alert.setContentText(message);
        		alert.showAndWait();
    		}    		
    		
    		Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        	stage.close();
        	@SuppressWarnings("unused")
			EmployeeController e = new EmployeeController();
        	try {
        		mainController.rafraichir();
        	} catch (Exception e1) {
        		e1.printStackTrace();
        	}
    	}
    }
    
    
    public String getCodeEmp() {
		return codeEmp;
	}

	public void setCodeEmp(String codeEmp) {
		this.codeEmp = codeEmp;
	}

	void setTextField(String Nom, String Prenoms, String Poste) {        
        nom.setText(Nom);
        prenoms.setText(Prenoms);
        poste.setText(Poste);
    }

    void setUpdate(boolean b) {
        this.update = b;
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
