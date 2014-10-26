public class DistanciaPlanoNaranjaNMenor6 implements Propiedad {

	/**
	 * Devuelve true si la distancia del punto (R,G,B) al plano de ajuste por
	 * minimos cuadrados es menor a 6
	 */
	@Override
	public boolean verifica(int R, int G, int B) {
		return distPlano(R, G, B) < 6;
	}

	/**
	 * Calcula la distancia de un punto al plano segun la formula en
	 * http://mathworld.wolfram.com/Point-PlaneDistance.html
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	private double distPlano(int r, int g, int b) {
		// los elementos de c son tales que un punto (x,y,z) pertenece al plano
		// si
		// y solo si z = c[0]*x + c[1]*y + c[2]
		// c es el resultado del ajuste realizado mediante minimos cuadrados en
		// propiedadesPelotas.m
		double[] c = { -0.41292, 1.59088, -1, -39.56286 };
		double[] P = { r, g, b, 1 };
		double prod_esc = P[0] * c[0] + P[1] * c[1] + P[2] * c[2] + P[3] * c[3];
		if (prod_esc < 0)
			prod_esc = -prod_esc;
		double norma_vect_norm = Math.sqrt(c[0] * c[0] + c[1] * c[1] + c[2]
				* c[2]);
		return prod_esc / norma_vect_norm;
	}

}
