import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {

    private static final Map<String, TipoToken> palabrasReservadas;

    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("and",    TipoToken.AND);
        palabrasReservadas.put("else",   TipoToken.ELSE);
        palabrasReservadas.put("false",  TipoToken.FALSE);
        palabrasReservadas.put("for",    TipoToken.FOR);
        palabrasReservadas.put("fun",    TipoToken.FUN);
        palabrasReservadas.put("if",     TipoToken.IF);
        palabrasReservadas.put("null",   TipoToken.NULL);
        palabrasReservadas.put("or",     TipoToken.OR);
        palabrasReservadas.put("print",  TipoToken.PRINT);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("true",   TipoToken.TRUE);
        palabrasReservadas.put("var",    TipoToken.VAR);
        palabrasReservadas.put("while",  TipoToken.WHILE);
    }

    private final String source;

    private final List<Token> tokens = new ArrayList<>();
    
    public Scanner(String source){
        this.source = source + " ";
    }

    public List<Token> scan() throws Exception {
        String lexema = "";
        int estado = 0;
        char c;
       
       
        for(int i=0; i<source.length(); i++){
            c = source.charAt(i);
            
            switch (estado){
          
                //Estado base
                case 0:
                    if(c == '<'){
                        lexema+=c;
                        estado=1;
                    }else if(c == '>'){
                        lexema+=c;
                        estado=3;
                    }else if(c == '='){
                        
                        //Verifica si es un ==
                        if(source.charAt(i + 1) == '='){
                            estado=6;
                           lexema+=c;
                       }else{  //Si no, lo es solo un =
                            estado=5;
                            lexema+=c;
                       }
                    }else if(c == '!'){
                        lexema+=c;
                        estado=7;
                    }else if(c == '+'){
                        lexema+=c;
                        estado=9;
                    }else if(c == '-'){
                        lexema+=c;
                        estado=10;
                    }else if(c == '*'){
                        lexema+=c;
                        estado=11;
                    }else if(c == '/'){
                        
                        //verificamos si es comentario de una línea
                        if (source.charAt(i + 1) == '/') {
                            estado = 13;
                            lexema += c;
                       } 
                        
                        //Verifica si es un comentario multilínea
                        else if(source.charAt(i + 1) == '*'){
                            estado=14;
                            lexema+=c;
                            
                        //Si no es ninguno, entonces solo es un slash    
                        }else{
                            estado=12;
                            lexema+=c;
                       }
                    }else if(c == '{'){
                        lexema+=c;
                        estado=15;
                    }else if(c == '}'){
                        lexema+=c;
                        estado=16;
                    }else if(c == '('){
                        lexema+=c;
                        estado=17;
                    }else if(c == ')'){
                        lexema+=c;
                        estado=18;
                    }else if(c == ','){
                        lexema+=c;
                        estado=19;
                    }else if(c == '.'){
                        lexema+=c;
                        estado=20;
                    }else if(c == ';'){
                        lexema+=c;
                        estado=21;
                    }else if(Character.isLetter(c)){
                        lexema+=c;
                        estado=22;
                    }else if(Character.isDigit(c)){
                        lexema+=c;
                        estado=23;
                    }else if (c == '"') {
                        estado = 27;
                        lexema += c;
                    }else if(c == ' ' || c== '\t'){
                    }
                    else if(c == '\r' || c == '\n'){    
                    }
                    
                    /*Si no es ninguno de los caracteres antes declarados, lo manda al estado 30 que 
                    arroja un error*/
                    else{
                        estado=30;
                        lexema+=c;
                    }
                    
                break;
	
                 //Caso para <
                case 1:
                    
                    //Verifica si hay un igual después para ver si es un <=
                    if(c=='='){
                        estado=2;
                        lexema+=c;
                    }
                    
                    //Si no, crea el Token de <
                    else{
                        Token t1=new Token(TipoToken.LESS, lexema);
                        tokens.add(t1);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
                    }
                    
		break;
	
                //Caso para <=        
                case 2:
                    
                    //Crea el Token de <=
                    Token t2=new Token(TipoToken.LESS_EQUAL, lexema);
                    tokens.add(t2);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;
	
                //Caso para >        
                case 3:
                    
                    //Verifica si hay un igual después para ver si es un >=
                    if(c=='='){
                        estado=4;
                        lexema+=c;
                    }
                    
                   //Si no, crea el Token de >
                    else{
                        Token t3=new Token(TipoToken.GREATER, lexema);
                        tokens.add(t3);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
                    }
                    
		break;
	
                //Caso para >=        
                case 4:

                   //Crea el Token de >=
                   Token t4=new Token(TipoToken.GREATER_EQUAL, lexema);
                   tokens.add(t4);
                   estado=0;
                   lexema="";
                   i--; //Regresamos un  caracter para que se analice
                   
               break;
	
                //Caso para =
                case 5:

                    //Crea el Token de =
                    Token t5 = new Token(TipoToken.EQUAL, lexema);
                    tokens.add(t5);
                    lexema = "";
                    estado = 0; // Cambiamos el estado a 0
                    i--;
                    
                break;	
	
                //Caso para ==        
               case 6:
                   
                        //Crea el Token de ==
                        Token t6 = new Token(TipoToken.EQUAL_EQUAL, lexema+c);
                        tokens.add(t6);
                        lexema = "";
                        estado = 0; // Cambiamos el estado a 0
                        
		break;				
	
                //Caso para !       
                case 7:
                    
                    //Verifica si hay un igual después para ver si es un != 
                    if(c=='='){
                        estado=8;
                        lexema+=c;
                    }
                    
                    //Si no, crea el Token de !
                    else{
                        Token t7=new Token(TipoToken.BANG, lexema);
                        tokens.add(t7);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
                }
                    
		break;	
	
                //Caso para !=        
                case 8:
                    
                   //Crea el Token para !=
                    Token t8=new Token(TipoToken.BANG_EQUAL, lexema);
                    tokens.add(t8);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                        
		break;	
	
                //Caso para +        
                case 9:

                    //Crea el Token para +
                    Token t9=new Token(TipoToken.PLUS, lexema);
                    tokens.add(t9);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
	
                //Caso para -        
                case 10:
                    
                    //Crea el Token para -
                    Token t10=new Token(TipoToken.MINUS, lexema);
                    tokens.add(t10);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
	
                //Caso para *        
                case 11:

                   //Crea el Token para *
                    Token t11=new Token(TipoToken.STAR, lexema);
                    tokens.add(t11);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                        
		break;	
	
                //Caso para /        
                case 12:
                    
                    //Crea el Token para /
                    Token t12=new Token(TipoToken.SLASH, lexema);
                    tokens.add(t12);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;		
	
                //Caso para Comentario de una línea        
                case 13:

                   /*Verifica si el caracter actual es un salto de l[inea y si el iterador est[a en la 
                   última posici[on de la cadena*/
                   if (c == '\n' || i == source.length() - 1) {
                       estado = 0; // Volver al estado inicial
                       lexema = "";
                   } else {
                       lexema += c; // Continuar recopilando el comentario
                   }
            
		break;
	
                 //Caso para comentario multilínea        
                case 14:
                    
                    //Verifica cuando haya un */ que indique que es el fin del comentario multil[inea
                    if (c == '*' && source.charAt(i + 1) == '/') {
                        estado = 0; // Fin del comentario, volver al estado inicial
                        lexema = "";
                        i++; // Avanzar un carácter para omitir el '/' en el comentario cerrado
                    } else {
                        if (c == '\n') {
                            // Si encontramos un salto de línea, simplemente seguimos recopilando el comentario
                            lexema += c;
                        } else {
                            lexema += c; // Continuar recopilando el comentario multilínea
                        }
                    }  
                    
		break;		
	
                 //Caso para {
                case 15:
                    
                    //Crea el Token para {
                    Token t13=new Token(TipoToken.LEFT_BRACE, lexema);
                    tokens.add(t13);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
	
                 //Caso para }        
                case 16:
                    
                    //Crea el Token para }
                    Token t14=new Token(TipoToken.RIGHT_BRACE, lexema);
                    tokens.add(t14);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
	
                //Caso para (
                 case 17:
                     
                     //Crea e; Token para (
                    Token t16=new Token(TipoToken.LEFT_PAREN, lexema);
                    tokens.add(t16);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
	
                 //Caso para )              
                case 18:
            
                    //Crea e; Token para )
                    Token t17=new Token(TipoToken.RIGHT_PAREN, lexema);
                    tokens.add(t17);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
		
                 //Caso para ,	
                case 19:
                    
                    //Crea e; Token para ,
                    Token t18=new Token(TipoToken.COMMA, lexema);
                    tokens.add(t18);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
		
                //Caso para .	
                case 20:

                   //Crea el Token para .
                    Token t19=new Token(TipoToken.DOT, lexema);
                    tokens.add(t19);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                        
		break;	
		
                //Caso para ;	
                case 21:
                   //Crea el Token para ;
                    Token t20=new Token(TipoToken.SEMICOLON, lexema);
                    tokens.add(t20);
                    estado=0;
                    lexema="";
                    i--; //Regresamos un  caracter para que se analice
                    
		break;	
		
                //Caso para Identificadores	
                case 22:
                    
                    //Valida si elc aracter actual es una letra o un d[igito y mantiene el estado 22 
                    if(Character.isLetter(c) || Character.isDigit(c)){
                        estado = 22;
                        lexema += c;
                    }
                    else{
                        
                        // Vamos a crear el Token de identificador o palabra reservada
                        TipoToken tt = palabrasReservadas.get(lexema);

                        if(tt == null){
                            Token t = new Token(TipoToken.IDENTIFIER, lexema);
                            tokens.add(t);
                        }
                        else{
                            Token t = new Token(tt, lexema);
                            tokens.add(t);
                        }

                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    
                break;
			
                //Caso para números	
                case 23:
                    
                    //Verifica si c es un número
                    if(Character.isDigit(c)){
                        estado = 23;
                        lexema += c;
                        
                        //Verifica si el c es una E  para indicar que se va a elevar a un exponente un número
                        if(source.charAt(i+1)=='E'){
                            estado=25;
                            lexema+='E';
                        }
                        
                        //Verifica si el c es una e para indicar que se va a elevar a un exponente un número
                        else if(source.charAt(i+1)=='e'){
                            estado=25;
                            lexema+='e';
                        }
                    }
                    
                    //Verifica si el c es una E  para indicar que se va a elevar a un exponente un número (Caso para un d[igito)
                    else if(c=='E' || c=='e'){
                        estado=25;
                        lexema+='E';
                    }
                    
                    //Verfica si c es un punto indicando que ser[a un número decimal
                    else if(c == '.'){
                        char siguiente= source.charAt(i+1); //Variable auxiliar
                        
                        //Valida si hay dígito después del punto 
                        if(Character.isDigit(siguiente)){
                            estado = 24;
                            lexema+=c;
                        }
                        
                        /*Crea el Token de solo los números antes del punto porque no hubo otro número 
                        después del punto*/
                        else{
                            Token t = new Token(TipoToken.NUMBER, lexema, Long.valueOf(lexema));
                            tokens.add(t);
                            estado = 0;
                            lexema = "";
                            i--;
                        }
                    }
                    
                    //Se crea el Token de número (Es un número entero sin exponente)
                    else{
                        Token t = new Token(TipoToken.NUMBER, lexema, Long.valueOf(lexema));
                        tokens.add(t);
                        estado = 0;
                        lexema = "";
                        i--;
                    }
                    
		break;	
		
                 //Caso para numeros con decimales 
                case 24:
                    
                    //Verifica que sea un dígito
                    if(Character.isDigit(c)){
                        estado = 24;
                        lexema += c;
                    }
                    
                    //Verifica si c es una E o e indicando que va a ser un número con exponente
                    else if(c == 'E' || c == 'e'){
                        estado=25;
                        lexema+=c;
                    }
                    
                    //Si no, se crea el Token de número (Va a ser un número decimal)
                    else{                                          
                        Token t23= new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t23);
                        estado=0;
                        lexema="";
                        i--;
                    }
                    
		break;      
	
                 //Caso para después de la "E o e" que le sigue una +, - o número	
                case 25:
                    
                    //Verfica si c es un +, - o un Dígito
                    if(c=='+' || c== '-' || Character.isDigit(c)){
                        estado=26;
                        lexema+=c;
                    } //Aquí va código para cuando no hay +, - o número después de E
                    
                break;
         
                //Almacena los numeros después de la E o e            
                case 26:   
                    //Verifica que c sea un dígito
                    if(Character.isDigit(c)){
                        estado = 26;
                        lexema += c;
                    } 
                    //Si no, crea ya el Token de número
                    else{                                          
                        Token t25= new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t25);
                        estado=0;
                        lexema="";
                        i--;
                    }
                    
                break; 
         
                //Caso para strings           
                case 27: 
                    
                    //Verifica si c son unas comillas lo que indicaría el fin de cadena
                    if (c == '"') {
                        // Se crea el Token de cadena
                        Token t24 = new Token(TipoToken.STRING, lexema + c, lexema.replaceAll("\"", ""));
                        tokens.add(t24);
                        estado = 0;
                        lexema = "";
                    }
                    
                    //Si no se cerraron las comillas, lanzará un error
                    else if (i == source.length() - 1) {
                    // Fin del código fuente y la cadena no tiene cierre de comillas
                    throw new Exception("Error: Cadena sin cierre de comillas en posicion " + i + ": '" + lexema + "'");
                    } else {
                        lexema += c; //Sigue alamcenando los caracteres hasta que se cumpla un caso anterior
                    }
                break;
             
                //Caso de caracter no reconocido o declarado previamente    
                case 30:
                    estado=0;
                    throw new Exception("Error: Caracter no reconocido en posicion " + i + ": '" + lexema + "'");
            }
        }
        return tokens;
    }
    }

