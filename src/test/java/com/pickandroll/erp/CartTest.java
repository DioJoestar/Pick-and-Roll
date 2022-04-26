package com.pickandroll.erp;

import com.pickandroll.erp.model.Cart;
import com.pickandroll.erp.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author santialves
 */
@SpringBootTest
public class CartTest {

    private Cart cart = new Cart();
    private List<Vehicle> vehicles = new ArrayList();

    @Test
    public void testCartModel() {
        //Crear vehicles i afegir-los a la llista de vehicles
        Vehicle v = new Vehicle();
        Vehicle v1 = new Vehicle();
        v.setPrice(20d);
        v1.setPrice(10d);
        vehicles.add(v);
        vehicles.add(v1);

        //Provar dies del cistell
        cart.setDays(8);
        int daysCart = cart.getDays();

        //Provar IVA 
        cart.setIVA(21);
        int ivaCart = cart.getIVA();

        //Provar suma preus unitats
        cart.setPriceU(vehicles);
        double priceU = cart.getPriceU();

        //Provar subPrice
        //totalPrice - IVA
        cart.setSubPrice();
        double subPrice = cart.getSubPrice();

        //Provar totalPrice
        //PriceU * days
        cart.setTotalPrice();
        double totalPrice = cart.getTotalPrice();

        //Asserts
        assertEquals(8, daysCart);
        assertEquals(21d, ivaCart);
        assertEquals(30d, priceU);

        //Provar mètodes personalitzats cistell
        assertEquals(240d, totalPrice);
        assertEquals("189,60", String.format("%,.2f", subPrice));

    }

}
