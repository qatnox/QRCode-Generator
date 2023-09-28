package UI;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeUI implements ActionListener {
    JFrame frame = new JFrame("QRCode Generator");
    JPanel funcPanel = new JPanel();
    JPanel qrCodePanel = new JPanel();
    JButton generateButton = new JButton("Generate");
    JButton clearButton = new JButton("Clear");
    JLabel mainText = new JLabel("<html>QR-Code<br>Generator</html>");
    JLabel textLabel = new JLabel("Enter the text to encode");
    JLabel dirLabel = new JLabel("<html> QRCodes will be saved to <br>'D:/QRCodes' </html>");
    JLabel background;
    JTextField textField = new JTextField();
    BufferedImage image;
    static int counter = 0;

    {
        try {
            image = ImageIO.read(new File("src/backgr.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Font font = new Font("Consolas", Font.BOLD, 15);
    static final String ERROR_TEXT = "Unable to generate QR-Code";


    public void setPanel(){
        funcPanel.setBounds(0, 0, 250, 550);
        funcPanel.setBackground(Color.green);
        funcPanel.setLayout(null);

        qrCodePanel.setBackground(Color.white);
        qrCodePanel.setLayout(null);
    }

    public void setLabels(){
        mainText.setBounds(20, 20, 210, 90);
        mainText.setFont(new Font("Montserrat", Font.BOLD, 40));

        textLabel.setBounds(20, 180, 210, 20);
        textLabel.setFont(new Font("Century Gothic", Font.BOLD, 17));

        dirLabel.setBounds(350, 200, 300, 50);
        dirLabel.setFont(new Font("Century Gothic", Font.BOLD, 23));
        dirLabel.setForeground(Color.black);

        background = new JLabel(new ImageIcon(image));
        background.setSize(250, 550);
    }

    public void setFields(){
        textField.setBounds(20, 210, 210, 25);
        textField.setForeground(Color.black);
        textField.setFont(font);
        textField.setBorder(null);
    }

    public void setButtons(){
        generateButton.setBounds(20, 250, 120, 35);
        generateButton.setFont(font);
        generateButton.setFocusable(false);
        generateButton.setOpaque(false);
        generateButton.addActionListener(this);

        clearButton.setBounds(150, 250, 80, 35);
        clearButton.setFont(font);
        clearButton.setForeground(Color.red);
        clearButton.setFocusable(false);
        clearButton.setOpaque(false);
        clearButton.addActionListener(this);
    }

    public void setComponents(){


        funcPanel.add(mainText);
        funcPanel.add(textLabel);
        funcPanel.add(textField);

        funcPanel.add(generateButton);
        funcPanel.add(clearButton);
        funcPanel.add(background);

        qrCodePanel.add(dirLabel);
        frame.add(funcPanel);
        frame.add(qrCodePanel);
    }

    public void setFrame(){
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 550);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == generateButton) {

            if (textField.getText().isEmpty()) {
                System.out.println(ERROR_TEXT);
            } else {

                String qrCodeText = textField.getText();
                BufferedImage qrCodeImage = generateQRCode(qrCodeText);
                counter++;
                File qrCodePhoto = new File("D:\\QRCodes\\QRCode" + counter + ".png");
                try {
                    ImageIO.write(qrCodeImage, "png", qrCodePhoto);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JLabel qrCodeLabel = new JLabel(new ImageIcon(qrCodeImage));
                qrCodeLabel.setBounds(250, -20, 550, 550);
                qrCodePanel.removeAll();
                qrCodePanel.setOpaque(false);
                qrCodePanel.add(qrCodeLabel);
                qrCodePanel.revalidate();
                qrCodePanel.repaint();
            }
        }
        if(e.getSource() == clearButton)
            textField.setText("");
    }

    public static BufferedImage generateQRCode(String qrCodeText){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 550, 550);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
