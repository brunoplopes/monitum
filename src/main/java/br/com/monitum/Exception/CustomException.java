package br.com.monitum.Exception;

import org.springframework.http.HttpStatus;

public class CustomException extends  RuntimeException {
	private static final long serialVersionUID = 2767596202958572939L;
	private String message;
	private Integer statusCode;
	public CustomException(String message, Integer statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public static final CustomException EMAIL_INVALIDO = new CustomException("Email inválido!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException EMAIL_SENHA_INVALIDO = new CustomException("Email ou senha incorreta!", HttpStatus.UNAUTHORIZED.value());
	public static final CustomException REGISTRO_ALUNO_JA_CADASTRADO = new CustomException("Resgistro já cadastrado!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException EMAIL_JA_CADASTRADO = new CustomException("Email já cadastrado!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException FOTO_NAO_RECORTADA = new CustomException("Recorte sua foto", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException IMAGEM_FORA_DO_PADRAO = new CustomException("Por favor, envie outra foto!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException PAGINA_NAO_ENCONTRADA = new CustomException("Página não encontrada", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException TOKEN_INVALIDO = new CustomException("URL inválida!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException ACCESS_TOKEN_INVALIDO = new CustomException("Token inválido!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException EMAIL_ERRO_TOKEN = new CustomException("Não foi possível alterar a senha!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException SENHAS_DIFERENTES = new CustomException("Por favor, repita a mesma senha nos dois campos!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException SENHAS_INCORRETA_ALTERACAO_PERFIL = new CustomException("Sua senha esta incorreta!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException CADASTRO_CAMPO_VAZIO = new CustomException("Por favor, preencha o campo!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException DOCUMENTO_FORA_DO_PADRAO = new CustomException("Documento fora do padrão, por favor, envie outro!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException TURMA_NAO_ENCONTRADA = new CustomException("Não foi possível localizar a turma!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException PROFESSOR_NAO_ENCONTRADO = new CustomException("Não encontramos seu registro como professor!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException CONTEUDO_USUARIO_TURMA_DIFERENTE = new CustomException("Não foi possível publicar seu conteúdo!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException CONTEUDO_NAO_ENCONTRADO = new CustomException("Não foi possível encontrar tipo de conteúdo!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException ATIVIDADE_NAO_ENCONTRADA = new CustomException("Não foi possível encontrar atividade!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException ALUNO_JA_CADASTRADO_NO_GRUPO = new CustomException("Você já está cadastrado em um grupo nessa atividade!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException GRUPO_NAO_ENCONTRADO = new CustomException("Grupo não encontrado!", HttpStatus.PRECONDITION_FAILED.value());
	public static final CustomException FREQUENCIA_NAO_ENCONTRADA = new CustomException("Não encontramos registro para essa chamada!", HttpStatus.PRECONDITION_FAILED.value());
	
}
