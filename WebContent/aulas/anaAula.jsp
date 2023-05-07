<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir aula</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Control de errores de alta
int       errCodAlta = 0;
AulasVO   aulasVO    = new AulasVO();

//Se comprueba si vienen datos de el servlet de alta de interesados para recargar datos
//y los códigos de error
if(request.getParameter("errorCode") != null)
{
    errCodAlta = new Integer(request.getParameter("errorCode")).intValue();
}


if(session.getAttribute("nuevoAulaErr") != null)
{
    aulasVO = (AulasVO) session.getAttribute("nuevoAulaErr");
    session.removeAttribute("nuevoAulaErr");
}

%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />


<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarCampos  = true;		
		
	validarCampos = sprytextfield1.validate() & sprytextfield2.validate() & spryselect1.validate() &  sprytextarea1.validate();
				   
	if(validarCampos)
	{
		frmAltaAula.action = "../AltaAulaServlet";
		frmAltaAula.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados")
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
</script>


</head>

<%if(errCodAlta == -1){%>
<script>
    alert("Se produjo un error en el alta del aula");
</script>
<%}%>
<%if(errCodAlta == -2){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>


<body class="fondoFormularios" onload="cargarComboCentros('-99');">
<form id="frmAltaAula" name="frmAltaAula" method="post" action="">
<table style="width:90%" border="0">
  <tr>
    <td colspan="5" class="thDef">Añadir Aula</td>
  </tr>
  <tr class="tdDef">
    <td style="width:8%" class="tdDef">Nombre</td>
    <td style="width:41%" class="tdDef">
      <span id="txtValNombre">
          <input name="txtNombre" type="text" id="txtNombre" value="<%=aulasVO.getNombre()%>" size="75" maxlength="100" />
        <span class="textfieldRequiredMsg">*</span></span>
    </td>
    <td style="width:3%">&nbsp;</td>
    <td style="width:9%">Centro</td>
    <td style="width:39%"><span id="lstValCentros">
      <label for="lstCentros"></label>
      <select name="lstCentros" id="lstCentros">
      </select>
      <script>frmAltaAula.lstCentros.value="<%=aulasVO.getIdCen()%>";</script>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
  </tr>
  <tr class="tdDef">
    <td>Plazas</td>
    <td><span id="txtValPlazas">
    <label for="txtPlazas"></label>
    <input name="txtPlazas" type="text" id="txtPlazas" <%if(aulasVO.getNumPla()!=-1){%> value="<%=aulasVO.getNumPla()%>"<%}%> size="8" maxlength="4" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td>&nbsp;</td>
    <td>Descripción</td>
    <td><span id="txtValDescripcion">
      <label for="txtDescripcion"></label>
      <textarea name="txtDescripcion" id="txtDescripcion" cols="45" rows="5"><%=aulasVO.getDescripcion()%></textarea>
      <span class="textareaRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td colspan="5" class="tdDef"><strong>Características</strong></td>
  </tr>
  <tr>
    <td rowspan="3">&nbsp;</td>
    <td><span class="tdDef">
      <label>
        <input name="chkAulaInformatica" type="checkbox" id="chkAulaInformatica" <%if(aulasVO.isEsAulInf()){%>checked="checked"<%}%> value="true" />
        Aula informática</label>
    </span></td>
    <td>&nbsp;</td>
    <td colspan="2"><span class="tdDef">
      <input name="chkProyector" type="checkbox" id="chkProyector" <%if(aulasVO.isTieneProy()){%>checked="checked"<%}%> value="true" />
      Proyector
    </span></td>
  </tr>
  <tr>
    <td><span class="tdDef">
      <input name="chkTelevision" type="checkbox" id="chkTelevision" <%if(aulasVO.isTieneTV()){%>checked="checked"<%}%> value="true" />
Televisión</span></td>
    <td>&nbsp;</td>
    <td colspan="2"><span class="tdDef">
      <input name="chkAireAcond" type="checkbox" id="chkAireAcond" <%if(aulasVO.isTieneAC()){%>checked="checked"<%}%> value="true" />
      Aire Acondicionado</span></td>
  </tr>
  <tr>
    <td><span class="tdDef">
      <input name="chkImpresora" type="checkbox" id="chkImpresora" <%if(aulasVO.isTieneImp()){%>checked="checked"<%}%> value="true" />
      Impresora
    </span></td>
    <td>&nbsp;</td>
    <td colspan="2"><span class="tdDef">
      <input name="chkInternet" type="checkbox" id="chkInternet" <%if(aulasVO.isTieneInt()){%>checked="checked"<%}%> value="true" />
      Internet</span></td>
  </tr>
  <tr>
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5" class="cellBtnSub"><input name="btnAlta" type="button" class="cellBtnSub" id="btnAlta" value="Guardar" onclick="validaForm();" /></td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre");
var sprytextfield2 = new Spry.Widget.ValidationTextField("txtValPlazas", "integer");
var spryselect1    = new Spry.Widget.ValidationSelect("lstValCentros", {invalidValue:"-1"});
var sprytextarea1  = new Spry.Widget.ValidationTextarea("txtValDescripcion");
</script>
</body>
</html>