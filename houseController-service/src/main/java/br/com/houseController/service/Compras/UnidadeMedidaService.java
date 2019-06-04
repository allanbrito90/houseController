package br.com.houseController.service.Compras;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.produto.UnidadeMedida;
import br.com.houseController.persistence.ConnectionFactory;

public class UnidadeMedidaService implements InterfaceService<UnidadeMedida> {

	@Override
	public Integer insert(UnidadeMedida obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public UnidadeMedida findOne(UnidadeMedida obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<UnidadeMedida> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from unidade_medida");
		ArrayList<UnidadeMedida> list = (ArrayList<UnidadeMedida>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from unidade_medida where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

}
