package co.com.jorge.springboot.backend.apirest.models.services;

import co.com.jorge.springboot.backend.apirest.models.dao.IClientDao;
import co.com.jorge.springboot.backend.apirest.models.entities.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientsService{

    @Autowired
    private IClientDao clientDao;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Client> clientOp = clientDao.findById(id);
        clientOp.ifPresent(client -> clientDao.delete(client));
    }
}
