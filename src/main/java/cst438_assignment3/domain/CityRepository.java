package cst438_assignment3.domain;


import java.util.List;

import javax.persistence.Table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Table(name="city")
public interface CityRepository extends JpaRepository<City, Long> {
	List<City> findByName(String name);
}
