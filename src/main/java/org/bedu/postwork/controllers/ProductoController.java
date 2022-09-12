package org.bedu.postwork.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.model.Producto;
import org.bedu.postwork.services.ProductoService;
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
@RequestMapping("/producto")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping("/{productoid}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long productoid){
        Optional<Producto> productoDb = productoService.obtenProducto(productoid);
        if (productoDb.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto especificado no existe.");
        }
        return ResponseEntity.ok(productoDb.get());
    }


    @GetMapping
    public ResponseEntity<List<Producto>> getProductos(){
        return ResponseEntity.ok(productoService.obtenProductos());
    }


    @PostMapping
    public ResponseEntity<Void> crearproducto(@RequestBody Producto producto){
        Producto productoNuevo = productoService.guardaProducto(producto);
        return ResponseEntity.created(URI.create(String.valueOf(productoNuevo.getId()))).build();
    }


    @PutMapping("/{productoid}")
    public ResponseEntity<Void> actualizaProducto(@PathVariable Long productoid, @RequestBody Producto producto){
        productoService.actualizaProducto(producto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/{productoid}")
    public ResponseEntity<Void> eliminaproducto(@PathVariable Long productoid){
        productoService.eliminaProducto(productoid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
