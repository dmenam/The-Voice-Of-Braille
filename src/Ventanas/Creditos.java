package Ventanas;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Creditos extends JDialog {

    private JLabel titulo;
    private JLabel fondo;
    private JLabel creditosAplicacion;
    private JLabel credito1;
    private JLabel credito2;
    private JLabel creditosFondo;
    private JLabel linkFondo;

    public Creditos(JFrame frame) {
        super(frame, true);
        setTitle("Creditos");
        //Icono
        setIconImage(new ImageIcon(getClass().getResource("../Imagenes/Icono_VoB.png")).getImage());
        //Tamaño de la pantalla
        Dimension pantalla;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        //Tamaño de la ventana
        setSize((pantalla.width * 65) / 100, (pantalla.height * 80) / 100);
        setResizable(false);
        Dimension ventana = getSize();
        //Inicializar los componentes internos de la ventana
        inicializarComponentes(ventana);

        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarComponentes(Dimension ventana) {
        titulo = new JLabel("Creditos");
        titulo.setBounds(10, 10, ventana.width, ventana.height * 10 / 100);
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 38));
        titulo.setLocation((ventana.width - titulo.getWidth()) / 2, (ventana.height - titulo.getHeight()) * 5 / 100);
        getContentPane().add(titulo);

        creditosAplicacion = new JLabel();
        creditosAplicacion.setText("Desarrollado por:");
        creditosAplicacion.setBounds(10, 10, ventana.width, ventana.height * 10 / 100);
        creditosAplicacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        creditosAplicacion.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        creditosAplicacion.setLocation((ventana.width - creditosAplicacion.getWidth()) * 20 / 100, (ventana.height - creditosAplicacion.getHeight()) * 15 / 100);
        getContentPane().add(creditosAplicacion);

        credito1 = new JLabel();
        credito1.setText("David Uzziel Mena Maldonado");
        credito1.setBounds(10, 10, ventana.width, ventana.height * 10 / 100);
        credito1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        credito1.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        credito1.setLocation((ventana.width - credito1.getWidth()) * 20 / 100, (ventana.height - credito1.getHeight()) * 25 / 100);
        getContentPane().add(credito1);

        credito2 = new JLabel();
        credito2.setText("Luis Roberto Carlos Reyes Rayas");
        credito2.setBounds(10, 10, ventana.width, ventana.height * 10 / 100);
        credito2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        credito2.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        credito2.setLocation((ventana.width - credito2.getWidth()) * 20 / 100, (ventana.height - credito2.getHeight()) * 30 / 100);
        getContentPane().add(credito2);

        creditosFondo = new JLabel();
        creditosFondo.setText("Fondo de:");
        creditosFondo.setBounds(10, 10, ventana.width, ventana.height * 10 / 100);
        creditosFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        creditosFondo.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        creditosFondo.setLocation((ventana.width - creditosFondo.getWidth()) * 20 / 100, (ventana.height - creditosFondo.getHeight()) * 65 / 100);
        getContentPane().add(creditosFondo);

        linkFondo = new JLabel();
        //<a href=\"https://www.freepik.com/free-photos-vectors/background\">Background vector created by dotstudio - www.freepik.com</a>
        linkFondo.setText("https://www.freepik.com/free-photos-vectors/background/");
        linkFondo.setForeground(Color.blue);
        linkFondo.setBounds(10, 10, ventana.width, ventana.height * 10 / 100);
        linkFondo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        //Fuente de subrayado--------------------------------------------------//
        Font subrayado = linkFondo.getFont();
        Map attributes = subrayado.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        //---------------------------------------------------------------------//
        linkFondo.setFont(subrayado.deriveFont(attributes));
        linkFondo.setLocation((ventana.width - linkFondo.getWidth()) * 20 / 100, (ventana.height - linkFondo.getHeight()) * 75 / 100);
        linkFondo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (java.awt.Desktop.isDesktopSupported()) {
                    try {
                        Desktop dk = Desktop.getDesktop();
                        dk.browse(new URI("https://www.freepik.com/free-photos-vectors/background/"));
                    } catch (IOException ex) {
                        Logger.getLogger(Creditos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(Creditos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e){
                linkFondo.setForeground(new Color(50, 143, 194));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                linkFondo.setForeground(Color.BLUE);
            }
        });
        
        getContentPane().add(linkFondo);

        //Inicializar el fondo
        fondo = new JLabel();
        fondo.setSize(ventana.width, ventana.height);
        ImageIcon imagenFondo = new ImageIcon(getClass().getResource("../Imagenes/Fondo.jpg"));
        Icon iconoFondo = new ImageIcon(imagenFondo.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), Image.SCALE_DEFAULT));
        fondo.setIcon(iconoFondo);
        getContentPane().add(fondo);
    }

}
