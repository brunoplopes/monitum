package br.com.monitum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.monitum.dto.TurmaUsuarioDTO;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.entity.Turma;
import br.com.monitum.entity.Usuario;
import br.com.monitum.repository.AlunoRepository;
import br.com.monitum.repository.FrequenciaAlunoRepository;
import br.com.monitum.repository.ProfessorRepository;
import br.com.monitum.repository.TurmaAlunoRepository;
import br.com.monitum.repository.TurmaRepository;
import br.com.monitum.repository.UsuarioRepository;
import br.com.monitum.security.UsuarioContext;

@Service
public class TurmaAlunoService {
	@Autowired
	private TurmaAlunoRepository turmaAlunoRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private FrequenciaAlunoRepository frequenciaAlunoRepository;
	
	public List<TurmaUsuarioDTO> getTurmasDTO(){
		Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		List<TurmaUsuarioDTO> turmas = new ArrayList<TurmaUsuarioDTO>();
		if(usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR) || usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR)){
			Professor professor = professorRepository.findByUsuario(usuario);
			List<Turma> turmasProfessor = turmaRepository.findByProfessor(professor);			
			
			if(!turmasProfessor.isEmpty()){				
				for (Turma turmaProfessor : turmasProfessor) {
					turmas.add(new TurmaUsuarioDTO(turmaProfessor.getId(), turmaProfessor.getDisciplina().getCodDisciplina(), turmaProfessor.getCodigoTurma(), turmaProfessor.getCalendario(), "0"));
				}
			}
		}else{
			Aluno aluno = alunoRepository.findByUsuario(usuario);
			List<Turma> turmasAluno = turmaAlunoRepository.findByIdAluno(aluno.getId());
			for (Turma turmaAluno : turmasAluno) {
				Integer faltas = frequenciaAlunoRepository.findByIdAlunoAndIdTurma(aluno.getId(), turmaAluno.getId());
				turmas.add(new TurmaUsuarioDTO(turmaAluno.getId(), turmaAluno.getDisciplina().getCodDisciplina(), turmaAluno.getDisciplina().getNomeDisciplina(), turmaAluno.getCalendario(), faltas == null ? "-" : faltas.toString()));
			}
		}
		
		return turmas;
	}
}
