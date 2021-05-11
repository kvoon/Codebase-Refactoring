package au.edu.sydney.cpa.erp.feaa.reports;

import java.util.HashMap;

/**
 * Representation of the Flyweight Factory for the ReportObj
 */
public class ReportObjFactory{

    private static final HashMap<String, ReportObj> reportObj = new HashMap<>();

    /**
     * Method used to cache a ReportObj, and stores it into a HashMap.
     * @param key - key of the ReportObj stored in the flyweight state Hashmap.
     * @param legalData - legal data stored in a report.
     * @param cashFlowData - cash flow data stored in a report.
     * @param mergesData - merges data stored in a report.
     * @param tallyingData - tallying data stored in a report.
     * @param deductionsData - deduction data stored in a given report.
     */
    public static void cacheReportObj(String key,
                                       double[] legalData,
                                       double[] cashFlowData,
                                       double[] mergesData,
                                       double[] tallyingData,
                                       double[] deductionsData) {

        reportObj.put(key, new ReportObj(legalData, cashFlowData, mergesData, tallyingData, deductionsData));
    }

    /**
     * Accessor method to retrieve the flyweight state from the Hashmap.
     * @param key - part of the key-value pair to the reportObj Hashmap.
     * @return the Report that matches the given key.
     */
    public static ReportObj getReportObj(String key) {

        return reportObj.get(key);

    }
}
