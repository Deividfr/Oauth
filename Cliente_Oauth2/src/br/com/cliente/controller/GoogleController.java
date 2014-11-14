/**
 * 
 */
package br.com.cliente.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cliente.dao.ClienteDAO;
import br.com.cliente.model.Cliente;
import br.com.cliente.model.GoogleOauthHelper;

/**
 * @author y8qu
 *
 */
@Controller
public class GoogleController {

	

	    private GoogleOauthHelper helper = new GoogleOauthHelper(); 
		
	    private ClienteDAO dao;
	    private Cliente cliente;
	    
	    
	    @Autowired
	    public GoogleController(ClienteDAO clienteDao)
	    {
	    	this.cliente = new Cliente();
	    	this.dao = clienteDao;
	    }
	    
	    @RequestMapping("/google")
	    public void home(HttpSession session,
	                                    HttpServletRequest request,
	                                             HttpServletResponse response) throws IOException
	    {       
	    	
	         if((request.getParameter("code") == null) ||
	                   (request.getParameter("state") == null))
	         {
	             response.sendRedirect(helper.buildLoginUrl());
	             
	             session.setAttribute("state", helper.getStateToken());   
	         } 
	         
	         
	         else if((request.getParameter("code") != null) ||
	                    (request.getParameter("state") != null) && 
	                        (request.getParameter("state").equals(session.getAttribute("state"))))
	         {
	             session.removeAttribute("state");
	             
	             JSONObject jObj = new JSONObject(helper.
	            		 getUsuarioInfoJson(request.getParameter("code")));
	         
	            cliente.setNome(jObj.getString("given_name"));
	            cliente.setSobrenome(jObj.getString("family_name"));
	            cliente.setEmail(jObj.getString("email"));
	            cliente.setSenha("senha"); 
	            
	            if (dao.buscarPorEmail(cliente.getEmail())!= null)
	            {
	            	cliente = dao.buscarPorEmail(cliente.getEmail());
	            	session.setAttribute("cliente", cliente);
	            }else 
	            {
	            	dao.adicionar(cliente);
	            	session.setAttribute("cliente", cliente);
	            }
	            
	            
	        }
	    }
	}


