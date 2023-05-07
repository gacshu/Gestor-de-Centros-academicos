<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="es.jahernandez.datos.AlumnosVO"%>
<%@page import="es.jahernandez.datos.EdicionesVO"%>
<%@page import="es.jahernandez.datos.CursosVO"%>
<%@page import="es.jahernandez.accesodatos.AlumnosDAO"%>
<%@page import="es.jahernandez.accesodatos.EdicionesDAO"%>
<%@page import="es.jahernandez.accesodatos.CursosDAO"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Matricular Alumno</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
</head>

<%
   //Control de caché
        response.setDateHeader ("Expires", -1); 
        response.setHeader("Pragma","no-cache"); 
        if(request.getProtocol().equals("HTTP/1.1")) 
            response.setHeader("Cache-Control","no-cache"); 
   
   
   AlumnosVO aluVO  = null;     
   String    codAlu = ""; 
    
   if(request.getParameter("codAlu") != null)
   {
      codAlu = request.getParameter("codAlu");
      aluVO = AlumnosGestion.devolverDatosAlumno(codAlu);
   }
%>

<script>
function validarForm()
{
	if(spryselect1.validate())
	{
		parent.desactBotRec();
		frmLiqRec.submit();
	}
	else
	{
		alert("Tiene que elegir un recibo");
	}
}

function cargarComboRecibos(idRecibo)
{
	$.ajax({
	  url: "../CargaComboRecPenAluServlet?codAlu=<%=codAlu%>&valSel=" + idRecibo,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstRecPenAlu").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los recibos");
	  }
	});
}

			
function mostrarDatosRec(codAlu,numRec)
{
	$.ajax({
	  url: "../MuestraDatosReciboServlet?numRec=" + numRec + "&codAlu=" + codAlu,
	  success: function(data) {
		eval(data);
		var datRecibo = new Recibo();
				
		$($('#tabDatRec').find('tbody > tr')[1]).children('td')[1].innerHTML = datRecibo.fechaEx;
		$($('#tabDatRec').find('tbody > tr')[2]).children('td')[1].innerHTML = datRecibo.curso;
		$($('#tabDatRec').find('tbody > tr')[3]).children('td')[1].innerHTML = datRecibo.importe;
	
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los  datos del recibo");
	  }
	});
}

</script>


<body class="fondoFormularios" onload="cargarComboRecibos();">
<form action="../LiquidarReciboServlet" method="post" name="frmLiqRec" target="_self" id="frmLiqRec">
  <table style="width:477" border="0" class="tdDef">
    <tr>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td style="width:30%">N&uacute;mero recibo</td>
      <td style="width:70%"><span id="ValRecPenAlu">
        <select name="lstRecPenAlu" id="lstRecPenAlu" tabindex="1" onchange="mostrarDatosRec($('#lstRecPenAlu').val(),'<%=aluVO.getIdAlu()%>');">
        </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*.</span></span></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    </table>
   
    <table  name="tabDatRec" id="tabDatRec" style="width:477" border="0" class="tdDef">
    <tr>
      <td style="width:30%">Alumno</td>
      <td style="width:328"><%=aluVO.getNombre() + " " + aluVO.getAp1Alu()%></td>
    </tr>
    <tr>
      <td>Fecha</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Curso</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>Importe</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
        <td>
            <input type="hidden" name="hidCodAlu" id="hidCodAlu" value="<%=aluVO.getIdAlu()%>" /></td>
      <td>&nbsp;</td>
    </tr>
    <tr class="cellBtnSub">
      <td colspan="2">&nbsp;</td>
    </tr>
  </table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("ValRecPenAlu", {invalidValue:"-1"});
</script>
</body>
</html>