package jcolonia.daw2021.rifa;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * Gestión de menú con opciones numeradas sobre consola de texto.
 * 
 * @author <a href="dmartin.jcolonia@gmail.com">David H. Martín</a>
 * @version 1.0 (20220510)
 */
public class ControlRifa {
	private List<Fetiche> listaFetiches;
//	- listaFetiches: List<String>
	
/**
 * El número de intentos totales.
 */
	private int númIntentos;
	
	/**
	 * El número de aciertos.
	 */
	private int númAciertos;
	
	private Scanner entrada;
//	- entrada: Scanner

	VistaMenúBásico vista;
	private static final String[] OPCIONES_MENÚ_PRINCIPAL = { "Elegir objeto", "Salir" };

	public ControlRifa(Scanner entrada) {
		this.entrada = entrada;
		vista = new VistaMenúBásico("Rifa / Menú principal", entrada, OPCIONES_MENÚ_PRINCIPAL);
		listaFetiches = new Vector<Fetiche>();
	}
//	+ ControlNombres(entrada: Scanner)

	@Override
	public String toString() {
		String texto;
		int tamaño = (listaFetiches == null) ? 0 : listaFetiches.size();

		texto = String.format("Se han sorteado %d fetiches", tamaño);
		return texto;
	}
//	+ toString(): String

//	+ buclePrincipal(): void
	public void buclePrincipal() {
		int opciónElegida;
		boolean fin = false;

		do {
			vista.mostrarTítulo1();
			vista.mostrarOpciones();
			opciónElegida = vista.pedirOpción();

			switch (opciónElegida) {
			case 1:
				gestiónSorteo();
				break;
			case 2:
				fin = true;
				Vista.mostrarTexto("\n\n«Programa finalizado»\n\n");
				break;
			default:
				operaciónNoImplementada();
				break;
			}
		} while (!fin);
	}

//	+ gestiónSorteo(): void
	public void gestiónSorteo() {
		VistaMenúBásico vistaSorteo;
		Fetiche objetoElegido, objetoGanador;

		int opción;
		String mensaje;
		

		vistaSorteo = new VistaMenúBásico("Pedir boleto", entrada, Fetiche.nombres());

		vistaSorteo.mostrarTítulo2();
		vistaSorteo.mostrarOpciones();
		opción = vistaSorteo.pedirOpción();

		objetoElegido = Fetiche.values()[opción - 1];
		objetoGanador = Fetiche.sortear();
		listaFetiches.add(objetoGanador);


		mensaje = String.format("   ** ¡Ha salido «%s»!", objetoGanador);
		Vista.mostrarTexto(mensaje);

		if (objetoElegido == objetoGanador) {
			mensaje = "   ** ¡Mala suerte le llueve a tu compañero!";
			númIntentos++;
			númAciertos++;
		} else {
			mensaje = "   ** ¡Suerte que te libraste!";
			númIntentos++;
		}
		
		String textoFinal;
		textoFinal = String.format("%nHa tenido un total de %d aciertos sobre %d intentos", númAciertos,númIntentos);
		Vista.mostrarTexto(mensaje);
		mostrarHistórico();
		//Los aciertos conseguidos.
		Vista.mostrarTexto(textoFinal);

		vistaSorteo.pedirContinuar();
	}
	/**
	 * Muestra los últimos objetos ganadores.
	 */
	public void mostrarHistórico() {
		System.out.println("");
		System.out.println("Históricos de sorteo");
		System.out.println("--------------------");
		for(Fetiche tipo: listaFetiches) {
			
			System.out.println(tipo);
		}
}

	public void operaciónNoImplementada() {
		Vista.mostrarTexto(" --> Operación no implementada todavía…\n");
		Vista.esperar(2000); // 2 s.
	}
//	+ operaciónNoImplementada(): void

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		ControlRifa control = new ControlRifa(entrada);

		control.buclePrincipal();
		entrada.close();
		
	}
//	+ {static} main(argumentos: String[]): void

}
