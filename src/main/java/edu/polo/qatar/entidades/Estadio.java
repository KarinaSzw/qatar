package edu.polo.qatar.entidades;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Range;

import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="estadios")
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Range(min = 1, max=100000, message = "Valor fuera de rango")
    private int capacidad;

    @NotNull
    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String nombre;

    @NotNull
    @NotBlank(message = "Campo obligatorio")
    @Size(max = 250, message= "Nombre demasiado largo")
    private String ciudad;

    @NotNull
    @NotBlank(message = "Campo obligatorio")
    @Lob
    private String descripcion;

    private String foto;

}