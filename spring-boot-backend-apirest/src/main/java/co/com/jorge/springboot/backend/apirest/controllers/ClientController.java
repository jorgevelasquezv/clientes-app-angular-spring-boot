package co.com.jorge.springboot.backend.apirest.controllers;

import co.com.jorge.springboot.backend.apirest.models.entities.Client;
import co.com.jorge.springboot.backend.apirest.models.services.IClientsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Client client = null;
        Map<String, Object> response = new HashMap<>();

        try {
            client = clientsService.findById(id);
            response.put("mensaje", "Cliente ".concat(client.getName()).concat( " actualizado con éxito "));
            response.put("cliente", client);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta a la base datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        if (client != null) {
            return ResponseEntity.ok(response);
        }
        response.put("mensaje", "No se encontró el cliente con ID: ".concat(id.toString()).concat(" en la base de datos"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/clientes")
    public ResponseEntity<?> save(@Valid @RequestBody Client client, BindingResult result) {

        Client clientSave = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '"+ error.getField() + "' "+ error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            clientSave = clientsService.save(client);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.put("mensaje", "El cliente ha sido creado con éxito");
        response.put("cliente", clientSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Client client, BindingResult result, @PathVariable long id) {
        Client clientDB = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> "El campo '"+ error.getField() + "' "+ error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try{
            clientDB = clientsService.findById(id);

            if (clientDB == null){
                response.put("mensaje", "No es posible editar el cliente con ID: ".concat(String.valueOf(id)).concat(" en la base de datos"));
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            clientDB.setName(client.getName());
            clientDB.setLastname(client.getLastname());
            clientDB.setEmail(client.getEmail());
            clientDB = clientsService.save(clientDB);

            response.put("mensaje", "Cliente actualizado con éxito");
            response.put("cliente", clientDB);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try{
            clientsService.delete(id);
            response.put("mensaje", "Cliente eliminado con éxito");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar el delete en la base datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

}
