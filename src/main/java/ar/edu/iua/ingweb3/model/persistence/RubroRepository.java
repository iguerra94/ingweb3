package ar.edu.iua.ingweb3.model.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.ingweb3.model.Rubro;

@Repository
public interface RubroRepository extends JpaRepository<Rubro, Integer> {

}
