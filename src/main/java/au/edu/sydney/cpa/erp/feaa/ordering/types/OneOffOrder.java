package au.edu.sydney.cpa.erp.feaa.ordering.types;

import au.edu.sydney.cpa.erp.ordering.Order;

/**
 * Representation of a One-Off Order.
 */
public class OneOffOrder implements ScheduleType{

    private final int numQuarters = 1;

    /**
     * Default Constructor for OneOffOrders
     */
    public OneOffOrder(){}

    /**
     * Simple accessor method.
     * @return the number of quarters in the order.
     */
    @Override
    public int getNumberOfQuarters() {
        return numQuarters;
    }

    /**
     * Simple method to verify that an order is scheduled or not.
     * @return always false, as it is not a regularly Scheduled order.
     */
    @Override
    public boolean isScheduled() {
        return false;
    }

    /**
     * Simple accessor method.
     * @param order - the order you are looking at.
     * @return The recurring costs for the order.
     */
    @Override
    public double getRecurringCost(Order order) {
        return order.getTotalCommission()/numQuarters;
    }

    /**
     * Creates a copy of a one off order.
     * @return a copy of a one off order.
     */
    @Override
    public ScheduleType copy() {
        return new OneOffOrder();
    }
}
