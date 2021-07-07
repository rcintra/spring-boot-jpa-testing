package com.rcintra.rest.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteRepositoryTests {

	@Autowired
	private ClienteRepository repository;
	
	// Junit test for cliente
	@Test
	@Order(1)
	@Rollback(value = false)
	public void saveClienteTest() {
		
		Cliente cliente = Cliente.builder()
				.nome("Rafael")
				.sobrenome("Cintra")
				.email("ra.cintra@gmail.com")
				.build();
		
		repository.save(cliente);
		
		assertThat(cliente.getId()).isGreaterThan(0);
		
	}
	
	@Test
	@Order(2)
	public void getClienteTest() {
		Cliente cliente = repository.findById(1L).get();
		
		assertThat(cliente.getId()).isEqualTo(1L);
	}
	
	@Test
	@Order(3)
	public void getListOfClienteTest() {
		List<Cliente> clientes = repository.findAll();
		
		assertThat(clientes.size()).isGreaterThan(0);
	}
	
	@Test
	@Order(4)
	public void updateClienteTest() {
		Cliente cliente = repository.findById(1L).get();
		
		cliente.setEmail("rafael@rcintra.com");
		
		Cliente clienteUpdate = repository.save(cliente);
		
		assertThat(clienteUpdate.getEmail()).isEqualTo("rafael@rcintra.com");
	}
	
	@Test
	@Order(5)
	@Rollback(value = false)
	public void deleteClienteTest() {
		
		Cliente cliente = repository.findById(1L).get();
		
		repository.delete(cliente);
		
		Cliente cliente1 = null;
		
		Optional<Cliente> optionalCliente = repository.findByEmail("rafael@rcintra.com");
		
		if (optionalCliente.isPresent()) {
			cliente1 = optionalCliente.get();
		}
		
		assertThat(cliente1).isNull();
	}
}
