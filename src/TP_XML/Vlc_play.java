/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TP_XML;

import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author User
 */
public class Vlc_play extends JFrame implements KeyListener, WindowListener {

    EmbeddedMediaPlayer emp;
    Canvas c;
    public Vlc_play(String path) {
        setLocation(100, 100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
        addKeyListener(this);
        addWindowListener(this);
        c = new Canvas();
        c.setBackground(Color.BLACK);
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

// video will take half surface of the main panelR
        p.add(c);
        this.add(p);
        this.prepareMediaPlayer(path);

    }

    public void prepareMediaPlayer(String media) {
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Users\\User\\Documents\\NetBeansProjects\\TP_XML\\lib");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        MediaPlayerFactory mp = new MediaPlayerFactory();

        emp = mp.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(this));
        emp.setVideoSurface(mp.newVideoSurface(c));
        emp.setEnableMouseInputHandling(false);
        emp.setEnableKeyInputHandling(false);
        emp.prepareMedia(media);
        emp.play();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int i = 0;
        if (keyCode == KeyEvent.VK_SPACE) {
            i++;
            if ((i % 2) == 1) {
                emp.pause();
            }
            if ((i % 2) == 0) {
                emp.play();
            }
        }
        //full screen
        if (keyCode == KeyEvent.VK_ENTER) {
            emp.toggleFullScreen();
        }
        
        if  (keyCode== KeyEvent.VK_RIGHT){
        emp.skip(5000);
         }
        
        if  (keyCode== KeyEvent.VK_LEFT){
            emp.skip(-5000);
        }
        

    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        emp.stop();
        dispose();

    }

    @Override
    public void windowClosed(WindowEvent e) {
        emp.stop();
        dispose();
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
 
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
   
    }
}
