package com.suarez.categoria;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*; import java.util.concurrent.atomic.AtomicLong;
@RestController @RequestMapping("/api/categorias")
public class CategoriaController {
 private final Map<Long,Categoria> db=new LinkedHashMap<>(); private final AtomicLong seq=new AtomicLong();
 @PostMapping @ResponseStatus(HttpStatus.CREATED) public Categoria crear(@Valid @RequestBody Categoria x){long id=seq.incrementAndGet();var z=new Categoria(id,x.nombre(),x.estado()==null?"ACTIVO":x.estado());db.put(id,z);return z;}
 @GetMapping public Collection<Categoria> listar(){return List.copyOf(db.values());}
 @GetMapping("/{id}") public Categoria obtener(@PathVariable long id){var x=db.get(id);if(x==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);return x;}
 @PutMapping("/{id}") public Categoria actualizar(@PathVariable long id,@Valid @RequestBody Categoria x){obtener(id);var z=new Categoria(id,x.nombre(),x.estado());db.put(id,z);return z;}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable long id){if(db.remove(id)==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
}
