package edu.polo.qatar.servicios;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class EntrenadorServicio {

    @Autowired
    EntrenadorRepositorio entrenadorRepositorio;

    public List<Entrenador> getAll()
    {
        List<Entrenador> lista = new ArrayList<Entrenador>();
        lista = (ArrayList<Entrenador>) entrenadorRepositorio.findAll();
        return lista;
    }

    public Entrenador getById(Long id)
    {
        return entrenadorRepositorio.findById(id).get();
    }

    public void save(Entrenador entrenador)
    {
        entrenadorRepositorio.save(entrenador);
    }

    public void delete(Long id)
    {
        entrenadorRepositorio.deleteById(id);
    }
    
}