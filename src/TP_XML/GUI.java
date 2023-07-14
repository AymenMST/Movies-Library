/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_XML;

import java.io.IOException;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author User
 */
public class GUI extends JFrame {

    //String imgUrl = "C:\\Users\\User\\Downloads\\clipart1596157.png";
    //ImageIcon icone = new ImageIcon(imgUrl);
    Dimension dim = new Dimension(200, 45);
    int margin = 15;

    public GUI() {
        JButton b_films = new JButton("Get into Films");
        JButton b_exit = new JButton("Close");

        Box mainPanel = Box.createVerticalBox();
        mainPanel.add(Box.createVerticalStrut(margin));

        b_films.setPreferredSize(dim);
        b_films.setMaximumSize(dim);
        mainPanel.add(b_films);
        mainPanel.add(Box.createVerticalStrut(margin));

        mainPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        mainPanel.add(Box.createVerticalStrut(margin));

        b_exit.setPreferredSize(dim);
        b_exit.setMaximumSize(dim);
        mainPanel.add(b_exit);

        mainPanel.add(Box.createVerticalStrut(margin));

        Box rootPanel = Box.createHorizontalBox();
        rootPanel.add(Box.createHorizontalStrut(margin));
        rootPanel.add(mainPanel);
        rootPanel.add(Box.createHorizontalStrut(margin));

        b_films.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                new Movies().setVisible(true);
                dispose();
            });
        });

        b_exit.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                dispose();
            });
        });
        setTitle("Watch what you want");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) throws IOException {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });

    }
}
