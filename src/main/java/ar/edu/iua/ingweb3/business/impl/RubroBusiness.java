package ar.edu.iua.ingweb3.business.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IRubroBusiness;
import ar.edu.iua.ingweb3.model.Rubro;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistence.RubroRepository;

@Service
public class RubroBusiness implements IRubroBusiness {

	@Autowired
	private RubroRepository rubroDAO;
	
	public RubroBusiness() {}

	@Override
	public List<Rubro> getAll() throws BusinessException {
		try {
			return rubroDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Rubro getOne(int id) throws BusinessException, NotFoundException {
		Optional<Rubro> rubro = null;
		
		try {
			rubro = rubroDAO.findById(id);
			
			if (rubro == null) {
				throw new NotFoundException();
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		} 
		
		return rubro.get();
	}

	@Override
	public Rubro add(Rubro rubro) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rubro update(Rubro rubro) throws BusinessException, NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Rubro rubro) throws BusinessException, NotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Rubro> search(String searchInput) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
