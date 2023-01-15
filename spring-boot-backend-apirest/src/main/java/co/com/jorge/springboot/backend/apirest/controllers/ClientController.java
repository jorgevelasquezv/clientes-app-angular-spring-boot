package co.com.jorge.springboot.backend.apirest.controllers;

import co.com.jorge.springboot.backend.apirest.models.entities.Client;
import co.com.jorge.springboot.backend.apirest.models.services.IClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private IClientsService clientsService;

    @GetMapping("/clientes")
    public ResponseEntity<List<Client>> findAll() {
        List<Client> clients = clientsService.findAll();

        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        Client client = clientsService.findById(id);

        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/clientes")
    public ResponseEntity<Client> save(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientsService.save(client));
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Client update(@RequestBody Client client, @PathVariable long id) {
        Client clientDB = clientsService.findById(id);

        clientDB.setName(client.getName());
        clientDB.setLastname(client.getLastname());
        clientDB.setEmail(client.getEmail());

        return  clientsService.save(clientDB);
    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Client client = clientsService.findById(id);

        if (client != null) {
            clientsService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();
    }

}
