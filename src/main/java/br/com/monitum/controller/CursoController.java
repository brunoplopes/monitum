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
import br.com.monitum.service.CursoService;

@Controller
@Transactional
@RequestMapping("/curso")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	Logger logger = Logger.getLogger(CursoController.class);
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	@ResponseBody
	public String cadastro(@RequestParam(name="cursoNome") String cursoNome, @RequestParam(name="codCurso") String codCurso, HttpServletResponse response){
		try {
			return cursoService.criarCurso(cursoNome, codCurso);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			response.setStatus(e.getStatusCode());
			return e.getMessage();
		} catch(Exception e){
		}
		return null;
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})	
	public ModelAndView cursoListar(){
		ModelAndView model = new ModelAndView("Aplicacao/CursoLista");
		model.addObject("Curso", cursoService.getCurso());
		return model;
	}
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	@ResponseBody
	public String cursoEditar(@RequestParam(name="nomeCurso") String nomeCurso,@RequestParam(name="codigoCurso") String codigoCurso, 
			@RequestParam(name="idCurso") long idCurso, HttpServletResponse response){
		try {
			return cursoService.editarCurso(nomeCurso, codigoCurso, idCurso);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			response.setStatus(e.getStatusCode());
			return e.getMessage();
		}catch(Exception e){
		}
		return null;
	}	
}
