/**
 * 
 */
package br.com.cliente.dao;

import javax.transaction.Transaction;
import javax.websocket.Session;

import net.sf.ehcache.search.expression.Criteria;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.cliente.model.Cliente;

/**
 * @author y8qu
 * @param <Usuario>
 *
 */
@Repository
public class ClienteDAO<Usuario> {

		private SessionFactory factory;
		private org.hibernate.Session session;
		private org.hibernate.Transaction transaction;

		@Autowired
		public ClienteDAO(SessionFactory factory){
			this.factory = factory;
		}

		public void adicionar(Cliente cliente) {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.save(cliente);
			transaction.commit();
			session.close();
		}

		public void atualizar(Cliente cliente){
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.update(cliente);
			transaction.commit();
			session.close();
		}
		
		public void remover(Cliente cliente) {
			session = factory.openSession();
			transaction = session.beginTransaction();
			session.delete(cliente);
			transaction.commit();
			session.close();
		}

		public Cliente carregar(Integer id){
			session = factory.openSession();
			transaction = session.beginTransaction();
			Cliente cliente = (Cliente) session.get(Cliente.class, id);
			transaction.commit();
			session.close();
			return cliente;
		} 
		
		public Cliente buscarPorLogin(String email, String senha) {
			session = factory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.Criteria criteria = session.createCriteria(Cliente.class);
			criteria.add(Restrictions.eq("email", email));
			criteria.add(Restrictions.eq("senha", senha));
			Cliente cliente = (Cliente) criteria.uniqueResult();
			transaction.commit();
			session.close();
			return cliente;
		}

		public Cliente buscarPorEmail(String email) {
			session = factory.openSession();
			transaction = session.beginTransaction();
			org.hibernate.Criteria criteria = session.createCriteria(Cliente.class);
			criteria.add(Restrictions.ilike("email", email));	
			Cliente cliente = (Cliente) criteria.uniqueResult();
			transaction.commit();
			session.close();
			return cliente;
		}

	

}
