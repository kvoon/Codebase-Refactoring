package au.edu.sydney.cpa.erp.feaa.reports;

import com.google.common.primitives.ImmutableDoubleArray;

/**
 *  Representation of the Flyweight version of the Report Value object.
 *  Contains part of the original Report object.
 */

public class ReportObj{

    private final ImmutableDoubleArray legalData;
    private final ImmutableDoubleArray cashFlowData;
    private final ImmutableDoubleArray mergesData;
    private final ImmutableDoubleArray tallyingData;
    private final ImmutableDoubleArray deductionsData;


    /**
     * ReportObj Constructor - checks to see if any of the data entries are null, and sets it to null.
     * Otherwise, initialise the data as an immutable copy.
     * @param legalData - legal data stored in the report.
     * @param cashFlowData - the cash flow data stored in the report.
     * @param mergesData - the merges data stored in the report.
     * @param tallyingData - the tallying data stored in the report.
     * @param deductionsData - the deductions data stored in the report.
     */
    public ReportObj( double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {

        if(legalData == null) {

            this.legalData = null;


        } else {

            this.legalData = ImmutableDoubleArray.copyOf(legalData);

        }

        if(cashFlowData == null) {

            this.cashFlowData = null;

        }

        else {

            this.cashFlowData = ImmutableDoubleArray.copyOf(cashFlowData);

        }

        if(mergesData == null) {

            this.mergesData = null;

        } else {

            this.mergesData = ImmutableDoubleArray.copyOf(mergesData);
        }

        if(tallyingData == null) {

            this.tallyingData = null;

        } else {

            this.tallyingData = ImmutableDoubleArray.copyOf(tallyingData);
        }

        if(deductionsData == null) {

            this.deductionsData = null;

        }

        else  {

            this.deductionsData = ImmutableDoubleArray.copyOf(deductionsData);

        }
    }

    /**
     * Accessor method for the legal data.
     * @return returns the legal data for the report.
     */
    public double[] getLegalData() {

        if(this.legalData != null) {

            double[] temp = new double[this.legalData.length()];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = legalData.get(i);

            }

            return temp;

        } else {

            return null;
        }
    }

    /**
     * Accessor method for the cash flow data.
     * @return the cash flow data of the report.
     */
    public double[] getCashFlowData() {

        if(this.cashFlowData != null) {

            double[] temp = new double[this.cashFlowData.length()];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = cashFlowData.get(i);
            }

            return temp;

        } else {

            return null;

        }
    }

    /**
     * Accessor method
     * @return the merges data for the report.
     */
    public double[] getMergesData() {

        if(this.mergesData != null) {

            double[] temp = new double[this.mergesData.length()];

            for (int i = 0; i < temp.length; i++) {

                temp[i] = mergesData.get(i);
            }

            return temp;

        } else {

            return null;

        }

    }

    /**
     * Accessor method
     * @return the tallying data for the report.
     */
    public double[] getTallyingData() {

        if(this.tallyingData != null) {

            double[] temp = new double[this.tallyingData.length()];

            for (int i = 0; i < temp.length; ++i) {

                temp[i] = tallyingData.get(i);
            }

            return temp;

        } else {

            return null;
        }
    }

    /**
     * Accessor method
     * @return the deductions data for the report.
     */
    public double[] getDeductionsData() {

        if(this.deductionsData!=null) {

            double[] temp = new double[this.deductionsData.length()];

            for (int i = 0; i < temp.length; ++i) {

                temp[i] = deductionsData.get(i);

            }

            return temp;

        } else {

            return null;

        }
    }

}

