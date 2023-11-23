package com.aroska.fifa.persistence.daos.elearning;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.elearning.Quiz;
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
public class QuizDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public QuizDao() {
		
		tableName = TableNames.QUIZZES;
		
		columnNames = new String[2];
		columnNames[0] = "id_sessao";
		columnNames[1] = "descricao";
	}
	
	public void save(Quiz quiz) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, quiz.getId_sessao())
		.setParameter(2, quiz.getDescricao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Quiz with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Quiz quiz) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", quiz.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Quiz with ID \'" + quiz.getId() + "\' has been deleted from the database");
	}
	
	public List<Quiz> findByIdSessao(int idSessao) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[1], idSessao);
		
		try
		{
			return (List<Quiz>) em.createNativeQuery(query, Quiz.class).getResultList();
		}
		catch(NoResultException e) {
			System.out.println("No quizes found with for session ID: " + idSessao);
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