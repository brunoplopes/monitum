package br.com.monitum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.entity.Aluno;
import br.com.monitum.repository.AlunoRepository;

@Service
public class AlunoService {
	@Autowired
	private AlunoRepository alunoRepository;
	
	public String editarAluno(String nomeAluno, String prontuarioAluno, long idAluno) throws CustomException{
		if(nomeAluno != null && prontuarioAluno != null && !nomeAluno.isEmpty() && !prontuarioAluno.isEmpty() && idAluno > 0){
			Aluno aluno = alunoRepository.findOne(idAluno);
			aluno.setNome(nomeAluno);
			aluno.setProntuario(prontuarioAluno);
			return String.valueOf(alunoRepository.save(aluno).getId());
		}
		throw CustomException.CADASTRO_CAMPO_VAZIO;
	}
	public List<Aluno> getAluno() throws CustomException{
		List<Aluno> aluno = (List<Aluno>) alunoRepository.findAll();
		return aluno;
	}
}
