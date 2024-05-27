package view;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entity.Employe;
import entity.Lieu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.EmployeService;
import service.affecterService;
import service.lieuService;

public class AjoutAffectationController implements Initializable{
	
	private int idAffect;
	
	@FXML
	private TextField matriculeInp;
	
    @FXML
    private ComboBox<Lieu> lieu_dispo;
    
    @FXML
    private DatePicker date_affectation  ;
      
	private EmployeeController mainController;	
	
	private boolean update;
	
	
	public boolean isUpdate() {
		return update;
	}

	affecterService affectServ = new affecterService();    
	EmployeService emplServ = new EmployeService();
	
	private boolean isExiste(String matricule) {
		for(Employe tmp : emplServ.tous()) {
			if(matricule.equals(tmp.getCodeemp())) {
				return true;
			}
		}
		return false;
	}
	@FXML
    private void annuler(MouseEvent event) {
    	Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    private void valider(MouseEvent event) {
    	String matricule= matriculeInp.getText();
    	//System.out.println("eto mory e");
    	if(matricule.isEmpty() || lieu_dispo.getValue()==null || date_affectation.getValue()==null){
    		Alert alert= new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("formulaire incomplet");
    		alert.showAndWait();
    	}else if(!isExiste(matricule)){
    		Alert alert= new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("L'employé avec cette matricule n'existe pas");
    		alert.showAndWait();
    	}
    	else {
    		int lieu= lieu_dispo.getValue().getCodelieu();
        	LocalDate localDate= date_affectation.getValue();
        	Date date= java.sql.Date.valueOf(localDate);     	
        	String message = "";
    			if(update) {
    				affectServ.modifier(idAffect, matricule, lieu,date);
    				message="Affectation modifiée";
    			}else {
    				affectServ.ajouter(matricule, lieu, date);
    				message="Une nouvelle affectation qui se présente";
    			}
    			Alert alert= new Alert(Alert.AlertType.INFORMATION);
    			alert.setHeaderText(null);
    			alert.setContentText(message);
    			alert.showAndWait();
    			
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lieuService lieuServ=new lieuService();
		List<Lieu> tousLesLieux = lieuServ.tous();
		tousLesLieux.sort(Comparator.comparing(Lieu::getDesignation));
		
		 lieu_dispo.setCellFactory(new Callback<ListView<Lieu>, ListCell<Lieu>>() {
		        public ListCell<Lieu> call(ListView<Lieu> param) {
		            return new ListCell<Lieu>() {
		                @Override
		                protected void updateItem(Lieu item, boolean empty) {
		                    super.updateItem(item, empty);
		                    if (item == null || empty) {
		                        setText(null);
		                    } else {		                       
		                        setText(item.getDesignation());
		                    }
		                }
		            };
		        }
		  });

		lieu_dispo.getItems().clear();
		lieu_dispo.getItems().addAll(tousLesLieux);
	}

	public EmployeeController getMainController() {
		return mainController;
	}

	public void setMainController(EmployeeController mainController) {
		this.mainController = mainController;
	}

	public void setUpdate(boolean b) {
		this.update = b;
	}

	public void setTextField(String codememp, @SuppressWarnings("exports") Lieu lieu, Date dateAffectation) {
		matriculeInp.setText(codememp);
        lieu_dispo.setValue(lieu);
        date_affectation.setValue(((java.sql.Date) dateAffectation).toLocalDate());
	}

	public int getIdAffect() {
		return idAffect;
	}

	public void setIdAffect(int idAffect) {
		this.idAffect = idAffect;
	}

}
