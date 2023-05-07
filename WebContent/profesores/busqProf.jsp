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
<title>Búsqueda Profesor</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios" onload="cargarCombosAreas();">
<script src="../js/validaGlobal.js"></script>


<script>
function validarForm()
{
	var todoCorrecto = true;
	
	if(todoCorrecto)
	{
		frmBusqProf.submit();
	}
	else
	{
		alert("Compruebe los campos resaltados");
	}

}

function cargarCombosAreas(idArea)
{   
	$.ajax({
	  url: "../CargaComboAreaServlet?valSel=" + idArea,
	  success: function(data) {
		$(document).ready(function() 
		{
			$("#lstAreas").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las áreas");
	  }
	});
}

</script>

<form action="../BusProfServlet" method="post" name="frmBusqProf" target="_self" id="frmBusqProf">
  <table style="width:66%" border="0" class="tdDef">
  <tr class="thDef">
    <th height="33" colspan="2" scope="col">B&uacute;squeda Profesores</th>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td style="width:18%">Nombre</td>
    <td style="width:82%">
      <input name="txtNombre" type="text" id="txtNombre" size="25" maxlength="15" />
    </td>
    </tr>
  <tr>
    <td>Apellidos</td>
    <td>
      <input name="txtApellidos" type="text" id="txtApellidos" size="40" maxlength="31" />
    </td>
    </tr>
  <tr>
    <td>N&uacute;mero Doc.</td>
    <td>
      <input name="txtNumDoc" type="text" id="txtNumDoc" size="25" maxlength="50" />
    </td>
    </tr>
  <tr>
    <td>&Aacute;rea Conoc.</td>
    <td><select name="lstAreas" id="lstAreas"></select></td>
    </tr>
  <tr>
    <td colspan="2"><input name="chkActivo" type="checkbox" id="chkActivo" value="true" />
      Activo</td>
  </tr>
  <tr>
    <td colspan="2" class="cellBtnSub"><input class="cellBtnSub" type="button" name="btnAceptar" id="btnAceptar" value="Buscar" onclick="validarForm();"/></td>
  </tr>
  </table>
</form>

</body>
</html>