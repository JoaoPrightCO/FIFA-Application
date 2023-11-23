package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Contrato;
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
public class ContratoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public ContratoDao() {
		
		tableName = TableNames.CONTRATOS;
		
		columnNames = new String[9];
		
		columnNames[0] = "ent1_id";
		columnNames[1] = "ent2_id";
		columnNames[2] = "tipo_contrato";
		columnNames[3] = "descr_contrato";
		columnNames[4] = "estado";
		columnNames[5] = "data_criacao";
		columnNames[6] = "data_modificacao";
		columnNames[7] = "motivo";
		columnNames[8] = "documento";
	}
	
	public void save(Contrato contrato) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, contrato.getEnt1_id())
		.setParameter(2, contrato.getEnt2_id())
		.setParameter(3, contrato.getTipo_contrato())
		.setParameter(4, contrato.getDescr_contrato())
		.setParameter(5, contrato.getEstado())
		.setParameter(6, contrato.getData_criacao())
		.setParameter(7, contrato.getData_modificacao())
		.setParameter(8, contrato.getMotivo())
		.setParameter(9, contrato.getDocumento())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Contrato with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Contrato contrato) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", contrato.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Agente with ID \'" + contrato.getId() + "\' has been deleted from the database");
	}
	
	public Contrato findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Contrato) em.createNativeQuery(query, Contrato.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No contract found with ID: " + id);
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