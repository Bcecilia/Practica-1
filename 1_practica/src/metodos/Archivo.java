package metodos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Archivo {
	
	public File a = null;
	public String linea = "";
	public byte num_linea = 0;//para contar las lineas le�das
	public boolean b_end = false;
	public boolean bandera = false;
	public int contador = 0;
	public Linea l = null;
	public Archivo(File n){//constructor
		a = n;
	}
	/**
	 * El m�todo "LeerArchivo" lee el archivo linea por linea y por cada linea verifica:
	 * 1. Si no se ha encontrado el "end"
	 * 2. Si es una linea vac�a
	 * 3. Si comienza con punto y coma (que es la primer validaci�n que hay para los comentarios)
	 * Y si no se cumple ninguna de las anteriores manda llamar a la funci�n "SepararLinea" para
	 * separar por tokens.
	 * Cuando ya no hay lineas por leer (o si encuentra el "end")cierra el archivo.
	 */
	public void LeerArchivo() throws IOException{
		FileReader fr = new FileReader(a);
		BufferedReader lector = new BufferedReader(fr);
		while(lector.ready()&& b_end == false){//toma linea por linea hasta el final del archivo y verfica bandera
			num_linea++;
			linea = lector.readLine();
			//System.out.println("Linea "+num_linea+ ": " + linea);
			if(linea.length() > 0){
				for(int i = 0 ; i < linea.length() ; i++){
					if((linea.charAt(i)== ' ') || (linea.charAt(i) == '\t')){
						contador ++;
					}
				}
				if(contador == linea.length()){
					bandera = true;
				}
			}else{
				bandera = true;
			}
		    if(bandera == true)//verifica si es linea vac�a
			{
				System.out.println("linea "+num_linea+": linea vac�a");
			}else if(linea.startsWith(";"))//caso en que el comentario comience con ;
			{
			    System.out.println("linea "+num_linea+": l�nea de comentario");
			}else{
				l = new Linea(num_linea, linea);//enviamos el # de linea y la linea
				l.SepararLinea();
				b_end = l.ValidaEnd();
			    }
		    bandera = false;
		    contador = 0;
		}
		if(b_end == false){
			System.out.println("No se encontr� el end :c");
		}else{
			System.out.println("Si hay end");
		}
		lector.close(); //cerrar el archivo
	}
}
