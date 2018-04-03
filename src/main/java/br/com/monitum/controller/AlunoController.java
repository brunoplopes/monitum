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
import br.com.monitum.service.AlunoService;

@Controller
@Transactional
@RequestMapping("/aluno")
public class AlunoController {
	@Autowired
	private AlunoService alunoService;
	Logger logger = Logger.getLogger(AlunoController.class);
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_COORDENADOR})	
	public ModelAndView alunoListar(){
		ModelAndView model = new ModelAndView("Aplicacao/AlunoLista");
		model.addObject("Aluno", alunoService.getAluno());
		return model;
	}
	@RequestMapping(value = "/editar", method = RequestMethod.GET)
	@ResponseBody
	public String alunoEditar(@RequestParam(name="nomeAluno") String nomeAluno,@RequestParam(name="prontuarioAluno") String prontuarioAluno, 
			@RequestParam(name="idAluno") long idAluno, HttpServletResponse response){
		try {
			return alunoService.editarAluno(nomeAluno, prontuarioAluno, idAluno);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			response.setStatus(e.getStatusCode());
			return e.getMessage();
		}
	}	
}
