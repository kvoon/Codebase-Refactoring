package au.edu.sydney.cpa.erp.feaa.ordering.types;

public class NonPriorityOrder  implements PriorityType {

    public NonPriorityOrder(){}


    /**
     * Simple accessor method for the critical loading.
     * @return 0.0 because it is a non-priority order.
     */
    @Override
    public double getCriticalLoading() {
        return 0.0;
    }

    /**
     * Simple method to check if an order is flagged as priority or not.
     * @return return false, as it is a non priority order.
     */
    @Override
    public boolean isCritical() {
        return false;
    }

    /**
     * @return a copy of the priority type
     */
    @Override
    public PriorityType copy() {
        return new NonPriorityOrder();

    }



}
