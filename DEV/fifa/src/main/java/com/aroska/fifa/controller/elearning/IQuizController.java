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
import com.aroska.fifa.persistence.daos.elearning.QuizDao;
import com.aroska.fifa.persistence.model.elearning.Quiz;
import com.aroska.fifa.requests.dtos.elearning.QuizDto;
import com.aroska.fifa.util.AccountUtil;
import com.aroska.fifa.util.StringUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/fifa/quizzes")
public class IQuizController {

	@Autowired
	QuizDao quizDao;
	
	//TODO este controlador é um sistema de inscrição/subscrição, é diferente
	@PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto, @RequestParam int ids, HttpServletRequest request){

		if(!AccountUtil.validateLogin(request, TiposUtilizador.ADMIN.getId())) {
			return new ResponseEntity<>("User not logged in or insufficient permissions to perform action", HttpStatus.BAD_REQUEST);
		}
		
		if(!StringUtil.validate(quizDto.getValues())) {
			return new ResponseEntity<>("Some parameters are not correct", HttpStatus.BAD_REQUEST);
		}
		
		Quiz quiz = new Quiz();
		quiz.setId_sessao(ids);
		quiz.setDescricao(quizDto.getDescricao());
		
		try {
            quizDao.save(quiz);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not create quiz, possible duplicate value. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Duplicate entry/value detected", HttpStatus.BAD_REQUEST);
		} 
		
        return new ResponseEntity<>("Quiz successfully created", HttpStatus.OK);
    }
	
	@PostMapping("/edit")
	public ResponseEntity<String> editQuiz(@RequestBody QuizDto quizDto, @RequestParam String idq) {
		
		ArrayList<String> columns = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		String[] dtoValues = quizDto.getValues();
		
		for(int i = 0; i<dtoValues.length; i++) {
			if(dtoValues[i] != null && dtoValues[i] != "null") {
				columns.add(quizDao.getColumnNames()[i]);
				values.add(dtoValues[i]);
			}
		}
		
		try {
			quizDao.edit(idq, columns, values);
		} catch (DataIntegrityViolationException e) {
			System.out.println("Could not edit quiz. Message:\n"
								+ e.getMessage());
			return new ResponseEntity<>("Error editing quiz", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>("Edited quiz with ID " + idq, HttpStatus.OK);
	}
	
	@GetMapping("/get")
	public ResponseEntity<String> getQuiz(@RequestParam int idSessao) {
		List<Quiz> quizList = (List<Quiz>) quizDao.findByIdSessao(idSessao);
		
		String quizzesString = "";
		
		for(int i = 0; i<quizList.size(); i++) {
			Quiz u = quizList.get(i);
			quizzesString+=u.getDescricao() + "\n";
		}
		
		if(quizList == null || quizList.isEmpty()) {
			return new ResponseEntity<>("Could not find quizzes with session ID <" + idSessao + ">", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("Sessao with id '" + idSessao + "' has the following quizzes: \n" + quizzesString, HttpStatus.OK);
	}
}
