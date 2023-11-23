package com.aroska.fifa.controller.elearning;

import java.util.ArrayList;
import java.util.List;

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
import com.aroska.fifa.persistence.daos.elearning.SessaoDao;
import com.aroska.fifa.persistence.model.elearning.Sessao;
import com.aroska.fifa.requests.dtos.elearning.SessaoDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/sessoes")
public class ISessaoController {

	@Autowired
	SessaoDao sessaoDao;
	
	@PostMapping("/create")
    public ResponseEntity<String> createSessao(@RequestBody SessaoDto sessaoDto, @RequestParam int idc, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(sessaoDto.getValues())) {
			return new ResponseEntity<>("Some parameters are not correct", HttpStatus.BAD_REQUEST);
		}
		
		Sessao sessao = new Sessao();
		sessao.setId_curso(idc);
		sessao.setNome(sessaoDto.getNome());
		sessao.setDescricao(sessaoDto.getDescricao());
		sessao.setLib_filepath(sessaoDto.getLib_filepath());
		sessao.setIntervencao(sessaoDto.getIntervencao());
		sessao.setNr_ficheiros(sessaoDto.getNr_ficheiros());
		sessao.setCarga_horaria(sessaoDto.getCarga_horaria());
		sessao.setEstado(sessaoDto.getEstado());
		sessao.setData_pub(sessaoDto.getData_pub());
		sessao.setAutor(sessaoDto.getAutor());
		sessao.setImagem(sessaoDto.getImagem());
		
		try {
            sessaoDao.save(sessao);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create session, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Session successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editSessao(@RequestBody SessaoDto sessaoDto, @RequestParam String ids) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = sessaoDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(sessaoDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			sessaoDao.edit(ids, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit sessao. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing sessao", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited sessao with ID " + ids, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getSessao(@RequestParam int idCurso) {
		List<Sessao> sessaoList = (List<Sessao>) sessaoDao.findByIdCurso(idCurso);
		
		String sessoesString = "";
		
		for(int i = 0; i<sessaoList.size(); i++) {
			Sessao u = sessaoList.get(i);
			sessoesString+=u.getNome() + "\n";
		}
		
		if(sessaoList == null || sessaoList.isEmpty()) {
			return new ResponseEntity<>("Could not find sessoes with course ID <" + idCurso + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Curso with id '" + idCurso + "' has the following sessoes: \n" + sessoesString, HttpStatus.OK);
	}
}
