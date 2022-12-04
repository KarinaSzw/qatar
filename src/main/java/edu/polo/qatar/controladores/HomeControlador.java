package edu.polo.qatar.controladores;

import edu.polo.qatar.repositorios.EstadioRepositorio;
import edu.polo.qatar.servicios.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeControlador {

    @Autowired
    EstadioRepositorio estadioRepositorio;

    @Autowired
    EstadioServicio estadioServicio;

    @RequestMapping("/")
    public ModelAndView home()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Inicio");
        maw.addObject("vista", "inicio/home");

        long random = (long) ((Math.random() * (estadioRepositorio.count() - 1)) + 1);
        maw.addObject("estadio", estadioServicio.getById(random));
        return maw;  
    }

    @RequestMapping("/ejemplo")
    public ModelAndView ejemplo()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Ejemplo");
        maw.addObject("vista", "inicio/ejemplo");
        return maw;  
    }
    
}