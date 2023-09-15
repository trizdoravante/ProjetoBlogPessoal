package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		
	usuarioRepository.deleteAll();
	
	usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", " ", null));
	}
	
	@Test
	@DisplayName("Cadastrar um Usuário")
	
	public void deveCriarUmUsuario() {
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Beatriz Campos", "beatrizcaos@email.com.br", "12345678910", "-", null));
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar",
				HttpMethod.POST, corpoRequisicao, Usuario.class);
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
	
	
	}
	
	@Test
	@DisplayName ("Não deve permitir duplicação de usuário")
	public void naoDeveDuplicarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L, "Leonardo Izidoro",
				"leonardoizidoro@email.com.br", "325161208987", "-", null));
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L, "Leonardo Izidoro",
				"leonardoizidoro@email.com.br", "325161208987", "-", null));
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar",
				HttpMethod.POST, corpoRequisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
	}
	
	@Test
	@DisplayName("Atualizar um usuário")
	public void deveAtualizarUmUsuario() {
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
				"Luis Guerreiro", "luisguerreiro@email.com.br", "5678999006", "-", null ));
		
		Usuario usuarioUpdate = new Usuario (usuarioCadastrado.get().getId(),
				"Luis Guerreiro da Silva", "luisguerreiro@email.com.br", "5678999006", "-", null);
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root@root.com", 
				"rootroot").exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		
			}
	@Test
	@DisplayName("Lista todos os usuários")
	public void deveMostrarTodosOsUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario (0L,
				"Giulia Scotch", "giulia_scotch@email.com.br", "@123hju", "-", null));
		
		usuarioService.cadastrarUsuario(new Usuario (0L,
				"Carolina Scotch", "carol_scotch@email.com.br", "ubudbduwuw9", "-", null));
		
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root@root.com", 
				"rootroot").exchange("/usuarios/all", HttpMethod.GET, null, String.class);
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
}
