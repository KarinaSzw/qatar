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
@RequestMapping("selecciones")
public class SeleccionControlador implements WebMvcConfigurer {

	@Autowired
    SeleccionServicio seleccionServicio;
    
    @Autowired
    PaisServicio paisServicio;

	@GetMapping
    private ModelAndView index()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de selecciones");
        maw.addObject("vista", "selecciones/index");
        maw.addObject("selecciones", seleccionServicio.getAll());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView one(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del seleccion #" + id);
        maw.addObject("vista", "selecciones/ver");
        maw.addObject("seleccion", seleccionServicio.getById(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Seleccion seleccion)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear seleccion");
        maw.addObject("vista", "selecciones/crear");
        maw.addObject("paises", paisServicio.getAll());
        maw.addObject("selecciones", seleccionServicio.getAll());
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo, @Valid Seleccion seleccion, BindingResult br, RedirectAttributes ra)
    {
        System.out.println(archivo.isEmpty());
        if ( archivo.isEmpty() )
			br.reject("archivo", "Por favor, cargue una imagen"); 

		if ( br.hasErrors() ) {
			return this.crear(seleccion);
		}

		seleccionServicio.save(seleccion);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = seleccion.getId() + extension;
        String path = Paths.get("src/main/resources/static/images/selecciones", foto).toAbsolutePath().toString();
        ModelAndView maw = this.index();

        try {
            archivo.transferTo( new File(path) );
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        seleccion.setFoto(foto);
        seleccionServicio.save(seleccion);
        maw.addObject("exito", "Selecci??n guardada exitosamente");
		return maw;
	}

	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Seleccion seleccion)
    {
        return this.editar(id, seleccion, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Seleccion seleccion, boolean estaPersistido)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar seleccion");
        maw.addObject("vista", "selecciones/editar");
        maw.addObject("paises", paisServicio.getAll());

        if (estaPersistido)
            maw.addObject("seleccion", seleccionServicio.getById(id));
        else
            seleccion.setFoto( seleccionServicio.getById(id).getFoto() );

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView update(@PathVariable("id") Long id,
    @RequestParam(value = "archivo", required = false) MultipartFile archivo,
    @Valid Seleccion seleccion, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
			return this.editar(id, seleccion, false);
		}

        Seleccion registro = seleccionServicio.getById(id);
        registro.setPais( seleccion.getPais() );
        registro.setApodo( seleccion.getApodo() );
        registro.setMundialesGanados( seleccion.getMundialesGanados() );
        registro.setParticipacionesPrevias( seleccion.getParticipacionesPrevias() );
        ModelAndView maw = this.index();

        if ( ! archivo.isEmpty() ) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = seleccion.getId() + extension;
            String path = Paths.get("src/main/resources/static/images/selecciones", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo( new File(path) );
            } catch (Exception e) {
                maw.addObject("error", "No se pudo guardar la imagen");
                return maw;
            }

            registro.setFoto(foto);
        }

        seleccionServicio.save(registro);
        maw.addObject("exito", "Selecci??n editada exitosamente");
		return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView delete(@PathVariable("id") Long id)
    {
        seleccionServicio.delete(id);
        ModelAndView maw = this.index();
        maw.addObject("exito", "Selecci??n borrada exitosamente");
		return maw;
    }
    
}