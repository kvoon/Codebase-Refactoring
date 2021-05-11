package au.edu.sydney.cpa.erp.feaa.ordering.desc;

import au.edu.sydney.cpa.erp.feaa.ordering.types.OrderType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.PriorityType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.ScheduleType;
import au.edu.sydney.cpa.erp.ordering.Order;

public class ShortDescImpl implements ShortDescStrategy{

    /**
     * Algorithm to generate a short description of the order.
     *
     * @param order - the order you are looking at.
     * @param orderType - the type of order (audit, daytoday)
     * @param priorityType - the priority type (priority or non priority)
     * @param scheduleType - the schedule type (regularly scheduled or one off)
     * @param isFinalised - whether the order has been finalised or not.
     * @return a short description of the order.
     */
    @Override
    public String generateShortDesc(Order order, OrderType orderType, PriorityType priorityType, ScheduleType scheduleType, boolean isFinalised) {

        if(scheduleType.isScheduled()){
            return String.format("ID:%s $%,.2f per quarter, $%,.2f total", order.getOrderID(), scheduleType.getRecurringCost(order), order.getTotalCommission());
        }else{
            return String.format("ID:%s $%,.2f", order.getOrderID(), order.getTotalCommission());
        }
    }
}
