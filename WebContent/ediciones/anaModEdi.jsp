<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
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
//Control de errores de alta

String      codEdi  = "";
String      indPrev = "";
EdicionesVO ediVO  = new EdicionesVO();

//Se comprueba si vienen datos de el servlet de alta de interesados para recargar datos
//y los códigos de error
if(request.getParameter("codEdi") != null)
{
    codEdi = request.getParameter("codEdi");
    ediVO  = EdicionesGestion.devolverDatosEdi(codEdi);
}

if(request.getParameter("ind") != null)
{
    indPrev = request.getParameter("ind");
}


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />



<!--Funciones jquery ui-->
<script>
	$(function() {
        $( ".claseForFecha" ).datepicker({
            changeMonth:     true,
            changeYear:      true,
			dayNames:        [ "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado" ],
			dayNamesMin:     [ "D", "L", "M", "X", "J", "V", "S" ],
			dayNamesShort:   [ "Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sáb" ], 
			monthNamesType:  [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ] ,
			monthNamesShort: [ "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov","Dic"], 
			prevText:        "Ant",
			nextTextType:    "Sig",
			currentText:     "Hoy",
			closeText:       "Salir", 
			firstDay:        1,
			dateFormat:      "dd/mm/yy",
			yearRange:       "c-5:c+5",
			showButtonPanel: true
        });
	});
</script>


<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarCampos  = true;		
		
	validarCampos = spryselect1.validate()    & spryselect2.validate()    & 
	                sprytextfield1.validate() & sprytextfield2.validate() & sprytextfield3.validate() & sprytextfield4.validate() ;
				   
	if(validarCampos)
	{
		frmAnaModEdi.action = "../AltaModEdiServlet?ind=<%=indPrev%>";
		frmAnaModEdi.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados")
	}
	
}
  
function cargarComboModulos(idCurso,idMod)
{
	$.ajax({
	  url: "../CargaComboModEdiServlet?codCur=" + idCurso + "&valSel=" + idMod,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstModulos").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los profesores");
	  }
	});
}


function cargarComboProfesores(idMod,idProf)
{
	$.ajax({
	  url: "../CargaComboProfesoresArea?codMod=" + idMod + "&valSel=" + idProf,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstProfesores").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los profesores");
	  }
	});
}

function cargarComboAulas(idCentro,idAula)
{
	$.ajax({
	  url: "../CargarComboAulaServlet?codCentro=" + idCentro + "&muestraSelec=s&valSel=" + idAula,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstAulas").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las aulas");
	  }
	});
}

function cargarCombos()
{
	cargarComboModulos("<%=ediVO.getIdCur()%>","");
	cargarComboAulas("<%=ediVO.getCodCen()%>","");
}

</script>


</head>

<body class="fondoFormularios" onload="cargarCombos();">
<form id="frmAnaModEdi" name="frmAnaModEdi" method="post" action="">
<table width="63%" border="0">
  <tr>
    <td colspan="5" class="thDef">Añadir M&oacute;dulo
      <input name="hidCodEdi" type="hidden" id="hidCodEdi" value="<%=codEdi%>" /></td>
  </tr>
  <tr class="tdDef">
    <td width="14%" class="tdDef">M&oacute;dulos</td>
    <td colspan="4" class="tdDef"><span id="valMod">
      <select name="lstModulos" id="lstModulos" onchange="cargarComboProfesores($('#lstModulos').val());" tabindex="1">
      </select>
      <span class="selectInvalidMsg">*.</span><span class="selectRequiredMsg">*.</span></span></td>
    </tr>
  <tr class="tdDef">
    <td>Profesores</td>
    <td colspan="4"><span id="valProf">
      <select name="lstProfesores" id="lstProfesores" tabindex="1">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*.</span></span></td>
    </tr>
  <tr>
    <td class="tdDef">Aulas</td>
    <td colspan="4" class="tdDef"><select name="lstAulas" id="lstAulas" tabindex="1">
    </select></td>
    </tr>
  <tr>
    <td class="tdDef">Fecha Inicio</td>
    <td width="18%" class="tdDef"><span id="valFecIni">
    <input class="claseForFecha" name="txtFecIni" type="text" id="txtFecIni" size="15" maxlength="10" onchange="document.getElementById('hidFecIni').value = document.getElementById('txtFecIni').value;"/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>      
      <input type="hidden" name="hidFecIni" id="hidFecIni"/></td>
    <td width="14%" class="tdDef">Fecha Fin</td>
    <td width="54%" colspan="2" class="tdDef"><span id="valFecFin">
    <input class="claseForFecha" name="txtFecFin" type="text" id="txtFecFin" size="15" maxlength="10" onchange="document.getElementById('hidFecFin').value = document.getElementById('txtFecFin').value;"/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>      
      <input type="hidden" name="hidFecFin" id="hidFecFin" /></td>
  </tr>
  <tr>
    <td class="tdDef">Hora Inicio</td>
    <td class="tdDef"><span id="valHorIni">
    <input input  name="txtHoraIni" type="text" id="txtHoraIni" size="8" maxlength="5" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td class="tdDef">Hora Fin</td>
    <td colspan="2" class="tdDef"><span id="valHorFin">
    <input name="txtHoraFin" type="text" id="txtHoraFin" size="8" maxlength="5" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
  </tr>
 
  <tr>
    <td colspan="5" class="tdDef">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5" class="tdDef">D&iacute;as clase</td>
  </tr>
  <tr>
    <td class="tdDef">&nbsp;</td>
    <td colspan="2" class="tdDef">
        <%if(ediVO.isHayLun()){%>
            <input name="chkLunes" type="checkbox" id="chkLunes" value="true" />Lunes
        <%}%>        
    </td>
    <td class="tdDef">
        <%if(ediVO.isHayMar()){%>
            <input name="chkMartes" type="checkbox" id="chkMartes" value="true" />Martes
        <%}%>
        </td>
        
        <td class="tdDef">
        <%if(ediVO.isHayMie()){%>
            <input name="chkMierc" type="checkbox" id="chkMierc" value="true" />Mi&eacute;rcoles
        <%}%>
        </td>
  </tr>
  <tr>
    <td class="tdDef">&nbsp;</td>
    <td colspan="2" class="tdDef">
        <%if(ediVO.isHayJue()){%>
            <input name="chkJueves" type="checkbox" id="chkJueves" value="true" />Jueves
        <%}%>
    </td>
    <td class="tdDef">
        <%if(ediVO.isHayVie()){%>
            <input name="chkViernes" type="checkbox" id="chkViernes" value="true" />Viernes
        <%}%>
    </td>
    <td class="tdDef">
        <%if(ediVO.isHaySab()){%>
            <input name="chkSabado" type="checkbox" id="chkSabado" value="true" />S&aacute;bado
        <%}%>
    </td>
  </tr>
  <tr>
    <td colspan="5" class="cellBtnSub"><input name="btnAlta" type="button" class="cellBtnSub" id="btnAlta" value="Guardar" onclick="validaForm();" /></td>
  </tr>
</table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("valMod", {invalidValue:"-1"});
var spryselect2 = new Spry.Widget.ValidationSelect("valProf", {invalidValue:"-1"});
var sprytextfield1 = new Spry.Widget.ValidationTextField("valFecIni", "date", {format:"dd/mm/yyyy"});
var sprytextfield2 = new Spry.Widget.ValidationTextField("valFecFin", "date", {format:"dd/mm/yyyy"});
var sprytextfield3 = new Spry.Widget.ValidationTextField("valHorIni", "custom", {pattern:"00:00"});
var sprytextfield4 = new Spry.Widget.ValidationTextField("valHorFin", "custom", {pattern:"00:00"});
</script>
</body>
</html>