package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.FedRespExtraContr;
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
public class FedRespExtraContrDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public FedRespExtraContrDao() {
		
		tableName = TableNames.FED_RESP_EXTRA_CONTR;
		
		columnNames = new String[2];
		
		columnNames[0] = "entidade";
		columnNames[1] = "descricao";
	}
	
	public void save(FedRespExtraContr fedRespExtraContr) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, fedRespExtraContr.getEntidade())
		.setParameter(2, fedRespExtraContr.getDescricao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Fed resp extra with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(FedRespExtraContr fedRespExtraContr) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", fedRespExtraContr.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Fed resp extra with ID \'" + fedRespExtraContr.getId() + "\' has been deleted from the database");
	}
	
	public FedRespExtraContr findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (FedRespExtraContr) em.createNativeQuery(query, FedRespExtraContr.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No fed resp extra found with ID: " + id);
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