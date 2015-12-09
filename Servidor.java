package chat;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.UIManager;


public class Servidor extends Frame implements ActionListener,Runnable,KeyListener
{
		ServerSocket s; // el servidor crea un socket servidor
        Socket so1; // este socket esta asociado al serversocket
        BufferedReader br; // Nos permite leer los datos de entrada bufferedreader=br
        BufferedWriter bw; // Nos permite escribir los datos de entrada bufferedwriter= bw
        TextField texto; // campo de texto para el mensaje
        TextField nick; // campo de texto para el nombre de usuario
        JButton borrar; // boton para borrar el campo nick
        JLabel label, label1; // Nos permite mostrar mensajes en el Jpanel
        List lista; // lista que nos mostrara todo el contexto de lo que esta pasando por el servidor
        
        JPanel p1,p2,sp21,sp22,jp; // declaramos los paneles

        public void run()  // haciendo uso de la interfaz Runnable
		{
				try{
					so1.setSoTimeout(1); // tiempo de espera del socket 1ms, para recibir o enviar una señal
						}
				catch(Exception e){}
				while (true)
				{
					try{
		                lista.add(br.readLine()); // agrega a la lista los datos de entrada
							}
					catch (Exception h){}
		            
		            if(lista.getItemCount()==7) // si existen mas de 7 elementos crea un scrollbar para seguir viendo la lista
		               lista.remove(0); 
		
				}
			}
	
        public Servidor(String m) // creamos nuestra  GUI de servidor
        {       
        		super(m);
                
                jp=new JPanel();
                p1=new JPanel();
                p2=new JPanel();
                sp21=new JPanel();
                sp22=new JPanel();

                jp.setLayout(new GridLayout(2,1));
                p1.setLayout(new GridLayout(1,1));
                p2.setLayout(new GridLayout(2,1));
                sp21.setLayout(new FlowLayout());
                sp22.setLayout(new FlowLayout());

                borrar = new JButton("Borrar");
                borrar.addActionListener(this);
                lista = new List(50);
                texto = new TextField(43);
                nick = new TextField(10);
                label = new JLabel("<html><h4><font color='orange'> Servidor Nick </font></h4></html>"); // podemos agregar codigo HTMl a los componenetes de swing para configuraracion visual
                label1 = new JLabel("<html><h3><font color='orange'> Mensaje </font></h3></html>"); // podemos agregar codigo HTMl a los componenetes de swing para configuraracion visual
                nick.addKeyListener(this); // agregamos el escuchador de eventos al campo de texto de nick
                texto.addKeyListener(this); // agregamos el escuchador de eventos al campo de texto de mensaje
                p1.add(lista); /* agregamos los elementos a nuestros respectivos paneles */
                sp21.add(label1);
                sp21.add(texto);                
                sp22.add(label);
                sp22.add(nick);
                sp22.add(borrar);
                p2.add(sp21);
                p2.add(sp22);
                jp.add(p1);
                jp.add(p2);  /* agregamos los elementos a nuestros respectivos paneles   */

                this.add(jp);
                setBackground(Color.orange);
                setSize(380,300);
                setLocation(0,0);
                addWindowListener (new WindowAdapter(){ // este evento de ventana permite cerrar la ventana desde el boton cerrar
						public void windowClosing(WindowEvent e){
						System.exit(0);
						}
						});
                setVisible(true); 
                setResizable(false);
                nick.requestFocus(); // permite mantener el cursor en el campo de texto
                try{
		            s = new ServerSocket(786); //creaun socket servidor como parametro el puerto por donde va a estar escuchando las paticiones
					
					so1=s.accept(); // metodo que se mantiene a la espera de conexiones entrantes
					br = new BufferedReader(new InputStreamReader(so1.getInputStream()));//entrada de datos por medio del socket
					bw = new BufferedWriter(new OutputStreamWriter(so1.getOutputStream())); // salida de datos por medio del socket
		            bw.write("Bienvenido!!!");
		            bw.newLine(); // crea una nueva lista
		            bw.flush(); // limpia la entrada de datos, para eliminar basura 
		            
					 Thread th; // iniciamos un hilo para poder manejar la entrada de datos tantas veces sea posible ("los mensajes")
					 th = new Thread(this);
					 th.start();
			 
			
		}catch(Exception e){}
	}
    public static void main(String args[])
	{
		try 
		{
			//(UIManager.getSystemLookAndFeelClassName());
			//("net.sourceforge.napkinlaf.NapkinLookAndFeel");
			//"com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // look and feal permite implementar temas para java swing
			//new Raven3();
				} 
		catch (Exception e) 
		{
			e.printStackTrace();
				}
          new Servidor("Servidor local");
			}

	public void actionPerformed ( ActionEvent e)
	{
		
       if (e.getSource().equals(borrar))
       { 
       		nick.setText(" ");
         	nick.setEditable(true);
                  }
			}

    public void keyPressed(KeyEvent ke)  // escuchador de eventos del teclado
    {
        if(texto.equals(ke.getSource())) 
        { 
        	if(ke.getKeyCode()==KeyEvent.VK_ENTER) // si presionamos enter agrega una nueva linea a la lista con los datos de entradaen el campo de texto
        	{
	          try{
	                bw.write(nick.getText()+">>"+texto.getText());
				 	bw.newLine();bw.flush();
				 	}
			  catch(Exception m){}
	                                 
	          lista.add(nick.getText()+">>"+texto.getText());
	          texto.setText("");
	
	         }
         			}
         else if(nick.equals(ke.getSource())) // si presionamos enter en el campo de nick lo bloquea para mantener el nombre ypara borrarlo utilizamos el boton borrar
         {
             if(ke.getKeyCode()==KeyEvent.VK_ENTER)
             {
                nick.setEditable(false);
                texto.requestFocus(); // permite mantener el cursor en el campo de texto
                 }
                    }

         }
    public void keyReleased(KeyEvent ke) // metodo key event extra para el teclado
    {     
        		}

    public void keyTyped(KeyEvent ke) // 2 metodo key event extra para el teclado
    {
           		}

  }
