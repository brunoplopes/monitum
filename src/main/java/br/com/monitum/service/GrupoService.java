package br.com.monitum.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.AtividadeGrupoDTO;
import br.com.monitum.dto.GrupoDTO;
import br.com.monitum.dto.OptionDTO;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Atividade;
import br.com.monitum.entity.Grupo;
import br.com.monitum.entity.GrupoAluno;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.Turma;
import br.com.monitum.repository.AtividadeRepository;
import br.com.monitum.repository.GrupoAlunoRepository;
import br.com.monitum.repository.GrupoRepository;
import br.com.monitum.repository.TurmaRepository;

@Service
public class GrupoService {
	@Autowired
	private AtividadeRepository atividadeRepository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private GrupoRepository grupoRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private GrupoAlunoRepository grupoAlunoRepository;
	
	public List<AtividadeGrupoDTO> getListGrupoProfessorDTO(long idTurma) throws CustomException{
		Professor professor = usuarioService.getProfessor();
		Turma turma = turmaRepository.findByIdAndProfessor(idTurma, professor);
		if(turma == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		List<Atividade> atividades = atividadeRepository.findByTurma(turma); 
		
		List<AtividadeGrupoDTO> grupos = new ArrayList<AtividadeGrupoDTO>();
		if(grupoRepository.findByAtividadeIn(atividades).isEmpty())
			return grupos;
		
		for (Atividade atividade : atividades) {
			AtividadeGrupoDTO grupo = getGrupoDTO(atividade);
			if(grupo != null)
				grupos.add(grupo);
		}
		return grupos;
	}
	public Map<String, String> getListGrupoAlunoDTO(long idTurma) throws CustomException{
		Aluno aluno = usuarioService.getAluno();
		Turma turma = turmaRepository.findByIdTurmaAndIdAluno(idTurma, aluno.getId());
		if(turma == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		
		Map<String, String> atividadeGrupo = new HashMap<String, String>();
		List<Grupo> grupos = grupoAlunoRepository.findByIdAlunoAndIdTurma(aluno.getId(), idTurma);
		if(grupos.isEmpty())
			return atividadeGrupo;
		for (Grupo grupo : grupos) {
			atividadeGrupo.put(grupo.getAtividade().getTitulo(), grupo.getNomeGrupo());
		}
		return atividadeGrupo;
	}
	public List<String> getNomesGrupos(List<Grupo> grupos){
		List<String> nomesGrupos = new ArrayList<String>();
		for (Grupo grupo : grupos) {
			nomesGrupos.add(grupo.getNomeGrupo());
		}
		return nomesGrupos;
	}
	public AtividadeGrupoDTO getGrupoDTO(Atividade atividade){
		List<Grupo> grupos = grupoRepository.findByAtividade(atividade);
		if(grupos.isEmpty())
			return null;
		AtividadeGrupoDTO grupo = new AtividadeGrupoDTO();
		grupo.setIdTurma(atividade.getTurma().getId());
		grupo.setIdAtividade(atividade.getId());
		grupo.setAtividadeTitulo(atividade.getTitulo());
		grupo.setNomegrupos(getNomesGrupos(grupos));
		grupo.setIdGrupo(grupos.get(0).getId());
		
		return grupo;
	}
	public void criarGrupos(GrupoDTO dto) throws CustomException{
		Professor professor = usuarioService.getProfessor();
		Atividade atividade = atividadeRepository.findByIdAtividadeAndIdProfessor(dto.getIdAtividade(), professor.getId());
		if(atividade == null)
			throw CustomException.ATIVIDADE_NAO_ENCONTRADA;
		List<Grupo> grupos = new ArrayList<Grupo>();
		for (GrupoDTO grupoDTO : dto.getGrupos()) {
			grupos.add(new Grupo(atividade, grupoDTO.getNome(), new Date(), dto.getNumerointegrantes()));
		}
		dto.setIdTurma(atividade.getTurma().getId());
		
		grupoRepository.save(grupos);
	}
	public List<OptionDTO> getGruposAtividade(long idAtividade){
		Atividade atividade = atividadeRepository.findOne(idAtividade);
		List<Grupo> grupos = grupoRepository.findByAtividade(atividade);
		List<OptionDTO> options = new ArrayList<OptionDTO>();
		for (Grupo grupo : grupos) {
			options.add(new OptionDTO(grupo.getId(), grupo.getNomeGrupo()));
		}
		return options;
	}
	public List<OptionDTO> getIntegrantesGrupo(long idGrupo) throws CustomException{
		List<GrupoAluno> grupoAlunos = grupoAlunoRepository.findByIdGrupoAndIdProfessor(idGrupo, usuarioService.getProfessor().getId());
		List<OptionDTO> options = new ArrayList<OptionDTO>();
		if(grupoAlunos.isEmpty())
			return options;
		for (GrupoAluno grupoAluno : grupoAlunos) {
			options.add(new OptionDTO(grupoAluno.getId(), grupoAluno.getAluno().getNome()));
		}
		return options;
	}
	public void removerGrupoAluno(long idGrupoAluno) throws CustomException{
		GrupoAluno grupoAluno = grupoAlunoRepository.findByIdGrupoAlunoAndIdProfessor(idGrupoAluno, usuarioService.getProfessor().getId());
		grupoAlunoRepository.delete(grupoAluno);
	}
	public void entrarGrupo(long idGrupo, long idTurma) throws CustomException{
		Aluno aluno = usuarioService.getAluno();
		Atividade atividade = atividadeRepository.findByIdGrupoIdAluno(idGrupo, aluno.getId());
		if( atividade == null )
			throw CustomException.ATIVIDADE_NAO_ENCONTRADA;
		Grupo grupo = grupoRepository.findOne(idGrupo);
		if(grupo == null)
			throw CustomException.GRUPO_NAO_ENCONTRADO;
		if(grupoAlunoRepository.findByIdAlunoAndIdAtividade(aluno.getId(), atividade.getId()) != null)
			throw CustomException.ALUNO_JA_CADASTRADO_NO_GRUPO;
		GrupoAluno grupoAluno = new GrupoAluno();
		grupoAluno.setAluno(aluno);
		grupoAluno.setGrupo(grupo);
		grupoAluno.setDataEntrada(new Date());
		grupoAlunoRepository.save(grupoAluno);
	}
}
