<%@page import="es.jahernandez.gestion.ProfesoresGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>


<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>

<html>

<head>
<title>Ficha Profesor</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
// TODO Auto-generated method stub
response.setContentType("text/html; charset=UTF-8");
//Control de caché
response.setDateHeader ("Expires", -1); 
response.setHeader("Pragma","no-cache"); 
if(request.getProtocol().equals("HTTP/1.1")) 
	response.setHeader("Cache-Control","no-cache"); 

ProfesoresVO profVO = new ProfesoresVO();

//Control de errores de edición
int       errCodEdi     = 0;
String    indLista      = "";


//Se comprueba si vienen datos del servlet de alta de profesores para recargar datos
//y los códigos de error

if(request.getParameter("codProf") != null)
{
    profVO = ProfesoresGestion.devolverDatosProfesor(request.getParameter("codProf"));
}

if(request.getParameter("ind") != null)
{
    indLista = request.getParameter("ind");
}

if(request.getParameter("errorCode") != null)
{
    errCodEdi = new Integer(request.getParameter("errorCode")).intValue();
}

if(session.getAttribute("edicionProfesorErr") != null)
{
    profVO = (ProfesoresVO) session.getAttribute("edicionProfesorErr");
    session.removeAttribute("nuevoProfesorErr");
}
%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios" onload="cargaCombos();">
<script src="../js/validaGlobal.js"></script>


<!--Funciones jquery ui-->
<script>
	$(function() {
        $( "#gestionAreas" ).dialog({
           autoOpen: false,
            height: 400,
            width: 450,
            modal: true,
            buttons: {
                "Cerrar": function() {
					cargarCombosAreas();
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                cargarCombosAreas();
				allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
	
	$(function() {
        $( "#lstMod" ).dialog({
           autoOpen: false,
            height: 600,
            width: 1200,
            modal: true,
            buttons: {
                "Cerrar": function() {
                    $( this ).dialog( "close" );
                }
            },
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
	
	$(function() {
        $( "#txtFecNac" ).datepicker({
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
			yearRange:       "c-100:c+0",
			showButtonPanel: true
        });
	});
 </script>

<script>
function validarForm()
{
	var todoCorrecto;
	
	todoCorrecto = sprytextfield1.validate() & sprytextfield2.validate() & sprytextfield3.validate() & sprytextfield4.validate() & 
                   sprytextfield5.validate() & sprytextfield6.validate() & sprytextfield7.validate() & sprytextfield8.validate() & 
                   sprytextfield9.validate() & spryselect1.validate()    & spryselect2.validate()    & sprytextarea1.validate();

	if(todoCorrecto)
	{
		frmFichaProfesor.submit();
	}
	else
	{
		alert("Compruebe los campos resaltados");
	}

}

function cargaComboTipDoc(idTipDoc)
{   
	$.ajax({
	  url: "../CargaComboTipoDocumentoServlet",
	  success: function(data) {
		$("#selTipDoc").html(data);
		if(idTipDoc != "undefined")
		{
			$("#selTipDoc").val(idTipDoc);
		}
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de documentos");
	  }
	});
}

function cargaComboProv(idProv)
{   
	$.ajax({
	  url: "../CargaComboProvinciasServlet",
	  success: function(data) {
		$("#selProv").html(data);
		if(idProv != "undefined")
		{
			$("#selProv").val(idProv);
		}
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando provincias");
	  }
	});
}

function cargarCombosAreas(idArea)
{   
        $.ajax({
	  url: "../CargaComboAreaServlet?codProf=<%=profVO.getIdProf()%>&selec=n&valSel=" + idArea,
	  success: function(data) {
		$("#lstAreas").html(data);
		
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las áreas");
	  }
	});
}



function cargaCombos()
{
    cargaComboTipDoc("<%=profVO.getCodDoc()%>");
    cargaComboProv("<%=profVO.getCodProv()%>");
	cargarCombosAreas();
}


function verClasesProf(tipBus)
{
	document.getElementById("fraMod").src="./verModProf.jsp?codProf=<%=profVO.getIdProf()%>&tipBus=" + tipBus;
	
	switch (tipBus)
	{
		case 1: $("#lstMod").dialog("option" , "title" , "Histórico Clases" );break;
		case 2: $("#lstMod").dialog("option" , "title" , "Clases Pendientes" );break;
		case 3:
			document.getElementById("fraMod").src="./clasIndProf.jsp?codProf=<%=profVO.getIdProf()%>";
			$("#lstMod").dialog("option" , "title" , "Clases Individuales" );
			break;
	}
	
	$("#lstMod").dialog( "open" );
}




</script>

<%if(errCodEdi == -1){%>
<script>
    alert("El DNI introducido está duplicado");
</script>
<%}%>
<%if(errCodEdi == -2){%>
<script>
    alert("Se produjo un error de acceso a la base de datos");
</script>
<%}%>

<div id="gestionAreas" title="Áreas Profesor">
   <iframe name="fraAreaProf"     id="fraAreaProf"     frameborder="0" src="./gestionAreas.jsp?codProf=<%=profVO.getIdProf()%>" width="100%" height="300" scrolling="no"> </iframe>
</div>   
<div id="lstMod" title="Listado Módulos">
   <iframe name="fraMod"     id="fraMod"     frameborder="0" src="" width="100%" height="500" scrolling="no"> </iframe>
</div>



<form action="../EditarProfesorServlet?ind=<%=indLista%>" method="post" name="frmFichaProfesor" target="_self" id="frmFichaProfesor">
  <table width="100%" border="0" class="tdDef">
  <tr class="thDef">
    <th height="33" colspan="6" scope="col">Ficha Profesor</th>
  </tr>
  <tr>
    <td colspan="6">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="6" bgcolor="#FFFFFF"><span class="colorTextoBotPest"><strong>Datos Personales</strong></span></td>
  </tr>
  <tr>
    <td width="2%" rowspan="8" bgcolor="#FFFFFF">&nbsp;</td>
    <td width="11%">Nombre</td>
    <td width="31%"><span id="valNombre">
      <input name="txtNombre" type="text" id="txtNombre" value="<%=profVO.getNombre()%>" size="25" maxlength="15" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    <td width="2%">&nbsp;</td>
    <td width="11%">Apellidos</td>
    <td width="43%"><span id="valApellidos">
      <input name="txtApellidos" type="text" id="txtApellidos" value="<%=profVO.getApellidos()%>" size="40" maxlength="31" />
      <span class="textfieldRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td>Tipo Documento</td>
    <td><span id="valTipDoc">
      <select name="selTipDoc" id="selTipDoc">
      </select>
      <span class="selectRequiredMsg">*</span><span class="selectInvalidMsg">*</span></span></td>
    <td>&nbsp;</td>
    <td>N&uacute;mero Doc.</td>
    <td><span id="valNumDoc">
      <input name="txtNumDoc" type="text" id="txtNumDoc" value="<%=profVO.getNumDoc()%>" size="25" maxlength="50" />
      <span class="textfieldRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td>Fecha Nacimiento</td>
    <td><span id="valFecNac">
            <input name="txtFecNac" type="text" id="txtFecNac" size="15" 
                   <%if(profVO.getFecNac().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(profVO.getFecNac())%>"<%}%> maxlength="10" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>Direcci&oacute;n</td>
    <td colspan="4"><span id="valDireccion">
      <textarea name="txtDireccion" id="txtDireccion" cols="75" rows="5"><%=profVO.getDireccion()%></textarea>
      <span class="textareaRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td>Localidad</td>
    <td colspan="4"><span id="valLocalidad">
      <input name="txtLocalidad" type="text" id="txtLocalidad" value="<%=profVO.getLocalidad()%>" size="100" maxlength="255" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td>Provincia</td>
    <td><span id="valProvincia">
      <select name="selProv" id="selProv">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td>&nbsp;</td>
    <td>C&oacute;digo Postal</td>
    <td><span id="valCodPostal">
    <input name="txtCodPostal" type="text" id="txtCodPostal" value="<%=profVO.getCodPos()%>" size="10" maxlength="5" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
  </tr>
  <tr>
    <td colspan="5">Activo
        <input name="chkActivo" type="checkbox" id="chkActivo" value="true" <%if(profVO.isActivo()){%> checked="checked"<%}%> /></td>
  </tr>
  <tr>
    <td colspan="5"><input name="hidCodProf" type="hidden" id="hidCodProf" value="<%=profVO.getIdProf()%>" /></td>
  </tr>
  <tr>
    <td colspan="6" bgcolor="#FFFF99"><span class="colorTextoBotPest"><strong>Datos de Contacto</strong></span></td>
  </tr>
  <tr>
    <td rowspan="3" bgcolor="#FFFF99">&nbsp;</td>
    <td>Tel&eacute;fono</td>
    <td><span id="valTelef">
    <input name="txtTelef" type="text" id="txtTelef" value="<%=profVO.getTelef()%>" size="15" maxlength="9" />
<span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td>&nbsp;</td>
    <td>M&oacute;vil</td>
    <td><span id="valMovi">
    <input name="txtMovil" type="text" id="txtMovil" value="<%=profVO.getMov()%>" size="15" maxlength="9" />
<span class="textfieldInvalidFormatMsg">*</span></span></td>
  </tr>
  <tr>
    <td>Correo electr&oacute;nico</td>
    <td colspan="4"><span id="valEMail">
    <input name="txtEmail" type="text" id="txtEmail" value="<%=profVO.getEmail()%>" size="100" maxlength="100" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="4">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="6" bgcolor="#FFFF66"><span class="colorTextoBotPest"><strong>Conocimientos</strong></span></td>
    </tr>
  <tr>
    <td rowspan="2" bgcolor="#FFFF66">&nbsp;</td>
    <td>&Aacute;reas</td>
    <td><select name="lstAreas" size="5" id="lstAreas" style="width:250px"></select>
      <input type="button" name="btnAreas" id="btnAreas" value="Cambiar" onclick="$('#gestionAreas').dialog( 'open' );" /></td>
    <td>&nbsp;</td>
    <td>Observaciones</td>
    <td><textarea name="txtObserv" id="txtObserv" cols="75" rows="5"><%=profVO.getObserv()%></textarea></td>
  </tr>
  <tr>
    <td colspan="5"><input class="tdDef" type="button" name="btnVerMod" id="btnVerMod" value="Hist. Clases"  onclick="verClasesProf(1);"/> 
    				<input class="tdDef" type="button" name="btnClasPend" id="btnClasPend" value="Clases Pend." onclick="verClasesProf(2);"/>
    				<input class="tdDef" type="button" name="btnClasInd" id="btnClasInd" value="Clases Indiv." onclick="verClasesProf(3);"/>
    </td>
   </tr>
  <tr>
    <td colspan="6" class="cellBtnSub">
    	<input class="cellBtnSub" type="button" name="btnVolver" id="btnVolver" value="Volver"  onclick="window.open('./RBProfesores.jsp?ind<%=indLista%>' , '_self' , '')"/>      
        <input class="cellBtnSub" type="button" name="btnAceptar" id="btnAceptar" value="Editar" onclick="validarForm();"/></td>
  </tr>
  </table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("valNombre");
var sprytextfield2 = new Spry.Widget.ValidationTextField("valApellidos");
var sprytextfield3 = new Spry.Widget.ValidationTextField("valNumDoc");
var sprytextfield4 = new Spry.Widget.ValidationTextField("valFecNac", "date", {format:"dd/mm/yyyy"});
var sprytextfield5 = new Spry.Widget.ValidationTextField("valLocalidad");
var sprytextfield6 = new Spry.Widget.ValidationTextField("valCodPostal", "zip_code", {format:"zip_custom", pattern:"00000"});
var sprytextfield7 = new Spry.Widget.ValidationTextField("valTelef", "phone_number", {format:"phone_custom", pattern:"900000000", isRequired:false});
var sprytextfield8 = new Spry.Widget.ValidationTextField("valMovi", "phone_number", {format:"phone_custom", pattern:"600000000", isRequired:false});
var sprytextfield9 = new Spry.Widget.ValidationTextField("valEMail", "email");
var spryselect1 = new Spry.Widget.ValidationSelect("valTipDoc", {invalidValue:"0"});
var spryselect2 = new Spry.Widget.ValidationSelect("valProvincia", {invalidValue:"0"});
var sprytextarea1 = new Spry.Widget.ValidationTextarea("valDireccion");
</script>
</body>
</html>