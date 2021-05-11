package au.edu.sydney.cpa.erp.feaa.ordering.desc;

import au.edu.sydney.cpa.erp.feaa.ordering.types.OrderType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.PriorityType;
import au.edu.sydney.cpa.erp.feaa.ordering.types.ScheduleType;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InvoiceDataImpl implements InvoiceData{

    /**
     * Algorithm to generate the invoice data.
     * Implementation mostly ported over from the existing codebase, with minor changes to fit the new design.
     * @param order - the order you are looking at.
     * @param orderType - the type of order (audit, daytoday)
     * @param priorityType - the priority type (priority or non priority)
     * @param scheduleType - the schedule type (regularly scheduled or one off)
     * @param isFinalised - whether the order has been finalised or not.
     * @return The order's invoice data.
     */
    @Override
    public String generateInvoiceData(Order order, OrderType orderType, PriorityType priorityType, ScheduleType scheduleType, boolean isFinalised){

        if(priorityType.getCriticalLoading() != 0){   //critical order

            if(scheduleType.isScheduled()){ //scheduled

                return String.format("Your priority business account will be charged: $%,.2f each quarter for %d quarters, with a total overall cost of: $%,.2f" +
                        "\nPlease see your internal accounting department for itemised details.", scheduleType.getRecurringCost(order), scheduleType.getNumberOfQuarters(), order.getTotalCommission());
            }else{  //unscheduled
                return String.format("Your priority business account has been charged: $%,.2f" +
                        "\nPlease see your internal accounting department for itemised details.", order.getTotalCommission());
            }
        }else{  //normal order
            if(scheduleType.isScheduled()){ //scheduled
                StringBuilder sb = new StringBuilder();

                sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
                sb.append("The cost to provide these services: $");
                sb.append(String.format("%,.2f",scheduleType.getRecurringCost(order)));
                sb.append(" each quarter, with a total overall cost of: $");
                sb.append(String.format("%,.2f", order.getTotalCommission()));
                sb.append("\nPlease see below for details:\n");

                //Map<Report, Integer> reports = getReports();
                List<Report> keyList = new ArrayList<>(order.getAllReports());
                keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

                for (Report report : keyList) {
                    sb.append("\tReport name: ");
                    sb.append(report.getReportName());
                    sb.append("\tEmployee Count: ");
                    sb.append(order.getReportEmployeeCount(report));
                    sb.append("\tCost per employee: ");
                    sb.append(String.format("$%,.2f", report.getCommission()));
                    sb.append("\tSubtotal: ");
                    sb.append(String.format("$%,.2f\n", report.getCommission() * order.getReportEmployeeCount(report)));
                }
                return sb.toString();
            }else{  //unscheduled
                double baseCommission = 0.0;
                double loadedCommission = order.getTotalCommission();

                StringBuilder sb = new StringBuilder();

                sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
                sb.append("The cost to provide these services: $");
                sb.append(String.format("%,.2f", order.getTotalCommission()));
                sb.append("\nPlease see below for details:\n");
                List<Report> keyList = new ArrayList<>(order.getAllReports());
                keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

                for (Report report : keyList) {
                    double subtotal = report.getCommission() * order.getReportEmployeeCount(report);
                    baseCommission += subtotal;

                    sb.append("\tReport name: ");
                    sb.append(report.getReportName());
                    sb.append("\tEmployee Count: ");
                    sb.append(order.getReportEmployeeCount(report));
                    sb.append("\tCost per employee: ");
                    sb.append(String.format("$%,.2f", report.getCommission()));
                    sb.append("\tSubtotal: ");
                    sb.append(String.format("$%,.2f\n", subtotal));
                }
                return sb.toString();
            }
        }
    }
}

