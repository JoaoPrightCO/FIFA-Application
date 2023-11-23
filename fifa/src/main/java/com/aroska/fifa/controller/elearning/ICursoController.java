package com.aroska.fifa.controller.elearning;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aroska.fifa.constants.TiposUtilizador;
import com.aroska.fifa.persistence.daos.elearning.CursoDao;
import com.aroska.fifa.persistence.model.elearning.Curso;
import com.aroska.fifa.requests.dtos.elearning.CursoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/cursos")
public class ICursoController {

	@Autowired
	CursoDao cursoDao;
	
	
	//TODO verificar se é seguro meter o id da formacao no URL ou se é melhor guardar no atributo de sessão
	@PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody CursoDto cursoDto, @RequestParam int idf, HttpServletRequest request){

		if(idf == 0) {
			return new ResponseEntity<>("Could not associate formacao ID", HttpStatus.BAD_REQUEST);
		}
		
		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(cursoDto.getValues())) {
			return new ResponseEntity<>("Some parameters are not correct", HttpStatus.BAD_REQUEST);
		}
		
		//TODO buscar do request o id da formação para a qual estamos a criar o curso
		Curso curso = new Curso();
		curso.setId_formacao(idf);
		curso.setNome(cursoDto.getNome());
		curso.setDescricao(cursoDto.getDescricao());
		curso.setLib_filepath(cursoDto.getLib_filepath());
		curso.setIntervencao(cursoDto.getIntervencao());
		curso.setNr_ficheiros(cursoDto.getNr_ficheiros());
		curso.setCarga_horaria(cursoDto.getCarga_horaria());
		curso.setEstado(cursoDto.getEstado());
		curso.setData_pub(cursoDto.getData_pub());
		curso.setAutor(cursoDto.getAutor());
		curso.setImagem(cursoDto.getImagem());
		
		try {
            cursoDao.save(curso);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create course, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Course successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editCourse(@RequestBody CursoDto cursoDto, @RequestParam String idc) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = cursoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(cursoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			cursoDao.edit(idc, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit curso. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing curso", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited curso with ID " + idc, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getCourse(@RequestParam int id) {
		Curso curso = cursoDao.findById(id);

		if(curso == null) {
			return new ResponseEntity<>("Could not find course with ID <" + id + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Found course named " + curso.getNome(), HttpStatus.OK);
	}
}
