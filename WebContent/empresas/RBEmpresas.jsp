<%@page import="es.jahernandez.gestion.SegEmpGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>



<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Resultado Búsqueda Interesados</title>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
    //Se cargan los datos del servlet y de paginación
    Vector       empBusq = new Vector();
  
    int          valInf  = 0;
    int          valSup  = 0;
    int          valAnt  = 0;

    EmpresasVO   empVO   = null;
    
    String       cssFI   = "";

    empBusq = (Vector) session.getAttribute("busEmp");
  
    if(empBusq == null) empBusq = new Vector();
 
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

    if(valSup > empBusq.size()) valSup = empBusq.size();
    //if(valAnt < 0) valAnt = 0;
%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

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
	document.getElementById("fraMailing").src = "../mailing/mailing.jsp?tipoMail=5";
	$('#mailing' ).dialog( 'open' );
	
}
</script>

</head>

<body class="fondoFormularios">
<div id="mailing" title="Mailing">
    <iframe name="fraMailing"     id="fraMailing"     frameborder="0" src="" style="width:100%" height="700" scrolling="no"> </iframe>
</div>
<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
                <th colspan="3" style="text-align: center; font-size: 14pt;">
                    Lista Empresas</th>
            </tr>
             <tr>
                <td height="69" style="width: 374px">
                    <img  onmouseover="this.src='../imagenes/anadeusrGr.png'" onmouseout="this.src='../imagenes/anadeusrPe.png'" src="../imagenes/anadeusrPe.png" style="width:64" height="64" onclick="window.open('./altaEmp.jsp','_self','');"/>
                    <img  onmouseover="this.src='../imagenes/mailGr.png'"     onmouseout="this.src='../imagenes/mailPe.png'"     src="../imagenes/mailPe.png"     style="width:64" height="64" onclick="cargaMailing();" /></td>
                <td style="text-align: right; margin-left: 80px">&nbsp;</td>
                <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>
                                                 
<table style="width:100%" class="tablaListadoExtensa"> 
            <tr>
                <td style="width:280"><span class="tablaListadoExtensaCabecera">
                  Raz&oacute;n Social</span></td>
                <td style="width:169"><span class="tablaListadoExtensaCabecera">
                  Nombre comercial</span></td>
                <td style="width:177"><span class="tablaListadoExtensaCabecera">
                  Seg.</span></td>
                <td style="width:207">Tel&eacute;fono</td>
                <td style="width:572"><span class="tablaListadoExtensaCabecera">
                  Responsable</span></td>
  </tr>
      <%for (int ind = valInf; ind < valSup; ind++)
              {
                  empVO = (EmpresasVO) empBusq.elementAt(ind);
                  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";

            %>
     
            <tr id="fila<%=ind%>" class="<%=cssFI%>" onmouseover="this.className='filaresaltada';"  onmouseout="this.className='<%=cssFI%>';" onclick="location.href='./datEmpresa.jsp?codEmp=<%=empVO.getIdEmpresa()%>&ind=<%=valInf%>'">
                <td height="21"><%=empVO.getNombreEmpresa()%></td>
                <td><%=empVO.getNomComercial()%></td>
                <td><%=SegEmpGestion.devolverNumSeg(empVO.getIdEmpresa())%></td>
                <td><%=empVO.getTelEmpresa()%></td>
                <td><%=empVO.getResEmpresa()%></td>
            </tr>
<%} %>
</table>
<table style="width:100%" border="0">
<tr>
        <td style="width:27%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./RBEmpresas.jsp?ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" style="width:50" height="50" border="0" /></a><%}%></td>
        <td style="width:48%" class="cellBtnSub" scope="col"><%=empBusq.size()%> &nbsp;registros</td>
        <td style="width:25%" class="cellBtnSub" scope="col"><%if(valSup < empBusq.size()){%><a href="./RBEmpresas.jsp?ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" style="width:50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
    <p>&nbsp;</p>
</body>
</html>