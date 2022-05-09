package practica;

import java.io.Serializable;

public class Vacuna implements Serializable{

	private String id;
	private String descripcion;
	
	public Vacuna(String id,String descripcion) {
		this.id=id;
		this.descripcion=descripcion;
	}
	public String getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String desc) {
		descripcion=desc;
	}
	
}
