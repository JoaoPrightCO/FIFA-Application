package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Jogo;
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
public class JogoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public JogoDao() {
		
		tableName = TableNames.JOGOS;
		
		columnNames = new String[13];
		
		columnNames[0] = "id_equipa1";
		columnNames[1] = "id_equipa2";
		columnNames[2] = "id_evento";
		columnNames[3] = "localizacao";
		columnNames[4] = "dur_prolongamento";
		columnNames[5] = "equipa_vencedora";
		columnNames[6] = "golos_equipa1";
		columnNames[7] = "golos_equipa2";
		columnNames[8] = "nr_substituicoes";
		columnNames[9] = "nr_faltas";
		columnNames[10] = "posse_equipa1";
		columnNames[11] = "posse_equipa2";
		columnNames[12] = "est_metereologico";
	}
	
	public void save(Jogo jogo) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, jogo.getId_equipa1())
		.setParameter(2, jogo.getId_equipa2())
		.setParameter(3, jogo.getId_evento())
		.setParameter(4, jogo.getLocalizacao())
		.setParameter(5, jogo.getDur_prolongamento())
		.setParameter(6, jogo.getEquipa_vencedora())
		.setParameter(7, jogo.getGolos_equipa1())
		.setParameter(8, jogo.getGolos_equipa2())
		.setParameter(9, jogo.getNr_substituicoes())
		.setParameter(10, jogo.getNr_faltas())
		.setParameter(11, jogo.getPosse_equipa1())
		.setParameter(12, jogo.getPosse_equipa2())
		.setParameter(13, jogo.getEst_metereologico())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Jogo with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Jogo jogo) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", jogo.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Jogo with ID \'" + jogo.getId() + "\' has been deleted from the database");
	}
	
	public Jogo findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Jogo) em.createNativeQuery(query, Jogo.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No match found with ID: " + id);
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