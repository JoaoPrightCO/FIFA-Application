package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Conflito;
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
public class ConflitoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public ConflitoDao() {
		
		tableName = TableNames.CONFLITOS;
		
		columnNames = new String[11];
		
		columnNames[0] = "ent1_id";
		columnNames[1] = "ent2_id";
		columnNames[2] = "entg_id";
		columnNames[3] = "tipo_conflito";
		columnNames[4] = "descr_conflito";
		columnNames[5] = "estado";
		columnNames[6] = "data_criacao";
		columnNames[7] = "data_modificacao";
		columnNames[8] = "data_verdicto";
		columnNames[9] = "motivo_recusa";
		columnNames[10] = "documento";
	}
	
	public void save(Conflito conflito) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, conflito.getEnt1_id())
		.setParameter(2, conflito.getEnt2_id())
		.setParameter(3, conflito.getEntg_id())
		.setParameter(4, conflito.getTipo_conflito())
		.setParameter(5, conflito.getDescr_conflito())
		.setParameter(6, conflito.getEstado())
		.setParameter(7, conflito.getData_criacao())
		.setParameter(8, conflito.getData_modificacao())
		.setParameter(9, conflito.getData_verdicto())
		.setParameter(10, conflito.getMotivo_recusa())
		.setParameter(11, conflito.getDocumento())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Conflito with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Conflito conflito) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", conflito.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Conflito with ID \'" + conflito.getId() + "\' has been deleted from the database");
	}
	
	public Conflito findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Conflito) em.createNativeQuery(query, Conflito.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No conflict found with ID: " + id);
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