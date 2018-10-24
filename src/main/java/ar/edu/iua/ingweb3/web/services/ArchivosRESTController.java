package ar.edu.iua.ingweb3.web.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IArchivoBusiness;
import ar.edu.iua.ingweb3.model.Archivo;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@RestController
@RequestMapping(Constantes.URL_ARCHIVOS)
public class ArchivosRESTController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IArchivoBusiness archivoBusiness;
	
	// Metodos FS

	@GetMapping(value = {"/fs", "/fs/"})
	public ResponseEntity<List<Archivo>> getAll(
			@RequestParam(required=false, value="q", defaultValue="*") String q,
			@RequestParam(required=false, value="mime", defaultValue="*") String mime,
			@RequestParam(required=false, value="tamanio_desde", defaultValue="-1") long tamanioDesde,
			@RequestParam(required=false, value="tamanio_hasta", defaultValue="-1") long tamanioHasta
			) {
		
		try {
		    if (!q.equals("*") && q.trim().length() > 0) {
		    	return new ResponseEntity<List<Archivo>>(archivoBusiness.searchByName(q), HttpStatus.OK);
		    } else if (tamanioDesde != -1 && tamanioHasta != -1 && tamanioDesde <= tamanioHasta) {
				return new ResponseEntity<List<Archivo>>(archivoBusiness.searchByTamanios(tamanioDesde, tamanioHasta), HttpStatus.OK);
			} else if (mime.equals("*") || mime.trim().length() == 0) {
				return new ResponseEntity<List<Archivo>>(archivoBusiness.getAll(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Archivo>>(archivoBusiness.searchByMime(mime), HttpStatus.OK);
			}
		} catch (BusinessException e) {
			return new ResponseEntity<List<Archivo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}

	@GetMapping("/fs/{fileName:.+}")
	public ResponseEntity<Resource> downloadFS(@PathVariable("fileName") String fileName, HttpServletRequest request) {
		Resource resource;

		try {
			resource = archivoBusiness.loadFromFS(fileName);
		} catch (BusinessException e) {
			return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		String mime = null;
		
		try {
			mime = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			
		}
		
		if(mime == null) {
			mime = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(mime))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PostMapping(value = {"/fs", "/fs/"})
	public ResponseEntity<Archivo> uploadFS(@RequestParam("file") MultipartFile file) {
		Archivo r;

		try {
			r = archivoBusiness.saveToFS(file);
			r.setDownloadUri(Constantes.URL_ARCHIVOS + "/fs/" + r.getNombre());

			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("location", r.getDownloadUri());
			return new ResponseEntity<Archivo>(r, responseHeaders, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
		
	@PostMapping(value = {"/fs/multi", "/fs/multi/"})
	public ResponseEntity<List<Object>> uploadFSMulti(@RequestParam("files") MultipartFile[] files) {
		List<Object> r = Arrays.asList(files).stream()
				.map(file -> {
					try {
						Archivo archivo = archivoBusiness.saveToFS(file);
						archivo.setDownloadUri(Constantes.URL_ARCHIVOS + "/fs/" + archivo.getNombre());
						return archivo;
					} catch (BusinessException e) {
						return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
					} 
			}).collect(Collectors.toList());
		
		return new ResponseEntity<List<Object>>(r, HttpStatus.CREATED);
	}

	@DeleteMapping("/fs/{fileName:.+}")
	public ResponseEntity<Archivo> deleteFS(@PathVariable("fileName") String fileName) {

		try {
			Resource resource = archivoBusiness.loadFromFS(fileName);
			
			Archivo r = archivoBusiness.getArchivoFromFS(resource);
			
			archivoBusiness.delete(fileName);
			
			return new ResponseEntity<Archivo>(r, HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Archivo>(HttpStatus.NOT_FOUND);
		}
		
	}

	// Metodos BD

	@GetMapping(value= {"","/"})
	public ResponseEntity<List<Archivo>> listaBD(
			@RequestParam(required=false, value="q", defaultValue="*") String q,
			@RequestParam(required=false, value="mime", defaultValue="*") String mime,
			@RequestParam(required=false, value="tamanio_desde", defaultValue="-1") long tamanioDesde,
			@RequestParam(required=false, value="tamanio_hasta", defaultValue="-1") long tamanioHasta
			) {

		try {
		    if (!q.equals("*") && q.trim().length() > 0) {
		    	return new ResponseEntity<List<Archivo>>(archivoBusiness.searchByNameBD(q), HttpStatus.OK);
		    } else if (tamanioDesde != -1 && tamanioHasta != -1 && tamanioDesde <= tamanioHasta) {
				return new ResponseEntity<List<Archivo>>(archivoBusiness.searchByTamaniosBD(tamanioDesde, tamanioHasta), HttpStatus.OK);
			} else if (mime.equals("*") || mime.trim().length() == 0) {
				return new ResponseEntity<List<Archivo>>(archivoBusiness.getAllBD(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Archivo>>(archivoBusiness.searchByMimeBD(mime), HttpStatus.OK);
			}
		} catch (BusinessException e) {
			return new ResponseEntity<List<Archivo>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Archivo> uno(@PathVariable("id") int id){
		try {
			return new ResponseEntity<Archivo>(archivoBusiness.getOneBD(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Archivo>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value = {"", "/"})
	public ResponseEntity<Archivo> addBD(@RequestParam("file") MultipartFile file, UriComponentsBuilder uriComponentsBuilder) {
		try {
			Archivo a = archivoBusiness.getArchivoFromFile(file);
			Archivo archivo = archivoBusiness.addBD(a);

			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
				uriComponentsBuilder
					.path("/archivos/{id}")
					.buildAndExpand(archivo.getId())
					.toUri()
			);
			
			return new ResponseEntity<Archivo>(archivo, headers, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
		
	@PostMapping(value = {"/multi", "/multi/"})
	public ResponseEntity<List<Object>> addMultiBD(@RequestParam("files") MultipartFile[] files) {
		List<Object> archivos = Arrays.asList(files).stream()
				.map(file -> {
					try {
						Archivo a = archivoBusiness.getArchivoFromFile(file);
						Archivo archivo = archivoBusiness.addBD(a);
						return archivo;
					} catch (BusinessException e) {
						return new ResponseEntity<List<Archivo>>(HttpStatus.INTERNAL_SERVER_ERROR);
					} 
			}).collect(Collectors.toList());
		
		return new ResponseEntity<List<Object>>(archivos, HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Archivo> deleteBD(@PathVariable("id") int id) {

		try {
			Archivo archivo = archivoBusiness.getOneBD(id);
			archivoBusiness.deleteBD(archivo);

			return new ResponseEntity<Archivo>(archivo, HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Archivo>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Archivo>(HttpStatus.NOT_FOUND);
		}
		
	}

}