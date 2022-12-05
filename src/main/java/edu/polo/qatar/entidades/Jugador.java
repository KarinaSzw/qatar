package edu.polo.qatar.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper=true)
@Table(name="jugadores")
public class Jugador extends Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @NotNull(message = "Debe elegir un valor")
    private Seleccion seleccion;

    private String foto;

    public Jugador(String nombre, Date fechaNacimiento, Pais lugarDeNacimiento, String ciudadDeNacimiento) {
        super(nombre, fechaNacimiento, lugarDeNacimiento, ciudadDeNacimiento);
    }

    public Jugador(Long id, Seleccion seleccion, String foto, String nombre, Date fechaNacimiento, Pais lugarDeNacimiento, String ciudadDeNacimiento) {
        super(nombre, fechaNacimiento, lugarDeNacimiento, ciudadDeNacimiento);
        this.id = id;
        this.seleccion = seleccion;
        this.foto = foto;
    }

}