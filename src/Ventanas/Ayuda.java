package Ventanas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Ayuda extends JDialog{
    
    private JLabel titulo;
    private JLabel tituloComandos;
    private JLabel fondo;
    
    private JTextArea txtComandos;
    
    private String tituloC = "Comandos disponibles";
    private String comandos = "Encender impresora: Habilita el uso de la impresora. \n"
            + "Apagar impresora: Deshabilita el uso de la impresora. \n"
            + "Imprimir archivo de texto: Imprime el texto que hay en el campo de texto \n"
            + "Desactivar comandos de Voz: Desactiva los comandos de voz \n"
            + "Activar comandos de Voz: Activa los comandos de voz\n"
            + "Guardar archivo de Texto: Abre la ventana para guardar el texto introducido en el campo de texto \n"
            + "Salir de la aplicacion: Finaliza la ejecución del programa \n"
            + "Leer texto introducido: Lee el texto de la pantalla principal\n"
            + "Iniciar dictado por voz: Inicia el dictado por voz.\n"
            + "\n NOTA: EL DICTADO DEBE SER SEGUIDO, SI SE HACE UNA PAUSA"
            + " PROLONGADA, DEBERA VOLVER A COMENZAR EL DICTADO "
            + " POR VOZ.\n";
   
    public Ayuda(JFrame frame){
        super(frame, true);
        setTitle("Ayuda");
        //Icono
        //setIconImage(new ImageIcon(getClass().getResource("../Imagenes/Icono_VoB.png")).getImage());
        try {
            BufferedImage imglogo;
            imglogo = ImageIO.read(getClass().getClassLoader().getResource("Imagenes/Icono_VoB.png"));
            setIconImage(imglogo);
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        titulo = new JLabel("Ayuda");
        titulo.setBounds(10, 10, ventana.width, ventana.height * 10 / 100);
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 38));
        titulo.setLocation((ventana.width - titulo.getWidth()) / 2, (ventana.height - titulo.getHeight()) * 5 / 100);
        getContentPane().add(titulo);
        
        tituloComandos = new JLabel();
        tituloComandos.setBounds(10, 10, ventana.width * 95 / 100, ventana.height * 5 / 100);
        tituloComandos.setFont(new Font("Times New Roman", Font.BOLD, 38));
        tituloComandos.setLocation((ventana.width - tituloComandos.getWidth()) / 2, (ventana.height - tituloComandos.getHeight()) * 15 / 100);
        tituloComandos.setOpaque(false);
        tituloComandos.setText(tituloC);
        getContentPane().add(tituloComandos);
        
        txtComandos = new JTextArea();
        txtComandos.setBounds(10, 10, ventana.width * 95 / 100, ventana.height * 75 / 100);
        txtComandos.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        txtComandos.setLocation((ventana.width - txtComandos.getWidth()) / 2, (ventana.height - txtComandos.getHeight()) * 95 / 100);
        txtComandos.setEditable(false);
        txtComandos.setOpaque(false);
        txtComandos.setLineWrap(true);
        txtComandos.setWrapStyleWord(true);
        txtComandos.setText(comandos);
        getContentPane().add(txtComandos);
        
        
        //Inicializar el fondo
        fondo = new JLabel();
        fondo.setSize(ventana.width, ventana.height);
        //ImageIcon imagenFondo = new ImageIcon(getClass().getResource("../Imagenes/Fondo.jpg"));
        URL urlDelFondo = Splash.class.getClassLoader().getResource("Imagenes/fondo.jpg");
        ImageIcon imagenFondo = new ImageIcon(urlDelFondo);
        Icon iconoFondo = new ImageIcon(imagenFondo.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), Image.SCALE_DEFAULT));
        fondo.setIcon(iconoFondo);
        getContentPane().add(fondo);
    }
}
