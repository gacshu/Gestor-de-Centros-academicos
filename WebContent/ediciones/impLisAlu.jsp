<%@page import="es.jahernandez.datos.AreasVO"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.AreasVO"%>
<%@ page import = "es.jahernandez.accesodatos.AreasDAO"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir tipo curso</title>
<%@ include file="../controlAcceso/includeComAutProf.jsp"%>
<%
//Control de errores de alta
String      codEdi = "";

//Se comprueba si vienen datos de el servlet de alta de interesados para recargar datos
//y los códigos de error

if(request.getParameter("codEdi") != null)
{
    codEdi = request.getParameter("codEdi");
}

%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>
<script language="JavaScript" type="text/JavaScript">
function validaForm()
{		   
	if(sprytextfield1.validate())
	{
		frmAltaArea.action = "../AltaAreaServlet";
		frmAltaArea.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados")
	}
	
}


function imprimeCalif()
{
	window.open("../ImpCalificacionesEdiServlet?codEdi=<%=codEdi%>" 
							      + "&eva=" + $("#lstEva").val()  ,
	                          "_calif","");	
}

function imprimeFaltas()
{
	window.open("../ImpFaltasEdiServlet?codEdi=<%=codEdi%>",
	                                "_faltas","");	
}

function generaFIP()
{
	
	window.open("../ImpListFIPServlet?codEdi=<%=codEdi%>" 
								  + "&txtNumCur="      + $("#txtNumCurso").val() 
	 							  + "&txtDiaIni="      + $("#txtNumDiaIni").val() 
								  +	"&txtDiaFin="      + $("#txtNumDiaFin").val() 
								  +	"&txtNumDiasLect=" + $("#txtNumDiaLec").val() 	
								  +	"&txtMes="         + $("#lstMes").val() 
								  +	"&txtNomProf="     + $("#txtNomProf").val() 
								  +	"&txtCent="        + $("#lstCentro").val() ,
	                          "_FIP","");	
	
}

function generaFPO()
{
	
	window.open("../ImpListFPOServlet?codEdi=<%=codEdi%>" 
								  + "&txtNumCur="      + $("#txtNumCurso").val() 
	 							  + "&txtDiaIni="      + $("#txtNumDiaIni").val() 
								  +	"&txtDiaFin="      + $("#txtNumDiaFin").val() 
								  +	"&txtNumDiasLect=" + $("#txtNumDiaLec").val() 	
								  +	"&txtMes="         + $("#lstMes").val() 
								  +	"&txtNomProf="     + $("#txtNomProf").val() 
								  +	"&txtLugImp="      + $("#txtLugImp").val() 
								  +	"&txtLugCen="      + $("#txtLugCen").val(),
	                          "_FPO","");	
	
}

function generaControlMat()
{
	
	window.open("../ImpContMatServlet?codEdi=<%=codEdi%>" 
								  + "&txtMatEnt="      + $("#txtMatEnt").val() 
	 							  + "&txtCentro="      + $("#lstCentro").val(),
	                          "_ControlMat","");	
	
}


function generaListProp()
{
	
	window.open("../ImpListPropServlet?codEdi=<%=codEdi%>" 
								  + "&txtMes="      + $("#lstMes").val() 
	 							  + "&txtLugImp="   + $("#txtLugImp").val(),
	                          "_ListProp","");	
	
}

function generaListSeg()
{
	
	window.open("../ImpSegAlumServlet?codEdi=<%=codEdi%>" 
								  + "&txtMes="    + $("#lstMes").val() 
	 							  + "&txtCent="   + $("#lstCentro").val(),
	                          "_ListSeg","");	
	
}

function generaCert()
{
	
	window.open("../ImpCertificadosServlet?codEdi=<%=codEdi%>" 								  
	 							  + "&txtNomProf=" + $("#txtNomProf").val(), 
	                          "_ListSeg","");	
	
}

function cargarComboCentros(idCentro)
{
	$.ajax({
	  url: "../CargaComboCentrosServlet?valSel=" + idCentro,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstCentro").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los centros");
	  }
	});
		
}


</script>

</head>

<body class="fondoFormularios" onload="cargarComboCentros('-99');">
<form id="frmImpAlu" name="frmImpAlu" method="post" action="">
<table style="width:90%" border="0">
  <tr>
    <td colspan="2" class="thDef">Introduzca los datos para generar el Listado</td>
  </tr>
  <tr class="tdDef">
    <td height="24" class="tdDef">N&uacute;mero de Curso</td>
    <td style="width:77%" class="tdDef"><label for="txtNumCurso"></label>
      <input name="txtNumCurso" type="text" id="txtNumCurso" size="30" maxlength="25" />
      <label for="textfield"></label>
      <input name="textfield" type="text" id="textfield" size="3" readonly="readonly" style="background-color:gray" />
      <input name="textfield2" type="text" id="textfield2" size="3" readonly="readonly" style="background-color:red" /></td>
  </tr>
  <tr class="tdDef">
    <td style="width:23%" class="tdDef">D&iacute;a Inicio Semana</td>
    <td class="tdDef"><input name="txtNumDiaIni" type="text" id="txtNumDiaIni" size="10" maxlength="5" />
      <input name="textfield4" type="text" id="textfield4" size="3" readonly="readonly" style="background-color:gray" />
      <input name="textfield3" type="text" id="textfield3" size="3" readonly="readonly" style="background-color:red" /></td>
    </tr>
  <tr class="tdDef">
    <td class="tdDef">D&iacute;a Fin Semana</td>
    <td class="tdDef"><input name="txtNumDiaFin" type="text" id="txtNumDiaFin" size="10" maxlength="5" />
      <input name="textfield5" type="text" id="textfield5" size="3" readonly="readonly" style="background-color:gray" />
      <input name="textfield7" type="text" id="textfield7" size="3" readonly="readonly" style="background-color:red" /></td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">N&uacute;mero de D&iacute;as Lectivos</td>
    <td class="tdDef"><input name="txtNumDiaLec" type="text" id="txtNumDiaLec" size="10" maxlength="5" />
      <input name="textfield6" type="text" id="textfield6" size="3" readonly="readonly" style="background-color:gray" />
      <input name="textfield8" type="text" id="textfield8" size="3" readonly="readonly" style="background-color:red" /></td>
  </tr>
  <tr class="tdDef">
    <td valign="top" class="tdDef">Material Entregado</td>
    <td class="tdDef"><label for="txtMatEnt"></label>
      <textarea name="txtMatEnt" id="txtMatEnt" cols="70" rows="5"></textarea>
      <input name="textfield9" type="text" id="textfield9" size="3" readonly="readonly" style="background-color:lightblue" /></td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">Mes</td>
    <td class="tdDef"><label for="lstMes"></label>
      <select name="lstMes" id="lstMes">
        <option value=" " selected="selected">Seleccione...</option>
        <option value="ENERO">ENERO</option>
        <option value="FEBRERO">FEBRERO</option>
        <option value="MARZO">MARZO</option>
        <option value="ABRIL">ABRIL</option>
        <option value="MAYO">MAYO</option>
        <option value="JUNIO">JUNIO</option>
        <option value="JULIO">JULIO</option>
        <option value="AGOSTO">AGOSTO</option>
        <option value="SEPTIEMBRE">SEPTIEMBRE</option>
        <option value="OCTUBRE">OCTUBRE</option>
        <option value="NOVIEMBRE">NOVIEMBRE</option>
        <option value="DICIEMBRE">DICIEMBRE</option>
      </select>
      <input name="textfield10" type="text" id="textfield10" size="3" readonly="readonly" style="background-color:gray" />
      <input name="textfield11" type="text" id="textfield11" size="3" readonly="readonly" style="background-color:red" />
      <input name="textfield12" type="text" id="textfield12" size="3" readonly="readonly" style="background-color:salmon" /></td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">Nombre Profesor</td>
    <td class="tdDef"><input name="txtNomProf" type="text" id="txtNomProf" size="75" maxlength="75" />
      <input name="textfield13" type="text" id="textfield13" size="3" readonly="readonly" style="background-color:gray" />
      <input name="textfield19" type="text" id="textfield19" size="3" readonly="readonly" style="background-color:#39F" /></td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">Lugar de Impartici&oacute;n</td>
    <td class="tdDef"><input name="txtLugImp" type="text" id="txtLugImp" size="75" maxlength="75" />
      <input name="textfield15" type="text" id="textfield15" size="3" readonly="readonly" style="background-color:red" /></td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">Lugar y nombre del centro</td>
    <td class="tdDef"><input name="txtLugCen" type="text" id="txtLugCen" size="75" maxlength="120" />
      <input name="textfield16" type="text" id="textfield16" size="3" readonly="readonly" style="background-color:red" /></td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">Centro</td>
    <td class="tdDef"><select name="lstCentro" id="lstCentro">
</select>
      <input name="textfield14" type="text" id="textfield14" size="3" readonly="readonly" style="background-color:gray" />
      <input name="textfield17" type="text" id="textfield17" size="3" readonly="readonly" style="background-color:lightblue" />
      <input name="textfield18" type="text" id="textfield18" size="3" readonly="readonly" style="background-color:blue" /></td>
  </tr>
  <tr class="tdDef">
    <td class="tdDef">Evaluaci&oacute;n</td>
    <td class="tdDef">
    	<select name="lstEva" id="lstEva">
        <option value="-1" selected="selected">Seleccione...</option>
        <option value="1">1ª Evaluación</option>
        <option value="2">2ª Evaluación</option>
        <option value="3">3ª Evaluación</option>
        <option value="4">4ª Evaluación</option>
        <option value="5">5ª Evaluación</option>
        <option value="6">6ª Evaluación</option>
        <option value="7">7ª Evaluación</option>
        <option value="8">8ª Evaluación</option>
        <option value="9">9ª Evaluación</option>
        <option value="10">10ª Evaluación</option>
      </select>
      <input name="textfield18" type="text" id="textfield18" size="3" readonly="readonly" style="background-color:#3C6" /></td>
    </td>
  </tr>
  <tr class="tdDef">
    <td colspan="2" class="cellBtnSub">
    	<input type="button" name="btnCalif" id="btnCalif" value="Calificaciones" style="background-color:#3C6" onclick="imprimeCalif();" />
    	<input type="button" name="btnCalif2" id="btnCalif2" value="Faltas" style="background-color:#CF3" onclick="imprimeFaltas();" />
<input type="button" name="btnLisFIP" id="btnLisFIP" value="Listado FIP" onclick="generaFIP();"/>
       	<input type="button" name="btnLisFPO" id="btnLisFPO" value="Listado FPO" style="background-color:red; color:white" onclick="generaFPO();" /> 
      	<input type="button" name="btnCtrlMat" id="btnCtrlMat" value="Control Materiales" style="background-color:lightblue" onclick="generaControlMat();"/>
      	<input type="button" name="btnLisFPO2" id="btnLisFPO2" value="Listado Propio" style="background-color:salmon" onclick="generaListProp();"/>
      	<input type="button" name="btnLisF25" id="btnLisF25" value="Seguimientos" style="background-color:blue;color:white" onclick="generaListSeg();"/>
      	<input type="button" name="btnCertificados" id="btnCertificados" value="Certificados" style="background-color:#39F;color:#FFF" onclick="generaCert();"/></td>
    </tr>
  <tr>
    <td colspan="2" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>