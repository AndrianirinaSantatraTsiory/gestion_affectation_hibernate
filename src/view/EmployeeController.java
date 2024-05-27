package view;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import entity.Affecter;
import entity.Employe;
import entity.Lieu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import service.EmployeService;
import service.affecterService;
import service.lieuService;

public class EmployeeController implements Initializable{
	@FXML
   private AnchorPane employe_form;

	@FXML
	private AnchorPane lieux_form;

	@FXML
	private AnchorPane affectations_form;
	
    @FXML
    private Button affectation_btn;
    
    @FXML
    private Button employe_btn;

    @FXML
    private Button lieux_btn;
    
	@FXML
    private TableView<Employe> tableEmployee;

	@FXML
	private TableView<Lieu> tableLieux;
	
	@FXML
	private TableView<Affecter> tableAffectations;
	
    @FXML
    private TableColumn<Employe, String> matricule;

    @FXML
    private TableColumn<Employe, String> nom;

    @FXML
    private TableColumn<Employe, String> prenoms;

    @FXML
    private TableColumn<Employe, String> poste;    
    
    @FXML
    private TableColumn<Lieu, String> codeLieu;

    @FXML
    private TableColumn<Lieu, String> designation;
    
    @FXML
    private TableColumn<Lieu, String> province;
    
    @FXML
    private TableColumn<Affecter, String> idAff;
    
    @FXML
    private TableColumn<Affecter, String> codeLieuAff;

    @FXML
    private TableColumn<Affecter, String> matriculeAff;
    
    @FXML
    private TableColumn<Affecter, Date> dateAff;
    
    @FXML
    private TableColumn<Employe, Void> actionsColumn;
    
    @FXML
    private TableColumn<Lieu, Void> actionsColumnLieu;
    
    @FXML
    private TableColumn<Affecter, Void> actionsColumnAff;
    
    @FXML
    private TextField barreRecherche;
    
    @FXML
    private TextField barreRechercheLieu;

    @FXML
    private TextField matrEmp;
    
    private EmployeeController propre;
    
    private boolean change= false;
    
    public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}
	
	 @FXML
	    private Button textListAff;

	@SuppressWarnings("exports")
	public EmployeService emplServ = new EmployeService();
    @SuppressWarnings("exports")
	public lieuService lieuServ=new lieuService();
    @SuppressWarnings("exports")
	public affecterService affectServ = new affecterService();
    
    @SuppressWarnings("exports")
	public ObservableList<Employe> list = FXCollections.observableList(emplServ.tous());
    @SuppressWarnings("exports")
	public ObservableList<Lieu> listeLieu = FXCollections.observableList(lieuServ.tous());
    @SuppressWarnings("exports")
	public ObservableList<Affecter> listeAff= FXCollections.observableList(affectServ.tous(change));
    
    List<Employe> allEmp=emplServ.tous();
    List<Lieu> allLieu=lieuServ.tous();
    List<Affecter> allAff=affectServ.tous(change);

    List<Employe> listEmp;  
    List<Lieu> liste_lieu;
    List<Affecter> liste_Aff;  
    
    @FXML
    void historique(ActionEvent event) {
    	setChange(!change);
    	if(!change) {    		
    		textListAff.setText("CINQ (05) DERNIÈRES AFFECTATIONS");
    	}else {
    		textListAff.setText("TOUTES LES AFFECTATIONS");
    	}
    	rafraichir();  	
    }    
    
    public void switchForm(ActionEvent event) {
    	if(event.getSource()==affectation_btn) {    		
    		affectations_form.setVisible(true);
    		employe_form.setVisible(false);
    		lieux_form.setVisible(false);
    		
    		employe_btn.setStyle("-fx-background-color: transparent");
    		affectation_btn.setStyle("-fx-background-color: #95a5a6");
    		lieux_btn.setStyle("-fx-background-color: transparent");	
    	}else if(event.getSource()==employe_btn) {    		
    		affectations_form.setVisible(false);
    		employe_form.setVisible(true);
    		lieux_form.setVisible(false);
    		
    		employe_btn.setStyle("-fx-background-color: #95a5a6");
    		affectation_btn.setStyle("-fx-background-color: transparent");
    		lieux_btn.setStyle("-fx-background-color: transparent");
    	}else if(event.getSource()==lieux_btn) {    		
    		affectations_form.setVisible(false);
    		employe_form.setVisible(false);
    		lieux_form.setVisible(true);
    		
    		employe_btn.setStyle("-fx-background-color: transparent");
    		affectation_btn.setStyle("-fx-background-color: transparent");
    		lieux_btn.setStyle("-fx-background-color: #95a5a6");	
    	}
    }
    
    @FXML
    void ajoutBouton(MouseEvent event) throws IOException {
    	FXMLLoader loader=  new FXMLLoader(getClass().getResource("AjoutEmploye.fxml"));
    	Parent parent= loader.load();
    	
		AjoutController ajEmp=  loader.getController();
		ajEmp.setMainController(this);
		
		Scene scene = new Scene(parent);
		Stage stage= new Stage();
		
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();		
    }
   
    @FXML
    void ajoutBoutonLieu(MouseEvent event) throws IOException {
    	FXMLLoader loader=  new FXMLLoader(getClass().getResource("AjoutLieu.fxml"));
    	Parent parent= loader.load();
    	
		AjoutLieuController ajLieu=  loader.getController();
		ajLieu.setMainController(this);
		
		Scene scene = new Scene(parent);
		Stage stage= new Stage();
		
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();		
    }
    
    @FXML
    void histEmp(MouseEvent event) throws IOException {
    	String mat= matrEmp.getText();
    	if(mat.isEmpty()) {
    		Alert alert= new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("Veuillez entrer le numéro de l'employé");
    		alert.showAndWait();
    	}else if(emplServ.selectionner(mat)==null) {
    		Alert alert= new Alert(Alert.AlertType.ERROR);
    		alert.setHeaderText(null);
    		alert.setContentText("L'employe n'existe pas");
    		alert.showAndWait();
    	}else {
    		FXMLLoader loader=  new FXMLLoader(getClass().getResource("Historique.fxml"));
        	Parent parent= loader.load();
        	
    		Historique hist=	loader.getController();
    		hist.setMainController(this);
    		hist.setMatrEmpl(mat);
    		
    		Scene scene = new Scene(parent);
    		Stage stage= new Stage();
    		
    		stage.setScene(scene);		
    		stage.show();
    	}	
    }
    
    @FXML
    void ajoutBoutonAff(MouseEvent event) throws IOException {
    	FXMLLoader loader=  new FXMLLoader(getClass().getResource("AjoutAffectation.fxml"));
    	Parent parent= loader.load();
    	
		AjoutAffectationController ajAff=  loader.getController();
		ajAff.setMainController(this);
		
		Scene scene = new Scene(parent);
		Stage stage= new Stage();
		
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();	
    }
    
    @Override
	public void initialize (URL url, ResourceBundle rb) {
		//Pour Employé
		matricule.setCellValueFactory(new PropertyValueFactory<Employe,String>("codeemp"));
		nom.setCellValueFactory(new PropertyValueFactory<Employe,String>("nom"));
		prenoms.setCellValueFactory(new PropertyValueFactory<Employe,String>("prenom"));
		poste.setCellValueFactory(new PropertyValueFactory<Employe,String>("poste"));
		
		//Pour Lieu
		codeLieu.setCellValueFactory(new PropertyValueFactory<Lieu,String>("codelieu"));
		designation.setCellValueFactory(new PropertyValueFactory<Lieu,String>("designation"));
		province.setCellValueFactory(new PropertyValueFactory<Lieu,String>("province"));
		
		//Pour Affectation
		idAff.setCellValueFactory(new PropertyValueFactory<Affecter,String>("idAffect"));
		matriculeAff.setCellValueFactory(new PropertyValueFactory<Affecter,String>("codememp"));
		codeLieuAff.setCellValueFactory(new PropertyValueFactory<Affecter,String>("codelieu"));
		dateAff.setCellValueFactory(new PropertyValueFactory<Affecter,Date>("dateAffectation"));
		
		actionsColumn= new TableColumn<>("Actions");
		actionsColumnLieu = new TableColumn<>("Actions");
		actionsColumnAff= new TableColumn<>("Actions");
		
		affichage(allEmp);
		affichageLieu(allLieu);
		affichageAff(allAff);
	}
	
	public void rafraichir() {
        try {
            list.clear();  
            listeLieu.clear();
            listeAff.clear();
            for(Employe tmp : emplServ.tous()) {
            	list.add(tmp);
            }      
            for(Lieu tmp : lieuServ.tous()) {
            	listeLieu.add(tmp);
            }
            for(Affecter tmp : affectServ.tous(change)) {
            	listeAff.add(tmp);
            }
            tableLieux.setItems(listeLieu);
            tableAffectations.setItems(listeAff);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
    
	public void affichage( @SuppressWarnings("exports") List<Employe> typeAffichage) {		
		listEmp = typeAffichage;
		list.clear();
		
		list = FXCollections.observableList(listEmp);
		tableEmployee.setItems(list);	
		propre=this;
	    actionsColumn.setCellFactory(createButtonCellFactory());
	    tableEmployee.getColumns().add(actionsColumn);	    
	}

	public void affichageLieu( @SuppressWarnings("exports") List<Lieu> typeAffichage) {		
		liste_lieu= typeAffichage;
		listeLieu.clear();
		
		listeLieu = FXCollections.observableList(liste_lieu);
		tableLieux.setItems(listeLieu);	
		
	    actionsColumnLieu.setCellFactory(createButtonCellFactoryLieu());
	    tableLieux.getColumns().add(actionsColumnLieu);	    
	}
	
	public void affichageAff( @SuppressWarnings("exports") List<Affecter> typeAffichage) {
		liste_Aff= typeAffichage;
		listeAff.clear();
		
		listeAff= FXCollections.observableList(liste_Aff);
		tableAffectations.setItems(listeAff);	
		
		actionsColumnAff.setCellFactory(createButtonCellFactoryAff());
		tableAffectations.getColumns().add(actionsColumnAff);	 				
	}

	private Callback<TableColumn<Employe, Void>, TableCell<Employe, Void>> createButtonCellFactory() {
	    return param -> new TableCell<>() {
	        private final Button modifierButton = new Button("Modifier");
	        private final Button supprimerButton = new Button("Supprimer");

	        {
	        	modifierButton.getStyleClass().add("modifier-button");
	            supprimerButton.getStyleClass().add("supprimer-button");
	            
	            //Modification
	            modifierButton.setOnAction(event -> {
	                Employe employe = getTableView().getItems().get(getIndex());	                
	                FXMLLoader loader=  new FXMLLoader(getClass().getResource("AjoutEmploye.fxml"));
	                Parent parent;
					try {
						parent = loader.load();
						AjoutController modif=  loader.getController();
						modif.setMainController(propre);
		                             
		                modif.setUpdate(true);
		                modif.setTextField(employe.getNom(), employe.getPrenom(), employe.getPoste());
		                modif.setCodeEmp(employe.getCodeemp());		               
		                
		                Scene scene = new Scene(parent);
		                Stage stage = new Stage();

		                stage.setScene(scene);
		                stage.initStyle(StageStyle.UNDECORATED);
		                stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}					              
	            });

	            //Suppression
	            supprimerButton.setOnAction(event -> {                
	                Employe employe = getTableView().getItems().get(getIndex());
					Alert confirmationDialog= new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");
					confirmationDialog.setContentText("Voulez-vous vraiment le supprimer?");
						
					confirmationDialog.showAndWait().ifPresent(response ->{
						if(response == ButtonType.OK) {
							emplServ.supprimer(employe.getCodeemp());
						}
					});
	                
	                try {
						rafraichir();
					} catch (Exception e) {	
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
	            });

	        }	       

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);

	            if (empty) {
	                setGraphic(null);
	            } else {
	                HBox buttons = new HBox(5); 
	                buttons.getChildren().addAll(modifierButton, supprimerButton);
	                setGraphic(buttons);
	            }
	        }
	    };
	}

	private Callback<TableColumn<Lieu, Void>, TableCell<Lieu, Void>> createButtonCellFactoryLieu() {
	    return param -> new TableCell<>() {
	        private final Button modifierButton = new Button("Modifier");
	        private final Button supprimerButton = new Button("Supprimer");

	        {
	        	modifierButton.getStyleClass().add("modifier-button");
	            supprimerButton.getStyleClass().add("supprimer-button");
	            
	            //Modification
	            modifierButton.setOnAction(event -> {
	                Lieu lieu = getTableView().getItems().get(getIndex());	                
	                FXMLLoader loader=  new FXMLLoader(getClass().getResource("AjoutLieu.fxml"));
	                Parent parent;
					try {
						parent = loader.load();
						AjoutLieuController modif=  loader.getController();
						modif.setMainController(propre);
		                             
		                modif.setUpdate(true);
		                modif.setTextField(lieu.getDesignation(), lieu.getProvince());
		                modif.setCodeLieu(lieu.getCodelieu());		               
		                
		                Scene scene = new Scene(parent);
		                Stage stage = new Stage();

		                stage.setScene(scene);
		                stage.initStyle(StageStyle.UNDECORATED);
		                stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}					              
	            });

	            //Suppression
	            supprimerButton.setOnAction(event -> {                
	                Lieu lieu = getTableView().getItems().get(getIndex());
					Alert confirmationDialog= new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");			
					confirmationDialog.setContentText("Voulez-vous vraiment le supprimer?");						
					confirmationDialog.showAndWait().ifPresent(response ->{
						if(response == ButtonType.OK) {
							lieuServ.supprimer(lieu.getCodelieu());
						}
					});
	                
	                try {
						rafraichir();
					} catch (Exception e) {	
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
	            }); 
	        }	       

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);

	            if (empty) {
	                setGraphic(null);
	            } else {
	                HBox buttons = new HBox(5); 
	                buttons.getChildren().addAll(modifierButton, supprimerButton);
	                setGraphic(buttons);
	            }
	        }
	    	};
	}
	private Callback<TableColumn<Affecter, Void>, TableCell<Affecter, Void>> createButtonCellFactoryAff() {
	    return param -> new TableCell<>() {
	        private final Button modifierButton = new Button("Modifier");
	        private final Button supprimerButton = new Button("Annuler");
	        {
	        	modifierButton.getStyleClass().add("modifier-button");
	            supprimerButton.getStyleClass().add("supprimer-button");
	            
	            //Modification
	            modifierButton.setOnAction(event -> {
	                Affecter aff = getTableView().getItems().get(getIndex());	                
	                FXMLLoader loader=  new FXMLLoader(getClass().getResource("AjoutAffectation.fxml"));
	                Parent parent;
	                lieuService lieuServ=new lieuService();
					try {
						parent = loader.load();
						AjoutAffectationController modif=  loader.getController();
						modif.setMainController(propre);
		                             
		                modif.setUpdate(true);
		                modif.setTextField(aff.getCodememp(), lieuServ.selectionner(aff.getCodelieu()),aff.getDateAffectation());
		                modif.setIdAffect(aff.getIdAffect());		               
		                
		                Scene scene = new Scene(parent);
		                Stage stage = new Stage();

		                stage.setScene(scene);
		                stage.initStyle(StageStyle.UNDECORATED);
		                stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}					              
	            });

	            //Annulation
	            supprimerButton.setOnAction(event -> {                
	                Affecter aff = getTableView().getItems().get(getIndex());
					Alert confirmationDialog= new Alert(Alert.AlertType.CONFIRMATION);
					confirmationDialog.setTitle("Confirmation");			
					confirmationDialog.setContentText("Voulez-vous vraiment annuler cette affectation?");						
					confirmationDialog.showAndWait().ifPresent(response ->{
						if(response == ButtonType.OK) {
							affectServ.annuler(aff.getIdAffect());
						}
					});
	                
	                try {
						rafraichir();
					} catch (Exception e) {	
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
	            });  
	        }	      

	        @Override
	        protected void updateItem(Void item, boolean empty) {
	            super.updateItem(item, empty);

	            if (empty) {
	                setGraphic(null);
	            } else {
	                HBox buttons = new HBox(5); 
	                buttons.getChildren().addAll(modifierButton, supprimerButton);
	                setGraphic(buttons);
	            }
	        }
	    	};
	}
	
	@FXML
	public void rechercher(MouseEvent event) {	
		 	 try {
		            list.clear();                        
		            for(Employe tmp : emplServ.rechercher(barreRecherche.getText())) {
		            	list.add(tmp);
		            }                      
		            tableEmployee.setItems(list);
		        } catch (Exception ex) {
		            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
		        }
	  }
	
	@FXML
    public void rechercherLieu(MouseEvent event) {
	 	 try {
	 		 	listeLieu.clear();                        
	            for(Lieu tmp : lieuServ.rechercher(barreRechercheLieu.getText())) {
	            	listeLieu.add(tmp);
	            } 
	            tableLieux.setItems(listeLieu);
	        } catch (Exception ex) {
	            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
	        }
    }
	
	 @FXML
	 void quitter(ActionEvent event) {
	 		System.exit(0);
	  }	
	 

}
