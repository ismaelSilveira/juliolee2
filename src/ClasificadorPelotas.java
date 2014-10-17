import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;

public class ClasificadorPelotas {
	private ColorSensor sensor;
	public static int NADA = 0;
	public static int AZUL = 1;
	public static int NARANJA = 2;
	private int color_sensado;
	private Propiedad[] propiedades;
	// La siguiente es una tabla de probabilidades de que los vectores (R,G,B)
	// de cada clase verifiquen las propiedades que se chequearan.
	// Cada arreglo de probabilidades_ocurrencia representa las probabilidades
	// por clase para cada propiedad. Cada elemento de cada arreglo representa
	// la probabilidad de cada clase. Las clases son (en orden): Celeste,
	// Azul, Naranja A, Naranja N, Nada.
	// Por ejemplo probabilidades_ocurrencia[0][0] indica la probabilidad de que
	// dada una pelota celeste, los valores leídos por el sensor RGB cumplan la
	// propiedad G > R > B.
	// probabilidades_ocurrencia[3][4] indica la probabilidad de que el valor
	// devuelto por el sensor cumpla |G - R| > |B - R|, en ausencia de pelotas.
	// Esas probabilidades se estimaron experimentalmente de una muestra de
	// mediciones.
	// Ademas los numeros van de 0 a 100 en lugar de 0 a 1 para evitar errores
	// de redondeo.
	private int[][] probabilidades_ocurrencia = { { 44, 0, 99, 4, 12 }, // G > R
																		// > B
			{ 0, 95, 100, 11, 100 }, // B < 120
			{ 95, 2, 1, 99, 45 }, // (B + G)/2 > R
			{ 95, 96, 1, 54, 42 }, // |G - R| > |B - R|
			{ 99, 0, 99, 54, 39 }, // G > B
			{ 99, 0, 99, 99, 33 }, // G > R
			{ 98, 0, 75, 42, 0 }, // G > 150
			{ 7, 17, 0, 99, 100 }, // |G - B| < 19
			{ 0, 0, 0, 0, 100 } // B < 30
	};

	public ClasificadorPelotas(ColorSensor cs) {
		sensor = cs;
		propiedades = new Propiedad[9];
		propiedades[0] = new GMayorRMayorB();
		propiedades[1] = new BMenor120();
		propiedades[2] = new PromBGMayorR();
		propiedades[3] = new DistanciaGRMayorDistanciaBR();
		propiedades[4] = new GMayorB();
		propiedades[5] = new GMayorR();
		propiedades[6] = new GMayor150();
		propiedades[7] = new DistanciaGBMenor19();
		propiedades[8] = new BMenor30();
	}

	public int getColor() {
		Color color = sensor.getColor();
		int R = color.getRed();
		int G = color.getGreen();
		int B = color.getBlue();
		switch (clasificarRGB(R, G, B)) {
		case 0: // Celeste
		case 1: // Azul
			color_sensado = AZUL;
			break;
		case 2: // Naranja A
		case 3: // Naranja N
			color_sensado = NARANJA;
			break;
		case 4: // Nada
			color_sensado = NADA;
		}
		return color_sensado;
	}

	private int clasificarRGB(int R, int G, int B) {
		// El algoritmo de clasificación inicia asumiendo que el color medido
		// puede pertenecer a cualquier
		// categoría con la misma probabilidad. Para esto se le asigna un mismo
		// "peso" a cada categoría.
		// Luego va ponderando de acuerdo a las probabilidades en
		// probabilidades_ocurrencia, multiplicando
		// cada probabilidad por el peso asignado en el paso previo. Estos
		// valores no son las probabilidades de que la
		// medida pertenezca a cada una de las categorías, porque los eventos
		// que se están midiendo no son
		// independientes (por ejemplo B < 30 => B < 120).

		int[] pesos = { 1, 1, 1, 1, 1 }; // Ponderaciones iniciales

		// Pondero de acuerdo a las probabilidades de que cumpla cada
		// propiedad...
		for (int i = 0, len = propiedades.length; i < len; i++) {
			int[] probabilidades_prop = probabilidades_ocurrencia[i];
			if (propiedades[i].verifica(R, G, B)) {
				// ... para cada categoria
				for (int j = 0, len2 = probabilidades_prop.length; j < len2; j++) {
					pesos[j] *= probabilidades_prop[j];
				}
			} else {
				// La probabilidad de que no se cumpla la propiedad es el
				// complemento
				for (int j = 0, len2 = probabilidades_prop.length; j < len2; j++) {
					pesos[j] *= 100 - probabilidades_prop[j];
				}
			}
		}
		
		int indice_maximo = 0;
		int maximo = 0;
		for (int i = 0, len = pesos.length; i < len; i++) {
			if (maximo < pesos[i]) {
				indice_maximo = i;
				maximo = pesos[i];
			}
		}
			
		return indice_maximo;
	}

	public int getSensado() {
		return color_sensado;
	}

	public void resetSensado() {
		color_sensado = NADA;
	}

}
