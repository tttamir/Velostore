package com.velostore.repo;

import com.velostore.model.StatProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatProductRepo extends JpaRepository<StatProduct, Long> {

}
