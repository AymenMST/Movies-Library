/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_XML;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * s
 *
 * @author User
 */
public class Ajouter extends JFrame {

    int i = 1;
    public String id, s;
    public String xmlFile = "C:\\Users\\User\\Documents\\NetBeansProjects\\TP_XML\\src\\TP_XML\\file.xml";
    public JButton ok, clear, movie_path;
    public JPanel mainPanel;
    public JTextField category_input, director_input, title_input, year_input, description_input;
    public JComboBox rating_input;
    public JLabel category_lable, description_lable, director_lable, title_lable, rating_lable, year_lable;
    public JFileChooser fileChooser;
    public DefaultTableModel tmodel;
    public Element movie, title, director, category, rating, year, description, ide;
    XMLOutputter sortie;
    SAXBuilder sxb = new SAXBuilder();
    public Document doc = (Document) sxb.build(xmlFile);
    Dimension dim = new Dimension(75, 25);
    int margin = 50;
    public String path;

    public Ajouter() throws JDOMException, IOException {

        title_lable = new JLabel("Title :");
        title_input = new JTextField();

        category_lable = new JLabel("Category :");
        category_input = new JTextField();

        director_lable = new JLabel("Director :");
        director_input = new JTextField();

        description_lable = new JLabel("Summary");
        description_input = new JTextField();

        year_lable = new JLabel("Year of Release :");
        year_input = new JTextField();

        rating_lable = new JLabel("Rating :");
        rating_input = new JComboBox<>(new String[]{
            "✩✩✩✩✩",
            "★✩✩✩✩",
            "★★✩✩✩",
            "★★★✩✩",
            "★★★★✩",
            "★★★★★",});

        movie_path = new JButton(new ImageIcon("C:\\Users\\User\\Downloads\\doc.png"));
        clear = new JButton("clear all");
        ok = new JButton("ok");
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));

        formPanel.add(title_lable);
        formPanel.add(title_input);

        formPanel.add(director_lable);
        formPanel.add(director_input);

        formPanel.add(category_lable);
        formPanel.add(category_input);

        formPanel.add(rating_lable);
        formPanel.add(rating_input);

        formPanel.add(year_lable);
        formPanel.add(year_input);

        formPanel.add(description_lable);
        formPanel.add(description_input);

        formPanel.add(new JLabel("Select a movie"));
        formPanel.add(movie_path);

        formPanel.add(clear);
        formPanel.add(ok);
        panel.add(formPanel);

        setTitle("Add a Movie");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(panel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        movie_path.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("C:\\Users\\User\\Downloads"));

                chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                    public boolean accept(File f) {
                        return (f.getName().toLowerCase().endsWith(".mkv")) || (f.getName().toLowerCase().endsWith(".mp4"))
                                || (f.getName().toLowerCase().endsWith(".avi"))
                                || f.isDirectory();
                    }

                    public String getDescription() {
                        return "Vidoes only";
                    }
                });

                int r = chooser.showOpenDialog(new JFrame());
                if (r == JFileChooser.APPROVE_OPTION) {
                    path = chooser.getSelectedFile().getPath();
                    System.out.println(path);
                }
            });

        });

        ok.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {

                // id = String.valueOf(i);
                try {
                    doc = sxb.build("C:\\Users\\User\\Documents\\NetBeansProjects\\TP_XML\\src\\TP_XML\\file.xml");
                } catch (JDOMException | IOException ex) {
                    Logger.getLogger(Ajouter.class.getName()).log(Level.SEVERE, null, ex);
                }
                Element racin = doc.getRootElement();
                List moviesList = racin.getChildren("Movie");
                if (moviesList.isEmpty() == false) {
                    Element last = (Element) moviesList.get(moviesList.size() - 1);
                    s = last.getChildText("Id");
                    i = Integer.valueOf(s) + 1;
                } else {
                    i = 1;
                }

                movie = new Element("Movie");
                //I tried it as an attribute and it doesn't work
                //  movie.setAttribute(new Attribute("id", String.valueOf(i)));

                movie.setAttribute(new Attribute("url", path));
                ide = (new Element("Id").setText(String.valueOf(i)));
                title = (new Element("Title").setText("" + title_input.getText()));
                director = (new Element("Director").setText("" + director_input.getText()));
                category = (new Element("Category").setText("" + category_input.getText()));
                rating = (new Element("Rating").setText("" + rating_input.getSelectedItem().toString()));
                year = (new Element("Year").setText("" + year_input.getText()));
                description = (new Element("Description").setText("" + description_input.getText()));
                movie.addContent(ide);
                movie.addContent(title);
                movie.addContent(director);
                movie.addContent(category);
                movie.addContent(rating);
                movie.addContent(year);
                movie.addContent(description);

                doc.getRootElement().addContent(movie);
                sauvegarder();
                JOptionPane.showMessageDialog(null, title_input.getText() + " ajouté avec succès");
            });

        });

        clear.addActionListener((e) -> {
            SwingUtilities.invokeLater(() -> {
                clear();
            });

        });
    }

    public void sauvegarder() {
        try {
            sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(doc, new FileWriter(xmlFile));
        } catch (IOException e) {
        }

    }


    public void clear() {
        title_input.setText("");
        director_input.setText("");
        category_input.setText("");
        year_input.setText("");
        description_input.setText("");

        path = "";
        id = "";
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
            java.util.logging.Logger.getLogger(Ajouter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(() -> {
            try {
                new Ajouter().setVisible(true);
            } catch (JDOMException | IOException ex) {
                Logger.getLogger(Ajouter.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
