package au.edu.sydney.cpa.erp.feaa.ordering.types;

import au.edu.sydney.cpa.erp.ordering.Order;

public class RegScheduledOrder implements ScheduleType {

    private int numQuarters;

    /**
     * Basic constructor
     * @param numQuarters - the number of quarters for an order.
     */
    public RegScheduledOrder(int numQuarters){

        this.numQuarters = numQuarters;
    }

    /**
     * The total recurring cost from that order.
     * @param order - the order to be selected.
     * @return the recurring costs from the selected order.
     */
    @Override
    public double getRecurringCost(Order order) {

        return order.getTotalCommission()/numQuarters;

    }

    /**
     * Simple accessor method.
     * @return the number of quarters in an order.
     */
    @Override
    public int getNumberOfQuarters() {

        return this.numQuarters;
    }

    /**
     * Method to check if an order is a scheduled order or not.
     * @return always true, as this is a regularly scheduled order.
     */
    @Override
    public boolean isScheduled() {

        return true;
    }

    /**
     * Creates a copy of the RegScheduledOrder
     * @return a copy of the RegScheduledOrder
     */
    @Override
    public ScheduleType copy() {

        return new RegScheduledOrder(this.numQuarters);

    }
}
