package com.aroska.fifa.persistence.daos.elearning;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.elearning.Formacao;
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
public class FormacaoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public FormacaoDao() {
		
		tableName = TableNames.FORMACOES;
		
		columnNames = new String[12];
		columnNames[0] = "nome";
		columnNames[1] = "descricao";
		columnNames[2] = "entidade";
		columnNames[3] = "tipo";
		columnNames[4] = "forma";
		columnNames[5] = "competencias";
		columnNames[6] = "area";
		columnNames[7] = "trab_prop";
		columnNames[8] = "estado";
		columnNames[9] = "data_pub";
		columnNames[10] = "autor";
		columnNames[11] = "imagem";
	}
	
	public void save(Formacao formacao) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, formacao.getNome())
		.setParameter(2, formacao.getDescricao())
		.setParameter(3, formacao.getEntidade())
		.setParameter(4, formacao.getTipo())
		.setParameter(5, formacao.getForma())
		.setParameter(6, formacao.getCompetencias())
		.setParameter(7, formacao.getArea())
		.setParameter(8, formacao.getTrab_prop())
		.setParameter(9, formacao.getEstado())
		.setParameter(10, formacao.getData_pub())
		.setParameter(11, formacao.getAutor())
		.setParameter(12, formacao.getImagem())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Formacao with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Formacao formacao) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", formacao.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Formacao with ID \'" + formacao.getId() + "\' has been deleted from the database");
	}
	
	public Formacao findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Formacao) em.createNativeQuery(query, Formacao.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No formacao found with ID: " + id);
			return null;
		}
	}
	
	public Formacao findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Formacao) em.createNativeQuery(query, Formacao.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No formacao found with name: " + name);
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