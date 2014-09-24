import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class JulioLee2 {
	private static SensorPort PUERTO_COLOR = SensorPort.S3;

	public static void main(String[] args) {
		// Inicializo el sensor
		ColorSensor sensor = new ColorSensor(PUERTO_COLOR);
		ClasificadorPelotas clasificador = new ClasificadorPelotas(sensor);
		
		// Inicializacion de comportamientos
		Behavior sensar = new Sensar(clasificador, Motor.A);
		Behavior patear = new Patear(clasificador, Motor.A, Motor.C);
		Behavior[] comportamientos = { sensar, patear};
				
		Arbitrator arbitro = new Arbitrator(comportamientos);
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}
		LCD.drawString("Arranca", 0, 0);
		arbitro.start();
	}
}
