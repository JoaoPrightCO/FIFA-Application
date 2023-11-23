package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Treinador;
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
public class TreinadorDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TreinadorDao() {
		
		tableName = TableNames.TREINADORES;
		
		columnNames = new String[9];
		
		columnNames[0] = "nome";
		columnNames[1] = "morada";
		columnNames[2] = "nif";
		columnNames[3] = "nacionalidade";
		columnNames[4] = "data_nasc";
		columnNames[5] = "sexo";
		columnNames[6] = "id_equipa";
		columnNames[7] = "certificacao";
		columnNames[8] = "foto";
	}
	
	public void save(Treinador treinador) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, treinador.getNome())
		.setParameter(2, treinador.getMorada())
		.setParameter(3, treinador.getNif())
		.setParameter(4, treinador.getNacionalidade())
		.setParameter(5, treinador.getData_nasc())
		.setParameter(6, treinador.getSexo())
		.setParameter(7, treinador.getId_equipa())
		.setParameter(8, treinador.getCertificacao())
		.setParameter(9, treinador.getFoto())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Treinador with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Treinador treinador) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", treinador.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Treinador with ID \'" + treinador.getId() + "\' has been deleted from the database");
	}
	
	public Treinador findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Treinador) em.createNativeQuery(query, Treinador.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No trainer found with ID: " + id);
			return null;
		}
	}
	
	public Treinador findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Treinador) em.createNativeQuery(query, Treinador.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No trainer found with name: " + name);
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