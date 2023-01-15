package co.com.jorge.springboot.backend.apirest.models.services;

import co.com.jorge.springboot.backend.apirest.models.entities.Client;

import java.util.List;

public interface IClientsService {

    List<Client> findAll();

    Client findById(Long id);

    Client save(Client client);

    void delete(Long id);

}
