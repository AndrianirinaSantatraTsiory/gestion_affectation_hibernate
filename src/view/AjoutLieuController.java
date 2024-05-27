package view;

import java.net.URL;
import java.util.ResourceBundle;
import entity.Lieu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import service.lieuService;

public class AjoutLieuController implements Initializable{
	private int codeLieu;

    @FXML
    private TextField designation;

    @FXML
    private TextField province;
      
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

    @SuppressWarnings("unlikely-arg-type")
	@FXML
    private void valider(MouseEvent event) {
    	String designationText= designation.getText();
    	String provinceText= province.getText();
    	
    	String message = "";
    	boolean[] okay= {true};
    	if(designationText.isEmpty() || provinceText.isEmpty()) {
    		Alert alert= new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("formulaire incomplet");
    		alert.showAndWait();
    	}else {    		
    		lieuService lieuServ=new lieuService();
    		Lieu currLieu= lieuServ.selectionner(codeLieu); 
    		
    		if(lieuServ.existe(designationText, provinceText)) {
				if(!update||(update && !designationText.equals(currLieu.getCodelieu()) && !provinceText.equals(currLieu.getProvince()))) {
					Alert confirmationDialog= new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");
					confirmationDialog.setHeaderText("Un lieu possèdant le même nom et prénom existe dejà.");
					confirmationDialog.setContentText("Voulez-vous quand même continuer?");
					
					confirmationDialog.showAndWait().ifPresent(response ->{
						if(response != ButtonType.OK) {
							okay[0]=false;
						}
					});
				}
			}
    		if(okay[0]) {
    			if(update) {
        			lieuServ.modifier(codeLieu, designationText, provinceText);
            		message="Lieu bien modifié";
        			
        		}else {
        			lieuServ.ajouter(designationText,provinceText);
        			message="Nouveau lieu ajouté avec succès";
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
    
   public int getCodeLieu() {
		return codeLieu;
	}

	public void setCodeLieu(int codeLieu) {
		this.codeLieu = codeLieu;
	}

	void setTextField(String Designation, String Province) {        
        designation.setText(Designation);
        province.setText(Province);       
    }
	
    void setUpdate(boolean b) {
        this.update = b;
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}
