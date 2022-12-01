package edu.polo.qatar.servicios;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PartidoServicio {

    @Autowired
    PartidoRepositorio partidoRepositorio;

    public List<Partido> getAll()
    {
        List<Partido> lista = new ArrayList<Partido>();
        partidoRepositorio.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }

    public Partido getById(Long id)
    {
        return partidoRepositorio.findById(id).get();
    }

    public void save(Partido partido)
    {
        partidoRepositorio.save(partido);
    }

    public void delete(Long id)
    {
        partidoRepositorio.deleteById(id);
    }
    
}