package br.com.houseController.service.Receita;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
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
		Query query = session.createQuery("from receita order by id desc");
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
	
	public Receita findReceitaById(int obj){
		Session session = ConnectionFactory.obterNovaSessao();
		Receita receita = session.get(Receita.class, obj);
		ConnectionFactory.fecharSessao(session);
		return receita;
	}
	
	public ArrayList<Receita> findAllByMes(int mes, int ano){
		LocalDate localDateFinal = LocalDate.of(ano, mes, 1).with(TemporalAdjusters.lastDayOfMonth());
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from receita where dtPagamento between :dtInicial and :dtFinal");
		query.setParameter("dtInicial", LocalDate.of(ano, mes, 1));
		query.setParameter("dtFinal", localDateFinal);
		ArrayList<Receita> listReceitas = (ArrayList<Receita>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return listReceitas;
	}
	
	public ArrayList<Receita> findAllByMesAndUsuario(int mes, int ano, Usuario usuario){
		LocalDate localDateFinal = LocalDate.of(ano, mes, 1).with(TemporalAdjusters.lastDayOfMonth());
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from receita where usuario = :usuario and (dtPagamento between :dtInicial and :dtFinal)");
		query.setParameter("dtInicial", LocalDate.of(ano, mes, 1));
		query.setParameter("dtFinal", localDateFinal);
		query.setParameter("usuario",usuario);
		ArrayList<Receita> listReceitas = (ArrayList<Receita>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return listReceitas;
	}

}
