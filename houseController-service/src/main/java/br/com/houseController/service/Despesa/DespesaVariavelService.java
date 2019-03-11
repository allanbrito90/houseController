package br.com.houseController.service.Despesa;

import java.util.ArrayList;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.despesas.DespesaVariavel;
import br.com.houseController.persistence.ConnectionFactory;

public class DespesaVariavelService implements InterfaceService<DespesaVariavel>{

	@Override
	public Integer insert(DespesaVariavel obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.save(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@Override
	public DespesaVariavel findOne(DespesaVariavel obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DespesaVariavel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
