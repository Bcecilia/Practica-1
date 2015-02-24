package metodos;

import java.io.IOException;
//import java.util.ArrayList;
import java.util.StringTokenizer;

public class Linea {
	public String etiqueta, codop, operando, li, tipo_error = "0";
	byte num_linea;
	boolean band = false;

	public Linea(byte n_l, String l) {//constructor
		num_linea = n_l;
		li = l;
	}
	/**
	 * EL método "SepararLinea" elimina los posibles comentarios usando StrigTokenizer y el ; como separador
	 * toma la primera parte antes del ";" y lo demás lo ignora.
	 * Se utiliza el "replaceAll" para eliminar los tabuladores y poner espacios en blanco en su lugar.
	 * Con otro StringTokenizer separa por espacios y una vez que identifica de que tipo es el token, lo 
	 * manda analizar a su respectiva función.
	 */
	public void SepararLinea() throws IOException{//Separar linea en tokens: etiqueta, codop y operando (ignora comentarios)
		
		StringTokenizer st1 = new StringTokenizer(li,";");//quita los comentarios
		String parte1 = st1.nextToken();
		parte1 = parte1.replaceAll("\t", " ");
		StringTokenizer st = new StringTokenizer(parte1," ");
		String parte;
		if(Character.isWhitespace(li.charAt(0))){ //si comienza con espacio en blanco 
			etiqueta = null;
			System.out.println("ETIQUETA: "+ etiqueta);
			if(st.hasMoreTokens()){
				parte= st.nextToken();
				ValidaCodop(parte);
			}else{
				codop = null;
				System.out.println("CODOP: "+codop);
				System.out.println("\tError: siempre debe haber un código de operación");
				tipo_error = "4";
				if(etiqueta==null&&codop==null){
					tipo_error = "0";
				}
			}
			if(st.hasMoreTokens()&&tipo_error.equals("0")){
				parte= st.nextToken();
				ValidaOperando(parte);
			}else{
				operando = null; 
				System.out.println("OPERANDO:"+ operando);
			}
			if(st.hasMoreTokens()){ //si hay un cuarto token
				tipo_error = "6";
			}
		}else{//si no comienza con espacio en blanco
			parte= st.nextToken();
			ValidaEtiqueta(parte);
			if(tipo_error.equals("0")){//si no hay error en la etiqueta
				if(st.hasMoreTokens()){
					parte= st.nextToken();
					ValidaCodop(parte);
				}else{
					codop = null;
					System.out.println("CODOP: "+codop);
					System.out.println("\tError: siempre debe haber un código de operación");
					tipo_error = "4";
				}
				if(tipo_error.equals("0")){//si no hay error en el codop
					if(st.hasMoreTokens()){
						parte= st.nextToken();
						ValidaOperando(parte);
					}else{
						operando = null;
						System.out.println("OPERANDO: "+operando);
					}
					
				}//si no hay error en el codop
				if(st.hasMoreTokens()){
					tipo_error = "6";
				}
			}//si no hay error en la etiqueta
			
		}
		if(tipo_error.equals("0")){
			if(codop!=null){
				Salida ins = new Salida(num_linea,etiqueta,codop,operando);
				ins.ImprimeInst();
			}
		}
		else{
			Error h = new Error(tipo_error);
			String detalle = h.TipoDeError();
			Salida s = new Salida(num_linea, detalle);
			s.ImprimeError();
		}
	}//Fin SepararLinea
	
	/** 
	 * @param e
	 * Recibe una cadena y la compara contra la expresión regular
	 * si es válida guarda el resultado en la variable global "etiqueta"
	 * si no, guarda el numero de error correpondiente en "tipo_error"
	 */
	public void ValidaEtiqueta(String e){
	
		if(e.matches("^[A-Za-z]{1}[a-zA-Z0-9_]{0,7}")){
			etiqueta = e;
			System.out.println("ETIQUETA: "+etiqueta);
		}else{
			tipo_error = "1";
		}	
	}//fin ValidarEtiqueta
	/**
	 * @param co
	 * Recibe una cadena y la compara contra la expresión regular
	 * si es válida guarda el resultado en la variable global "codop"
	 * si no, guarda el numero de error correpondiente en "tipo_error"
	 * También pone la bandera en true si el codop es un "end".
	 */
	public void ValidaCodop(String co){
		if(co.length()<=5){
			if(co.matches("(^[a-zA-Z][a-zA-Z]*[.]?[a-zA-Z]*)")){
				codop=co;
				System.out.println("CODOP:"+ codop);
				if(codop.toUpperCase().equals("END")){
					band = true;
				}
			}else{
				tipo_error = "2";
			}	
		}else{
			tipo_error = "2";
		}			
	}//fin ValidarCodop
	/**
	 * @param o
	 * Recibe una cadena y la guarda en la variable "operando"
	 */
	public void ValidaOperando(String o){
		operando = o;
	    System.out.println("OPERANDO:"+ operando);
	}
	/**
	 * Regresa el valor de la bandera que valida el end
	 * @return
	 */
	public boolean ValidaEnd(){
		return band; //regresa el valor de la bandera del end
	}
}
