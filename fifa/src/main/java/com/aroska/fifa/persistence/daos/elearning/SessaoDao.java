package com.aroska.fifa.persistence.daos.elearning;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.elearning.Sessao;
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
public class SessaoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public SessaoDao() {
		
		tableName = TableNames.SESSOES;
		
		columnNames = new String[11];
		columnNames[0] = "id_curso";
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
	
	public void save(Sessao sessao) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, sessao.getId_curso())
		.setParameter(2, sessao.getNome())
		.setParameter(3, sessao.getDescricao())
		.setParameter(4, sessao.getLib_filepath())
		.setParameter(5, sessao.getIntervencao())
		.setParameter(6, sessao.getNr_ficheiros())
		.setParameter(7, sessao.getCarga_horaria())
		.setParameter(8, sessao.getEstado())
		.setParameter(9, sessao.getData_pub())
		.setParameter(10, sessao.getAutor())
		.setParameter(11, sessao.getImagem())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Sessao with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Sessao sessao) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", sessao.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Sessao with ID \'" + sessao.getId() + "\' has been deleted from the database");
	}
	
	public Sessao findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (Sessao) em.createNativeQuery(query, Sessao.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No session found with ID: " + id);
			return null;
		}
	}
	
	public Sessao findByName(String name) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[2], name);
		
		try
		{
			return (Sessao) em.createNativeQuery(query, Sessao.class).getSingleResult();
		} 
		catch(NoResultException e)
		{	
			System.out.println("No session found with name: " + name);
			return null;
		}
		
	}
	
	public List<Sessao> findByIdCurso(int idCurso) {
		
		String query = QueryUtil.createFindByColumn(tableName, columnNames[1], idCurso);
		
		try
		{
			return (List<Sessao>) em.createNativeQuery(query, Sessao.class).getResultList();
		}
		catch(NoResultException e) {
			System.out.println("No session found with course ID: " + idCurso);
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