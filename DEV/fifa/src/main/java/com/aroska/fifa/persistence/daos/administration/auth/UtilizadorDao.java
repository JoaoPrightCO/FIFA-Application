package com.aroska.fifa.persistence.daos.administration.auth;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.auth.Utilizador;
import com.aroska.fifa.util.QueryUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UtilizadorDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public UtilizadorDao() {
		
		tableName = TableNames.UTILIZADORES;
		
		columnNames = new String[11];

		columnNames[0] = "nome";
		columnNames[1] = "username";
		columnNames[2] = "email";
		columnNames[3] = "password";
		columnNames[4] = "morada";
		columnNames[5] = "tipo_utilizador";
		columnNames[6] = "nif";
		columnNames[7] = "nacionalidade";
		columnNames[8] = "data_nasc";
		columnNames[9] = "sexo";
		columnNames[10] = "foto";
	}
	
	public void save(Utilizador utilizador) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, utilizador.getNome())
		.setParameter(2, utilizador.getUsername())
		.setParameter(3, utilizador.getEmail())
		.setParameter(4, utilizador.getPassword())
		.setParameter(5, utilizador.getMorada())
		.setParameter(6, utilizador.getTipo_utilizador())
		.setParameter(7, utilizador.getNif())
		.setParameter(8, utilizador.getNacionalidade())
		.setParameter(9, utilizador.getData_nasc())
		.setParameter(10, utilizador.getSexo())
		.setParameter(11, utilizador.getFoto())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("User with ID '" + id + "' successfully edited");
	}
	
	public void delete(Utilizador utilizador) {
		
		String query = QueryUtil.createDeleteQuery(tableName, columnNames[6], utilizador.getNif());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("User with NIF \'" + utilizador.getNif() + "\' has been deleted from the database");
	}
	
	public Utilizador findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Utilizador) em.createNativeQuery(query, Utilizador.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No user found with ID: " + id);
			return null;
		}
	}
	
	public Utilizador findByUsername(String username) {
		
		String query = QueryUtil.createFindByUsernameQuery(tableName, username);
		
		try
		{
			return (Utilizador) em.createNativeQuery(query, Utilizador.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No user found with username: " + username);
			return null;
		}
	}
	
	public Utilizador findByEmail(String email) {
		
		String query = QueryUtil.createFindByEmailQuery(tableName, email);
		
		try
		{
			return (Utilizador) em.createNativeQuery(query, Utilizador.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No user found with email: " + email);
			return null;
		}
		
	}
	
	public Utilizador findByUsernameOrEmail(String usernameOrEmail) {
		
		String query = QueryUtil.createFindByUsernameOrEmailQuery(tableName, usernameOrEmail);
		
		try
		{
			return (Utilizador) em.createNativeQuery(query, Utilizador.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No user found with username/email: " + usernameOrEmail);
			return null;
		}
	}
	
	public List<Utilizador> getAllUsers() {
		
		String query = QueryUtil.createGetAllQuery(tableName);
		
		try
		{
			return (List<Utilizador>) em.createNativeQuery(query, Utilizador.class).getResultList();
		}
		catch(NoResultException e) {
			System.out.println("Could not fetch user list");
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