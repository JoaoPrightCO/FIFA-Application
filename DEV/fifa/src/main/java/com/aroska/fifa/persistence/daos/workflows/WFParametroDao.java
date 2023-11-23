package com.aroska.fifa.persistence.daos.workflows;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.workflows.WFParametro;
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
public class WFParametroDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public WFParametroDao() {
		
		tableName = TableNames.WF_PARAMETROS;
		
		columnNames = new String[4];
		
		columnNames[0] = "id_accao";
		columnNames[1] = "tipo_entidade";
		columnNames[2] = "campo";
		columnNames[3] = "valor";
	}
	
	public void save(WFParametro wfParametro) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, wfParametro.getId_accao())
		.setParameter(2, wfParametro.getTipo_entidade())
		.setParameter(3, wfParametro.getCampo())
		.setParameter(4, wfParametro.getValor())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Workflow(Parametro) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(WFParametro wfParametro) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", wfParametro.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Workflow(Parametro) with ID \'" + wfParametro.getId() + "\' has been deleted from the database");
	}
	
	public WFParametro findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (WFParametro) em.createNativeQuery(query, WFParametro.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No workflow parameter found with ID: " + id);
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