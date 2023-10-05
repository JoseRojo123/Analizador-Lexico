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
		 if(source.charAt(i + 1) == '='){
                            estado=6;
                            lexema+=c;
                        }else{
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
                        } else if(source.charAt(i + 1) == '*'){
                            estado=14;
                            lexema+=c;

                        }else{
                             estado=12;
                            lexema+=c;
                        }
	}
        else if(c == '{'){
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
        else{
            estado=30;
            lexema+=c;
        }
	break;
	
        //Caso para <
	case 1:
		if(c=='='){
                        estado=2;
                        lexema+=c;
                    }else{
                           Token t1=new Token(TipoToken.LESS, lexema);
                        tokens.add(t1);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
                    }
		break;
	
         //Caso para <=        
	case 2:
		Token t2=new Token(TipoToken.LESS_EQUAL, lexema);
                        tokens.add(t2);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;
	
         //Caso para >        
	case 3:
			if(c=='='){
                        estado=4;
                        lexema+=c;
                    }else{
                           Token t3=new Token(TipoToken.GREATER, lexema);
                        tokens.add(t3);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
                    }
		break;
	
         //Caso para >=        
	case 4:
		Token t4=new Token(TipoToken.GREATER_EQUAL, lexema);
                        tokens.add(t4);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;
	
         //Caso para =
	case 5:
		Token t5 = new Token(TipoToken.EQUAL, lexema);
                        tokens.add(t5);
                        lexema = "";
                        estado = 0; // Cambiamos el estado a 0
                        i--;
		break;	
	
         //Caso para ==        
	case 6:
		Token t6 = new Token(TipoToken.EQUAL_EQUAL, lexema+c);
                        tokens.add(t6);
                        lexema = "";
                        estado = 0; // Cambiamos el estado a 0
                        
		break;				
	
         //Caso para !       
	case 7:
                if(c=='='){
                        estado=8;
                        lexema+=c;
                    }else{
                           Token t7=new Token(TipoToken.BANG, lexema);
                        tokens.add(t7);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
                    }
		break;	
	
         //Caso para !=        
	case 8:
		Token t8=new Token(TipoToken.BANG_EQUAL, lexema);
                        tokens.add(t8);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
	
         //Caso para +        
	case 9:
		Token t9=new Token(TipoToken.PLUS, lexema);
                        tokens.add(t9);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
	
         //Caso para -        
	case 10:
		Token t10=new Token(TipoToken.MINUS, lexema);
                        tokens.add(t10);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
	
         //Caso para *        
        case 11:
		Token t11=new Token(TipoToken.STAR, lexema);
                        tokens.add(t11);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
	
         //Caso para /        
        case 12:
		Token t12=new Token(TipoToken.SLASH, lexema);
                        tokens.add(t12);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;		
	
         //Caso para Comentario de una línea        
	case 13:
		if (c == '\n' || i == source.length() - 1) {
                    estado = 0; // Volver al estado inicial
                    lexema = "";
                } else {
                    lexema += c; // Continuar recopilando el comentario
                }
		break;
	
         //Caso para comentario multilínea        
	case 14:
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
		Token t13=new Token(TipoToken.LEFT_BRACE, lexema);
                        tokens.add(t13);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
	
         //Caso para }        
	case 16:
		Token t14=new Token(TipoToken.RIGHT_BRACE, lexema);
                        tokens.add(t14);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
	
       //Caso para (
        case 17:
		Token t16=new Token(TipoToken.LEFT_PAREN, lexema);
                        tokens.add(t16);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
	
         //Caso para )              
        case 18:
		Token t17=new Token(TipoToken.RIGHT_PAREN, lexema);
                        tokens.add(t17);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
		
	 //Caso para ,	
        case 19:
		Token t18=new Token(TipoToken.COMMA, lexema);
                        tokens.add(t18);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
		
	 //Caso para .	
        case 20:
		Token t19=new Token(TipoToken.DOT, lexema);
                        tokens.add(t19);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
		
	 //Caso para ;	
        case 21:
		Token t20=new Token(TipoToken.SEMICOLON, lexema);
                        tokens.add(t20);
                        estado=0;
                        lexema="";
                        i--; //Regresamos un  caracter para que se analice
		break;	
		
	 //Caso para Identificadores	
        case 22:
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
		if(Character.isDigit(c)){
                        estado = 23;
                        lexema += c;
                        if(source.charAt(i+1)=='E'){
                            estado=25;
                            lexema+='E';
                        }else if(source.charAt(i+1)=='e'){
                            estado=25;
                            lexema+='e';
                        }
                    }
                    else if(c=='E' || c=='e'){
                            estado=25;
                            lexema+='E';
                    }
                    else if(c == '.'){
                        char siguiente= source.charAt(i+1);
                        
                        //Valida si hay dígito después, sino crea el Token 
                        if(Character.isDigit(siguiente)){
                            estado = 24;
                            lexema+=c;
                        }
                        
                        else{
                            Token t = new Token(TipoToken.NUMBER, lexema, Long.valueOf(lexema));
                        tokens.add(t);

                        estado = 0;
                        lexema = "";
                        i--;
                        }

                    }
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

		if(Character.isDigit(c)){
                        estado = 24;
                        lexema += c;
                    }
                    else if(c == 'E' || c == 'e'){
                        estado=25;
                        lexema+=c;
                    }
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
                  if(c=='+' || c== '-' || Character.isDigit(c)){
                      estado=26;
                      lexema+=c;
                  }
                    break;
         
         //Almacena los numeros después de la E o e            
         case 26:
                    if(Character.isDigit(c)){
                        estado = 26;
                        lexema += c;
                    } else{                                          
                        Token t25= new Token(TipoToken.NUMBER, lexema, Double.valueOf(lexema));
                        tokens.add(t25);
                        estado=0;
                        lexema="";
                        i--;
                    }
                    break; 
         
         //Caso para strings           
         case 27: 
             if (c == '"') {
                        // Fin de la cadena
                        Token t24 = new Token(TipoToken.STRING, lexema + c, lexema.replaceAll("\"", ""));
                        tokens.add(t24);
                        estado = 0;
                        lexema = "";
                    }else if (i == source.length() - 1) {
                    // Fin del código fuente y la cadena no tiene cierre de comillas
                    throw new Exception("Error: Cadena sin cierre de comillas en posicion " + i + ": '" + lexema + "'");
                } else {
                    lexema += c;
                }
             break;
             
         case 30:
             estado=0;
              throw new Exception("Error: Caracter no reconocido en posicion " + i + ": '" + lexema + "'");
              
            }
        }
        return tokens;
    }
    }

