package br.com.houseController.main;


import br.com.houseController.login.Login;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
    	launch(args);
    	
    	/*SessionFactory factory = new Configuration()
    			.configure("hibernate.cfg.xml")
    			.addAnnotatedClass(Usuario.class)
    			.buildSessionFactory();
    	
    	Session session = factory.getCurrentSession();
    	
    	try {
			Usuario usuario = new Usuario(1, "allan", "1234", "Allan", "allan.brito@outlook.com.br");
			session.beginTransaction();
			session.save(usuario);
			session.getTransaction().commit();
		} catch (Exception e) {
			factory.close();
		}*/
    	
    }

	@Override
	public void start(Stage stage) throws Exception {
		Login login = new Login();
		login.exibirLogin(stage);		
	}
}
