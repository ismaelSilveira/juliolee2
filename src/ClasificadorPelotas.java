import lejos.nxt.ColorSensor;

public class ClasificadorPelotas {
	private ColorSensor sensor;
	public static int NADA = 0;
	public static int AZUL = 1;
	public static int NARANJA = 2;
	
	public ClasificadorPelotas(ColorSensor cs) {
		sensor = cs;
	}
	
	public int getColor () {
		return (int) (Math.random() * 3) + 1;
	}
}
