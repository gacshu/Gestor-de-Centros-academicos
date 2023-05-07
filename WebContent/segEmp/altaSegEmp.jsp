<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<%@ page import = "java.text.SimpleDateFormat"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir Seguimiento</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
ConUsuVO usuario    = new ConUsuVO();
Date     fechaHoy   = new Date(System.currentTimeMillis());
String   codEmp     = "";
int      errCodAlta = 0;

if( session.getAttribute("usuario") != null)
{
    usuario = (ConUsuVO) session.getAttribute("usuario");
}

if( request.getParameter("codEmp") != null)
{
    codEmp = request.getParameter("codEmp");
}


if(request.getParameter("errorCode") != null)
{
    errCodAlta = new Integer(request.getParameter("errorCode")).intValue();
}


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>

</head>

<body class="fondoFormularios" onload="cargarComboTipoCurso();">
<script src="../js/validaGlobal.js"></script>

<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarCampos = true; 
	
	validarCampos =  sprytextarea1.validate() ;
	
	if(validarCampos)
	{
		frmAnadirSeguimiento.submit();
	}
	else
	{
		alert('Valide al valor de los campos resaltados');
	}
} 

function cargarComboTipoCurso(idTipCur)
{   
	$.ajax({
	  url: "../CargaTipoCursoServlet?valSel=" + idTipCur,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstTipCurso").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de cursos");
	  }
	});
}

function cargarComboCursos(idCurso)
{   
	
	var idTipCurso = $("#lstTipCurso").val();
	
	$.ajax({
	  url: "../CargaComboCursosServlet?codTipo=" + idTipCurso + "&muestraSelec=s&valSel=" + idCurso,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstCursos").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los cursos");
	  }
	});
}
</script>

<%if(errCodAlta == -1){%>
<script>
    alert("Se produjeron errores en\nel alta del seguimiento");
</script>
<%}%>
<form action="../GuardarSegEmpServlet" method="post" name="frmAnadirSeguimiento" target="_self" id="frmAnadirSeguimiento">
<table style="width:98%" border="0">
  <tr>
    <th colspan="4" class="thDef" scope="col">Añadir Seguimiento
        <input type="hidden" name="hidCodEmp" id="hidCodEmp" value="<%=codEmp%>" /></th>
  </tr>
  
  <tr>
    <td style="width:11%" height="30"><span class="tdDef">Usuario</span></td>
    <td style="width:89%"><span id="txtValUsuario">
            <input name="txtUsuario" type="text" id="txtUsuario" value="<%=usuario.getUsuario()%>" size="25" maxlength="25" readonly="readonly" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td height="30"><span class="tdDef">Fecha</span></td>
    <td><span id="txtValFecha">
            <input name="txtFecha" type="text" id="txtFecha" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(fechaHoy)%>" size="15" maxlength="10" readonly="readonly" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
  <tr>
    <td height="30"><span class="tdDef">Incidencias</span></td>
    <td><span id="txtValIncidencias">
      <textarea name="txtIncidencias" id="txtIncidencias" cols="75" rows="3"></textarea>
<span class="textareaRequiredMsg">*</span></span>
      <input type="hidden" name="hidCodEmp" id="hidCodEmp" /></td>
    </tr>
  <tr>
    <td colspan="4" class="cellBtnSub"><input name="btnBuscar" type="button" class="cellBtnSub" id="btnBuscar" tabindex="9"  onclick="validaForm();" value="Añadir"/>
      <input type="button" name="btnCancelar" id="btnCancelar"  class="cellBtnSub" value="Volver" onclick="window.open('./verSegEmp.jsp' , '_self' , '')"  /></td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValUsuario", "none");
var sprytextfield2 = new Spry.Widget.ValidationTextField("txtValFecha", "date", {format:"dd/mm/yyyy", hint:"dd/mm/yyyy"});
var sprytextarea1 = new Spry.Widget.ValidationTextarea("txtValIncidencias");
</script>
</body>
</html>