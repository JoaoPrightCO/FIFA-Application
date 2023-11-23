package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Seguro;
import com.aroska.fifa.util.QueryUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class SeguroDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public SeguroDao() {
		
		tableName = TableNames.SEGUROS;
		
		columnNames = new String[7];
		
		columnNames[0] = "entidade";
		columnNames[1] = "tipo";
		columnNames[2] = "data_inicio_contr";
		columnNames[3] = "pagamento_mensal";
		columnNames[4] = "descricao";
		columnNames[5] = "data_criacao";
		columnNames[6] = "data_modificacao";
	}
	
	public void save(Seguro seguro) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, seguro.getEntidade())
		.setParameter(2, seguro.getTipo())
		.setParameter(3, seguro.getData_inicio_contr())
		.setParameter(4, seguro.getPagamento_mensal())
		.setParameter(5, seguro.getDescricao())
		.setParameter(6, seguro.getData_criacao())
		.setParameter(7, seguro.getData_modificacao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Seguro with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Seguro seguro) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", seguro.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Seguro with ID \'" + seguro.getId() + "\' has been deleted from the database");
	}
	
	public Seguro findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Seguro) em.createNativeQuery(query, Seguro.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No insurance found with ID: " + id);
			return null;
		}
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String[] getColumnNames() {
		return columnNames;
	}
}