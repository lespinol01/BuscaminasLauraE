package model;

import utiles.Utiles;

public class Tablero {

	private Casilla[][] casillas;

	public Tablero(int lado, int numeroBombas) {
		super();
		crearTablero(lado);
		colocarMinas(lado, numeroBombas);
	}

	public Casilla[][] getCasillas() {
		return casillas;
	}

	private void establecerMinasAlrededor(Coordenada posicionMinaCoordenada) {
		int posX = posicionMinaCoordenada.getPosX();
		int posY = posicionMinaCoordenada.getPosY();
		int lado = casillas.length;

		for (int i = posX - 1; i <= posX + 1; i++) {
			for (int j = posY - 1; j <= posY + 1; j++) {
				Coordenada casillasAlrededor = new Coordenada(i, j);
				if (!casillasAlrededor.equals(posicionMinaCoordenada) && isDentroLimites(casillasAlrededor)) {
					Casilla casillaAlrededorSeleccionada = getCasilla(casillasAlrededor);
					if (!casillaAlrededorSeleccionada.isMina()) {
						casillaAlrededorSeleccionada
								.setMinasAlrededor(casillaAlrededorSeleccionada.getMinasAlrededor() + 1);
					}
				}
			}
		}
	}

	private boolean isDentroLimites(Coordenada alrededor) {
		return alrededor.getPosX() >= 0 && alrededor.getPosX() < casillas.length && alrededor.getPosY() >= 0
				&& alrededor.getPosY() < casillas.length;
	}

	private void colocarMinas(int lado, int numeroBombas) {
		int tamano = 2;
		int posiciones[][] = new int[numeroBombas][tamano];
		damePosicionesAleatorias(lado, posiciones);

		for (int i = 0; i < posiciones.length; i++) {
			for (int j = 0; j < posiciones[i].length - 1; j++) {
				Coordenada coordenada = new Coordenada(posiciones[i][j], posiciones[i][j + 1]);

				if (getCasilla(coordenada).isMina()) {
					do {
						dameOtraPosicionAleatoria(lado, posiciones, i);
						coordenada = new Coordenada(posiciones[i][j], posiciones[i][j + 1]);
					} while (getCasilla(coordenada).isMina());
				}
				getCasilla(coordenada).setMina(true);
				establecerMinasAlrededor(coordenada);
			}
		}
	}

	private void dameOtraPosicionAleatoria(int lado, int[][] posiciones, int posicion) {
		int longitud = 2;
		for (int i = 0; i < longitud; i++) {
			posiciones[posicion][i] = Utiles.dameNumero(lado);
		}
	}

	private void damePosicionesAleatorias(int lado, int[][] posiciones) {
		for (int i = 0; i < posiciones.length; i++) {
			for (int j = 0; j < posiciones[i].length; j++) {
				posiciones[i][j] = Utiles.dameNumero(lado);
			}
		}
	}

	private void crearTablero(int lado) {
		this.casillas = new Casilla[lado][lado];
		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas.length; j++) {
				casillas[i][j] = new Casilla();
			}
		}
	}

	// TODO antes todo esto era private
	public Casilla getCasilla(Coordenada posicion) {
		return casillas[posicion.getPosX()][posicion.getPosY()];
	}

	private void setMina(Coordenada posicion, boolean bandera) {
		getCasilla(posicion).setMina(bandera);
	}

	private boolean isMina(Coordenada posicion) {
		return getCasilla(posicion).isMina();
	}

	public void desvelarCasilla(Coordenada coordenada) {
		int posX = coordenada.getPosX();
		int posY = coordenada.getPosY();
		Casilla casillaPrincipal = getCasilla(coordenada);

		if (casillaPrincipal.isVelada() && !casillaPrincipal.isMarcada()) {
			casillaPrincipal.setVelada(false);

			for (int i = posX - 1; i <= posX + 1; i++) {
				for (int j = posY - 1; j <= posY + 1; j++) {
					Coordenada coordenadasAlrededor = new Coordenada(i, j);
					if (casillaPrincipal.getMinasAlrededor() == 0 && isDentroLimites(coordenadasAlrededor)
							&& !casillaPrincipal.isMina() && !coordenada.equals(coordenadasAlrededor)) {
						desvelarCasilla(coordenadasAlrededor);
					}
				}
			}

		}

		if (!casillaPrincipal.isVelada() && !casillaPrincipal.isMarcada()) {
			int contadorMarcadas = 0;
			for (int i = posX - 1; i <= posX + 1; i++) {
				for (int j = posY - 1; j <= posY + 1; j++) {
					Coordenada coordenadaAlrededor = new Coordenada(i, j);
					if (isDentroLimites(coordenadaAlrededor) && !coordenada.equals(coordenadaAlrededor)
							&& getCasilla(coordenadaAlrededor).isMarcada()) {
						contadorMarcadas++;
					}
				}
			}

			if (casillaPrincipal.getMinasAlrededor() == contadorMarcadas) {
				for (int i = posX - 1; i <= posX + 1; i++) {
					for (int j = posY - 1; j <= posY + 1; j++) {
						Coordenada coordenadaAlrededor = new Coordenada(i, j);
						if (isDentroLimites(coordenadaAlrededor) && !coordenada.equals(coordenadaAlrededor)
								&& getCasilla(coordenadaAlrededor).isVelada()) {
							desvelarCasilla(coordenadaAlrededor);
						}
					}
				}
			}
		}

	}

	public boolean marcarCasilla(Coordenada posicion) {
		Casilla casilla = getCasilla(posicion);
		return casilla.marcar();
	}

	public boolean perderPartida() {
		boolean perder = false;
		for (int i = 0; i < getCasillas().length && !perder; i++) {
			for (int j = 0; j < getCasillas().length && !perder; j++) {
				Coordenada coordenada = new Coordenada(i, j);
				if (isDentroLimites(coordenada) && getCasilla(coordenada).isMina()
						&& !getCasilla(coordenada).isVelada()) {
					perder = true;
				}

			}
		}
		
		if (perder) {
			for (int i = 0; i < casillas.length; i++) {
				for (int j = 0; j < casillas.length; j++) {
					getCasilla(new Coordenada(i, j)).setVelada(false);
				}
			}
		}

		return perder;
	}
	
	public boolean ganarPartida() {
		boolean ganar = true;
		for (int i = 0; i < getCasillas().length; i++) {
			for (int j = 0; j < getCasillas().length; j++) {
				Coordenada coordenada = new Coordenada(i, j);
				if (getCasilla(coordenada).isVelada() && !getCasilla(coordenada).isMina()) {
					ganar = false;
				}
				if (getCasilla(coordenada).isVelada() && getCasilla(coordenada).isMina() 
						&& !getCasilla(coordenada).isMarcada()) {
					ganar = false;
				}
				
				if (getCasilla(coordenada).isMina() && !getCasilla(coordenada).isVelada()) {
					ganar = false;
				}
			}
		}
		
		return ganar;
	}

}
