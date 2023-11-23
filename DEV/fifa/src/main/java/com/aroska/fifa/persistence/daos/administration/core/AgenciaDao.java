package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Agencia;
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
public class AgenciaDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public AgenciaDao() {
		
		tableName = TableNames.AGENCIAS;
		
		columnNames = new String[1];
		
		columnNames[0] = "agencia";
	}
	
	public void save(Agencia agencia) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, agencia.getAgencia())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("User with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Agencia agencia) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", agencia.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Agencia with ID \'" + agencia.getId() + "\' has been deleted from the database");
	}
	
	public Agencia findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Agencia) em.createNativeQuery(query, Agencia.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No agency found with ID: " + id);
			return null;
		}
	}
	
	public Agencia findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Agencia) em.createNativeQuery(query, Agencia.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No agency found with name: " + name);
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