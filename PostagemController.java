package com.generation.blogpessoal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.generation.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import com.generation.blogpessoal.model.Postagem;

@RestController
@RequestMapping ("/postagens")
@CrossOrigin(origins = "*", allowedHeaders ="*")
public class PostagemController {
	@Autowired
	private PostagemRepository postagemRepository;
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
@GetMapping("/{id}")
public ResponseEntity<Postagem>getById(@PathVariable Long id){
	return postagemRepository.findById(id)
			.map(resposta->ResponseEntity.ok(resposta))
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
}
@GetMapping("/titulo/{titulo}")
public ResponseEntity<List<Postagem>>getByTitulo(@PathVariable String titulo){
	return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
}


}
