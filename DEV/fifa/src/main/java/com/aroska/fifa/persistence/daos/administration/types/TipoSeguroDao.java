package com.aroska.fifa.persistence.daos.administration.types;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.types.TipoSeguro;
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
public class TipoSeguroDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TipoSeguroDao() {
		
		tableName = TableNames.TIPOS_SEGURO;
		
		columnNames = new String[1];
		
		columnNames[0] = "tipo_seguro";
	}
	
	public void save(TipoSeguro tipoSeguro) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, tipoSeguro.getTipo_seguro())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Tipo(Seguro) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(TipoSeguro tipoSeguro) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", tipoSeguro.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Tipo(Seguro) with ID \'" + tipoSeguro.getId() + "\' has been deleted from the database");
	}
	
	public TipoSeguro findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (TipoSeguro) em.createNativeQuery(query, TipoSeguro.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No insurance type found with ID: " + id);
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