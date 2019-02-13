package br.com.caelum.livraria.interceptador;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LogInterceptador {

	/*
	 * Há algumas obrigações na assinatura. A primeira é que o método deve retornar
	 * um Object e objeto especial que estará automaticamente disponível dentro do
	 * interceptador e que se chama InvocationContext
	 * 
	 * Para terminar a implementação do interceptador falta uma configuração que
	 * deixe claro para o EJB Container que o método realmente intercepta o fluxo.
	 * Essa configuração é feita através de uma anotação que se chama @AroundInvoke
	 * 
	 * Quais são as classes e métodos que serão interceptados? Como o EJB Container
	 * sabe que queremos, por exemplo, interceptar as chamadas da classe AutorDao e
	 * não do LivroDao Isso também deve ser configurado na classe a ser
	 * interceptada, nesse caso a classe AutorDao, com a anotação @Interceptors. A
	 * anotação recebe um array de classes interceptadoras. Podemos usar a
	 * anotação @Interceptors em cima da classe ou em cima de um método especifico
	 * 
	 * No entanto, isso gera um outro problema. Se existirem 200 DAOs, será preciso
	 * abrir todas essas classes para colocar a anotação. A configuração ficará
	 * espalhada pelas classes! Para resolver isso há uma outra forma de associar o
	 * interceptador às classes DAOs, através de um XML. O arquivo deve se chamar
	 * ejb-jar.xml e ficar, como se trata de uma aplicação web, dentro da pasta
	 * WEB-INF.
	 * 
	 * Ao abrir o arquivo podemos ver que o mesmo possui dois elementos principais.
	 * O primeiro pode definir uma lista de interceptadores, aqui temos apenas um, o
	 * LogInterceptador. O segundo define onde os interceptadores são aplicados.
	 * Repare o elemento <ejb-name> que possui um asterisco. Isso significa que
	 * queremos associar o interceptador para todos os EJBs. Invés do asterisco
	 * podemos usar o nome do EJB, como por exemplo AutorDao
	 * 
	 * Então, um interceptador permite ligar e desligar um serviço com os Session
	 * Beans, sem alterar um bean especifico. Fizemos apenas um monitoramento, mas
	 * poderíamos implementar algo muito mais sofisticado com segurança ou cache.
	 * Repare também que seria possível implementar o gerenciamento da transação
	 * manualmente, através do UserTransaction no interceptador. Podemos injetar
	 * qualquer recurso dentro do interceptador. É bom pensar que o interceptador
	 * faz parte da funcionalidade do Session Bean, só que fica separado e serve
	 * para vários beans.
	 */
	@AroundInvoke
	public Object intercepta(InvocationContext context) throws Exception {
		long millis = System.currentTimeMillis();

		/*
		 * Através do objeto context podemos continuar a execução da aplicação, ou seja,
		 * chamar o método no DAO. O InvocationContext possui um método proceed() que
		 * "prossegue" com o método interceptado. Além disso, ao chamar o proceed() é
		 * necessário tratar uma exceção que faremos através do throws
		 */
		/*
		 * O lugar no interceptador onde chamamos ctx.proceed() é muito importante, o
		 * código da classe interceptada vai executada onde esse proceed é chamado
		 */
		Object o = context.proceed();

		/*
		 * Trabalhando com InvocationContext O InvocationContext possui vários métodos
		 * que ajudam a descobrir quais métodos realmente foram interceptados. Vamos
		 * aproveitar o objeto context para pegar o nome do método e o nome da classe
		 */

		String metodo = context.getMethod().getName();
		String nomeClasse = context.getTarget().getClass().getSimpleName();

		System.out.println(nomeClasse + ", " + metodo);
		System.out.println("Tempo gasto: " + (System.currentTimeMillis() - millis));

		/*
		 * Para finalizar o método falta retornar algo. Novamente entra o método
		 * proceed() no jogo. O retorno desse método representa o possível retorno do
		 * método do DAO. Vamos guardar o retorno em uma variável auxiliar e retornar no
		 * final do método
		 */

		return o;

	}
}
