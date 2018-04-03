package br.com.monitum.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.AlunoFrequenciaDTO;
import br.com.monitum.dto.FrequenciaDTO;
import br.com.monitum.dto.OptionDTO;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Atividade;
import br.com.monitum.entity.Frequencia;
import br.com.monitum.entity.FrequenciaAluno;
import br.com.monitum.entity.Grupo;
import br.com.monitum.entity.Periodo;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.Turma;
import br.com.monitum.entity.TurmaAluno;
import br.com.monitum.entity.Usuario;
import br.com.monitum.repository.AlunoRepository;
import br.com.monitum.repository.AtividadeRepository;
import br.com.monitum.repository.FrequenciaAlunoRepository;
import br.com.monitum.repository.FrequenciaRepository;
import br.com.monitum.repository.GrupoAlunoRepository;
import br.com.monitum.repository.ProfessorRepository;
import br.com.monitum.repository.TurmaAlunoRepository;
import br.com.monitum.repository.TurmaRepository;
import br.com.monitum.repository.UsuarioRepository;
import br.com.monitum.security.UsuarioContext;
@Service
public class FrequenciaService {
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TurmaAlunoRepository turmaAlunoRepository;
	@Autowired
	private FrequenciaRepository frequenciaRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AtividadeRepository atividadeRepository;
	@Autowired
	private GrupoAlunoRepository grupoAlunoRepository;
	@Autowired
	private FrequenciaAlunoRepository frequenciaAlunoRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	
	public FrequenciaDTO getFrenquenciaTurmaDTO(long idTurma) throws CustomException{
		Usuario usuario =  usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		Professor professor = professorRepository.findByUsuario(usuario);
		
		Turma turma = turmaRepository.findByIdAndProfessor(idTurma, professor);
		if(turma == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		
		return getFrequenciaDTO(turma, null);
	}
	public FrequenciaDTO getFrenquenciaEditarDTO(long idFrequencia) throws CustomException{
		Frequencia frequencia = frequenciaRepository.findByIdFrequenciaAndIdProfessor(idFrequencia, usuarioService.getProfessor().getId());
		if(frequencia == null)
			throw CustomException.FREQUENCIA_NAO_ENCONTRADA;
		
		return getFrequenciaDTO(frequencia.getTurma(), frequencia);
	}
	public FrequenciaDTO getFrequenciaDTO(Turma turma, Frequencia frequencia){
		FrequenciaDTO dto = new FrequenciaDTO();
		
		dto.setCodigoTurma(turma.getCodigoTurma());
		dto.setCodigoDisciplina(turma.getDisciplina().getCodDisciplina());
		dto.setQuantidadeAula(frequencia == null ? turma.getAulasDia() : frequencia.getQuantidadeAula());
		dto.setIdTurma(turma.getId());
		dto.setDataAula(frequencia == null ? null : frequencia.getDataAula());
		dto.setPeriodo(frequencia == null ? null : frequencia.getPeriodo());
		dto.setAlunosFrequencia(getListAlunoFrequencia(turma, frequencia));
		dto.setIdFrequencia(frequencia == null ? 0 : frequencia.getId());
		
		return dto;
	}
	public List<OptionDTO> getListAtividadeTurma(long idAtividade){
		Atividade atividade = atividadeRepository.findOne(idAtividade);
		if(atividade == null ) return null;
		return getListAtividadesTurma(atividade.getTurma().getId());
	}
	public List<OptionDTO> getListAtividadesTurma(long idTurma){
		List<OptionDTO> gruposTurma = new ArrayList<OptionDTO>();
		List<Atividade> atividades = atividadeRepository.findAtividadeGrupoByIdTurma(idTurma);
		for (Atividade atividade : atividades) {
			gruposTurma.add(new OptionDTO(atividade.getId(), atividade.getTitulo()));
		}
		return gruposTurma;
	}
	public List<AlunoFrequenciaDTO> getListAlunoFrequencia(Turma turma,Frequencia registro){
		List<AlunoFrequenciaDTO> alunosFrequencia = new ArrayList<AlunoFrequenciaDTO>();
		List<TurmaAluno> alunosTurma = turmaAlunoRepository.findByIdTurmaOrderByNomeAsc(turma.getId());
		
		if(alunosTurma.size() == 0 || alunosTurma.get(0).getAluno() == null)
			return alunosFrequencia;
		List<Atividade> atividades = atividadeRepository.findAtividadeGrupoByIdTurma(turma.getId());
		
		for (TurmaAluno alunoTurma : alunosTurma) {

			AlunoFrequenciaDTO alunoDTO = new AlunoFrequenciaDTO();
			alunoDTO.setUrlFoto(usuarioService.getUrlFoto(alunoTurma.getAluno().getUsuario()));
			alunoDTO.setNome(alunoTurma.getAluno().getNome());
			alunoDTO.setCodigo(alunoTurma.getCodigo());
			alunoDTO.setIdAluno(alunoTurma.getAluno().getId());
			if(registro != null){
				FrequenciaAluno frequenciaAluno = frequenciaAlunoRepository.findByFrequenciaAndAluno(registro, alunoTurma.getAluno());
				alunoDTO.setFrequencia(frequenciaAluno == null ? null : getFrequencia(frequenciaAluno.getRegistro()));
			}
			if(atividades.size() == 1){
				Grupo grupo = grupoAlunoRepository.findByIdAlunoAndIdAtividade(alunoTurma.getAluno().getId(), atividades.get(0).getId());
				alunoDTO.setGrupo(grupo == null ? null : grupo.getNomeGrupo());
			}
			alunosFrequencia.add(alunoDTO);
		}
		
		return alunosFrequencia;
	}
	public Map<String, String> getPeriodoList(){
		Map<String, String> map = new HashMap<String, String>();
		for (Periodo periodo : Periodo.values()) {
			map.put(periodo.name(), periodo.getValue());
		}
		return map;
	}
	public void registrarFrenquencia(FrequenciaDTO dto) throws CustomException{
		Professor professor = usuarioService.getProfessor();
		Turma turma = turmaRepository.findByIdAndProfessor(dto.getIdTurma(), professor);
		if(turma == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		
		Frequencia frequenciaRegistro = frequenciaRepository.findByIdFrequenciaAndIdProfessor(dto.getIdFrequencia(), professor.getId());
		
		if(frequenciaRegistro == null){
			Frequencia frequencia = getFrequencia(dto, turma);
			frequencia = frequenciaRepository.save(frequencia);
			criarFrequencia(dto, frequencia, turma);
		}else{
			alterarFrequenciaAlunos(dto, frequenciaRegistro);
		}
	}
	public void alterarFrequenciaAlunos(FrequenciaDTO dto ,Frequencia frequencia){
		alterarFrequencia(dto, frequencia);
		for (AlunoFrequenciaDTO alunoDTO : dto.getAlunosFrequencia()) {
			Aluno aluno = alunoRepository.findOne(alunoDTO.getIdAluno());
			if(aluno != null){
				FrequenciaAluno frequenciaAlunoRegistro = frequenciaAlunoRepository.findByFrequenciaAndAluno(frequencia, aluno);
				if(frequenciaAlunoRegistro != null){
					frequenciaAlunoRegistro.setRegistro(getFrequencia(alunoDTO, dto.getQuantidadeAula()));
					frequenciaAlunoRegistro.setFaltas(getFaltas(dto.getQuantidadeAula(), alunoDTO));
					frequenciaAlunoRepository.save(frequenciaAlunoRegistro);
				}else{
					FrequenciaAluno frequenciaAluno = new FrequenciaAluno();
					frequenciaAluno.setAluno(aluno);
					frequenciaAluno.setFrequencia(frequencia);
					frequenciaAluno.setRegistro(getFrequencia(alunoDTO, dto.getQuantidadeAula()));
					frequenciaAluno.setFaltas(getFaltas(dto.getQuantidadeAula(), alunoDTO));
					frequenciaAlunoRepository.save(frequenciaAluno);
				}
			}
		}
		
	}
	public void criarFrequencia(FrequenciaDTO dto, Frequencia frequencia, Turma turma){
		List<FrequenciaAluno> frequenciaAlunos = new ArrayList<FrequenciaAluno>();
		
		for (AlunoFrequenciaDTO alunoFrequencia : dto.getAlunosFrequencia()) {
			FrequenciaAluno frequenciaAluno = new FrequenciaAluno();
			Aluno aluno = turmaAlunoRepository.findByIdTurmaAndIdAluno(turma.getId(), alunoFrequencia.getIdAluno());
			if(aluno != null){
				frequenciaAluno.setAluno(aluno);
				frequenciaAluno.setRegistro(getFrequencia(alunoFrequencia, dto.getQuantidadeAula()));
				frequenciaAluno.setFaltas(getFaltas(dto.getQuantidadeAula(), alunoFrequencia));
				frequenciaAluno.setFrequencia(frequencia);
				
				frequenciaAlunos.add(frequenciaAluno);
			}
		}
		frequenciaAlunoRepository.save(frequenciaAlunos);
	}
	public void alterarFrequencia(FrequenciaDTO dto ,Frequencia frequencia){
		frequencia.setDataAula(dto.getDataAula());
		frequencia.setPeriodo(dto.getPeriodo());
		frequencia.setQuantidadeAula(dto.getQuantidadeAula());
		frequencia.setDataRegistro(new Date());
		
		frequenciaRepository.save(frequencia);
	}
	public Frequencia getFrequencia(FrequenciaDTO dto, Turma turma){
		Frequencia frequencia = new Frequencia();
		
		frequencia.setTurma(turma);
		frequencia.setDataAula(dto.getDataAula());
		frequencia.setPeriodo(dto.getPeriodo());
		frequencia.setQuantidadeAula(dto.getQuantidadeAula());
		frequencia.setDataRegistro(new Date());
		
		return frequencia;
	}
	public String getFrequencia(AlunoFrequenciaDTO dto, int quantidadeAulas){
		String array = "";
		for (int i = 0; i < quantidadeAulas ; i++) {
			if(dto.getFrequencia() != null && i < dto.getFrequencia().size() && dto.getFrequencia().get(i) != null && dto.getFrequencia().get(i).booleanValue())
				array = array + "P";
			else array = array + "F";
		}
		return array;
	}
	public int getFaltas(int quantidadeAulas, AlunoFrequenciaDTO dto){
		int count = 0;
		for (int i = 0; i < quantidadeAulas ; i++)
			if(dto.getFrequencia() == null)
				count ++;
		return count;
	}
	public List<Boolean> getFrequencia(String presencas){
		List<Boolean> frequencias = new ArrayList<Boolean>();
		for (char presenca : presencas.toCharArray()) {
			if(presenca == 'P')
				frequencias.add(true);
			else frequencias.add(false);
		}
		return frequencias;
	}
	public List<FrequenciaDTO> getListFrequenciaDTO(long idTurma){
		List<FrequenciaDTO> registros =  new ArrayList<FrequenciaDTO>();
		List<Frequencia> frequencias = frequenciaRepository.findByIdTurmaAndIdProfessorOrderByData(idTurma, usuarioService.getProfessor().getId());
		for (Frequencia frequencia : frequencias) {
			FrequenciaDTO dto = new FrequenciaDTO();
			dto.setDataAula(frequencia.getDataAula());
			dto.setQuantidadeAula(frequencia.getQuantidadeAula());
			dto.setPeriodo(frequencia.getPeriodo());
			dto.setIdFrequencia(frequencia.getId());
			dto.setIdTurma(idTurma);
			registros.add(dto);
		}
		return registros;
	}
}
