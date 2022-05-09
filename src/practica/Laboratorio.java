package practica;

import java.io.Serializable;
import java.util.ArrayList;

public class Laboratorio implements Serializable{

	private String id;
	private String nombre;
	//en este array guardaremos las vacunas
	private ArrayList<Vacuna>vacunas= new ArrayList<Vacuna>();
	private static int contador=0;
	
	public Laboratorio(String id, String nombre) {
		this.id=id;
		this.nombre=nombre;
	}
	public void addVacunaLaboratorio(Vacuna vacuna) {
		vacunas.add(vacuna);
	}
	
	public String getIdLab() {
		return id;
	}
	public String getNombreLab() {
		return nombre;
	}
	public void eliminarVacunaLaboratorio(Vacuna vacuna) {
		try {
		vacunas.remove(vacuna);
		}catch(Exception e) {
			System.out.println("Error");
		}
	}
	public Vacuna getVacunaLab(String id) {
		Vacuna aux = null;
		for(Vacuna v: vacunas) {
			if(v.getId().equals(id)) {
				aux=v;
				
			}
		}
		return aux;
	}
	
	
}
