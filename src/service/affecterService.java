package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import entity.Affecter;
import entity.HistoriqueEntity;

public class affecterService {
	public void ajouter(String codeemp, int codelieu, Date date) {
		SessionFactory factory = getFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			Affecter tmpAffect = new Affecter(codeemp,codelieu,date); 
			
			//start a transaction
			session.beginTransaction();
			
			//save affectation
			session.save(tmpAffect);
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
	}
	
	public void annuler(int id) {
		SessionFactory factory = getFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			Affecter tempAffect = session.get(Affecter.class, id);
			
			//Delete lieu
			session.delete(tempAffect);
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
	}
	
	public void modifier(int id, String codeemp, int codelieu, Date date) {
		SessionFactory factory = getFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select affection
			Affecter tempAffect = session.get(Affecter.class, id);
			
			//Modify affectation
			tempAffect.setCodememp(codeemp);
			tempAffect.setCodelieu(codelieu);
			tempAffect.setDateAffectation(date);
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
	}
	
	public Affecter selectionner(int id) {
		Affecter retAffect = null;
		SessionFactory factory = getFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select affectation
			retAffect = session.get(Affecter.class, id);
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return retAffect;
	}
	
	public List<Affecter> tous(boolean limit){
		List<Affecter> affectations = null;
		SessionFactory factory = getFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select affectation
			if(limit) {
				affectations = session.createQuery("from Affecter order by dateAffectation DESC").setMaxResults(5).list();
				Collections.reverse(affectations);
			}else {
				affectations = session.createQuery("from Affecter order by dateAffectation ASC").list();
			}
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return affectations;
	}

	private SessionFactory getFactory() {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Affecter.class)
				.buildSessionFactory();
		return factory;
	}
	
	
	public List<HistoriqueEntity> affectEmploye(String codeemp, boolean limit){
		List<Affecter> affectations = null;
		SessionFactory factory = getFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select affectation
			if(limit) { 
				affectations = session.createQuery("from Affecter where codeemp='"+codeemp+"' order by dateAffectation DESC").setMaxResults(5).list();
				Collections.reverse(affectations);
			}
			
			else
				affectations = session.createQuery("from Affecter where codeemp='"+codeemp+"' order by dateAffectation ASC").list();
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return remap(affectations);
	}
	
	public List<HistoriqueEntity> remap(List<Affecter> affectations){
		List<HistoriqueEntity> historiques = new ArrayList<HistoriqueEntity>();
		lieuService serv = new lieuService();
		String avant = "Entrée à l'entreprise";
		String apres = "";
		for(Affecter tmp : affectations) {
			HistoriqueEntity historique = new HistoriqueEntity();
			apres = (serv.selectionner(tmp.getCodelieu())).getDesignation();
			historique.setDateAffectation(tmp.getDateAffectation());
			historique.setLieuApres(apres);
			historique.setLieuAvant(avant);
			historiques.add(historique);
			avant = apres;
		}
		return historiques;
	}
}
