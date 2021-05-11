package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;

/**
 * Implementation of the Client interface.
 * Representation of a client.
 */
public class ClientImpl implements Client {

    private final int id;
    private String fName;
    private String lName;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String internalAccounting;
    private String businessName;
    private String pigeonCoopID;
    private AuthToken token;

    /**
     * Constructs a new client by only initializing the id and token.
     * @param token - The authentication token to verify this operation with. May not be null.
     * @param id - the client id.
     */
    public ClientImpl(AuthToken token, int id) {

        this.id = id;
        this.token = token;

    }

    /**
     * Simple getter for client id
     * @return the client ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Simple getter for fName utilising lazy initialization.
     * @return The client's first name, only when needed.
     */
    @Override
    public String getFName() {

        if(this.fName == null){

            this.fName = TestDatabase.getInstance().getClientField(token, id, "fName");
        }
        return fName;
    }

    /**
     * Simple getter for lName utilising lazy initialization.
     * @return The client's last name, only when needed.
     */
    @Override
    public String getLName() {

        if(this.lName == null){

            this.lName = TestDatabase.getInstance().getClientField(token, id, "lName");
        }

        return lName;
    }
    /**
     * Simple getter for phoneNumber utilising lazy initialization.
     * @return The client's phone number, only when needed.
     */
    @Override
    public String getPhoneNumber() {

        if(this.phoneNumber == null){

            this.phoneNumber = TestDatabase.getInstance().getClientField(token, id, "phoneNumber");
        }

        return phoneNumber;
    }

    /**
     * Simple getter for emailAddress utilising lazy initialization.
     * @return The client's email address, only when needed.
     */
    @Override
    public String getEmailAddress() {

        if(this.emailAddress == null){

            this.emailAddress = TestDatabase.getInstance().getClientField(token, id, "emailAddress");
        }

        return emailAddress;
    }

    /**
     * Simple getter for address utilising lazy initialization.
     * @return The client's address, only when needed.
     */
    @Override
    public String getAddress() {

        if(this.address == null){

            this.address = TestDatabase.getInstance().getClientField(token, id, "address");
        }

        return address;
    }

    /**
     * Simple getter for suburb utilising lazy initialization.
     * @return The client's suburb, only when needed.
     */
    @Override
    public String getSuburb() {

        if(this.suburb == null){

            this.suburb = TestDatabase.getInstance().getClientField(token, id, "suburb");

        }

        return suburb;
    }

    /**
     * Simple getter for state utilising lazy initialization.
     * @return The client's state, only when needed.
     */
    @Override
    public String getState() {

        if(this.state == null){

            this.state = TestDatabase.getInstance().getClientField(token, id, "state");
        }

        return state;
    }

    /**
     * Simple getter for postCode utilising lazy initialization.
     * @return The client's postcode, only when needed.
     */
    @Override
    public String getPostCode() {

        if(this.postCode == null){

            this.postCode = TestDatabase.getInstance().getClientField(token, id, "postCode");
        }

        return postCode;
    }

    /**
     * Simple getter for internalAccounting utilising lazy initialization.
     * @return The client's internal accounting information, only when needed.
     */
    @Override
    public String getInternalAccounting() {

        if(this.internalAccounting == null){

            this.internalAccounting = TestDatabase.getInstance().getClientField(token, id, "internal accounting");
        }
        return internalAccounting;
    }

    /**
     * Simple getter for businessName utilising lazy initialization.
     * @return The client's business name, only when needed.
     */
    @Override
    public String getBusinessName() {

        if(this.businessName == null){

            this.businessName = TestDatabase.getInstance().getClientField(token, id, "businessName");
        }

        return businessName;
    }

    /**
     * Simple getter for pigeonCoopID utilising lazy initialization.
     * @return The client's carrier pigeon code, only when needed.
     */
    @Override
    public String getPigeonCoopID() {

        if(this.pigeonCoopID == null){

            this.pigeonCoopID = TestDatabase.getInstance().getClientField(token, id, "pigeonCoopID");
        }
        return pigeonCoopID;

    }
}

