import br.com.monitum.dto.UsuarioDTO;
import br.com.monitum.entity.Usuario;
import br.com.monitum.service.UsuarioService;
import junit.framework.Assert;
import junit.framework.TestCase;


public class usuarioServiceTest extends TestCase {
		
	UsuarioService user;
	UsuarioDTO dto;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		user = new UsuarioService();
		
		dto = new UsuarioDTO();
		dto.setEmail("teste123@email.com");
		dto.setFoto("C:\foto\naruto.png");
		dto.setNome("teste");
		dto.setRegistro("1234567");
		dto.setSenha("123456");
		dto.setTelefone("11944445555");
		
		
	}
		public void testCriarUsuario() {
			// teste 01 criar usuario
		Assert.assertEquals("", user.criarUsuario(dto));
	}

}
