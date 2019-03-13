package br.com.houseController.service.Despesa;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.despesas.DespesaFixa;
import br.com.houseController.model.despesas.DespesaVariavel;
import br.com.houseController.persistence.ConnectionFactory;

public class DespesaFixaService implements InterfaceService<DespesaFixa> {

	@Override
	public Integer insert(DespesaFixa obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.save(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public DespesaFixa findOne(DespesaFixa obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DespesaFixa> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from despesaFixa");
		ArrayList<DespesaFixa> list = (ArrayList<DespesaFixa>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from despesa_fixa where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

}
