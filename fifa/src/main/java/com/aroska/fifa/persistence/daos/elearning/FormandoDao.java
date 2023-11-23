package com.aroska.fifa.persistence.daos.elearning;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.elearning.Formando;
import com.aroska.fifa.util.QueryUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class FormandoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public FormandoDao() {
		
		tableName = TableNames.FORMANDOS;
		
		columnNames = new String[5];
		columnNames[0] = "id_user";
		columnNames[1] = "id_formacao";
		columnNames[2] = "data_inicio";
		columnNames[3] = "data_fim";
		columnNames[4] = "estado";
	}
	
	public void save(Formando formando) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, formando.getId_user())
		.setParameter(2, formando.getId_formacao())
		.setParameter(3, formando.getData_inicio())
		.setParameter(4, formando.getData_fim())
		.setParameter(5, formando.getEstado())
		.executeUpdate();
	}
	
	/**
	 * TODO
	 * both "edit" and "delete" need specific values since it's an auxiliary table
	 */
//	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
//		
//		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
//		
//		em.createNativeQuery(query).executeUpdate();
//	
//		System.out.println("Formando with ID \'" + id + "\' successfully edited");
//	}
//	
//	public void delete(Formando formando) {
//		
//		String query = QueryUtil.createDeleteQuery(tableName, "id", formando.getId());
//		
//		em.createNativeQuery(query).executeUpdate();
//		
//		System.out.println("Formando with ID \'" + formando.getId() + "\' has been deleted from the database");
//	}
	
	public List<Formando> findByIdUser(int idUser) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], String.valueOf(idUser));
		
		try
		{
			return (List<Formando>) em.createNativeQuery(query, Formando.class).getResultList();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No formando (or formacoes associated with user) found with id: " + idUser);
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