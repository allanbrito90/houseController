package br.com.houseController.main;


import java.util.List;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;

import br.com.houseController.login.Login;
import br.com.houseController.model.usuario.Usuario;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application{

    public static void main( String[] args )
    {
//    	launch(args);
    	
    	SessionFactory factory = new Configuration()
    			.configure("hibernate.cfg.xml")
    			.addAnnotatedClass(Usuario.class)
    			.buildSessionFactory();
    	
    	Session session = factory.getCurrentSession();
    	
    	try {
			Usuario usuario = new Usuario(4, "talita", "4321", "Talita", "talitanunes.tlt@hotmail.com");
			session.beginTransaction();
			session.save(usuario);
			session.getTransaction().commit();
			System.out.println("Inserido!");
		} catch (Exception e) {
			factory.close();
		}
    	  	 
    	
    }

	@Override
	public void start(Stage stage) throws Exception {
		Login login = new Login();
		login.exibirLogin(stage);		
	}
}
