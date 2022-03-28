package com.pickandroll.erp.dao;

import com.pickandroll.erp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Xavi
 */
public interface OrderDAO extends JpaRepository<Order, Long> {
    
   //Order findOrder(Long id);
}
