#include <SoftwareSerial.h>  // libreria que permite establecer pines digitales para comunicacion serie
//Puertos bluetooth
SoftwareSerial miBT(0, 1);  // primer pin como RX, segundo pin como TX para la comunicacion

//Motor encargado de mover el actuador lineal
#define VELOCIDAD_ACT 1400 //Mientras menor sea el numero, mayor velocidad
#define RES_ACT 2
#define STEP_ACT 3
#define DIR_ACT 4

//Motor encargado de la carga de papel
#define VELOCIDAD 1800 //Mientras menor sea el numero, mayor velocidad
#define RES 5
#define STEP 6
#define DIR 7

//Salida actuador
#define ACTUADOR 8

//Entrada del interruptor de la posicion inicial
#define POSICIONI 10 

//Entrada delPOSICIONF interruptor de la posicion final
#define  11

//Entrada optointerruptor carga de papel
#define OPTO 12

//Datos de interfaz 
char dato;
boolean ban = true;

void setup()
{
  Serial.begin(9600);
  miBT.begin(38400);    // comunicacion serie entre Arduino y el modulo a 38400 bps
  
  //Pines del motor del actuador
  pinMode(STEP_ACT, OUTPUT);
  pinMode(DIR_ACT, OUTPUT);
  pinMode(RES_ACT, OUTPUT);

  //Pines del motor del papel
  pinMode(STEP, OUTPUT);
  pinMode(DIR, OUTPUT);
  pinMode(RES, OUTPUT);

  //Pin de activacion del actuador
  pinMode(ACTUADOR, OUTPUT);

  digitalWrite(RES_ACT, LOW);       //Cuando reset se encuentre en HIGH el motor arra (Actuador)
  digitalWrite(RES, LOW);           //Cuando reset se encuentre en HIGH el motor arra (Papel)
}

void loop()
{
  if(miBT.available())
  {
    dato = miBT.read();
    Serial.print(dato);

    switch(dato)
    {
      case '0':
      {
        espacio();
      }
      break;

      case '1':
      {
        punto();
      }
      break;

      case '2':
      {
        if(!ban)
        {
          Serial.println("Imprimiendo hacia la derecha"); 
          saltoLinea();
          reset();
          moverPasos(100);
          ban = true; //TRUE == desplazar hacia la derecha
        }
        else if(ban)
        {
          Serial.println("Imprimiendo hacia la izquierda");
          ban = false; //FALSE == desplazar hacia la izauierda
          saltoLinea();
          moverPasos(70);
        }
      }
      break;

      case '3':
      {
        cargaP();
      }
      break;

      case '4':
      {
        liberarP();
      }
      break;

      case '5':
      {
        reset();
        moverPasos(100);
      }
      break;
      
      case '6':
      {
        saltoRenglon();
      }
      break;
    }
  }
}

void liberarP()
{
  Serial.println("Liberando papel");
  digitalWrite(RES, HIGH); //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR, HIGH);
  
  while(digitalRead(OPTO) == HIGH)
  {
    digitalWrite(STEP, HIGH);
    digitalWrite(STEP, LOW);
    delayMicroseconds(VELOCIDAD);  // leemos la referencia de velocidad
  }
  
  for(int pasos = 0; pasos < 900; pasos++)
  {
    digitalWrite(STEP, HIGH);
    digitalWrite(STEP, LOW);
    delayMicroseconds(VELOCIDAD);  // leemos la referencia de velocidad
  }

  digitalWrite(RES, LOW); //Cuando reset se encuentre en HIGH el motor arra
}

void cargaP()
{ 
  Serial.println("Cargando papel");
  digitalWrite(RES, HIGH);  //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR, LOW);   //Sentido de giro del motor
  
  while(digitalRead(OPTO) == LOW)
  {
    digitalWrite(STEP, HIGH);
    digitalWrite(STEP, LOW);
    delayMicroseconds(VELOCIDAD);  // leemos la referencia de velocidad
  }

  for(int pasos = 0; pasos < 200; pasos++)
  {
    digitalWrite(STEP, HIGH);
    digitalWrite(STEP, LOW);
    delayMicroseconds(VELOCIDAD);  // leemos la referencia de velocidad
  }
  
  Serial.println("Moviendo papel");
  digitalWrite(DIR, HIGH);
  for(int pasos = 0; pasos < 1600; pasos++)
  {
    digitalWrite(STEP, HIGH);
    digitalWrite(STEP, LOW);
    delayMicroseconds(VELOCIDAD);  // leemos la referencia de velocidad
  }

  digitalWrite(RES, LOW); //Cuando reset se encuentre en HIGH el motor arra
}

void espacio()
{
  Serial.println("Dando un espacio");
  digitalWrite(RES_ACT, HIGH);  //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR_ACT, ban);  //Sentido de giro del motor

  
  for(int pasos = 0; pasos < 70; pasos++)
  {
    digitalWrite(STEP_ACT, HIGH);
    //delayMicroseconds(VELOCIDAD_ACT);  // leemos la referencia de velocidad
    digitalWrite(STEP_ACT, LOW);
    delayMicroseconds(VELOCIDAD_ACT);  // leemos la referencia de velocidad
  }
  
  digitalWrite(RES_ACT, LOW);
}

void punto()
{
  Serial.println("Creando punto");
  
  digitalWrite(ACTUADOR, HIGH);
  delay(100);
  digitalWrite(ACTUADOR, LOW);
  
  digitalWrite(RES_ACT, HIGH);  //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR_ACT, ban);  //Sentido de giro del motor
  
  for(int pasos = 0; pasos < 70; pasos++)
  {
    digitalWrite(STEP_ACT, HIGH);
    //delayMicroseconds(VELOCIDAD_ACT);  // leemos la referencia de velocidad
    digitalWrite(STEP_ACT, LOW);
    delayMicroseconds(VELOCIDAD_ACT);  // leemos la referencia de velocidad
  }
  
  digitalWrite(RES_ACT, LOW);
}

void saltoLinea()
{
  Serial.println("Salto de linea");
  digitalWrite(RES, HIGH); //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR, HIGH);
  
  for(int pasos = 0; pasos < 45; pasos++)
  {
    digitalWrite(STEP, HIGH);
    digitalWrite(STEP, LOW);
    delayMicroseconds(VELOCIDAD);  // leemos la referencia de velocidad
  }

  digitalWrite(RES, LOW); //Cuando reset se encuentre en HIGH el motor arra
  delay(1000);
}

void saltoRenglon()
{
  Serial.println("Salto de renglon");
  digitalWrite(RES, HIGH); //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR, HIGH);
  
  for(int pasos = 0; pasos < 80; pasos++)
  {
    digitalWrite(STEP, HIGH);
    digitalWrite(STEP, LOW);
    delayMicroseconds(VELOCIDAD);  // leemos la referencia de velocidad
  }

  digitalWrite(RES, LOW); //Cuando reset se encuentre en HIGH el motor arra
  delay(1000);
}

void reset()
{
  ban=true;
  Serial.println("Desplazando actuador hacia la posicion inicial");
  digitalWrite(RES_ACT, HIGH);  //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR_ACT, LOW);  //Sentido de giro del motor
  Serial.println(digitalRead(POSICIONI));
  while(digitalRead(POSICIONI) == HIGH)
  {
    digitalWrite(STEP_ACT, HIGH);
    digitalWrite(STEP_ACT, LOW);
    delayMicroseconds(VELOCIDAD_ACT);  // leemos la referencia de velocidad
  }
  digitalWrite(RES_ACT, LOW);
}

void moverPasos(int p)
{
  Serial.println("Rotando una cantidad de pasos");
  digitalWrite(RES_ACT, HIGH);  //Cuando reset se encuentre en HIGH el motor arranca
  digitalWrite(DIR_ACT, ban);  //Sentido de giro del motor

  
  for(int pasos = 0; pasos < p; pasos++)
  {
    digitalWrite(STEP_ACT, HIGH);
    //delayMicroseconds(VELOCIDAD_ACT);  // leemos la referencia de velocidad
    digitalWrite(STEP_ACT, LOW);
    delayMicroseconds(VELOCIDAD_ACT);  // leemos la referencia de velocidad
  }
  
  digitalWrite(RES_ACT, LOW);
}
