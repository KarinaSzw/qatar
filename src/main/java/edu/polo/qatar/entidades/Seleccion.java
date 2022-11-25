package edu.polo.qatar.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.*;
import javax.persistence.*;
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

    private String nombre;
    private Integer mundialesGanados;
    private Integer participacionesPrevias;

    @OneToMany(mappedBy = "seleccion")
    @JsonManagedReference
    private List<Jugador> jugadores;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Partido", referencedColumnName = "id")
    private Partido partidos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entrenador", referencedColumnName = "id")
    private Entrenador entrenador;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Pais", referencedColumnName = "id")
    private Pais pais;

    @Override
    public String toString() {
        return this.nombre;
    }
    
}