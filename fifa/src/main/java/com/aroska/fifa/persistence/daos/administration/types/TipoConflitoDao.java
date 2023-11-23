package com.aroska.fifa.persistence.daos.administration.types;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.types.TipoConflito;
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
public class TipoConflitoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TipoConflitoDao() {
		
		tableName = TableNames.TIPOS_CONFLITO;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome_curto";
		columnNames[1] = "nome_ext";
	}
	
	public void save(TipoConflito tipoConflito) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, tipoConflito.getNome_curto())
		.setParameter(2, tipoConflito.getNome_ext())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Tipo(Conflito) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(TipoConflito tipoConflito) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", tipoConflito.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Tipo(Conflito) with ID \'" + tipoConflito.getId() + "\' has been deleted from the database");
	}
	
	public TipoConflito findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (TipoConflito) em.createNativeQuery(query, TipoConflito.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No conflict type found with ID: " + id);
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