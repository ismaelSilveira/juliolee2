public class DistanciaPlanoCelestesMenor6 implements Propiedad {

	@Override
	public boolean verifica(int R, int G, int B) {
		return distPlano(R, G, B) < 6;
	}

	private double distPlano(int r, int g, int b ) {
		double[] c = { -0.10456, 1.01929, -1, -10.10234 }; //coeficientes del plano ajustado por minimos cuadrados
		double[] P = {r, g, b, 1};
		double prod_esc = P[0] * c[0] + P[1] * c[1] + P[2] * c[2] + P[3] * c[3];
		if (prod_esc < 0)
			prod_esc = -prod_esc;
		double norma_vect_norm = Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2] * c[2]); 
		return prod_esc / norma_vect_norm;
	}

}
