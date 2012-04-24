package roge.encodingviewer.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import roge.encodingviewer.gui.encodingpaneldrawers.*;
import roge.gui.RWindow;
import roge.encodingviewer.gui.basedecoders.*;


/**
 * Main window;  Contains all the elements to view the Encoding graph.
 * 
 * @author Nicholas Rogé
 */
public class EncodingWindow extends RWindow{    
    /**I have no idea what this is actually for, but meh.  Here's some BS documentation for it so the warning goes away.*/
    private static final long serialVersionUID = 5589396243291611297L;
    
    private JRadioButtonMenuItem __auto_detect_button;
    private EncodingPanel        __encoding_panel;
    private EncodingInput        __input_field;
    
    
    /*Begin Constructor Methods*/
    /**
     * Initializes the window to its default settings.
     */
    public EncodingWindow(){
        super();
    }
    
    /**
     * Initializes the window to its default settings.
     * 
     * @param title Title of the window to be displayed in the top bar.
     */
    public EncodingWindow(String title){
        super(title);
    }
    /*End Constructor Methods*/
    
    /*Start Overridden Methods*/   
    @Override protected void _addContent(final JPanel main_panel){
        GridBagConstraints constraints=null;
        
        
        /*Create the...  Panel?*/
        main_panel.setBorder(new EmptyBorder(5,5,5,5));
        main_panel.setBackground(Color.BLACK);
        main_panel.setLayout(new GridBagLayout());
            /*Set the default constraints*/
            constraints=new GridBagConstraints();
            constraints.fill=GridBagConstraints.BOTH;
            
            /*Create the panel for teh graph*/
            this.getEncodingPanel().setBackground(Color.WHITE);
            this.getEncodingPanel().changeEncodingDrawer(new NonReturnToZeroDrawer()); //Because of how the JMenu system works, this <i>should</i> be set up at runtime.  This is just a precaution.  :3
                constraints.anchor=GridBagConstraints.FIRST_LINE_START;
                constraints.weightx=1;
                constraints.weighty=1;
                constraints.gridx=0;
                constraints.gridy=0;
            main_panel.add(this.getEncodingPanel(),constraints);
            
                /*Create the input for the string to be encoded*/
            this.getInputField().setBorder(new EmptyBorder(10,0,0,0));
            this.getInputField().setAlignmentY(JTextArea.CENTER_ALIGNMENT);
            this.getInputField().getInputRegion().addTextUpdateListener(this.getEncodingPanel());
            this.getInputField().getInputRegion().addCaretListener(this.getEncodingPanel());
            this.getInputField().getInputRegion().setFont(new Font("Arial",Font.PLAIN,60));
                constraints.weightx=1;
                constraints.weighty=0;
                constraints.gridx=0;
                constraints.gridy=1;
            main_panel.add(this.getInputField(),constraints);
    }
    
    @Override protected void _addMenu(final JFrame frame){
        JMenuBar menu_bar=null;
        JMenu file,preferences,help,encoding_mode,encoding_base=null;
        JMenuItem menu_item=null;
        JCheckBoxMenuItem checkbox_menu_item=null;
        JRadioButtonMenuItem radiobutton_menu_item=null;
        ButtonGroup button_group=null;
        
        
        /*Create the MenuBar*/
        menu_bar=new JMenuBar();
            //M:File
            file=new JMenu("File");
            file.setMnemonic('f');
                //MI:File->Exit
                menu_item=new JMenuItem("Exit");
                menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));
                menu_item.addActionListener(new ActionListener(){
                    @Override public void actionPerformed(ActionEvent event){
                        frame.setVisible(false);
                        frame.dispose();
                    }
                });
                file.add(menu_item);
            menu_bar.add(file);
            
            preferences=new JMenu("Preferences");
                checkbox_menu_item=new JCheckBoxMenuItem("Enable Bitstuffing");
                checkbox_menu_item.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        boolean checked=false;
                        
                        
                        checked=((JCheckBoxMenuItem)event.getSource()).isSelected();
                        EncodingWindow.this.getEncodingPanel().setBitStuffingEnabled(checked);
                    }
                });
                preferences.add(checkbox_menu_item);
                
                checkbox_menu_item=new JCheckBoxMenuItem("Disable Axes");
                checkbox_menu_item.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        boolean checked=false;
                        
                        
                        checked=((JCheckBoxMenuItem)event.getSource()).isSelected();
                        EncodingWindow.this.getEncodingPanel().setAxesEnabled(!checked);
                    }
                });
                preferences.add(checkbox_menu_item);
                
                checkbox_menu_item=new JCheckBoxMenuItem("Disable Bit Interval Markers");
                checkbox_menu_item.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        boolean checked=false;
                        
                        
                        checked=((JCheckBoxMenuItem)event.getSource()).isSelected();
                        EncodingWindow.this.getEncodingPanel().setBitSeparatorsEnabled(!checked);
                    }
                });
                preferences.add(checkbox_menu_item);
                
                //M:Encoding Mode
                
                encoding_mode=new JMenu("Encoding Mode");
                encoding_mode.setMnemonic('m');
                button_group=new ButtonGroup();
                    radiobutton_menu_item=new JRadioButtonMenuItem("Non-Return to Zero");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            EncodingWindow.this.getEncodingPanel().changeEncodingDrawer(new NonReturnToZeroDrawer());                        
                        }
                    });
                    encoding_mode.add(radiobutton_menu_item);
                    radiobutton_menu_item.setSelected(true);
                    button_group.add(radiobutton_menu_item);
                    
                    radiobutton_menu_item=new JRadioButtonMenuItem("Non-Return to Zero Inverted");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            EncodingWindow.this.getEncodingPanel().changeEncodingDrawer(new NonReturnToZeroInvertedDrawer());                        
                        }
                    });
                    encoding_mode.add(radiobutton_menu_item);
                    button_group.add(radiobutton_menu_item);
                        
                    radiobutton_menu_item=new JRadioButtonMenuItem("Return to Zero");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            EncodingWindow.this.getEncodingPanel().changeEncodingDrawer(new ReturnToZeroDrawer());
                        }
                    });
                    encoding_mode.add(radiobutton_menu_item);
                    button_group.add(radiobutton_menu_item);
                    
                    radiobutton_menu_item=new JRadioButtonMenuItem("Machester");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            EncodingWindow.this.getEncodingPanel().changeEncodingDrawer(new ManchesterDrawer());
                        }
                    });
                    encoding_mode.add(radiobutton_menu_item);
                    button_group.add(radiobutton_menu_item);
                    
                    radiobutton_menu_item=new JRadioButtonMenuItem("Differential Machester");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            EncodingWindow.this.getEncodingPanel().changeEncodingDrawer(new DifferentialManchesterDrawer());
                        }
                    });
                    encoding_mode.add(radiobutton_menu_item);
                    button_group.add(radiobutton_menu_item);
                preferences.add(encoding_mode);
                
              //M:Encoding Base
                encoding_base=new JMenu("Encoding Base");
                encoding_base.setMnemonic('b');
                button_group=new ButtonGroup();
                    this.__auto_detect_button=new JRadioButtonMenuItem("Auto-Detect");
                    this.__auto_detect_button.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,ActionEvent.CTRL_MASK));
                    this.__auto_detect_button.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            AutoDetectDecoder decoder=null;
                            
                            
                            if(event.getStateChange()==ItemEvent.SELECTED){
                                decoder=new AutoDetectDecoder();
                                decoder.setLastDecoder(EncodingWindow.this.getInputField().getCurrentDecoder());
                                decoder.addDecoder(new BinaryDecoder());
                                decoder.addDecoder(new OctalDecoder());
                                decoder.addDecoder(new HexadecimalDecoder());
                                
                                EncodingWindow.this.getInputField().setDecoder(decoder);
                                EncodingWindow.this.getEncodingPanel().repaint();
                            }
                        }
                    });
                    encoding_base.add(this.__auto_detect_button);
                    button_group.add(this.__auto_detect_button);
                    this.__auto_detect_button.setSelected(true);
                    
                    radiobutton_menu_item=new JRadioButtonMenuItem("Binary");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            if(event.getStateChange()==ItemEvent.SELECTED){
                                EncodingWindow.this.getInputField().setDecoder(new BinaryDecoder());
                                EncodingWindow.this.getEncodingPanel().repaint();
                                
                                EncodingWindow.this.__auto_detect_button.setEnabled(true);
                            }
                        }
                    });
                    encoding_base.add(radiobutton_menu_item);
                    button_group.add(radiobutton_menu_item);
                        
                    radiobutton_menu_item=new JRadioButtonMenuItem("Octal");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            if(event.getStateChange()==ItemEvent.SELECTED){
                                EncodingWindow.this.getInputField().setDecoder(new OctalDecoder());
                                EncodingWindow.this.getEncodingPanel().repaint();
                                
                                if(EncodingWindow.this.getInputField().isLocked()){
                                    EncodingWindow.this.__auto_detect_button.setEnabled(false);
                                }else{
                                    EncodingWindow.this.__auto_detect_button.setEnabled(true);
                                }
                            }
                        }
                    });
                    encoding_base.add(radiobutton_menu_item);
                    button_group.add(radiobutton_menu_item);
                    
                    radiobutton_menu_item=new JRadioButtonMenuItem("Hexidecimal");
                    radiobutton_menu_item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
                    radiobutton_menu_item.addItemListener(new ItemListener(){
                        @Override public void itemStateChanged(ItemEvent event){
                            if(event.getStateChange()==ItemEvent.SELECTED){
                                EncodingWindow.this.getInputField().setDecoder(new HexadecimalDecoder());
                                EncodingWindow.this.getEncodingPanel().repaint();
                                
                                if(EncodingWindow.this.getInputField().isLocked()){
                                    EncodingWindow.this.__auto_detect_button.setEnabled(false);
                                }else{
                                    EncodingWindow.this.__auto_detect_button.setEnabled(true);
                                }
                            }
                        }
                    });
                    encoding_base.add(radiobutton_menu_item);
                    button_group.add(radiobutton_menu_item);
                preferences.add(encoding_base);
            menu_bar.add(preferences);
            
            help=new JMenu("Help");
                //MI:About
                menu_item=new JMenuItem("About");
                menu_item.addActionListener(new ActionListener(){
                    @Override public void actionPerformed(ActionEvent event){
                        //AboutWindow window=new AboutWindow(Resources.Strings.app_title);
                        //window.setVisible(true);
                    }
                });
                help.add(menu_item);
            menu_bar.add(help);
        frame.setJMenuBar(menu_bar);
    }
    /*End Overridden Methods*/
    
    /*Begin Getters Methods*/
    /**
     * Gets the data for the private __encoding_panel member.
     * 
     * @return The data for the private __encoding_panel member.
     */
    public EncodingPanel getEncodingPanel(){
        if(this.__encoding_panel==null){
            this.__encoding_panel=new EncodingPanel();
        }
        
        return this.__encoding_panel;
    }
    
    /**
     * Gets the data for the private __input_panel member.
     * 
     * @return The data for the private __input_panel member.
     */
    public EncodingInput getInputField(){
       if(this.__input_field==null){
           this.__input_field=new EncodingInput();
       }
       
       return this.__input_field;
    }
    /*End Getter Methods*/
}
