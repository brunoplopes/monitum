package br.com.monitum.controller;

import java.util.List;

import org.apache.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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

import br.com.monitum.dto.AtividadeAlunoDTO;
import br.com.monitum.dto.AtividadeDTO;
import br.com.monitum.dto.OptionDTO;
import br.com.monitum.entity.Atividade;
import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.security.UsuarioContext;
import br.com.monitum.service.AtividadeService;
import br.com.monitum.service.UsuarioService;


@RestController
@Transactional
@RequestMapping("/atividade")
public class AtividadeController {
	
	Logger logger = Logger.getLogger(AtividadeController.class);

	String caminho = "br.com.monitum.controller.AtividadeController";
	
	@Autowired
	private AtividadeService atividadeService;
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/criar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView novaAtividade(@PathVariable long idTurma){

		return new ModelAndView("Aplicacao/AtividadeCadastro").addObject("AtividadeDTO", atividadeService.getAtividadeDTO(idTurma));
	}
	
	@RequestMapping(value = "/criar/{idTurma}", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView novaAtividade(@ModelAttribute("AtividadeDTO") AtividadeDTO dto, @PathVariable long idTurma){
		try {
			atividadeService.criarAtividade(dto, idTurma);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}

		return new ModelAndView("redirect:/atividade/" + idTurma);
	}
	@RequestMapping(value = "/registrarNota", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView registrarNotas(@RequestParam(value = "mensagem", required = false) String mensagem){
		ModelAndView model = new ModelAndView("Aplicacao/Atividade");
		if(mensagem != null && mensagem.equals("ok"))
			model.addObject("mensagem", "Dados salvos com sucesso.");
		model.addObject("turmaList", usuarioService.getTurmaList());
		return model;
	}
	@RequestMapping(value = "/atividadesTurma/{idTurma}", method = RequestMethod.GET, produces = "application/json")
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public List<OptionDTO> atividadesTurma(@PathVariable long idTurma){
		try {
			return atividadeService.getAtividadesTurma(idTurma);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		return atividadeService.getAtividadesTurma(idTurma); // aqui deve retornar uma tela de erro
	}
	@RequestMapping(value = "/gruposAlunos/{idAtividade}", method = RequestMethod.GET, produces = "application/json")
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public List<OptionDTO> gruposAlunos(@PathVariable long idAtividade){
		return atividadeService.getGruposAlunos(idAtividade);
	}
	@RequestMapping(value = "/atividadesTurma", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView atividade(@RequestParam(name="idAtividade") long idAtividade){
		ModelAndView model = new ModelAndView("Aplicacao/AtividadeNotas");
		model.addObject("AtividadeAlunoDTO", atividadeService.getAtividadeAlunoTurma(idAtividade));
		return model;
	}
	@RequestMapping(value = "/atividadesTurma", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView atividadeLancarNota(@ModelAttribute("AtividadeAlunoDTO") AtividadeAlunoDTO dto){
		try {
			atividadeService.salvarNotas(dto);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}

		return new ModelAndView("redirect:/atividade/registrarNota?mensagem=ok");
	}
	@RequestMapping(value = "/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})	
	public ModelAndView atividadeListar(@PathVariable long idTurma){
		ModelAndView model = new ModelAndView("Aplicacao/AtividadeLista");		
		model.addObject("AtividadeDTO", atividadeService.getAtividades(idTurma));
		model.addObject("turma", atividadeService.getCodTurmaAtividade(idTurma));
		model.addObject("turmaId", idTurma);
		model.addObject("disciplina", atividadeService.getDiscAtividade(idTurma));
		return model;
	}	
	@RequestMapping(value = "/editar/{idAtividade}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView editarAtividade(@PathVariable long idAtividade){
		ModelAndView model = new ModelAndView("Aplicacao/AtividadeEditar");
		model.addObject("AtividadeDTO", atividadeService.getAtividadeDTOId(idAtividade));
		model.addObject("id", idAtividade);
		return model;
	}
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView editarAtividade(@ModelAttribute("AtividadeDTO") AtividadeDTO atividade, @RequestParam("id") long id){
		try {
			atividadeService.updateAtividade(atividade, id);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		
		return new ModelAndView("redirect:/atividade/" + atividade.getIdTurma());
	}
	@RequestMapping(value = "/deletar/{atividadeId}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView deletarAtividade(@PathVariable long atividadeId){
		long idTurma = atividadeService.getIdTurmaAtividade(atividadeId);
		try{
			atividadeService.deletarAtividade(atividadeId);
		}catch(Exception e){
			logger.error("erro na aplicação: " + e.toString());
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/atividade/" + idTurma);
	}
	@RequestMapping(value = "/detalhes/{atividadeId}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView detalhesAtividade(@PathVariable long atividadeId){
		ModelAndView model = new ModelAndView("Aplicacao/AtividadeDetalhes");
		Atividade atividade = atividadeService.getAtividadeId(atividadeId);
		model.addObject("Atividade", atividade);
		if(UsuarioContext.getUsuarioLogado().getTipoUsuario() == TipoUsuario.ESTUDANTE){
			model.addObject("nota", atividadeService.getNotaLogado(atividadeId));
		}else{
			model.addObject("alunoList", atividadeService.getAlunosNotaDTO(atividade));
		}
		model.addObject("turma", atividadeService.getCodTurmaAtividade(atividade.getTurma().getId()));
		model.addObject("disciplina", atividadeService.getDiscAtividade(atividade.getTurma().getId()));		
		return model;
	}
}

 