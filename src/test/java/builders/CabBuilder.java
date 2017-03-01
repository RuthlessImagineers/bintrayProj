package builders;

import entity.Payment;
import entity.searchResults.Cab;
import enums.PaymentType;
import org.openqa.selenium.WebElement;

import static utils.Constants.CommonData.*;
import static utils.Constants.Currency.POUND;

public class CabBuilder extends Builder {

    Cab cab = new Cab();

    public CabBuilder() throws Exception {
        cab.setCabProviderTitle("Cab provider 7");
        cab.setCabProviderBase("Based in Elgin");
        cab.setCabProviderPaymentType("Card Only");
    }

    public CabBuilder withCabProviderTitle(WebElement cabProviderTitleEle) {
        if(cabProviderTitleEle!=null)
        cab.setCabProviderTitle(cabProviderTitleEle.getText());
        else
        cab.setCabProviderTitle("");
        return this;
    }

    public CabBuilder withCabProviderPrice(WebElement cabProviderPriceEle) {
        if(cabProviderPriceEle!=null)
        cab.setCabProviderPrice(cabProviderPriceEle.getText());
        else
        cab.setCabProviderPrice("");
        return this;
    }

    public CabBuilder withCabProviderPrice(WebElement cabProviderPriceEle, PaymentType paymentType) {
        if(cabProviderPriceEle==null) {
            cab.setCabProviderPrice("");
            return this;
        }
        switch (paymentType) {
            case CASH:
            case PAYPAL:
            case PINGIT:
                cab.setCabProviderPrice(cabProviderPriceEle.getText());
                break;
            case CARD:
            case EXISTINGCARD:
                cab.setCabProviderPrice(getPriceWithLevy(cabProviderPriceEle.getText()));
                break;
        }
        return this;
    }

    public CabBuilder withCabProviderBase(WebElement cabProviderBaseEle) {
        if(cabProviderBaseEle!=null)
            cab.setCabProviderBase(cabProviderBaseEle.getText());
        else
            cab.setCabProviderBase("");

        return this;
    }

    public CabBuilder withCabProviderEmission(WebElement cabProviderEmissionEle) {
        if(cabProviderEmissionEle!=null)
            cab.setCabProviderEmission(cabProviderEmissionEle.getText());
        else
            cab.setCabProviderEmission("");
        return this;
    }

    public CabBuilder withCabProviderPaymentType(WebElement cabProviderPaymentTypeEle) {
        if(cabProviderPaymentTypeEle != null)
            cab.setCabProviderPaymentType(cabProviderPaymentTypeEle.getText());
        else
            cab.setCabProviderPaymentType("");
        return this;
    }


    public Cab build() {
        return cab;
    }



}
