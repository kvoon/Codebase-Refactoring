package au.edu.sydney.cpa.erp.feaa.contacts;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.List;

/**
 * Abstract implementation of the Handler interface.
 * Used as a layer of abstraction to create concrete handler classes.
 */
public abstract class AbstractHandler implements Handler {

    private Handler handler;

    /**
     * Default constructor
     */

    public AbstractHandler(){
    }

    /**
     * method to handle the requests to handlers.
     * @param token The authentication token to verify this operation with.
     * @param client The client to be referenced.
     * @param method The contact handler to be requested.
     * @param data The data to be printed.
     * @return true if a request is successful, otherwise false.
     */
    @Override
    public boolean request(AuthToken token, Client client, List<Handler> method, String data){

        if(handler != null){

            handler.request(token,client,method,data);

            return true;

        }else {

            return false;
        }

    }


}
