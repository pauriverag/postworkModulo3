package org.bedu.postwork.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Venta;
import org.bedu.postwork.services.VentaService;
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
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/{ventaid}")
    public ResponseEntity<Venta> getVenta(@PathVariable Long ventaid){
        Optional<Venta> ventaDb = ventaService.obtenVenta(ventaid);
        if (ventaDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La venta especificada no existe.");
        }
        return ResponseEntity.ok(ventaDb.get());
    }


    @GetMapping
    public ResponseEntity<List<Venta>> getVentas(){
        return ResponseEntity.ok(ventaService.obtenVentas());
    }


    @PostMapping
    public ResponseEntity<Void> crearVenta(@RequestBody Venta venta){
        Venta ventaNueva = ventaService.guardaVenta(venta);
        return ResponseEntity.created(URI.create(String.valueOf(ventaNueva.getVentaId()))).build();
    }


    @PutMapping("/{ventaid}")
    public ResponseEntity<Void> actualizaVenta(@PathVariable Long ventaid, @RequestBody Venta venta){
        ventaService.actualizaVenta(venta);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/{ventaid}")
    public ResponseEntity<Void> eliminaVenta(@PathVariable Long ventaid){
        ventaService.eliminaVenta(ventaid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
