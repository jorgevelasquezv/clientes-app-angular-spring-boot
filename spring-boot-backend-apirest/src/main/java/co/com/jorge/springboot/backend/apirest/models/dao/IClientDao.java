package co.com.jorge.springboot.backend.apirest.models.dao;

import co.com.jorge.springboot.backend.apirest.models.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClientDao extends JpaRepository<Client, Long> {

}
