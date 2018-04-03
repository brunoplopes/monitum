package br.com.monitum.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.monitum.dto.GrupoDTO;
import br.com.monitum.dto.OptionDTO;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.service.AtividadeService;
import br.com.monitum.service.GrupoService;

@RestController
@Transactional
@RequestMapping("/grupo")
public class GrupoController {
	@Autowired
	private AtividadeService atividadeService;
	@Autowired
	private GrupoService grupoService;
	
	Logger logger = Logger.getLogger(GrupoController.class);
	
	@RequestMapping(value = "/listar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView listarAtividades(@PathVariable long idTurma ,@RequestParam(value = "mensagem", required = false) String mensagem){
		ModelAndView model = new ModelAndView("Aplicacao/Grupo");
		if(mensagem != null && mensagem.equals("ok"))
			model.addObject("mensagem", "Grupo(s) criado(s) com com sucesso.");
		model.addObject("atividadeList", atividadeService.getAtividadesTurma(idTurma));
		model.addObject("GrupoDTO", grupoService.getListGrupoProfessorDTO(idTurma));
		return model;
	}
	@RequestMapping(value = "/criar", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView criarGrupo(@RequestParam(name="idAtividade") long idAtividade){
		return new ModelAndView("Aplicacao/GrupoCriar").addObject("GrupoDTO", new GrupoDTO(idAtividade));
	}
	@RequestMapping(value = "/entrar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_ESTUDANTE})
	public ModelAndView entrarGrupo(@PathVariable long idTurma, @RequestParam(name="idGrupo") long idGrupo){
		
		try {
			grupoService.entrarGrupo(idGrupo, idTurma);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		
		return new ModelAndView("redirect:/grupo/listarGrupos/" + idTurma +"?mensagem=ok");
	}
	@RequestMapping(value = "/criar/{idAtividade}", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView criarGrupo(@ModelAttribute("GrupoDTO") GrupoDTO grupo){
		try {
			grupoService.criarGrupos(grupo);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		
		return new ModelAndView("redirect:/grupo/listar/" + grupo.getIdTurma() +"?mensagem=ok");
	}
	@RequestMapping(value = "/grupos/{idAtividade}", method = RequestMethod.GET, produces = "application/json")
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public List<OptionDTO> grupos(@PathVariable long idAtividade){
		return grupoService.getGruposAtividade(idAtividade);
	}
	@RequestMapping(value = "/integrantes/{idGrupo}", method = RequestMethod.GET, produces = "application/json")
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public List<OptionDTO> integrantesGrupo(@PathVariable long idGrupo){
		return grupoService.getIntegrantesGrupo(idGrupo);
	}
	@RequestMapping(value = "/remover/{idGrupoAluno}", method = RequestMethod.GET, produces = "application/json")
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public void removerGrupo(@PathVariable long idGrupoAluno){
		try {
			grupoService.removerGrupoAluno(idGrupoAluno);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		
	}
	
	@RequestMapping(value = "/listarGrupos/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_ESTUDANTE})
	public ModelAndView listarGrupos(@PathVariable long idTurma ,@RequestParam(value = "mensagem", required = false) String mensagem){
		ModelAndView model = new ModelAndView("Aplicacao/GrupoEstudante");
		if(mensagem != null && mensagem.equals("ok"))
			model.addObject("mensagem", "Voc� foi inclu�do no grupo com com sucesso.");
		model.addObject("atividadeList", atividadeService.getAtividadesTurma(idTurma));
		model.addObject("AtividadeGrupoList", grupoService.getListGrupoAlunoDTO(idTurma));
		model.addObject("idTurma",idTurma);
		return model;
	}
}
