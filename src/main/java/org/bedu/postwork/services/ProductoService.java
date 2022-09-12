package org.bedu.postwork.services;

import lombok.RequiredArgsConstructor;
import org.bedu.postwork.controllers.mappers.ProductoMapper;
import org.bedu.postwork.model.Producto;
import org.bedu.postwork.persistence.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper mapper;

    public Producto guardaProducto(Producto producto) {
        return mapper.productoEntityToProductoModel(
                repository.save(mapper.productoModelToProductoEntity(producto))
        );
    }

    public List<Producto> obtenProductos(){
        return repository.findAll().stream().map(Producto ->
                mapper.productoEntityToProductoModel(Producto)).collect(Collectors.toList());
    }

    public Optional<Producto> obtenProducto(long idProducto) {
        return repository.findById(idProducto)
                .map(producto -> Optional.of(mapper.productoEntityToProductoModel(producto)))
                .orElse(Optional.empty());
    }

    public void eliminaProducto(long idProducto){
        repository.deleteById(idProducto);
    }

    public Producto actualizaProducto(Producto producto){
        return mapper.productoEntityToProductoModel(
                repository.save(mapper.productoModelToProductoEntity(producto))
        );
    }

    public long cuenteProductos(){
        return repository.count();
    }

}
