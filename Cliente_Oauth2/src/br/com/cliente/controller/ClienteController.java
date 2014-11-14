/**
 * 
 */
package br.com.cliente.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.cliente.dao.ClienteDAO;
import br.com.cliente.model.Cliente;

/**
 * @author y8qu
 * @param <Usuario>
 *
 */
@Controller
public class ClienteController<Usuario> {
			private ClienteDAO dao;
		
		@Autowired
		public ClienteController(ClienteDAO dao){
			this.dao = dao;
		}
		
		@RequestMapping(value = "/login", method = RequestMethod.GET)
		public String login(HttpSession session, Map<String, Object> model) {
			model.put("sucesso", session.getAttribute("sucesso"));
			return "cliente/login";
		}
		
		@RequestMapping(value = "/login", method = RequestMethod.POST)
		public String fazerLogin(@RequestParam("email") String email, @RequestParam("senha") String senha, HttpSession session, Map<String, Object> model){
			Cliente cliente = dao.buscarPorLogin(email, senha);
			session.removeAttribute("sucesso");
			if (cliente == null){
				model.put("msg", "Email ou senha inválidos");
				return "cliente/login";
			}else{
				session.setAttribute("cliente",cliente);
				return "reuniao/agenda";
			}
		}
		
		@RequestMapping(value = "/registro", method = RequestMethod.GET)
		public String registro(Map<String, Object> model){
			model.put("cliente", new Cliente());
			return "cliente/registro";
		}
		
		@RequestMapping(value = "/registro", method = RequestMethod.POST)
		public String fazerRegistro(Cliente cliente, HttpSession session){
			Cliente us = dao.buscarPorEmail(cliente.getEmail());
			if (us == null){
				dao.adicionar(cliente);
				session.setAttribute("cliente", cliente);
				session.setAttribute("sucesso", "Seu cadastro foi criado com sucesso!");
				return "redirect:/login";			
			}else{
				return "cliente/erro_cadastro";
			}
		}
		
		@RequestMapping(value = "/sair")
		public String logout(HttpSession session) {
			session.invalidate();
			return "redirect:/";
		}

	}



