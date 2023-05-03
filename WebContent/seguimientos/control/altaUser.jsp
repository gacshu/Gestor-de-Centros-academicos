<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir aula</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
    String ind = "";
    
    if(request.getParameter("ind") != null)
    {
        ind = request.getParameter("ind").trim();
    }
%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/validaGlobal.js"></script>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>



<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationConfirm.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationPassword.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationConfirm.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationPassword.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarCampos  = true;		
		
	validarCampos =  sprytextfield1.validate() & sprytextfield2.validate() & 
	                 sprypassword1.validate()  & spryconfirm2.validate()   &
					 spryselect1.validate()    & spryselect2.validate();
				   
	
	if($("#lstNivAcc").val() == 2 )
	{
		validarCampos = validarCampos & spryselect3.validate();
	}
	
	if(validarCampos)
	{
		frmAltaUser.action = "../AltaUserServlet?ind=<%=ind%>";
		frmAltaUser.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados")
	}
}
  
function habilitaProf(nivAcc)
{
	if(nivAcc==2)
	{
		cargarCombosProfesores();
		//document.frmAltaUser.labProf.style.color      ="#FFF";
		document.frmAltaUser.lstProf.style.visibility = "visible";
	}
	else
	{
		//document.frmAltaUser.labProf.style.color      = "#03F";
		document.frmAltaUser.lstProf.style.visibility = "hidden";
	}
}

function mostrarNombreProf()
{
	var nombre;
	var apellidos;
	var nombreCompleto = $("#lstProf option:selected").html().split(',');
	
	if($("#lstProf").val() != -1)
	{
		apellidos = Trim(nombreCompleto[0]);
		nombre = Trim(nombreCompleto[1]);
		
		$("#txtNombre").val(nombre + " " + apellidos);
	}
	
}
  
function cargarComboCentros(idCentro)
{
	$.ajax({
	  url: "../CargaComboCentrosServlet?valSel=" + idCentro,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstCentros").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los centros");
	  }
	});
}

function cargarCombosProfesores(idProf)
{   
	$.ajax({
	  url: "../CargaComboProfesoresServlet?valSel=" + idProf,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstProf").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los profesores");
	  }
	});
}

</script>


</head>

<body class="fondoFormularios" onload="cargarComboCentros('-99');">
<form id="frmAltaUser" name="frmAltaUser" method="post" action="">
<table width="90%" border="0">
  <tr>
    <td colspan="4" class="thDef">Añadir Usuario</td>
  </tr>
  <tr class="tdDef">
    <td width="13%" class="tdDef">Usuario</td>
    <td colspan="3" class="tdDef"><label for="txtUser"></label>
      <span id="valUser">
        <input name="txtUser" type="text" id="txtUser" size="15" maxlength="8" />
      <span class="textfieldRequiredMsg">*.</span></span></td>
    </tr>
  <tr class="tdDef">
    <td>Password</td>
    <td width="17%">
      <label for="txtPass"></label>
      <span id="valPass">
      <label for="txtPass"></label>
      <input type="password" name="txtPass" id="txtPass" />
      <span class="passwordRequiredMsg">*</span>
      <span class="passwordMinCharsMsg">*</span>
      <span class="passwordMaxCharsMsg">*</span></span></td>
    <td width="17%">Confirmar Password</td>
    <td width="53%"><span id="valConfPass">
      <label for="txtConfPass"></label>
      <input name="txtConfPass" type="password" id="txtConfPass" size="15" maxlength="8" />
      <span class="confirmRequiredMsg">*</span><span class="confirmInvalidMsg">*</span></span></td>
  </tr>
  <tr class="tdDef">
    <td>Nivel Acceso</td>
    <td><label for="lstNivAcc"></label>
      <span id="valNivAcc">
      <select name="lstNivAcc" id="lstNivAcc" onchange="habilitaProf(this.value)">
        <option value="-1" selected="selected">Seleccione...</option>
        <option value="2">Profesor</option>
        <option value="3">Usuario</option>
        <option value="5">Administrador</option>
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td colspan="2"><span id="valProf">
      <select name="lstProf" id="lstProf" style="visibility:hidden" onchange="mostrarNombreProf()">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    </tr>
  <tr class="tdDef">
    <td>Nombre</td>
    <td colspan="3"><span id="valNombre">
      <input name="txtNombre" type="text" id="txtNombre" size="50" maxlength="47" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    </tr>
  <tr class="tdDef">
    <td>Centro</td>
    <td colspan="3"><span id="lstValCentros">
      <label for="lstCentros"></label>
      <select name="lstCentros" id="lstCentros"></select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td class="tdDef">Activo</td>
    <td colspan="3" class="tdDef"><input name="chkActivo" type="checkbox" class="tdDef" id="chkActivo" value="true" checked="checked" />
      <label for="chkActivo"></label></td>
    </tr>
  <tr>
    <td colspan="4" class="cellBtnSub"><input name="btnAlta" type="button" class="cellBtnSub" id="btnAlta" value="Guardar" onclick="validaForm();" /></td>
  </tr>
  <tr>
    <td colspan="4" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var spryselect1    = new Spry.Widget.ValidationSelect("lstValCentros", {invalidValue:"-1"});
var sprytextfield1 = new Spry.Widget.ValidationTextField("valUser");

var spryconfirm2 = new Spry.Widget.ValidationConfirm("valConfPass", "txtPass");
var spryselect2 = new Spry.Widget.ValidationSelect("valNivAcc", {invalidValue:"-1"});
var sprytextfield2 = new Spry.Widget.ValidationTextField("valNombre");
var sprypassword1 = new Spry.Widget.ValidationPassword("valPass", {minChars:8, maxChars:8});
var spryselect3 = new Spry.Widget.ValidationSelect("valProf", {invalidValue:"-1"});
</script>
</body>
</html>