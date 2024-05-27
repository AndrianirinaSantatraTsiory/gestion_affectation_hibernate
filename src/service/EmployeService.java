package service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entity.Affecter;
import entity.Employe;
import entity.Lieu;

public class EmployeService {
	public String ajouter(String nom, String prenom, String poste) {
		String matr="";
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			matr=generateMatricule();
			Employe tmpEmpl = new Employe(matr,nom, prenom, poste);
			
			//start a transaction
			session.beginTransaction();
			
			//save lieu
			session.save(tmpEmpl);
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return matr;
	}
	
	public void supprimer(String code) {
		SessionFactory factory = getSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select employe
			Employe tempEmploye = session.get(Employe.class, code);
			
			List<Affecter> affectations = session.createQuery("from Affecter where codeemp='"+code+"' order by dateAffectation ASC").list();
			
			for(Affecter tmp : affectations) {
				session.delete(tmp);
			}
			
			//Delete lieu
			session.delete(tempEmploye);
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
	}
	
	public void modifier(String code, String nom, String prenom, String poste) {
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select employe
			Employe tempEmploye = session.get(Employe.class, code);
			
			//Modify employe
			tempEmploye.setNom(nom);
			tempEmploye.setPrenom(prenom);
			tempEmploye.setPoste(poste);
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
	}
	
	public Employe selectionner(String code) {
		Employe retEmploye = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			retEmploye = session.get(Employe.class, code);
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return retEmploye;
	}
	
	public List<Employe> tous(){
		List<Employe> employes = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			employes = session.createQuery("from Employe").list();
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return employes;
	}
	
	public List<Employe> rechercher(String keyword){
		List<Employe> employes = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			employes = session.createQuery("from Employe where codeemp LIKE '%"+keyword+"%' or nom LIKE '%"+keyword+"%' or prenom LIKE '%"+keyword+"%'").list();
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return employes;
	}
	
	
	public boolean existe(String nom, String prenom) {
		List<Lieu> employes = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			employes = session.createQuery("from Employe where Nom='"+nom+"' and Prenom='"+prenom+"'").list();
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		if(employes.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	private String generateMatricule() {
		int nextNum=0;
		List<Employe> employes = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			employes = session.createQuery("from Employe").list();
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		
		if(employes.size()==0) {
			return "E0001";
		}else {
			for(Employe employe : employes) {
				StringBuilder matrBuild = new StringBuilder(employe.getCodeemp());
				int tmp=Integer.parseInt(matrBuild.substring(1, matrBuild.length()));
				if(nextNum<tmp) nextNum=tmp;
			}
		}
		return "E"+ajoutZero(nextNum+1,4).toString();
	}

	private SessionFactory getSessionFactory() {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Employe.class)
				.addAnnotatedClass(Affecter.class)
				.buildSessionFactory();
		return factory;
	}
	
	private String ajoutZero(int num,int n) {
		StringBuilder tmp = new StringBuilder(""+num);
		int length = tmp.length();
		for(int i=0;i<n-length;i++){
			tmp.insert(0,'0');
		}
		return tmp.toString();
	}
}
