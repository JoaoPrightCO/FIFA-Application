package com.aroska.fifa.persistence.daos.elearning;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.elearning.Curso;
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
public class CursoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public CursoDao() {
		
		tableName = TableNames.CURSOS;
		
		columnNames = new String[11];
		columnNames[0] = "id_formacao";
		columnNames[1] = "nome";
		columnNames[2] = "descricao";
		columnNames[3] = "lib_filepath";
		columnNames[4] = "intervencao";
		columnNames[5] = "nr_ficheiros";
		columnNames[6] = "carga_horaria";
		columnNames[7] = "estado";
		columnNames[8] = "data_pub";
		columnNames[9] = "autor";
		columnNames[10] = "imagem";
	}
	
	public void save(Curso curso) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, curso.getId_formacao())
		.setParameter(2, curso.getNome())
		.setParameter(3, curso.getDescricao())
		.setParameter(4, curso.getLib_filepath())
		.setParameter(5, curso.getIntervencao())
		.setParameter(6, curso.getNr_ficheiros())
		.setParameter(7, curso.getCarga_horaria())
		.setParameter(8, curso.getEstado())
		.setParameter(9, curso.getData_pub())
		.setParameter(10, curso.getAutor())
		.setParameter(11, curso.getImagem())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Curso with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Curso curso) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", curso.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Curso with ID \'" + curso.getId() + "\' has been deleted from the database");
	}
	
	public Curso findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Curso) em.createNativeQuery(query, Curso.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No course found with ID: " + id);
			return null;
		}
	}
	
	public Curso findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[2], name);
		
		try
		{
			return (Curso) em.createNativeQuery(query, Curso.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No course found with name: " + name);
			return null;
		}
		
	}
	
	public List<Curso> findByIdFormacao(int idFormacao) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[1], idFormacao);
		
		try
		{
			return (List<Curso>) em.createNativeQuery(query, Curso.class).getResultList();
		}
		catch(NoResultException e) {
			System.out.println("No course found with formacao ID: " + idFormacao);
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