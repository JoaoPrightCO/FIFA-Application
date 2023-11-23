package com.aroska.fifa.persistence.daos.administration.core;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.administration.core.Video;
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
public class VideoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public VideoDao() {
		
		tableName = TableNames.VIDEOS;
		
		columnNames = new String[4];
		
		columnNames[0] = "id_jogo";
		columnNames[1] = "id_camara";
		columnNames[2] = "video";
		columnNames[3] = "duracao";
	}
	
	public void save(Video video) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, video.getId_jogo())
		.setParameter(2, video.getId_camara())
		.setParameter(3, video.getVideo())
		.setParameter(4, video.getDuracao())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Video with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(Video video) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", video.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Video with ID \'" + video.getId() + "\' has been deleted from the database");
	}
	
	public Video findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);

		try
		{
			return (Video) em.createNativeQuery(query, Video.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No video found with ID: " + id);
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