package br.com.houseController.service.Despesa;

import java.time.LocalDate;import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.despesas.RelacaoDespesaReceita;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.persistence.ConnectionFactory;

public class RelacaoDespesaReceitaService implements InterfaceService<RelacaoDespesaReceita> {

	@Override
	public Integer insert(RelacaoDespesaReceita obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public RelacaoDespesaReceita findOne(RelacaoDespesaReceita obj) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<RelacaoDespesaReceita> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from relacao_despesa_receita");
		ArrayList<RelacaoDespesaReceita> list = (ArrayList<RelacaoDespesaReceita>) query.getResultList();
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

	public ArrayList<Despesa> findDespesaByPeriodo(int mes, int ano){
		LocalDate localDateInicial = LocalDate.of(ano, mes, 1);
		LocalDate localDateFinal = localDateInicial.with(TemporalAdjusters.lastDayOfMonth());
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from despesa where dtPagamento between :dtInicial and :dtFinal or dtVencimento between :dtInicial and :dtFinal or (dtVencimento IS NULL and dtPagamento IS NULL) ");
		query.setParameter("dtInicial", localDateInicial);
		query.setParameter("dtFinal", localDateFinal);
		ArrayList<Despesa> list = (ArrayList<Despesa>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
		
	}
	
	public ArrayList<RelacaoDespesaReceita> findReceitaByDespesa(int idDespesa){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from relacao_despesa_receita where idDespesa = :idDespesa");
		query.setParameter("idDespesa", idDespesa);
		ArrayList<RelacaoDespesaReceita> list = (ArrayList<RelacaoDespesaReceita>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

}
