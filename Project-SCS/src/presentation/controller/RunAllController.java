package presentation.controller;

import presentation.view.HomepageView;
import presentation.view.RunAllView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RunAllController {
    private RunAllView runAllView;

    public RunAllController(RunAllView runAllView) {
        this.runAllView = runAllView;
        this.runAllView.addBackButtonListener(new BackButtonListener());
    }

    class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            runAllView.setVisible(false);
            HomepageView homepageView = new HomepageView();
            new HomepageController(homepageView);
        }
    }
}
