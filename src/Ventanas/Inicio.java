package Ventanas;

import Braille.FontBraille;
import Conexiones.FileManager;
import Conexiones.Arduino;
import Braille.Braille;
import Conexiones.Comandos_Voz;
import Conexiones.LeerTTS;
import Conexiones.Voz;
import com.sun.javafx.geom.transform.CanTransformVec3d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

public class Inicio extends JFrame {

    private JMenuBar menu;
    private JMenu Marchivo, Mconfiguracion, Mayuda;
    private JMenuItem MInuevo, MIabrir, MIguardar, MIguardarC;
    private JMenuItem MIopciones;
    private JMenuItem MIcreditos, MIAyuda;

    private JTextArea texto;

    private Arduino ino;
    private Braille braille;

    private FileManager manager;
    private File archivo;

    private Button btnLimpiar, btnImprimir, btnGuardar;
    private Button btnTipoLetra;
    private Button btnEscanear, btnConectar;
    private Button btnDictado, btnLeer;

    private JLabel fondo, titulo;
    private JLabel BTdispositivo;
    private JLabel JLestado;
    private JLabel cantCaracteres;

    private JComboBox CBdispositivosBT;

    private JPanelRound panel1, panel2;

    private Configuracion configuracion;
    private Creditos creditos;
    private Ayuda ayuda;

    private Voz voz;
    private Comandos_Voz comandos;
    private boolean estadoComandos;
    private LeerTTS hablar;

    public Inicio() {
        //Tamaño de la pantalla
        Dimension pantalla;
        pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        //Tamaño de la ventana
        setSize(pantalla.width, pantalla.height);
        Dimension ventana = getSize();
        //Icono
        //setIconImage(new ImageIcon(getClass().getResource("../Imagenes/Icono_VoB.png")).getImage());
        try {
            BufferedImage imglogo;
            imglogo = ImageIO.read(getClass().getClassLoader().getResource("Imagenes/Icono_VoB.png"));
            setIconImage(imglogo);
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        setExtendedState(MAXIMIZED_BOTH);
        setTitle("The Voice of Braille");

        //Inicializar componentes
        inicializar(ventana);
        setLocationRelativeTo(null);
        setVisible(true);
        if (FileManager.leerConfiguracion(3).equals("0")) {
            suspenderComandos();
            this.estadoComandos = false;
            //hablar("comandos desactivados");
        }
        if (FileManager.leerConfiguracion(3).equals("1")) {
            //reaunudarComandos();
            this.estadoComandos = true;
            hablar("comandos activados");
        }
    }

    private void inicializar(Dimension ventana) {
        //Manager de Archivos---------------------------------------------------
        manager = new FileManager();
        manager.setDirectorioPorDefecto(FileManager.leerConfiguracion(0));
        //Braille---------------------------------------------------------------
        braille = new Braille();
        //----------------------------------------------------------------------  
        //Arduino
        ino = new Arduino();
        //----------------------------------------------------------------------
        voz = new Voz(this);
        comandos = new Comandos_Voz(this);
        comandos.iniciarComandos();
        this.estadoComandos = false;
        hablar = new LeerTTS();

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
                nuevoArchivo();
            }
        });
        MInuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        Marchivo.add(MInuevo);
        //---------------------------------------------------------------------//
        MIabrir = new JMenuItem("Abrir Archivo...");
        MIabrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                abrirArchivo();
            }
        });
        MIabrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        Marchivo.add(MIabrir);
        //---------------------------------------------------------------------//
        MIguardar = new JMenuItem("Guardar");
        MIguardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                guardarArchivo();
            }
        });
        MIguardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        Marchivo.add(MIguardar);
        //---------------------------------------------------------------------//
        MIguardarC = new JMenuItem("Guardar Como...");
        MIguardarC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                guardarArchivoComo();
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
                configuracion = new Configuracion(Inicio.this.getMe(), manager, ino);
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
                Inicio.this.ayuda = new Ayuda(Inicio.this.getMe());
            }
        });
        Mayuda.add(MIAyuda);
        //---------------------------------------------------------------------//
        MIcreditos = new JMenuItem("Creditos de la aplicación     ");
        MIcreditos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Inicio.this.creditos = new Creditos(Inicio.this.getMe());
            }
        });
        Mayuda.add(MIcreditos);
        //---------------------------------------------------------------------//

        menu.add(Mayuda);

        getContentPane().add(menu);
//Fin acomodo del menu superior       

//Acomodo del panel 1 donde se muestra el texto normal
        panel1 = new JPanelRound();
        panel1.setBounds(0, 0, (ventana.width * 70) / 100, (ventana.height * 75) / 100);
        panel1.setLocation((ventana.width - panel1.getWidth()) * 80 / 100, (ventana.height - panel1.getHeight()) * 60 / 100);
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.setBackground(Color.DARK_GRAY);
        panel1.setLayout(null);

        texto = new JTextArea();
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        JScrollPane scroll = new JScrollPane(texto);
        scroll.setSize(panel1.getWidth() * 95 / 100, (panel1.getHeight() * 85) / 100);
        scroll.setLocation(panel1.getWidth() * 2 / 100, panel1.getHeight() * 2 / 100);

        cantCaracteres = new JLabel("0 / 250");
        cantCaracteres.setSize(panel1.getWidth() * 15 / 100, (panel1.getHeight() * 5) / 100);
        cantCaracteres.setLocation((panel1.getWidth() - cantCaracteres.getWidth()) * 98 / 100, (panel1.getHeight() - cantCaracteres.getHeight()) * 85 / 100);
        cantCaracteres.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel1.add(cantCaracteres);

        texto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                cantCaracteres.setText(texto.getText().length() + " / 250");
                if (texto.getText().length() >= 250) {
                    cantCaracteres.setForeground(Color.red);
                    texto.setText(texto.getText().substring(0, 250));
                    e.consume();
                } else {
                    cantCaracteres.setForeground(Color.BLACK);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                cantCaracteres.setText(texto.getText().length() + " / 250");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                cantCaracteres.setText(texto.getText().length() + " / 250");
            }
        });
        panel1.add(scroll);

        //Tipo de letra del texto
        FontBraille fontBraille = new FontBraille();
        btnTipoLetra = new Button();
        btnTipoLetra.setText("ABC");
        btnTipoLetra.setBounds(10, 10, ventana.width * 5 / 100, ventana.height * 5 / 100);
        btnTipoLetra.setLocation((ventana.width - btnTipoLetra.getWidth()) * 90 / 100, (ventana.height - btnTipoLetra.getHeight()) * 10 / 100);
        btnTipoLetra.setColor1(Color.WHITE); //Color superior
        btnTipoLetra.setColor2(Color.BLACK); //Color inferior
        btnTipoLetra.setColor3(Color.GRAY); //Color de borde
        //btnTipoLetra.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        btnTipoLetra.setFont(fontBraille.MyFont(Font.PLAIN, 15));
        btnTipoLetra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (btnTipoLetra.getFont().equals(fontBraille.MyFont(Font.PLAIN, 15))) {
                    btnTipoLetra.setFont(new Font("Times New Roman", Font.PLAIN, 15));
                    texto.setFont(fontBraille.MyFont(Font.PLAIN, 25));
                } else {                    
                    
                    btnTipoLetra.setFont(fontBraille.MyFont(Font.PLAIN, 15));
                    texto.setFont(new Font("Times New Roman", Font.PLAIN, 25));
                    
                }
            }
        });
        getContentPane().add(btnTipoLetra);

        btnImprimir = new Button();
        btnImprimir.setText("Imprimir");
        btnImprimir.setBounds(10, 10, panel1.getWidth() * 20 / 100, (panel1.getHeight() * 5) / 100);
        btnImprimir.setLocation((panel1.getWidth() - btnImprimir.getWidth()) * 2 / 100, (panel1.getHeight() - btnImprimir.getHeight()) * 95 / 100);
        btnImprimir.setEnabled(false);
        btnImprimir.setColor1(Color.WHITE); //Color superior
        btnImprimir.setColor2(Color.BLACK); //Color inferior
        btnImprimir.setColor3(Color.WHITE); //Color de borde
        btnImprimir.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                imprimir();
            }
        });
        panel1.add(btnImprimir);

        btnGuardar = new Button();
        btnGuardar.setText("Guardar");
        btnGuardar.setBounds(10, 10, panel1.getWidth() * 20 / 100, (panel1.getHeight() * 5) / 100);
        btnGuardar.setLocation((panel1.getWidth() - btnGuardar.getWidth()) * 30 / 100, (panel1.getHeight() - btnGuardar.getHeight()) * 95 / 100);
        btnGuardar.setColor1(Color.WHITE); //Color superior
        btnGuardar.setColor2(Color.BLACK); //Color inferior
        btnGuardar.setColor3(Color.WHITE); //Color de borde
        btnGuardar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArchivo();
            }
        });
        panel1.add(btnGuardar);

        btnLimpiar = new Button();
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBounds(10, 10, panel1.getWidth() * 20 / 100, (panel1.getHeight() * 5) / 100);
        btnLimpiar.setLocation((panel1.getWidth() - btnLimpiar.getWidth()) * 95 / 100, (panel1.getHeight() - btnLimpiar.getHeight()) * 95 / 100);
        btnLimpiar.setColor1(Color.WHITE); //Color superior
        btnLimpiar.setColor2(Color.BLACK); //Color inferior
        btnLimpiar.setColor3(Color.WHITE); //Color de borde
        btnLimpiar.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!texto.getText().isEmpty()) {
                    int opc = JOptionPane.showConfirmDialog(getMe(), "¿Desea guardar el documento?",
                            "¿Desea eliminar el texto sin guardarlo?", JOptionPane.YES_NO_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        guardarArchivo();
                        archivo = null;
                        setTitle("The Voice of Braille");
                    }
                    texto.setText("");
                }
            }
        });
        panel1.add(btnLimpiar);

        getContentPane().add(panel1);
//Fin del acomodo del panel 1  

//Acomodo del panel 2 donde se muestra las conexiones Bluetooth        
        panel2 = new JPanelRound();
        panel2.setBounds(0, 0, (ventana.width * 18) / 100, (ventana.height * 10) / 100);
        panel2.setLocation((ventana.width - panel2.getWidth()) * 3 / 100, (ventana.height - panel2.getHeight()) * 20 / 100);
        Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(110, 110, 100)), "Dispositivos Bluetooth");
        panel2.setBorder(border);
        panel2.setBackground(new Color(0, 0, 0, 0));
        panel2.setLayout(null);

        BTdispositivo = new JLabel("Dispositivos ");
        BTdispositivo.setBounds(15, 15, panel2.getWidth() * 45 / 100, (panel2.getHeight() * 25) / 100);
        BTdispositivo.setLocation((panel2.getWidth() - BTdispositivo.getWidth()) * 10 / 100, (panel2.getHeight() - BTdispositivo.getHeight()) * 35 / 100);
        panel2.add(BTdispositivo);

        //GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //String []fuentes= environment.getAvailableFontFamilyNames();
        CBdispositivosBT = new JComboBox();
        CBdispositivosBT.setBounds(15, 15, panel2.getWidth() * 60 / 100, (panel2.getHeight() * 25) / 100);
        CBdispositivosBT.setLocation((panel2.getWidth() - CBdispositivosBT.getWidth()) * 90 / 100, (panel2.getHeight() - CBdispositivosBT.getHeight()) * 35 / 100);
        CBdispositivosBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnConectar.setEnabled(true);
            }
        });
        panel2.add(CBdispositivosBT);

        btnEscanear = new Button();
        btnEscanear.setText("Escanear");
        btnEscanear.setBounds(15, 15, panel2.getWidth() * 45 / 100, (panel2.getHeight() * 25) / 100);
        btnEscanear.setLocation((panel2.getWidth() - btnEscanear.getWidth()) * 5 / 100, (panel2.getHeight() - btnEscanear.getHeight()) * 90 / 100);
        btnEscanear.setColor1(Color.WHITE); //Color superior
        btnEscanear.setColor2(Color.GRAY); //Color inferior
        btnEscanear.setColor3(Color.WHITE); //Color de borde
        btnEscanear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int x = 0; x < ino.getPortsAvailable(); x++) {
                    CBdispositivosBT.addItem(ino.getSerialPorts().get(x));
                }
                if (CBdispositivosBT.getItemCount() > 0) {
                    btnConectar.setEnabled(true);
                    CBdispositivosBT.setSelectedItem(FileManager.leerConfiguracion(1));
                }
            }
        });
        panel2.add(btnEscanear);

        btnConectar = new Button();
        btnConectar.setText("Conectar");
        btnConectar.setBounds(15, 15, panel2.getWidth() * 45 / 100, (panel2.getHeight() * 25) / 100);
        btnConectar.setLocation((panel2.getWidth() - btnConectar.getWidth()) * 95 / 100, (panel2.getHeight() - btnConectar.getHeight()) * 90 / 100);
        btnConectar.setEnabled(false);
        btnConectar.setColor1(Color.WHITE); //Color superior
        btnConectar.setColor2(Color.GRAY); //Color inferior
        btnConectar.setColor3(Color.WHITE); //Color de borde
        btnConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (btnConectar.getText().equals("Conectar")) {
                    if (ino.conectar(CBdispositivosBT.getSelectedItem().toString())) {
                        braille.setArduino(ino);
                        JLestado.setText("Estado: Conectado");
                        btnConectar.setText("Desconectar");
                        int opc = JOptionPane.showConfirmDialog(null, "¿Desea encender la impresora?", "ATENCIÓN", JOptionPane.YES_NO_OPTION);
                        switch (opc) {
                            case JOptionPane.YES_OPTION:
                                ino.setEstado(true);
                                btnImprimir.setEnabled(true);
                                break;
                            case JOptionPane.NO_OPTION:
                                ino.setEstado(false);
                                break;
                            default:
                                ino.setEstado(false);
                                break;
                        }
                    }
                    return;
                } else {
                    braille.setArduino(null);
                    if (ino != null) {
                        ino.finalizarConexion();
                    }
                    btnConectar.setText("Conectar");
                    btnConectar.setEnabled(false);
                    btnImprimir.setEnabled(false);
                    JLestado.setText("Estado: Desconectado");
                    ino = null;
                    return;
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

//Botones del manejor de voz        
        btnDictado = new Button();
        btnDictado.setText("Iniciar Dictado por Voz");
        btnDictado.setBounds(10, 10, ventana.width * 20 / 100, (ventana.height * 5) / 100);
        btnDictado.setLocation((ventana.width - btnDictado.getWidth()) * 2 / 100, (ventana.height - btnDictado.getHeight()) * 65 / 100);
        btnDictado.setColor1(Color.WHITE); //Color superior
        btnDictado.setColor2(Color.BLACK); //Color inferior
        btnDictado.setColor3(Color.WHITE); //Color de borde
        btnDictado.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnDictado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarDictado();
            }
        });
        getContentPane().add(btnDictado);

        btnLeer = new Button();
        btnLeer.setText("Leer Texto Introducido");
        btnLeer.setBounds(10, 10, ventana.width * 20 / 100, (ventana.height * 5) / 100);
        btnLeer.setLocation((ventana.width - btnLeer.getWidth()) * 2 / 100, (ventana.height - btnLeer.getHeight()) * 75 / 100);
        btnLeer.setColor1(Color.WHITE); //Color superior
        btnLeer.setColor2(Color.BLACK); //Color inferior
        btnLeer.setColor3(Color.WHITE); //Color de borde
        btnLeer.setFont(new Font("Times New Roman", Font.BOLD, 15));
        btnLeer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leerTexto();
            }
        });
        getContentPane().add(btnLeer);

        fondo = new JLabel();
        fondo.setSize(ventana.width, ventana.height);
        //ImageIcon imagenFondo = new ImageIcon(getClass().getResource("../Imagenes/fondo.jpg"));
        URL urlDelFondo = Splash.class.getClassLoader().getResource("Imagenes/fondo.jpg");
        ImageIcon imagenFondo = new ImageIcon(urlDelFondo);
        Icon iconoFondo = new ImageIcon(imagenFondo.getImage().getScaledInstance(ventana.width, ventana.height, Image.SCALE_AREA_AVERAGING));
        fondo.setIcon(iconoFondo);
        getContentPane().add(fondo);
    }

    public Inicio getMe() {
        return this;
    }

    public void setTexto(String texto) {
        this.texto.append(texto + "\n");
    }

    public String getTexto() {
        return this.texto.getText();
    }

    public boolean imprimir() {
        if (ino.getConexion()) {
            if (ino.getEstado()) {
                try {
                    ino.cargarPapel();
                    System.out.println("Cargando papel");
                    braille.imprimirBraille(texto.getText(), 30);
                    //JOptionPane.showMessageDialog(this, "Imprimiendo...");
                    ino.expulsarPapel();
                    System.out.print("Sacando papel....");
                    return true;
                } catch (Exception e) {
                    return false;
                }
            } else {
                hablar("Error en la impresion, la impresora podria estar apagada, presione enter para continuar");
                JOptionPane.showMessageDialog(null, "La impresora podria encontrarse apagada...", "Error de Impresión", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            hablar("Error en la conexion con la impresora, verifique su conexion, presione enter para continuar");
            JOptionPane.showMessageDialog(null, "Verifique su conexion, No es posible conectarse con Arduino", "Verifique su conexión", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public Configuracion getConfiguracion() {
        return this.configuracion;
    }

    public Ayuda getAyuda() {
        return this.ayuda;
    }

    public Creditos getCreditos() {
        return this.creditos;
    }

    public void cerrarConfiguracion() {
        this.configuracion.dispose();
    }

    public void cerrarAyuda() {
        this.ayuda.dispose();
    }

    public void cerrarCreditos() {
        this.creditos.dispose();
    }

    public void abrirArchivo() {
        try {
            archivo = manager.abrirAchivo();
            if (archivo != null) {
                setTitle("The Voice of Braille - " + archivo.getName());
                texto.setText(manager.leerArchivo(archivo));
            }
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarArchivo() {
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
                if (archivo != null) {
                    setTitle("The Voice of Braille - " + archivo.getName());
                }
            } catch (IOException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void guardarArchivoComo() {
        try {
            manager.guardarArchivoComo(texto.getText());
            archivo = manager.getArchivo();
            if (archivo != null) {
                setTitle("The Voice of Braille - " + archivo.getName());
            }
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nuevoArchivo() {
        try {
            manager.guardarArchivoComo("");
            archivo = manager.getArchivo();
            if (archivo != null) {
                setTitle("The Voice of Braille - " + archivo.getName());
            }
        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void leerTexto() {
        try {
            hablar.leerTexto(getTexto());
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarDictado() {
        if (comandos.getEstadoComandos()) {
            comandos.suspenerComandos();
            hablar.leerTexto("Iniciando dictado");
            voz.iniciarDictado();
            hablar.leerTexto("Dictado terminado");
            comandos.reaunudarComandos();
        } else {
            hablar.leerTexto("Iniciando dictado");
            voz.iniciarDictado();
            hablar.leerTexto("Dictado terminado");
        }
    }

    public void iniciarComandos() {
        if (!comandos.getEstadoComandos()) {
            comandos.iniciarComandos();
            hablar("Comandos activados");
        }
    }

    public void suspenderComandos() {
        if (comandos.getEstadoComandos()) {
            comandos.suspenerComandos();
            estadoComandos = false;
            hablar.leerTexto("Comandos desactivados");
            //JOptionPane.showMessageDialog(null, "Comandos Finalizados", "Alerta", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void reaunudarComandos() {
        if (!comandos.getEstadoComandos()) {
            comandos.reaunudarComandos();
            estadoComandos = true;
            hablar("Comandos activados");
        }
    }

    public void vozSalir() {
        try {
            hablar.leerTexto("Hasta pronto");
            hablar.finalizarTodo();
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void vozSaludar() {
        try {
            hablar.leerTexto("Hola, Esto es The Voice of Braile.");
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hablar(String texto) {
        hablar.leerTexto(texto);
    }

    public void setEstadoArduino(boolean estado) {
        if (ino.getConexion()) {
            ino.setEstado(estado);
            if (estado) {
                hablar("impresora encendida");
            } else {
                hablar("impresora apagada");
            }
        } else {
            hablar("Sin conexion con la impresora, compruebe su conexion");
        }
    }

    public boolean getEstadoArduino() {
        return ino.getEstado();
    }

    public void contarCarcateres() {
        cantCaracteres.setText(texto.getText().length() + " / 250");
        if (texto.getText().length() >= 250) {
            cantCaracteres.setForeground(Color.red);
            texto.setText(texto.getText().substring(0, 250));
        } else {
            cantCaracteres.setForeground(Color.BLACK);
        }
    }
}
