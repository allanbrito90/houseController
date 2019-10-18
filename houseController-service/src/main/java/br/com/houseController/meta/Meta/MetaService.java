package br.com.houseController.meta.Meta;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.meta.Meta;
import br.com.houseController.model.meta.MetaTempo;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;

public class MetaService implements InterfaceService<Meta> {

	@Override
	public Integer insert(Meta obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public Meta findOne(Meta obj) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Meta> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from meta");
		ArrayList<Meta> list = (ArrayList<Meta>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from meta where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Meta> findMetabyUsuario(Usuario usuario){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from meta where usuario = :usuario order by id desc");
		query.setParameter("usuario", usuario);
		ArrayList<Meta> list = (ArrayList<Meta>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<MetaTempo> findAllMetaTempo() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from meta");
		ArrayList<Meta> list = (ArrayList<Meta>) query.getResultList();
		ArrayList<MetaTempo> retorno = new ArrayList<>();
		for(Meta meta : list){
			MetaTempo mt = new MetaTempo();
			mt.setId(meta.getId());
			mt.setCumprido(meta.isCumprido());
			mt.setDescMeta(meta.getDescMeta());
			mt.setDtConclusao(meta.getDtConclusao());
			mt.setDtInicial(meta.getDtInicial());
			mt.setTitulo(meta.getTitulo());
			mt.setUsuario(meta.getUsuario());
			mt.setTempo(meta.getDtConclusao().until(LocalDate.now(), ChronoUnit.DAYS));
			retorno.add(mt);
		}
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

}
