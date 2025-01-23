import java.util.Scanner;
import java.io.*;

public class wordle16 {

	public static void titulo() {//Procedimiento para imprimir el título
		System.out.println("###               ###                           ###########");
        System.out.println(" ##               ##                            ##########");
        System.out.println(" ###             ###                            ###");
        System.out.println("  ##             ##   ####  #####  ####   #     ######");
        System.out.println("  ###    ###    ###  #    # #    # #   #  #     ######");
        System.out.println("   ##   ## ##   ##   #    # #    # #    # #     ###");
        System.out.println("   ### ### ### ###   #    # #####  #    # #     ###");
        System.out.println("    ####     ####    #    # #   #  #   #  #     ##########");
        System.out.println("    ###       ###     ####  #    # ####   ##### ###########");
	}
	
    public static boolean validarNum(String s) {//Función para validar el número que devuelve el usuario
    	boolean validar = false;
    	    	
    	if(s.length() == 5) {//Comprobar que sean 5 numeros
    		int a = 0;
    		for(int i = 0; i < s.length(); i++) //Comprobar que todo son 0, 1, 2
    			if((s.charAt(i) == '0')||(s.charAt(i) == '1')||(s.charAt(i) == '2'))
    				a++;

    		if(a == 5)
    			validar = true;
    	}
    	return validar;
    }

    public static String todoMayusculas(String s){//Función para pasar cualquier String a todo letras mayúsculas

        char v[] = aVector(s);
        for(int i = 0; i < s.length(); i++)
              if('a' <= s.charAt(i) && s.charAt(i) <= 'z') 
                    v[i] = (char)(v[i]+'A'-'a');
        
        return aString(v);
      }
    
	public static boolean validarNuevaPalabra(String s) {//Función para validar que la palabra pensada y no acertada es correcta para añadirla al diccionario
		boolean validar = false;
    	
    	if(s.length() == 5) {//Comprobar que sean 5 caracteres
    		int a = 0;
    		for(int i = 0; i < s.length(); i++) //Comprobar que todo son 0, 1, 2
    			if((s.charAt(i) >='A')&&(s.charAt(i) <= 'Z'))
    				a++;

    		if(a == 5)
    			validar = true;
    	}
    	return validar;
	}
	
	public static int posicion(char v[], char c) {//Función que devuelve la posición de un char en un vector (-1 si no está)
		int p = -1;
        int i = 0;
        
        while (i < v.length && p == -1) {
            if (v[i] == c)
            	p = i;
            else
            	i = i+1;
        }// i=v.length OR p>=0
        return p;
    }

    public static String aString(char v[]) {//Función para pasar de vector de char a String
        String s = "";
        for (int i = 0; i<v.length; i++)
            s = s + v[i];
        return s;
    }

    public static char[] aVector(String s) {//Función para pasar de String a vector de char
        char v[] = new char[s.length()];
        for (int i = 0; i<v.length; i++)
            v[i] = s.charAt(i);
        return v;
    }
	
    public static String eval(String oculta, String propuesta) {
    	//Función que devuelve la cadena de doses, unos y ceros
    	//que daría el Wordle como evaluación de la palabra propuesta respecto a la oculta
            	
        String respuesta;
        char vOculta[], vPropuesta[], vRespuesta[];
        int longitud = oculta.length();

        vOculta = aVector (oculta);
        vPropuesta = aVector (propuesta);
        vRespuesta = new char [longitud];

        for (int i = 0; i<vRespuesta.length; i++)
            vRespuesta [i] = '0';

        //Pasada de 2s
        for (int i = 0; i<longitud; i++) {
            if (vOculta[i] == vPropuesta[i]) {
                vRespuesta [i] = '2';
                vOculta[i] = '+';    // tachar
                vPropuesta[i] = '-'; // tachar
            }
        }

        //Pasada de 1s
        for (int i = 0; i<longitud; i++) { //recorrido de la palabra propuesta
            char c = vPropuesta[i];
            if (c != '-') { // no tachada
                int pos = posicion (vOculta, c); // -1 si no está
                if (pos != -1) {
                    vRespuesta [i] = '1';
                    vOculta[pos] = '+';  //tachar
                    vPropuesta[i] = '-'; // tachar // innecesario, dado el recorrido
                }
            }
        }
        respuesta = aString (vRespuesta);
        
        return respuesta;
    }
    
	public static boolean sinTrampas(String respuestas[], String palIntentadas[], String nuevaPalabra) {
		//Método para comprobar si el usuario ha hecho trampas o si se ha equivocado
		//Se comparan las respuestas metidas por el usuario con las que debería haber dado
		
		boolean sinTrampas = true;
    	String respuestasBuenas[] = new String[respuestas.length]; //Se guardan las respuestas que debería haber introducido el usuario
    	
    	for(int i = 0; i < respuestasBuenas.length; i++) {//Usar el método eval para ir guardando las respuestas que se deberían haber dado
    		respuestasBuenas[i] = eval(nuevaPalabra, palIntentadas[i]);
    		/*System.out.println(respuestas[i]+"->"+respuestasBuenas[i]);*/
    	}
    	
    	for(int i = 0; i < respuestas.length; i++) //Se comprueba que cada respuesta dada sea igual que la que se debería haber dado
    		if(!respuestas[i].equals(respuestasBuenas[i]))
    			sinTrampas = false;
    	
		return sinTrampas;
	}
	
	
    public static void main(String[] args) {
        /*
         * Pre: 
         * 		-El usuario piensa una palabra existente en el castellano de 5 caracteres
         * 		-Caracteres solo del alfabeto inglés
         * 
         * Entrada: 
         * 		-Cadena de 5 números (0, 1 o 2) para comprobar la palabra
         * 		-Si el programa falla, la palabra pensada
         * 
         * Salida: 
         * 		-Propuestas de palabras de un diccionario propio
         * 		-Resultado del juego
         */
    	
        Scanner in = new Scanner(System.in);
        
        String jugar = "SI";
        
        titulo();
        
        while(jugar.equals("SI")) { //El juego se vuelve a ejecutar si el jugador quiere, se pregunta al final del código	
        	
        	//IMPORTAR EL DICCIONARIO
        	int palabras = 0;
    		
    		try { //Cuenta las palabras que hay en el diccionario
    			Scanner leerFichero = new Scanner(new File("Diccionario.txt"));
    			while(leerFichero.hasNextLine()) {
    				String line = leerFichero.nextLine();
    				palabras++;
    			}
    			leerFichero.close();
    		}
    		catch(Exception e) {
    			System.out.println("Se ha producido un error: "+e);
    		}
            
    		String diccionario[] = new String[palabras]; //Crear un vector de tamaño el número de palabras que haya en el diccionario para importar las palabras
    		int valAsoc[] = new int[palabras]; //Crear un vector de la misma dimensión que el diccionario para asociarle un valor a cada palabra con lo que introduce el usuario
    		
    		try { //Pasar las palabras del diccionario a un vector de Strings en el programa
    			Scanner leerFichero = new Scanner(new File("Diccionario.txt"));
    			
    			int i = 0;
    			while(leerFichero.hasNextLine()) {
    				String line = leerFichero.nextLine();
    				diccionario[i] = line;
    				i++;
    			}
    			leerFichero.close();
    		}
    		catch(Exception e) {
    			System.out.println("Se ha producido un error: "+e);
    		}
    		
    		
    		
        	//UTILIZANDO EL VECTOR, SE JUEGA
        	int intentos = 0; //Para que como mucho el programa tenga 6 intentos
        	boolean ganar = false;
        	
        	System.out.println("\n\nPiensa una palabra de 5 letras");
            System.out.println("La palabra tiene que existir en el Castellano");
            System.out.println("···");
            System.out.println("¿Ya la has pensado?");
            System.out.println("···");
            System.out.println("");
            System.out.println("Para cada palabra propuesta por el ordenador introduce 5 dígitos (0, 1, 2) según las siguientes instrucciones:\n");
            System.out.println("0 -> Si la letra no está en tu palabra");
            System.out.println("1 -> Si la letra está en tu palabra, pero en otra posición");
            System.out.println("2 -> Si la letra está en tu palabra y en esa posición\n");
                        
            String palIntentadas[] = new String[6]; //Para guardar las palabras ya intentadas
            String palIntentadasCopia[] = new String[6];
            
            for(int i = 0; i < palIntentadas.length; i++) //Inicializamos todas las palabras para poder darse la comparación
            	palIntentadas[i] = "a";
            
            String respuestas[] = new String[6]; //Para guardar las respuestas del usuario
            
            int primera = (int)(Math.random()*palabras); //La primera palabra se elige de forma aleatoria
            String palabraDada = diccionario[primera];//En la variable palabraDada se guarda la última propuesta
            palIntentadas[5] = palabraDada; //la primera palabra dada se guarda en la última posición
            
            String numeros; //El resultado que introduce el usuario a la palabra propuesta
            
            while((intentos < 6) && (!ganar)) {
            	
            	System.out.println(palabraDada);
            	
            	for(int i = 0; i < valAsoc.length; i++) //Resetear los valores asociados cada intento
            		valAsoc[i] = 0;
            	
            	numeros = in.next(); //Pedir el número al usuario
                while(validarNum(numeros) == false) { //Comprobar que el número introducido es correcto
                	System.out.println("ERROR. La entrada no es correcta");
                	System.out.println("Tienes que meter 5 números, y deben ser 0, 1 o 2");
                	numeros = in.next();
                }
                
                respuestas[intentos] = numeros; //Ir guardando los números que introduce el usuario
                
                if(numeros.equals("22222")) {//Si ya se ha adivinado, se termina
                	ganar = true;
                	System.out.println("\n\t¡¡¡HE GANADO!!!\n");
                }
                else {//Si todavía no se ha adivinado la palabra
                	ganar = false; //Por asegurar
                	
                	//Comprobar las posiciones en las que se ha devuelto un 2
                    for(int i = 0; i < diccionario.length; i++) //Recorrer el diccionario
                    	for(int j = 0; j < 5; j++)
                    		if(numeros.charAt(j) == '2')
                    			if(palabraDada.charAt(j) == diccionario[i].charAt(j))
                    				valAsoc[i] += 1000; //Por cada letra que coincida en la posición sumamos 1000
                    
                    //Comprobar las posiciones en las que se ha devuelto un 1
                    for(int i = 0; i < diccionario.length; i++) //Recorrer el diccionario
                    	for(int j = 0; j < 5; j++)
                    		if(numeros.charAt(j) == '1')
                    			for(int k = 0; k < 5; k++)
                    				if(palabraDada.charAt(j) == diccionario[i].charAt(k))
                        				valAsoc[i] += 1; //Por cada letra que coincida en la posición sumamos 2
                    
                    //Comprobar las posiciones en las que se ha devuelto un 0
                    for(int i = 0; i < diccionario.length; i++) //Recorrer el diccionario
                    	for(int j = 0; j < 5; j++)
                    		if(numeros.charAt(j) == '0')
                    			for(int k = 0; k < 5; k++)
                    				if(palabraDada.charAt(j) == diccionario[i].charAt(k))
                        				valAsoc[i] -= 1;
                    
       				for(int i = 0; i < diccionario.length; i++)       // Da valor -1000 a las palabras ya impresas 
                        for(int j = 0; j < palIntentadas.length; j++) // en pantalla para que no vuelvan a salir
                        	if(diccionario[i].equals(palIntentadas[j]))
                        		valAsoc[i] = -1000;
                    
                   	int max = -100; //Damos este valor para que sea mayor que las ya intentadas, pero menor que el mínimo de una no intentada
                   	
                   	for(int i = 0; i < valAsoc.length; i++) //Sacar la mejor propuesta que hacer
                   		for(int j = 0; j < palIntentadas.length; j++)
                   			if(valAsoc[i] > max) {
                       			max = valAsoc[i];
                       			palabraDada = diccionario[i]; 			
                       		}
                   	
                   	if(intentos < 5)
                   		palIntentadas[intentos] = palabraDada; //Ir guardando las palabras ya usadas
                   		//Las palabras propuestas se han guardado en el orden de posiciones del vector: 5-0-1-2-3-4
                   		//Por lo que las ordenamos para luego comprobar si el usuario ha hecho trampas
                   	intentos ++;
                }//Intentos = 6 o el usuario ha ganado (ganar = true)
            }
            
            //Ordenar las palabras intentadas
            palIntentadasCopia[0] = palIntentadas[5];
            for(int i = 0; i < 5; i++)
            	palIntentadasCopia[i+1] = palIntentadas[i];
            
            for(int i = 0; i < palIntentadas.length; i++)
            	palIntentadas[i] = palIntentadasCopia[i]; //En el vector palIntentadas ya están las palabras en el orden correcto
            
            String guardarNueva = "";
            String nuevaPalabra = "";
            boolean trampas = false;
            
            //SI HA PERDIDO: preguntar si se quiere actualizar el diccionario y comprobar si ha hecho trampas
            if(!ganar) {

            	System.out.println("\n\tLO SIENTO, NO HE PODIDO ADIVINARLA\n");
            	
            	System.out.print("¿Qué palabra habías pensado?: ");//Preguntar la palabra que había pensado para comprobar si lo ha hecho bien
            	nuevaPalabra = in.next();
            	nuevaPalabra = todoMayusculas(nuevaPalabra);
            	
            	while(!validarNuevaPalabra(nuevaPalabra)) {
            		System.out.println("La palabra que quiere añadir no es válida");
            		System.out.println("La palabra debe tener 5 letras, todas ellas mayúsculas, del alfabeto inglés y sin espacios\n");
            		System.out.print("Inténtelo de nuevo: ");
            		nuevaPalabra = in.next();
            		nuevaPalabra = todoMayusculas(nuevaPalabra);
            	}
            	
            	trampas = !sinTrampas(respuestas, palIntentadas, nuevaPalabra);//La variable trampas es true si el usuario ha hecho trampas y false si no
            	
            	if(trampas == true) {//Si ha hecho trampas, no vamos a dejar que añada su palabra, al igual que el ordenador no se va a considerar perdedor
            		System.out.println("\nTe hemos pillado haciendo trampas");
            		System.out.println("así que en esta partida voy a considerar que he ganado también");
            	}
            	else {
            		System.out.println("\n¿Quieres añadir tu palabra a mi diccionario?");
                	System.out.print("SI/NO: ");            	
                	guardarNueva = in.next();
                	guardarNueva = todoMayusculas(guardarNueva);
                	
                    while(!((guardarNueva.equals("SI"))||(guardarNueva.equals("NO")))) {
                    	System.out.println("ERROR, debe responder SI o NO");
                        guardarNueva = in.next();
                        guardarNueva = todoMayusculas(guardarNueva);
                    }
            	}
            }
            
            
            
            //ACTUALIZAR EL DICCIONARIO
            try {
            	PrintWriter escribirFichero = new PrintWriter("Diccionario.txt");
            	
            	for(int i = 0; i < diccionario.length; i++) //Escribir todo el diccionario antiguo
            		escribirFichero.println(diccionario[i]);
            	
            	if(guardarNueva.equals("SI")) //Si se ha fallado y se ha querido se escribe la nueva
            		escribirFichero.println(nuevaPalabra);
            	
            	escribirFichero.close();
            }
            catch(Exception e) {
            	System.out.println("Se ha producido un error: "+e);
            }
            
            
            
            //ACTUALIZAR LOS RESULTADOS
            int partidasJugadas = 0, partidasGanadas = 0;

            try { //Tomar los resultados antiguos
            	Scanner leerFichero = new Scanner(new File("Resultados.txt"));
            	
            	partidasJugadas = leerFichero.nextInt();
            	String linea = leerFichero.nextLine(); //Coger el resto de la línea para poder tomar el siguiente valor
            	partidasGanadas = leerFichero.nextInt();

            	leerFichero.close();
            }
            catch(Exception e){
            	System.out.println("Se ha producido un error: "+e);
            }
            
            //Actualizarlos con los resultados
            partidasJugadas++; //Las partidas jugadas siempre aumentan
            if(ganar == true || trampas == true) {
            	partidasGanadas++; //Las partidas ganadas se actualizan si ha ganado, o si el usuario ha hecho trampas
            }
            
            try { //Guardarlos en el documento
            	PrintWriter escribirFichero = new PrintWriter("Resultados.txt");
            	
            	escribirFichero.println(partidasJugadas+" -> PARTIDAS JUGADAS");
            	escribirFichero.println(partidasGanadas+" -> PARTIDAS GANADAS");
            	
            	escribirFichero.close();
            }
            catch(Exception e) {
            	System.out.println("Se ha producido un error: "+e);
            }
            
            
            
            //MOSTRAR LAS ESTADÍSTICAS SI EL USUARIO QUIERE
            System.out.println("\n¿Quieres ver las estadísticas históricas?");
            System.out.print("SI/NO: ");
            String estadisticas = in.next();
            estadisticas = todoMayusculas(estadisticas);
            
            while(!((estadisticas.equals("SI"))||(estadisticas.equals("NO")))) {
            	System.out.println("ERROR, debe responder SI o NO");
                estadisticas = in.next();
                estadisticas = todoMayusculas(estadisticas);
            }
            
            if(estadisticas.equals("SI")) {
            	double porcentaje = ((double)partidasGanadas/partidasJugadas)*100;
            	
            	System.out.println("\n\t\t~ESTADÍSTICAS~\n");            	
            	System.out.println("Partidas jugadas: "+partidasJugadas);
            	System.out.println("Partidas ganadas: "+partidasGanadas);
            	System.out.print("El ordenador ha ganado el ");
            	System.out.printf("%3.2f",porcentaje);
            	System.out.println("% de las veces");
            }
            
            
            
            //VOLVER A JUGAR
            System.out.println("\n¿Desea jugar otra vez?");
            System.out.print("SI/NO: ");
            jugar = in.next();
            jugar = todoMayusculas(jugar);
            
            while(!((jugar.equals("SI"))||(jugar.equals("NO")))) {
            	System.out.println("ERROR, debe responder SI o NO");
                jugar = in.next();
                jugar = todoMayusculas(jugar);
            }
        }
        
        System.out.println("\nMUCHAS GRACIAS POR JUGAR");
        System.out.println("\n\t~Fernando Carmona y Ángel Ibáñez~");
        
        in.close();
    }
}