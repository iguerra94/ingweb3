package ar.edu.iua.ingweb3.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ar.edu.iua.ingweb3.business.BusinessException;
import ar.edu.iua.ingweb3.business.IRubroBusiness;
import ar.edu.iua.ingweb3.model.Rubro;
import ar.edu.iua.ingweb3.model.exception.NotFoundException;

@RestController
@RequestMapping("/rubros")
public class RubrosRESTController {

	@Autowired
	private IRubroBusiness rubroBusiness;
	
	public RubrosRESTController() {}
	
	@RequestMapping(value= {"","/"},method=RequestMethod.GET,produces="application/json")
	public ResponseEntity<List<Rubro>> lista(){
		try {
			return new ResponseEntity<List<Rubro>>(rubroBusiness.getAll(), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<List<Rubro>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value= {"/{id}"},method=RequestMethod.GET,produces="application/json")
	public ResponseEntity<Rubro> uno(@PathVariable("id") int id){
		try {
			return new ResponseEntity<Rubro>(rubroBusiness.getOne(id), HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Rubro>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			return new ResponseEntity<Rubro>(HttpStatus.NOT_FOUND);
		}
	}
		
	@RequestMapping(value= {"","/"},method=RequestMethod.POST,produces="application/json")
	public ResponseEntity<Rubro> add(@RequestBody Rubro rubro, UriComponentsBuilder uriComponentsBuilder){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(
				uriComponentsBuilder
					.path("/rubros/{id}")
					.buildAndExpand(rubro.getId())
					.toUri()
			);
			
			return new ResponseEntity<Rubro>(rubroBusiness.add(rubro), headers, HttpStatus.CREATED);
		} catch (BusinessException e) {
			return new ResponseEntity<Rubro>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}