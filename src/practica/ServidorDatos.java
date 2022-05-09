package practica;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorDatos extends Thread{
	
	private int puerto;
	private int codRespuesta;
	private Vacuna vacuna;
	private String llave;
	private Laboratorio laboratorio;
	
	public ServidorDatos(int puerto, int cod) {
		
		this.puerto=puerto;
		this.codRespuesta=cod;
		
	}
	
	
	public ServidorDatos(int puerto,int cod, String llave) {
		this.puerto=puerto;
		this.codRespuesta=cod;
		this.llave=llave;
		
	}

	public void run() {
		ServerSocket serverSocket = null;
		Socket socket;
		try {
			
			serverSocket = new ServerSocket(puerto);
	
			socket = serverSocket.accept();
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ios = new ObjectInputStream(socket.getInputStream());

			if(codRespuesta==1) {		
				vacuna = (Vacuna ) ios.readObject();
				System.out.println(vacuna.getId()+" "+vacuna.getDescripcion());
				Servidor.addVacuna(vacuna);
				
			}
		
			if(codRespuesta==2) {
				
				vacuna = Servidor.getVacuna(llave);
				if(vacuna!=null) {
					
					oos.writeObject(vacuna);
					oos.flush();
				}
				else {
				System.out.println("no la hemos encontrado");
				}	
			}
			if(codRespuesta==4) {
				
				vacuna = (Vacuna ) ios.readObject();
				System.out.println(vacuna.getId()+" "+vacuna.getDescripcion());
				Servidor.updateVacuna(vacuna);
				
			}
			if(codRespuesta==6) {
				Vacuna arrayVac[]= new Vacuna[Servidor.contarVacunas()];
				arrayVac= Servidor.dameLista();
				
				for(int i=0;i<Servidor.contarVacunas();i++) {
					oos.writeObject(arrayVac[i]);
					oos.flush();
				}
			}
			
			if(codRespuesta==7) {		
				laboratorio = (Laboratorio) ios.readObject();
				Servidor.addLaboratorio(laboratorio);
				System.out.println(laboratorio.getIdLab()+" "+laboratorio.getNombreLab());		
				
			}
			if(codRespuesta==8) {
			
				laboratorio = Servidor.getLab(llave);
				if(laboratorio!=null) {
					
					oos.writeObject(laboratorio);
					oos.flush();
				}
				else {
				System.out.println("no lo hemos encontrado");
				}	
			}
			
		
			
		} catch (IOException e) {
			System.out.println(" ");
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
}
