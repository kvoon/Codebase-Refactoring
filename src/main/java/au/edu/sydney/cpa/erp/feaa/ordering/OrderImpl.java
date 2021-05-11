package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.feaa.ordering.desc.DescStrategyImpl;
import au.edu.sydney.cpa.erp.feaa.ordering.types.OrderType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.PriorityType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.ScheduleType;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of an order. Moved over the relevant existing code from
 * the original different order types, with modifications to fit the new design.
 */

@SuppressWarnings("Duplicates")
public class OrderImpl implements Order {

    private final int id;
    private int clientID;
    private LocalDateTime date;
    private OrderType orderType;
    private PriorityType priorityType;
    private ScheduleType scheduleType;
    private boolean finalised = false;
    private DescStrategyImpl descStrategy;

    private Map<Report, Integer> reports = new HashMap<>();

    /**
     * Default Constructor for concrete Implementation of Order.
     * @param id - the order id
     * @param clientID - the client id to be assigned.
     * @param date - the date order was created.
     * @param orderType - the type of order (audit, daytoday)
     * @param priorityType - the priority type (priority or non priority)
     * @param scheduleType - the schedule type (regularly scheduled or one off)
     */
    public OrderImpl(int id, int clientID, LocalDateTime date, OrderType orderType, PriorityType priorityType, ScheduleType scheduleType){

        this.id = id;
        this.clientID = clientID;
        this.date = date;
        this.orderType = orderType;
        this.priorityType = priorityType;
        this.scheduleType = scheduleType;

        this.descStrategy = new DescStrategyImpl();
    }


    /**
     * Simple accessor method.
     * @return the order id.
     */
    @Override
    public int getOrderID() {
        return this.id;
    }

    /**
     * Simple accessor method.
     * @return the total commission( for the particular order.
     */
    @Override
    public double getTotalCommission() {
        return orderType.getTotalCommission(this,priorityType,scheduleType);
    }

    /**
     * Simple accessor method.
     * @return the order date for the given order.
     */
    @Override
    public LocalDateTime getOrderDate() {
        return this.date;
    }

    /**
     * Sets up the report for an order.
     * @param report - report to be set up.
     * @param employeeCount - get the total amount of employees involved.
     */
    @Override
    public void setReport(Report report, int employeeCount) {

        if (finalised) throw new IllegalStateException("Order was already finalised.");

        for (Report contained: reports.keySet()) {
            if (contained.getCommission() == report.getCommission() &&
                    contained.getReportName().equals(report.getReportName()) &&
                    Arrays.equals(contained.getLegalData(), report.getLegalData()) &&
                    Arrays.equals(contained.getCashFlowData(), report.getCashFlowData()) &&
                    Arrays.equals(contained.getMergesData(), report.getMergesData()) &&
                    Arrays.equals(contained.getTallyingData(), report.getTallyingData()) &&
                    Arrays.equals(contained.getDeductionsData(), report.getDeductionsData())) {
                report = contained;
                break;
            }
        }

        reports.put(report, employeeCount);

    }

    /**
     * Simple accessor method
     * @return all the reports available.
     */
    @Override
    public Set<Report> getAllReports() {

         return this.reports.keySet();
    }

    /**
     * Simple accessor method
     * @param report - report to be selected.
     * @return the employee count listed in a given report.
     */
    @Override
    public int getReportEmployeeCount(Report report) {

        for (Report contained: reports.keySet()) {

            if (contained.getCommission() == report.getCommission() &&
                    contained.getReportName().equals(report.getReportName()) &&
                    Arrays.equals(contained.getLegalData(), report.getLegalData()) &&
                    Arrays.equals(contained.getCashFlowData(), report.getCashFlowData()) &&
                    Arrays.equals(contained.getMergesData(), report.getMergesData()) &&
                    Arrays.equals(contained.getTallyingData(), report.getTallyingData()) &&
                    Arrays.equals(contained.getDeductionsData(), report.getDeductionsData())) {

                report = contained;
                break;
            }
        }
        Integer result = reports.get(report);

        return null == result ? 0 : result;
    }

    /**
     * Generates the invoice data for a given order.
     * @return invoice data for a given order.
     */
    @Override
    public String generateInvoiceData() {

        return descStrategy.generateDesc("invoice",this,orderType,priorityType, scheduleType, finalised);

    }

    /**
     * Simple accessor method.
     * @return the client id.
     */
    @Override
    public int getClient() {
        return this.clientID;
    }

    /**
     * sets the finalised value to true, to indicate that the order has been finalised.
     */
    @Override
    public void finalise() {

        this.finalised = true;
    }

    /**
     * Create a copy of the order object.
     * @return a copy of the order object.
     */
    @Override
    public Order copy() {

        Order copy = new OrderImpl(id, clientID, date, orderType.copy(), priorityType.copy(),scheduleType.copy());

        for (Report report : reports.keySet()) {

            copy.setReport(report, reports.get(report));
        }
        return copy;
    }

    /**
     * Generate a short description of the order.
     * @return a short description of the order.
     */
    @Override
    public String shortDesc() {

        return descStrategy.generateDesc("short", this, orderType.copy(), priorityType.copy(),scheduleType.copy(), finalised);

    }

    /**
     * Generate a long description of the order.
     * @return a long description of the order.
     */
    @Override
    public String longDesc() {

        return descStrategy.generateDesc("long", this, orderType.copy(), priorityType.copy(),scheduleType.copy(), finalised);

    }
}
