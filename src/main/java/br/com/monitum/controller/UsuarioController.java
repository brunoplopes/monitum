package br.com.monitum.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
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

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.SenhaDTO;
import br.com.monitum.dto.UsuarioDTO;
import br.com.monitum.entity.Usuario;
import br.com.monitum.repository.UsuarioRepository;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.service.TurmaAlunoService;
import br.com.monitum.service.UsuarioService;

@Controller
@Transactional
@RequestMapping("/usuario")
public class UsuarioController {
	
	Logger logger = Logger.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TurmaAlunoService turmaAlunoService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView usuario(){
		ModelAndView model = new ModelAndView("Aplicacao/Usuario");
		model.addObject("listaTurmas",turmaAlunoService.getTurmasDTO());
		return model;
	}
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView cadastro(){
		return new ModelAndView("Cadastro").addObject("UsuarioDTO", new UsuarioDTO());
	}
	@RequestMapping(value = "/perfil", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView perfil(@RequestParam(value = "mensagem", required = false) String mensagem){ // nao sei se precisa do loggin
		ModelAndView model = new ModelAndView("Aplicacao/Perfil");
		if(mensagem != null && mensagem.equals("senha"))
			model.addObject("mensagem", "Senha alterada com sucesso.");
		if(mensagem != null && mensagem.equals("dados"))
			model.addObject("mensagem", "Dados alterados com sucesso.");
		model.addObject("UsuarioDTO", usuarioService.getUsuarioDTO());
		model.addObject("SenhaDTO", new SenhaDTO());
		return model;
	}
	@RequestMapping(value = "/alterarSenha", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView alterarSenha(@ModelAttribute("SenhaDTO") SenhaDTO dto){
		try {
			usuarioService.alterarSenhaPerfil(dto);
		}catch(CustomException e){
			logger.error("Erro na aplicação: " + e.getMessage());
			ModelAndView model = new ModelAndView("Aplicacao/Perfil");
			model.addObject("mensagemError", e.getMessage());
			model.addObject("UsuarioDTO", usuarioService.getUsuarioDTO());
			model.addObject("SenhaDTO", new SenhaDTO());
			return model;
		}
		catch (Exception e) {
			logger.error("Erro na aplicação: " + e.toString());
		}
		return new ModelAndView("redirect:/usuario/perfil?mensagem=senha");
	}
	@RequestMapping(value = "/perfil", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView perfilEditar(@ModelAttribute("UsuarioDTO") UsuarioDTO dto){
		try {
			usuarioService.editarPerfil(dto);
		} catch (CustomException e) {
			logger.error("Erro na aplicação: " + e.getMessage());
			return new ModelAndView("Aplicacao/Perfil").addObject("mensagemError", e.getMessage());
		}catch (IOException e) {
			logger.error("Erro na aplicação: " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/usuario/perfil?mensagem=dados");
	}
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid @ModelAttribute("UsuarioDTO") UsuarioDTO dto, BindingResult result){
		ModelAndView model = new ModelAndView("Cadastro");
		if (result.hasErrors())
			return model.addObject("UsuarioDTO", dto);
		try {
			usuarioService.criarUsuario(dto);
			
		} catch (CustomException e) {
			logger.error("Erro na aplicação: " + e.getMessage());
			model.addObject("UsuarioDTO", dto);
			model.addObject("mensagem", e.getMessage());
			
			return model;
		} catch(Exception e){
			logger.error("Erro na aplicação: " + e.getMessage());
			model.addObject("UsuarioDTO", dto);
			model.addObject("mensagem", "Ocorreu um erro inesperado. Favor entrar em contato com o administrador do sistema.");
			return model;
		}
		return new ModelAndView("redirect:/login");
	}
	@RequestMapping(value="/foto/{tokenFoto}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public void getFile(@PathVariable String tokenFoto ,HttpServletResponse response)  {
	    try {
	    	Usuario usuario = usuarioRepository.findByFoto(tokenFoto);
	    	String caminho = usuarioService.getPathFoto(usuario);
	        InputStream inputStream = new FileInputStream(caminho); 
			response.setContentType("image/png");
			response.getOutputStream().write(IOUtils.toByteArray(inputStream));
			response.getOutputStream().flush();
			inputStream.close();

	    } catch (IOException e) {
	    	logger.error("Erro na aplicação: " + e.getMessage());
	    	throw new RuntimeException(e);
	    }
	}
}
