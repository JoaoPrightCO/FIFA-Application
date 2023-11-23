package com.aroska.fifa.persistence.daos.administration.types;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.types.TipoAgente;
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
public class TipoAgenteDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TipoAgenteDao() {
		
		tableName = TableNames.TIPOS_AGENTE;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome_curto";
		columnNames[1] = "nome_ext";
	}
	
	public void save(TipoAgente tipoAgente) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, tipoAgente.getNome_curto())
		.setParameter(2, tipoAgente.getNome_ext())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Tipo(Agente) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(TipoAgente tipoAgente) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", tipoAgente.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Tipo(Agente) with ID \'" + tipoAgente.getId() + "\' has been deleted from the database");
	}
	
	public TipoAgente findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (TipoAgente) em.createNativeQuery(query, TipoAgente.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No agent type found with ID: " + id);
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