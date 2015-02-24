package metodos;
/**
 * Recibe como parámetro un número, lo busca en el switch y regresa
 * una cadena con la descripción del error
 */

public class Error {
	String tipo = "";
	String error;
	public Error(String er) {//constructor
		error = er;
	}
	public String TipoDeError(){
		switch(error){
		case "1": tipo = "Error en la etiqueta";
			break;
		case "2": tipo = "Error en el código de operación";
			break;
		case "3": tipo = "Error en el operando";
			break;
		case "4": tipo = "No hay código de operación";
			break;
		case "5": tipo = "No se encuentra el END";
			break;
		case "6": tipo = "Error en el número de tokens";
		}
		return tipo;
	}
}
