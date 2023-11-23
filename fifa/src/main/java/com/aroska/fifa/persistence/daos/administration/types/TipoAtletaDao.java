package com.aroska.fifa.persistence.daos.administration.types;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.types.TipoAtleta;
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
public class TipoAtletaDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TipoAtletaDao() {
		
		tableName = TableNames.TIPOS_ATLETA;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome_curto";
		columnNames[1] = "nome_ext";
	}
	
	public void save(TipoAtleta tipoAtleta) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, tipoAtleta.getNome_curto())
		.setParameter(2, tipoAtleta.getNome_ext())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Tipo(Atleta) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(TipoAtleta tipoAtleta) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", tipoAtleta.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Tipo(Atleta) with ID \'" + tipoAtleta.getId() + "\' has been deleted from the database");
	}
	
	public TipoAtleta findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (TipoAtleta) em.createNativeQuery(query, TipoAtleta.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No athlete type found with ID: " + id);
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