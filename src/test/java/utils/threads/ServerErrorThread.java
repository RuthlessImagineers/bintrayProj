package utils.threads;

import exceptions.UnknownServerException;
import pages.BasePage;
import pages.Popup;
import steps.StartingSteps;
import utils.Constants;

public class ServerErrorThread extends BasePage implements Runnable {

    private boolean scenarioCompleted;
    private StartingSteps startingSteps;

    public ServerErrorThread(StartingSteps startingSteps) throws Exception {
        this.startingSteps = startingSteps;
    }


    @Override
    public void run() {
        while (scenarioCompleted) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean errorExists = pageStore.get(Popup.class).verifyIfAErrorPopupIsDisplayed(Constants.PopupMessages.ERROR_SERVER);
                if(errorExists) {
                    try {
                        scenarioCompleted = true;
                        throw new UnknownServerException();
                    } catch (UnknownServerException e) {
                    }
                }
        }

    }
}
