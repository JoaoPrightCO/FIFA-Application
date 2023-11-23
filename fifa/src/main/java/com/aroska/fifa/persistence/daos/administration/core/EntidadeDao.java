package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Entidade;
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
public class EntidadeDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public EntidadeDao() {
		
		tableName = TableNames.ENTIDADES;
		
		columnNames = new String[5];
		
		columnNames[0] = "nome";
		columnNames[1] = "morada";
		columnNames[2] = "tipo_entidade";
		columnNames[3] = "data_fundacao";
		columnNames[4] = "logotipo";
	}
	
	public void save(Entidade entidade) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, entidade.getNome())
		.setParameter(2, entidade.getMorada())
		.setParameter(3, entidade.getTipo_entidade())
		.setParameter(4, entidade.getData_fundacao())
		.setParameter(5, entidade.getLogotipo())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Entidade with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Entidade entidade) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", entidade.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Entidade with ID \'" + entidade.getId() + "\' has been deleted from the database");
	}
	
	public Entidade findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Entidade) em.createNativeQuery(query, Entidade.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No entity found with ID: " + id);
			return null;
		}
	}
	
	public Entidade findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Entidade) em.createNativeQuery(query, Entidade.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No entity found with name: " + name);
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