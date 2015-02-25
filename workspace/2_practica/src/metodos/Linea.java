package metodos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Linea {
	public String etiqueta, codop, operando, li, tipo_error = "0", cad_modos = "";
	byte num_linea;
	boolean band = false, lleva_op = true;
	public Map<String, MiTabop> tabop = new TreeMap<String, MiTabop>();
	public ArrayList<String> modos = new ArrayList<String>();

	public Linea(byte n_l, String l, Map<String, MiTabop> t ) {//constructor
		num_linea = n_l;
		li = l;
		tabop = t;
	}
	/**
	 * EL método "SepararLinea" elimina los posibles comentarios usando StrigTokenizer con ; como separador.
	 * Toma la primera parte antes del ";" y lo demás lo ignora (por ser comentario).
	 * Se utiliza el "replaceAll" para eliminar los tabuladores y poner espacios en blanco en su lugar.
	 * Con otro StringTokenizer separa por espacios y una vez que identifica de que tipo es el token, lo 
	 * manda analizar a su respectiva función.
	 */
	public void SepararLinea() throws IOException{//Separar linea en tokens: etiqueta, codop y operando
		
		StringTokenizer st1 = new StringTokenizer(li,";");//quita los comentarios
		String parte1 = st1.nextToken();
		parte1 = parte1.replaceAll("\t", " ");
		StringTokenizer st = new StringTokenizer(parte1," ");
		String parte;
		//String cad_modos = "";
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
				if(etiqueta==null&&codop==null){//línea vacía
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
					codop = null; //null
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
				if(tipo_error.equals("0")){
					if(st.hasMoreTokens()){
						tipo_error = "6";
					}
				}	
			}//si no hay error en la etiqueta
		}
		if(codop!=null){
			if(codop.toUpperCase().equals("ORG")||codop.toUpperCase().equals("END")){//validar directivas
				Salida ins = new Salida(num_linea,etiqueta,codop,operando,cad_modos); //enviar a imprimir
				ins.ImprimeInst();
			}else if(tipo_error.equals("0")){//si no ha habido errores
				if(codop!=null){
					BuscarEnTabop();
					//si el Arraylist está vacío es que no encontró el codop
					if(!modos.isEmpty()){ //si no está vacío
						Iterator<String> ite = modos.iterator();
						String elemento = ite.next();
						
						cad_modos = elemento;
						while(ite.hasNext()){
							elemento = ite.next();
							cad_modos = (cad_modos+", "+elemento); //guardar modos en string
						}
						if(lleva_op && operando==null){//si lleva operando y no tiene
							tipo_error = "8";
						}else if(lleva_op==false && operando !=null){//no lleva operando y sí tiene
							tipo_error = "9";
						}else{
							Salida ins = new Salida(num_linea,etiqueta,codop,operando,cad_modos); //enviar modos a imprimir
							ins.ImprimeInst();
						}	
					}else{
						tipo_error = "7";
					}	
				}
			}
		}//if para validar codops en tabop
		if(tipo_error != "0"){ //si hubo error en la linea
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
	 * si no, guarda el numero de error correspondiente en "tipo_error"
	 */
	public void ValidaEtiqueta(String e){
	
		if(e.matches("^[A-Za-z]{1}[a-zA-Z0-9_]{0,7}")){
			etiqueta = e;
			System.out.println("ETIQUETA: "+etiqueta);
		}else{
			tipo_error = "1";
		}	
	}
	/**
	 * @param co
	 * Recibe una cadena y la compara contra la expresión regular
	 * si es válida guarda el resultado en la variable global "codop"
	 * si no, guarda el numero de error correpondiente en "tipo_error"
	 * También pone la bandera en true si el codop es un "end" para detener el programa.
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
	}
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
	/**
	 * Se utiliza un iterador para buscar en el map.
	 * Si encuentra la instucción buscada, guarda en un arreglo los modos de direccionamiento y 
	 * en una variable booleana si debe o no tener operando.
	 * 
	 */
	public void BuscarEnTabop(){
		
		Iterator<String> it = tabop.keySet().iterator(); //iterador para buscar en el map
		while(it.hasNext()){
			String llave = it.next();
			MiTabop valor = tabop.get(llave); //guarda el valor de esa llave
			String val = valor.getInst();// regresa la instruccion guardada en el valor
			if(val.equals(codop.toUpperCase())){//buscar todos los valores con cierto codop
				MiTabop fgh = tabop.get(llave);
				String modo = fgh.getModo(); //OBTENER MODO DE DIRECCIONAMIENTO
				System.out.print(" "+ modo);
				modos.add(modo);//guardar modos
				String op = fgh.getB_xcal();
				if(op.equals("0")){ //si los bytes por calcular son igual a cero
					lleva_op = false;//no lleva operando
				}else if(!op.equals("0")){
					lleva_op = true; //si lleva operando
				}
			}
		}//while	
	}//BuscarEnTabop
}
