package br.com.caelum.livraria.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.livraria.modelo.Autor;

/*
 * O TransactionManagement padrão é o TransactionManagementType.CONTAINER
 * */
/*
 * O TransactionManagement gerenciavel na mão é o TransactionManagementType.BEAN onde o 
 * Session Bean vai gerenciar a transação (chamado BEAN MANAGED TRANSACTION) 
 * Assim também podemos apagar a anotação @TransactionAttribute que não faz mais sentido.
 * Para utilizar desse modo, devemos injetar o @Inject UserTransaction tx;
 * 
 * UserTransaction possui os métodos clássicos relacionados com o gerenciamento da 
 * transação como begin(), commit() e rollback(). O problema é que exige um tratamento 
 * excessivo de exceções checked que poluem muito o código.
 * */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER) // opcional
//@Interceptors({LogInterceptador.class}) // largar isso aqui pq se não vai ficar mt espalhado
public class AutorDao {
	/*
	 * É importante ressaltar que o tipo de gerenciamento CONTAINER e o atributo
	 * REQUIRED já é o padrão adotado para um Session Bean, então não é necessário
	 * configurar
	 */
	// Quando injetamos um entityManager, não podemos utilizar a notação @Inject
	// é preciso usar uma anotação especifica do ejb
	// Para isso, é preciso usar a anotação @PersistenceContext
	@PersistenceContext
	private EntityManager em;

	@PostConstruct
	void aposCriacao() {
		System.out.println("AutorDAO foi criado");
	}

	/*
	 * REQUIRED significa que o JTA garante uma transação rodando quando o método é
	 * chamado. Se não tiver nenhuma transação, uma nova é aberta. Caso já tenha uma
	 * rodando, a atual será utilizada. De qualquer forma, sempre é preciso ter uma
	 * transação
	 */
	/*
	 * Outras tipo de transactionAttribute -> MANDATORY significa obrigatório. Nesse
	 * caso, o container verifica se já existe uma transação rodando, caso
	 * contrário, joga uma exceção. Ou seja, quem faz a chamada deve abrir uma
	 * transação.
	 */
	/*
	 * REQUIRES_NEW indica que sempre deve ter uma nova transação rodando. Caso já
	 * exista, a transação atual será suspensa para abrir uma nova. Caso não tenha
	 * nenhuma rodando, será criada uma nova transação.
	 */
	/*
	 * NEVER é quem indica que jamais deve haver uma transação em execução. Isso
	 * pode ser útil para métodos que obrigatoriamente devem ser executados sem
	 * contexto transacional
	 */
	/*
	 * Com o atributo configurado para SUPPORTS, o código será executado com ou sem
	 * transação. Já com NOT_SUPPORTED o código deverá ser executado sem
	 * transação, caso alguma transação esteja aberta, ela será suspensa
	 * temporariamente até a execução do método acabar.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salva(Autor autor) {
		System.out.println("Salvando autor " + autor.getNome());

		/* Não posso abrir a transação aqui pq o container ta gerenciando */

		// em.getTransaction().begin();
		em.persist(autor);
		// em.getTransaction().commit();

		System.out.println("salvo autor " + autor.getNome());

		/* RuntimeException = Roolback */

		// throw new RuntimeException("Serviço externo deu erro!");
	}

	public List<Autor> todosAutores() {
		return em.createQuery("select a from Autor a", Autor.class).getResultList();
	}

	public Autor buscaPelaId(Integer autorId) {
		Autor autor = this.em.find(Autor.class, autorId);
		return autor;
	}

}
