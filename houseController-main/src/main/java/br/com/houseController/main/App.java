package br.com.houseController.main;


import java.io.File;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.houseController.login.Login;
import br.com.houseController.model.usuario.Usuario;
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
    		try {   			
    			
//    			Session session3 = ConnectionFactory.obterNovaSessao();
//    			try {
//					Usuario usuario2 = new Usuario("teste", "1234", "User Teste", "teste2@teste2.com.br");
//					
//					session3.beginTransaction();
//					session3.save(usuario2);
//					session3.getTransaction().rollback();
//					System.out.println("Inserido!");
//				} catch (HibernateException e) {
//					session3.getTransaction().rollback();
//					e.printStackTrace();
//				}

    			Session session = ConnectionFactory.obterNovaSessao();
    			Query query = session.createQuery("from Usuario");
    			List<?> list = query.getResultList();
    			for(Object obj : list){
    				Usuario usuario = (Usuario) obj; 
    				System.out.println(usuario.getNome());
    			}
    			ConnectionFactory.fecharSessao(session);
    			
    			Session session2 = ConnectionFactory.obterNovaSessao();
    			Query query2 = session2.createQuery("from Usuario");
    			List<?> list2 = query2.getResultList();
    			for(Object obj : list2){
    				Usuario usuario = (Usuario) obj; 
    				System.out.println(usuario.getEmail());
    			}
    			
    		} catch (RuntimeException e) {
    			e.printStackTrace();
    		}finally {
    			//factory.close();
    		}
    	  	 
    	
    	launch(args);
    }

	@Override
	public void start(Stage stage) throws Exception {
		Login login = new Login();
		login.exibirLogin(stage);		
	}
}
