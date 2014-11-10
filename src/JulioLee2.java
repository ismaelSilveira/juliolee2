import lejos.nxt.Button;
import lejos.nxt.ButtonListener;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.LnrActrFirgelliNXT;

public class JulioLee2 {
	private static SensorPort PUERTO_COLOR = SensorPort.S3;
	private static SensorPort PUERTO_DISTANCIA = SensorPort.S2;

	public static void main(String[] args) {

		// Estiro el actuador lineal e inicio thread que hace polling de el valor
		LnrActrFirgelliNXT actuador = new LnrActrFirgelliNXT(MotorPort.B);
		SensorDistancia distancia = new SensorDistancia(actuador, PUERTO_DISTANCIA);
		distancia.start();

		// Inicializo el sensor
		ColorSensor sensor = new ColorSensor(PUERTO_COLOR);
		ClasificadorPelotas clasificador = new ClasificadorPelotas(sensor);

		// Motor para el brazo del sensor
		NXTRegulatedMotor motor_sensor = Motor.A;
		motor_sensor.resetTachoCount();
		motor_sensor.setSpeed(200);
		motor_sensor.rotateTo(30);

		// Motor del pateador
		NXTRegulatedMotor motor_pateador = Motor.C;
		motor_pateador.resetTachoCount();

		// Inicio la comunicacion
		Comunicacion com = new Comunicacion();
		com.start();

		// Evento que realiza el apagado correcto, bajando el actuador lineal y
		// vuelvo a la posicion inicial el brazo del sensor de color
		Button.LEFT.addButtonListener(new ButtonListener() {
			@Override
			public void buttonReleased(Button b) {
				actuador.move(-200, false);
				motor_sensor.rotateTo(0);
			}

			@Override
			public void buttonPressed(Button b) {

			}
		});

		while (true) {
			if (com.getLectura() == Comunicacion.SENSAR) {
				// Se mueve el brazo del sensor a la posicion de sensar
				motor_sensor.setSpeed(300);
				motor_sensor.rotateTo(0);

				// Sensa y si es NADA acomoda el brazo para caminar y
				// sigue
				if (clasificador.getColor() == ClasificadorPelotas.NADA) {
					motor_sensor.rotateTo(30);
					// Termino la conexion
					LCD.drawString("NADA", 2, 0);
				}

				com.comunicar(clasificador.getSensado());

			} else if (com.getLectura() == Comunicacion.PATEAR) {
				// Acomodo el sensor
				motor_sensor.setSpeed(900);
				motor_sensor.rotateTo(300);

				// Acomodo el pateador
				motor_pateador.setSpeed(50);
				motor_pateador.rotateTo(-40);

				// Pateo, si es naranja fuerte y si es azul despacio
				if (clasificador.getSensado() == ClasificadorPelotas.NARANJA) {
					// Sound.beep();
					motor_pateador.setSpeed(450);
					motor_pateador.rotateTo(10);
				} else {
					motor_pateador.rotate(0);
				}

				// Vuelvo el pateador a la posicion inical
				motor_pateador.setSpeed(100);
				motor_pateador.rotateTo(-2);

				// Reseteo el sensor de color
				clasificador.resetSensado();

				// Le aviso que termine
				com.comunicar(Comunicacion.PATEAR);
			} else if (com.getLectura() == Comunicacion.DISTANCIA) {
				com.comunicar(distancia.getDistancia());
			}
		}
	}
}
