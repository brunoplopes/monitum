package br.com.monitum.controller;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.ConteudoDTO;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.security.UsuarioContext;
import br.com.monitum.service.ConteudoService;

@RestController
@Transactional
@RequestMapping("/conteudo")
public class ConteudoController {
	@Autowired
	private ConteudoService conteudoService;
	
	Logger logger = Logger.getLogger(ConteudoController.class);
	
	@RequestMapping(value="/criar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView conteudo(@PathVariable long idTurma){
		ModelAndView model = new ModelAndView("Aplicacao/ConteudoCadastro");
		model.addObject("ConteudoDTO", conteudoService.getConteudo(idTurma));
		model.addObject("tipoConteudoList", conteudoService.getTipoConteudoList());
		return model;
	}
	
	@RequestMapping(value="/criar/{idTurma}", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView conteudo(@Valid @ModelAttribute("ConteudoDTO") ConteudoDTO dto, @PathVariable long idTurma){
		try {
			conteudoService.criarConteudo(dto, idTurma);
		}catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		return new ModelAndView("redirect:/conteudo/listar/" + idTurma);
	}
	
	@RequestMapping(value="/listar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView conteudos(@PathVariable long idTurma){
		ModelAndView model = new ModelAndView("Aplicacao/Conteudo");
		List<ConteudoDTO> conteudoList = conteudoService.getConteudoList(idTurma);
		Collections.reverse(conteudoList);
		model.addObject("ConteudoList", conteudoList);
		model.addObject("idUsuario", UsuarioContext.getUsuarioLogado().getId());
		model.addObject("idTurma", idTurma);
		return model;
	}
	
	@RequestMapping(value="/deletar/{idConteudo}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView deletarConteudo(@PathVariable long idConteudo) throws Exception{
		long idTurma = conteudoService.getConteudoDTOId(idConteudo).getIdTurma();
		conteudoService.deletarConteudo(idConteudo);
		return new ModelAndView("redirect:/conteudo/listar/" + idTurma);
	}
	
	@RequestMapping(value="/editar/{idConteudo}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView editarConteudo(@PathVariable long idConteudo) throws Exception{
		ModelAndView model = new ModelAndView("Aplicacao/ConteudoEditar");
		model.addObject("ConteudoDTO", conteudoService.getConteudoDTOId(idConteudo));
		model.addObject("tipoConteudoList", conteudoService.getTipoConteudoList());
		return model;
	}
	
	@RequestMapping(value="/editar/{idConteudo}", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView editaConteudo(@Valid @ModelAttribute("ConteudoDTO") ConteudoDTO dto, @PathVariable long idConteudo){
		try {
			conteudoService.editarConteudo(dto);
		}catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		return new ModelAndView("redirect:/conteudo/listar/" + conteudoService.getConteudoDTOId(idConteudo).getIdTurma());
	}
}
