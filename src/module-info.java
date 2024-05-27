/**
 * @author Santatra
 *
 */
module gestion_affectation {
	requires java.persistence;
	requires java.desktop;
	requires org.hibernate.orm.core;
	requires java.sql;
	requires com.sun.istack.runtime;
	requires transitive javafx.base;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;
	    
	opens entity;
	opens view to javafx.fxml;    
	exports view;
}