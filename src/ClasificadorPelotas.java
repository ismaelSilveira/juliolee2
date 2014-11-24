import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;

public class ClasificadorPelotas {
	public final static int NADA = 6;
	public final static int AZUL = 1;
	public final static int NARANJA = 2;
	private int color_sensado;
	private ColorSensor sensor_color;
	private Propiedad[] propiedades;
	// La siguiente es una tabla de probabilidades de que los vectores (R,G,B)
	// de cada clase verifiquen las propiedades que se chequearan.
	// Cada arreglo de probabilidades_ocurrencia representa las probabilidades
	// por clase para cada propiedad. Cada elemento de cada arreglo representa
	// la probabilidad de cada clase. Las clases son (en orden): 
	// Azul, Naranja A, Naranja N, Nada.
	// Por ejemplo probabilidades_ocurrencia[0][0] indica la probabilidad de que
	// dada una pelota celeste, los valores leidos por el sensor RGB cumplan la
	// propiedad G > R > B.
	// probabilidades_ocurrencia[3][4] indica la probabilidad de que el valor
	// devuelto por el sensor cumpla |G - R| > |B - R|, en ausencia de pelotas.
	// Esas probabilidades se estimaron experimentalmente de una muestra de
	// mediciones.
	// Ademas los numeros van de 0 a 10 en lugar de 0 a 1 para evitar errores
	// de redondeo.
	private double[][] probabilidades_ocurrencia = { 
			{ 0, 9.9, 0.4, 1.2 },// G > R > B
			{ 9.5, 10, 1.1, 10 }, // B < 120
			{ 0.2, 0.1, 9.9, 4.5 }, // (B + G)/2 > R
			{ 9.6, 0.1, 5.4, 4.2 }, // |G - R| > |B - R|
			{ 0, 9.9, 5.4, 3.9 }, // G > B
			{ 0, 9.9, 9.9, 3.3 }, // G > R
			{ 0, 7.5, 4.2, 0 }, // G > 150
			{ 1.7, 0, 9.9, 10 }, // |G - B| < 19
			{ 0, 0, 0, 10 }, // B < 30
			{ 0, 0, 1.2, 3.3 },// dist(plano ajuste celestes, (R,G,B)) < 6
			{ 0, 0, 9.9, 0 } // dist(plano ajuste naranja N, (R,G,B)) < 6

	};

	public ClasificadorPelotas(ColorSensor cs) {
		sensor_color = cs;
		cs.setFloodlight(Color.BLUE);
		propiedades = new Propiedad[11];
		propiedades[0] = new GMayorRMayorB();
		propiedades[1] = new BMenor120();
		propiedades[2] = new PromBGMayorR();
		propiedades[3] = new DistanciaGRMayorDistanciaBR();
		propiedades[4] = new GMayorB();
		propiedades[5] = new GMayorR();
		propiedades[6] = new GMayor150();
		propiedades[7] = new DistanciaGBMenor19();
		propiedades[8] = new BMenor30();
		propiedades[9] = new DistanciaPlanoCelestesMenor6();
		propiedades[10] = new DistanciaPlanoNaranjaNMenor6();
	}

	public int getColor() {
		switch (clasificarRGB()) {
		case 0: // Celeste
			color_sensado = AZUL;
			break;
		case 1: // Naranja A
		case 2: // Naranja N
			color_sensado = NARANJA;
			break;
		case 3: // Nada
			color_sensado = NADA;
		}
		return color_sensado;
	}

	private int clasificarRGB() {
		// El algoritmo de clasificación inicia asumiendo que el color medido
		// puede pertenecer a cualquier
		// categoría con la misma probabilidad. Para esto se le asigna un mismo
		// "peso" a cada categoría.
		// Luego va ponderando de acuerdo a las probabilidades en
		// probabilidades_ocurrencia, multiplicando
		// cada probabilidad por el peso asignado en el paso previo. Estos
		// valores no son las probabilidades de que la
		// medida pertenezca a cada una de las categorías, porque los eventos
		// que se están midiendo no son independientes (por ejemplo B < 30 => B
		// < 120).
		// De todas formas los resultados obtenidos experimentalmente para los
		// datos de muestra son satisfactorios, como explica el informe.

		Color color = sensor_color.getColor();
		int R = color.getRed();
		int G = color.getGreen();
		int B = color.getBlue();

		double[] pesos = { 1, 1, 1, 1 }; // Ponderaciones iniciales

		// Pondero de acuerdo a las probabilidades de que cumpla cada
		// propiedad...
		for (int i = 0, len = propiedades.length; i < len; i++) {
			double[] probabilidades_prop = probabilidades_ocurrencia[i];
			if (propiedades[i].verifica(R, G, B)) {
				// ... para cada categoria
				for (int j = 0, len2 = probabilidades_prop.length; j < len2; j++) {
					pesos[j] *= probabilidades_prop[j];
				}
			} else {
				// La probabilidad de que no se cumpla la propiedad es el
				// complemento
				for (int j = 0, len2 = probabilidades_prop.length; j < len2; j++) {
					pesos[j] *= 10 - probabilidades_prop[j];
				}
			}
		}

		int indice_maximo = 0;
		double maximo = 0;
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
