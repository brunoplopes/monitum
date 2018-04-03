package br.com.monitum.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.ProfessorDTO;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.service.ProfessorService;

@Controller
@Transactional
@RequestMapping("/professor")
public class ProfessorController {
	@Autowired
	private ProfessorService professorService;
	
	Logger logger = Logger.getLogger(ProfessorController.class);
	
	@RequestMapping(value="/cadastro", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView professor(@RequestParam(value = "_csrf", required = false) String _csrf){
		return new ModelAndView("Aplicacao/Professor").addObject("professorDTO", new ProfessorDTO());
	}
	@RequestMapping(value="/cadastro", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView cadastrar(@ModelAttribute("professorDTO") ProfessorDTO dto, @RequestParam(value = "_csrf", required = false) String _csrf){
		try {
			professorService.cadastrar(dto);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("erro na aplicação: " + e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/professor/");
	}
	@RequestMapping(value = "", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_COORDENADOR})	
	public ModelAndView professorListar(){
		ModelAndView model = new ModelAndView("Aplicacao/ProfessorLista");
		model.addObject("Professor", professorService.getProfessor());
		return model;
	}
	@RequestMapping(value = "/editar", method = RequestMethod.GET)
	@ResponseBody
	public String professorEditar(@RequestParam(name="nomeProfessor") String nomeProfessor,@RequestParam(name="registroProfessor") String registroProfessor, 
			@RequestParam(name="idProfessor") long idProfessor, @RequestParam(name="emailProfessor") String emailProfessor, 
			@RequestParam(name="coordenador") boolean coordenador, HttpServletResponse response){
		try {
			return professorService.editarProfessor(nomeProfessor, registroProfessor, emailProfessor, idProfessor, coordenador);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			response.setStatus(e.getStatusCode());
			return e.getMessage();
		}
	}	
}
