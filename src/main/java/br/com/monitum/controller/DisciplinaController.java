package br.com.monitum.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.service.DisciplinaService;

@Controller
@Transactional
@RequestMapping("/disciplina")
public class DisciplinaController {
	@Autowired
	private DisciplinaService disciplinaService;
	
	Logger logger = Logger.getLogger(DisciplinaController.class);
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	@ResponseBody
	public String cadastro(@RequestParam(name="nomeDisciplina") String nomeDisciplina,@RequestParam(name="codigoDisciplina") String codigoDisciplina, HttpServletResponse response){
		try {
			return disciplinaService.criarDisciplina(nomeDisciplina, codigoDisciplina);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			response.setStatus(e.getStatusCode());
			return e.getMessage();
		}catch(Exception e){
		}
		return null;
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})	
	public ModelAndView disciplinaListar(){
		ModelAndView model = new ModelAndView("Aplicacao/DisciplinaLista");
		model.addObject("Disciplina", disciplinaService.getDisciplina());
		return model;
	}
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	@ResponseBody
	public String disciplinaEditar(@RequestParam(name="nomeDisciplina") String nomeDisciplina,@RequestParam(name="codigoDisciplina") String codigoDisciplina, 
			@RequestParam(name="idDisciplina") long idDisciplina, HttpServletResponse response){
		try {
			return disciplinaService.editarDisciplina(nomeDisciplina, codigoDisciplina, idDisciplina);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			response.setStatus(e.getStatusCode());
			return e.getMessage();
		}catch(Exception e){
		}
		return null;
	}	
}
