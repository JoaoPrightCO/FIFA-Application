package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.RelacaoJuridica;
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
public class RelacaoJuridicaDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public RelacaoJuridicaDao() {
		
		tableName = TableNames.RELACOES_JURIDICAS;
		
		columnNames = new String[5];
		
		columnNames[0] = "entidade";
		columnNames[1] = "instituicao";
		columnNames[2] = "relacao";
		columnNames[3] = "data_criacao";
		columnNames[4] = "data_modificacao";
	}
	
	public void save(RelacaoJuridica relacaoJuridica) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, relacaoJuridica.getEntidade())
		.setParameter(2, relacaoJuridica.getInstituicao())
		.setParameter(3, relacaoJuridica.getRelacao())
		.setParameter(4, relacaoJuridica.getData_criacao())
		.setParameter(5, relacaoJuridica.getData_modificacao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Relacao juridica with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(RelacaoJuridica relacaoJuridica) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", relacaoJuridica.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Relacao juridica with ID \'" + relacaoJuridica.getId() + "\' has been deleted from the database");
	}
	
	public RelacaoJuridica findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (RelacaoJuridica) em.createNativeQuery(query, RelacaoJuridica.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No relationship(juridical) found with ID: " + id);
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