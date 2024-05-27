package view;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import entity.Affecter;
import entity.HistoriqueEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.affecterService;

public class Historique implements Initializable{
	
    @FXML
    private TableColumn<HistoriqueEntity, String> apresH;

    @FXML
    private TableColumn<HistoriqueEntity, String> avantH;

    @FXML
    private TableColumn<HistoriqueEntity, Date> dateH;

    @FXML
    private TableView<HistoriqueEntity> tableHisto;

	private EmployeeController mainController;
	
	public EmployeeController getMainController() {
		return mainController;
	}

	public void setMainController(EmployeeController mainController) {
		this.mainController = mainController;
	}
	
	private String matrEmpl;
	
	@SuppressWarnings("exports")
	public affecterService affectServ = new affecterService();
	@SuppressWarnings("exports")
	public ObservableList<HistoriqueEntity> historiqueEmpl= FXCollections.observableList(affectServ.affectEmploye(matrEmpl,true));
	
	List<HistoriqueEntity> listHisto;  
	List<HistoriqueEntity> affTab= affectServ.affectEmploye(matrEmpl,true);
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		avantH.setCellValueFactory(new PropertyValueFactory<HistoriqueEntity,String>("lieuAvant"));
		apresH.setCellValueFactory(new PropertyValueFactory<HistoriqueEntity,String>("lieuApres"));
		dateH.setCellValueFactory(new PropertyValueFactory<HistoriqueEntity,Date>("dateAffectation"));	
				
		affichageTab(affTab);
	}

	private void affichageTab(List<HistoriqueEntity> affiche) {
		listHisto= affiche;
		historiqueEmpl.clear();
		
		historiqueEmpl= FXCollections.observableList(listHisto);
		tableHisto.setItems(historiqueEmpl);	
		tableHisto.getColumns();		
	}

	public String getMatrEmpl() {
		return matrEmpl;
	}

	public void setMatrEmpl(String matrEmpl) {
		this.matrEmpl = matrEmpl;
		affTab = affectServ.affectEmploye(matrEmpl, true);
	    affichageTab(affTab);
	}

}
