package br.com.houseController.service.Receita;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;

public class ReceitaService implements InterfaceService<Receita>{

	@Override
	public Integer insert(Receita obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Receita findOne(Receita obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from receita where dtPagamento = :dtPagamento and descricaoPagamento = :descricaoPagamento");
		query.setParameter("dtPagamento", obj.getDtPagamento());
		query.setParameter("descricaoPagamento", obj.getDescricaoPagamento());
		ArrayList<Receita> list = (ArrayList<Receita>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Receita> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from receita");
		ArrayList<Receita> list = (ArrayList<Receita>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from receita where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}
	
	public Receita findReceitaById(Receita obj){
		Session session = ConnectionFactory.obterNovaSessao();
		Receita receita = session.get(Receita.class, obj.getId());
		ConnectionFactory.fecharSessao(session);
		return receita;
	}

}
