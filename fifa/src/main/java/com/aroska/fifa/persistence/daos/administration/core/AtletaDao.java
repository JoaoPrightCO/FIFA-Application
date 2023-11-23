package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Atleta;
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
public class AtletaDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public AtletaDao() {
		
		tableName = TableNames.ATLETAS;
		
		columnNames = new String[16];

		columnNames[0] = "nome";
		columnNames[1] = "morada";
		columnNames[2] = "tipo_atleta";
		columnNames[3] = "nif";
		columnNames[4] = "nacionalidade";
		columnNames[5] = "data_nasc";
		columnNames[6] = "sexo";
		columnNames[7] = "id_equipa";
		columnNames[8] = "posicao";
		columnNames[9] = "peso";
		columnNames[10] = "altura";
		columnNames[11] = "posse_bola";
		columnNames[12] = "golos_total";
		columnNames[13] = "golos_media";
		columnNames[14] = "substituicoes";
		columnNames[15] = "foto";
	}
	
	public void save(Atleta atleta) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, atleta.getNome())
		.setParameter(2, atleta.getMorada())
		.setParameter(3, atleta.getTipo_atleta())
		.setParameter(4, atleta.getNif())
		.setParameter(5, atleta.getNacionalidade())
		.setParameter(6, atleta.getData_nasc())
		.setParameter(7, atleta.getSexo())
		.setParameter(8, atleta.getId_equipa())
		.setParameter(9, atleta.getPosicao())
		.setParameter(10, atleta.getPeso())
		.setParameter(11, atleta.getAltura())
		.setParameter(12, atleta.getPosse_bola())
		.setParameter(13, atleta.getGolos_total())
		.setParameter(14, atleta.getGolos_media())
		.setParameter(15, atleta.getSubstituicoes())
		.setParameter(16, atleta.getFoto())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Atleta with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Atleta atleta) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", atleta.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Atleta with ID \'" + atleta.getId() + "\' has been deleted from the database");
	}
	
	public Atleta findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Atleta) em.createNativeQuery(query, Atleta.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No agency found with ID: " + id);
			return null;
		}
	}
	
	public Atleta findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[0], name);
		
		try
		{
			return (Atleta) em.createNativeQuery(query, Atleta.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No athlete found with name: " + name);
			return null;
		}
		
	}
	
	
	public List<Atleta> getAllAthletes() {
		
		String query = QueryUtil.createGetAllQuery(tableName);
		
		try
		{
			return (List<Atleta>) em.createNativeQuery(query, Atleta.class).getResultList();
		} 
		catch(NoResultException e)
		{	
			System.out.println("Could not fetch athletes list");
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