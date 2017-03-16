package fr.unice.polytech.isa.tcf;

import fr.unice.polytech.isa.tcf.entities.OrderStatus;
import fr.unice.polytech.isa.tcf.exceptions.UnknownOrderId;

import javax.ejb.Local;

/**
 * Created by huang on 10/03/17.
 */
@Local
public interface Tracker {
    OrderStatus status(String orderId) throws UnknownOrderId;
}
