package presentation.controller;

import presentation.view.HomepageView;
import presentation.view.ResultsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResultsController {
    private ResultsView resultsView;

    public ResultsController(ResultsView resultsView) {
        this.resultsView = resultsView;
        this.resultsView.addBackButtonListener(new BackButtonListener());
    }

    class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultsView.setVisible(false);
            HomepageView homepageView = new HomepageView();
            new HomepageController(homepageView);
        }
    }
}
