package practica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HiloServidor  extends Thread{
	private BufferedReader br = null;
	private PrintWriter pw = null;
	private String mensaje;
	private String partes[];
	private static int puerto=2021;
	private Vacuna vacuna;
	private Laboratorio laboratorio;
	private int codMensaje;
	ServidorDatos servidorDatos;
	public HiloServidor(BufferedReader br,PrintWriter pw) {
		
		this.br=br;
		this.pw=pw;
		
	}
	
	public void run() {
	try {	
		while(true) {
			
			mensaje= br.readLine();
			System.out.println(mensaje);
			partes=mensaje.split(" ");
			puerto ++;
			
		
			switch(partes[1]) {
			case "USER": 
				codMensaje=0;
				//si el usuario es admin dejalo entrar
				if(partes[2].equals("admin")) {
					
					pw.println("OK "+partes[0]+" "+codMensaje+" Envia contraseña");
					pw.flush();
					
				}
				else {
					pw.println("FAILED "+partes[0]+" "+codMensaje+ " Not user");
					pw.flush();
				}
				break;
				
			case "PASS":
				codMensaje=0;
				// la contraseña correcta es admin
				if(partes[2].equals("admin")) {
					pw.println("OK "+partes[0]+" "+codMensaje+" Welcome "+partes[2]);
					pw.flush();
					
				}
				else {
					pw.println("FAILED "+partes[0]+" "+codMensaje+" Prueba de nuevo");
					pw.flush();
				}
				
				break;
				
			case "ADDVACUNA":
				codMensaje=1;
				//el codMensaje de addvacuna = 1
				//enviamos ip y puerto
				pw.println("OK "+partes[0]+" "+codMensaje+ " 192.168.0.108 "+puerto);
				pw.flush();
				//creamos un hilo para poder crear simultaneamente el canal de datos
				servidorDatos = new ServidorDatos(puerto,codMensaje);
				servidorDatos.start();
				
				
				break;
				
			case "GET":
				codMensaje=2;
				// el codMensaje de getvacuna =2
				if(Servidor.estaVacuna(partes[2])==true) {
					pw.println("OK "+partes[0]+" "+codMensaje+  " 192.168.0.108 "+puerto);
					pw.flush();
					//creamos un hilo para poder crear simultaneamente el canal de datos
					servidorDatos = new ServidorDatos(puerto,codMensaje,partes[2]);
					servidorDatos.start();
				}
				else {
					// el codMensaje de que ha habido un error es el 12
					codMensaje=12;
					pw.println("FAILED "+partes[0]+" "+codMensaje+" Mensaje error");
					pw.flush();
				}
				
				break;
				
				
			case "REMOVE":
				codMensaje=3;
				
				vacuna = Servidor.getVacuna(partes[2]);
			    if(vacuna!=null) {
			    	//ha encontrado la vacuna
			    	//la va a eliminar 
			    	//no hace falta conectarse al canal de datos, ya que no estoy enviando ningun objeto
			    	Servidor.eliminarVacuna(vacuna);
			    	pw.println("OK "+partes[0]+" "+codMensaje+ " Paciente eliminado ");
					pw.flush();
			    }
			    else {
			    	codMensaje=12;
			    	//ha habido un error al encontrar la vacuna
			        pw.println("FAILED "+partes[0] +" "+codMensaje + " No autorizado");
			        pw.flush();
			    }
				break;	
			
           case "UPDATE":
				codMensaje=4;
				if(Servidor.estaVacuna(partes[2])==true) {
					pw.println("OK "+partes[0]+" "+codMensaje+" 192.168.0.108 "+puerto);
					pw.flush();
					//cremos el canal de datos para recibir la vacuna actualizada
					servidorDatos = new ServidorDatos(puerto,codMensaje);
					servidorDatos.start();
				}
				else {
					codMensaje=12;
					//no se ha encontrado la vacuna, por tanto no se podra actualizar
					pw.println("FAILED "+partes[0]+" "+codMensaje+" Mensaje error");
					pw.flush();
				}
				
				
				
				break;
           case "COUNT":
        	    codMensaje=5;
        	    // enviaremos el numero de vacunas
        	    pw.println("OK "+partes[0]+" "+codMensaje +" "+ Servidor.contarVacunas());
				pw.flush();
        	   break;
				
           case "LIST":
        	   //Este caso no esta implementado de forma correcta, 
        	 /*  codMensaje =6;
        	   pw.println("OK "+partes[0]+" "+codMensaje+" 192.168.0.108 "+puerto);
			   pw.flush();
			   servidorDatos = new ServidorDatos(puerto,codMensaje);
			   servidorDatos.start();*/
        	   break;
        	   
        	   
           case "ADDLAB":
        	    codMensaje=7;
        	    pw.println("OK "+partes[0]+" "+codMensaje+" 192.168.0.108 "+puerto);
				pw.flush();
				//cremaos el canal de datos con un nuevo hilo para poder recibir el laboratorio
				servidorDatos = new ServidorDatos(puerto,codMensaje);
				servidorDatos.start();
				
        	   break;
        	   
        	   
           case "GETLAB":
        	   codMensaje=8;
        	   if(Servidor.estaLaboratorio(partes[2])==true) {
        		pw.println("OK "+partes[0]+" "+codMensaje+" 192.168.0.108 "+puerto);
   				pw.flush();
   				//creamos el canal de datos para enviarle al cliente el laboratorio
   				servidorDatos = new ServidorDatos(puerto,codMensaje,partes[2]);
   				servidorDatos.start();
   				
        	   }
        	   else {
        		    codMensaje=12;
        		    //no existe el laboratorio que ha pedido el cliente
        		    pw.println("FAILED "+partes[0]+" "+codMensaje+" Mensaje error");
					pw.flush();
        	   }
				
				break;
        	   
           case "REMOVELAB":
        	   
				laboratorio = Servidor.getLab(partes[2]);
			    if(laboratorio!=null) {
			    	codMensaje=9;
			    	//eliminamos el laboratorio que ha pedido el cliente
				    pw.println("OK "+partes[0]+" "+codMensaje+ " Laboratorio eliminado ");
					pw.flush();
			    	Servidor.eliminarLaboratorio(laboratorio);
			    
			    }
			    else {
			    	codMensaje=12;
			    	//no existe el laboratio que ha pedido el cliente
			    	//por ello el servidor envia un failed al cliente
			        pw.println("FAILED "+partes[0] +" "+codMensaje + " No autorizado");
			        pw.flush();
			    }
				break;		
				
           case "ADDVACLAB":
        	   
        	   laboratorio = Servidor.getLab(partes[3]);    	   
        	   vacuna = Servidor.getVacuna(partes[2]);
        	   //si el laboratorio y la vacuna existen entonces puedo añadir la vacuna al laboratorio
        	   if(laboratorio!=null && vacuna!=null) {
        		   codMensaje=10;	     	   
            	   laboratorio.addVacunaLaboratorio(vacuna);
            	   pw.println("OK "+partes[0]+" " +codMensaje+" Vacuna anadida");
             	   pw.flush();
        	   }
        	   else {
        		   codMensaje=12;
       		       pw.println("FAILED "+partes[0]+" "+codMensaje+" Mensaje error");
				   pw.flush();
        	   }
        	   

        	   break;
				
           case "REMOVEVACLAB":
  	   
         	
        	   laboratorio = Servidor.getLab(partes[3]);    	   
        	   vacuna = laboratorio.getVacunaLab(partes[2]);
        	   //si el laboratorio y la vacuna existen entonces puedo eliminar la vacuna del laboratorio
        	   if(laboratorio!=null && vacuna!=null ) {
        		   codMensaje=11;
        		   laboratorio.eliminarVacunaLaboratorio(vacuna);
        		   pw.println("OK "+partes[0]+" " +codMensaje+" Vacuna eliminada");
             	   pw.flush();
        	   }
        	   else {
        		   codMensaje=12;
       		       pw.println("FAILED "+partes[0]+" "+codMensaje+" Mensaje error");
				   pw.flush();
        	   }
        	   
        	   

        	   break;
				
			default:
				pw.println("FAILED "+partes[0]+" cod MensajeError");
			    pw.flush();
			}
			
		
			
			
		}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
