package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.feaa.ordering.types.RegScheduledOrder;
import au.edu.sydney.cpa.erp.feaa.ordering.types.*;
import au.edu.sydney.cpa.erp.ordering.Order;

import java.time.LocalDateTime;

public class OrderFactory{


    /**
     * Creates and returns an order specified by the orderType in the parameters.
     * @param id - the order id
     * @param clientID - the client id.
     * @param date - the date of the order.
     * @param orderType - the type of order (1 = daytoday, 2 = audit)
     * @param isCritical - whether the order is a priority order or not.
     * @param isScheduled - whether the order is a regularly scheduled order or a one off order.
     * @param criticalLoading - the critical loading for an order.
     * @param maxCountedEmployees - the max counted employees in the order.
     * @param numQuarters - the number of quarters for an order.
     * @return a concrete implementation of an order based on the order type parameter selected.
     */
    public static Order makeOrder(int id, int clientID, LocalDateTime date,
                           boolean isCritical, boolean isScheduled,
                           int orderType, double criticalLoading,
                           int maxCountedEmployees, int numQuarters) {

        Order order = null;

        if (orderType == 1) {

            order = new OrderImpl(id, clientID, date, new DayToDayOrder(maxCountedEmployees), isCritical ?
                    new PriorityOrder(criticalLoading) : new NonPriorityOrder(), isScheduled ?
                    new RegScheduledOrder(numQuarters) : new OneOffOrder());

        } else if (orderType == 2) {

            order = new OrderImpl(id, clientID, date, new AuditOrder(), isCritical ?
                    new PriorityOrder(criticalLoading) : new NonPriorityOrder(), isScheduled ?
                    new RegScheduledOrder(numQuarters) : new OneOffOrder());

        }

        return order;
    }
}
