package Conexiones;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public final class Arduino extends PanamaHitek_Arduino {

    private SerialPortEventListener listener;
    private String puerto;
    private int BAUDIOS = 9600;
    private String dato;
    private boolean conexion;

    public Arduino() {
        this.puerto = null;
        this.conexion = false;
    }

    public boolean conectar(String puerto) {
        try {
            this.arduinoRXTX(puerto, BAUDIOS, new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent spe) {
                    try {
                        dato = Arduino.this.printMessage();
                    } catch (SerialPortException | ArduinoException ex) {
                        Logger.getLogger(Arduino.class
                                .getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error en la conexion");
                    }
                }
            });
            conexion = true;
            return true;
        } catch (ArduinoException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al conectarse con el Arduino", "Error", JOptionPane.ERROR_MESSAGE);
            conexion = false;
            return false;
        }
    }

    public boolean enviarDato(String dato) {
        try {
            this.sendData(dato);
            return true;
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String getDato() {
        return dato;
    }

    public void finalizarConexion() {
        try {
            Arduino.this.killArduinoConnection();
        } catch (ArduinoException ex) {
            Logger.getLogger(Arduino.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void expulsarPapel() {
        try {
            this.sendData("4");
            this.sendData("5");
        } catch (ArduinoException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarPapel() {
        try {
            this.sendData("3");
        } catch (ArduinoException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean getConexion() {
        return conexion;
    }
}
