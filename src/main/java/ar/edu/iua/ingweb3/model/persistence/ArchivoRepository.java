package ar.edu.iua.ingweb3.model.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.ingweb3.model.Archivo;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Integer> {
	public List<Archivo> findByNombreContaining(String input);
	public List<Archivo> findByMime(String mime);
	public List<Archivo> findByLengthBetween(long tamanioDesde, long tamanioHasta);
}