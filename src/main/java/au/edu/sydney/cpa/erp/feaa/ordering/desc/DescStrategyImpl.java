package au.edu.sydney.cpa.erp.feaa.ordering.desc;

import au.edu.sydney.cpa.erp.feaa.ordering.types.OrderType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.PriorityType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.ScheduleType;
import au.edu.sydney.cpa.erp.ordering.Order;

/**
 * Implementation of the Description Strategy
 */
public class DescStrategyImpl implements  DescStrategy{

    private ShortDescStrategy shortDesc;

    private LongDescStrategy longDesc;

    private InvoiceData invoiceData;

    public DescStrategyImpl(){

    }

    /**
     * Algorithm to generate invoice data, long and short descriptions.
     * @param descType - whether its a long, short description or invoice data.
     * @param order - the order you are looking at.
     * @param orderType - the type of order (audit, daytoday)
     * @param priorityType - the priority type (priority or non priority)
     * @param scheduleType - the schedule type (regularly scheduled or one off)
     * @param isFinalised - whether the order has been finalised or not.
     * @return the order's invoice data, or a long or short description of the order based on the descType.
     */

    @Override
    public String generateDesc(String descType, Order order, OrderType orderType, PriorityType priorityType, ScheduleType scheduleType, boolean isFinalised) {

        String descStrategy = null;

        switch (descType) {
            case "long":

                longDesc = new LongDescImpl();

                descStrategy = longDesc.generateLongDesc(order, orderType, priorityType, scheduleType, isFinalised);

                return descStrategy;

            case "short":

                shortDesc = new ShortDescImpl();

                descStrategy = shortDesc.generateShortDesc(order, orderType, priorityType, scheduleType, isFinalised);

                return descStrategy;

            case "invoice":

                invoiceData = new InvoiceDataImpl();

                descStrategy = invoiceData.generateInvoiceData(order, orderType, priorityType, scheduleType, isFinalised);

                return descStrategy;
        }
        return descStrategy;

    }

}
