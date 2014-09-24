import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Patear implements Behavior {
	ClasificadorPelotas clasificador;
	NXTRegulatedMotor motor_pateador;
	NXTRegulatedMotor motor_sensor;

	public Patear(ClasificadorPelotas clasificador, NXTRegulatedMotor motor_sensor, NXTRegulatedMotor motor_pateador) {
		this.clasificador = clasificador;
		this.motor_pateador = motor_pateador;
		this.motor_sensor = motor_sensor;
		this.motor_pateador.resetTachoCount();
	}

	@Override
	public boolean takeControl() {
		return ((clasificador.getSensado() == ClasificadorPelotas.AZUL) || (clasificador.getSensado() == ClasificadorPelotas.NARANJA));
	}

	@Override
	public void action() {
		// Acomodo el sensor
		motor_sensor.rotateTo(-80);
		
		// Acomodo el pateador
		motor_pateador.setSpeed(100);
		motor_pateador.rotateTo(-36);
		
		//Pateo, si es naranja fuerte y si es azul despacio
		if(clasificador.getSensado() == ClasificadorPelotas.NARANJA)
			motor_pateador.setSpeed(900);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		motor_pateador.rotateTo(20);
		
		// Vuelvo el pateador a la posicion inical
		motor_pateador.setSpeed(100);
		motor_pateador.rotateTo(-2);
		
		// Reseteo el sensor de color
		clasificador.resetSensado();
	}

	@Override
	public void suppress() {
	}

}
