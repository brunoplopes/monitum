package br.com.monitum.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

import br.com.monitum.Exception.CustomException;
import br.com.monitum.dto.ConteudoDTO;
import br.com.monitum.dto.NotificacaoDTO;
import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Conteudo;
import br.com.monitum.entity.Mobile;
import br.com.monitum.repository.MobileRepository;
import br.com.monitum.entity.Professor;
import br.com.monitum.entity.TipoConteudo;
import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.entity.Turma;
import br.com.monitum.entity.Usuario;
import br.com.monitum.init.PropertyConstants;
import br.com.monitum.repository.AlunoRepository;
import br.com.monitum.repository.ConteudoRepository;
import br.com.monitum.repository.ProfessorRepository;
import br.com.monitum.repository.TipoConteudoRepository;
import br.com.monitum.repository.TurmaAlunoRepository;
import br.com.monitum.repository.TurmaRepository;
import br.com.monitum.repository.UsuarioRepository;
import br.com.monitum.security.UsuarioContext;

@Service
public class ConteudoService {
	Logger logger = Logger.getLogger(ConteudoService.class);
	@Autowired
	private ConteudoRepository conteudoRepository;
	@Autowired
	private TipoConteudoRepository tipoConteudoRepository;
	@Autowired
	private TurmaRepository turmaRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TurmaAlunoRepository turmaAlunoRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private MobileRepository mobileRepository;
	@Autowired
	private Environment env;
	
	public ConteudoDTO getConteudo(long idTurma) throws CustomException{
		ConteudoDTO dto = new ConteudoDTO();
		if(turmaRepository.findOne(idTurma) == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		dto.setIdTurma(idTurma);
		return dto;
	}
	public void criarConteudo(ConteudoDTO dto, long idTurma) throws CustomException{
		Usuario usuario = usuarioRepository.findOne(UsuarioContext.getUsuarioLogado().getId());
		Turma turma = turmaRepository.findOne(idTurma);
		Professor professor = null;
		if(usuario.getTipoUsuario().equals(TipoUsuario.ESTUDANTE)){
			Aluno aluno = alunoRepository.findByUsuario(usuario);
			if(turmaAlunoRepository.findByTurmaAndAluno(turma, aluno) == null)
				throw CustomException.CONTEUDO_USUARIO_TURMA_DIFERENTE;
		}else{
			professor = professorRepository.findByUsuario(usuario);
			if(turma.getProfessor().getId() != professor.getId())
				throw CustomException.CONTEUDO_USUARIO_TURMA_DIFERENTE;
		}
		
		TipoConteudo tipoConteudo = tipoConteudoRepository.findOne(dto.getIdTipoConteudo());
		if(tipoConteudo == null)
			throw CustomException.CONTEUDO_NAO_ENCONTRADO;
			
		Conteudo conteudo = new Conteudo();
		conteudo.setDataCriacao(new Date());
		conteudo.setDescricao(dto.getDescricao());
		conteudo.setTitulo(dto.getTitulo());
		conteudo.setTurma(turma);
		conteudo.setUsuario(usuario);
		conteudo.setTipoConteudo(tipoConteudo);
		
		if(tipoConteudo.getId() == 1 && professor != null){
			enviarNotificacao(turma, conteudo, professor);
		}
		
		conteudoRepository.save(conteudo);
	}
	public Map<Long, String> getTipoConteudoList(){
		Map<Long, String> tiposconteudo = new HashMap<Long, String>();
		List<TipoConteudo> tipos =  (List<TipoConteudo>) tipoConteudoRepository.findAll();
		for (TipoConteudo tipoConteudo : tipos) {
			tiposconteudo.put(tipoConteudo.getId(), tipoConteudo.getNomeTipoConteudo());
		}
		Usuario usuario = usuarioService.getUsuario();
		if(usuario.getTipoUsuario().equals(TipoUsuario.ESTUDANTE))
			tiposconteudo.remove(1L);
		return tiposconteudo;
	}
	public List<ConteudoDTO> getConteudoList(long idTurma){
		List<ConteudoDTO> conteudosDTO = new ArrayList<ConteudoDTO>();
		Usuario usuario = usuarioService.getUsuario();
		Turma turma =  new Turma();
		if(usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR) || usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR))
			 turma = turmaRepository.findByIdAndProfessor(idTurma, usuarioService.getProfessor());
		else turma = turmaRepository.findByIdTurmaAndIdAluno(idTurma, usuarioService.getAluno().getId());
		List<Conteudo> conteudos = conteudoRepository.findByTurma(turma);
		for (Conteudo conteudo : conteudos) {
			ConteudoDTO dto = new ConteudoDTO();
			dto.setIdConteudo(conteudo.getId());
			dto.setIdUsuario(conteudo.getUsuario().getId());
			dto.setNome(conteudo.getUsuario().getNome());
			dto.setDataPostagem(conteudo.getDataCriacao());
			dto.setTitulo(conteudo.getTitulo());
			dto.setTipoConteudo(conteudo.getTipoConteudo().getNomeTipoConteudo());
			dto.setDescricao(conteudo.getDescricao());
			dto.setUrlFoto(usuarioService.getUrlFoto(conteudo.getUsuario()));
			dto.setDataEdicao(conteudo.getDataEdicao());
			dto.setEditado(conteudo.getEditado());
			conteudosDTO.add(dto);
		}
		return conteudosDTO;
	}
	
	public void deletarConteudo(long idConteudo) throws Exception{
		Conteudo conteudo = conteudoRepository.findOne(idConteudo);
		try{
			conteudoRepository.delete(conteudo);
		}catch(Exception e){
			logger.error("erro na aplicação: " + e.toString());
			throw new Exception();
		}		
	}
	
	public ConteudoDTO getConteudoDTOId(long idConteudo) throws CustomException{
		Conteudo conteudo = conteudoRepository.findOne(idConteudo);
		ConteudoDTO dto = new ConteudoDTO();
		dto.setIdConteudo(idConteudo);
		dto.setDescricao(conteudo.getDescricao());
		dto.setIdTurma(conteudo.getTurma().getId());
		dto.setTitulo(conteudo.getTitulo());
		dto.setIdTipoConteudo(conteudo.getTipoConteudo().getId());
		dto.setEditado(conteudo.getEditado());
		dto.setDataEdicao(conteudo.getDataEdicao());
		return dto;
	}
	
	public void editarConteudo(ConteudoDTO dto) throws CustomException{
		Conteudo conteudo = conteudoRepository.findOne(dto.getIdConteudo());
		conteudo.setDescricao(dto.getDescricao());
		conteudo.setTitulo(dto.getTitulo());
		conteudo.setTipoConteudo(tipoConteudoRepository.findOne(dto.getIdTipoConteudo()));
		conteudo.setDataEdicao(new Date());
		conteudo.setEditado(true);
		conteudoRepository.save(conteudo);
	}
	public void enviarNotificacaoGoogle(ArrayList<String> gcmTokenList, Conteudo conteudo){
		try{
		     Sender sender = new Sender(env.getRequiredProperty(PropertyConstants.PROPERTY_GOOGLE_KEY));
		     String data = conteudo.getDescricao();
		     Message message = new Message.Builder()
		                        .collapseKey("1")
		                        .timeToLive(3)
		                        .delayWhileIdle(true)
		                        .addData("message",data)
		                        .addData("title", "Monitum")
		                        .addData("icon", "icon")
		                        .build();
		     MulticastResult result = sender.send(message, gcmTokenList, 1);
               sender.send(message, gcmTokenList, 1);

               System.out.println(result.toString());
               if (result.getResults() != null) {
                   int canonicalRegId = result.getCanonicalIds();
                   if (canonicalRegId != 0) {
                   }
               } else {
                   int error = result.getFailure();
                   System.out.println(error);
               }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void enviarNotificacao(Turma turma, Conteudo conteudo, Professor professor){
		List<Usuario> usuarios = turmaAlunoRepository.findByIdTurma(turma.getId());
		usuarios.add(professor.getUsuario());
		List<Mobile> mobiles =  mobileRepository.findByUsuarioIn(usuarios);
		ArrayList<String> tokensGCM = new ArrayList<String>();
		for (Mobile mobile : mobiles) {
			tokensGCM.add(mobile.getGcmToken());
		}
		enviarNotificacaoGoogle(tokensGCM, conteudo);
	}
	public void notificacao(String token, String titulo, String mensagem, long idTurma) throws CustomException{
		Usuario usuario = usuarioService.getUsuarioAccessToken(token);
		Professor professor = professorRepository.findByUsuario(usuario);
		Turma turma = turmaRepository.findByIdAndProfessor(idTurma, professor);
		if(turma == null)
			throw CustomException.TURMA_NAO_ENCONTRADA;
		
		Conteudo conteudo = new Conteudo();
		conteudo.setDataCriacao(new Date());
		conteudo.setDescricao(mensagem);
		conteudo.setTitulo(titulo);
		conteudo.setTurma(turma);
		conteudo.setUsuario(usuario);
		conteudo.setTipoConteudo(tipoConteudoRepository.findOne(1L));
		conteudoRepository.save(conteudo);
		
		enviarNotificacao(turma, conteudo, professor);
	}
	public List<NotificacaoDTO> getNotificacoes(String token){
		Usuario usuario = usuarioService.getUsuarioAccessToken(token);
		List<NotificacaoDTO> notificaoes = new ArrayList<NotificacaoDTO>();
		List<Turma> turmas = new ArrayList<Turma>();
		if(usuario.getTipoUsuario().equals(TipoUsuario.COORDENADOR) || usuario.getTipoUsuario().equals(TipoUsuario.PROFESSOR)){
			Professor professor =  professorRepository.findByUsuario(usuario);
			turmas = turmaRepository.findByProfessor(professor);
			List<Conteudo> conteudos = conteudoRepository.findByTipoConteudoAndTurmasIn(1L, turmas); 
			for (Conteudo conteudo : conteudos) {
				NotificacaoDTO dto = new NotificacaoDTO();
				dto.setTitulo(conteudo.getTitulo());
				dto.setMensagem(conteudo.getDescricao());
				dto.setTurma(conteudo.getTurma().getCodigoTurma() + " - " + conteudo.getTurma().getDisciplina().getCodDisciplina());
				notificaoes.add(dto);
			}
		}else{
			Aluno aluno = alunoRepository.findByUsuario(usuario);
			turmas = turmaAlunoRepository.findByIdAluno(aluno.getId());
			List<Conteudo> conteudos = conteudoRepository.findByTipoConteudoAndTurmasIn(1L, turmas); 
			for (Conteudo conteudo : conteudos) {
				NotificacaoDTO dto = new NotificacaoDTO();
				dto.setTitulo(conteudo.getTitulo());
				dto.setMensagem(conteudo.getDescricao());
				dto.setTurma(conteudo.getTurma().getDisciplina().getCodDisciplina());
				notificaoes.add(dto);
			}
		}
		return notificaoes;
	}
}
