package edu.polo.qatar.entidades;

import com.fasterxml.jackson.annotation.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="selecciones")
public class Seleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JsonBackReference
    @NotNull(message = "Debe elegir un valor")
    protected Pais pais;

    private String apodo;

    @OneToMany(mappedBy = "seleccion")
    @JsonManagedReference
    private List<Jugador> jugadores;

    @NotNull(message = "Campo obligatorio")
    private int mundialesGanados;

    @NotNull(message = "Campo obligatorio")
    private int participacionesPrevias;

    private String foto;

    @Override
    public String toString() {
        return this.apodo;
    }
    
}