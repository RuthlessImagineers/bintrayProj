package entity.passengerDetails;

/**
 * Created by krishnanand on 26/07/16.
 */
public class Social {

    private boolean article;
    private boolean socialMedia;
    private boolean taxiOperator;

    public boolean isArticle() {
        return article;
    }

    public void setArticle(boolean article) {
        this.article = article;
    }

    public boolean isSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(boolean socialMedia) {
        this.socialMedia = socialMedia;
    }

    public boolean isTaxiOperator() {
        return taxiOperator;
    }

    public void setTaxiOperator(boolean taxiOperator) {
        this.taxiOperator = taxiOperator;
    }
}
