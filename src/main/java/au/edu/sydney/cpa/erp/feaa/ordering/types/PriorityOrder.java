package au.edu.sydney.cpa.erp.feaa.ordering.types;

public class PriorityOrder implements PriorityType{

    private final double criticalLoading;

    public PriorityOrder(double criticalLoading){

        this.criticalLoading = criticalLoading;

    }

    /**
     * Simple accessor for the critical loading.
     * @return returns the critical loading for the order.
     */
    @Override
    public double getCriticalLoading() {

        return this.criticalLoading;
    }

    /**
     * Simple method to check if an order is flagged as priority or not.
     * @return true since it is a priority order
     */
    @Override
    public boolean isCritical() {

        return true;
    }

    /**
     * @return a copy of the priority type
     */
    @Override
    public PriorityType copy() {
        return new PriorityOrder(this.criticalLoading);
    }

}
