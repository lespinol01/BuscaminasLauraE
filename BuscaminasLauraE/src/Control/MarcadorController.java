package Control;

import model.Coordenada;
import model.Tablero;
import vista.Botonera;

public class MarcadorController {
	Tablero tablero;
	
	public MarcadorController(Tablero tablero) {
		super();
		this.tablero = tablero;
	}



	public boolean marcarCasilla(String name) {
		Coordenada obtenCoordenada = Botonera.obtenCoordenada(name);
		return tablero.marcarCasilla(obtenCoordenada);
	}
}
