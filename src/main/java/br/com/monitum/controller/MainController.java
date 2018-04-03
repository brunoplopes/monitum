package br.com.monitum.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.security.UsuarioContext;
import br.com.monitum.service.UsuarioService;

@Controller
public class MainController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	Logger logger = Logger.getLogger(MainController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, @RequestParam(value = "mensagemSenha", required = false) String mensagemSenha, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "Logout realizado com sucesso.");
		}
		
		if (mensagemSenha != null) {
			model.addObject("mensagemSenha",  "Senha alterada com sucesso!");
		}
		if(UsuarioContext.getUsuarioLogado() != null)
			return new ModelAndView("redirect:/usuario");
		model.setViewName("Login");

		return model;

	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Email ou senha incorreta!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Email ou senha incorreta!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;

	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
 	}
	@RequestMapping(value="/login/recuperar-senha", method = RequestMethod.GET)
	@ResponseBody
	public String recuperarSenha(@RequestParam(value = "email", required = false) String email, HttpServletResponse response){
		try {			
			return usuarioService.recuperarSenha(email);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			response.setStatus(e.getStatusCode());
			return e.getMessage();
		}
	}
	@RequestMapping(value="/login/recuperar-senha/{token}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView formRecuperarSenha(@PathVariable(value = "token") String token, HttpServletResponse response){
		ModelAndView model = new ModelAndView("RecuperarSenha");
		try {
			model.addObject("token", usuarioService.validarToken(token));
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			model.setViewName("404");
			return model;
		}
		return model;
	}
	@RequestMapping(value="/login/recuperar-senha/{token}", method = RequestMethod.POST)
	public ModelAndView formRecuperarSenha(@PathVariable("token") String token, @RequestParam("senha") String senha, @RequestParam("senhaRepetida") String senhaRepetida){
		ModelAndView model = new ModelAndView("redirect:/login?mensagemSenha");
		try {
			usuarioService.mudarSenha(token, senha, senhaRepetida);
		} catch (CustomException e) {
			logger.error("erro na aplicação: " + e.toString());
			model.setViewName("RecuperarSenha");
			model.addObject("token", token);
			model.addObject("mensagem", e.getMessage());
			return model;
		} catch(Exception e){
			logger.error("erro na aplicação: " + e.toString());
			System.err.println("Exception");
		}
		return model;
	}
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView home(){
		return new ModelAndView("redirect:/login");
	}
}