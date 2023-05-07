<%@page import="es.jahernandez.gestion.NivelesGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<title>Interesados por Tipo Curso</title>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
</head>
<%
    Vector       listAluCur = new Vector();
    AlumnosVO    aluVO      = null;
    NivelesVO    nivVO      = null;
    CursosAluVO  curAluVO   = null;
    
    String       codCur     = "";
	
	String       cssFI      = "";
    
    //Control de paginación
    int          valInf     = 0;
    int          valSup     = 0;
    int          valAnt     = 0;
	
    
    if(request.getParameter("codCurso") != null)
    {
        codCur = request.getParameter("codCurso");
    }
    
    if(session.getAttribute("busAluCur") != null)
    {
        listAluCur = (Vector) session.getAttribute("busAluCur");
    }
		
    if(request.getParameter("ind") != null)
    {
        valInf = new Integer(request.getParameter("ind")).intValue();
    }
    else
    {
        valInf = 0;
    }

    valSup = valInf + 15 ;
    valAnt = valInf - 15 ;

    if(valSup > listAluCur.size()) valSup = listAluCur.size();
	
%>

<!--Funciones jquery ui-->
<script>
	$(function() {
        $( "#mailing" ).dialog({
           autoOpen: false,
            height: 800,
            width: 1200,
            modal: true,
            buttons: {
                "Mailing": function() {
                   fraMailing.validaForm();
                },
                "Cerrar": function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
	
</script>

<script>
function cargaMailing()
{
	document.getElementById("fraMailing").src = "../mailing/mailing.jsp?tipoMail=3";
	$('#mailing' ).dialog( 'open' );
	
}
</script>



<body class="fondoFormularios">
<div id="mailing" title="Mailing">
    <iframe name="fraMailing"     id="fraMailing"     frameborder="0" src="" style="width:100%" height="700" scrolling="no"> </iframe>
</div>
<table style="width:100%"> 
    	<tr>
             <td colspan="2" class="thDef" >
             Alumnos por Curso
             </td>
        </tr>
        <tr>
        	<td style="width:172" style="height: 19px">
             Curso:
          </td>
            <td style="width:934" style="height: 19px"><%=(CursosGestion.devolverDatosCurso(codCur)).getNomCur()%></td>
        </tr>
        <tr>
          <td height="70" colspan="2" style="height: 19px">
                    <img  onmouseover="this.src='../imagenes/mailGr.png'"     onmouseout="this.src='../imagenes/mailPe.png'"     src="../imagenes/mailPe.png"     style="width:64" height="64" onclick="cargaMailing();" />
                    <img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" style="width:64" height="64" onclick="window.open('../impVisAluCurServlet','_listaAlu','');"/>
          </td>
        </tr>
        </table>
     <table style="width:100%" class="tablaListadoExtensa">   
   <tr>
         <td colspan="2" style="width: 225px; height: 7px; text-align: left;">
             Nombre</td>
         <td style="width:69" style="width: 68px; height: 7px; text-align: left;">
             Móvil</td>
         <td style="width:79" style="width: 78px; height: 7px; text-align: left;">
             Teléfono</td>
         <td style="width:214" style="width: 207px; height: 7px; text-align: left;">
             Correo Electrónico</td>
     </tr>
     <%
          String auxNomNiv = "";
          if (listAluCur != null)
          {
              for (int ind = valInf; ind < valSup; ind++)
              {
                  curAluVO = (CursosAluVO) listAluCur.elementAt(ind);
                  aluVO    = AlumnosGestion.devolverDatosAlumno(curAluVO.getIdAlu());
                  nivVO    = NivelesGestion.devolverDatosNivel(curAluVO.getIdNiv());
				  
				  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";
                  
                  if(! nivVO.getNomNiv().equals(auxNomNiv))
                  {
                    auxNomNiv = nivVO.getNomNiv();%>
                     <tr class="encabezadoSeccionLista">
                         <td colspan="5" style="height: 17px; text-align: center;">
                             <%=auxNomNiv%>
                         </td>
                     </tr>
       			<%}%>
                     <tr class="<%=cssFI%>" onmouseover="this.className='filaresaltada';"  onmouseout="this.className='<%=cssFI%>';" onclick="location.href='../interesados/datosInteresados.jsp?pestana=1&codInt=<%=aluVO.getIdAlu()%>'">
                         <td colspan="2" style="width: 225px; height: 18px; text-align: left;">
                            <%=aluVO.getAp1Alu()%> , <%=aluVO.getNombre()%>
                         </td>
                         <td style="width: 68px; height: 18px; text-align: left;">
                             <%=aluVO.getMovAlu()%>
                         </td>
                         <td style="width: 78px; height: 18px; text-align: left;">
                             <%=aluVO.getFijAlu()%>
                         </td>
                         <td style="width: 207px; height: 18px; text-align: left;">
                             <%=aluVO.getEmail()%>
                         </td>
                     </tr>
      <%      }
            }
        %>
         <tr>
           <td colspan="2">&nbsp;</td>
           <td>&nbsp;</td>
           <td colspan="2" style="text-align: right"></td>
         </tr>
         <tr>
           <td style="width:168" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./lisIntCur.jsp?ind=<%=valAnt%>&codCurso=<%=codCur%>"><img src="../imagenes/btnprev.png" alt="&lt;---" style="width:50" height="50" border="0" /></a><%}%></td>
           <td style="width:858" class="cellBtnSub" scope="col"><%=listAluCur.size()%> &nbsp;registros</td>
           <td colspan="3" class="cellBtnSub" scope="col"><%if(valSup < listAluCur.size()){%><a href="./lisIntCur.jsp?ind=<%=valSup%>&codCurso=<%=codCur%>"><img src="../imagenes/btnsig.png" alt="---&gt;" style="width:50" height="50" border="0" /></a><%}%></td>
         </table>
</body>
</html>