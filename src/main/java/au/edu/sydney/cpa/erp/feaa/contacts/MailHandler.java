package au.edu.sydney.cpa.erp.feaa.contacts;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.Mail;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.List;

/**
 * One of the Concrete Handler Class for the MAIL contact method.
 * An actual handler of the request chained in order of the original switch-case calls in ContactHandler.
 */
public class MailHandler extends AbstractHandler {

    private Handler nextHandler;

    /**
     * Default Constructor

     */
    public MailHandler() {
    }

    /**
     * Used as a pointer to the next handler in the chain of handlers.
     *
     * @param nextHandler - the next contact handler in the chain.
     */
    public void setNextHandler(Handler nextHandler) {

        this.nextHandler = nextHandler;

    }


    /**
     * request() method to handle the request for the MAIL contact method
     *
     * @param token  The authentication token to verify this operation with.
     * @param client The client to be referenced.
     * @param method The contact method to be requested.
     * @param data   The data to be printed.
     * @return true if invoice has been sent, otherwise false.
     */
    @Override
    public boolean request(AuthToken token, Client client, List<Handler> method, String data) {

        String address = client.getAddress();
        String suburb = client.getSuburb();
        String state = client.getState();
        String postcode = client.getPostCode();

        if (!method.isEmpty()) {

            setNextHandler(method.remove(0));
        }

        if (null != address && null != suburb &&

                null != state && null != postcode) {

            Mail.sendInvoice(token, client.getFName(), client.getLName(), data, address, suburb, state, postcode);

            return true;

        } else {

            nextHandler.request(token, client, method, data);

            return false;
        }

    }
}
