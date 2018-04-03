package br.com.monitum.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.validation.Valid;

import br.com.monitum.dto.AlunoDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.TurmaDTO;
import br.com.monitum.entity.Turma;
import br.com.monitum.security.AuthorityConstant;
import br.com.monitum.service.TurmaService;

@RestController
@Transactional
@RequestMapping("/turma")
public class TurmaController {
	
	Logger logger = Logger.getLogger(TurmaController.class);
	
	@Autowired
	private TurmaService turmaService;
	@Resource
	private Environment env;

    @RequestMapping(value = "/buscarAluno/{prontuario}", method = RequestMethod.GET)
    @Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
    public AlunoDTO getAluno(@PathVariable String prontuario){
        return turmaService.getAluno(prontuario);
    }
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView cadastrar(@Valid @ModelAttribute("TurmaDTO") TurmaDTO dto, BindingResult result, @RequestParam(value = "_csrf", required = false) String _csrf){
		Turma turma = null;
		try {
			turma = turmaService.cadastrar(dto);
		} catch (CustomException e) {
			logger.error("Erro na aplicação: " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Erro na aplicação: " + e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Erro na aplicação: " + e.toString());
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/frequencia/turma/" + turma.getId());
	}
	@RequestMapping(value="/cadastro", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView formTurma(@RequestParam(value = "_csrf", required = false) String _csrf){
		ModelAndView model = new ModelAndView("Aplicacao/TurmaCadastro");
		model.addObject("TurmaDTO", new TurmaDTO());
		model.addObject("cursoList",turmaService.getCursoList());
		model.addObject("disciplinaList",turmaService.getDisciplinaList());
		return model;
	}
	@RequestMapping(value="/editar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView editarTurma(@PathVariable long idTurma){
		ModelAndView model = new ModelAndView("Aplicacao/TurmaEdicao");
		model.addObject("TurmaDTO", turmaService.getTurmaDTOId(idTurma));
		model.addObject("cursoList",turmaService.getCursoList());
		model.addObject("disciplinaList",turmaService.getDisciplinaList());
		model.addObject("idTurma", idTurma);
		return model;
	}
	@RequestMapping(value="/editar", method = RequestMethod.POST)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView editaTurma(@Valid @ModelAttribute("TurmaDTO") TurmaDTO dto, @ModelAttribute("idTurma") long idTurma){
		try {
			turmaService.editarId(dto, idTurma);
		} catch (CustomException e) {
			logger.error("Erro na aplicação: " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("Erro na aplicação: " + e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Erro na aplicação: " + e.toString());
			e.printStackTrace();
		}
		
		return new ModelAndView("redirect:/usuario");
	}
	@RequestMapping(value="/calendario/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_USUARIO})
	public ModelAndView calendarioTurma(@PathVariable long idTurma){
		ModelAndView model = new ModelAndView("Aplicacao/TurmaCalendario");
	    model.addObject("calId", turmaService.getCalendarioId(idTurma));
		return model;
	}
	@RequestMapping(value="/encerrar/{idTurma}", method = RequestMethod.GET)
	@Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView encerrarTurma(@PathVariable long idTurma) throws Exception{		
		turmaService.desativarTurma(idTurma);
		return new ModelAndView("redirect:/usuario/");
	}
	@RequestMapping(value="/arquivadas", method = RequestMethod.GET)
    @Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
	public ModelAndView turmasArquivadas() throws Exception{		
			ModelAndView model = new ModelAndView("Aplicacao/TurmasArquivadas");
			model.addObject("listaTurmas",turmaService.getTurmasArquivadasDTO());
			return model;

	}
    @RequestMapping(value="/finalizadas", method = RequestMethod.GET)
    @Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
    public ModelAndView turmasFinalizadas() throws Exception{
        ModelAndView model = new ModelAndView("Aplicacao/TurmasArquivadas");
        model.addObject("listaTurmas",turmaService.getTurmasFinalizadasDTO());
        return model;

    }
    @RequestMapping(value="/finalizar/{idTurma}", method = RequestMethod.GET)
    @Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
    public ModelAndView turmaFinalizar(@PathVariable long idTurma) throws Exception{
        ModelAndView model = new ModelAndView("Aplicacao/TurmaFinalizar");
        model.addObject("idTurma",idTurma);
        return model;

    }
    @RequestMapping(value="/fechar/{idTurma}", method = RequestMethod.GET)
    @Secured(value = {AuthorityConstant.ROLE_PROFESSOR, AuthorityConstant.ROLE_COORDENADOR})
    public ModelAndView turmaFechar(@PathVariable long idTurma) throws Exception{
        ModelAndView model = new ModelAndView("Aplicacao/TurmaFinalizar");
        model.addObject("idTurma",idTurma);
        return model;

    }
}
