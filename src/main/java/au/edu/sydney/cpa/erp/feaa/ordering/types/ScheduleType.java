package au.edu.sydney.cpa.erp.feaa.ordering.types;

import au.edu.sydney.cpa.erp.ordering.Order;

/**
 * Representation of the the schedule type, i.e. one off or regularly scheduled
 */
public interface ScheduleType{

    double getRecurringCost(Order order);

    int getNumberOfQuarters();

    boolean isScheduled();

    ScheduleType copy();

}
