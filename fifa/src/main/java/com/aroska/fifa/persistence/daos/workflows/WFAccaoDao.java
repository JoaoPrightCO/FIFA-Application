package com.aroska.fifa.persistence.daos.workflows;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.workflows.WFAccao;
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
public class WFAccaoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public WFAccaoDao() {
		
		tableName = TableNames.WF_ACCOES;
		
		columnNames = new String[2];
		
		columnNames[0] = "id_wf";
		columnNames[1] = "accao";
	}
	
	public void save(WFAccao wfAccao) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, wfAccao.getId_wf())
		.setParameter(2, wfAccao.getAccao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Workflow(Accao) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(WFAccao wfAccao) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", wfAccao.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Workflow(Accao) with ID \'" + wfAccao.getId() + "\' has been deleted from the database");
	}
	
	public WFAccao findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (WFAccao) em.createNativeQuery(query, WFAccao.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No workflow action found with ID: " + id);
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