package com.suarez.pedido;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class ConsultasPedido {
    private final ClienteClient clientes;
    private final ProductoClient productos;
    public ConsultasPedido(ClienteClient clientes, ProductoClient productos) {
        this.clientes = clientes; this.productos = productos;
    }
    @Retry(name = "clienteService", fallbackMethod = "clienteFallback")
    public ClienteDTO cliente(Long id) { return clientes.get(id); }
    public ClienteDTO clienteFallback(Long id, Throwable error) {
        return new ClienteDTO(id, "Cliente temporal no disponible", "", "", "", "I");
    }
    @Bulkhead(name = "productoService", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "productoFallback")
    public ProductoDTO producto(Long id) { return productos.get(id); }
    public ProductoDTO productoFallback(Long id, Throwable error) {
        return new ProductoDTO(id, "Producto temporal no disponible", 0, 0, null, null);
    }
}
