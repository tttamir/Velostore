package com.velostore.repo;

import com.velostore.model.Ordering;
import com.velostore.model.User;
import com.velostore.model.enums.OrderingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderingRepo extends JpaRepository<Ordering, Long> {
    public List<Ordering> findAllByUserAndOrderingStatus(User user, OrderingStatus orderingStatus);
}
