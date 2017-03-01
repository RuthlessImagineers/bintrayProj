package utils.dataUtils;

import entity.payment.Card;
import enums.*;
import exceptions.*;

/**
 * Maps enum values passed as string in feature file to the Enum type in java.enums package
 * Created by krishnanand on 27/07/16.
 */
public class EnumMapper {

    public TripType getTripType(String tripType) throws TripTypeException {
            TripType type = null;
        try {
            type = TripType.valueOf(tripType.toUpperCase());
        } catch (Exception e) {
            throw new TripTypeException("Trip type "+tripType+" does not exist. Currently allowed triptypes are "+TripType.values());
        }
       return type;
    }

    public UserType getUserType(String userType) throws UserTypeException {
        UserType type = null;
        try {
            type = UserType.valueOf(userType.toUpperCase());
        } catch (Exception e) {
            throw new UserTypeException("User type "+userType+" does not exist. Current allowed users are "+UserType.values());
        }

        return type;
    }

    public JsonData getJsonType(String jsonData) throws MissingDataSourceException {
        JsonData data = null;
        try {
            data = JsonData.valueOf(jsonData.toUpperCase());
        } catch (Exception e) {
            throw new MissingDataSourceException("Data file for "+jsonData+" could not be found. Or the data file name is invalid." +
                    "Check if your data source matches current data files "+ JsonData.values());
        }
        return data;
    }

    public PaymentType getPaymentType(String paymentType) {
        PaymentType type = null;
        try {
            type =PaymentType.valueOf(paymentType.toUpperCase());
        } catch (Exception e) {

        }
        return type;
    }

    public CardType getCardType(String cardType) throws UnsupportedCardTypeException {
        CardType type = null;
        try {
            type = CardType.valueOf(cardType.toUpperCase());
        } catch (Exception e) {
            throw new UnsupportedCardTypeException("Card type " + cardType + " is not supported for payment by card. Check if" +
                    " your card type matches current cardtypes " + CardType.values());
        }
        return type;
    }

    public MandatoryFields getMandatoryFieldType(String mandatoryField) throws UnsupportedMandatoryFieldException {
        MandatoryFields type = null;
        try{
            type = MandatoryFields.valueOf(mandatoryField.toUpperCase());
        } catch (Exception e) {
            throw new UnsupportedMandatoryFieldException("Mandatory Field type "+mandatoryField+" is not supported for payment by card. Check if" +
                    " your card type matches current mandatory fields "+MandatoryFields.values());
        }
        return type;
    }
}
