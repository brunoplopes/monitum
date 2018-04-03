package br.com.monitum.service;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.CharSequenceReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.ProfessorDTO;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.entity.Usuario;
import br.com.monitum.repository.AlunoRepository;
import br.com.monitum.repository.ProfessorRepository;
import br.com.monitum.repository.UsuarioRepository;

@Service
public class ProfessorService {
	
	Logger logger = Logger.getLogger(ProfessorService.class);
	
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static final String [] ARQUIVO_HEADER_CSV_PROFESSOR = {"Nome","Email","Registro"};
	private static final String PROFESSOR_NOME = "Nome";
	private static final String PROFESSOR_EMAIL = "Email";
	private static final String PROFESSOR_REGISTRO = "Registro";
	
	public void cadastrar(ProfessorDTO dto) throws CustomException, IOException, Exception{
		MultipartFile file = dto.getArquivo();
		if(!file.isEmpty())
			dto.setProfessores(getListaProfessor(file));
		
		try {
			for (Professor professor : dto.getProfessores()) {
				Professor professorCadastrado = professorRepository.findByRegistroProfessor(professor.getRegistroProfessor());
				if(professorCadastrado == null){
					Aluno aluno = alunoRepository.findByProntuario(professor.getRegistroProfessor());
						if(aluno != null && aluno.getUsuario()!=null && aluno.getUsuario().getEmail().equals(professor.getEmail())){
							Usuario usuario = aluno.getUsuario();
							usuario.setTipoUsuario(usuario.getTipoUsuario() == TipoUsuario.COORDENADOR ? TipoUsuario.COORDENADOR : TipoUsuario.PROFESSOR);
							usuarioRepository.save(usuario);
							professor.setUsuario(usuario);
							aluno.setUsuario(null);
							alunoRepository.save(aluno);
					
				}
				professorRepository.save(professor);
			}
		}
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}

	}
	public List<Professor> getListaProfessor(MultipartFile file) throws IOException, CustomException, Exception {
		List<Professor> professors = new ArrayList<Professor>();
		try {
			byte[] scanBytes = file.getBytes();
			Reader reader = new CharSequenceReader(new String(scanBytes));
			CSVParser csvFileParser = null;
	        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(ARQUIVO_HEADER_CSV_PROFESSOR);
	        
	        try {
	            csvFileParser = new CSVParser(reader, csvFileFormat);
	            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
	            
	            for (int i = 1; i < csvRecords.size(); i++) {
	            	CSVRecord record = (CSVRecord) csvRecords.get(i);
	            	Professor professor = new Professor(record.get(PROFESSOR_NOME), record.get(PROFESSOR_EMAIL),record.get(PROFESSOR_REGISTRO));
	            	professors.add(professor);	
				}
	        } 
	        catch (CustomException e) {
	        	//logger.error("erro na aplicação: " + e.toString());
	        	System.out.println("Documento fora do padr�o");
	        	throw CustomException.DOCUMENTO_FORA_DO_PADRAO;
	        	
	        }catch (Exception e) {
	        	logger.error("erro na aplicação: " + e.toString());
	        	throw new Exception();
	        	
	        }
	        finally {
	            try {
	                reader.close();
	                csvFileParser.close();
	            } catch (IOException e) {
	            	logger.error("erro na aplicação: " + e.toString());
	            	System.out.println("Error ao fechar fileReader/csvFileParser !!!");
	                e.printStackTrace();
	            }
	        }
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return professors;
	}
	public String editarProfessor(String nomeProfessor, String registroProfessor, String emailProfessor, long idProfessor, boolean coordenador) throws CustomException{
		if(nomeProfessor != null && registroProfessor != null && emailProfessor != null && !nomeProfessor.isEmpty() && !registroProfessor.isEmpty() && !emailProfessor.isEmpty() && idProfessor > 0){
			Professor professor = professorRepository.findOne(idProfessor);
			professor.setNome(nomeProfessor);
			professor.setRegistroProfessor(registroProfessor);
			professor.setEmail(emailProfessor);
			if(coordenador == true){
				professor.setCoordenador(coordenador);
				if(professor.getUsuario() != null){
					professor.getUsuario().setTipoUsuario(TipoUsuario.COORDENADOR);
				}
			}else{
				professor.setCoordenador(false);
				if(professor.getUsuario() != null){
					professor.getUsuario().setTipoUsuario(TipoUsuario.PROFESSOR);
				}
			}
			return String.valueOf(professorRepository.save(professor).getId());
		}
		throw CustomException.CADASTRO_CAMPO_VAZIO;
	}
	public List<Professor> getProfessor() throws CustomException{
		List<Professor> professores = (List<Professor>) professorRepository.findAll();
		return professores;
	}
}
