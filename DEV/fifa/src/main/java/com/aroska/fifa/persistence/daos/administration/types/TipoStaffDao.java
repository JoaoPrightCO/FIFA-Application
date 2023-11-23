package com.aroska.fifa.persistence.daos.administration.types;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.types.TipoStaff;
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
public class TipoStaffDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TipoStaffDao() {
		
		tableName = TableNames.TIPOS_STAFF;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome_curto";
		columnNames[1] = "nome_ext";
	}
	
	public void save(TipoStaff tipoStaff) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, tipoStaff.getNome_curto())
		.setParameter(2, tipoStaff.getNome_ext())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Tipo(Staff) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(TipoStaff tipoStaff) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", tipoStaff.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Tipo(Staff) with ID \'" + tipoStaff.getId() + "\' has been deleted from the database");
	}
	
	public TipoStaff findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (TipoStaff) em.createNativeQuery(query, TipoStaff.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No staff type found with ID: " + id);
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