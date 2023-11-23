package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Zona;
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
public class ZonaDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public ZonaDao() {
		
		tableName = TableNames.ZONAS;
		
		columnNames = new String[1];
		
		columnNames[0] = "zona";
	}
	
	public void save(Zona zona) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, zona.getZona())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Zona with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Zona zona) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", zona.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Zona with ID \'" + zona.getId() + "\' has been deleted from the database");
	}
	
	public Zona findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Zona) em.createNativeQuery(query, Zona.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No workflow found with ID: " + id);
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