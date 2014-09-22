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
	
	public void getColor () {
		color_sensado = 1;
	}
	
	public int getSensado(){
		return color_sensado;
	}
}
