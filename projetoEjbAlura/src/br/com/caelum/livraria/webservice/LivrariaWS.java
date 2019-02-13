package br.com.caelum.livraria.webservice;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;

/*Para realmente publicar essa funcionalidade como serviço 
 * falta uma configuração. É preciso anotar a classe com 
 * @WebService para o container EJB publicar o serviços 
 * usando os padrões SOAP e WSDL
 * A anotação @WebService faz parte de um outro padrão Java, 
 * o JAX-WS
 * Com essa anotação, aparentemente todas os métodos dessa 
 * classe resultam em um webservice, ou sei lá. dps vou 
 * assistir o curso sobre JaxWS
 * */
@WebService // anotação necessária para webService com SOAP (faz parte do JAX-WS)
@Stateless
public class LivrariaWS {

	@Inject
	LivroDao dao;

	/*
	 * Vamos melhorar a expressividade da mensagem SOAP alterando o nosso serviço A
	 * primeira anotação é relacionada com o parâmetro do método. Ao usar @WebParam
	 * fica claro para o container EJB que queremos usar aquele nome na mensagem
	 * SOAP. Outro ponto é o retorno. Para dar um nome ao elemento que representa o
	 * retorno usa-se a anotação @WebResult
	 */

	@WebResult(name = "autores")
	public List<Livro> getLivrosPeloNome(@WebParam(name = "titulo") String nome) {
		System.out.println("Livraria WS: procurando pelo nome: " + nome);
		return dao.livrosPeloNome(nome);
	}
}
