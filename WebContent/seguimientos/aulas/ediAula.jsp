<%@page import="es.jahernandez.gestion.AulasGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<%@ page import = "java.util.Vector"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Datos aula</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Control de errores de edicion
int       errCodEdi = 0;
AulasVO   aulasVO   = new AulasVO();
CentrosVO centrosVO = new CentrosVO();
boolean   msgEdiCor = false;

//Se cargan los datos del aula
if(request.getParameter("lstAula") != null)
{
    aulasVO = AulasGestion.devolverDatosAula(request.getParameter("lstAula"));
}

//Se comprueba si vienen datos de el servlet de edicion de aula para recargar datos
//y los códigos de error
if(request.getParameter("errorEdiCode") != null)
{
    errCodEdi = new Integer(request.getParameter("errorEdiCode")).intValue();
}

if(request.getParameter("msgEdiCor") != null)
{
    msgEdiCor = true;
}


if(session.getAttribute("ediAulaErr") != null)
{
    aulasVO = (AulasVO) session.getAttribute("ediAulaErr");
    session.removeAttribute("ediAulaErr");
}

%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarCampos  = true;		
		
	validarCampos = sprytextfield1.validate() & sprytextfield2.validate() & spryselect1.validate() &  sprytextarea1.validate();
				   
	if(validarCampos)
	{
		frmEdiAula.action = "../EditarAulaServlet";
		frmEdiAula.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
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

<%if(errCodEdi == -1){%>
<script>
    alert("Se produjo un error editando el aula");
</script>
<%}%>
<%if(errCodEdi == -2){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>
<%if(msgEdiCor){%>
<script>
    alert("El aula se editó correctamente");
</script>
<%}%>



<body class="fondoFormularios" onload="cargarComboCentros('<%=aulasVO.getIdCen()%>');">
<form id="frmEdiAula" name="frmEdiAula" method="post" action="">
<input name="hidIdAula" type="hidden" value="<%=aulasVO.getIdAula()%>" />
<table style="width:90%" border="0">
  <tr>
    <td colspan="5" class="thDef">Datos Aula</td>
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
      <select name="lstCentros" id="lstCentros" disabled="disabled"></select>
</span></td>
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
    <td colspan="5" class="cellBtnSub"><input name="btnAlta" type="button" class="cellBtnSub" id="btnAlta" value="Editar" onclick="validaForm();" /></td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre");
var sprytextfield2 = new Spry.Widget.ValidationTextField("txtValPlazas", "integer");
var spryselect1    = new Spry.Widget.ValidationSelect("lstValCentros", {isRequired:false});
var sprytextarea1  = new Spry.Widget.ValidationTextarea("txtValDescripcion");
</script>
</body>
</html>