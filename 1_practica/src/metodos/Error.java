package metodos;
/**
 * Recibe como par�metro un n�mero, lo busca en el switch y regresa
 * una cadena con la descripci�n del error
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
		case "2": tipo = "Error en el c�digo de operaci�n";
			break;
		case "3": tipo = "Error en el operando";
			break;
		case "4": tipo = "No hay c�digo de operaci�n";
			break;
		case "5": tipo = "No se encuentra el END";
			break;
		case "6": tipo = "Error en el n�mero de tokens";
		}
		return tipo;
	}
}
