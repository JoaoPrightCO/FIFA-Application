package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.RegraDesportiva;
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
public class RegraDesportivaDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public RegraDesportivaDao() {
		
		tableName = TableNames.REGRAS_DESPORTIVAS;
		
		columnNames = new String[5];
		
		columnNames[0] = "zona";
		columnNames[1] = "tipo";
		columnNames[2] = "descricao";
		columnNames[3] = "data_criacao";
		columnNames[4] = "data_modificacao";
	}
	
	public void save(RegraDesportiva regraDesportiva) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, regraDesportiva.getZona())
		.setParameter(2, regraDesportiva.getTipo())
		.setParameter(3, regraDesportiva.getDescricao())
		.setParameter(4, regraDesportiva.getData_criacao())
		.setParameter(5, regraDesportiva.getData_modificacao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Regra desportiva with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(RegraDesportiva regraDesportiva) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", regraDesportiva.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Regra desportiva with ID \'" + regraDesportiva.getId() + "\' has been deleted from the database");
	}
	
	public RegraDesportiva findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (RegraDesportiva) em.createNativeQuery(query, RegraDesportiva.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No sports rule found with ID: " + id);
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