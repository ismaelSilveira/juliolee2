import lejos.nxt.Button;
import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Sensar implements Behavior {
	ClasificadorPelotas clasificador;
	NXTRegulatedMotor motor_sensor;

	public Sensar(ClasificadorPelotas clasificador, NXTRegulatedMotor motor_sensor) {
		this.clasificador = clasificador;
		this.motor_sensor = motor_sensor;	
		motor_sensor.resetTachoCount();
		motor_sensor.setSpeed(200);
		motor_sensor.rotateTo(-4);
	}

	@Override
	public boolean takeControl() {
		// TODO: true cuando me aviso el otro brick
		return (Button.readButtons() > 0);
	}

	@Override
	public void action() {
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
			// TODO: avisar al otro brick que siga caminando
		}
	}

	@Override
	public void suppress() {
	}

}
