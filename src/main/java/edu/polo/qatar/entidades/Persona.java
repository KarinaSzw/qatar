package edu.polo.qatar.entidades;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
@MappedSuperclass
public abstract class Persona {
    
    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    protected String    nombre;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Campo obligatorio")
    @Past(message = "La fecha de nacimiento no puede ser futura")
    protected Date      fechaNacimiento;
    
    @OneToOne
    protected Pais      lugarDeNacimiento;

    public Persona() {
    }
    
    public Persona(String nombre, Date fechaNacimiento, Pais lugarDeNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.lugarDeNacimiento = lugarDeNacimiento;
    }
}