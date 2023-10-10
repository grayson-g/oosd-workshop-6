// Author: Alice
package com.team2.oosdworkshop6;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {
    private SimpleIntegerProperty agentId;
    private SimpleStringProperty agtFirstName;
    private SimpleStringProperty agtMiddleInitial;
    private SimpleStringProperty agtLastName;
    private SimpleStringProperty agtBusPhone;
    private SimpleStringProperty agtEmail;
    private SimpleStringProperty agtPosition;
    private SimpleIntegerProperty agencyId;

    public Agent(int agentId, String agtFirstName, String agtMiddleInitial, String agtLastName,
                 String agtBusPhone, String agtEmail, String agtPosition, int agencyId) {
        this.agentId = new SimpleIntegerProperty(agentId);
        this.agtFirstName = new SimpleStringProperty(agtFirstName);
        this.agtMiddleInitial = new SimpleStringProperty(agtMiddleInitial);
        this.agtLastName = new SimpleStringProperty(agtLastName);
        this.agtBusPhone = new SimpleStringProperty(agtBusPhone);
        this.agtEmail = new SimpleStringProperty(agtEmail);
        this.agtPosition = new SimpleStringProperty(agtPosition);
        this.agencyId = new SimpleIntegerProperty(agencyId);
    }

    public int getAgentId() {
        return agentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
    }

    public String getAgtFirstName() {
        return agtFirstName.get();
    }

    public SimpleStringProperty agtFirstNameProperty() {
        return agtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        this.agtFirstName.set(agtFirstName);
    }

    public String getAgtMiddleInitial() {
        return agtMiddleInitial.get();
    }

    public SimpleStringProperty agtMiddleInitialProperty() {
        return agtMiddleInitial;
    }

    public void setAgtMiddleInitial(String agtMiddleInitial) {
        this.agtMiddleInitial.set(agtMiddleInitial);
    }

    public String getAgtLastName() {
        return agtLastName.get();
    }

    public SimpleStringProperty agtLastNameProperty() {
        return agtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        this.agtLastName.set(agtLastName);
    }

    public String getAgtBusPhone() {
        return agtBusPhone.get();
    }

    public SimpleStringProperty agtBusPhoneProperty() {
        return agtBusPhone;
    }

    public void setAgtBusPhone(String agtBusPhone) {
        this.agtBusPhone.set(agtBusPhone);
    }

    public String getAgtEmail() {
        return agtEmail.get();
    }

    public SimpleStringProperty agtEmailProperty() {
        return agtEmail;
    }

    public void setAgtEmail(String agtEmail) {
        this.agtEmail.set(agtEmail);
    }

    public String getAgtPosition() {
        return agtPosition.get();
    }

    public SimpleStringProperty agtPositionProperty() {
        return agtPosition;
    }

    public void setAgtPosition(String agtPosition) {
        this.agtPosition.set(agtPosition);
    }

    public int getAgencyId() {
        return agencyId.get();
    }

    public SimpleIntegerProperty agencyIdProperty() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId.set(agencyId);
    }

}

