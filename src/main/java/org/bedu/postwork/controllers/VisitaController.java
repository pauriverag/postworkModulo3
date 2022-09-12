package org.bedu.postwork.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Visita;
import org.bedu.postwork.services.VisitaService;
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
@RequestMapping("/visita")
@RequiredArgsConstructor
public class VisitaController {

    private final VisitaService visitaService;

    @GetMapping("/{visitaid}")
    public ResponseEntity<Visita> getVisita(@PathVariable Long visitaid){
        Optional<Visita> visitaDb = visitaService.obtenVisita(visitaid);
        if (visitaDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El visita especificado no existe.");
        }
        return ResponseEntity.ok(visitaDb.get());
    }


    @GetMapping
    public ResponseEntity<List<Visita>> getVisitas(){
        return ResponseEntity.ok(visitaService.obtenVisitas());
    }


    @PostMapping
    public ResponseEntity<Void> crearVisita(@RequestBody Visita visita){
        Visita visitaNuevo = visitaService.guardaVisita(visita);
        return ResponseEntity.created(URI.create(String.valueOf(visitaNuevo.getId()))).build();
    }


    @PutMapping("/{visitaid}")
    public ResponseEntity<Void> actualizaVisita(@PathVariable Long visitaid, @RequestBody Visita visita){
        visitaService.actualizaVisita(visita);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/{visitaid}")
    public ResponseEntity<Void> eliminaVisita(@PathVariable Long visitaid){
        visitaService.eliminaVisita(visitaid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
}
