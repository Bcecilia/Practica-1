package metodos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.swing.JFileChooser;

public class Archivo {
	
	public File a = null;
	public String linea = "", tipo_error = "0";
	public byte num_linea = 0;//para contar las lineas leídas
	public boolean b_end = false, bandera = false;
	public int contador = 0;
	public Linea l = null;
	Map <String, MiTabop> mapa = new TreeMap<String,MiTabop>();
	
	public Archivo(File n){//constructor
		a = n;
	}
	/**
	 * El método "LeerArchivo" lee el archivo linea por linea y por cada linea verifica:
	 * 1. Si no se ha encontrado el "end"
	 * 2. Si es una linea vacía
	 * 3. Si comienza con punto y coma (que es la primer validación que hay para los comentarios)
	 * Y si no se cumple ninguna de las anteriores manda llamar a la función "SepararLinea" para
	 * separar por tokens.
	 * Cuando ya no hay lineas por leer (o si encuentra el "end")cierra el archivo.
	 */
	public void LeerArchivo() throws IOException{
		FileReader fr = new FileReader(a);
		BufferedReader lector = new BufferedReader(fr);
		mapa = GuardarTabop(); //guardar el tebop en el map
		while(lector.ready()&& b_end == false){//toma linea por linea hasta el final del archivo y verfica bandera
			num_linea++;
			linea = lector.readLine();
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
		    if(bandera == true)//verifica si es linea vacía
			{
				System.out.println("linea "+num_linea+": linea vacía");
			}else if(linea.startsWith(";"))//caso en que el comentario comience con ;
			{
			    System.out.println("linea "+num_linea+": línea de comentario");
			}else{
				l = new Linea(num_linea, linea, mapa);//manda el # de linea, la linea y el map
				l.SepararLinea();
				b_end = l.ValidaEnd();
			    }
		    bandera = false;
		    contador = 0;
		}
		if(b_end == false){
			System.out.println("No se encontró el end :c");
			Error h = new Error("10");
			String detalle = h.TipoDeError();
			Salida s = new Salida((byte) 0, detalle);
			s.ImprimeError();
		}else{
			System.out.println("Si hay end");
		}
		lector.close();
	}
	/**
	 * Se pide el tabop con filechooser, se abre el archivo y se guarda todo en un Map
	 * @return mapa con el tabop
	 * @throws IOException
	 */
	public Map<String, MiTabop> GuardarTabop() throws IOException{
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(null);
    	File f = new File(jfc.getSelectedFile().getPath());
    	System.out.println("Elegiste abrir este archivo: "+f);
		BufferedReader lector = new BufferedReader(new FileReader(f));
		int n = 1;
		while(lector.ready()){//guardando el tabop en memoria
			String linea = lector.readLine();
			StringTokenizer st = new StringTokenizer(linea,"|");
			String c = st.nextToken();
			String m = st.nextToken();
			//String key = c+m;
			String key = Integer.toString(n);
			mapa.put(key, new MiTabop(key,c,m,st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken()));
			n++;
		}
		lector.close();
		return mapa;
	}
}
