package org.bedu.postwork.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Etapa;
import org.bedu.postwork.services.EtapaService;
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
@RequestMapping("/etapa")
@RequiredArgsConstructor
public class EtapaController {

    private final EtapaService etapaService;

    @GetMapping("/{etapaid}")
    public ResponseEntity<Etapa> getEtapa(@PathVariable Long etapaid){
        Optional<Etapa> etapaDb = etapaService.obtenEtapa(etapaid);
        if (etapaDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La etapa especificada no existe.");
        }
        return ResponseEntity.ok(etapaDb.get());
    }


    @GetMapping
    public ResponseEntity<List<Etapa>> getEtapas(){
        return ResponseEntity.ok(etapaService.obtenEtapas());
    }


    @PostMapping
    public ResponseEntity<Void> crearEtapa(@RequestBody Etapa etapa){
        Etapa etapaNueva = etapaService.guardaEtapa(etapa);
        return ResponseEntity.created(URI.create(String.valueOf(etapaNueva.getEtapaId()))).build();
    }


    @PutMapping("/{etapaid}")
    public ResponseEntity<Void> actualizaEtapa(@PathVariable Long etapaid, @RequestBody Etapa etapa){
        etapaService.actualizaEtapa(etapa);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/{etapaid}")
    public ResponseEntity<Void> eliminaEtapa(@PathVariable Long etapaid){
        etapaService.eliminaEtapa(etapaid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
