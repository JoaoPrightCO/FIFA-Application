package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Cargo;
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
public class CargoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public CargoDao() {
		
		tableName = TableNames.CARGOS;
		
		columnNames = new String[1];
		
		columnNames[0] = "cargo";
	}
	
	public void save(Cargo cargo) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, cargo.getCargo())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Cargo with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Cargo cargo) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", cargo.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Cargo with ID \'" + cargo.getId() + "\' has been deleted from the database");
	}
	
	public Cargo findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Cargo) em.createNativeQuery(query, Cargo.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No role found with ID: " + id);
			return null;
		}
	}
	
	public Cargo findByCargo(String cargo) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], cargo);
		
		try
		{
			return (Cargo) em.createNativeQuery(query, Cargo.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No role found with name: " + cargo);
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