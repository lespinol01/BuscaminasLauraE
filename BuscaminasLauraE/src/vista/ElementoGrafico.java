package vista;

import javax.swing.Icon;

public class ElementoGrafico {
	private boolean ocultado;
	private boolean senalado;
	private int valor;
	private boolean isMina;
	
	public ElementoGrafico(boolean mostrada, boolean senalada, int valor,boolean isMina) {
		super();
		this.ocultado = mostrada;
		this.senalado = senalada;
		this.valor = valor;
		this.isMina = isMina;
	}
	public boolean isOcultado() {
		return ocultado;
	}
	public boolean isSenalada() {
		return senalado;
	}
	public int getValor() {
		return valor;
	}
	public boolean isMina() {
		return isMina;
	}
	
	
}
