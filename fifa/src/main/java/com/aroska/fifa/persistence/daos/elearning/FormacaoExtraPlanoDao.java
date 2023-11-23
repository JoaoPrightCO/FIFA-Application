package com.aroska.fifa.persistence.daos.elearning;

import com.aroska.fifa.constants.TableNames;
import com.aroska.fifa.persistence.model.elearning.FormacaoExtraPlano;
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
public class FormacaoExtraPlanoDao {

	@PersistenceContext
	EntityManager em;
	
	String tableName;
	String[] columnNames;
	
	public FormacaoExtraPlanoDao() {
		
		tableName = TableNames.FORMACOES_EXTRAPLANO;
		
		columnNames = new String[19];
		columnNames[0] = "descricao";
		columnNames[1] = "unidade_organica";
		columnNames[2] = "requisitante";
		columnNames[3] = "area";
		columnNames[4] = "justificacao";
		columnNames[5] = "objectivos_esp";
		columnNames[6] = "carga_horaria";
		columnNames[7] = "trab_prop";
		columnNames[8] = "entidade";
		columnNames[9] = "tipo";
		columnNames[10] = "forma_org";
		columnNames[11] = "data_prevista";
		columnNames[12] = "horario_realizacao";
		columnNames[13] = "local_realizacao";
		columnNames[14] = "custo";
		columnNames[15] = "conteudo_prog";
		columnNames[16] = "estado";
		columnNames[17] = "data_pub";
		columnNames[18] = "autor";
	}
	
	public void save(FormacaoExtraPlano formacaoExtraPlano) throws DataIntegrityViolationException {		
		
		String query = QueryUtil.createInsertQuery(tableName, columnNames);
		
		em.createNativeQuery(query)
		.setParameter(1, formacaoExtraPlano.getDescricao())
		.setParameter(2, formacaoExtraPlano.getUnidade_organica())
		.setParameter(3, formacaoExtraPlano.getRequisitante())
		.setParameter(4, formacaoExtraPlano.getArea())
		.setParameter(5, formacaoExtraPlano.getJustificacao())
		.setParameter(6, formacaoExtraPlano.getObjectivos_esp())
		.setParameter(7, formacaoExtraPlano.getCarga_horaria())
		.setParameter(8, formacaoExtraPlano.getTrab_prop())
		.setParameter(9, formacaoExtraPlano.getEntidade())
		.setParameter(10, formacaoExtraPlano.getTipo())
		.setParameter(11, formacaoExtraPlano.getForma_org())
		.setParameter(12, formacaoExtraPlano.getData_prevista())
		.setParameter(13, formacaoExtraPlano.getHorario_realizacao())
		.setParameter(14, formacaoExtraPlano.getLocal_realizacao())
		.setParameter(15, formacaoExtraPlano.getCusto())
		.setParameter(16, formacaoExtraPlano.getConteudo_prog())
		.setParameter(17, formacaoExtraPlano.getEstado())
		.setParameter(18, formacaoExtraPlano.getData_pub())
		.setParameter(19, formacaoExtraPlano.getAutor())
		.executeUpdate();
	}
	
	public void edit(String id, ArrayList<String> columns, ArrayList<String> values) {
		
		String query = QueryUtil.createEditQuery(tableName, columns, values, id);
		
		em.createNativeQuery(query).executeUpdate();
	
		System.out.println("Formacao(Extraplano) with ID \'" + id + "\' successfully edited");
	}
	
	public void delete(FormacaoExtraPlano formacaoExtraPlano) {
		
		String query = QueryUtil.createDeleteQuery(tableName, "id", formacaoExtraPlano.getId());
		
		em.createNativeQuery(query).executeUpdate();
		
		System.out.println("Formacao(Extraplano) with ID \'" + formacaoExtraPlano.getId() + "\' has been deleted from the database");
	}
	
	public FormacaoExtraPlano findById(int id) {
		
		String query = QueryUtil.createFindByIdQuery(tableName, id);
		
		try
		{
			return (FormacaoExtraPlano) em.createNativeQuery(query, FormacaoExtraPlano.class).getSingleResult();
		}
		catch(NoResultException e) {
			System.out.println("No formacao(extraplano) found with ID: " + id);
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