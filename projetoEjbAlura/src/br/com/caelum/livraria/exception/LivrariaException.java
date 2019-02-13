package br.com.caelum.livraria.exception;

import javax.ejb.ApplicationException;

/*Vamos abrir a classe LivrariaException e deixar explícito que 
 * ela é uma Application Exception. Para isso usaremos a anotação
 *  @ApplicationException que possui atributos para redefinir o 
 *  comportamento referente a transação. Vamos fazer uma configuração 
 *  para que essa Application Exception cause sim um rollback
 *  */
@ApplicationException(rollback = true)
public class LivrariaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/*
	 * Uma System Exception é algo grave, imprevisto, não deveria acontecer e sempre
	 * causa um rollback da transação. Normalmente são exceções de infra-estrutura.
	 * Além disso, aquele Session Bean que lançou a exceção é invalidado e retirado
	 * do pool de objetos. Por padrão, qualquer exceção unchecked é System
	 * Exception.
	 * 
	 * Uma Application Exception pode acontecer durante a vida da aplicação e
	 * relacionada ao domínio. Por isso não causa rollback e nem invalida o Session
	 * Bean. Por padrão, qualquer exceção checked é Application Exception.
	 */

	public LivrariaException() {
		super();
	}

	public LivrariaException(String message, Throwable cause) {
		super(message, cause);
	}

	public LivrariaException(String message) {
		super(message);
	}

	public LivrariaException(Throwable cause) {
		super(cause);
	}

}
