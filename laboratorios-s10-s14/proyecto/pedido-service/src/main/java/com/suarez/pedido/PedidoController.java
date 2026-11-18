package com.suarez.pedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController @RequestMapping("/api/pedidos")
public class PedidoController {
    enum Estado { PENDIENTE, ATENDIDO, ANULADO }
    record Nuevo(@NotNull Long clienteId,@NotNull Long productoId,@Positive int cantidad) {}
    record Pedido(Long id,long clienteId,long productoId,int cantidad,Estado estado) {}
    record PedidoDetalle(Long id,int cantidad,Estado estado,ClienteDTO cliente,ProductoDTO producto) {}
    private final Map<Long,Pedido> db=new LinkedHashMap<>(); private final AtomicLong seq=new AtomicLong(); private final ConsultasPedido consultas;
    PedidoController(ConsultasPedido consultas){this.consultas=consultas;}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Pedido crear(@Valid @RequestBody Nuevo x){var z=new Pedido(seq.incrementAndGet(),x.clienteId(),x.productoId(),x.cantidad(),Estado.PENDIENTE);db.put(z.id(),z);return z;}
    @GetMapping public Collection<Pedido> listar(){return List.copyOf(db.values());}
    @GetMapping("/{id}") public PedidoDetalle obtener(@PathVariable long id){var x=existente(id);return new PedidoDetalle(id,x.cantidad(),x.estado(),consultas.cliente(x.clienteId()),consultas.producto(x.productoId()));}
    @PatchMapping("/{id}/estado") public Pedido estado(@PathVariable long id,@RequestParam Estado valor){var x=existente(id);var z=new Pedido(x.id(),x.clienteId(),x.productoId(),x.cantidad(),valor);db.put(id,z);return z;}
    private Pedido existente(long id){var x=db.get(id);if(x==null)throw new ResponseStatusException(HttpStatus.NOT_FOUND);return x;}
}
