package Ventanas;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
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
    private String comandos = "INICIO: Abre la ventana principal.\n"
            + "ENCENDER IMPRESORA: Enciende la impresora. \n"
            + "APAGAR IMPRESORA: Apaga la impresora. \n"
            + "IMPRIMIR: Manda a immprimir el texto a la impresora. \n"
            + "CANCELAR: Cierra la ventana.  \n"
            + "GUARDAR: Guarda el archivo actual. \n"
            + "AYUDA: Abre la ventana de Ayuda. \n"
            + "SALIR: Finaliza la ejecucion del programa. \n";
   
    public Ayuda(JFrame frame){
        super(frame, true);
        setTitle("Ayuda");
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
        txtComandos.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        txtComandos.setLocation((ventana.width - txtComandos.getWidth()) / 2, (ventana.height - txtComandos.getHeight()) * 95 / 100);
        txtComandos.setEditable(false);
        txtComandos.setOpaque(false);
        txtComandos.setText(comandos);
        getContentPane().add(txtComandos);
        
        
        //Inicializar el fondo
        fondo = new JLabel();
        fondo.setSize(ventana.width, ventana.height);
        ImageIcon imagenFondo = new ImageIcon(getClass().getResource("../Imagenes/Fondo.jpg"));
        Icon iconoFondo = new ImageIcon(imagenFondo.getImage().getScaledInstance(fondo.getWidth(), fondo.getHeight(), Image.SCALE_DEFAULT));
        fondo.setIcon(iconoFondo);
        getContentPane().add(fondo);
    }
}
