package br.com.houseController.service.Despesa;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.despesas.DespesaFixa;
import br.com.houseController.model.despesas.DespesaVariavel;
import br.com.houseController.persistence.ConnectionFactory;
import static java.time.temporal.TemporalAdjusters.*;

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

	public ArrayList<Despesa> findAllByMes(int mes, int ano) {
		//Obtendo LocalDate
		LocalDate localDateFinal = LocalDate.of(ano,mes,1).with(lastDayOfMonth());
		//Obtendo primeiro e último dia do mes
		
		
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from despesa where dtVencimento between :dtInicial and :dtFinal or dtPagamento between :dtInicial and :dtFinal or dtPagamento IS NULL");
		query.setParameter("dtInicial", LocalDate.of(ano, mes, 1));
		query.setParameter("dtFinal", localDateFinal);
		ArrayList<Despesa> list = (ArrayList<Despesa>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

}
