package br.com.houseController.service.Produto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.produto.Produto;
import br.com.houseController.persistence.ConnectionFactory;

public class ProdutoService implements InterfaceService<Produto> {

	@Override
	public Integer insert(Produto obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public Produto findOne(Produto obj) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Produto> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from produto");
		ArrayList<Produto> list = (ArrayList<Produto>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from produto where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public List<Produto> produtosPorMes(LocalDate periodo){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from produto where periodoReferencia = :periodoReferencia");
		query.setParameter("periodoReferencia", periodo);
		List<Produto> list = (ArrayList<Produto>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}
	
	public Integer deleteProdutosPorMes(LocalDate periodo){
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from produto where periodoReferencia = :periodoReferencia");
		query.setParameter("periodoReferencia", periodo);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

	
	@SuppressWarnings("unchecked")
	public List<Produto> produtosPorAno(Integer ano){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from produto where periodoReferencia between :periodoReferenciaInicial and :periodoReferenciaFinal");
		query.setParameter("periodoReferenciaInicial", LocalDate.of(ano, 1, 1));
		query.setParameter("periodoReferenciaFinal", LocalDate.of(ano, 12, 1));
		List<Produto> list = (ArrayList<Produto>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}
	
}
