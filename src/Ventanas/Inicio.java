package Ventanas;

import Conexiones.FileManager;
import Conexiones.Arduino;
//import Conexiones.Voz;
import com.panamahitek.ArduinoException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import jssc.SerialPortException;

public class Inicio extends JFrame {

    private JMenuBar menu;
    private JMenu Marchivo, Mconfiguracion, Mayuda;
    private JMenuItem MInuevo, MIabrir, MIguardar, MIguardarC;
    private JMenuItem MIopciones;
    private JMenuItem MIcreditos, MIAyuda;

    private JTextArea texto;

    private Arduino ino;

    private FileManager manager;
    private File archivo;

    private JButton btnLimpiar, btnImprimir, btnGuardar, btnCancelar;
    private JButton btnEscanear, btnConectar;

    private JLabel fondo, titulo;
    private JLabel BTdispositivo;
    private JLabel JLestado;

    private JComboBox CBdispositivosBT;

    private JPanel panel1, panel2;

    private Configuracion configuracion;
    private Creditos creditos;
    private Ayuda ayuda;
    
    //private Voz voz;

    public Inicio() {
        //Tamaño de la pantalla
        Dimension pantalla;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        //Tamaño de la ventana
        setSize(pantalla.width, pantalla.height);
        Dimension ventana = getSize();
        //Icono
        setIconImage(new ImageIcon(getClass().getResource("../Imagenes/Icono_VoB.png")).getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("The Voice of Braille");

        //Inicializar componentes
        inicializar(ventana);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void inicializar(Dimension ventana) {
        //Manager de Archivos
        manager = new FileManager();
        manager.setDirectorioPorDefecto(FileManager.leerConfiguracion(0));
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //Voz
        if(FileManager.leerConfiguracion(3).equals("1")) {
            //voz = new Voz(this);
        }
        //----------------------------------------------------------------------

        titulo = new JLabel("The Voice Of Braille");
        titulo.setSize((ventana.width * 60) / 100, (ventana.height * 15) / 100);
        titulo.setLocation((ventana.width - titulo.getWidth()) * 55 / 100, (ventana.height - titulo.getHeight()) * 5 / 100);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 38));
        getContentPane().add(titulo);

        //Inicio Acomodo del Menu superior
        menu = new JMenuBar();
        menu.setBounds(0, 0, ventana.width, (ventana.height * 3) / 100);

        //Menu de archivos
        Marchivo = new JMenu("Archivo");
        //SubMenus
        MInuevo = new JMenuItem("Nuevo Archivo...          ");
        MInuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    manager.guardarArchivoComo("");
                    archivo = manager.getArchivo();
                    if(archivo != null)
                    {
                        setTitle("The Voice of Braille - " + archivo.getName());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        MInuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        Marchivo.add(MInuevo);
        //---------------------------------------------------------------------//
        MIabrir = new JMenuItem("Abrir Archivo...");
        MIabrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    archivo = manager.abrirAchivo();
                    if(archivo != null) {
                        setTitle("The Voice of Braille - " + archivo.getName());
                        texto.setText(manager.leerArchivo(archivo));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        MIabrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        Marchivo.add(MIabrir);
        //---------------------------------------------------------------------//
        MIguardar = new JMenuItem("Guardar");
        MIguardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (archivo != null) {
                    try {
                        manager.guardarArchivo(archivo.getAbsolutePath(), texto.getText());
                        archivo = manager.getArchivo();
                        setTitle("The Voice of Braille - " + archivo.getName());
                    } catch (IOException ex) {
                        Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        manager.guardarArchivoComo(texto.getText());
                        archivo = manager.getArchivo();
                        if(archivo != null){
                            setTitle("The Voice of Braille - " + archivo.getName());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        MIguardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        Marchivo.add(MIguardar);
        //---------------------------------------------------------------------//
        MIguardarC = new JMenuItem("Guardar Como...");
        MIguardarC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    manager.guardarArchivoComo(texto.getText());
                    archivo = manager.getArchivo();
                    if(archivo != null){
                        setTitle("The Voice of Braille - " + archivo.getName());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Marchivo.add(MIguardarC);
        //---------------------------------------------------------------------//
        menu.add(Marchivo);

        //Menu de Configuracion
        Mconfiguracion = new JMenu("Configuracion");
        //SubMenus
        MIopciones = new JMenuItem("Opciones      ");
        MIopciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                configuracion = new Configuracion(Inicio.this.getMe(),manager, ino);
            }
        });
        Mconfiguracion.add(MIopciones);
        //---------------------------------------------------------------------//

        menu.add(Mconfiguracion);

        //Menu de Ayuda
        Mayuda = new JMenu("Ayuda");
        //SubMenus
        MIAyuda = new JMenuItem("Ayuda");
        MIAyuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ayuda = new Ayuda(Inicio.this.getMe());
            }
        });
        Mayuda.add(MIAyuda);
        //---------------------------------------------------------------------//
        MIcreditos = new JMenuItem("Creditos de la aplicación     ");
        MIcreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                creditos = new Creditos(Inicio.this.getMe());
            }
        });
        Mayuda.add(MIcreditos);
        //---------------------------------------------------------------------//

        menu.add(Mayuda);

        getContentPane().add(menu);
//Fin acomodo del menu superior       

//Acomodo del panel 1 donde se muestra el texto normal
        panel1 = new JPanel();
        panel1.setBounds(0, 0, (ventana.width * 70) / 100, (ventana.height * 75) / 100);
        panel1.setLocation((ventana.width - panel1.getWidth()) * 80 / 100, (ventana.height - panel1.getHeight()) * 60 / 100);
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.setBackground(Color.DARK_GRAY);
        panel1.setLayout(null);

        //Arduino
        ino = new Arduino();
        //----------------------------------------------------------------------

        texto = new JTextArea();
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        JScrollPane scroll = new JScrollPane(texto);
        scroll.setSize(panel1.getWidth() * 95 / 100, (panel1.getHeight() * 85) / 100);
        scroll.setLocation(panel1.getWidth() * 2 / 100, panel1.getHeight() * 2 / 100);
        panel1.add(scroll);

        btnLimpiar = new JButton("Enviar");
        btnLimpiar.setBounds(10, 10, panel1.getWidth() * 20 / 100, (panel1.getHeight() * 5) / 100);
        btnLimpiar.setLocation((panel1.getWidth() - btnLimpiar.getWidth()) * 2 / 100, (panel1.getHeight() - btnLimpiar.getHeight()) * 95 / 100);
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ino.sendData(texto.getText());
                } catch (ArduinoException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SerialPortException ex) {
                    Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel1.add(btnLimpiar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(10, 10, panel1.getWidth() * 20 / 100, (panel1.getHeight() * 5) / 100);
        btnCancelar.setLocation((panel1.getWidth() - btnCancelar.getWidth()) * 30 / 100, (panel1.getHeight() - btnCancelar.getHeight()) * 95 / 100);
        panel1.add(btnCancelar);

        btnImprimir = new JButton("Recibir");
        btnImprimir.setBounds(10, 10, panel1.getWidth() * 20 / 100, (panel1.getHeight() * 5) / 100);
        btnImprimir.setLocation((panel1.getWidth() - btnImprimir.getWidth()) * 95 / 100, (panel1.getHeight() - btnImprimir.getHeight()) * 95 / 100);
        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                texto.append(ino.getDato());
            }
        });
        panel1.add(btnImprimir);
        getContentPane().add(panel1);
//Fin del acomodo del panel 1  

//Acomodo del panel 2 donde se muestra las conexiones Bluetooth        
        panel2 = new JPanel();
        panel2.setBounds(0, 0, (ventana.width * 18) / 100, (ventana.height * 10) / 100);
        panel2.setLocation((ventana.width - panel2.getWidth()) * 3 / 100, (ventana.height - panel2.getHeight()) * 20 / 100);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)), "Dispositivos Bluetooth"));
        panel2.setBackground(new Color(0, 0, 0, 0));
        panel2.setLayout(null);

        BTdispositivo = new JLabel("Dispositivos ");
        BTdispositivo.setBounds(15, 15, panel2.getWidth() * 45 / 100, (panel2.getHeight() * 25) / 100);
        BTdispositivo.setLocation((panel2.getWidth() - BTdispositivo.getWidth()) * 10 / 100, (panel2.getHeight() - BTdispositivo.getHeight()) * 35 / 100);
        panel2.add(BTdispositivo);

        CBdispositivosBT = new JComboBox();
        CBdispositivosBT.setBounds(15, 15, panel2.getWidth() * 60 / 100, (panel2.getHeight() * 25) / 100);
        CBdispositivosBT.setLocation((panel2.getWidth() - CBdispositivosBT.getWidth()) * 90 / 100, (panel2.getHeight() - CBdispositivosBT.getHeight()) * 35 / 100);
        panel2.add(CBdispositivosBT);

        btnEscanear = new JButton("Escanear");
        btnEscanear.setBounds(15, 15, panel2.getWidth() * 45 / 100, (panel2.getHeight() * 25) / 100);
        btnEscanear.setLocation((panel2.getWidth() - btnEscanear.getWidth()) * 5 / 100, (panel2.getHeight() - btnEscanear.getHeight()) * 90 / 100);
        btnEscanear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int x = 0; x < ino.getPortsAvailable(); x++) {
                    CBdispositivosBT.addItem(ino.getSerialPorts().get(x));
                }
                if(CBdispositivosBT.getItemCount()>0)
                {
                    btnConectar.setEnabled(true);
                    CBdispositivosBT.setSelectedItem(FileManager.leerConfiguracion(1));
                }
            }
        });
        panel2.add(btnEscanear);

        btnConectar = new JButton("Conectar");
        btnConectar.setBounds(15, 15, panel2.getWidth() * 45 / 100, (panel2.getHeight() * 25) / 100);
        btnConectar.setLocation((panel2.getWidth() - btnConectar.getWidth()) * 95 / 100, (panel2.getHeight() - btnConectar.getHeight()) * 90 / 100);
        btnConectar.setEnabled(false);
        btnConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (btnConectar.getText().equals("Conectar")) {
                    ino.conectar(CBdispositivosBT.getSelectedItem().toString());
                    if (ino != null) {
                        JLestado.setText("Estado: Conectado");
                        btnConectar.setText("Desconectar");
                    }
                } else {
                    ino.finalizarConexion();
                    btnConectar.setText("Conectar");
                    JLestado.setText("Estado: Desconectado");
                    ino = null;
                }
            }
        });
        panel2.add(btnConectar);

        getContentPane().add(panel2);
//Fin de Panel 2

//Estado de conexion
        JLestado = new JLabel("Estado: Desconectado");
        JLestado.setSize(ventana.width * 25 / 100, ventana.height * 5 / 100);
        JLestado.setLocation(ventana.width * 2 / 100, ventana.height * 40 / 100);
        JLestado.setFont(new Font("Tahoma", Font.PLAIN, 30));
        getContentPane().add(JLestado);

        fondo = new JLabel();
        fondo.setSize(ventana.width, ventana.height);
        ImageIcon imagenFondo = new ImageIcon(getClass().getResource("../Imagenes/fondo.jpg"));
        Icon iconoFondo = new ImageIcon(imagenFondo.getImage().getScaledInstance(ventana.width, ventana.height, Image.SCALE_AREA_AVERAGING));
        fondo.setIcon(iconoFondo);
        getContentPane().add(fondo);
    }

    public Inicio getMe() {
        return this;
    }

    public void setTexto(String texto) {
        this.texto.append(texto);
    }
}
