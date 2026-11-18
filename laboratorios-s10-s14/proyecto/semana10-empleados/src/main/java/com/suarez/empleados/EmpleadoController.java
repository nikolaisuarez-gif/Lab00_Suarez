package com.suarez.empleados;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController @RequestMapping("/empleados")
public class EmpleadoController {
    private final EmpleadoRepository empleados;
    public EmpleadoController(EmpleadoRepository empleados){this.empleados=empleados;}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Empleado crear(@Valid @RequestBody Empleado e){e.setId(null);return empleados.save(e);}
    @GetMapping public List<Empleado> listar(){return empleados.findAll();}
    @GetMapping("/{id}") public Empleado obtener(@PathVariable long id){return required(id);}
    @PutMapping("/{id}") public Empleado actualizar(@PathVariable long id,@Valid @RequestBody Empleado e){required(id);e.setId(id);return empleados.save(e);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void borrar(@PathVariable long id){if(!empleados.existsById(id))throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Empleado no encontrado");empleados.deleteById(id);}
    private Empleado required(long id){return empleados.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Empleado no encontrado"));}
}
