package br.com.cliente.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;



@Component
@Entity
@Table(name = "cliente")
public class Cliente {


		@NotNull(message = "{cliente.cadastro.senha.obrigatorio}")
		@Size(min = 3, max = 10, message = "{cliente.cadastro.senha.tamanho}")
		private String senha;

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}

		public String getEmail() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setNome(String string) {
			// TODO Auto-generated method stub
			
		}

		public void setSobrenome(String string) {
			// TODO Auto-generated method stub
			
		}

		public void setEmail(String string) {
			// TODO Auto-generated method stub
			
		}
		
		
	}



