import lejos.nxt.NXTRegulatedMotor;
import lejos.robotics.subsumption.Behavior;

public class Patear implements Behavior {
	ClasificadorPelotas clasificador;
	NXTRegulatedMotor motor_pateador;

	public Patear(ClasificadorPelotas clasificador, NXTRegulatedMotor motor_pateador) {
		this.clasificador = clasificador;
		this.motor_pateador = motor_pateador;		
	}

	@Override
	public boolean takeControl() {
		return ((clasificador.getSensado() == ClasificadorPelotas.AZUL) || (clasificador.getSensado() == ClasificadorPelotas.NARANJA));
	}

	@Override
	public void action() {
	}

	@Override
	public void suppress() {
	}

}
