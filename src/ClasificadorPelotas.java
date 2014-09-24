import lejos.nxt.ColorSensor;

public class ClasificadorPelotas {
	private ColorSensor sensor;
	public static int NADA = 0;
	public static int AZUL = 1;
	public static int NARANJA = 2;
	private int color_sensado;
	
	public ClasificadorPelotas(ColorSensor cs) {
		sensor = cs;
	}
	
	public int getColor () {
		color_sensado = 2;
		return color_sensado;
	}
	
	public int getSensado(){
		return color_sensado;
	}
	
	public void resetSensado(){
		color_sensado = 0;
	}
}
