
public class DistanciaGBMenor19 implements Propiedad {

	@Override
	public boolean verifica(int R, int G, int B) {
		int dGB = G > B ? G - B : B - G; 
		return dGB < 19;
	}

}
