package br.com.monitum.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import br.com.monitum.dto.*;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Mobile;
import br.com.monitum.repository.MobileRepository;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.entity.Usuario;
import br.com.monitum.init.PropertyConstants;
import br.com.monitum.repository.AlunoRepository;
import br.com.monitum.repository.ProfessorRepository;
import br.com.monitum.repository.UsuarioRepository;
import br.com.monitum.security.UsuarioContext;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioService {
	Logger logger = Logger.getLogger(UsuarioService.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Resource
	private Environment env;
	@Autowired
	private EmailService emailService;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private TurmaAlunoService turmaAlunoService;
	@Autowired
	private MobileRepository  mobileRepository;
	
	private FileOutputStream osf;

	public List<Usuario> getUsuarios() {
		List<Usuario> usuario = (List<Usuario>) usuarioRepository.findAll();
		return usuario;
	}

	public String getPathFoto(Usuario usuario) {
		if (usuario.getFoto() != null && !usuario.getFoto().isEmpty())
			return env.getRequiredProperty(PropertyConstants.PROPERTY_FILE_UPLOAD_PATH).trim() + "/" + usuario.getFoto() + ".png";
		return null;
	}
	public String getUrlFoto(Usuario usuario){
		if( usuario != null && usuario.getFoto() != null && !usuario.getFoto().isEmpty())
			return "/usuario/foto/" + usuario.getFoto();
		return "/assets/img/avatar.jpeg";
	}
	public String criarFoto(UsuarioDTO dto, String token) throws IOException{
		String imageBase64 = dto.getFoto().split(",")[1];
		byte[] scanBytes = org.springframework.security.crypto.codec.Base64.decode(imageBase64.getBytes());
		File of = new File(env.getRequiredProperty(PropertyConstants.PROPERTY_FILE_UPLOAD_PATH).trim() + "/"
				+ token.toString() + ".png");
		of.setReadable(true);
		osf = new FileOutputStream(of);
		osf.write(scanBytes);
		osf.flush();
		return token.toString();
	}
	public String criarUsuario(UsuarioDTO dto) throws CustomException {
		if(usuarioRepository.findByEmail(dto.getEmail()) != null)
			throw CustomException.EMAIL_JA_CADASTRADO;
		try {
			
			Usuario usuario = new Usuario();
			usuario.setFoto(criarFoto(dto, UUID.randomUUID().toString()));
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setDataCriacao(new Date());
			usuario.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
			usuario.setTelefone(dto.getTelefone());
			usuario.setTipoUsuario(TipoUsuario.ESTUDANTE);
			usuario.setAtivo(true);
			usuario.setToken(UUID.randomUUID().toString());
			criarTipoUsuario(dto, usuarioRepository.save(usuario));
			emailService.enviar(usuario, "cadastro");
		} catch (Exception e) {
			if (dto.getFoto().isEmpty() || dto.getFoto() == null)
				throw CustomException.FOTO_NAO_RECORTADA;
			throw CustomException.IMAGEM_FORA_DO_PADRAO;
		}
		return "";
	}
	public void criarTipoUsuario(UsuarioDTO dto, Usuario usuario) throws CustomException{
		Professor professor = professorRepository.findByRegistroProfessorAndEmail(dto.getRegistro(), dto.getEmail());
		if(professor != null){
			professor.setUsuario(usuario);
			usuario.setTipoUsuario(professor.isCoordenador() ? TipoUsuario.COORDENADOR : TipoUsuario.PROFESSOR);
			professorRepository.save(professor);
			usuarioRepository.save(usuario);
		}else{
			Aluno aluno = alunoRepository.findByProntuario(dto.getRegistro());
			if(aluno != null && aluno.getUsuario() != null)
				throw CustomException.REGISTRO_ALUNO_JA_CADASTRADO;
			if(aluno != null){
				aluno.setUsuario(usuario);
				alunoRepository.save(aluno);
			}
			else{
				aluno = new Aluno(usuario, dto.getRegistro(), usuario.getNome());
				alunoRepository.save(aluno);
			}
		}
	}
	@SuppressWarnings("resource")
	public List<AlunoDTO> lerXLSAlunos(MultipartFile file) {
        List<AlunoDTO> alunosDTO = new ArrayList<AlunoDTO>();
		try {
            File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + UUID.randomUUID().toString() + file.getOriginalFilename());
            file.transferTo(tmpFile);
			FileInputStream arquivo = new FileInputStream(tmpFile);
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			XSSFSheet sheetAlunos = workbook.getSheetAt(2);
			Iterator<Row> rowIterator = sheetAlunos.iterator();
			sheetAlunos.getLastRowNum();

			for(int i = 1; i < sheetAlunos.getLastRowNum(); i++){
				Row row = sheetAlunos.getRow(i);
                AlunoDTO alunoDTO = new AlunoDTO();
                String valor = null;

				for(int j = 0; j < 5 ; j++){
					Cell cell = row.getCell(j);
					switch(cell.getCellType()){
						case Cell.CELL_TYPE_STRING:
							valor = cell.getStringCellValue();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							valor = String.valueOf(cell.getNumericCellValue());
							break;
						default:
					}

					switch (j){
                        case 0:
                            alunoDTO.setNome(valor);
                            break;
                        case 1:
                            alunoDTO.setProntuario(valor);
                            break;
                        case 4:
                            alunoDTO.setCodigo(valor);
                            break;
                        default:
                    }
				}
                if(alunoDTO.getNome().isEmpty())
                    break;
                alunosDTO.add(alunoDTO);
			}
			arquivo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo Excel n�o encontrado!");
		} catch (IOException e) {
			logger.error("erro na aplicação: " + e.toString());
			e.printStackTrace();
		} catch (POIXMLException e){
			return alunosDTO;
        }
        return alunosDTO;
	}
	public String recuperarSenha(String email) throws CustomException{
		Usuario usuario = usuarioRepository.findByEmail(email);
		if(usuario == null)
			throw CustomException.EMAIL_INVALIDO;
		String url = env.getRequiredProperty(PropertyConstants.PROPERTY_URL) + "/login/recuperar-senha/" + usuario.getToken();
		String html = "<a href=\"http://" + url +"\">Recuperar Senha</a>";
		emailService.enviar(usuario, html);
		return "Enviamos um email de recuperação de senha!";
	}
	public String validarToken(String token) throws CustomException{
		Usuario usuario =  usuarioRepository.findByToken(token);
		if(usuario == null)
			throw CustomException.PAGINA_NAO_ENCONTRADA;
		return token;
	}
	public void mudarSenha(String token, String senha, String senhaRepetida) throws CustomException{
		Usuario usuario = usuarioRepository.findByToken(token);
		if(usuario == null)
			throw CustomException.EMAIL_ERRO_TOKEN;
		if(!senha.equals(senhaRepetida))
			throw CustomException.SENHAS_DIFERENTES;
		usuario.setSenha(new BCryptPasswordEncoder().encode(senha));
		usuarioRepository.save(usuario);
	}
	public Map<Long, String> getTurmaList(){
		Map<Long, String> turmas =  new HashMap<Long, String>();
		List<TurmaUsuarioDTO> turmasDTO = turmaAlunoService.getTurmasDTO();
		for (TurmaUsuarioDTO turmaUsuarioDTO : turmasDTO) {
			turmas.put(turmaUsuarioDTO.getIdTurma(), turmaUsuarioDTO.getCodigo() + " - " + turmaUsuarioDTO.getNomeDisciplina());
		}
		return turmas;
	}
	public Professor getProfessor(){
		Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		return professorRepository.findByUsuario(usuario);
	}
	public Aluno getAluno(){
		Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		return alunoRepository.findByUsuario(usuario);
	}
	public Usuario getUsuario(){
		return usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
	}
	public UsuarioDTO getUsuarioDTO(){
		Usuario usuario = getUsuario();
		UsuarioDTO dto = new UsuarioDTO();
		dto.setEmail(usuario.getEmail());
		dto.setNome(usuario.getNome());
		dto.setTelefone(usuario.getTelefone());
		return dto;
	}
	public void editarPerfil(UsuarioDTO dto) throws CustomException, IOException{
		try {
				Usuario usuario = getUsuario();
				String foto = dto.getFoto();
				if(foto != null && !foto.isEmpty())
					criarFoto(dto, usuario.getFoto());
				if(usuarioRepository.findByEmailAndIdNot(dto.getEmail(), usuario.getId()) != null)
					throw CustomException.EMAIL_JA_CADASTRADO;
				usuario.setEmail(dto.getEmail());
				usuario.setTelefone(dto.getTelefone());
				usuario.setNome(dto.getNome());

				usuarioRepository.save(usuario);
				
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
	
	}
	public void alterarSenhaPerfil(SenhaDTO dto) throws CustomException{
		try {
			Usuario usuario = getUsuario();
			if(usuario.getSenha().equals(new BCryptPasswordEncoder().encode(dto.getSenha())))
				throw CustomException.SENHAS_INCORRETA_ALTERACAO_PERFIL;
			if(!dto.getNovaSenha().equals(dto.getSenhaRepetida()))
				throw CustomException.SENHAS_DIFERENTES;
			usuario.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
			usuarioRepository.save(usuario);
		} catch (Exception e) {
			logger.error("erro na aplicação: " + e.toString());
		}
		
	}
	public PerfilMobileDTO getAccessToken(String email, String senha, String gcmGoogle) throws CustomException, Exception{
		Usuario usuario = usuarioRepository.findByEmail(email);
		if(usuario == null || usuario.getSenha().equals(new BCryptPasswordEncoder().encode(senha)))
			throw CustomException.EMAIL_SENHA_INVALIDO;
		
		Mobile mobile = mobileRepository.findByGcmToken(gcmGoogle);
		
		if(mobile == null){
			mobile = new Mobile();
			mobile.setUsuario(usuario);
			mobile.setAccessToken(UUID.randomUUID().toString());
			mobile.setReceberNotivicacao(true);
			mobile.setGcmToken(gcmGoogle);
		}else mobile.setUsuario(usuario);
		mobileRepository.save(mobile);
		
		PerfilMobileDTO perfil = getPerfilMobileDTO(usuario, mobile.getAccessToken());
		
		
		return perfil;
	}
	public PerfilMobileDTO getPerfilMobileDTO(Usuario usuario, String token){
		PerfilMobileDTO perfil = new PerfilMobileDTO();
		perfil.setEmail(usuario.getEmail());
		perfil.setNome(usuario.getNome());
		perfil.setReceberNotificacao(true);
		perfil.setTelefone(usuario.getTelefone());
		perfil.setTipoUsuario(usuario.getTipoUsuario());
		perfil.setToken(token);
		
		return perfil;
	}
	public Usuario getUsuarioAccessToken(String token) throws CustomException{
		Mobile mobile = mobileRepository.findByAccessToken(token);
		if(mobile == null)
			throw CustomException.TOKEN_INVALIDO;
		return mobile.getUsuario();
	}
	public PerfilMobileDTO getPerfilMobileDTO(String token) throws CustomException{
		Mobile mobile = mobileRepository.findByAccessToken(token);
		if(mobile == null)
			throw CustomException.ACCESS_TOKEN_INVALIDO;
		return getPerfilMobileDTO(mobile.getUsuario(), token);
	}
	public void receberNotificacao(String token, Boolean receberNotificacao){
		Mobile mobile = mobileRepository.findByAccessToken(token);
		if(mobile == null)
			throw CustomException.ACCESS_TOKEN_INVALIDO;
		mobile.setReceberNotivicacao(receberNotificacao);
		mobileRepository.save(mobile);
	}
	public void desconectar(String token){
		Mobile mobile = mobileRepository.findByAccessToken(token);
		mobileRepository.delete(mobile);
	}
}
