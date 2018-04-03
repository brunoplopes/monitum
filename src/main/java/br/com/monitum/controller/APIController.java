package br.com.monitum.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.NotificacaoDTO;
import br.com.monitum.dto.PerfilMobileDTO;
import br.com.monitum.dto.TurmaAtividadeMobileDTO;
import br.com.monitum.dto.TurmaMobileDTO;
import br.com.monitum.service.AtividadeService;
import br.com.monitum.service.ConteudoService;
import br.com.monitum.service.TurmaService;
import br.com.monitum.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class APIController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TurmaService turmaService;
	@Autowired
	private AtividadeService atividadeService;
	@Autowired
	private ConteudoService conteudoService;
	
	@RequestMapping(value="/notificacoes", method = RequestMethod.POST, produces = "application/json")
	public List<NotificacaoDTO> notificacoes(@RequestParam("token") String token, HttpServletResponse response) {
		List<NotificacaoDTO> notificacoes = conteudoService.getNotificacoes(token);
		return notificacoes;
	}
	@RequestMapping(value="/desconectar", method = RequestMethod.POST, produces = "application/json")
	public void enviarNotificacao(@RequestParam("token") String token, HttpServletResponse response) {
		usuarioService.desconectar(token);
	}
	@RequestMapping(value="/notificar/{idTurma}", method = RequestMethod.POST, produces = "application/json")
	public void enviarNotificacao(@RequestParam("token") String token,@RequestParam("titulo") String titulo, @RequestParam("mensagem") String mensagem, @PathVariable long idTurma, HttpServletResponse response) {
		try {
			conteudoService.notificacao(token, titulo, mensagem, idTurma);
		} catch (CustomException e) {
			response.setStatus(e.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/receberNotificacao", method = RequestMethod.POST, produces = "application/json")
	public void receberNotificacao(@RequestParam("token") String token,@RequestParam("receberNotificacao") Boolean receberNotificacao , HttpServletResponse response) {
		try {
			usuarioService.receberNotificacao(token, receberNotificacao);
		} catch (CustomException e) {
			response.setStatus(e.getStatusCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/validarToken", method = RequestMethod.POST, produces = "application/json")
	public PerfilMobileDTO validarToken(@RequestParam("token") String token, HttpServletResponse response) {
		try {
			return usuarioService.getPerfilMobileDTO(token);
		} catch (CustomException e) {
			response.setStatus(e.getStatusCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="/token", method = RequestMethod.POST, produces = "application/json")
	public PerfilMobileDTO token(@RequestParam("username") String email, @RequestParam("password") String senha, @RequestParam("gcmGoogle") String gcmGoogle, HttpServletResponse response) {
		try {
			return usuarioService.getAccessToken(email, senha, gcmGoogle);
		} catch (CustomException e) {
			response.setStatus(e.getStatusCode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="/turmas", method = RequestMethod.POST, produces = "application/json")
	public List<TurmaMobileDTO> validarAcesso(@RequestParam("token") String token, HttpServletResponse response) {
		try {
			return turmaService.getTurmasMobile(token);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping(value="/atividades", method = RequestMethod.POST, produces = "application/json")
	public TurmaAtividadeMobileDTO atividades(@RequestParam("token") String token, @RequestParam("idTurma") long idTurma, HttpServletResponse response) {
		try {
			return atividadeService.getListAtividadeDTO(token, idTurma);
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
