package ar.edu.iua.ingweb3.business;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.iua.ingweb3.model.Archivo;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

public interface IArchivoBusiness {
	// Metodos FS
	
	public List<Archivo> getAll() throws BusinessException;
	public List<Archivo> searchByName(String input) throws BusinessException;
	public List<Archivo> searchByMime(String mime) throws BusinessException;
	public List<Archivo> searchByTamanios(long tamanioDesde, long tamanioHasta) throws BusinessException;
	public Resource loadFromFS(String nombreArchivo) throws BusinessException, NotFoundException;
	public Archivo getArchivoFromFS(Resource resource) throws BusinessException, NotFoundException;
	public Archivo saveToFS(MultipartFile mf) throws BusinessException;
	public void delete(String filename) throws BusinessException, NotFoundException;
	
	// Metodos BD
	
	public List<Archivo> getAllBD() throws BusinessException;
	public List<Archivo> searchByNameBD(String input) throws BusinessException;
	public List<Archivo> searchByMimeBD(String mime) throws BusinessException;
	public List<Archivo> searchByTamaniosBD(long tamanioDesde, long tamanioHasta) throws BusinessException;
	public Archivo getOneBD(int id) throws BusinessException, NotFoundException;
	public Archivo getArchivoFromFile(MultipartFile file);
	public Archivo addBD(Archivo archivo) throws BusinessException;
	public void deleteBD(Archivo archivo) throws BusinessException, NotFoundException;
}