<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir Curso</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Control de caché
response.setDateHeader ("Expires", -1); 
response.setHeader("Pragma","no-cache"); 
if(request.getProtocol().equals("HTTP/1.1")) 
 response.setHeader("Cache-Control","no-cache"); 

//Control de errores de alta
int       errCodAlta = 0;
CursosVO  cursoVO    = new CursosVO();

//Se comprueba si vienen datos de el servlet de alta de cursos para recargar datos
//y los códigos de error
if(request.getParameter("errorCode") != null)
{
    errCodAlta = new Integer(request.getParameter("errorCode")).intValue();
}


if(session.getAttribute("nuevoCursoErr") != null)
{
    cursoVO = (CursosVO) session.getAttribute("nuevoCursoErr");
    session.removeAttribute("nuevoCursoErr");
}

%>


<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js"    type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>

</head>

<%if(errCodAlta == -1){%>
<script>
    alert("Se produjo un error en el alta del curso");
</script>
<%}%>
<%if(errCodAlta == -2){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>

<script>
function validaForm()
{
	var validarCampos  = true;		
					
	validarCampos = sprytextfield1.validate() & spryselect1.validate()   &  
				    spryselect2.validate()    & sprytextarea1.validate() ;
				   
	if(validarCampos)
	{
		document.frmAltaCurso.action = "../AltaCursosServlet";	
		document.frmAltaCurso.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
	
}

function cargarComboTipoCurso(idTipCur)
{
	$.ajax({
	  url: "../CargaTipoCursoServlet?valSel=" + idTipCur,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstTipoCurso").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de cursos");
	  }
	});
		
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

</script>


<body class="fondoFormularios" onload="cargarComboTipoCurso();cargarComboCentros('-99');">
<form  method="post" name="frmAltaCurso" id="frmAltaCurso">
<table width="90%" border="0">
  <tr>
    <td colspan="2" class="thDef">Alta Curso</td>
  </tr>
  <tr>
    <td width="21%" class="tdDef">Nombre</td>
    <td width="79%" class="tdDef"><span id="txtValNombre">
      <input name="txtNombre" type="text" id="txtNombre" tabindex="1" value="<%=cursoVO.getNomCur()%>" size="75" maxlength="150" />
      <span class="textfieldRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td class="tdDef">Tipo</td>
    <td class="tdDef"><span id="valTipCurso">
      <select name="lstTipoCurso" id="lstTipoCurso" tabindex="2"></select>
      <span class="selectInvalidMsg">*</span></span></td>
  </tr>
  <tr>
    <td class="tdDef">Centro</td>
    <td class="tdDef"><span id="valCentro">
      <select name="lstCentros" id="lstCentros" tabindex="2"></select>
      <span class="selectInvalidMsg">*</span></span></td>
  </tr>
  <tr>
    <td valign="top" class="tdDef">Contenido</td>
    <td class="tdDef"><span id="valContenido">
      <textarea name="txtContenido" id="txtContenido" cols="75" rows="5" tabindex="4"></textarea>
      <span class="textareaRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td colspan="2" class="cellBtnSub"><input name="btnAceptar" type="button" class="cellBtnSub" id="btnAceptar" tabindex="5" value="Guardar" onclick="validaForm();" /></td>
  </tr>
  
</table>
<p id="frmAltaCurso">&nbsp;</p>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre");
var spryselect1    = new Spry.Widget.ValidationSelect   ("valTipCurso", {isRequired:false, invalidValue:"-1"});
var spryselect2    = new Spry.Widget.ValidationSelect   ("valCentro", {invalidValue:"-1", isRequired:false});
var sprytextarea1 = new Spry.Widget.ValidationTextarea  ("valContenido");
</script>
</body>
</html>