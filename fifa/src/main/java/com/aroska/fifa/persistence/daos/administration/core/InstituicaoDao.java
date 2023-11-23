package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Instituicao;
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
public class InstituicaoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public InstituicaoDao() {
		
		tableName = TableNames.INSTITUICOES;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome";
		columnNames[1] = "tipo";
	}
	
	public void save(Instituicao instituicao) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, instituicao.getNome())
		.setParameter(2, instituicao.getTipo())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Instituicao with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Instituicao instituicao) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", instituicao.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Instituicao with ID \'" + instituicao.getId() + "\' has been deleted from the database");
	}
	
	public Instituicao findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Instituicao) em.createNativeQuery(query, Instituicao.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No institution found with ID: " + id);
			return null;
		}
	}
	
	public Instituicao findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Instituicao) em.createNativeQuery(query, Instituicao.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No institution found with name: " + name);
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