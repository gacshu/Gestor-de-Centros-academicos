//Función que elimina los espacios en blanco por la izquierda
 function LTrim(str)
 {
	/***
		Propósito: Eliminar los espacios en blanco por la izquierda.
    	Entrada: str - Cadena a la que queremos eliminar los blancos 
    	Devuelve: La cadena de entrada sin blancos por la izquierda 
	***/

	var whitespace = new String("\t\n\r ");
        var s = new String(str);
	if(whitespace.indexOf(s.charAt(0)) != -1)
	{
		//We have a string with leading blank(s)...

		var j = 0, i = s.length;

		// Iterate from the far left of string until we don't have any more whitespace...

		while(j<i && whitespace.indexOf(s.charAt(j)) != -1) j ++

		// Get de substring from the first non-whitespace character to the end of the string...

		s = s.substring(j,i);
	}
	return s;
 }
 
 //Función que elimina los espacios en blanco por la derecha	
 function RTrim(str)
 {
	/***
		Propósito: Eliminar los espacios en blanco por la izquierda.
    	Entrada: str - Cadena a la que queremos eliminar los blancos 
    	Devuelve: La cadena de entrada sin blancos por la izquierda 
	***/
	
	var whitespace = new String("\t\n\r ");
    var s = new String(str);
	if(whitespace.indexOf(s.charAt(s.length - 1)) != -1)
	{
		//We have a string with trailing blank(s)...

		var i = s.length - 1;   // Get length of string

		// Iterate from the far right of string until we don't have any more whitespace...

		while(i>=0 && whitespace.indexOf(s.charAt(i)) != -1) i--

		// Get de substring from the front of the string to where the last non-whitespace char

		s = s.substring(0,i+1);
	}
	return s;
 }



 //Función que elimina los espacios en blanco por la izquierda y por la derecha en una cadena.
 function Trim(str)
 {
	/***
		Propósito: Eliminar los espacios en blanco por izquierda y derecha.
    	Entrada: str - Cadena a la que queremos eliminar los blancos 
    	Devuelve: La cadena de entrada sin blancos por izquierda y derecha.
	***/
	return LTrim(RTrim(str));
 }

 //Función que comprueba que una cadena tenga una longitud predeterminada
 function cadenaVacia(inputString)
 {
 	return (inputString.length == 0) ? true : false;
 }
 
 //Función que comprueba que una cadena sea un numero de coma flotante
 function isNumberFloat(inputString)
 {
  	return (!isNaN(parseFloat(inputString))) ? true : false;
 }
 
 //Función que comprueba que una cadena sea un numero entero
 function isNumberInt(inputString)
 {
  	return (!isNaN(parseInt(inputString))) ? true : false;
 }

//Función que valida si una cadena dada es un código postal válido
function isCP(inputString)
{
	var expRegCP = /^([1-9]{2}|[0-9][1-9]|[1-9][0-9])[0-9]{3}$/
	if (expRegCP.test(inputString)) 
	{ 
		return true;
	} 
	else 
	{ 
 		if(Trim(inputString)=='') 
		{
			return true;	
		}
		
		return false;  
 	} 

}

//Función que valida si un email es válido
function isEmail(inputString)
{
	var expRegMail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/
	if(expRegMail.test(inputString))
	{ 
		return true;
	} 
	else 
	{ 
 		if(Trim(inputString)=='') 
		{
			return true;	
		}
		  
		return false;  
 	} 

}