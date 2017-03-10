package fr.unice.polytech.isa.tcf;

import arquillian.AbstractTCFTest;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by huang on 10/03/17.
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class KitchenTest extends AbstractTCFTest {


    @EJB
    private OrderProcessing processor;
    @EJB private Tracker tracker;

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    private UserTransaction utx;

    private Customer customer;
    private Order order;

    @Before
    public void setUpContext() throws Exception {
        // Cookies to order
        Set<Item> items = new HashSet<>();
        items.add(new Item(Cookies.CHOCOLALALA, 3));
        items.add(new Item(Cookies.DARK_TEMPTATION, 2));

        customer = new Customer("pat", "1234567890");
        entityManager.persist(customer);

        order = new Order(customer, items);
        customer.add(order);
        entityManager.persist(order);
    }

    @After
    public void cleanUp() throws Exception {
        utx.begin();
        customer = entityManager.merge(customer);
        entityManager.remove(customer);
        utx.commit();
    }

    @Test
    public void processCommand() throws Exception {
        processor.process(order);
        assertEquals(OrderStatus.CREATED, tracker.status(""+ order.getId()));
    }




}
