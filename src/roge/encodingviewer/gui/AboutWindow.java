package roge.encodingviewer.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import roge.encodingviewer.main.Main;
import roge.encodingviewer.resources.Resources;
import roge.gui.RWindow;

/**
 * @author Nicholas Rogé
 *
 */
public class AboutWindow extends RWindow{
    /*Begin Constructor Methods*/
    public AboutWindow(){
        super();
        
        this.setDefaultCloseOperation(RWindow.DISPOSE_ON_CLOSE);
    }
    
    public AboutWindow(String title){
        super(title);
        
        this.setDefaultCloseOperation(RWindow.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setSize(new Dimension(300,200));
    }
    /*End Constructor Methods*/
    
    /*Start Overridden Methods*/   
    @Override protected void _addContent(final JPanel main_panel){
        JLabel about_text=null;
        
        
        about_text=new JLabel(
"<html>"+
    "Program Written by <strong>Nicholas Rogé</strong><br />"+
    "<br />"+
    "Version:  "+Resources.Strings.version+"<br />"+
    "This program was designed to work as an educational aid for anyone attempting to teach the various encoding techniques used when transmitting data digitally."+
"</html>"
        );
        main_panel.add(about_text);
    }
    /*End Overridden Methods*/
}
