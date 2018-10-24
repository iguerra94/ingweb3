package ar.edu.iua.ingweb3.business.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IProductoBusiness;
import ar.edu.iua.ingweb3.model.Producto;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;
import ar.edu.iua.ingweb3.model.persistence.ProductoRepository;

@Service
public class ProductoBusiness implements IProductoBusiness {

	@Autowired
	private ProductoRepository productoDAO;
	
	@Override
	public List<Producto> getAll() throws BusinessException {
		try {
			return productoDAO.findAll();
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Producto getOne(int id) throws BusinessException, NotFoundException {
		Optional<Producto> producto = null;
		
		try {
			producto = productoDAO.findById(id);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
		if (!producto.isPresent()) {
			throw new NotFoundException();
		}
	
		return producto.get();
	}

	@Override
	public Producto add(Producto producto) throws BusinessException {
		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public Producto update(Producto producto) throws BusinessException, NotFoundException {
		Optional<Producto> pr = null;
		
		pr = productoDAO.findById(producto.getId());

		if (!pr.isPresent()) {
			throw new NotFoundException();
		}
		
		try {
			return productoDAO.save(producto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void delete(Producto producto) throws BusinessException, NotFoundException {
		Optional<Producto> pr = null;
		
		pr = productoDAO.findById(producto.getId());

		if (!pr.isPresent()) {
			throw new NotFoundException();
		}

		try {
			productoDAO.delete(producto);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Producto> search(String searchInput) throws BusinessException {
		try {
			return productoDAO.findByDescripcionContaining(searchInput);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public List<Producto> searchByPrecios(double precioDesde, double precioHasta) throws BusinessException {
		try {
			return productoDAO.findByPrecioBetween(precioDesde, precioHasta);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

}