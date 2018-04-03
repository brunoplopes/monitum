package br.com.monitum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import br.com.monitum.Exception.CustomException;
import br.com.monitum.entity.Disciplina;
import br.com.monitum.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	public String criarDisciplina(String nomeDisciplina, String codigoDisciplina) throws CustomException{
		if(nomeDisciplina != null && codigoDisciplina != null && !nomeDisciplina.isEmpty() && !codigoDisciplina.isEmpty()){
			Disciplina disciplina = new Disciplina(nomeDisciplina,codigoDisciplina);
			return String.valueOf(disciplinaRepository.save(disciplina).getId());
		}
		throw CustomException.CADASTRO_CAMPO_VAZIO;
	}
	public String editarDisciplina(String nomeDisciplina, String codigoDisciplina, long idDisciplina) throws CustomException{
		if(nomeDisciplina != null && codigoDisciplina != null && !nomeDisciplina.isEmpty() && !codigoDisciplina.isEmpty() && idDisciplina > 0){
			Disciplina disciplina = disciplinaRepository.findOne(idDisciplina);
			disciplina.setNomeDisciplina(nomeDisciplina);
			disciplina.setCodDisciplina(codigoDisciplina);
			return String.valueOf(disciplinaRepository.save(disciplina).getId());
		}
		throw CustomException.CADASTRO_CAMPO_VAZIO;
	}
	public List<Disciplina> getDisciplina() throws CustomException{
		List<Disciplina> disciplinas = (List<Disciplina>) disciplinaRepository.findAll();
		return disciplinas;
	}
	public Disciplina getDisciplinaId(long idDisciplina) throws CustomException{
		Disciplina disciplina = disciplinaRepository.findOne(idDisciplina);
		return disciplina;
	}
}
