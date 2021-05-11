package au.edu.sydney.cpa.erp.feaa.ordering.types;

import au.edu.sydney.cpa.erp.ordering.Order;

/**
 * Representation of the type of order
 */
public interface OrderType{

    /**
     * Returns the total commission of the given order.
     * @param order - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a Regularly scheduled or one off order.
     * @return the total commission for that order.
     */
    double getTotalCommission(Order order, PriorityType priorityType, ScheduleType scheduleType);

    /**
     * A regularly scheduled order.
     * @param order - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a Regularly scheduled or one off order.
     * @param finalised - whether the order has been finalised or not.
     * @return a long description of a regularly scheduled non priority order.
     */
    String RegScheduledNonPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType,  boolean finalised);

    /**
     * A one-Off, non priority order.
     * @param order - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a Regularly scheduled or one off order.
     * @param finalised - whether the order has been finalised or not.
     * @return a long description of a one-off, non priority order.
     */
    String OneOffNonPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType, boolean finalised);

    /**
     * A regularly scheduled priority order
     * @param order - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a regularly scheduled or one off order.
     * @param finalised - whether the order has been finalised or not.
     * @return  a long description of a regularly scheduled, priority order.
     */
    String RegScheduledPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType, boolean finalised);

    /**
     * A one-Off,  priority order.
     * @param order - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a regularly scheduled or one off order.
     * @param finalised - whether the order has been finalised or not.
     * @return  a long description of a one-off scheduled, priority order.
     */
    String OneOffPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType, boolean finalised);

    /**
     * Returns a copy of the object.
     * @return copy of the order type.
     */
    OrderType copy();
}

