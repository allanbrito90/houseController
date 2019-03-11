package br.com.houseController.service.Categoria;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.categoria.Categoria;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;

public class CategoriaService implements InterfaceService<Categoria> {

	@Override
	public Integer insert(Categoria obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.save(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public Categoria findOne(Categoria obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Categoria> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from categoria");
		ArrayList<Categoria> list = (ArrayList<Categoria>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
