import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.comm.NXTCommConnector;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.RS485;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class JulioLee2 {
	private static SensorPort PUERTO_COLOR = SensorPort.S3;
	private static int SIN_COMUNICACION = 0;
	private static int GET_CONEXION = 1;
	private static int SENSAR = 2;
	private static int PATEAR = 3;
	
	public static void main(String[] args) {
		// Inicializo el sensor
		ColorSensor sensor = new ColorSensor(PUERTO_COLOR);
		ClasificadorPelotas clasificador = new ClasificadorPelotas(sensor);
		NXTCommConnector conector = RS485.getConnector();
		NXTConnection conn;
		DataInputStream dis;
		DataOutputStream dos;
		int lectura;
		NXTRegulatedMotor motor_sensor = Motor.A;
		motor_sensor.resetTachoCount();
		motor_sensor.setSpeed(200);
		motor_sensor.rotateTo(-4);
		NXTRegulatedMotor motor_pateador = Motor.C;
		motor_pateador.resetTachoCount();
		
		while(true){
			conn = conector.waitForConnection(0, NXTConnection.PACKET);
			Sound.twoBeeps();
			dis = conn.openDataInputStream();
			dos = conn.openDataOutputStream();

			while (true) {
				try {
					lectura = dis.readInt();
					
					if(lectura == SENSAR){
						// Se mueve el brazo del sensor a la posicion de sensar
						motor_sensor.setSpeed(200);
						motor_sensor.setAcceleration(3000);
						motor_sensor.rotateTo(1);
						
						// Sensa y si es NADA acomoda el brazo para caminar y sigue
						int color = clasificador.getColor();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(color == ClasificadorPelotas.NADA){
							motor_sensor.rotateTo(-4);
							// Termino la conexion
							break;
						}
						
						// Le devuelvo el color sensado si es azul o naranja
						dos.writeInt(clasificador.getSensado());
						dos.flush();
					}else if(lectura == PATEAR){
						// Acomodo el sensor
						motor_sensor.setSpeed(300);
						motor_sensor.rotateTo(-80);

						// Acomodo el pateador
						motor_pateador.setSpeed(100);
						motor_pateador.rotateTo(-36);

						// Pateo, si es naranja fuerte y si es azul despacio
						if (clasificador.getSensado() == ClasificadorPelotas.NARANJA)
							motor_pateador.setSpeed(900);

						motor_pateador.rotateTo(20);

						// Vuelvo el pateador a la posicion inical
						motor_pateador.setSpeed(100);
						motor_pateador.rotateTo(-2);

						// Reseteo el sensor de color
						clasificador.resetSensado();
						
						// Le aviso que termine
						dos.writeInt(PATEAR);
						dos.flush();
					}else{
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					break;
				}
			}
			
			try {
				dis.close();
				dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}

			conn.close();
		
		}
	}
}
