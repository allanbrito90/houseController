package br.com.houseController.service.Produto;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.produto.Ingrediente;
import br.com.houseController.persistence.ConnectionFactory;

public class IngredienteService implements InterfaceService<Ingrediente> {

	@Override
	public Integer insert(Ingrediente obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ingrediente findOne(Ingrediente obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from ingrediente where descricaoIngrediente = :descricaoIngrediente");
		query.setParameter("descricaoIngrediente", obj.getDescricaoIngrediente());
		ArrayList<Ingrediente> list = (ArrayList<Ingrediente>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Ingrediente> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from ingrediente");
		ArrayList<Ingrediente> list = (ArrayList<Ingrediente>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from ingrediente where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

}
