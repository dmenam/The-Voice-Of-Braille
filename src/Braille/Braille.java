package Braille;

import Conexiones.Arduino;
import com.panamahitek.ArduinoException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPortException;

public class Braille {

    //Letras en Braille                 
    private final String[] mayuscula = {"01",
        "00",
        "01"};//46

    private final String[] a = {"10",
        "00",
        "00"};//1

    private final String[] b = {"10",
        "10",
        "00"};//12

    private final String[] c = {"11",
        "00",
        "00"};//14

    private final String[] d = {"11",
        "01",
        "00"};//145

    private final String[] e = {"10",
        "01",
        "00"};//15

    private final String[] f = {"11",
        "10",
        "00"};//124

    private final String[] g = {"11",
        "11",
        "00"};//1245

    private final String[] h = {"10",
        "11",
        "00"};//125

    private final String[] i = {"01",
        "10",
        "00"};//24

    private final String[] j = {"01",
        "11",
        "00"};//245

    private final String[] k = {"10",
        "00",
        "10"};//13

    private final String[] l = {"10",
        "10",
        "10"};//123

    private final String[] m = {"11",
        "00",
        "10"};//134

    private final String[] n = {"11",
        "01",
        "10"};//1345

    private final String[] ñ = {"11",
        "11",
        "01"};//12456

    private final String[] o = {"10",
        "01",
        "10"};//135

    private final String[] p = {"11",
        "10",
        "10"};//1234

    private final String[] q = {"11",
        "11",
        "10"};//12345

    private final String[] r = {"10",
        "11",
        "10"};//1235

    private final String[] s = {"01",
        "10",
        "10"};//234

    private final String[] t = {"01",
        "11",
        "10"};//2345

    private final String[] u = {"10",
        "00",
        "11"};//136

    private final String[] v = {"10",
        "10",
        "11"};//1236

    private final String[] w = {"01",
        "11",
        "01"};//2456

    private final String[] x = {"11",
        "00",
        "11"};//1346

    private final String[] y = {"11",
        "01",
        "11"};//13456

    private final String[] z = {"10",
        "01",
        "11"};//1356

    //Letras acentuadas
    private final String[] á = {"10",
        "11",
        "11"};//12356

    private final String[] é = {"01",
        "10",
        "11"};//2346

    private final String[] í = {"01",
        "00",
        "10"};//34

    private final String[] ó = {"01",
        "00",
        "11"};//346

    private final String[] ú = {"01",
        "11",
        "11"};//23456

    private final String[] ü = {"10",
        "11",
        "01"};//1256

    //Numeros-------------------------------------------------------------------
    private final String[] signoNumero = {"01",
        "01",
        "11"};//3456

    //Los numeros del 0 al 9 son las letras del a - j 
    //Signos de puntuación------------------------------------------------------
    private final String[] coma = {"00",
        "00",
        "10"};//3

    private final String[] punto = {"00",
        "10",
        "00"};//2

    private final String[] dosPuntos = {"00",
        "11",
        "00"};//25

    private final String[] puntoComa = {"00",
        "10",
        "10"};//23

    private final String[] interrogacion = {"00",
        "10",
        "01"};//26

    private final String[] admiracion = {"00",
        "11",
        "10"};//235

    private final String[] comillasDobles = {"00",
        "10",
        "11"};//236

    private final String[] abrirParentesis = {"10",
        "10",
        "01"};//126

    private final String[] cerrarParentesis = {"01",
        "01",
        "10"};//345

    //Caracteres especiales-----------------------------------------------------
    private final String[] espacio = {"00",
        "00",
        "00"};//0

    private Arduino ino;

    public Braille(Arduino ino) {
        this.ino = ino;
    }

    public Braille() {

    }

    public void imprimirBraille(String texto, int cantCaracteres) {
        String[] result = texto.split("(?<=\\G.{" + cantCaracteres + "}|\n)");
        char letra;
        int tiempo = 400;
        int k;
        enviarDato("5");
        for (int j = 0; j < result.length; j++) { //Cuantos resultados hubo de la fracion en X caracteres                   
            //for (int i = 0; i < 2; i++) {
            for (int i = 0; i < 3; i++) {//Recorrido de cada punto de la letras en braille
                for (k = 0; k < result[j].length(); k++) {//Conversion de letra por letra en el renglon especificado
                    //for (char c : result[j].toCharArray()) {
                    letra = result[j].charAt(k); //En el renglon[j] leer el caracter numero K
                    try {
                        if (Character.isDigit(letra)) {
                            System.out.print(convierteNumero(letra, i, 0));
                            Thread.sleep(tiempo);
                            enviarDato(convierteNumero(letra, i, 0));
                            Thread.sleep(tiempo);
                            System.out.print(convierteNumero(letra, i, 1));
                            enviarDato(convierteNumero(letra, i, 1));
                            Thread.sleep(tiempo);
                            System.out.print(" ");
                        }
                        if (Character.isUpperCase(letra)) {
                            //Impresion del simbolo de mayuscula
                            System.out.print(convierteMayuscula(i, 0));
                            Thread.sleep(tiempo);
                            enviarDato(convierteMayuscula(i, 0));
                            Thread.sleep(tiempo);

                            System.out.print(convierteMayuscula(i, 1));
                            enviarDato(convierteMayuscula(i, 1));
                            Thread.sleep(tiempo);

                            System.out.print(" ");
                            //Letra en minuscula
                            letra = Character.toLowerCase(letra);

                            System.out.print(convierte(letra, i, 0)); //i es el punto en braille en la columna 0
                            enviarDato(convierte(letra, i, 0));
                            Thread.sleep(tiempo);

                            System.out.print(convierte(letra, i, 1) + " ");// punto de braille en la columna 1
                            //enviarDato(convierte(letra, i, 1));
                            Thread.sleep(tiempo);
                        } else {
                            System.out.print(convierte(letra, i, 0)); //i es el punto en braille en la columna 0
                            enviarDato(convierte(letra, i, 0));
                            Thread.sleep(tiempo);

                            System.out.print(convierte(letra, i, 1) + " ");// punto de braille en la columna 1
                            enviarDato(convierte(letra, i, 1));
                            Thread.sleep(tiempo);
                        }

                    } catch (InterruptedException ex) {
                        Logger.getLogger(Braille.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("prro");//Reglon de palabra (despues de todo el primer conjunto de puntos pasa al siguiente
                try {
                    enviarDato("2");
                    Thread.sleep(tiempo);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Braille.class.getName()).log(Level.SEVERE, null, ex);
                }                
                if (i == 0) {
                    try {
                        k = volverAtras(k, j, i + 1, result, tiempo);
                        System.out.println("prro");//Reglon de palabra (despues de todo el primer conjunto de puntos pasa al siguiente
                        enviarDato("2");
                        Thread.sleep(tiempo);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Braille.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    i++;
                }
            }
            System.out.println("");//Renglon de conjunto de X caracteres
            enviarDato("6");//Enviar salto de linea
            
            enviarDato("5");//Reset
        }
        enviarDato("5");
        System.out.println("RESET");
    }

    private int volverAtras(int k, int j, int i, String[] result, int tiempo) {
        char letra;
        for (k = k - 1; k >= 0; k--) {
            try {
                letra = result[j].charAt(k); //En el renglon[j] leer el caracter numero K
                if (Character.isDigit(letra)) {
                    System.out.print(convierteNumero(letra, i, 1));
                    enviarDato(convierteNumero(letra, i, 1));
                    Thread.sleep(tiempo);

                    System.out.print(convierteNumero(letra, i, 0));
                    enviarDato(convierteNumero(letra, i, 0));
                    Thread.sleep(tiempo);
                }
                if (Character.isUpperCase(letra)) {
                    //Impresion del simbolo de mayuscula
                    System.out.print(convierteMayuscula(i, 0));
                    enviarDato(convierteMayuscula(i, 0));
                    Thread.sleep(tiempo);

                    System.out.print(convierteMayuscula(i, 1));
                    enviarDato(convierteMayuscula(i, 1));
                    Thread.sleep(tiempo);

                    System.out.print(" ");
                    //Letra en minuscula
                    letra = Character.toLowerCase(letra);

                    System.out.print(convierte(letra, i, 1)); //i es el punto en braille en la columna 0
                    enviarDato(convierte(letra, i, 1));
                    Thread.sleep(tiempo);

                    System.out.print(convierte(letra, i, 0) + " ");// punto de braille en la columna 1
                    enviarDato(convierte(letra, i, 0));
                    Thread.sleep(tiempo);
                } else {
                    System.out.print(convierte(letra, i, 1)); //i es el punto en braille en la columna 0
                    enviarDato(convierte(letra, i, 1));
                    Thread.sleep(tiempo);

                    System.out.print(convierte(letra, i, 0) + " ");// punto de braille en la columna 1
                    enviarDato(convierte(letra, i, 0));
                    Thread.sleep(tiempo);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Braille.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return k;
    }

    private String convierteMayuscula(int row, int col) {
        String aux = "";
        aux = mayuscula[row].charAt(col) + "";
        return aux;
    }

    private String convierteNumero(char letra, int row, int col) {
        String aux = "";
        aux = signoNumero[row].charAt(col) + "";
        return aux;
    }

    private String convierte(char letra, int row, int col) {
        String aux = "";
        switch (letra) {
            //Letras acentuadas
            case 'á':
                //System.out.println("á");
                aux = á[row].charAt(col) + "";
                break;
            case 'é':
                //System.out.println("é");
                aux = é[row].charAt(col) + "";
                break;
            case 'í':
                //System.out.println("í");
                aux = í[row].charAt(col) + "";
                break;
            case 'ó':
                //System.out.println("ó");
                aux = ó[row].charAt(col) + "";
                break;
            case 'ú':
                //System.out.println("ú");
                aux = ú[row].charAt(col) + "";
                break;
            case 'ü':
                //System.out.println("ü");
                aux = ü[row].charAt(col) + "";
                break;
            //Minusculas----------------------------------------------------    
            case 'a':
                //System.out.println("a");
                aux = a[row].charAt(col) + "";
                break;
            case 'b':
                //System.out.println("b");
                aux = b[row].charAt(col) + "";
                break;
            case 'c':
                //System.out.println("c");
                aux = c[row].charAt(col) + "";
                break;
            case 'd':
                //System.out.println("d");
                aux = d[row].charAt(col) + "";
                break;
            case 'e':
                //System.out.println("e");
                aux = e[row].charAt(col) + "";
                break;
            case 'f':
                //System.out.println("f");
                aux = f[row].charAt(col) + "";
                break;
            case 'g':
                //System.out.println("g");
                aux = g[row].charAt(col) + "";
                break;
            case 'h':
                //System.out.println("h");
                aux = h[row].charAt(col) + "";
                break;
            case 'i':
                //System.out.println("i");
                aux = i[row].charAt(col) + "";
                break;
            case 'j':
                //System.out.println("j");
                aux = j[row].charAt(col) + "";
                break;
            case 'k':
                //System.out.println("k");
                aux = k[row].charAt(col) + "";
                break;
            case 'l':
                //System.out.println("l");
                aux = l[row].charAt(col) + "";
                break;
            case 'm':
                //System.out.println("m");
                aux = m[row].charAt(col) + "";
                break;
            case 'n':
                //System.out.println("n");
                aux = n[row].charAt(col) + "";
                break;
            case 'ñ':
                //System.out.println("ñ");
                aux = ñ[row].charAt(col) + "";
                break;
            case 'o':
                //System.out.println("o");
                aux = o[row].charAt(col) + "";
                break;
            case 'p':
                //System.out.println("p");
                aux = p[row].charAt(col) + "";
                break;
            case 'q':
                //System.out.println("q");
                aux = q[row].charAt(col) + "";
                break;
            case 'r':
                //System.out.println("r");
                aux = r[row].charAt(col) + "";
                break;
            case 's':
                //System.out.println("s");
                aux = s[row].charAt(col) + "";
                break;
            case 't':
                //System.out.println("t");
                aux = t[row].charAt(col) + "";
                break;
            case 'u':
                //System.out.println("u");
                aux = u[row].charAt(col) + "";
                break;
            case 'v':
                //System.out.println("v");
                aux = v[row].charAt(col) + "";
                break;
            case 'w':
                //System.out.println("w");
                aux = w[row].charAt(col) + "";
                break;
            case 'x':
                //System.out.println("x");
                aux = x[row].charAt(col) + "";
                break;
            case 'y':
                //System.out.println("y");
                aux = y[row].charAt(col) + "";
                break;
            case 'z':
                //System.out.println("z");
                aux = z[row].charAt(col) + "";
                break;
            //Numeros-------------------------------------------------------
            case '1':
                //System.out.println("1");
                aux = a[row].charAt(col) + "";
                break;
            case '2':
                //System.out.println("2");
                aux = b[row].charAt(col) + "";
                break;
            case '3':
                //System.out.println("3");
                aux = c[row].charAt(col) + "";
                break;
            case '4':
                //System.out.println("4");
                aux = d[row].charAt(col) + "";
                break;
            case '5':
                //System.out.println("5");
                aux = e[row].charAt(col) + "";
                break;
            case '6':
                //System.out.println("6");
                aux = f[row].charAt(col) + "";
                break;
            case '7':
                //System.out.println("7");
                aux = g[row].charAt(col) + "";
                break;
            case '8':
                //System.out.println("8");
                aux = h[row].charAt(col) + "";
                break;
            case '9':
                //System.out.println("9");
                aux = i[row].charAt(col) + "";
                break;
            case '0':
                //System.out.println("0");
                aux = j[row].charAt(col) + "";
                break;
            //Signos de puntuacion   
            case '.':
                //System.out.println("Punto .");
                aux = punto[row].charAt(col) + "";
                break;
            case ',':
                //System.out.println("Coma .");
                aux = coma[row].charAt(col) + "";
                break;
            case ':':
                //System.out.println("Doble punto :");
                aux = dosPuntos[row].charAt(col) + "";
                break;
            case ';':
                //System.out.println("Punto y coma ;");
                aux = puntoComa[row].charAt(col) + "";
                break;
            case '?':
            case '¿':
                //System.out.println("Signo de interrogacion ? o ¿");
                aux = interrogacion[row].charAt(col) + "";
                break;
            case '!':
            case '¡':
                //System.out.println("Signo de admiracion ! o ¡");
                aux = admiracion[row].charAt(col) + "";
                break;
            case '"':
                //System.out.println("Comilla doble " + '"');
                aux = comillasDobles[row].charAt(col) + "";
                break;
            case '(':
                // System.out.println("Paretnesis )");
                aux = abrirParentesis[row].charAt(col) + "";
                break;
            case ')':
                //System.out.println("Parentesis (");
                aux = cerrarParentesis[row].charAt(col) + "";
                break;
            case ' ':
                //System.out.println("Espacio ");
                aux = espacio[row].charAt(col) + "";
                break;
            case '\n': //System.out.println("");
            {
                //System.out.println("SALTO DE LINEA");
                aux = espacio[row].charAt(col) + "";
            }
            break;

            default:
                System.out.println("Caracter no reconocido: " + letra);
                break;
        }
        return aux;
    }

    private String convierte(char letra, int row) {
        String aux = "";
        switch (letra) {
            case 'a':
                aux = a[row];
                break;
        }
        return aux;
    }

    public void setArduino(Arduino ino) {
        this.ino = ino;
    }

    private void enviarDato(String dato) {
        try {
            ino.sendData(dato);
        } catch (ArduinoException ex) {
            Logger.getLogger(Braille.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(Braille.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
