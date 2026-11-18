package com.suarez.cliente;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController @RequestMapping("/api/clientes")
public class ClienteController {
    @Value("${simulacion.demora-ms:0}") private long demoraMs;
    private final Map<Long, Cliente> db = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong();
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Cliente crear(@Valid @RequestBody Cliente x) { long id=seq.incrementAndGet(); var z=new Cliente(id,x.nombre(),x.apellido(),x.direccion(),x.nroDni(),x.estado()); db.put(id,z); return z; }
    @GetMapping public Collection<Cliente> listar() { return List.copyOf(db.values()); }
    @GetMapping("/{id}") public Cliente obtener(@PathVariable long id) {
        if(demoraMs>0) try { Thread.sleep(demoraMs); } catch(InterruptedException e) { Thread.currentThread().interrupt(); throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Consulta interrumpida"); }
        var x=db.get(id); if(x==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND); return x;
    }
    @PutMapping("/{id}") public Cliente actualizar(@PathVariable long id,@Valid @RequestBody Cliente x) { obtenerExistente(id); var z=new Cliente(id,x.nombre(),x.apellido(),x.direccion(),x.nroDni(),x.estado()); db.put(id,z); return z; }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable long id) { if(db.remove(id)==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
    private Cliente obtenerExistente(long id) { var x=db.get(id); if(x==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND); return x; }
}
