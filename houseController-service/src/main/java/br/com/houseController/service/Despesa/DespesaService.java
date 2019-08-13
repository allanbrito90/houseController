package br.com.houseController.service.Despesa;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.despesas.DespesaFixa;
import br.com.houseController.model.despesas.DespesaVariavel;
import br.com.houseController.persistence.ConnectionFactory;

public class DespesaService implements InterfaceService<Despesa> {

	@Override
	public Integer insert(Despesa obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public Despesa findOne(Despesa obj) {
		return null;
	}

	@Override
	public ArrayList<Despesa> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from despesa");
		ArrayList<Despesa> list = (ArrayList<Despesa>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from despesa where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

}
