package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.RelacaoInstituicao;
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
public class RelacaoInstituicaoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public RelacaoInstituicaoDao() {
		
		tableName = TableNames.RELACOES_INSTITUICOES;
		
		columnNames = new String[5];
		
		columnNames[0] = "entidade";
		columnNames[1] = "instituicao";
		columnNames[2] = "relacao";
		columnNames[3] = "data_criacao";
		columnNames[4] = "data_modificacao";
	}
	
	public void save(RelacaoInstituicao relacaoInstituicao) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, relacaoInstituicao.getEntidade())
		.setParameter(2, relacaoInstituicao.getInstituicao())
		.setParameter(3, relacaoInstituicao.getRelacao())
		.setParameter(4, relacaoInstituicao.getData_criacao())
		.setParameter(5, relacaoInstituicao.getData_modificacao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Relacao instituicao with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(RelacaoInstituicao relacaoInstituicao) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", relacaoInstituicao.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Relacao instituicao with ID \'" + relacaoInstituicao.getId() + "\' has been deleted from the database");
	}
	
	public RelacaoInstituicao findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (RelacaoInstituicao) em.createNativeQuery(query, RelacaoInstituicao.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No relationship(institution) found with ID: " + id);
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