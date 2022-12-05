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

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Component
@MappedSuperclass
@Data
@SuperBuilder
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
    @JsonBackReference
    @NotNull(message = "Debe elegir un valor")
    protected Pais      paisDeNacimiento;

    @NotNull(message = "Campo obligatorio")
    protected String      ciudadDeNacimiento;

    protected Persona() {
    }
    
    protected Persona(String nombre, Date fechaNacimiento, Pais paisDeNacimiento, String ciudadDeNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.paisDeNacimiento = paisDeNacimiento;
        this.ciudadDeNacimiento = ciudadDeNacimiento;
    }
}