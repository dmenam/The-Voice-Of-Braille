package Conexiones;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public final class Arduino extends PanamaHitek_Arduino {

    private SerialPortEventListener listener;
    private String puerto;
    private int BAUDIOS = 9600;
    private String dato;
    private boolean estado;

    public Arduino() {
        this.puerto = null;
        this.estado = false;
    }

public void conectar(String puerto){
        try {
            this.arduinoRXTX(puerto, BAUDIOS, new SerialPortEventListener() {
                @Override
        public void serialEvent(SerialPortEvent spe) {
                    try {
                        dato = Arduino.this.printMessage();
                    } catch (SerialPortException | ArduinoException ex) {
                        Logger.getLogger(Arduino

.class  


.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            estado = true;
        } catch (ArduinoException ex) {
            Logger.getLogger(Arduino

.class  


.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarDato(String dato){
        try {
            this.sendData(dato);
        } catch (ArduinoException | SerialPortException ex) {
            Logger.getLogger(Arduino

.class  


.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getDato(){
        return dato;
    }

    public void finalizarConexion() {
        try {
            Arduino.this.killArduinoConnection();
            estado = false;
        } catch (ArduinoException ex) {
            Logger.getLogger(Arduino

.class  


.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean getEstado(){
        return estado;
    }
    
}
