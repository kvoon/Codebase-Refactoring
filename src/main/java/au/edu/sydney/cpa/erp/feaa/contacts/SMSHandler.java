package au.edu.sydney.cpa.erp.feaa.contacts;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.contact.SMS;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.List;

/**
 * One of the Concrete Handler Class for the SMS contact method.
 * An actual handler of the request chained in order of the original switch-case calls in ContactHandler.
 */
public class SMSHandler extends AbstractHandler {

    private Handler nextHandler;

    /**
     * Default Constructor
     */
    public SMSHandler() {

    }


    /**
     * Used as a pointer to the next handler in the chain of handlers.
     * @param nextHandler - the next contact handler in the chain.
     */
    public void setNextHandler(Handler nextHandler){

        this.nextHandler = nextHandler;

    }

    /**
     * request() method to handle the request for the SMS contact method
     * @param token The authentication token to verify this operation with.
     * @param client The client to be referenced.
     * @param method The contact method to be requested.
     * @param data The data to be printed.
     * @return true if invoice has been sent, otherwise false.
     */
    @Override
    public boolean request(AuthToken token, Client client, List<Handler> method, String data) {

        if (!method.isEmpty()) {

            setNextHandler(method.remove(0));
        }

        String smsPhone = client.getPhoneNumber();

        if (null != smsPhone) {

            SMS.sendInvoice(token, client.getFName(), client.getLName(), data, smsPhone);

            return true;

        } else {

            nextHandler.request(token, client, method, data);

            return false;

        }
    }

}

