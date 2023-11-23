package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Equipa;
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
public class EquipaDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public EquipaDao() {
		
		tableName = TableNames.EQUIPAS;
		
		columnNames = new String[6];
		
		columnNames[0] = "nome";
		columnNames[1] = "morada";
		columnNames[2] = "zona";
		columnNames[3] = "data_criacao";
		columnNames[4] = "nr_membros";
		columnNames[5] = "logotipo";
	}
	
	public void save(Equipa equipa) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, equipa.getNome())
		.setParameter(2, equipa.getMorada())
		.setParameter(3, equipa.getZona())
		.setParameter(4, equipa.getData_criacao())
		.setParameter(5, equipa.getNr_membros())
		.setParameter(6, equipa.getLogotipo())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Equipa with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Equipa equipa) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", equipa.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Equipa with ID \'" + equipa.getId() + "\' has been deleted from the database");
	}
	
	public Equipa findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Equipa) em.createNativeQuery(query, Equipa.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No entity found with ID: " + id);
			return null;
		}
	}
	
	public Equipa findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Equipa) em.createNativeQuery(query, Equipa.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No team found with name: " + name);
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