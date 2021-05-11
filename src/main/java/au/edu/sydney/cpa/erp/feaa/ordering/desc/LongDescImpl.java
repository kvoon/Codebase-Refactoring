package au.edu.sydney.cpa.erp.feaa.ordering.desc;

import au.edu.sydney.cpa.erp.feaa.ordering.types.OrderType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.PriorityType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.ScheduleType;
import au.edu.sydney.cpa.erp.ordering.Order;

public class LongDescImpl implements LongDescStrategy{

    /**
     * Algorithm to generate a long description of the order.
     * Implementation mostly ported over from the existing codebase, with minor changes to fit the new design.
     * @param order - the order you are looking at.
     * @param orderType - the type of order (audit, daytoday)
     * @param priorityType - the priority type (priority or non priority)
     * @param scheduleType - the schedule type (regularly scheduled or one off)
     * @param isFinalised - whether the order has been finalised or not.
     * @return The order's invoice data.
     */
    @Override
    public String generateLongDesc(Order order, OrderType orderType, PriorityType priorityType, ScheduleType scheduleType, boolean isFinalised) {

        if(scheduleType.isScheduled()){

            if(priorityType.isCritical()){

                return orderType.RegScheduledPriorityLongDesc(order,  priorityType,  scheduleType,  isFinalised);

            }else{

                return orderType.RegScheduledNonPriorityLongDesc(order, priorityType, scheduleType, isFinalised);

            }

        }else{

            if(priorityType.isCritical()){

                return orderType.OneOffPriorityLongDesc(order, priorityType, scheduleType, isFinalised);

            }else{

                return orderType.OneOffNonPriorityLongDesc(order, priorityType, scheduleType, isFinalised);
            }
        }
    }
}
