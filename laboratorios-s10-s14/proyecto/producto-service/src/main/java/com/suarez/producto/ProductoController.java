package com.suarez.producto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController @RequestMapping("/api/productos")
public class ProductoController {
    @Value("${simulacion.demora-ms:0}") private long demoraMs;
    private final Map<Long,Producto> db=new LinkedHashMap<>(); private final AtomicLong seq=new AtomicLong(); private final CategoriaClient categorias;
    public ProductoController(CategoriaClient categorias){this.categorias=categorias;}
    record Detalle(Long id,String nombre,double precio,int stock,Long categoriaId,CategoriaDTO categoria){}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Producto crear(@Valid @RequestBody Producto x){categorias.obtener(x.categoriaId());long id=seq.incrementAndGet();var z=new Producto(id,x.nombre(),x.precio(),x.stock(),x.categoriaId());db.put(id,z);return z;}
    @GetMapping public Collection<Producto> listar(){return List.copyOf(db.values());}
    @GetMapping("/{id}") public Detalle obtener(@PathVariable long id){demorar();var x=existente(id);return detalle(x);}
    @PutMapping("/{id}") public Producto actualizar(@PathVariable long id,@Valid @RequestBody Producto x){existente(id);categorias.obtener(x.categoriaId());var z=new Producto(id,x.nombre(),x.precio(),x.stock(),x.categoriaId());db.put(id,z);return z;}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable long id){if(db.remove(id)==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
    private Detalle detalle(Producto x){return new Detalle(x.id(),x.nombre(),x.precio(),x.stock(),x.categoriaId(),categorias.obtener(x.categoriaId()));}
    private Producto existente(long id){var x=db.get(id);if(x==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);return x;}
    private void demorar(){if(demoraMs>0)try{Thread.sleep(demoraMs);}catch(InterruptedException e){Thread.currentThread().interrupt();throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Consulta interrumpida");}}
}
