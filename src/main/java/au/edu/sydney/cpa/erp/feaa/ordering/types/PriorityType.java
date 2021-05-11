package au.edu.sydney.cpa.erp.feaa.ordering.types;

/**
 * Representation of the order priority type (priority order or not)
 */
public interface PriorityType{

    /**
     * Simple accessor for the critical loading.
     * @return returns the critical loading for the order.
     */
    double getCriticalLoading();

    /**
     * Simple method to check if an order is flagged as priority or not.
     * @return true if its is a priority order, otherwise false.
     */
    boolean isCritical();

    /**
     * @return a copy of the priority type
     */
    PriorityType copy();

}
