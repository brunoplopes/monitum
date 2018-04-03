package br.com.monitum.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.AlunoDTO;
import br.com.monitum.dto.TurmaDTO;
import br.com.monitum.dto.TurmaMobileDTO;
import br.com.monitum.dto.TurmaUsuarioDTO;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Curso;
import br.com.monitum.entity.Disciplina;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.entity.Turma;
import br.com.monitum.entity.TurmaAluno;
import br.com.monitum.entity.Usuario;
import br.com.monitum.repository.AlunoRepository;
import br.com.monitum.repository.CursoRepository;
import br.com.monitum.repository.DisciplinaRepository;
import br.com.monitum.repository.FrequenciaAlunoRepository;
import br.com.monitum.repository.ProfessorRepository;
import br.com.monitum.repository.TurmaAlunoRepository;
import br.com.monitum.repository.TurmaRepository;
import br.com.monitum.repository.UsuarioRepository;
import br.com.monitum.security.UsuarioContext;

@Service
@Transactional
public class TurmaService {
	
	Logger logger = Logger.getLogger(TurmaService.class);
	
	@Autowired
	private CursoRepository cursoRepository;
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private TurmaAlunoRepository turmaAlunoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private AtividadeService atividadeService;
	@Autowired
	private FrequenciaAlunoRepository frequenciaAlunoRepository;
	
	private static final String [] ARQUIVO_HEADER_CSV_ALUNO = {"codigo","nome","prontuario"};
	private static final String ALUNO_CODIGO = "codigo";
	private static final String ALUNO_NOME = "nome";
	private static final String ALUNO_PRONTUARIO = "prontuario";

    public AlunoDTO getAluno(String prontuario){
        Aluno aluno = alunoRepository.findByProntuario(prontuario);
        AlunoDTO alunoDTO = new AlunoDTO();
        if(aluno != null)
            alunoDTO.setNome(aluno.getNome());
        return alunoDTO;
    }

	public Map<Long, String> getCursoList(){
		Map<Long, String> map = new HashMap<Long, String>();
		for (Curso curso : cursoRepository.findAll()) {
			map.put(curso.getId(), curso.getNomeCurso());
		}
		return map;
	}
	public Map<Long, String> getDisciplinaList(){
		Map<Long, String> map = new HashMap<Long, String>();
		for (Disciplina disciplina : disciplinaRepository.findAll()) {
			map.put(disciplina.getId(), disciplina.getCodDisciplina() + " - " + disciplina.getNomeDisciplina());
		}
		return map;
	}
	public Turma cadastrar(TurmaDTO dto) throws CustomException, IOException, Exception{
		Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		
		Turma turma = new Turma();
		Curso curso = cursoRepository.findOne(dto.getCursoId());
		Disciplina disciplina = disciplinaRepository.findOne(dto.getDisciplinaId());
		Professor professor = professorRepository.findByUsuario(usuario);
		if(professor == null)
			throw CustomException.PROFESSOR_NAO_ENCONTRADO;
		turma.setCurso(curso);
		turma.setDisciplina(disciplina);
		turma.setCodigoTurma(dto.getCodigoTurma());
		turma.setProfessor(professor);
		turma.setDataCriacao(new Date());
		turma.setTurmaAtiva(true);
		if(dto.getCalendario() != null && !dto.getCalendario().isEmpty()){
			turma.setCalendario(dto.getCalendario());
		}
		turma.setAulasDia(dto.getAulasDia());
		turma = turmaRepository.save(turma);
		
		List<TurmaAluno> turmaAlunos = new ArrayList<TurmaAluno>();
		turmaAlunos = getListaTurmaAlunos(dto, turma);
		if(turmaAlunos != null && !turmaAlunos.isEmpty())
			turmaAlunoRepository.save(turmaAlunos);
		return turma;
	}

	public List<TurmaAluno> getListaTurmaAlunos(TurmaDTO dto, Turma turma) throws CustomException, IOException, Exception{
		MultipartFile file = dto.getArquivo();

		if(!file.isEmpty())
			dto.setAlunos(getListaAlunosDTO(file));
		
		List<TurmaAluno> turmaAlunos = new ArrayList<TurmaAluno>();
		if(dto.getAlunos() != null)
			for (AlunoDTO alunoDTO : dto.getAlunos()) {
				Aluno alunoCadastrado = alunoRepository.findByProntuario(alunoDTO.getProntuario()); 
				Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getProntuario());
				if(alunoCadastrado == null)
					alunoCadastrado = alunoRepository.save(aluno);
				TurmaAluno turmaAluno = new TurmaAluno();
				turmaAluno.setTurma(turma);
				turmaAluno.setAluno(alunoCadastrado);
				turmaAluno.setCodigo(alunoDTO.getCodigo());
				turmaAlunos.add(turmaAluno);
			}
		
		return turmaAlunos;
	}
	public List<AlunoDTO> getListaAlunosDTO(MultipartFile file) throws IOException, CustomException, Exception {
		return usuarioService.lerXLSAlunos(file);
	}
	public List<TurmaDTO> getTurmaDTO(){
		List<Turma> turmas = (List<Turma>) turmaRepository.findAll();
		List<TurmaDTO> turmasDTO = new ArrayList<TurmaDTO>();
		for (Turma turma : turmas) {
			TurmaDTO turmaDTO = new TurmaDTO();
			turmaDTO.setCodigoTurma(turma.getCodigoTurma());
			turmasDTO.add(turmaDTO);
		}
		return turmasDTO;
	}
	public TurmaDTO getTurmaDTOId(long idTurma){
		Turma turma = turmaRepository.findOne(idTurma);
		TurmaDTO dto = new TurmaDTO();
		dto.setAulasDia(turma.getAulasDia());
		dto.setCodigoTurma(turma.getCodigoTurma());
		dto.setCursoId(turma.getCurso().getId());
		dto.setDisciplinaId(turma.getDisciplina().getId());
		if(turma.getCalendario() != null && !turma.getCalendario().isEmpty()){
			dto.setCalendario(turma.getCalendario());
		}
		AlunoDTO adto;
		List<AlunoDTO> lAluno = new ArrayList<AlunoDTO>();
		for(TurmaAluno tAluno : turma.getTurmaAlunos()){
			adto = new AlunoDTO();
			adto.setNome(tAluno.getAluno().getNome());
			adto.setProntuario(tAluno.getAluno().getProntuario());
			adto.setCodigo(tAluno.getCodigo());
			lAluno.add(adto);
		}
		for(AlunoDTO aludto : lAluno){
			System.out.println(aludto.getNome());
		}
			 
		dto.setAlunos(lAluno);
		
		return dto;
	}
	public Turma editarId(TurmaDTO dto, long idTurma) throws CustomException, IOException, Exception{
		Turma turma = turmaRepository.findOne(idTurma);
		Curso curso = cursoRepository.findOne(dto.getCursoId());
		Disciplina disciplina = disciplinaRepository.findOne(dto.getDisciplinaId());
		turma.setCurso(curso);
		turma.setDisciplina(disciplina);
		turma.setCalendario(dto.getCalendario());
		turma.setCodigoTurma(dto.getCodigoTurma());
		turma.setTurmaAtiva(true);
		turma.setAulasDia(dto.getAulasDia());
		turma = turmaRepository.save(turma);		
		
		List<TurmaAluno> turmaAlunos = new ArrayList<TurmaAluno>();
		turmaAlunos = editarTurmaAluno(dto, turma);
		if(turmaAlunos != null && !turmaAlunos.isEmpty())
			turmaAlunoRepository.save(turmaAlunos);
		return turma;
	}
	public List<TurmaMobileDTO> getTurmasMobile(String token) throws CustomException{
		Usuario usuario = usuarioService.getUsuarioAccessToken(token);
		List<TurmaMobileDTO> turmasDTO = new ArrayList<TurmaMobileDTO>();
		if(usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR) || usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR)){
			Professor professor =  professorRepository.findByUsuario(usuario);
			List<Turma> turmas = turmaRepository.findByProfessor(professor);
			for (Turma turma : turmas) {
				TurmaMobileDTO turmaDTO =  new TurmaMobileDTO();
				turmaDTO.setDisciplina(turma.getCodigoTurma() + " - " + turma.getDisciplina().getCodDisciplina());
				turmaDTO.setIdTurma(turma.getId());
				turmaDTO.setAtividades(atividadeService.getListAtividadeProfessor(professor,turma.getId()));
				turmaDTO.setCalendario(getCalendarioId(turma.getId()));
				turmasDTO.add(turmaDTO);
			}
		}else{
			Aluno aluno = alunoRepository.findByUsuario(usuario);
			
			List<Turma> turmasAluno = turmaAlunoRepository.findByIdAluno(aluno.getId());
			for (Turma turmaAluno : turmasAluno) {
				TurmaMobileDTO turmaDTO = new TurmaMobileDTO();
				turmaDTO.setDisciplina(turmaAluno.getDisciplina().getCodDisciplina());
				turmaDTO.setIdTurma(turmaAluno.getId());
				turmaDTO.setAtividades(atividadeService.getListAtividadeAluno(aluno, turmaAluno.getId()));
				Integer faltas = frequenciaAlunoRepository.findByIdAlunoAndIdTurma(aluno.getId(), turmaAluno.getId());
				turmaDTO.setFaltas(faltas == null ? "" : faltas.toString());
				turmaDTO.setCalendario(getCalendarioId(turmaAluno.getId()));
				turmasDTO.add(turmaDTO);
			}
		}
		return turmasDTO;		
	}
	public String getCalendarioId(long idTurma){
		Turma turma = turmaRepository.findOne(idTurma);
		Pattern pattern = Pattern.compile(Pattern.quote("ical/") + "(.*?)" +Pattern.quote("/private"));
		String calId = null;
		if(turma.getCalendario() != null){
			Matcher matcher = pattern.matcher(turma.getCalendario());
			while(matcher.find()){
				calId = matcher.group(1).toString();
				break;
			}
		}
	    return calId;
	}
	public List<TurmaAluno> editarTurmaAluno(TurmaDTO dto, Turma turma) throws CustomException, Exception{
		List<TurmaAluno> turmaAlunos = new ArrayList<TurmaAluno>();
		List<Aluno> alu = new ArrayList<Aluno>();
		if(!dto.getAlunos().isEmpty() && dto.getAlunos() != null){
			for (AlunoDTO alunoDTO : dto.getAlunos()) {
				Aluno alunoCadastrado = new Aluno();
				alunoCadastrado = alunoRepository.findByProntuario(alunoDTO.getProntuario()); 

				if(alunoCadastrado != null) {
					alunoCadastrado.setNome(alunoDTO.getNome());
					alunoRepository.save(alunoCadastrado);
				}
				Aluno aluno = new Aluno(alunoDTO.getNome(), alunoDTO.getProntuario());

				if(alunoCadastrado == null || alunoCadastrado.getId() <= 0){
					alunoCadastrado = alunoRepository.save(aluno);
				}

				TurmaAluno turmaAluno = turmaAlunoRepository.findByTurmaAndAluno(turma, alunoCadastrado);
				if(turmaAluno == null){				
					turmaAluno = new TurmaAluno();
					turmaAluno.setTurma(turma);
					turmaAluno.setAluno(alunoCadastrado);
					turmaAluno.setCodigo(alunoDTO.getCodigo());
					turmaAlunos.add(turmaAluno);				
				}

				alu.add(alunoCadastrado);
			}
			
			List<TurmaAluno> ta = turmaAlunoRepository.findByTurmaAlunoNotIn(turma, alu);
			for(TurmaAluno turAlu : ta){
				turmaAlunoRepository.delete(turAlu);
			}
		}

		return turmaAlunos;
	}
	public void desativarTurma(long idTurma) throws Exception{
		Turma turma = turmaRepository.findOne(idTurma);
		turma.setTurmaAtiva(false);
		turmaRepository.save(turma);
	}
	public List<TurmaUsuarioDTO> getTurmasArquivadasDTO(){
        Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
        List<TurmaUsuarioDTO> turmas = new ArrayList<TurmaUsuarioDTO>();
        if(usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR) || usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR)){
            Professor professor = professorRepository.findByUsuario(usuario);
            List<Turma> turmasProfessor = turmaRepository.findByProfessorDesativada(professor);

            if(!turmasProfessor.isEmpty()){
                for (Turma turmaProfessor : turmasProfessor) {
                    turmas.add(new TurmaUsuarioDTO(turmaProfessor.getId(), turmaProfessor.getDisciplina().getCodDisciplina(), turmaProfessor.getCodigoTurma(), turmaProfessor.getCalendario(),""));
                }
            }
        }else{
            Aluno aluno = alunoRepository.findByUsuario(usuario);
            List<Turma> turmasAluno = turmaAlunoRepository.findByIdAluno(aluno.getId());
            for (Turma turmaAluno : turmasAluno) {
                Integer faltas = frequenciaAlunoRepository.findByIdAlunoAndIdTurma(aluno.getId(), turmaAluno.getId());
                turmas.add(new TurmaUsuarioDTO(turmaAluno.getId(), turmaAluno.getDisciplina().getCodDisciplina(), turmaAluno.getDisciplina().getNomeDisciplina(), turmaAluno.getCalendario(), faltas == null ? "" : faltas.toString()));
            }
        }

        return turmas;
    }
    public List<TurmaUsuarioDTO> getTurmasFinalizadasDTO(){
        Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
        List<TurmaUsuarioDTO> turmas = new ArrayList<TurmaUsuarioDTO>();
        if(usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR) || usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR)){
            Professor professor = professorRepository.findByUsuario(usuario);
            List<Turma> turmasProfessor = turmaRepository.findByProfessorFinalizada(professor);

            if(!turmasProfessor.isEmpty()){
                for (Turma turmaProfessor : turmasProfessor) {
                    turmas.add(new TurmaUsuarioDTO(turmaProfessor.getId(), turmaProfessor.getDisciplina().getCodDisciplina(), turmaProfessor.getCodigoTurma(), turmaProfessor.getCalendario(),""));
                }
            }
        }
        return turmas;
    }

}
