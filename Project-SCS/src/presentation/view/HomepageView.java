package presentation.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The HomepageView class represents the homepage view of the Warehouse Order Management system.
 * It extends the Jthis class and contains three buttons for managing clients, products and orders.
 */
public class HomepageView extends JFrame {
    private JButton allocateStaticallyButton;
    private JButton allocateDynamicallyButton;
    private JButton accessStaticallyButton;
    private JButton accessDinamicallyButton;
    private JButton threadCreationButton;
    private JButton threadContextSwitchButton;
    private JButton insertIntoListButton;
    private JButton removeFromListButton;
    private JButton sortListButton;
    private JButton runAllButton;

    public HomepageView() {
        this.getContentPane().setBackground(new Color(204, 204, 255));
        this.setBounds(100, 100, 604, 367);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        this.setResizable(false);

        JLabel titleLabel = new JLabel("MEASURING EXECUTION TIME OF PROCESSES");
        titleLabel.setForeground(new Color(0, 28, 125));
        titleLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(54, 6, 510, 58);
        this.getContentPane().add(titleLabel);

        allocateStaticallyButton = new JButton("Allocate statically");
        allocateStaticallyButton.setBackground(new Color(255, 255, 204));
        allocateStaticallyButton.setBounds(54, 88, 213, 29);
        this.getContentPane().add(allocateStaticallyButton);

        allocateDynamicallyButton = new JButton("Allocate dynamically");
        allocateDynamicallyButton.setBounds(279, 88, 213, 29);
        this.getContentPane().add(allocateDynamicallyButton);

        accessStaticallyButton = new JButton("Access statically");
        accessStaticallyButton.setBounds(54, 129, 213, 29);
        this.getContentPane().add(accessStaticallyButton);

        accessDinamicallyButton = new JButton("Access dynamically");
        accessDinamicallyButton.setBounds(279, 129, 213, 29);
        this.getContentPane().add(accessDinamicallyButton);

        threadContextSwitchButton = new JButton("Thread context switch");
        threadContextSwitchButton.setBounds(279, 170, 213, 29);
        this.getContentPane().add(threadContextSwitchButton);

        threadCreationButton = new JButton("Thread creation");
        threadCreationButton.setBounds(54, 170, 213, 29);
        this.getContentPane().add(threadCreationButton);

        removeFromListButton = new JButton("Remove from list");
        removeFromListButton.setBackground(UIManager.getColor("ToolTip.background"));
        removeFromListButton.setBounds(279, 211, 213, 29);
        this.getContentPane().add(removeFromListButton);

        runAllButton = new JButton("Run all");
        runAllButton.setForeground(new Color(0, 0, 139));
        runAllButton.setBackground(Color.YELLOW);
        runAllButton.setBounds(189, 287, 213, 29);
        this.getContentPane().add(runAllButton);

        insertIntoListButton = new JButton("Insert into list");
        insertIntoListButton.setBackground(UIManager.getColor("ToolTip.background"));
        insertIntoListButton.setBounds(54, 211, 213, 29);
        this.getContentPane().add(insertIntoListButton);

        sortListButton = new JButton("Sort list");
        sortListButton.setBackground(UIManager.getColor("ToolTip.background"));
        sortListButton.setBounds(189, 246, 213, 29);
        this.getContentPane().add(sortListButton);

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBounds(0, 0, 604, 419);
        imageLabel.setBackground(new Color(255, 222, 173));
        imageLabel.setIcon(new ImageIcon("src/wallpaper.JPG"));
        this.add(imageLabel);

        this.setTitle("SCS - HOMEPAGE");
        this.setVisible(true);
    }

    public void addAllocateStaticallyButton(ActionListener actionListener) {
        this.allocateStaticallyButton.addActionListener(actionListener);
    }

    public void addAllocateDynamicallyButton(ActionListener actionListener) {
        this.allocateDynamicallyButton.addActionListener(actionListener);
    }

    public void addAccessStaticallyButton(ActionListener actionListener) {
        this.accessStaticallyButton.addActionListener(actionListener);
    }

    public void addAccessDynamicallyButton(ActionListener actionListener) {
        this.accessDinamicallyButton.addActionListener(actionListener);
    }

    public void addThreadCreationButton(ActionListener actionListener) {
        this.threadCreationButton.addActionListener(actionListener);
    }

    public void addThreadContextSwitchButton(ActionListener actionListener) {
        this.threadContextSwitchButton.addActionListener(actionListener);
    }

    public void addInsertIntoListButton(ActionListener actionListener){
        this.insertIntoListButton.addActionListener(actionListener);
    }

    public void addRemoveFromListButton(ActionListener actionListener){
        this.removeFromListButton.addActionListener(actionListener);
    }

    public void addSortListButton(ActionListener actionListener){
        this.sortListButton.addActionListener(actionListener);
    }

    public void addRunAllButton(ActionListener actionListener) {
        this.runAllButton.addActionListener(actionListener);
    }
}
