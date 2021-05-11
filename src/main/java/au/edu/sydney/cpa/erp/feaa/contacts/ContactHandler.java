package au.edu.sydney.cpa.erp.feaa.contacts;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {

    /**
     * Method that sends out the invoice based on the desired contact methods,
     * @param token The authentication token to verify this operation with.
     * @param client The client to be referenced.
     * @param priority List of contact methods to be requested.
     * @param data The data to be printed.
     * @return true if invoice has been sent, otherwise false.
     */
    public static boolean sendInvoice(AuthToken token, Client client, List<Handler> priority, String data) {

        return priority.remove(0).request(token, client, priority, data);

    }

    /**
     * Simple accessor method to retrieve the contact methods.
     * @return a contact method.
     */
    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Internal Accounting",
                "Phone call",
                "SMS"
        );
    }
}
