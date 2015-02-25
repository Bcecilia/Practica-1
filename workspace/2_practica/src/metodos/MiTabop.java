package metodos;
/**
 * La clase define los elementos que contendrá como valor el map.
 * Contiene métodos getters y setters para la manipulación individual de cada elemento.
 * La función toString regresa la instrucción y todos sus demás datos.
 * 
 *
 */
public class MiTabop {

	private String key, inst, modo, cod_m, b_cal, b_xcal,sum_b;
	
	public MiTabop(){
	}
	public MiTabop(String num,String inst,String modo,String cod_m, String b_cal, String b_xcal, String sum_b){
		this.key = num;
		this.inst = inst;
		this.modo = modo;
		this.cod_m = cod_m;
		this.b_cal = b_cal;
		this.b_xcal = b_xcal;
		this.sum_b = sum_b;
	}
	 /*Getters y Setters*/
	public String getNum() {return key;}
	public void setNum(String num){	this.key = num;	}
	public String getInst() {return inst;}
	public void setInst(String inst) {this.inst = inst;}
	public String getModo() {return modo;}
	public void setModo(String modo) {this.modo = modo;}
	public String getCod_m() {return cod_m;}
	public void setCod_m(String cod_m) {this.cod_m = cod_m;}
	public String getB_cal() {return b_cal;}
	public void setB_cal(String b_cal) {this.b_cal = b_cal;}
	public String getB_xcal() {return b_xcal;}
	public void setB_xcal(String b_xcal) {this.b_xcal = b_xcal;}
	public String getSum_b() {return sum_b;}
	public void setSum_b(String sum_b) {this.sum_b = sum_b;}
	
	public String toString(){
		return this.inst+"|"+modo+"|"+cod_m+"|"+b_cal+"|"+b_xcal+"|"+sum_b;
	}
}
