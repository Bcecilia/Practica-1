package metodos;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * La clase tiene tres constructores, uno recibe los datos para el archivo de intrucciones
 * el otro recibe los datos para el archivo de errores y el tercero recibe el nombre
 * del archivo de entrada
 */
public class Salida {
	byte num;
	String error, et, co, op, nombre_i, nombre_e;
	public Main m = new Main();
	public String nombre = m.NombreArchivo();
	
	public Salida(byte n, String e, String c, String o){
		num = n;
		et = e;
		co = c;
		op = o;
	}
	public Salida(byte num_linea, String detalle_e){
		num = num_linea;
		error = detalle_e;
	}
	public Salida(String nomf){
		nombre_i = nomf.toLowerCase().replace(".asm", ".inst");
		nombre_e = nomf.toLowerCase().replace(".asm", ".err");
	}
	
	public void CrearArchivoInst() throws IOException{//recibe el nombre del archivo
		
		FileWriter fi = new FileWriter(nombre_i);
		PrintWriter pw = new PrintWriter(fi);
		pw.println("Linea\tEtiqueta  Codop\tOperando");
		pw.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		pw.flush();
		pw.close();
				    	   
	}
	public void CrearArchivoErr() throws IOException{//recibe el nombre del archivo
		
		FileWriter fe = new FileWriter(nombre_e);
		PrintWriter pe = new PrintWriter(fe);
		pe.println("Linea\tError");
		pe.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		pe.flush();
		pe.close();	
	}
	/**
	 * Abre el archivo de instrucciones y escribe donde se quedó el archivo
	 * @throws IOException 
	 */
	public void ImprimeInst() throws IOException{
		
		nombre_i = nombre.toLowerCase().replace(".asm", ".inst");
		FileWriter fi = new FileWriter(nombre_i, true);
		PrintWriter pw = new PrintWriter(fi);
		pw.println("  "+num+"\t"+et+"\t"+co+"\t"+op);
		pw.flush();
		pw.close();	
	}
	/**
	 * Abre el archivo de errores y escribe donde se quedó el archivo
	 * @throws IOException 
	 */
	public void ImprimeError() throws IOException{
		
		nombre_e = nombre.toLowerCase().replace(".asm", ".err");
		FileWriter f = new FileWriter(nombre_e, true);
		PrintWriter p = new PrintWriter(f);
		p.println(num+"\t"+error);
		p.flush();
		p.close();	
		}
}