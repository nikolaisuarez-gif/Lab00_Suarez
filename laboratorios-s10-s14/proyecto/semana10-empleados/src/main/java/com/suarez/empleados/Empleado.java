package com.suarez.empleados;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "empleado")
public class Empleado {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable = false, length = 100) private String nombres;
    @NotBlank @Column(nullable = false, length = 100) private String apellidos;
    @Email @NotBlank @Column(nullable = false, unique = true, length = 100) private String email;
    @Positive @Column(nullable = false, precision = 10, scale = 2) private BigDecimal sueldo;
    protected Empleado() {}
    public Empleado(String nombres, String apellidos, String email, BigDecimal sueldo) { this.nombres=nombres; this.apellidos=apellidos; this.email=email; this.sueldo=sueldo; }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getNombres(){return nombres;} public void setNombres(String x){nombres=x;}
    public String getApellidos(){return apellidos;} public void setApellidos(String x){apellidos=x;}
    public String getEmail(){return email;} public void setEmail(String x){email=x;}
    public BigDecimal getSueldo(){return sueldo;} public void setSueldo(BigDecimal x){sueldo=x;}
}
