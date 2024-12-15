package start;

import presentation.controller.HomepageController;
import presentation.view.HomepageView;


public class Main {
    public static void main(String[] args) {
        HomepageView homepageView = new HomepageView();
        HomepageController homepageController = new HomepageController(homepageView);
    }
}