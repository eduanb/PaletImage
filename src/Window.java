import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


public class Window extends JFrame implements ActionListener
{
    //=============================================================== Private variables ==========================
    //Must be here for JFrame Class
    private static final long serialVersionUID = 1L;

    //Main window where everything will be displayed in
    private  JPanel window = null;
    //Status bar at bottom
    private JLabel status = null;
    //Menu bar that holds the menu items
    JMenuBar menuBar = null;
    //File menu
    JMenu fileMenu;
    //Options Menu
    JMenu optionsMenu;
    //New Game Button
    JMenuItem newGameButton;
    //Exit Button
    JMenuItem exitButton;
    //Depth menu Button
    private JMenuItem depthButton = null;
    //Radio Button to toggle PVP
    JRadioButtonMenuItem pvpRadioButton;
    //Menu button to toggle AB pruning
    public JRadioButtonMenuItem abRadioButtton;
    //=============================================================== Getters + Setters ==========================
    public JMenuItem getDepthButton()
    {
        return depthButton;
    }
    public void setDepthButton(JMenuItem depthButton)
    {
        this.depthButton = depthButton;
    }
    public JLabel getStatus()
    {
        return status;
    }
    public void setStatus(JLabel status)
    {
        this.status = status;
    }

    //=============================================================== Constructor ==========================
    public Window()
    {
        //Set window name
        super("Eduan Bekker 12214834");
        //Create all the GUI elements
        window = new JPanel();
        status = new JLabel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        optionsMenu = new JMenu("Options");
        newGameButton = new JMenuItem("New Game");
        exitButton = new JMenuItem("Exit");
        pvpRadioButton = new JRadioButtonMenuItem("PVP");
        abRadioButtton = new JRadioButtonMenuItem("AB Pruning");

        //Add Window and Status bar
        getContentPane().add(window, BorderLayout.CENTER);
        getContentPane().add(status, BorderLayout.SOUTH);

        //Set the alignment of the text within status bar to center
        status.setHorizontalAlignment(JLabel.CENTER);

        //Set the menu Bar
        setJMenuBar(menuBar);

        //Add Items to the menu
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        fileMenu.add(newGameButton);
        fileMenu.add(exitButton);
        optionsMenu.add(abRadioButtton);
        optionsMenu.add(pvpRadioButton);

        //Set default values to radio buttons
        pvpRadioButton.setSelected(true);
        abRadioButtton.setSelected(true);

        //Add  events to on click of menu items
        addListensers();
        //Add action to close button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //make this JFrame visible
        setVisible(true);


        Image image = null;
        try {
            image = ImageIO.read(new File("test2.bmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(image);

        JLabel label = new JLabel(icon);
        window.add(label);
        this.setSize(800,600);
    }
    //=============================================================== addListensers ==========================
    private void addListensers()
    {
        //Listener for new game
        //Show popup and validates input
        //calls newGame to start a new game
        newGameButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int i = 0;
                while(i < 4 || i > 20)
                {
                    i = Integer.parseInt(showInputDialog("Size of grid?"));
                }
            }
        });
        //========================================================================
        //exits whole program
        exitButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                setVisible(false); //you can't see me!
                dispose(); //Destroy the JFrame object
            }
        });
        //========================================================================
        //changes pvp in settings
        pvpRadioButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {

            }
        });
        //========================================================================
        //changes the heuristic between simple minimax and minimax with AB pruning
        abRadioButtton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {

            }
        });
    }

    //=============================================================== showInputDialog ==========================
    //recursive function to make sure that only integer values were input
    //paramater is what to display
    private String showInputDialog(String display)
    {
        String inputValue = JOptionPane.showInputDialog(window,display);
        if(inputValue == null || inputValue.isEmpty() || !inputValue.matches("[0-9]*"))
        {
            inputValue = showInputDialog(display);
        }
        return inputValue;
    }


    //=============================================================== actionPerformed ==========================
    public void actionPerformed(ActionEvent e)
    {
        //Required from parent JFrame class
    }
}