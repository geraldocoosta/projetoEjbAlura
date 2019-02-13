package br.com.caelum.livraria.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;

@Stateless
public class AutorService {

	@Inject
	private AutorDao dao;

	public void adiciona(Autor autor) {
		// Mais regras aqui
		/*
		 * Nesse método poderiam ficar mais regras ou chamadas de regras de negócios. É
		 * muito comum ter essa divisão de responsabilidade entre bean, serviço e DAO em
		 * um projeto real. O bean possui muito código relacionado ao JSF (view), o
		 * serviço é o controlador na regra de negócio e o DAO possui o código de
		 * infraestrutura.
		 */
		dao.salva(autor);

		/* exception checked = sem rollback */
//		if (ThreadLocalRandom.current().nextInt(100) > 50)
//			throw new LivrariaException();
	}

	public List<Autor> todosAutores() {
		return dao.todosAutores();
	}
}
