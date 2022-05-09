package practica;

import java.io.BufferedReader;
import java.io.IOException;

public class ClienteLeeRespuesta extends Thread{

	private BufferedReader br;
	private String mensaje;
	private String partes[];
	private Vacuna vacuna=null;
	private Laboratorio laboratorio;
	private String id;
	
	public ClienteLeeRespuesta(BufferedReader br,String id) {
		this.br=br;
		this.id=id;
	}
	
	
	public ClienteLeeRespuesta(BufferedReader br) {
		this.br=br;
		
	}
	public ClienteLeeRespuesta(BufferedReader br,Vacuna vacuna) {
		this.br=br;
		this.vacuna=vacuna;
	
		}
	
	public ClienteLeeRespuesta(BufferedReader br,Laboratorio lab) {
		
		this.br=br;
		this.laboratorio=lab;
	
		}
	
	
	public void run() {
		
			try {
				
				//LeeLaRespuestaDelServidor
				mensaje=br.readLine();		
				System.out.println(mensaje);
				partes=mensaje.split(" ");
				
				//partes[2] hace referencia al codigoMensje del sevidor
				
				if(Integer.parseInt(partes[2])==1) {
					//Codigo para añadir vacuna
					//creamos otro hilo,para crear el otro canal de datos
					//enviamos la vacuna que vamos a añadir
			    ClienteDatos clienteDatos = new ClienteDatos(partes[3],partes[4],partes[2],vacuna);
				clienteDatos.start();
				}
				
				if(Integer.parseInt(partes[2])==2) {
					//codigo para obtener vacuna
					//creamos otro hilo, para crear otro canal de datos
					//poder recibir la vacuna	
					
					ClienteDatos clienteDatos = new ClienteDatos(partes[3],partes[4],partes[2],id);
					clienteDatos.start();
					}
				if(Integer.parseInt(partes[2])==4) {
					//codigo para actualizar vacuna
					//creamos el hilo, para crear otro canal de datos
					//poder enviar la vacuna actualizada
					ClienteDatos clienteDatos = new ClienteDatos(partes[3],partes[4],partes[2],vacuna);
					clienteDatos.start();
					}
				
               if(Integer.parseInt(partes[2])==6) {	
            	   
					ClienteDatos clienteDatos = new ClienteDatos(partes[3],partes[4],partes[2]);
					clienteDatos.start();
					}
				
				
				if(Integer.parseInt(partes[2])==7) {
		           //codigo para crear el laboratorio
					// en este hilo crearemos el canal de datos
					//enviaremos el laboratorio creado
					ClienteDatos clienteDatos = new ClienteDatos(partes[3],partes[4],partes[2],laboratorio);
					clienteDatos.start();
					}
				if(Integer.parseInt(partes[2])==8) {
					//codigo para obtener laboratorio
					//creamos otro hilo, para crear otro canal de datos
					//poder recibir el laboratorio
					ClienteDatos clienteDatos = new ClienteDatos(partes[3],partes[4],partes[2],id);
					clienteDatos.start();
					}
		
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
