package br.com.monitum.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.monitum.dto.FrequenciaDTO;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.service.FrequenciaService;

@Controller
@Transactional
@RequestMapping("/frequencia")
public class FrequenciaController {
	@Autowired
	private FrequenciaService frequenciaService;
	
	Logger logger = Logger.getLogger(FrequenciaController.class);
	
	@RequestMapping(value = "/turma/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView frequencia(@PathVariable long idTurma){
		ModelAndView model = new ModelAndView("Aplicacao/FrequenciaCadastro");
		model.addObject("FrequenciaDTO", frequenciaService.getFrenquenciaTurmaDTO(idTurma));
		model.addObject("periodoList", frequenciaService.getPeriodoList());
		model.addObject("atividadeList", frequenciaService.getListAtividadesTurma(idTurma));
		return model;
	}
	@RequestMapping(value = "/editar/{idFrequencia}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView frequenciaEditar(@PathVariable long idFrequencia){
		ModelAndView model = new ModelAndView("Aplicacao/FrequenciaCadastro");
		model.addObject("periodoList", frequenciaService.getPeriodoList());
		model.addObject("atividadeList", frequenciaService.getListAtividadeTurma(idFrequencia));
		model.addObject("FrequenciaDTO", frequenciaService.getFrenquenciaEditarDTO(idFrequencia));
		return model;
	}

	@RequestMapping(value = "/turma/{idTurma}", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView frequencia(@PathVariable long idTurma, @Valid @ModelAttribute("FrequenciaDTO") FrequenciaDTO dto, BindingResult result){
		try {
			frequenciaService.registrarFrenquencia(dto);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		
		return new ModelAndView("redirect:/frequencia/listar/" + idTurma);
	}
	@RequestMapping(value = "/listar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView listar(@PathVariable long idTurma, @RequestParam(value = "mensagem", required = false) String mensagem){
		ModelAndView model = new ModelAndView("Aplicacao/Frequencia");
		if(mensagem != null && mensagem.equals("ok"))
		 model.addObject("mensagem", "Frequ�ncia registrada com com sucesso.");
		 model.addObject("registroList", frequenciaService.getListFrequenciaDTO(idTurma));
		 model.addObject("idTurma", idTurma);
		return model;
	}
}
