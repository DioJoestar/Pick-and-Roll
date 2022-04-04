/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.pickandroll.erp.service;

import com.pickandroll.erp.model.Order;
import java.util.List;

/**
 *
 * @author Xavi
 */
public interface OrderServiceInterface {

    public List<Order> listOrders();

    public void addOrder(Order order);

    public void deleteOrder(Order order);

    public Order findById(Long id);
}
