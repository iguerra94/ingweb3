package ar.edu.iua.ingweb3.business;

import java.util.List;

import ar.edu.iua.ingweb3.model.Rubro;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

public interface IRubroBusiness {
	public List<Rubro> getAll() throws BusinessException;
	public Rubro getOne(int id) throws BusinessException, NotFoundException;
	public Rubro add(Rubro rubro) throws BusinessException;
	public Rubro update(Rubro rubro) throws BusinessException, NotFoundException;
	public void delete(Rubro rubro) throws BusinessException, NotFoundException;
	public List<Rubro> search(String searchInput) throws BusinessException;
}
