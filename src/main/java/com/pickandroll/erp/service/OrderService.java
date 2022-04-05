package com.pickandroll.erp.service;

import com.pickandroll.erp.dao.OrderDAO;
import com.pickandroll.erp.model.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Xavi
 */
@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private OrderDAO orderDao;
    
    // Llista totes les ordres creades
    @Override
    @Transactional(readOnly = true)
    public List<Order> listOrders() {
        return (List<Order>) orderDao.findAll();
    }
    
    // Guarda una ordre
    @Override
    @Transactional
    public void addOrder(Order order) {
        this.orderDao.save(order);
    }
    
    // Elimina les dades d'una ordre
    @Override
    @Transactional
    public void deleteOrder(Order order) {
        this.orderDao.delete(order);
    }
    
    // Retorna una ordre en concret a partir d'una id
    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return this.orderDao.findById(id).orElse(null);
    }
}
