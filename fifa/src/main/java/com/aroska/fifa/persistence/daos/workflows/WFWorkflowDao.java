package com.aroska.fifa.persistence.daos.workflows;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.workflows.WFWorkflow;
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
public class WFWorkflowDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public WFWorkflowDao() {
		
		tableName = TableNames.WF_WORKFLOWS;
		
		columnNames = new String[2];
		
		columnNames[0] = "id_user";
		columnNames[1] = "nome";
	}
	
	public void save(WFWorkflow wfWorkflow) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, wfWorkflow.getId_user())
		.setParameter(2, wfWorkflow.getNome())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Workflow with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(WFWorkflow wfWorkflow) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", wfWorkflow.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Workflow with ID \'" + wfWorkflow.getId() + "\' has been deleted from the database");
	}
	
	public WFWorkflow findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (WFWorkflow) em.createNativeQuery(query, WFWorkflow.class).getSingleResult();
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