 package view;

import java.time.LocalDate;
import java.util.Date;

import service.affecterService;
import service.EmployeService;
import service.lieuService;

public class principal {
	public static void main(String[] args) {
		System.out.println("Bonjour");
		lieuService lieuServ=new lieuService();
		//System.out.println(lieuServ.existe("Farafanganad", "Fianarantsoa"));
		//lieuServ.ajouter("Manakara", "Fianarantsoa");
		//lieuServ.modifier(1, "Farafangana", "Fianarantsoa");
		//System.out.println(lieuServ.selectionner(1));
		//lieuServ.supprimer(2);
		//System.out.println(lieuServ.tous());
		//System.out.println(lieuServ.rechercher("Fianarantsoa"));
		
		EmployeService emplServ = new EmployeService();
		//System.out.println("matricule is:"+emplServ.ajouter("RAMINONANTENAINA", "Eloi Jospin", "PDG"));
		//System.out.println(emplServ.existe("ANDRIANIRINA", "Santatra"));
		//System.out.println(emplServ.selectionner("E002"));
		//emplServ.supprimer("E002");
		//emplServ.modifier("E003", "dfd", "dff", "CPT");
		//System.out.println(emplServ.rechercher("Santatra"));
		
		affecterService affectServ = new affecterService();
		//affectServ.ajouter("E0002", 3, new Date());
		//affectServ.annuler(2);
		//affectServ.modifier(1, "E0007", 1, new Date());
		//System.out.println(affectServ.selectionner(1));
		//System.out.println(affectServ.tous(false));
		System.out.println(affectServ.affectEmploye("E0001",true));
	}

}
