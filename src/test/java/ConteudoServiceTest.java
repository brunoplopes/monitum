import br.com.monitum.service.ConteudoService;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ConteudoServiceTest extends TestCase {

	ConteudoService conteudo;
	
	protected void setUp() throws Exception {
		super.setUp();
		conteudo = new ConteudoService();
	}

	public void testCriarConteudo() {
		// teste 03, simular o envio de uma msg pelo professor
		fail("Not yet implemented");
	}
	
	public void testCriarConteudoAluno() {
		// teste 06, simular o envio de uma msg pelo aluno
		fail("Not yet implemented");
	}

	public void testGetConteudoList() {
		// teste  05, listar conteudos
		//Assert.assertEquals(List<ConteudoDTO>, conteudo.getConteudoList(1));
	}

}
