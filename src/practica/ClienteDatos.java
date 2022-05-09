package practica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteDatos extends Thread{

	private String ip;
	private int puerto;
	private int codMensaje;
	private Vacuna vacuna;
	private String id;
	private Laboratorio laboratorio;
	
	public ClienteDatos(String ip, String puerto, String codMensaje,Vacuna vacuna) {
		this.ip=ip;
		this.puerto=Integer.parseInt(puerto);
		this.codMensaje=Integer.parseInt(codMensaje);
		this.vacuna=vacuna;
		
		}
	
	public ClienteDatos(String ip, String puerto, String codMensaje,Laboratorio lab) {
		this.ip=ip;
		this.puerto=Integer.parseInt(puerto);
		this.codMensaje=Integer.parseInt(codMensaje);
		this.laboratorio=lab;
		
		}
	
	public ClienteDatos(String ip, String puerto, String codMensaje,String id) {
		this.ip=ip;
		this.puerto=Integer.parseInt(puerto);
		this.codMensaje=Integer.parseInt(codMensaje);
		this.id=id;
		}
	public ClienteDatos(String ip, String puerto, String codMensaje) {
		this.ip=ip;
		this.puerto=Integer.parseInt(puerto);
		this.codMensaje=Integer.parseInt(codMensaje);
		
		}
	
	public void run() {
		try {
			
			
			Socket socketDatos = new Socket("192.168.0.109",puerto);
			
			ObjectOutputStream oos = new ObjectOutputStream(socketDatos.getOutputStream());
			ObjectInputStream ios = new ObjectInputStream(socketDatos.getInputStream());
			Scanner teclado = new Scanner(System.in);
			
			
			
			if(codMensaje==1) {
				//Enviamos vacuna(add)
				oos.writeObject(vacuna);
				oos.flush();
			
			}
			if(codMensaje==2) {
				//recibimos vacuna(get)
				try {
					vacuna = (Vacuna ) ios.readObject();
					System.out.println(vacuna.getId()+" "+vacuna.getDescripcion());
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}	
			}
             if(codMensaje==4) {	
            	 //enviamos vacuan(update)
            	 oos.writeObject(vacuna);
 				 oos.flush();
			}
            if(codMensaje==6) {
            	
            	try {
            	Vacuna arrayVac[]= new Vacuna[Servidor.contarVacunas()];
            	for(int i=0;i<Servidor.contarVacunas();i++) {
            		arrayVac[i]=(Vacuna ) ios.readObject();;
            	}

            	}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}	
            }
             
             if(codMensaje==7) {	
            	//enviamos laboratorio(add)
            	 oos.writeObject(laboratorio);
 				 oos.flush();
			}
             if(codMensaje==8) {	
            	 //recibimos laboratorio(get)
            	 try {
 					laboratorio = (Laboratorio ) ios.readObject();
 					System.out.println(laboratorio.getIdLab()+" "+laboratorio.getNombreLab());
 				} catch (ClassNotFoundException e) {
 					e.printStackTrace();
 				}
            	 
			}
			//cerramos el canal de datos
			socketDatos.close();
		
			
		} catch (UnknownHostException e) {
			System.out.println(" ");
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
