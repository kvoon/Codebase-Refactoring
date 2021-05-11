package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;

/**
 * Representation of the Report as a Value Object.
 */

public class ReportImpl implements Report, Comparable<Report>{

    private final String name;

    private final double commissionPerEmployee;

    /**
     * ReportImpl Constructor, caches all double[] variables upon initialisation.
     * @param name - The name of the report.
     * @param commissionPerEmployee - The commission amount per employee for the report.
     * @param legalData - The legal data for the report.
     * @param cashFlowData - The cash flow data for the report.
     * @param mergesData - the merges data for the report.
     * @param tallyingData - the tallying data for the report.
     * @param deductionsData - the deductions data for the report.
     */
    public ReportImpl(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {

        this.name = name;

        this.commissionPerEmployee = commissionPerEmployee;

        ReportObjFactory.cacheReportObj(name,legalData, cashFlowData, mergesData, tallyingData, deductionsData);

    }

    /**
     * Simple accessor method for report name.
     * @return The name of the report.
     */
    @Override
    public String getReportName() {
        return name;
    }

    /**
     * Simple accessor method for the Commission per employee value.
     * @return the Commission per employee.
     */
    @Override
    public double getCommission() {

        return commissionPerEmployee;
    }

    /**
     * Accessor method for the legal data.
     * Makes use of the ReportObj factory class to return the key of a particular report, before using the accessor.
     * @return The legal data stored in the report.
     */
    @Override
    public double[] getLegalData() {
        return ReportObjFactory.getReportObj(name).getLegalData();
    }

    /**
     * Accessor method for the cash flow data.
     * Makes use of the ReportObj factory class to return the key of a particular report, before using the accessor.
     * @return The cash flow data stored in the report.
     */
    @Override
    public double[] getCashFlowData() {
        return ReportObjFactory.getReportObj(name).getCashFlowData();
    }

    /**
     * Accessor method for the merges data.
     * Makes use of the ReportObj factory class to return the key of a particular report, before using the accessor.
     * @return The merges data stored in the report.
     */
    @Override
    public double[] getMergesData() {
        return ReportObjFactory.getReportObj(name).getMergesData();
    }

    /**
     * Accessor method for the tallying data.
     * Makes use of the ReportObj factory class to return the key of a particular report, before using the accessor.
     * @return The tallying data stored in the report.
     */
    @Override
    public double[] getTallyingData() {
        return ReportObjFactory.getReportObj(name).getTallyingData();
    }

    /**
     * Accessor method for the tallying data.
     * Makes use of the ReportObj factory class to return the key of a particular report, before using the accessor.
     * @return The tallying data stored in the report.
     */
    @Override
    public double[] getDeductionsData() {
        return ReportObjFactory.getReportObj(name).getDeductionsData();
    }

    /**
     * Formats the report name into a String representation.
     * @return String representation of the name.
     */
    @Override
    public String toString() {

        return String.format("%s", name);
    }

    /**
     * As ReportImpl is now a Value Object,
     * this method checks to see if all components are equal to one another.
     * @param other some Object, specifically report.
     * @return true if equal, false if not equal.
     */

    @Override
    public boolean equals(Object other) {

        return (name.equals(((Report) other).getReportName()) &&
                this.getCommission() == ((Report) other).getCommission() &&
                Arrays.equals(getLegalData(), ((Report) other).getLegalData()) &&
                Arrays.equals(this.getCashFlowData(), ((Report) other).getCashFlowData()) &&
                Arrays.equals(this.getMergesData(), ((Report) other).getMergesData()) &&
                Arrays.equals(this.getTallyingData(), ((Report) other).getTallyingData()) &&
                Arrays.equals(this.getDeductionsData(), ((Report) other).getDeductionsData()));
    }

    /**
     * Method used to generate the value object hashcode.
     * @return integer hash code value of the object.
     */
    @Override
    public int hashCode(){

        int result = 11; // some non zero value

        return 37 * result + (int) this.getCommission();
    }

    /**
     * Implementation of the Comparable interface.
     * @param o - some Report object
     * @return 0 if the values match, 1 if the value is greater, otherwise -1.
     */
    @Override
    public int compareTo(Report o) {

        int value = (int) this.getCommission();

        if(value == o.getCommission()){

            return  0;

        } else if (value > o.getCommission()){

            return 1;

        }else {

            return -1;
        }

    }
}
