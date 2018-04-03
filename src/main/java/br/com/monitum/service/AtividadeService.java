package br.com.monitum.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.AlunoNotaDTO;
import br.com.monitum.dto.AtividadeAlunoDTO;
import br.com.monitum.dto.AtividadeDTO;
import br.com.monitum.dto.AtividadeMobileDTO;
import br.com.monitum.dto.OptionDTO;
import br.com.monitum.dto.TurmaAtividadeMobileDTO;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.AlunoAtividade;
import br.com.monitum.entity.Atividade;
import br.com.monitum.entity.GrupoAluno;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.entity.Turma;
import br.com.monitum.entity.TurmaAluno;
import br.com.monitum.entity.Usuario;
import br.com.monitum.repository.AlunoAtividadeRepository;
import br.com.monitum.repository.AlunoRepository;
import br.com.monitum.repository.AtividadeRepository;
import br.com.monitum.repository.FrequenciaAlunoRepository;
import br.com.monitum.repository.GrupoAlunoRepository;
import br.com.monitum.repository.ProfessorRepository;
import br.com.monitum.repository.TurmaAlunoRepository;
import br.com.monitum.repository.TurmaRepository;
import br.com.monitum.repository.UsuarioRepository;
import br.com.monitum.security.UsuarioContext;

@Service
public class AtividadeService {
	
	Logger logger = Logger.getLogger(AtividadeService.class);
	
	@Autowired
	private AtividadeRepository atividadeRepository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TurmaAlunoRepository turmaAlunoRepository;
	@Autowired
	private AlunoAtividadeRepository alunoAtividadeRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private GrupoAlunoRepository grupoAlunoRepository;
	@Autowired
	private FrequenciaAlunoRepository frequenciaAlunoRepository;
	
	public AtividadeDTO getAtividadeDTO(long idTurma) throws CustomException{
		Turma turma = turmaRepository.findOne(idTurma);
		if(turma == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		return new AtividadeDTO(idTurma);
	}
	
	public void criarAtividade(AtividadeDTO dto, long idTurma) throws CustomException{
			Turma turma = turmaRepository.findOne(idTurma);
			if(turma == null)
				throw CustomException.TURMA_NAO_ENCONTRADA;
			
			try {
				Atividade atividade = new Atividade();
				atividade.setTurma(turma);
				atividade.setTitulo(dto.getTitulo());
				atividade.setDescricaoAtividade(dto.getDescricaoAtividade());
				atividade.setDataCriacao(new Date());
				atividade.setDataEntrega(dto.getDataEntrega());
				atividade.setTipoAtividade(dto.getTipoAtividade());
				
				atividadeRepository.save(atividade);
			} catch (Exception e) {
				logger.error("erro na aplicação: " + e.toString());
			}

	} 
	public List<OptionDTO> getAtividadesTurma(long idTurma){
		List<OptionDTO> atividadesTurma = new ArrayList<OptionDTO>();
		Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		Turma turma;
		if(usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR) || usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR)){
			Professor professor = professorRepository.findByUsuario(usuario);
			turma = turmaRepository.findByIdAndProfessor(idTurma, professor);
		}else {
			Aluno aluno = alunoRepository.findByUsuario(usuario);
			turma = turmaAlunoRepository.findByIdAlunoAndIdTurma(idTurma, aluno.getId());
		}		
		List<Atividade> atividades = atividadeRepository.findByTurma(turma);

		for (Atividade atividade : atividades) {
			atividadesTurma.add(new OptionDTO(atividade.getId(), atividade.getTitulo()));
		}
		
		return atividadesTurma;
	}
	public List<OptionDTO> getGruposAlunos(long idAtividade){
		List<GrupoAluno> grupoAlunos = grupoAlunoRepository.findByIdAtividade(idAtividade);
		List<OptionDTO> options = new ArrayList<OptionDTO>();
		for (GrupoAluno grupoAluno : grupoAlunos) {
			options.add(new OptionDTO(grupoAluno.getAluno().getId(), grupoAluno.getGrupo().getNomeGrupo()));
		}
		return options;
	}
	public List<AlunoNotaDTO> getAlunosNotaDTO(Atividade atividade) throws CustomException{
		List<AlunoNotaDTO> alunosNota = new ArrayList<AlunoNotaDTO>();
		Professor professor = usuarioService.getProfessor();
		
		if(atividade == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		
		List<TurmaAluno> alunosTurma = turmaAlunoRepository.findByTurma(atividade.getTurma());
		List<AlunoAtividade> alunosAtividade = alunoAtividadeRepository.findByAtividade(atividade);
		
		if(alunosAtividade != null & !alunosAtividade.isEmpty()){
			for (AlunoAtividade alunoAtividade : alunosAtividade) {
				alunosNota.add(new AlunoNotaDTO(alunoAtividade.getAluno().getId(), alunoAtividade.getAluno().getNome(), alunoAtividade.getNota()));
			}
		}else{
			if(atividade != null && atividade.getTurma().getProfessor().getId() == professor.getId()){
				for (TurmaAluno aluno : alunosTurma) {
					alunosNota.add(new AlunoNotaDTO(aluno.getAluno().getId(), aluno.getAluno().getNome(), 0));
				}
			}else throw CustomException.TURMA_NAO_ENCONTRADA;
		}
		return alunosNota;
	}
	public AtividadeAlunoDTO getAtividadeAlunoTurma(long idAtividade) throws CustomException{
		AtividadeAlunoDTO atividadeAluno = new AtividadeAlunoDTO();
		Atividade atividade = atividadeRepository.findOne(idAtividade);
		atividadeAluno.setAlunosNota(getAlunosNotaDTO(atividade));
		atividadeAluno.setIdAtividade(idAtividade);
		atividadeAluno.setTitulo(atividade.getTitulo());
		return atividadeAluno;
	}
	public void salvarNotas(AtividadeAlunoDTO dto) throws CustomException{
		Professor professor = usuarioService.getProfessor();
		List<AlunoAtividade> alunosAtividadeNota =  new ArrayList<AlunoAtividade>();
		
		Atividade atividade = atividadeRepository.findOne(dto.getIdAtividade());
		if(atividade == null || atividade.getTurma().getProfessor().getId() != professor.getId())
			throw CustomException.ATIVIDADE_NAO_ENCONTRADA;
		
		List<AlunoAtividade> alunosAtividade = alunoAtividadeRepository.findByAtividade(atividade);
		if(alunosAtividade != null & !alunosAtividade.isEmpty()){
			for (AlunoNotaDTO alunoNota : dto.getAlunosNota()) {
				Aluno aluno = turmaAlunoRepository.findByIdTurmaAndIdAluno(atividade.getTurma().getId(), alunoNota.getIdAluno());
				if(aluno != null){
					AlunoAtividade alunoAtividade = alunoAtividadeRepository.findByAtividadeAndAluno(atividade, aluno);
					if(alunoAtividade != null){
						alunoAtividade.setNota(alunoNota.getNota());
						alunosAtividadeNota.add(alunoAtividade);
					}else alunoAtividadeRepository.save(new AlunoAtividade(atividade, alunoNota.getNota(), aluno));
				}
			}
		}else{
			for (AlunoNotaDTO alunoNota : dto.getAlunosNota()) {
				Aluno aluno = turmaAlunoRepository.findByIdTurmaAndIdAluno(atividade.getTurma().getId(), alunoNota.getIdAluno());
				if(aluno != null){
					alunosAtividadeNota.add(new AlunoAtividade(atividade,alunoNota.getNota(),aluno));
				}
			}
		}
		alunoAtividadeRepository.save(alunosAtividadeNota);
	}
	
	public List<Atividade> getAtividades(long idTurma){
		Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		Turma turma;
		if(usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR) || usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR)){
			Professor professor = professorRepository.findByUsuario(usuario);
			turma = turmaRepository.findByIdAndProfessor(idTurma, professor);
		}else {
			Aluno aluno = alunoRepository.findByUsuario(usuario);
			turma = turmaAlunoRepository.findByIdAlunoAndIdTurma(idTurma, aluno.getId());
		}		
		List<Atividade> atividades = atividadeRepository.findByTurma(turma);
		
		return atividades;
	}
	
	public AtividadeDTO getAtividadeDTOId(long idAtividade) throws CustomException{
		Atividade atividade = atividadeRepository.findOne(idAtividade);
		AtividadeDTO dto = new AtividadeDTO();
		dto.setDataCriacao(atividade.getDataCriacao());
		dto.setDataEntrega(atividade.getDataEntrega());
		dto.setDescricaoAtividade(atividade.getDescricaoAtividade());
		dto.setIdTurma(atividade.getTurma().getId());
		dto.setTipoAtividade(atividade.getTipoAtividade());
		dto.setTitulo(atividade.getTitulo());
		return dto;
	}
	
	public Atividade getAtividadeId(long idAtividade) throws CustomException{
		Atividade atividade = atividadeRepository.findOne(idAtividade);
		return atividade;
	}
	
	public String getCodTurmaAtividade(long idTurma){		
		return turmaRepository.findOne(idTurma).getCodigoTurma();
	}
	
	public long getIdTurmaAtividade(long idAtividade){		
		return atividadeRepository.findOne(idAtividade).getTurma().getId();
	}
	
	public String getDiscAtividade(long idTurma){		
		return turmaRepository.findOne(idTurma).getDisciplina().getCodDisciplina();
	}
	
	public void updateAtividade(AtividadeDTO dto, long id) throws CustomException{
		try {
			Atividade atividade = new Atividade();
			Atividade atv = atividadeRepository.findOne(id);
			atividade.setDataCriacao(atv.getDataCriacao());
			atividade.setDataEntrega(dto.getDataEntrega());
			atividade.setDescricaoAtividade(dto.getDescricaoAtividade());
			atividade.setId(id);
			atividade.setTipoAtividade(dto.getTipoAtividade());
			atividade.setTitulo(dto.getTitulo());
			atividade.setTurma(turmaRepository.findOne(dto.getIdTurma()));
			
			atividadeRepository.save(atividade);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		
	} 
	
	public void deletarAtividade(long idAtividade) throws Exception{
		Atividade atividade = atividadeRepository.findOne(idAtividade);
		List<AlunoAtividade> alunoAtividade = alunoAtividadeRepository.findByAtividade(atividade);
		try{
			for(AlunoAtividade aluAtv : alunoAtividade){
				alunoAtividadeRepository.delete(aluAtv);
			}
			atividadeRepository.delete(atividade);
		}catch(Exception e){
			logger.error("erro na aplicação: " + e.toString());
			throw new Exception();
		}		
	}
	public TurmaAtividadeMobileDTO getListAtividadeDTO(String token, long idTurma) throws CustomException{
		Usuario usuario = usuarioService.getUsuarioAccessToken(token);
		TurmaAtividadeMobileDTO dto = new TurmaAtividadeMobileDTO();
		if(usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR) || usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR)){
			Professor professor = professorRepository.findByUsuario(usuario);
			dto.setAtividades(getListAtividadeProfessor(professor, idTurma));
			return dto;
		}else{
			Aluno aluno = alunoRepository.findByUsuario(usuario);
			Integer faltas = frequenciaAlunoRepository.findByIdAlunoAndIdTurma(aluno.getId(), idTurma);
			dto.setFaltas(faltas == null ? "" : faltas.toString());
			dto.setAtividades(getListAtividadeAluno(aluno, idTurma));
			return dto;
		}
	}
	public AlunoNotaDTO getNotaLogado(long idAtividade){
		Aluno aluno = alunoRepository.findByUsuario(usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId()));
		AlunoAtividade aluAtv = alunoAtividadeRepository.findByAtividadeAndAluno(atividadeRepository.findOne(idAtividade), aluno);
		AlunoNotaDTO dto = new AlunoNotaDTO();
		dto.setIdAluno(aluno.getId());
		dto.setNome(aluno.getNome());
		double nota;
		if (aluAtv == null){
			nota = 0;
		}else{
			nota = aluAtv.getNota();
		}
		dto.setNota(nota);
		return dto;
	}
	public List<AtividadeMobileDTO> getListAtividadeProfessor(Professor professor, long idTurma){
		List<AtividadeMobileDTO> atividadesDTO = new ArrayList<AtividadeMobileDTO>();
		List<Atividade> atividades = atividadeRepository.findAtividadeGrupoByIdTurma(idTurma);
		for (Atividade atividade : atividades) {
			AtividadeMobileDTO dto = new AtividadeMobileDTO();
			dto.setTitulo(atividade.getTitulo());
			dto.setDataEntrega(new java.sql.Timestamp(atividade.getDataEntrega().getTime()));
			dto.setDescricao(atividade.getDescricaoAtividade());
			atividadesDTO.add(dto);
		}
		return atividadesDTO;
	}
	public List<AtividadeMobileDTO> getListAtividadeAluno(Aluno aluno, long idTurma){
		List<AtividadeMobileDTO> atividadesDTO = new ArrayList<AtividadeMobileDTO>();
		List<Atividade> atividades = atividadeRepository.findAtividadeGrupoByIdTurma(idTurma);
		for (Atividade atividade : atividades) {
			AtividadeMobileDTO dto = new AtividadeMobileDTO();
			AlunoAtividade alunoAtividade = alunoAtividadeRepository.findByAtividadeAndAluno(atividade, aluno);
			dto.setNota(alunoAtividade == null ? "-" : String.valueOf(alunoAtividade.getNota()));
			dto.setDataEntrega(new java.sql.Timestamp(atividade.getDataEntrega().getTime()));
			dto.setDescricao(atividade.getDescricaoAtividade());
			dto.setTitulo(atividade.getTitulo());
			atividadesDTO.add(dto);
		}
		return atividadesDTO;
	}
}