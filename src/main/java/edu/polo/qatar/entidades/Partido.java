package edu.polo.qatar.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
  
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Campo obligatorio")
     public Date  fechaPartido;

     @DateTimeFormat(pattern = "HH:mm")
     @Temporal(TemporalType.TIME)
     @NotNull(message = "Campo obligatorio")
      public Date  horaPartido;
    
     @ManyToOne
     @JsonManagedReference
     private Seleccion seleccionLocal;
     
     @ManyToOne
     @JsonManagedReference
     private Seleccion seleccionVisitante;


    @NotBlank(message = "Campo obligatorio si no tiene goles debe indicar 0")
     public String golesLocal;
 

    @NotBlank(message = "Campo obligatorio si no tiene goles debe indicar 0")
    public String golesVisitante;

    @OneToOne
    public Estadio estadio;
}
