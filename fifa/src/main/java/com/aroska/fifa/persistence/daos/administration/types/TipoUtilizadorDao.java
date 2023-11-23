package com.aroska.fifa.persistence.daos.administration.types;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.types.TipoUtilizador;
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
public class TipoUtilizadorDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public TipoUtilizadorDao() {
		
		tableName = TableNames.TIPOS_UTILIZADOR;
		
		columnNames = new String[2];
		
		columnNames[0] = "nome_curto";
		columnNames[1] = "nome_ext";
	}
	
	public void save(TipoUtilizador tipoUtilizador) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, tipoUtilizador.getNome_curto())
		.setParameter(2, tipoUtilizador.getNome_ext())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Tipo(Utilizador) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(TipoUtilizador tipoUtilizador) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", tipoUtilizador.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Tipo(Utilizador) with ID \'" + tipoUtilizador.getId() + "\' has been deleted from the database");
	}
	
	public TipoUtilizador findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (TipoUtilizador) em.createNativeQuery(query, TipoUtilizador.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No user type found with ID: " + id);
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