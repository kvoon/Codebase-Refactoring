package au.edu.sydney.cpa.erp.feaa.ordering.types;

import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Representation of a day to day order, moved over relevant existing code with slight modifications to
 * fit the attempted Bridge design pattern.
 */

@SuppressWarnings("Duplicates")
public class DayToDayOrder implements OrderType{

    private int maxCountedEmployees;

    public DayToDayOrder(int maxCountedEmployees){

        this.maxCountedEmployees = maxCountedEmployees;
    }

    /**
     * Returns the total commission of the given order.
     *
     * @param order        - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a Regularly scheduled or one off order.
     * @return the total commission for that order.
     */
    @Override
    public double getTotalCommission(Order order, PriorityType priorityType, ScheduleType scheduleType) {

        double cost = 0.0;

        for (Report report : order.getAllReports()) {

            cost += report.getCommission() * Math.min(maxCountedEmployees, order.getReportEmployeeCount(report));

        }

        cost += cost * priorityType.getCriticalLoading();

        cost *= scheduleType.getNumberOfQuarters();

        return cost;
    }

    /**
     * A regularly scheduled order.
     *
     * @param order        - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a Regularly scheduled or one off order.
     * @param finalised    - whether the order has been finalised or not.
     * @return a long description of a regularly scheduled non priority order.
     */
    @Override
    public String RegScheduledNonPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType, boolean finalised) {

        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(order.getAllReports());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * Math.min(maxCountedEmployees, order.getReportEmployeeCount(report));

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                    report.getReportName(),
                    order.getReportEmployeeCount(report),
                    report.getCommission(),
                    subtotal));

            if (order.getReportEmployeeCount(report) > maxCountedEmployees) {
                reportSB.append(" *CAPPED*\n");
            } else {
                reportSB.append("\n");
            }
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of quarters: %d\n" +
                        "Reports:\n" +
                        "%s" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                order.getOrderID(),
                order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                scheduleType.getNumberOfQuarters(),
                reportSB.toString(),
                scheduleType.getRecurringCost(order),
                this.getTotalCommission(order, priorityType, scheduleType)

        );
    }

    /**
     * A one-Off, non priority order.
     *
     * @param order        - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a Regularly scheduled or one off order.
     * @param finalised    - whether the order has been finalised or not.
     * @return a long description of a one-off, non priority order.
     */
    @Override
    public String OneOffNonPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType, boolean finalised) {

        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(order.getAllReports());

        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * Math.min(maxCountedEmployees, order.getReportEmployeeCount(report));

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                    report.getReportName(),
                    order.getReportEmployeeCount(report),
                    report.getCommission(),
                    subtotal));

            if (order.getReportEmployeeCount(report) > maxCountedEmployees) {
                reportSB.append(" *CAPPED*\n");
            } else {
                reportSB.append("\n");
            }
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Reports:\n" +
                        "%s" +
                        "Total cost: $%,.2f\n",
                order.getOrderID(),
                order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                reportSB.toString(),
                getTotalCommission(order,priorityType,scheduleType)
        );
    }

    /**
     * A regularly scheduled priority order
     *
     * @param order        - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a regularly scheduled or one off order.
     * @param finalised    - whether the order has been finalised or not.
     * @return a long description of a regularly scheduled, priority order.
     */
    @Override
    public String RegScheduledPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType, boolean finalised) {

        double totalBaseCost = 0.0;

        double loadedCostPerQuarter = getTotalCommission(order, priorityType, scheduleType)/scheduleType.getNumberOfQuarters();

        double totalLoadedCost = getTotalCommission(order, priorityType, scheduleType);

        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(order.getAllReports());

        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {

            double subtotal = report.getCommission() * Math.min(maxCountedEmployees, order.getReportEmployeeCount(report));

            totalBaseCost += subtotal;

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                    report.getReportName(),
                    order.getReportEmployeeCount(report),
                    report.getCommission(),
                    subtotal));

            if (order.getReportEmployeeCount(report) > maxCountedEmployees) {

                reportSB.append(" *CAPPED*\n");

            } else {

                reportSB.append("\n");

            }
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of quarters: %d\n" +
                        "Reports:\n" +
                        "%s" +
                        "Critical Loading: $%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",

                order.getOrderID(),
                order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                scheduleType.getNumberOfQuarters(),
                reportSB.toString(),
                totalLoadedCost - (totalBaseCost * scheduleType.getNumberOfQuarters()),
                loadedCostPerQuarter,
                totalLoadedCost

        );
    }

    /**
     * A one-Off,  priority order.
     * @param order        - the order you wish to retrieve.
     * @param priorityType - whether it is a priority or non priority order.
     * @param scheduleType - whether it is a regularly scheduled or one off order.
     * @param finalised    - whether the order has been finalised or not.
     * @return a long description of a one-off scheduled, priority order.
     */
    @Override
    public String OneOffPriorityLongDesc(Order order, PriorityType priorityType, ScheduleType scheduleType, boolean finalised) {

        double baseCommission = 0.0;

        double loadedCommission = getTotalCommission(order,priorityType,scheduleType);

        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(order.getAllReports());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * Math.min(maxCountedEmployees, order.getReportEmployeeCount(report));
            baseCommission += subtotal;

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f",
                    report.getReportName(),
                    order.getReportEmployeeCount(report),
                    report.getCommission(),
                    subtotal));

            if (order.getReportEmployeeCount(report) > maxCountedEmployees) {
                reportSB.append(" *CAPPED*\n");
            } else {
                reportSB.append("\n");
            }
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Reports:\n" +
                        "%s" +
                        "Critical Loading: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                order.getOrderID(),
                order.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                reportSB.toString(),
                loadedCommission - baseCommission,
                loadedCommission
        );
    }

    /**
     * Returns a copy of the object.
     *
     * @return copy of the order type.
     */
    @Override
    public OrderType copy() {
        return new DayToDayOrder(this.maxCountedEmployees);
    }
}
