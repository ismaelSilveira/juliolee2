import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Sensar implements Behavior {
	ClasificadorPelotas clasificador;
	NXTRegulatedMotor motor_sensor;

	public Sensar(ClasificadorPelotas clasificador, NXTRegulatedMotor motor_sensor) {
		this.clasificador = clasificador;
		this.motor_sensor = motor_sensor;		
	}

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
	}

	@Override
	public void suppress() {
	}

}
