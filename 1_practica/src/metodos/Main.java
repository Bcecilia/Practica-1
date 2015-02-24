package metodos;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
/**
 * @author Brenda Cecilia Vel�zquez I�iguez
 * Se crea un Filechooser para seleccionar el archivo de entrada y se comprueba que 
 * su extensi�n sea "asm" de lo contrario habr� un error
 * Si es un archivo v�lido crea el archivo de errores y el de instrucciones y se escriben en ellos
 * los encabezados que dan el formato
 * Se crea un objeto de la clase Salida para crear los dos archivos de errores e instrucciones
 * Luego crea un objeto de la clase "Archivo" y llama a la funci�n "Leer"
 *
 */
public class Main {
	public static String nombre;
	public static void main(String[] args) throws IOException{
		
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(null);
    	File f = new File(jfc.getSelectedFile().getPath());
    	System.out.println("Elegiste abrir este archivo: "+f);
    	if(f.getName().toUpperCase().endsWith(".ASM")){//validar extensi�n asm
    	   System.out.println("Es v�lido");
    	   nombre = f.getName();
    	   System.out.println("Viejo nombre: "+nombre);
    	   Salida s = new Salida(nombre);
    	   s.CrearArchivoInst();
    	   s.CrearArchivoErr();
     	   Archivo a = new Archivo(f);
     	   try {
    			a.LeerArchivo();
    		} catch (IOException e1) {
    			e1.printStackTrace();
    			System.out.println("No se pudo leer el archivo");
    		}
    	}else{
    		System.out.println(f+" no es un archivo v�lido");
    	}
	}
	/**
	 * @return nombre
	 * regresa el nombre del archivo que lee
	 */
	public String NombreArchivo(){
		return nombre;
	}
}
