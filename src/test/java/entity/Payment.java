package entity;

import entity.payment.Card;
import entity.payment.PayPal;
import entity.payment.PingIt;

public class Payment {

    private boolean card;
    private boolean paypal;
    private boolean pingIt;
    private boolean cashToDriver;

    public boolean getCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public boolean getPaypal() {
        return paypal;
    }

    public void setPaypal(boolean paypal) {
        this.paypal = paypal;
    }

    public boolean getPingIt() {
        return pingIt;
    }

    public void setPingIt(boolean pingIt) {
        this.pingIt = pingIt;
    }

    public boolean isCashToDriver() {
        return cashToDriver;
    }

    public void setCashToDriver(boolean cashToDriver) {
        this.cashToDriver = cashToDriver;
    }
}
