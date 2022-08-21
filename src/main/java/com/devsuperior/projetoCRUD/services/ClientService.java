package com.devsuperior.projetoCRUD.services;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.projetoCRUD.dto.ClientDTO;
import com.devsuperior.projetoCRUD.entities.Client;
import com.devsuperior.projetoCRUD.repositories.ClientRepositoy;
import com.devsuperior.projetoCRUD.services.exceptions.DatabaseException;
import com.devsuperior.projetoCRUD.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepositoy repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findAll(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("ID NAO LOCALIZADO"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO inserir(ClientDTO dto) {
		Client entity = new Client();
		entity.setBirtDate(dto.getBirtDate());
		entity.setChildren(dto.getChildren());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO atualizar(Long id, ClientDTO dto) {
		try{
		Client entity = repository.getOne(id);
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirtDate(dto.getBirtDate());
		entity.setChildren(dto.getChildren());
		entity = repository.save(entity);
		return new ClientDTO(entity);
		}
		catch(EntityExistsException e) {
			throw new ResourceNotFoundException("Id " + id + " nao localizado ");
		
		}
	}

	 
	public void deletar(Long id) {
		try {
		repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id " + id + " nao localizado ");
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("violação de integridade");		
		}
	}
}
