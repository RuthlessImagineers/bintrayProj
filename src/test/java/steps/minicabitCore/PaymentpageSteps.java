package steps.minicabitCore;

import cucumber.api.java.en.And;
import pages.minicabitCore.PaymentPageModules.PaymentPage;
import steps.BaseSteps;


public class PaymentpageSteps extends BaseSteps {

    @And("^On Payment page I select payment type as (\\w+) and enter paymentDetails (\\w+) of (\\w+)$")
    public void onPaymentPageISelectPaymentTypeAsPaymentTypeAndEnterPaymentDetailsOfCardType(String paymentType, String paymentDetails, String cardType) throws Throwable {
            pageStore.get(PaymentPage.class).makePaymentWith(paymentType,paymentDetails,cardType);
    }
}
