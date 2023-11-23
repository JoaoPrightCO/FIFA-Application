package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Nacionalidade;
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
public class NacionalidadeDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public NacionalidadeDao() {
		
		tableName = TableNames.NACIONALIDADES;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome_curto";
		columnNames[1] = "nome_ext";
	}
	
	public void save(Nacionalidade nacionalidade) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, nacionalidade.getNome_curto())
		.setParameter(2, nacionalidade.getNome_ext())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Nacionalidade with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Nacionalidade nacionalidade) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", nacionalidade.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Nacionalidade with ID \'" + nacionalidade.getId() + "\' has been deleted from the database");
	}
	
	public Nacionalidade findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Nacionalidade) em.createNativeQuery(query, Nacionalidade.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No nationality found with ID: " + id);
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