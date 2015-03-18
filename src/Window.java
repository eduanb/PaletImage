import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Window extends JFrame implements ActionListener
{
    //=============================================================== Private variables ==========================
    //Must be here for JFrame Class
    private static final long serialVersionUID = 1L;

    //Main window where everything will be displayed in
    private  JPanel window = null;
    //Menu bar that holds the menu items
    JMenuBar menuBar = null;
    //File menu
    JMenu fileMenu;
    //Options Menu
    JMenu optionsMenu;
    //Open Button
    JMenuItem openBMPButton;
    //Open Button
    JMenuItem openPalletButton;
    //Convert Button
    JMenuItem convertButton;
    //Exit Button
    JMenuItem exitButton;
    //Depth menu Button
    private JMenuItem depthButton = null;
    //Radio Button to toggle PVP
    JRadioButtonMenuItem pvpRadioButton;
    //Menu button to toggle AB pruning
    public JRadioButtonMenuItem abRadioButtton;
    //Image to display the BMP in
    JLabel BMPImage = null;
    //Image to display the PBI in
    JLabel PBIImage = null;
    //BMP File
    File BMPFile = null;
    //PHI File
    File PBIFile = null;
    //PL File
    File PLFile = null;
    //=============================================================== Getters + Setters ==========================
    public JMenuItem getDepthButton()
    {
        return depthButton;
    }
    public void setDepthButton(JMenuItem depthButton)
    {
        this.depthButton = depthButton;
    }

    //=============================================================== Constructor ==========================
    public Window()
    {
        //Set window name
        super("Eduan Bekker 12214834");
        //Create all the GUI elements
        window = new JPanel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        optionsMenu = new JMenu("Options");
        openBMPButton = new JMenuItem("Open BMP");
        openPalletButton = new JMenuItem("Open Pallet Image");
        convertButton = new JMenuItem("Convert");
        exitButton = new JMenuItem("Exit");
        pvpRadioButton = new JRadioButtonMenuItem("PVP");
        abRadioButtton = new JRadioButtonMenuItem("AB Pruning");

        //Add Window and Status bar
        getContentPane().add(window, BorderLayout.WEST);

        //Set the menu Bar
        setJMenuBar(menuBar);

        //Add Items to the menu
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        fileMenu.add(openBMPButton);
        fileMenu.add(openPalletButton);
        fileMenu.add(convertButton);
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
        BMPFile = new File("test2.bmp");
        BMPImage = setBMPImage(BMPFile);
        window.add(BMPImage);


        PBIFile = new File("image.pbi");
        PLFile = new File("pallet.pl");
        PBIReader reader = new PBIReader();
        BufferedImage img = reader.getBufferedImage(PBIFile,PLFile);
        PBIImage = setBufferedImage(img);
        window.add(PBIImage);

        this.setSize(10 + (BMPImage.getWidth() * 2),BMPImage.getHeight() + 70);
    }
    private JLabel setBMPImage(File file)
    {
        try {
            Image image = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(image);

            JLabel label = new JLabel(icon);
            label.setSize(image.getWidth(null),image.getHeight(null));
            return label;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private JLabel setBufferedImage(BufferedImage file)
    {
        ImageIcon icon = new ImageIcon(file);

        JLabel label = new JLabel(icon);
        label.setSize(file.getWidth(),file.getHeight());
        return label;
    }
    //=============================================================== addListensers ==========================
    private void addListensers()
    {
        //Listener for new game
        //Show popup and validates input
        //calls newGame to start a new game
        openBMPButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                final JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "bmp"));
                int returnVal = fc.showOpenDialog(window);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    BMPFile = fc.getSelectedFile();
                    window.remove(BMPImage);
                    window.remove(PBIImage);
                    BMPImage = setBMPImage(BMPFile);
                    window.add(BMPImage);
                    window.add(PBIImage);
                    window.updateUI();
                } else {
                    System.out.println("Canceled by user");
                }
            }
        });

        openPalletButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "pbi"));
                fc.setDialogTitle("Select .pbi");
                int returnVal = fc.showOpenDialog(window);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    PBIFile = fc.getSelectedFile();
                    fc.setDialogTitle("Select .pl");
                    returnVal = fc.showOpenDialog(window);
                    fc.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "pl"));
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        PLFile = fc.getSelectedFile();
                        PBIReader reader = new PBIReader();
                        BufferedImage img = reader.getBufferedImage(PBIFile,PLFile);
                        //TODO
                        window.remove(BMPImage);
                        BMPImage = setBufferedImage(img);
                        window.add(BMPImage);
                        window.updateUI();
                    }else {
                        System.out.println("Canceled [pl] by user");
                    }
                } else {
                    System.out.println("Canceled [pbi] by user");
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
        //exits whole program
        convertButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                ImageConverter ic = new ImageConverter(BMPFile);
                window.remove(PBIImage);
                PBIFile = new File("image.pbi");
                PLFile = new File("pallet.pl");
                PBIReader reader = new PBIReader();
                BufferedImage img = reader.getBufferedImage(PBIFile,PLFile);
                PBIImage = setBufferedImage(img);
                window.remove(PBIImage);
                window.add(PBIImage);
                window.updateUI();
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