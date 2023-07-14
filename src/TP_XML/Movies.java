/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_XML;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author User
 */
public class Movies extends JFrame {

    int index = 0;
    int i =1;
    String id_value;
    public DefaultTableModel tmodel;
    public JTable tab;
    public Document doc;
    public SAXBuilder builder = new SAXBuilder();
    public JButton add, play, delete, update, load;
    public Object[] header;
    public Object[][] body;
Dimension dim =new Dimension(1000, 400);
    class JButton1 extends JButton {

        public JButton1(String s) {
            super();
            setText(s);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

    }

    public Movies() {

        JPanel p_main = new JPanel(new GridBagLayout());
        JPanel p_buttons = new JPanel(new GridLayout(8, 1));

        add = new JButton1("Add");
        play = new JButton1("Play");
        delete = new JButton1("Delete");
        //  update = new JButton1("Update");
        load = new JButton1("Load");

        p_buttons.add(play);
        p_buttons.add(new JSeparator());
        p_buttons.add(add);
        p_buttons.add(new JSeparator());
        // p_buttons.add(update);
        // p_buttons.add(new JSeparator());
        p_buttons.add(load);
        p_buttons.add(new JSeparator());
        p_buttons.add(delete);
        p_buttons.add(new JSeparator());

        header = new Object[]{"N°:", "Title", "Director", "Category", "Rating", "Release Year",};
        body = new Object[0][6];
        tmodel = new DefaultTableModel(body, header);
        tab = new JTable(tmodel);
        JScrollPane Jsp = new JScrollPane(tab);
        p_main.add(Jsp);
        p_main.add(p_buttons);
        setContentPane(p_main);
        setTitle("Watch by Two Click");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        init();
    }

    protected void init() {

        delete.setEnabled(false);
        play.setEnabled(false);
        tmodel.addTableModelListener((TableModelEvent e) -> {
            //e.getSource().toString();
            if (e.getType() == TableModelEvent.DELETE) {

            }
        });

        tab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tab.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {

            
            if (tab.getSelectedRow()>=0){
            int column = 0;
            int row = tab.getSelectedRow();
            id_value = tab.getModel().getValueAt(row, column).toString();}

            play.setEnabled(true);
            delete.setEnabled(true);
            // System.out.println("attribut d'element dans tableau"+ ind);

        });

        add.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    
                    if (i==1){
                    new Ajouter().setVisible(true);
                    i++;}
                } catch (JDOMException | IOException ex) {
                    Logger.getLogger(Movies.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        });

        play.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    String path = null;

                    //On crée un nouveau document JDOM avec en argument le fichier XML
                    doc = builder.build("C:\\Users\\User\\Documents\\NetBeansProjects\\TP_XML\\src\\TP_XML\\file.xml");
                    Element racine = doc.getRootElement();

                    List AttList = racine.getChildren("Movie");

                    Iterator iterator = AttList.iterator();
                    while (iterator.hasNext()) {
                        Element courant = (Element) iterator.next();
                        String from_xml = courant.getChildText("Id");

                        if (from_xml.equalsIgnoreCase(id_value)) {
                            String url = courant.getAttributeValue("url");
                            System.out.println("attribut d'element xml" + from_xml);
                            new Vlc_play(url).setTitle(courant.getChildText("Title"));

                        }
                    }
                    System.out.println("attribut d'element dans tableau" + id_value);

                } catch (JDOMException | IOException ex) {
                    Logger.getLogger(Movies.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });

        delete.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {

                try {
                    String ObjButtons[] = {"Yes", "No"};
                    doc = builder.build("C:\\Users\\User\\Documents\\NetBeansProjects\\TP_XML\\src\\TP_XML\\file.xml");
                    Element racine = doc.getRootElement();
                    int PromptResult = JOptionPane.showOptionDialog(null,
                            "Are you sure you want to delete?", "Really",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                            ObjButtons, ObjButtons[1]);
                    if (PromptResult == 0) {
                        System.out.println(tab.getSelectedRow());
                        tmodel.removeRow(tab.getSelectedRow());
                        tab.clearSelection();

                        List AttList = racine.getChildren("Movie");
                        Iterator iterator = AttList.iterator();
                        while (iterator.hasNext()) {
                            Element courant = (Element) iterator.next();
                            if (courant.getChildText("Id").equals(id_value)) {
                                iterator.remove();
                                sauvegarder();
                                //    courant.getParent().removeContent(courant);
                            }

                        }

                    }
                } catch (JDOMException | IOException ex) {
                    Logger.getLogger(Movies.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        });

        load.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    doc = builder.build("C:\\Users\\User\\Documents\\NetBeansProjects\\TP_XML\\src\\TP_XML\\file.xml");
                } catch (IOException | JDOMException e1) {
                }
                int rowCount = tmodel.getRowCount();
                for (int i = rowCount - 1; i >= 0; i--) {
                    tmodel.removeRow(i);
                }
                Element racine = doc.getRootElement();
                List moviesList = racine.getChildren("Movie");
                if (moviesList.isEmpty()) {

                    JOptionPane.showMessageDialog(null, "      Your movie list is empty " + "\n" + "Try to add a movie before loading ");
                } else {
                    Iterator iterator = moviesList.iterator();
                    int i = 0;
                    while (iterator.hasNext()) {
                        Element courant = (Element) iterator.next();
                        ++i;
                        courant.getChild("Id").setText(String.valueOf(i));
                        sauvegarder();
                        tmodel.addRow(new Object[]{i, courant.getChild("Title").getText(), courant.getChild("Director").getText(), courant.getChild("Category").getText(), courant.getChild("Rating").getText(), courant.getChild("Year").getText(),});
                    }
                }
            });
        });
    }

    public void sauvegarder() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(doc, new FileOutputStream("C:\\Users\\User\\Documents\\NetBeansProjects\\TP_XML\\src\\TP_XML\\file.xml"));
        } catch (IOException e) {
        }

    }

    public void reNumbred() {

    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ajouter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(() -> {
            new Movies().setVisible(true);
        });

    }
}
