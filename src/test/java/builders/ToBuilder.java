package builders;

import entity.To;

/**
 * Created by krishnanand on 25/07/16.
 */
public class ToBuilder extends Builder {

    private To to = pageStore.get(To.class);

    public ToBuilder() throws Exception {
        to.setDropOffLocation("Dr Gray's Hospital");
    }

    public ToBuilder withDropOffLocation(String dropOffLocation) {
        to.setDropOffLocation(dropOffLocation);
        return this;
    }

    public To build() {
        return to;
    }
}
