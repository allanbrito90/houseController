package br.com.houseController.service.Despesa;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.despesas.DespesaVariavel;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;

public class DespesaVariavelService implements InterfaceService<DespesaVariavel>{

	@Override
	public Integer insert(DespesaVariavel obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public DespesaVariavel findOne(DespesaVariavel obj) {
		return null;
	}

	@Override
	public ArrayList<DespesaVariavel> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from despesaVariavel");
		ArrayList<DespesaVariavel> list = (ArrayList<DespesaVariavel>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from despesa_variavel where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

}
