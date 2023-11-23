package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Evento;
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
public class EventoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public EventoDao() {
		
		tableName = TableNames.EVENTOS;
		
		columnNames = new String[6];
		
		columnNames[0] = "nome";
		columnNames[1] = "entidade";
		columnNames[2] = "localizacao";
		columnNames[3] = "data";
		columnNames[4] = "data_criacao";
		columnNames[5] = "data_modificacao";
	}
	
	public void save(Evento evento) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, evento.getNome())
		.setParameter(2, evento.getEntidade())
		.setParameter(3, evento.getLocalizacao())
		.setParameter(4, evento.getData())
		.setParameter(5, evento.getData_criacao())
		.setParameter(6, evento.getData_modificacao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Evento with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Evento evento) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", evento.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Agente with ID \'" + evento.getId() + "\' has been deleted from the database");
	}
	
	public Evento findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Evento) em.createNativeQuery(query, Evento.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No event found with ID: " + id);
			return null;
		}
	}
	
	public Evento findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Evento) em.createNativeQuery(query, Evento.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No event found with name: " + name);
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