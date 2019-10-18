package br.com.houseController.service.Compras;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.despesas.Compras;
import br.com.houseController.persistence.ConnectionFactory;

public class ComprasService implements InterfaceService<Compras> {

	@Override
	public Integer insert(Compras obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public Compras findOne(Compras obj) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Compras> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from compras order by id desc");
		ArrayList<Compras> list = (ArrayList<Compras>) query.getResultList();
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

	@SuppressWarnings("unchecked")
	public Compras findOneByMonthAndYear(Integer mes, Integer ano) {
			Session session = ConnectionFactory.obterNovaSessao();
			Query query = session.createQuery("from compras where periodoReferencia = :periodoReferencia");
			query.setParameter("periodoReferencia", LocalDate.of(ano, mes, 1));
			ArrayList<Compras> list = (ArrayList<Compras>) query.getResultList();
			ConnectionFactory.fecharSessao(session);
			if(list.size()==0){
				return null;
			}
			return list.get(0);
		}

}
