package br.com.monitum.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.entity.Curso;
import br.com.monitum.repository.CursoRepository;

@Service
public class CursoService {
	@Autowired
	private CursoRepository cursoRepository;
	
	public String criarCurso(String cursoNome, String codCurso) throws CustomException{
		if(cursoNome != null && !cursoNome.equals("") && !cursoNome.isEmpty()){
			Curso curso = new Curso(cursoNome, codCurso);
			cursoRepository.save(curso);
			return String.valueOf(cursoRepository.save(curso).getId());
		}
		throw CustomException.CADASTRO_CAMPO_VAZIO;
	}
	public String editarCurso(String nomeCurso, String codigoCurso, long idCurso) throws CustomException{
		if(nomeCurso != null && codigoCurso != null && !nomeCurso.isEmpty() && !codigoCurso.isEmpty() && idCurso > 0){
			Curso curso = cursoRepository.findOne(idCurso);
			curso.setNomeCurso(nomeCurso);
			curso.setCodCurso(codigoCurso);
			return String.valueOf(cursoRepository.save(curso).getId());
		}
		throw CustomException.CADASTRO_CAMPO_VAZIO;
	}
	public List<Curso> getCurso() throws CustomException{
		List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
		return cursos;
	}
	public Curso getCursoId(long idCurso) throws CustomException{
		Curso curso = cursoRepository.findOne(idCurso);
		return curso;
	}
}
