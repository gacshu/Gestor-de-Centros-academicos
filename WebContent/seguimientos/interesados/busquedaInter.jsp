<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Busqueda Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios" onload="cargaCombos();">
<script src="../js/validaGlobal.js"></script>

<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	formValido = true;
	
	//Valida que los campos tengan el formato correcto
	if(! isCP(frmBusquedaInter.txtCodPost.value))
	{
		formValido = false;
		frmBusquedaInter.txtCodPost.focus();
		alert("Introduzca un Código Postal correcto");
		
	}
	
	if(! isEmail(frmBusquedaInter.txtEmail.value))
	{
		formValido = false;
		frmBusquedaInter.txtEmail.focus();
		alert("Introduzca un Email correcto");
	}
	
	//Realiza el submit si se valida el formulario
	if(formValido)
	{
		frmBusquedaInter.submit();
	}
	
	
} 


function cargaComboEmpresas(idEmp)
{   
	$.ajax({
	  url: "../CargaComboEmpresaServlet",
	  success: function(data) {
		$("#lstEmpresas").html(data); 
		if(idEmp != "undefined")
		{
			$("#selEmpresa").val(idEmp);
		}
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando empresas");
	  }
	});
}

function cargaComboNF(idNF)
{   
	$.ajax({
	  url: "../CargaComboNivelFormativoServlet",
	  success: function(data) {
		$("#lstNivFor").html(data);
		if(idNF != "undefined")
		{
			$("#lstNivFor").val(idNF);
		}
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los niveles formativos");
	  }
	});
}

function cargaCombos()
{
	$(document).ready(function() {
    // put all your jQuery goodness in here.
		cargaComboEmpresas();
		cargaComboNF();
	});
}
</script>


<form action="../BusquedaInteresadosServlet" method="post" name="frmBusquedaInter" target="_self">
<table width="100%" border="0">
  <tr>
    <th colspan="4" class="thDef" scope="col">Búsqueda Interesados</th>
  </tr>
  <tr>
    <td width="18%">&nbsp;</td>
    <td width="45%">&nbsp;</td>
    <td width="7%">&nbsp;</td>
    <td width="30%">&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><span class="tdDef">Número Documento</span></td>
    <td>
      <span class="tdDef">
      <input name="txtNumDoc" type="text" id="txtNumDoc" tabindex="1" size="30" maxlength="20" />
      </span></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><span class="tdDef">Apellidos</span></td>
    <td><span class="tdDef">
      <input name="txtApellidos" type="text" id="txtApellidos" tabindex="2" size="60" maxlength="50" />
    </span></td>
    <td><span class="tdDef">Nombre</span></td>
    <td><span class="tdDef">
      <input name="txtNombreAlu" type="text" id="txtNombreAlu" tabindex="3" size="50" maxlength="50" />
    </span></td>
  </tr>
  <tr>
    <td height="30"><span class="tdDef">Mail</span></td>
    <td><span id="txtValMail">
      <input name="txtEmail" type="text" id="txtEmail" tabindex="4" size="90" maxlength="150" />
      <span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td>&nbsp;</td>
    <td><span class="tdDef">
      <input name="chkDesempleado" type="checkbox" id="chkDesempleado" tabindex="5" value="true" />
      Desempleado</span></td>
  </tr>
  <tr>
    <td height="30"><span class="tdDef">Código Postal</span></td>
    <td><span id="txtValCodPos">
    <input name="txtCodPost" type="text" id="txtCodPost" tabindex="6" size="10" maxlength="5" />
<span class="textfieldInvalidFormatMsg">* </span></span></td>
    <td>&nbsp;</td>
    <td><span class="tdDef">
      <input name="chkNoInterCurso" type="checkbox" id="chkNoInterCurso" value="true" /> 
      No Interesado en Curso</span></td>
  </tr>
  <tr>
    <td height="30"><span class="tdDef">Nivel Formativo</span></td>
    <td><span class="tdDef">
      <select name="lstNivFor" size="1" id="lstNivFor" tabindex="7"></select>
    </span></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td height="30"><span class="tdDef">Empresa</span></td>
    <td><span class="tdDef">
      <select name="lstEmpresas" size="1" id="lstEmpresas" tabindex="8"></select>
    </span></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" class="cellBtnSub"><input type="button" name="btnBuscar" id="btnBuscar" value="Buscar"  onclick="validaForm();" tabindex="9"/></td>
    </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValMail", "email", {isRequired:false, validateOn:["blur"]});
var sprytextfield2 = new Spry.Widget.ValidationTextField("txtValCodPos", "zip_code", {format:"zip_custom", pattern:"00000", isRequired:false});
</script>
</body>
</html>