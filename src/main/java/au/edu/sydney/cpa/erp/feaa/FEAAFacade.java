package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthModule;
import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.feaa.contacts.*;
import au.edu.sydney.cpa.erp.ordering.Client;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.feaa.ordering.*;
import au.edu.sydney.cpa.erp.feaa.reports.ReportDatabase;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")

/**
 * The main access view for users of the FEAA module.
 */
public class FEAAFacade {
    private AuthToken token;

    /**
     * If the credentials are valid the FEAA will be in a logged in state until its Auth provider is changed or logout() is called.
     * @param userName - The username to verify.
     * @param password - The password to verify.
     * @return True if the credentials were valid, otherwise false.
     */
    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);

        return null != token;
    }

    /**
     * Returns a list of all current orders.
     * @return A list of current orders.
     */
    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order : orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    /**
     * Creates a new order.
     * @param clientID - the client id to assign.
     * @param date - the date the order was created.
     * @param isCritical - whether the order is a priority or not.
     * @param isScheduled - whether the order has been scheduled or not (regularly scheduled or one off)
     * @param orderType - the type of order (audit or day to day)
     * @param criticalLoadingRaw - the raw critical loading value to assign.
     * @param maxCountedEmployees - the max counted employees to assign.
     * @param numQuarters - the number of quarters to assign.
     * @return a new order based on the given parameters.
     */
    public Integer createOrder(int clientID, LocalDateTime date, boolean isCritical, boolean isScheduled, int orderType, int criticalLoadingRaw, int maxCountedEmployees, int numQuarters) {

        if (null == token) {
            throw new SecurityException();
        }

        double criticalLoading = criticalLoadingRaw / 100.0;

        Order order;

        if (!TestDatabase.getInstance().getClientIDs(token).contains(clientID)) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();

        order = OrderFactory.makeOrder(id, clientID, date, isCritical, isScheduled, orderType, criticalLoading, maxCountedEmployees, numQuarters);

        if(order==null){
            return null;    //if order is null, order id is null;
        }

        TestDatabase.getInstance().saveOrder(token, order);
        return order.getOrderID();
    }

    /**
     * Returns a list of all current clients.
     * @return A list of all current clients represented by the client id number.
     */
    public List<Integer> getAllClientIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getClientIDs(token);
    }

    /**
     * Returns a given client.
     * @param id - client id to be selected.
     * @return details of the client selected based on the id.
     */
    public Client getClient(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        return new ClientImpl(token, id);
    }

    /**
     * Removes an existing order.
     * @param id - order id.
     * @return true if sucessfully removed, otherwise false
     */
    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    /**
     * Returns a list of all current reports.
     * @return a list of all current reports.
     */
    public List<Report> getAllReports() {
        if (null == token) {
            throw new SecurityException();
        }

        return new ArrayList<>(ReportDatabase.getTestReports());
    }

    /**
     * Finalises an order
     * @param orderID - the order id to be asssinged.
     * @param contactPriority - the contact method priority
     * @return true if successful, otherwise false.
     */
    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        List<ContactMethod> contactPriorityAsMethods = new ArrayList<>();

        List<Handler> contactHandlerList = new ArrayList<>();

        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "internal accounting":
                        contactPriorityAsMethods.add(ContactMethod.INTERNAL_ACCOUNTING);
                        contactHandlerList.add( new InternalAccountingHandler());
                        break;
                    case "email":
                        contactPriorityAsMethods.add(ContactMethod.EMAIL);
                        contactHandlerList.add( new EmailHandler());
                        break;
                    case "carrier pigeon":
                        contactPriorityAsMethods.add(ContactMethod.CARRIER_PIGEON);
                        contactHandlerList.add( new CarrierPigeonHandler());
                        break;
                    case "mail":
                        contactPriorityAsMethods.add(ContactMethod.MAIL);
                        contactHandlerList.add( new MailHandler());
                        break;
                    case "phone call":
                        contactPriorityAsMethods.add(ContactMethod.PHONECALL);
                        contactHandlerList.add( new PhoneCallHandler());
                        break;
                    case "sms":
                        contactPriorityAsMethods.add(ContactMethod.SMS);
                        contactHandlerList.add( new SMSHandler());
                        break;
                    default:
                        break;
                }
            }
        }

        if (contactPriorityAsMethods.size() == 0) { // needs setting to default
            contactPriorityAsMethods = Arrays.asList(
                    ContactMethod.INTERNAL_ACCOUNTING,
                    ContactMethod.EMAIL,
                    ContactMethod.CARRIER_PIGEON,
                    ContactMethod.MAIL,
                    ContactMethod.PHONECALL
            );
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        order.finalise();
        TestDatabase.getInstance().saveOrder(token, order);
        return ContactHandler.sendInvoice(token, getClient(order.getClient()), contactHandlerList, order.generateInvoiceData());
    }

    /**
     * Moves the FEAAFacade to a logged out state.
     */
    public void logout() {
        AuthModule.logout(token);
        token = null;
    }

    /**
     * Returns the total commission for an order.
     * @param orderID - the order id to be selected.
     * @return the total commission for an order.
     */
    public double getOrderTotalCommission(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCommission();
    }

    /**
     * Sets the number of employees for a report of the assigned order and saves it to the database.
     * @param orderID - the order id to assign.
     * @param report - the report to assign.
     * @param numEmployees - the number of employees involved in the order.
     */
    public void orderLineSet(int orderID, Report report, int numEmployees) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setReport(report, numEmployees);

        TestDatabase.getInstance().saveOrder(token, order);
    }

    /**
     * Returns a long description of the order
     * @param orderID - the order id to be selected.
     * @return a long description of the order.
     */
    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    /**
     * Returns a short description of the order
     * @param orderID - the order id to be selected.
     * @return a short description of the order.
     */
    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    /**
     * Returns a list of all current known contact methods.
     * @return a list of current known contact methods.
     */
    public List<String> getKnownContactMethods() {
        if (null == token) {
            throw new SecurityException();
        }
        return ContactHandler.getKnownMethods();
    }
}
