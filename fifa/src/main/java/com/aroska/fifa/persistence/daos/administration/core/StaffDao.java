package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Staff;
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
public class StaffDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public StaffDao() {
		
		tableName = TableNames.STAFF;
		
		columnNames = new String[9];
		
		columnNames[0] = "nome";
		columnNames[1] = "morada";
		columnNames[2] = "tipo_staff";
		columnNames[3] = "nif";
		columnNames[4] = "nacionalidade";
		columnNames[5] = "data_nasc";
		columnNames[6] = "sexo";
		columnNames[7] = "cargo";
		columnNames[8] = "foto";
	}
	
	public void save(Staff staff) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, staff.getNome())
		.setParameter(2, staff.getMorada())
		.setParameter(3, staff.getTipo_staff())
		.setParameter(4, staff.getNif())
		.setParameter(5, staff.getNacionalidade())
		.setParameter(6, staff.getData_nasc())
		.setParameter(7, staff.getSexo())
		.setParameter(8, staff.getCargo())
		.setParameter(9, staff.getFoto())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Staff with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Staff staff) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", staff.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Staff with ID \'" + staff.getId() + "\' has been deleted from the database");
	}
	
	public Staff findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Staff) em.createNativeQuery(query, Staff.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No staff member found with ID: " + id);
			return null;
		}
	}
	
	public Staff findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Staff) em.createNativeQuery(query, Staff.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No staff member found with name: " + name);
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