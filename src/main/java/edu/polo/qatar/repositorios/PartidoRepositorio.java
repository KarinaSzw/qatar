package edu.polo.qatar.repositorios;

import edu.polo.qatar.entidades.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartidoRepositorio extends CrudRepository<Partido, Long> {
    
}