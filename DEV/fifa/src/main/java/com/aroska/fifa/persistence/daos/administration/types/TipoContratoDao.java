package com.aroska.fifa.persistence.daos.administration.types;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.types.TipoContrato;
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
public class TipoContratoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TipoContratoDao() {
		
		tableName = TableNames.TIPOS_CONTRATO;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome_curto";
		columnNames[1] = "nome_ext";
	}
	
	public void save(TipoContrato tipoContrato) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, tipoContrato.getNome_curto())
		.setParameter(2, tipoContrato.getNome_ext())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Tipo(Contrato) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(TipoContrato tipoContrato) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", tipoContrato.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Tipo(Contrato) with ID \'" + tipoContrato.getId() + "\' has been deleted from the database");
	}
	
	public TipoContrato findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (TipoContrato) em.createNativeQuery(query, TipoContrato.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No contract type found with ID: " + id);
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