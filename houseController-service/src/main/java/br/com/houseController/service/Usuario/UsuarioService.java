package br.com.houseController.service.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.persistence.Query;
import javax.swing.JOptionPane;

import org.hibernate.Session;

import br.com.houseController.dialogs.Aguarde;
import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UsuarioService implements InterfaceService<Usuario>, Callable<Integer>{
	Usuario usuario = new Usuario();
	
	public UsuarioService(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public UsuarioService() {
	}

	@Override
	public Integer insert(Usuario obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.save(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Usuario> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from Usuario");
		ArrayList<Usuario> list = (ArrayList<Usuario>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}
	

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from Usuario where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

	@Override
	public Usuario findOne(Usuario obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		Usuario usuario = session.get(Usuario.class, obj.getId());
		ConnectionFactory.fecharSessao(session);
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public boolean checaLogin(Usuario usuario){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from Usuario where login = :login and senha = :senha");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());		
		ArrayList<Usuario> list = (ArrayList<Usuario>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(list.size()>0){
//			JOptionPane.showMessageDialog(null, "USUÁRIO ENCONTRADO");
			return true;
		}
//		JOptionPane.showMessageDialog(null, "USUÁRIO NÃO ENCONTRADO");
		return false;		
	}

//	@Override
//	protected Task<Boolean> createTask() {
//		return new Task<Boolean>() {
//
//			@Override
//			protected Boolean call() throws Exception {
//				Stage stage = new Stage();
//				try {
//					Parent root1 = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("fxml/aguarde.fxml"));
//					stage = new Stage();
//					stage.setScene(new Scene(root1));
//					stage.show();
//					checaLogin(usuario);
//					stage.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}		
//				return checaLogin(usuario);
//			}
//		};
//	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(2000);
		checaLogin(usuario);
		return 10056;
	}
}
