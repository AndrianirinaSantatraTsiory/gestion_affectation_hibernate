package service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entity.Affecter;
import entity.Employe;
import entity.Lieu;

public class lieuService {
	public void ajouter(String designation, String province) {
			SessionFactory factory = getSessionFactory();
			//create session
			Session session = factory.getCurrentSession();
			
			try {
			
				Lieu tempLieu = new Lieu(designation, province);
				//start a transaction
				session.beginTransaction();
				
				//save lieu
				session.save(tempLieu);
				
				//commit the transaction
				session.getTransaction().commit();
			}catch(Exception ext) {
				System.out.println("Erreur d'exception: "+ext.getMessage());
			}finally {
				factory.close();
			}
	}
	
	public void supprimer(int code) {
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			Lieu tempLieu = session.get(Lieu.class, code);
			
			List<Affecter> affectations = session.createQuery("from Affecter where codelieu="+code+" order by dateAffectation ASC").list();
			
			for(Affecter tmp : affectations) {
				session.delete(tmp);
			}
			
			//Delete lieu
			session.delete(tempLieu);
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
	}
	
	public void modifier(int code, String designation, String province) {
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			Lieu tempLieu = session.get(Lieu.class, code);
			
			//Modify lieu
			tempLieu.setDesignation(designation);
			tempLieu.setProvince(province);		
			
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
	}
	
	public Lieu selectionner(int code) {
		Lieu retLieu = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			retLieu = session.get(Lieu.class, code);
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return retLieu;
	}
	
	public List<Lieu> tous(){
		List<Lieu> all = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			all = session.createQuery("from Lieu").list();
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return all;
	}
	
	public List<Lieu> rechercher(String keyword){
		List<Lieu> lieux = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieu
			lieux = session.createQuery("from Lieu  where designation LIKE '%"+keyword+"%' or province LIKE '%"+keyword+"%'").list();
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		return lieux;
	}
	
	public boolean existe(String designation, String province) {
		List<Lieu> lieux = null;
		SessionFactory factory = getSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {	
			//start a transaction
			session.beginTransaction();
			
			//Select lieux
			lieux = session.createQuery("from Lieu where designation='"+designation+"' and province='"+province+"'").list();
						
			//commit the transaction
			session.getTransaction().commit();
		}catch(Exception ext) {
			System.out.println("Erreur d'exception: "+ext.getMessage());
		}finally {
			factory.close();
		}
		if(lieux.size()>0) {
			return true;
		}else {
			return false;
		}
	}

	private SessionFactory getSessionFactory() {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Lieu.class)
				.addAnnotatedClass(Affecter.class)
				.buildSessionFactory();
		return factory;
	}
}
