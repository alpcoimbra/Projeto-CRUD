package com.devsuperior.projetoCRUD.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.projetoCRUD.entities.Client;


@Repository
public interface ClientRepositoy extends JpaRepository<Client, Long>{
		
}
