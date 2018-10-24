package ar.edu.iua.ingweb3.business.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IArchivoBusiness;
import ar.edu.iua.ingweb3.business.impl.util.fs.ArchivoFSService;
import ar.edu.iua.ingweb3.model.Archivo;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistence.ArchivoRepository;

@Service
public class ArchivoBusiness implements IArchivoBusiness {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ArchivoFSService archivoFSService;
	
	@Autowired
	private ArchivoRepository archivoDAO;

	// Metodos FS

	@Override
	public List<Archivo> getAll() throws BusinessException {
		return archivoFSService.cargarArchivos();
	}

	@Override
	public List<Archivo> searchByName(String input) throws BusinessException {
		return archivoFSService.cargarArchivosPorNombreConteniendo(input);
	}
	
	@Override
	public List<Archivo> searchByMime(String mime) throws BusinessException {
		return archivoFSService.cargarArchivosPorMimeType(mime);
	}

	@Override
	public List<Archivo> searchByTamanios(long tamanioDesde, long tamanioHasta) throws BusinessException {
		return archivoFSService.cargarArchivosPorRangoTamanios(tamanioDesde, tamanioHasta);
	}
	
	@Override
	public Resource loadFromFS(String nombreArchivo) throws BusinessException, NotFoundException {
		return archivoFSService.cargarArchivo(nombreArchivo);
	}
	
	@Override
	public Archivo getArchivoFromFS(Resource resource) throws BusinessException, NotFoundException {		
		return archivoFSService.getArchivo(resource);
	}

	@Override
	public Archivo saveToFS(MultipartFile mf) throws BusinessException {
		Archivo archivo = new Archivo();
		
		String name = archivoFSService.almacenarArchivo(mf);
		archivo.setNombre(name);
		archivo.setMime(mf.getContentType());
		archivo.setLength(mf.getSize());
		
		return archivo;
	}

	@Override
	public void delete(String filename) throws BusinessException, NotFoundException {
		archivoFSService.eliminarArchivo(filename);
	}
	
	// Metodos BD

	@Override
	public List<Archivo> getAllBD() throws BusinessException {
		try {
			return archivoDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Archivo> searchByNameBD(String input) throws BusinessException {
		try {
			return archivoDAO.findByNombreContaining(input);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Archivo> searchByMimeBD(String mime) throws BusinessException {
		try {
			return archivoDAO.findByMime(mime);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Archivo> searchByTamaniosBD(long tamanioDesde, long tamanioHasta) throws BusinessException {
		try {
			return archivoDAO.findByLengthBetween(tamanioDesde, tamanioHasta);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Archivo getOneBD(int id) throws BusinessException, NotFoundException {
		Optional<Archivo> archivo = null;
		
		try {
			archivo = archivoDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
		if (!archivo.isPresent()) {
			throw new NotFoundException();
		}
	
		return archivo.get();
	}

	@Override
	public Archivo getArchivoFromFile(MultipartFile file) {
		Archivo archivo = null;
		try {
			archivo = new Archivo();
			archivo.setNombre(file.getOriginalFilename());
			archivo.setMime(file.getContentType());
			archivo.setLength(file.getSize());
			archivo.setContenido(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return archivo;
	}
	
	@Override
	public Archivo addBD(Archivo archivo) throws BusinessException {
		try {
			return archivoDAO.save(archivo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void deleteBD(Archivo archivo) throws BusinessException, NotFoundException {
//		Optional<Archivo> arch = null;
//		
//		arch = archivoDAO.findById(archivo.getId());
//
//		if (!arch .isPresent()) {
//			throw new NotFoundException();
//		}
//
		try {
			archivoDAO.delete(archivo);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
}