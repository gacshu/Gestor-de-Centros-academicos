<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="es.jahernandez.datos.AlumnosVO"%>
<%@page import="es.jahernandez.datos.EdicionesVO"%>
<%@page import="es.jahernandez.datos.CursosVO"%>
<%@page import="es.jahernandez.accesodatos.AlumnosDAO"%>
<%@page import="es.jahernandez.accesodatos.EdicionesDAO"%>
<%@page import="es.jahernandez.accesodatos.CursosDAO"%>

<html xmlns="http://www.w3.org/1999/xhtml">
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
   
    EdicionesVO ediVO = new EdicionesVO();
    CursosVO    curVO = new CursosVO();
    AlumnosVO   aluVO = new AlumnosVO();
    
    if(request.getParameter("codAlu") != null)
    {
       aluVO = AlumnosGestion.devolverDatosAlumno(request.getParameter("codAlu"));
    }
    
    if(request.getParameter("codCur") != null)
    {
       curVO = CursosGestion.devolverDatosCurso(request.getParameter("codCur"));
    }

%>

<!--Funciones jquery ui-->
<script>
$(function() {
        $( "#datEdicion" ).dialog({
           autoOpen: false,
            height: 275,
            width: 225,
            modal: true,
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
 
    });
</script>

<script>
function validarForm()
{
	if(spryselect1.validate())
	{
		parent.desactivaBtnMat();
		frmMatAlu.submit();
	}
}

function cargarComboEdiciones(idCodEdi)
{   
	$.ajax({
	  url: "../CargaComboEdicionesDispServlet?codCurso=<%=curVO.getIdCur()%>&valSel=" + idCodEdi,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstEdiciones").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las ediciones");
	  }
	});
}
			
function mostrarDatosEdi()
{
	
	var codEdi = $("#lstEdiciones").val();
	
	$.ajax({
	  url: "../MuestraDatosEdicionServlet?codEdi=" + codEdi,
	  success: function(data) {
		eval(data);
		var datEdicion = new Edicion();
				
		$($('#tabDatEdi').find('tbody > tr')[0]).children('td')[1].innerHTML = datEdicion.fechaIn;
		$($('#tabDatEdi').find('tbody > tr')[1]).children('td')[1].innerHTML = datEdicion.fechaFi;
		$($('#tabDatEdi').find('tbody > tr')[2]).children('td')[1].innerHTML = datEdicion.horasPr;
		$($('#tabDatEdi').find('tbody > tr')[3]).children('td')[1].innerHTML = datEdicion.horasDi;
		$($('#tabDatEdi').find('tbody > tr')[4]).children('td')[1].innerHTML = datEdicion.horasTe;
		$($('#tabDatEdi').find('tbody > tr')[5]).children('td')[1].innerHTML = datEdicion.horIni;
		$($('#tabDatEdi').find('tbody > tr')[6]).children('td')[1].innerHTML = datEdicion.horFin;
		$($('#tabDatEdi').find('tbody > tr')[7]).children('td')[1].innerHTML = datEdicion.precioMat;
		$($('#tabDatEdi').find('tbody > tr')[8]).children('td')[1].innerHTML = datEdicion.precioRec;  
		$("#datEdicion").dialog( "open" );
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los  datos del curso");
	  }
	});
}

</script>


<div id="datEdicion" name="datEdicion" title="Edición">
  <table name="tabDatEdi" id="tabDatEdi"  width="200" border="1" class="tdDef" border="0">
  <tr>
    <td>Fecha Inicio</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Fecha Fin</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Horas Pres</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Horas Dist</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Horas Telef</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Hora Inicio</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Hora Fin</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Precio Matric</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Precio Rec</td>
    <td>&nbsp;</td>
  </tr>
</table>

</div>


<body class="fondoFormularios" onload="cargarComboEdiciones();">
<form action="../MatricularAlumnoServlet" method="post" name="frmMatAlu" target="_self" id="frmMatAlu">
  <table width="477" border="0" class="tdDef">
    <tr>
      <td colspan="2"><%=aluVO.getNombre() + " " + aluVO.getAp1Alu() + " --- " + curVO.getNomCur()%></td>
    </tr>
    <tr>
      <td width="30%">Elegir Edici&oacute;n</td>
      <td width="70%"><span id="valEdiciones">
        <select name="lstEdiciones" id="lstEdiciones" tabindex="1">
        </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*.</span></span>
        <input type="button" name="btnDatEdi" id="btnDatEdi" value="--&gt;" onclick="mostrarDatosEdi();"/></td>
    </tr>
    <tr>
      <td>N&uacute;mero de Cuenta</td>
      <td><input name="txtNumCuenta" type="text" id="txtNumCuenta" tabindex="2" size="30" maxlength="20" /></td>
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
var spryselect1 = new Spry.Widget.ValidationSelect("valEdiciones", {invalidValue:"-1"});
</script>
</body>
</html>