import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;//Librerias :D

public class Client1 extends Frame implements ActionListener,Runnable,KeyListener 
{  //---Variables---
	Socket so; //Socket donde se realizara la conexión
	BufferedReader br; //El que escuchara los mensajes
	BufferedWriter bw; //El que mandara los mensajes
    TextField texto,nick; //Campos de texto
    JButton borrar; //Botón para eliminar un campo de texto
    JLabel label1,label; //Etiquetas de texto
    List lista;
    JPanel p1,p2,sp21,sp22,jp;//Paneles 
        

	public void run()
	{
		try{
			so.setSoTimeout(1);//Tiempo de espera de el Socket
		}catch(Exception e){}
		while (true)
		{
			
		try{
            lista.add(br.readLine());//Agrega un escuchador para ir poniendo al chat
		}catch (Exception e){}
                               
        if(lista.getItemCount()==7)//Checa los elementos de la lista
        lista.remove(0);//En caaso de ser 7 se agrega más espacio y se crea una scroll bar por consecuencia
		}
	}
	
	public static void main(String arg[])
	{
		try//inicia el tema
		{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}//termina el tema         
        new Client1("Cliente");//crea un objeto de la clase principal Client1 y manda un parametro String
		
	}


    public Client1(String nombre)//Constrcutor de la clase
	{
		
        super(nombre); //Como no hay una clase padre el super se puede utilizar como un remplazo del SetTitle                      
                jp=new JPanel();//panel principal
                p1=new JPanel();
                p2=new JPanel();
                sp21=new JPanel();
                sp22=new JPanel();//Se crean los diferentes paneles que vamos a estar utilizando

                jp.setLayout(new GridLayout(2,1));//GridLayout una cuadricula en pocas palabras donde pondras la ubicación de los elementos
                p1.setLayout(new GridLayout(1,1));
                p2.setLayout(new GridLayout(2,1));
                sp21.setLayout(new FlowLayout());//FlowLayout que ingresa los elementos y los acomoda de izquierda a derecha si no hay espacio los manda abajo de otro elemento
                sp22.setLayout(new FlowLayout());

   
                borrar = new JButton("Borrar");//Creamos el botón y asignamos un parametro que es su nombre
                borrar.addActionListener(this);//Le agregamos funciones al botón para que haga una acción cuando sea presionado
                lista = new List(50);//Se crea un cuadro de texto
                label = new JLabel("<html><h4><font color='orange'>Mensaje: </font></h4></html>");//Para los JLabel se puede utilizar código html para darle presentación
                texto = new TextField(40);//Campo de texto donde ira el texto que se pondra el Chat
                nick = new TextField(10);//Campo de texto donde se pondra el nick
                label1=new JLabel("<html><h4><font color='orange'>Ingresa Nick: </font></h4></html>");
                nick.addKeyListener(this);//Se agrega opciones de teclado al objeto nick y texto,
                texto.addKeyListener(this);//para poder escribir en ellos.
                
                p1.add(lista);
				sp21.add(label);
                sp21.add(texto);// Se agrega los objetos al panel
                sp22.add(label1);//después esos paneles a otros paneles,
                sp22.add(nick);//  para tener un mejor orden.
                sp22.add(borrar);
                p2.add(sp21);
                p2.add(sp22);

                jp.add(p1);
                jp.add(p2);

                this.add(jp);
                setBackground(Color.orange);
                setSize(380,300);  //Opciones de la ventana que aparecera con todos
                setLocation(400,0);//los paneles y botones.
                addWindowListener (new WindowAdapter(){
				public void windowClosing(WindowEvent e){ //Sección que hace que con presionar el boton cerrar de la barra
				System.exit(0);                           //se detenga el programa y se cierre
				}
				});
                setVisible(true);
                setResizable(false);//hacemos visible la ventana y le quitamos la opción
                nick.requestFocus();//de modificar el tamaño de la ventana con el ratón


		try{
            so = new Socket("localhost",786);//a partir del 3306 son libres de protocolos
            br = new BufferedReader(new InputStreamReader(so.getInputStream()));//El método getInputStream() interpreta lo que el programa de Java recibe
			bw = new BufferedWriter(new OutputStreamWriter(so.getOutputStream()));//El método getOutInputStream() manda una salida a otro programa en Java
			
			Thread unHilo;
			unHilo = new Thread(this);
			unHilo.start(); //Declaramos, creamos, e iniciamos un hilo para el proceso del Chat
			
		}catch(Exception e){}
		
	}
	public void actionPerformed ( ActionEvent e)//Método para eventos de botones
	{
                 if (e.getSource().equals(borrar))
                        { nick.setText(" ");
                         nick.setEditable(true);
                        }//La acción que realiza el boton que es borrar un campo de texto llamada nick
                 
        }
        public void keyPressed(KeyEvent ke)//Método para eventos de mouse
        {        if(texto.equals(ke.getSource()))//Sí el cursor esta en el texto y ocurre un evento desde el teclado 
                     {
                        if(ke.getKeyCode()==KeyEvent.VK_ENTER)//Si se presiona enter ocurre el bloque try y las dos sentencias 
                            {
                              try{
                                 bw.write(nick.getText()+">>"+texto.getText());
                                 bw.newLine();//El bloque try lee lo que hay en el texto lo pone en una nueva linea
                                 bw.flush();  //y se limpia la señal de las teclas para que no entre basura.
        
                                 }catch(Exception e){}
                                 
                                 lista.add(nick.getText()+">>"+texto.getText());
                                 texto.setText("");//Se vacia la caja de texto haciendo la simulación de que se envio un mensaje
                             }
                       }
                  if(nick.equals(ke.getSource()))//Si el cursor esta en el texto y ocurre un evento desde el teclado
                     {
                        if(ke.getKeyCode()==KeyEvent.VK_ENTER)//Si se presiona enter ocurren las sentencias
                             {
                                nick.setEditable(false);
                                texto.requestFocus();//Evita que se edite el campo de nick y el cursor sige en ese campo.
                               }
                      }

        }
        public void keyReleased(KeyEvent ke) {//Método extra para el teclado
         }
        public void keyTyped(KeyEvent ke) {//Método extra para el teclado
        }                                                                                                                              
	
}
