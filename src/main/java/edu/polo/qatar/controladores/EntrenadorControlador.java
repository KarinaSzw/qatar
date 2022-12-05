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
@RequestMapping("entrenadores")
public class EntrenadorControlador implements WebMvcConfigurer {

	@Autowired
    EntrenadorServicio entrenadorServicio;

    @Autowired
    SeleccionServicio seleccionServicio;
    
    @Autowired
    PaisServicio paisServicio;

	@GetMapping
    private ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de entrenadores");
        maw.addObject("vista", "entrenadores/index");
        maw.addObject("entrenadores", entrenadorServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del entrenador #" + id);
        maw.addObject("vista", "entrenadores/ver");
        maw.addObject("entrenador", entrenadorServicio.getById(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Entrenador entrenador)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear entrenador");
        maw.addObject("vista", "entrenadores/crear");
        maw.addObject("paises", paisServicio.getAll());
        maw.addObject("selecciones", seleccionServicio.getAll());
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo, @Valid Entrenador entrenador, BindingResult br, RedirectAttributes ra)
    {
        if ( archivo.isEmpty() )
			br.reject("archivo", "Por favor, cargue una imagen"); 

		if ( br.hasErrors() ) {
			return this.crear(entrenador);
		}

		entrenadorServicio.save(entrenador);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = entrenador.getId() + extension;
        String path = Paths.get("src/main/resources/static/images/entrenadores", foto).toAbsolutePath().toString();
        ModelAndView maw = this.index();

        try {
            archivo.transferTo( new File(path) );
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        entrenador.setFoto(foto);
        entrenadorServicio.save(entrenador);
        maw.addObject("exito", "Entrenador guardado exitosamente");
		return maw;
	}

	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Entrenador entrenador)
    {
        return this.editar(id, entrenador, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Entrenador entrenador, boolean estaPersistido)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar entrenador");
        maw.addObject("vista", "entrenadores/editar");
        maw.addObject("paises", paisServicio.getAll());
        maw.addObject("selecciones", seleccionServicio.getAll());

        if (estaPersistido)
            maw.addObject("entrenador", entrenadorServicio.getById(id));
        else
            entrenador.setFoto( entrenadorServicio.getById(id).getFoto() );

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id,
    @RequestParam(value = "archivo", required = false) MultipartFile archivo,
    @Valid Entrenador entrenador, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
			return this.editar(id, entrenador, false);
		}

        Entrenador registro = entrenadorServicio.getById(id);
        registro.setNombre( entrenador.getNombre() );
        registro.setFechaNacimiento( entrenador.getFechaNacimiento() );
        registro.setSeleccion( entrenador.getSeleccion() );
        ModelAndView maw = this.index();

        if ( ! archivo.isEmpty() ) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = entrenador.getId() + extension;
            String path = Paths.get("src/main/resources/static/images/entrenadores", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo( new File(path) );
            } catch (Exception e) {
                maw.addObject("error", "No se pudo guardar la imagen");
                return maw;
            }

            registro.setFoto(foto);
        }

        entrenadorServicio.save(registro);
        maw.addObject("exito", "Entrenador editado exitosamente");
		return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {
        entrenadorServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Entrenador borrado exitosamente");
		return maw;
    }
    
}