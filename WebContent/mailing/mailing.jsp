<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@page import="es.jahernandez.gestion.AluEdiGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>

<%@page import="java.util.Vector"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mailing</title>

<%@ include file="../controlAcceso/includeComAutProf.jsp"%>

<%
String listDest        = "";
String listDestFor     = "";
Vector mailingBusq     = null;
int    tipoMail        = -99;

if(request.getParameter("tipoMail") != null)
{
    tipoMail = new Integer(request.getParameter("tipoMail")).intValue();
}       

switch(tipoMail)
{
    case 1:
        if(session.getAttribute("busAlu") != null)
        {
            mailingBusq = (Vector) session.getAttribute("busAlu");
             //Cargamos los mails de los destinatarios
            for(int ind = 0; ind < mailingBusq.size() ; ind++)
            {
                AlumnosVO aluMailVO = (AlumnosVO) mailingBusq.elementAt(ind);

                if(! aluMailVO.getEmail().trim().equals("") )
                {
                    listDest = listDest + aluMailVO.getEmail().trim() + ",";
                    listDestFor = listDestFor + aluMailVO.getEmail().trim() + "  " + aluMailVO.getNombre() + " " + aluMailVO.getAp1Alu() + " ,";
                }
            }

			if(listDest.length() >= 1)
			{
				listDest = listDest.substring(0,listDest.length()-1); //Elimina la última coma
			}
			
			if(listDestFor.length() >= 1)
			{
				listDestFor = listDestFor.substring(0,listDestFor.length()-1); //Elimina la última coma
            	listDestFor = listDestFor.replace(",", "\n");
			}



            session.setAttribute("dest", listDest );
        }
        break;

    case 2: 
        if(session.getAttribute("busAluTipCur") != null)
        {
            mailingBusq = (Vector) session.getAttribute("busAluTipCur");

             //Cargamos los mails de los destinatarios
            for(int ind = 0; ind < mailingBusq.size() ; ind++)
            {
                CursosAluVO curAluMailVO = (CursosAluVO) mailingBusq.elementAt(ind);
                AlumnosVO   aluMailVO    = AlumnosGestion.devolverDatosAlumno(curAluMailVO.getIdAlu());

                if(! aluMailVO.getEmail().trim().equals("") )
                {
                    listDest = listDest + aluMailVO.getEmail().trim() + ",";
                    listDestFor = listDestFor + aluMailVO.getEmail().trim() + "  " + aluMailVO.getNombre() + " " + aluMailVO.getAp1Alu() + " ,";
                }
            }

            if(listDest.length() >= 1)
			{
				listDest = listDest.substring(0,listDest.length()-1); //Elimina la última coma
			}
			
			if(listDestFor.length() >= 1)
			{
				listDestFor = listDestFor.substring(0,listDestFor.length()-1); //Elimina la última coma
            	listDestFor = listDestFor.replace(",", "\n");
			}

            session.setAttribute("dest", listDest );
        }
        break;
    case 3:
        if(session.getAttribute("busAluCur") != null)
        {
            mailingBusq = (Vector) session.getAttribute("busAluCur");

             //Cargamos los mails de los destinatarios
            for(int ind = 0; ind < mailingBusq.size() ; ind++)
            {
                CursosAluVO curAluMailVO = (CursosAluVO) mailingBusq.elementAt(ind);
                AlumnosVO   aluMailVO    = AlumnosGestion.devolverDatosAlumno(curAluMailVO.getIdAlu());

                if(! aluMailVO.getEmail().trim().equals("") )
                {
                    listDest = listDest + aluMailVO.getEmail().trim() + ",";
                    listDestFor = listDestFor + aluMailVO.getEmail().trim() + "  " + aluMailVO.getNombre() + " " + aluMailVO.getAp1Alu() + " ,";
                }
            }

            if(listDest.length() >= 1)
			{
				listDest = listDest.substring(0,listDest.length()-1); //Elimina la última coma
			}
			
			if(listDestFor.length() >= 1)
			{
				listDestFor = listDestFor.substring(0,listDestFor.length()-1); //Elimina la última coma
            	listDestFor = listDestFor.replace(",", "\n");
			}

            session.setAttribute("dest", listDest );
        }
        break;
    case 4:
        if(session.getAttribute("busProf") != null)
        {
            mailingBusq = (Vector) session.getAttribute("busProf");

             //Cargamos los mails de los destinatarios
            for(int ind = 0; ind < mailingBusq.size() ; ind++)
            {
                ProfesoresVO profVO = (ProfesoresVO) mailingBusq.elementAt(ind);
                
                if(! profVO.getEmail().trim().equals("") )
                {
                    listDest = listDest + profVO.getEmail().trim() + ",";
                    listDestFor = listDestFor + profVO.getEmail().trim() + "  " + profVO.getNombre() + " " + profVO.getApellidos()+ " ,";
                }
            }

            if(listDest.length() >= 1)
			{
				listDest = listDest.substring(0,listDest.length()-1); //Elimina la última coma
			}
			
			if(listDestFor.length() >= 1)
			{
				listDestFor = listDestFor.substring(0,listDestFor.length()-1); //Elimina la última coma
            	listDestFor = listDestFor.replace(",", "\n");
			}

            session.setAttribute("dest", listDest );
        }
        break;     
     case 5:
        if(session.getAttribute("busEmp") != null)
        {
            mailingBusq = (Vector) session.getAttribute("busEmp");

             //Cargamos los mails de los destinatarios
            for(int ind = 0; ind < mailingBusq.size() ; ind++)
            {
                EmpresasVO empVO = (EmpresasVO) mailingBusq.elementAt(ind);
                
                if(! empVO.getMailEmpresa().trim().equals("") )
                {
                    listDest = listDest + empVO.getMailEmpresa().trim() + ",";
                    listDestFor = listDestFor + empVO.getMailEmpresa().trim() + "  " + empVO.getNomComercial() + " " + empVO.getNombreEmpresa() + ",";
                }
            }

            if(listDest.length() >= 1)
			{
				listDest = listDest.substring(0,listDest.length()-1); //Elimina la última coma
			}
			
			if(listDestFor.length() >= 1)
			{
				listDestFor = listDestFor.substring(0,listDestFor.length()-1); //Elimina la última coma
            	listDestFor = listDestFor.replace(",", "\n");
			}

            session.setAttribute("dest", listDest );
        }
        break;      
        
        case 6:
        if(request.getParameter("codEdi") != null)
        {
            EdicionesVO ediVO = EdicionesGestion.devolverDatosEdi(request.getParameter("codEdi"));
           
            mailingBusq = AluEdiGestion.devAluMatEdi(ediVO.getIdEdi());
            
             //Cargamos los mails de los destinatarios
            for(int ind = 0; ind < mailingBusq.size() ; ind++)
            {
                AluEdiVO  aluEdiVO = (AluEdiVO) mailingBusq.elementAt(ind);
                AlumnosVO aluVO    = AlumnosGestion.devolverDatosAlumno(aluEdiVO.getIdAlu());
                
                if(! aluVO.getEmail().trim().equals("") )
                {
                    listDest = listDest + aluVO.getEmail().trim() + ",";
                    listDestFor = listDestFor + aluVO.getEmail().trim() + "  " + aluVO.getNombre() + " " + aluVO.getAp1Alu() + " ,";
                }
            }

            if(listDest.length() >= 1)
			{
				listDest = listDest.substring(0,listDest.length()-1); //Elimina la última coma
			}
			
			if(listDestFor.length() >= 1)
			{
				listDestFor = listDestFor.substring(0,listDestFor.length()-1); //Elimina la última coma
            	listDestFor = listDestFor.replace(",", "\n");
			}
            session.setAttribute("dest", listDest );
        }
        break;      
         
    }
%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarCampos  = true;		
	var listaFich      = "";
	
	var combo = document.getElementById("lstArchivos"); 

	validarCampos = sprytextfield1.validate() & sprytextarea1.validate();
				   			   
	if(validarCampos)
	{
		for (var i = 0; i < combo.length; i++) 
		{
			//Recogemos el “option” actual
			opt = combo[i];

			// validamos para que no coger el primer option que seria “Seleccione Productos” con el valor “-1″
			//Armamos la cadena de la siguiente forma: 10, 15, 20, 65
			if (i == 0) 
			{
				listaFich = opt.value;
			} 
			else 
			{
				listaFich = listaFich + "," +  opt.value;
			}
		}

		
		frmMail.action = "../enviarMensajeServlet?lstArchivos=" + listaFich;
		frmMail.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados")
	}
	
}
  
function subirFichero(nombreFic)
{
	$('#lstArchivos').append('<option value="' + nombreFic + '">' + nombreFic +'</option>');
}

function quitarFichero()
{
	$("#lstArchivos option:selected").remove();
}


</script>


</head>


 
<body class="fondoFormularios">
<form action="" method="post" name="frmMail" id="frmMail">
<table width="100%" border="0">
  <tr class="tdDef">
    <td width="15%" height="88" valign="top" class="tdDef">Destinatarios</td>
    <td colspan="2" class="tdDef"><textarea name="txtDest" cols="100" rows="8" readonly="readonly" id="txtDest"><%=listDestFor%></textarea></td>
  </tr>
  <tr class="tdDef">
    <td>Asunto</td>
    <td colspan="2"><span id="valAsunto">
      <input name="txtAsunto" type="text" id="txtAsunto" size="100" maxlength="150" />
      <span class="textfieldRequiredMsg">*.</span></span></td>
    </tr>
  <tr>
    <td valign="top" class="tdDef">Cuerpo Mensaje</td>
    <td colspan="2"><span id="valMensaje">
      <textarea name="txtCuerpo" id="txtCuerpo" cols="100" rows="15"></textarea>
      <span class="textareaRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td class="tdDef">Adjuntar Archivo</td>
    <td width="49%" class="tdDef"><iframe src="./subirFic.jsp" width="600px" height="48px" frameborder="0"></iframe></td>
    <td width="36%"  class="tdDef">
      <select name="lstArchivos" size="4" id="lstArchivos" style="width:150px"></select>
      <br>	
        <input class="cellBtnSub" type="button" name="btnEliArc" id="btnEliArc" value="Elimina Archivo" onclick="quitarFichero();" />
      </td>
    </tr>
  <tr>
    
    <td colspan="3">&nbsp;</td>
  </tr>
  </table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("valAsunto");
var sprytextarea1 = new Spry.Widget.ValidationTextarea("valMensaje");
</script>
</body>
</html>