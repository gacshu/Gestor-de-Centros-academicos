<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.AluEdiGestion"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<html lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Cursos interesado</title>
<%@ include file="../controlAcceso/includeComAutProf.jsp"%>
<%
    //Carga de datos del formulario de búsqueda
    EdicionesVO ediVO   = null;
    CursosVO    curVO   = null;

    int         indInf  = 0;
    int         indSup  = 0;

    Vector      vecEdi  = null;
	
    String      cssFI    = "";

    boolean     errAlta = false;
    boolean     errEdi  = false;
    int         resBor  = -99;
    
    ConUsuVO    usuario =  null;
    
    boolean     msgEdiCor    = false;
    boolean     msgEdiCreada = false;
    boolean     colorBlanco  = false;
    boolean     msgModError  = false;
    
    //Cargamos datos de usuario
    if(session.getAttribute("usuario")!= null)
    {
        usuario = (ConUsuVO) session.getAttribute("usuario");
    }
    
    
    //Paginación de resultados
    if(request.getParameter("valInfEdi") != null)
    {
            indInf = new Integer(request.getParameter("valInfEdi")).intValue();
    }
    
    if(request.getParameter("msgEdiCor") != null)
    {
        msgEdiCor = true;
    }

    if(request.getParameter("msgEdiCreada") != null)
    {
        msgEdiCreada = true;
    }
        
    if(request.getParameter("msgModError") != null)
    {
        msgModError = true;
    }    
    
    vecEdi = EdicionesGestion.devolverEdiTutor(usuario.getIdProf());
    
    
    if(indInf < 0)
    {
        indInf = 0;
    }

    indSup = indInf + 10;
    if(indSup > vecEdi.size()) 
    {
        indSup = vecEdi.size();
    }
%>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>


<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

</head>
<%if(resBor == indInf-1){%>
<script>
    alert("Se produjeron errores en\nla baja de la edición");
</script>
<%}%>

<%if(errAlta){%>
<script>
    alert("Se produjeron errores en\nel alta de la edición");
</script>
<%}%>

<%if(errEdi){%>
<script>
    alert("Se produjeron errores en\neditando la edición");
</script>
<%}%>

<%if(msgEdiCreada){%>
<script>
    alert("La edición del curso se creo correctamente");
</script>
<%}%>

<%if(msgModError){%>
<script>
    alert("Se produjo un error\nmodificando la edición");
</script>
<%}%>



<script>
	$(function() {
        $( "#lstAlumnos" ).dialog({
           autoOpen: false,
            height: 600,
            width: 1200,
            modal: true,
            buttons: {
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
function abrirListadoAluEdi(codEdi)
{
	document.getElementById("fraList").src="../ediciones/verAlumEdi.jsp?codEdi=" + codEdi;
	$("#lstAlumnos").dialog( "option" , "title" , "Listado Alumnos" );
	$("#lstAlumnos").dialog( "open" );
}

function abrirListadoAluBajEdi(codEdi)
{
	document.getElementById("fraList").src="../ediciones/verAlumBajEdi.jsp?codEdi=" + codEdi;
	$("#lstAlumnos").dialog( "option" , "title" , "Listado Alumnos Baja" );
	$("#lstAlumnos").dialog( "open" );
}

function cerrarListadoAluEdi()
{
	$("#lstAlumnos").dialog( "close" );
}
</script>

<body class="fondoFormularios">
<div id="lstAlumnos" title="Listado Alumnos">
   <iframe name="fraList"     id="fraList"     frameborder="0" src="" style="width:100%" height="500" scrolling="no"> </iframe>
</div>
<form action="" method="post" name="frmlistEdiCur" target="_self" class="fondoFormularios" id="frmlistEdiCur">
  <table style="width:90%" border="0">
    <tr>
      <td style="width:21%" rowspan="2" class="tdDef"><strong>Descripci&oacute;n</strong></td>
    <td style="width:8%" rowspan="2" class="tdDef"><strong>Fecha Inicio</strong></td>
    <td style="width:7%" rowspan="2" class="tdDef"><strong>Fecha Fin</strong></td>
    <td style="width:5%" rowspan="2" class="tdDef"><strong>Plazas</strong></td>
    <td colspan="2" class="cellBtnSub"><strong class="tdDef">Precio</strong></td>
    <td style="width:6%" rowspan="2" class="tdDef"><strong>M&oacute;dulos</strong></td>
    <td style="width:30%" rowspan="2" class="tdDef"><strong>N&uacute;m Alumnos</strong></td>
    </tr>
    <tr>
      <td class="cellBtnSub"><strong>Matricula</strong></td>
      <td class="cellBtnSub"><strong>Recibo</strong></td>
    </tr>
    <%for(int ind = indInf; ind < indSup; ind++){
        ediVO = (EdicionesVO) vecEdi.elementAt(ind);
		
		if(ind%2!=0){ colorBlanco = true;} else {colorBlanco = false;}		
		cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";
			
    %>
        <tr class="<%=cssFI%>">
            <td><%=ediVO.getDescripcion()%></td>            
            <td><%=new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecIn())%></td>
            <td><%=new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecFi())%></td>
            <td><%=ediVO.getNumPla()%></td>
            <td style="width:8%">
                                <%if(ediVO.getPrecioM()>0){ %><%=new DecimalFormat("0.00").format(ediVO.getPrecioM())%>
              <%}else{%>Sin Coste<%}%></td>
            <td onclick="location.href='../ediciones/ediEdicion.jsp?codEdi=<%=ediVO.getIdEdi()%>&urlDes=1&valInfEdi=<%=indInf%>'" style="cursor:pointer" style="width:10%">
                                <%if(ediVO.getPrecioR()>0){ %><%=new DecimalFormat("0.00").format(ediVO.getPrecioR())%><%}else{%>Sin Coste<%}%></td>
            <td>&nbsp;</td>
            <td><input name="btnAlu<%=ind%>" type="button" value="<%=AluEdiGestion.devNumAluEdi(ediVO.getIdEdi())%>" onClick="abrirListadoAluEdi('<%=ediVO.getIdEdi()%>');" />
              <input type="button" name="btnAluBaj<%=ind%>" id="btnAluBaj<%=ind%>" value="Alumnos Baja" onClick="abrirListadoAluBajEdi('<%=ediVO.getIdEdi()%>');" />
            </td>
        </tr>
      <%}%>
</table>
<table style="width:90%" border="0">
<tr>
        <td style="width:55%" height="40" class="cellBtnSub" scope="col"><%if(indInf > 0){%><a href="./listEdiCursoProf.jsp?codCur=<%=curVO.getIdCur()%>&valInfEdi=<%=indInf-10%>"><img src="../imagenes/btnprev.png" alt="&lt;---" style="width:35" height="35" border="0" /></a><%}%></td>
      <td class="cellBtnSub" scope="col"><%if(indSup < vecEdi.size()){%><a href="./listEdiCursoProf.jsp?codCur=<%=curVO.getIdCur()%>&valInfEdi=<%=indInf+10%>"><img src="../imagenes/btnsig.png" alt="---&gt;" style="width:35" height="35" border="0" /></a><%}%></td>
    </tr>
</table>


  	
</form>
</body>
</html>