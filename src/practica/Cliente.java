package practica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	private static int number=0;
	
	public static void main(String[] args) {
		Socket socket;
		BufferedReader br = null;
		PrintWriter pw = null;
		String mensaje="a";
		Scanner teclado = new Scanner(System.in);
		String partes[]= {" "," "," "," "," "};
		String opc;
		String descripcion;
		ClienteLeeRespuesta clienteLeeRespuesta;
		
		try {
            //Creamos el socket 
			socket = new Socket("192.168.0.109", 2020);
			br = new BufferedReader(new	InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			// En usuario tenemos que poner admin para que el servidor nos acepte
			while(!partes[0].equals("OK")) {
				System.out.println("Usuario:");
				mensaje= teclado.nextLine();
				pw.println(number +" USER "+mensaje);
				pw.flush();
				number++;
				mensaje=br.readLine();
				System.out.println(mensaje);
				partes= mensaje.split(" ");
			}
			//Contraseña tenemos que poner admin
			partes[0]=" ";
			while(!partes[0].equals("OK")) {
				mensaje= teclado.nextLine();
				pw.println(number +" PASS "+mensaje);
				pw.flush();
				number++;
				mensaje=br.readLine();
				System.out.println(mensaje);
				partes= mensaje.split(" ");
			}
		
do {
				
				System.out.println("1.- ADD VACUNA");
				System.out.println("2.- GET VACUNA");
				System.out.println("3.- REMOVE VACUNA");
				System.out.println("4.- UPDATE VACUNA");
				System.out.println("5.- COUNT VACUNAS");
				System.out.println("6.- LIST VACUNAS");		
				System.out.println("7.- ADD LABORATORIO");
				System.out.println("8.- GET LABORATORIO");
				System.out.println("9.- ELIMINAR LABORATORIO");
				System.out.println("10.- ADD VACUNA AL LABORATORIO");
				System.out.println("11.- REMOVE VACUNA DEL LABORATORIO");
				System.out.print("¿Opcion? ");
				
				opc = teclado.nextLine();
				
				String id;
				
				switch(opc) {
				
				case "1":
					
					System.out.println("Dime el id de la vacuna: ");
					id=teclado.nextLine();
					System.out.println("Dime la descripcion de la vacuna: ");
					descripcion=teclado.nextLine();
                    
					//Creamos la vacuna
					Vacuna vacuna = new Vacuna(id,descripcion);
					//Creamos un hilo para leer respuesta del servidor 
					clienteLeeRespuesta = new ClienteLeeRespuesta(br,vacuna);
					clienteLeeRespuesta.start();
					//Enviamos el mensaje al servidor
            		pw.println(number +" ADDVACUNA ");
					pw.flush();

					break;
				
				case "2":
					System.out.print("Introduzca id: ");
					id = teclado.nextLine();
					pw.println(number +" GET " + id);
					pw.flush();
				    clienteLeeRespuesta = new ClienteLeeRespuesta(br,id);
					clienteLeeRespuesta.start();
	
					break;
				
				case "3":
					System.out.print("Introduzca id: ");
					id = teclado.nextLine();
					pw.println(number +" REMOVE " + id);
					pw.flush();
		     		clienteLeeRespuesta = new ClienteLeeRespuesta(br);
					clienteLeeRespuesta.start();
				
					break;
					
					
					
				case "4":
					System.out.println("Dime el id de la vacuna: ");
					id=teclado.nextLine();
					System.out.println("Dime la nueva descripcion de la vacuna: ");
					descripcion=teclado.nextLine();
					
				
					Vacuna vacunaa = new Vacuna(id,descripcion);
					clienteLeeRespuesta = new ClienteLeeRespuesta(br,vacunaa);
					clienteLeeRespuesta.start();
             		
		
					pw.println(number +" UPDATE "+id);
					pw.flush();
				
					break;
					

				case "5":
					
					pw.println(number +" COUNT " );
					pw.flush();
					clienteLeeRespuesta = new ClienteLeeRespuesta(br);
					clienteLeeRespuesta.start();
					number++;
					break;	
					
				case "6":
					/*pw.println(number +" LIST " );
					pw.flush();
					clienteLeeRespuesta= new ClienteLeeRespuesta(br);
					clienteLeeRespuesta.start();
					number++;
				*/
					break;
					
				case "7":
					System.out.println("Dime el id del laboratorio: ");
					id=teclado.nextLine();
					System.out.println("Dime el nombre del laboratorio: ");
					descripcion=teclado.nextLine();

					Laboratorio laboratorio = new Laboratorio(id,descripcion);
					clienteLeeRespuesta = new ClienteLeeRespuesta(br,laboratorio);
					clienteLeeRespuesta.start();
								
					number++;
		
					pw.println(number +" ADDLAB ");
					pw.flush();

					break;
					
				case "8":
					System.out.print("Introduzca id del laboratorio: ");
					id = teclado.nextLine();
					pw.println(number +" GETLAB " + id);
					pw.flush();
					clienteLeeRespuesta = new ClienteLeeRespuesta(br,id);
					clienteLeeRespuesta.start();
					number++;
					break;
			
				case "9":
					System.out.print("Introduzca id: ");
					id = teclado.nextLine();
					pw.println(number +" REMOVELAB " + id);
					pw.flush();
					clienteLeeRespuesta = new ClienteLeeRespuesta(br,id);
					clienteLeeRespuesta.start();
					number++;
					break;
			
				case "10":
					System.out.print("Introduzca id de la vacuna: ");
					id = teclado.nextLine();
					System.out.println("Introduzca id del laboratorio: ");
					descripcion=teclado.nextLine();
					
					pw.println(number +" ADDVACLAB " + id+" "+descripcion);
					pw.flush();
			
					clienteLeeRespuesta = new ClienteLeeRespuesta(br);
					clienteLeeRespuesta.start();
					break;
					
				case "11":
					System.out.print("Introduzca id de la vacuna: ");
					id = teclado.nextLine();
					System.out.println("Introduzca id del laboratorio: ");
					descripcion=teclado.nextLine();
					
					pw.println(number +" REMOVEVACLAB " + id+" "+descripcion);
					pw.flush();
					
					clienteLeeRespuesta = new ClienteLeeRespuesta(br);
					clienteLeeRespuesta.start();
					break;
					
					
					
				default:
					System.out.println("Opción no válida");
					break;
				}
				number++;
				
			}while (!opc.equals("13"));
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
