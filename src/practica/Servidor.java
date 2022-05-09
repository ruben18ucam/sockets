package practica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

	
	private static ArrayList<Vacuna>lista= new ArrayList<Vacuna>();
	private static ArrayList<Laboratorio>lista2= new ArrayList<Laboratorio>();
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		Socket socket;
	    String mensaje;
	    String partes[];
		int contador=0;
		
			try {
				serverSocket = new ServerSocket(2020);
				
				//hacemos un bucle para aceptar multiples clientes
				while (true) {
					//el servidor espera aqui, hasta que encuentra una peticion de conexion
					socket = serverSocket.accept();
					
					br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
					// crea un hilo para, hacerlo multicliente
					HiloServidor hiloServidor = new HiloServidor(br,pw);
					hiloServidor.start();
					
					
					
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	public static void addVacuna(Vacuna vacuna) {
	
		lista.add(vacuna);
	}
	
	public static void addLaboratorio(Laboratorio laboratorio) {
		lista2.add(laboratorio);
		}
	
	
	public static Vacuna getVacuna(String id) {
		Vacuna aux = null;
		for(Vacuna v: lista) {
			if(v.getId().equals(id)) {
				aux=v;
				
			}
		}
		return aux;
	}
	public static Laboratorio getLab(String id) {
	
		Laboratorio aux = null;
		for(Laboratorio v: lista2) {
			if(v.getIdLab().equals(id)) {
				aux=v;
							}
		}
		return aux;
	}
	
	
	
	public static void eliminarVacuna(Vacuna vacuna) {
		lista.remove(vacuna);
	}
	public static void eliminarLaboratorio(Laboratorio laboratorio) {
		
		lista2.remove(laboratorio);
	}
	
	
	public static void updateVacuna(Vacuna vacuna) {
		

		for(Vacuna v: lista) {
			if(v.getId().equals(vacuna.getId())) {
				v.setDescripcion(vacuna.getDescripcion());
			}
		}
		
	}
	
	
	public static int contarVacunas() {
		int contador=0;
		for(Vacuna v: lista) {
			contador++;
		}
		return contador;
	}
	public static Vacuna[] dameLista() {
		Vacuna aux[]= new Vacuna[Servidor.contarVacunas()];
		int contador=0;
		for(Vacuna v: lista) {
			aux[contador]=v;
			contador++;
		}
		return aux;
	}
	
	public static boolean estaVacuna(String id) {
	    int contador=0;
		for(Vacuna v: lista) {
			if(v.getId().equals(id)) {
				contador=1;
				
			}
		}
		if(contador==0) {
			return false;
		}
		else {
			return true;
		}

	}
	public static boolean estaLaboratorio(String id) {
	    int contador=0;
		for(Laboratorio v: lista2) {
			if(v.getIdLab().equals(id)) {
				contador=1;
			}
		}
		if(contador==0) {
			return false;
		}
		else {
			return true;
		}

	}

}
