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
import javax.swing.JProgressBar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Splash extends JFrame{
    
    private JLabel titulo, imagenLb, Fondo;
    private JProgressBar progreso;
    
    public Splash(){
        try {
            //Icono
            //URL urlDeLaImagenIcono = Splash.class.getClassLoader().getResource("Imagenes/Icono_VoB.png");
            //setIconImage(new ImageIcon(getClass().getResource("../Imagenes/Icono_VoB.png")).getImage());
            BufferedImage imglogo = ImageIO.read(getClass().getClassLoader().getResource("Imagenes/Icono_VoB.png"));
            setIconImage(imglogo);
        } catch (IOException ex) {
            Logger.getLogger(Splash.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        //Tamaño de la pantalla
        Dimension pantalla; 
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        //Tamaño de la ventana
        setSize((pantalla.width*65)/100, (pantalla.height*80)/100);
        Dimension ventana = getSize();
        getContentPane().setLayout(null);
        //Inicializar componentes de la ventana
        inicializar(ventana);
        
        setLocationRelativeTo(null);
        setVisible(true);
        //Carga de la barra de progreso
        iniciarHilo();
    }
    private void inicializar(Dimension ventana){        
        titulo = new JLabel("The Voice Of Braille");
        titulo.setSize((ventana.width*60)/100, (ventana.height*10)/100);
        titulo.setLocation((ventana.width-titulo.getWidth())*55/100, (ventana.height-titulo.getHeight())*5/100);
        titulo.setFont(new Font("Tahoma", Font.PLAIN, 38));
        getContentPane().add(titulo);
        
        imagenLb = new JLabel();
        imagenLb.setSize((ventana.width*40)/100, (ventana.height*50)/100);
        imagenLb.setLocation((ventana.width-imagenLb.getWidth())*55/100, (ventana.height-imagenLb.getHeight())*40/100);
        URL urlDeLaImagen = Splash.class.getClassLoader().getResource("Imagenes/Icono_VoB.png");
        //ImageIcon imagen = new ImageIcon(getClass().getResource("../Imagenes/Icono_VoB.png"));
        ImageIcon imagen = new ImageIcon(urlDeLaImagen);
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(imagenLb.getWidth(), imagenLb.getHeight(), Image.SCALE_DEFAULT));
        imagenLb.setIcon(icono);
        getContentPane().add(imagenLb);        
        
        progreso = new JProgressBar();
        progreso.setSize((ventana.width*80)/100, (ventana.height*10)/100);
        progreso.setLocation((ventana.width-progreso.getWidth())*50/100, (ventana.height-progreso.getHeight())*80/100);
        getContentPane().add(progreso);
        
        Fondo = new JLabel();
        Fondo.setSize(ventana.width, ventana.height);
        //ImageIcon imagenFondo = new ImageIcon(getClass().getResource("../Imagenes/Fondo.jpg"));
        URL urlDeLaImagen2 = Splash.class.getClassLoader().getResource("Imagenes/fondo.jpg");
        ImageIcon imagenFondo = new ImageIcon(urlDeLaImagen2);
        Icon iconoFondo = new ImageIcon(imagenFondo.getImage().getScaledInstance(Fondo.getWidth(), Fondo.getHeight(), Image.SCALE_DEFAULT));
        Fondo.setIcon(iconoFondo);
        getContentPane().add(Fondo);
    }
    private void iniciarHilo(){
        Thread hilo = new Thread(new Runnable() {
            int carga = 0;
            @Override
            public void run() {
                try {
                    while(carga<100){
                        progreso.setValue(carga);
                        carga+=5;
                        Thread.sleep(100);
                    }
                    dispose();
                    Inicio i = new Inicio();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });
        hilo.start();
    }
}
