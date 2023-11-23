package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Agente;
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
public class AgenteDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public AgenteDao() {
		
		tableName = TableNames.AGENTES;
		
		columnNames = new String[9];
		
		columnNames[0] = "nome";
		columnNames[1] = "morada";
		columnNames[2] = "tipo_agente";
		columnNames[3] = "nif";
		columnNames[4] = "nacionalidade";
		columnNames[5] = "data_nasc";
		columnNames[6] = "sexo";
		columnNames[7] = "agencia";
		columnNames[8] = "foto";
	}
	
	public void save(Agente agente) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, agente.getNome())
		.setParameter(2, agente.getMorada())
		.setParameter(3, agente.getTipo_agente())
		.setParameter(4, agente.getNif())
		.setParameter(5, agente.getNacionalidade())
		.setParameter(6, agente.getData_nasc())
		.setParameter(7, agente.getSexo())
		.setParameter(8, agente.getAgencia())
		.setParameter(9, agente.getFoto())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Agente with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Agente agente) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", agente.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Agente with ID \'" + agente.getId() + "\' has been deleted from the database");
	}
	
	public Agente findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Agente) em.createNativeQuery(query, Agente.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No agent found with ID: " + id);
			return null;
		}
	}
	
	public Agente findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Agente) em.createNativeQuery(query, Agente.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No agent found with name: " + name);
			return null;
		}
		
	}
	
	public Agente findByNif(int nif) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[3], nif);
		
		try
		{
			return (Agente) em.createNativeQuery(query, Agente.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No agent found with NIF: " + nif);
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