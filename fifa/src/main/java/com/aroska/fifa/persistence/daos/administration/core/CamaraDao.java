package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Camara;
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
public class CamaraDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public CamaraDao() {
		
		tableName = TableNames.CAMARAS;
		
		columnNames = new String[2];

		columnNames[0] = "localizacao";
		columnNames[1] = "localizacao_cam";
	}
	
	public void save(Camara camara) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, camara.getLocalizacao())
		.setParameter(2, camara.getLocalizacao_cam())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Camara with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Camara camara) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", camara.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Camara with ID \'" + camara.getId() + "\' has been deleted from the database");
	}
	
	public Camara findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Camara) em.createNativeQuery(query, Camara.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No camera found with ID: " + id);
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