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

    @Override
    @Transactional(readOnly = true)
    public List<Order> listOrders() {
        return (List<Order>) orderDao.findAll();
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        this.orderDao.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Order order) {
        this.orderDao.delete(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order findOrder(Order order) {
        return this.orderDao.findById(order.getId()).orElse(null);
    }
}
