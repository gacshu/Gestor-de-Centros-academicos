<%@page import="es.jahernandez.gestion.EmpresasGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>



<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Resultado Búsqueda Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
    //Se cargan los datos del servlet y de paginación
    Vector alumnosBusqueda = new Vector();
    String muesCond        = "";

    int    valInf       = 0;
    int    valSup       = 0;
    int    valAnt       = 0;

    AlumnosVO  aluVO    = null;
    EmpresasVO empVO    = null;

    String     cssFI    = "";
    String     listMail = "";

    alumnosBusqueda = (Vector) session.getAttribute("busAlu");
    muesCond        = (String) session.getAttribute("muesCond");

    if(alumnosBusqueda == null) alumnosBusqueda = new Vector();
    if(muesCond        == null) muesCond        = "";

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

    if(valSup > alumnosBusqueda.size()) valSup = alumnosBusqueda.size();
    //if(valAnt < 0) valAnt = 0;
         
%>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />


</head>


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
	document.getElementById("fraMailing").src = "../mailing/mailing.jsp?tipoMail=1";
	$('#mailing' ).dialog( 'open' );
	
}
</script>




<body class="fondoFormularios">
<div id="mailing" title="Mailing">
    <iframe name="fraMailing"     id="fraMailing"     frameborder="0" src="" width="100%" height="700" scrolling="no"> </iframe>
</div>
<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
                <th colspan="3" style="text-align: center; font-size: 14pt;">
                    Lista Alumnos</th>
            </tr>
             <tr>
                <td width="374" height="69" style="width: 374px">
                	<img  onmouseover="this.src='../imagenes/anadeusrGr.png'" onmouseout="this.src='../imagenes/anadeusrPe.png'" src="../imagenes/anadeusrPe.png" width="64" height="64" onclick="window.open('./altaInteresados.jsp','_self','');"/>
                    <img  onmouseover="this.src='../imagenes/mailGr.png'"     onmouseout="this.src='../imagenes/mailPe.png'"     src="../imagenes/mailPe.png"     width="64" height="64" onclick="cargaMailing();"/>
                <img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" width="64" height="64" onclick="window.open('../ImpLisAluServlet','_listaAlu','');"/></td>
                <td width="477" class="tdDef"></td>
                <td width="545" style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>
                                                 
<table width="100%" class="tablaListadoExtensa"> 
            <tr>
                <td width="235"><span class="tablaListadoExtensaCabecera">
                  Nombre</span></td>
                <td width="120"><span class="tablaListadoExtensaCabecera">
                  Móvil</span></td>
                <td width="120"><span class="tablaListadoExtensaCabecera">
                  Teléfono</span></td>
                <td width="285"><span class="tablaListadoExtensaCabecera">
                  Correo Elect.</span></td>
                <td width="183"><span class="tablaListadoExtensaCabecera">
                  Población</span></td>
                <td width="391"><span class="tablaListadoExtensaCabecera">
                  Empresa</span></td>
                <td width="29"><span class="tablaListadoExtensaCabecera">
                  ND</span></td>
  </tr>
      <%for (int ind = valInf; ind < valSup; ind++)
              {
                  aluVO = (AlumnosVO) alumnosBusqueda.elementAt(ind);
                  empVO = EmpresasGestion.devolverDatEmp(aluVO.getEmpAlu());

                  if (empVO == null) empVO = new EmpresasVO();


                  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";

            %>
     
            <tr id="fila<%=ind%>" class="<%=cssFI%>" onmouseover="this.className='filaresaltada';"  onmouseout="this.className='<%=cssFI%>';" onclick="location.href='./datosInteresados.jsp?pestana=1&codInt=<%=aluVO.getIdAlu()%>&ind=<%=valInf%>'">
                <td height="21"><%=aluVO.getAp1Alu()%> , <%=aluVO.getNombre() %></td>
                <td><%=aluVO.getMovAlu()%></td>
                <td><%=aluVO.getFijAlu()%></td>
                <td><%=aluVO.getEmail()%></td>
                <td><%=aluVO.getLocalAlu()%></td>
                <td><%=empVO.getNombreEmpresa()%>
                  &nbsp;</td>
                <td><%if (aluVO.isAlND()){%>
                  <img src="../imagenes/si.png" width="16" height="16" alt="X" />
                <%}%></td>
            </tr>
<%} %>
</table>
<table width="100%" border="0">
<tr>
        <td width="27%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./RBInteresado.jsp?ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" width="50" height="50" border="0" /></a><%}%></td>
        <td class="cellBtnSub" scope="col"><%=alumnosBusqueda.size()%> &nbsp;registros</td>
        <td width="22%" class="cellBtnSub" scope="col"><%if(valSup < alumnosBusqueda.size()){%><a href="./RBInteresado.jsp?ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" width="50" height="50" border="0" /></a><%}%></td>
        
      </tr>
</table>
    <p>&nbsp;</p>
</body>
</html>