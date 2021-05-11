package au.edu.sydney.cpa.erp.feaa.contacts;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.List;

/**
 * Representation of the Handler.
 */
public interface Handler {

    /**
     * request() method to handle the request for the a contact method
     * @param token The authentication token to verify this operation with.
     * @param client The client to be referenced.
     * @param method The contact handler to be requested.
     * @param data The data to be printed.
     * @return true if invoice has been sent, otherwise false.
     */
    boolean request(AuthToken token, Client client, List<Handler> method, String data);

}
