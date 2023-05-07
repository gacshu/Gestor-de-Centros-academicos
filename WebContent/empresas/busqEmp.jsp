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

<body class="fondoFormularios" onload="cargaComboActividades();">
<script src="../js/validaGlobal.js"></script>


<script>
function validarForm()
{
	var todoCorrecto = sprytextfield1.validate();
	
	if(todoCorrecto)
	{
		frmBusqEmp.submit();
	}
	else
	{
		alert("Compruebe los campos resaltados");
	}

}

function cargaComboActividades()
{   
	$.ajax({
	  url: "../CargaComboActividadesServlet",
	  success: function(data) {
		$("#selActEmp").html(data);
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando actividades");
	  }
	});
}

</script>

<form action="../BusEmpServlet" method="post" name="frmBusqEmp" target="_self" id="frmBusqEmp">
  <table style="width:66%" border="0" class="tdDef">
  <tr class="thDef">
    <th height="33" colspan="4" scope="col">B&uacute;squeda Empresas</th>
  </tr>
  <tr>
    <td colspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td style="width:17%">Razón social</td>
    <td style="width:42%">
      <input name="txtRazSoc" type="text" id="txtRazSoc" size="60" maxlength="50" />
    </td>
    <td style="width:14%">CNAE</td>
    <td style="width:27%"><span id="valCnae">
    <input name="txtCNAE" type="text" id="txtCNAE" size="8" maxlength="5" />
<span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
  <tr>
    <td>Nombre Comercial</td>
    <td>
      <input name="txtNomCom" type="text" id="txtNomCom" size="60" maxlength="100" />
    </td>
    <td>Importa/exporta</td>
    <td>
    	<select name="selImpExp" id="selImpExp" tabindex="127">
          <option selected="selected" value="----">----</option>
          <option value="IMPORTA">IMPORTA</option>
          <option value="EXPORTA">EXPORTA</option>
          <option value="IMPORTA Y EXPORTA">IMPORTA Y EXPORTA</option>
        </select>
    </td>
    </tr>
  <tr>
    <td>Localidad</td>
    <td>
      <input name="txtLocalidad" type="text" id="txtLocalidad" size="60" maxlength="150" />
    </td>
    <td>&nbsp;</td>
    <td><input name="chkCliente" type="checkbox" id="chkCliente" value="true" />
Cliente</td>
    </tr>
  <tr>
    <td>Actividad</td>
    <td colspan="3"><select name="selActEmp" id="selActEmp"></select></td>
    </tr>
  <tr>
    <td colspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" class="cellBtnSub"><input class="cellBtnSub" type="button" name="btnAceptar" id="btnAceptar" value="Buscar" onclick="validarForm();"/></td>
  </tr>
  </table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("valCnae", "integer", {isRequired:false});
</script>
</body>
</html>