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
    JMenu ditheringMenu;
    //Options Menu
    JMenu quantificationMenu;

    //Options Menu
    JMenu palletSizeMenu;
    //Open Button
    JMenuItem openBMPButton;
    //Open Button
    JMenuItem openPalletButton;
    //Convert Button
    JMenuItem convertButton;
    //Exit Button
    JMenuItem exitButton;
    JRadioButtonMenuItem truncationRadioButton;
    JRadioButtonMenuItem simpleDitheringRadioButton;
    JRadioButtonMenuItem popularityRadioButton;
    JRadioButtonMenuItem medianCutRadioButton;

    JRadioButtonMenuItem pallet8RadioButton;
    JRadioButtonMenuItem pallet16RadioButton;
    JRadioButtonMenuItem pallet32RadioButton;
    JRadioButtonMenuItem pallet64RadioButton;
    JRadioButtonMenuItem pallet128RadioButton;
    JRadioButtonMenuItem pallet256RadioButton;
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
    ImageConverter imageConverter;
    Dithering dithering;
    Quantization quantization;
    int palletSize = 256;
    //=============================================================== Constructor ==========================
    public Window()
    {
        //Set window name
        super("Eduan Bekker 12214834");
        //Create all the GUI elements
        window = new JPanel();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        ditheringMenu = new JMenu("Dithering");
        palletSizeMenu = new JMenu("Pallet Size");
        quantificationMenu = new JMenu("Quantification");
        openBMPButton = new JMenuItem("Open BMP");
        openPalletButton = new JMenuItem("Open Pallet Image");
        convertButton = new JMenuItem("Convert");
        exitButton = new JMenuItem("Exit");
        truncationRadioButton = new JRadioButtonMenuItem("Truncation");
        simpleDitheringRadioButton = new JRadioButtonMenuItem("Simple Dithering");
        popularityRadioButton = new JRadioButtonMenuItem("Popularity");
        medianCutRadioButton = new JRadioButtonMenuItem("Median Cut");

        pallet8RadioButton = new JRadioButtonMenuItem("8");
        pallet16RadioButton = new JRadioButtonMenuItem("16");
        pallet32RadioButton = new JRadioButtonMenuItem("32");
        pallet64RadioButton = new JRadioButtonMenuItem("64");
        pallet128RadioButton = new JRadioButtonMenuItem("128");
        pallet256RadioButton = new JRadioButtonMenuItem("256");
        //Add Window and Status bar
        getContentPane().add(window, BorderLayout.WEST);

        //Set the menu Bar
        setJMenuBar(menuBar);
        pallet256RadioButton.setSelected(true);
        //Add Items to the menu
        menuBar.add(fileMenu);
        menuBar.add(quantificationMenu);
        menuBar.add(ditheringMenu);
        menuBar.add(palletSizeMenu);
        fileMenu.add(openBMPButton);
        fileMenu.add(openPalletButton);
        fileMenu.add(convertButton);
        fileMenu.add(exitButton);
        ditheringMenu.add(truncationRadioButton);
        ditheringMenu.add(simpleDitheringRadioButton);
        quantificationMenu.add(popularityRadioButton);
        quantificationMenu.add(medianCutRadioButton);

        palletSizeMenu.add(pallet8RadioButton);
        palletSizeMenu.add(pallet16RadioButton);
        palletSizeMenu.add(pallet32RadioButton);
        palletSizeMenu.add(pallet64RadioButton);
        palletSizeMenu.add(pallet128RadioButton);
        palletSizeMenu.add(pallet256RadioButton);

        //Set default values to radio buttons
        truncationRadioButton.setSelected(true);
        popularityRadioButton.setSelected(true);

        //Add  events to on click of menu items
        addListensers();
        //Add action to close button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //make this JFrame visible
        setVisible(true);
        BMPFile = new File("test2.bmp");
        if(BMPFile.exists()) {
            BMPImage = setBMPImage(BMPFile);
            window.add(BMPImage);
        }


        PBIFile = new File("image.pbi");
        PLFile = new File("pallet.pl");
        if(PBIFile.exists() && PLFile.exists()) {
            PBIReader reader = new PBIReader();
            BufferedImage img = reader.getBufferedImage(PBIFile, PLFile);
            PBIImage = setBufferedImage(img);
            window.add(PBIImage);
        }
        this.setSize(10 + (BMPImage.getWidth() * 2),BMPImage.getHeight() + 70);
        imageConverter = new ImageConverter();
        dithering = new Truncation();
        quantization = new Popularity();

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
                imageConverter.ConvertAndSaveBMP(BMPFile,palletSize,quantization,dithering);
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
        truncationRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                truncationRadioButton.setSelected(true);
                simpleDitheringRadioButton.setSelected(false);
                dithering = new Truncation();
            }
        });
        simpleDitheringRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                truncationRadioButton.setSelected(false);
                simpleDitheringRadioButton.setSelected(true);
                dithering = new SimpleDithering();
            }
        });
        popularityRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                popularityRadioButton.setSelected(true);
                medianCutRadioButton.setSelected(false);
                quantization = new Popularity();
            }
        });
        medianCutRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                medianCutRadioButton.setSelected(true);
                popularityRadioButton.setSelected(false);
                quantization = new MedianCut();
            }
        });

        pallet8RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                palletSize = 8;
                pallet8RadioButton.setSelected(true);
                pallet16RadioButton.setSelected(false);
                pallet32RadioButton.setSelected(false);
                pallet64RadioButton.setSelected(false);
                pallet128RadioButton.setSelected(false);
                pallet256RadioButton.setSelected(false);
            }
        });

        pallet16RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                palletSize = 16;
                pallet8RadioButton.setSelected(false);
                pallet16RadioButton.setSelected(true);
                pallet32RadioButton.setSelected(false);
                pallet64RadioButton.setSelected(false);
                pallet128RadioButton.setSelected(false);
                pallet256RadioButton.setSelected(false);
            }
        });
        pallet32RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                palletSize = 32;
                pallet8RadioButton.setSelected(false);
                pallet16RadioButton.setSelected(false);
                pallet32RadioButton.setSelected(true);
                pallet64RadioButton.setSelected(false);
                pallet128RadioButton.setSelected(false);
                pallet256RadioButton.setSelected(false);
            }
        });
        pallet64RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                palletSize = 64;
                pallet8RadioButton.setSelected(false);
                pallet16RadioButton.setSelected(false);
                pallet32RadioButton.setSelected(false);
                pallet64RadioButton.setSelected(true);
                pallet128RadioButton.setSelected(false);
                pallet256RadioButton.setSelected(false);
            }
        });
        pallet128RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                palletSize = 128;
                pallet8RadioButton.setSelected(false);
                pallet16RadioButton.setSelected(false);
                pallet32RadioButton.setSelected(false);
                pallet64RadioButton.setSelected(false);
                pallet128RadioButton.setSelected(true);
                pallet256RadioButton.setSelected(false);
            }
        });
        pallet256RadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                palletSize = 256;
                pallet8RadioButton.setSelected(false);
                pallet16RadioButton.setSelected(false);
                pallet32RadioButton.setSelected(false);
                pallet64RadioButton.setSelected(false);
                pallet128RadioButton.setSelected(false);
                pallet256RadioButton.setSelected(true);
            }
        });

    }

    //=============================================================== showInputDialog ==========================
    //recursive function to make sure that only integer values were input
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