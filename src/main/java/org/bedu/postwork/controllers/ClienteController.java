package org.bedu.postwork.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Cliente;
import org.bedu.postwork.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping("/{clienteid}")
    public ResponseEntity<Cliente> getCliente(@Valid @PathVariable Long clienteid){
        Optional<Cliente> clienteDb = clienteService.obtenCliente(clienteid);

        if (clienteDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El cliente especificado no existe.");
        }

        return ResponseEntity.ok(clienteDb.get());
    }


    @GetMapping
    public ResponseEntity<List<Cliente>> getClientes(){
        return ResponseEntity.ok(clienteService.obtenClientes());
    }


    @PostMapping
    public ResponseEntity<Void> crearCliente(@Valid @RequestBody Cliente cliente){
        Cliente clienteNuevo = clienteService.guardaCliente(cliente);
        return ResponseEntity.created(URI.create(String.valueOf(clienteNuevo.getId()))).build();
    }


    @PutMapping("/{clienteid}")
    public ResponseEntity<Void> actualizaCliente(@Valid @PathVariable Long clienteid, @RequestBody Cliente cliente){
        clienteService.actualizaCliente(cliente);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/{clienteid}")
    public ResponseEntity<Void> eliminaCliente(@Valid @PathVariable Long clienteid){
        clienteService.eliminaCliente(clienteid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
