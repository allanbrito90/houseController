package br.com.houseController.main;


import br.com.houseController.login.Login;
import br.com.houseController.persistence.ConnectionFactory;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application{

    public static void main( String[] args )
    {
    	ConnectionFactory.criarConexao();    		    	  	 
    	
    	launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		Login login = new Login();
		login.exibirLogin(stage);		
	}
}
