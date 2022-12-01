package edu.polo.qatar.controladores;

import edu.polo.qatar.entidades.*;
import edu.polo.qatar.servicios.*;
import java.io.*;
import java.nio.file.Paths;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("partidos")
public class PartidoControlador implements WebMvcConfigurer {

	@Autowired
    PartidoServicio partidoServicio;
    @Autowired
    SeleccionServicio seleccionServicio;
    
    @Autowired
    EstadioServicio estadioServicio;

	@GetMapping
    private ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de partidos");
        maw.addObject("vista", "partidos/index");
        maw.addObject("partidos", partidoServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del partido #" + id);
        maw.addObject("vista", "partido/ver");
        maw.addObject("partido", partidoServicio.getById(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Partido partido)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear partido");
        maw.addObject("vista", "partidos/crear");        
        maw.addObject("selecciones", seleccionServicio.getAll());
        maw.addObject("estadios", estadioServicio.getAll());
        maw.addObject("partido", partido);
        return maw;
	}


  
	@PostMapping("/crear")
	public ModelAndView guardar( @Valid Partido partido, BindingResult br, RedirectAttributes ra)
    {
     		if ( br.hasErrors() ) {
			return this.crear(partido);
		}
		partidoServicio.save(partido);      
        ModelAndView maw = this.index();
        maw.addObject("exito", "partido guardado exitosamente");
            return maw;
                
	}

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id,  Partido partido)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar partido");
        maw.addObject("vista", "partidos/editar");       
        maw.addObject("selecciones", seleccionServicio.getAll());
        maw.addObject("estadios", estadioServicio.getAll());
        maw.addObject("partido", partidoServicio.getById(id));
        return maw;
    }
	

    @PutMapping("/editar/{id}")
    public ModelAndView update(@PathVariable("id") Long id, @Valid Partido partido, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
            ModelAndView maw = new ModelAndView();
            maw.setViewName("fragments/base");
            maw.addObject("titulo", "Editar partido");
            maw.addObject("vista", "partidos/editar");            
            maw.addObject("selecciones", seleccionServicio.getAll());
            maw.addObject("estadios", estadioServicio.getAll());
            maw.addObject("partido", partido);
			return maw;
		}

        Partido registro = partidoServicio.getById(id);
        registro.setFechaPartido(partido.getFechaPartido());
        registro.setSeleccionLocal(partido.getSeleccionLocal());
        registro.setSeleccionVisitante(partido.getSeleccionVisitante());
        registro.setGolesLocal(partido.getGolesLocal());
        registro.setGolesVisitante(partido.getGolesVisitante());
        registro.setEstadio(partido.getEstadio());
        
        ModelAndView maw = this.index();
        partidoServicio.save(registro);
        maw.addObject("exito", "Partido editado exitosamente");
		return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {
        partidoServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Partido borrado exitosamente");
		return maw;
    }
    
}