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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Alta Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
//Carga de datos del formulario de búsqueda
AlumnosVO aluVO          = new AlumnosVO();

//Control de errores de alta
int       errCodAlta     = 0;

if(request.getParameter("parNumDoc") != null)
{
    aluVO.setNumDocAlu(request.getParameter("parNumDoc"));
}

if(request.getParameter("parApellidos") != null)
{
    aluVO.setAp1Alu(request.getParameter("parApellidos"));
}

if(request.getParameter("parEmail") != null)
{
    aluVO.setEmail(request.getParameter("parEmail"));
}

if(request.getParameter("parCodPost") != null)
{
    aluVO.setCodPosAlu(request.getParameter("parCodPost"));
}

if(request.getParameter("parNivFor") != null)
{
    aluVO.setNivForm(new Integer(request.getParameter("parNivFor")).intValue());
}

if(request.getParameter("parEmpresas") != null)
{
    aluVO.setEmpAlu(request.getParameter("parEmpresas"));
}

if(request.getParameter("parNombreAlu") != null)
{
    aluVO.setNombre(request.getParameter("parNombreAlu"));
}
    
if(request.getParameter("parDesempleado") != null)
{
    if(request.getParameter("parDesempleado").equals("true"))
    {
        aluVO.setDesemp(true);
    }
}

//Se comprueba si vienen datos del servlet de alta de interesados para recargar datos
//y los códigos de error

if(request.getParameter("errorCode") != null)
{
    errCodAlta = new Integer(request.getParameter("errorCode")).intValue();
}


if(session.getAttribute("nuevoAlumnoErr") != null)
{
    aluVO = (AlumnosVO) session.getAttribute("nuevoAlumnoErr");
    session.removeAttribute("nuevoAlumnoErr");
}

if(session.getAttribute("datAluAltEmp") != null)
{
    aluVO = (AlumnosVO) session.getAttribute("datAluAltEmp");
    session.removeAttribute("datAluAltEmp");
}



String valDefCP = "";

if(! aluVO.getCodPosAlu().trim().equals(""))
{
    valDefCP = aluVO.getCodPosAlu().substring(0, 2);
}
else
{
    valDefCP = "37";
}


//Si se viene de la pagina de carga de empresa, se carge el código de la empresa recien creada
if(request.getParameter("codEmp") != null)
{
    aluVO.setEmpAlu(request.getParameter("codEmp"));
}


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js"    type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js"  type="text/javascript"></script>
<script src="../SpryAssets/SpryTabbedPanels.js"        type="text/javascript"></script>

<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css"    rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css"  rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryTabbedPanels.css"        rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

</head>

<body class="fondoFormularios" onload="cargaCombos();">
<script src="../js/validaGlobal.js"></script>

<!--Funciones jquery ui-->
<script>
	$(function() {
        $( "#nuevaEmpresa" ).dialog({
           autoOpen: false,
            height: 800,
            width: 1400,
            modal: true,
            buttons: {
                "Crear Empresa": function() {
                   fraLogos.validarForm();
                },
                "Cancelar": function() {
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
 
<script language="JavaScript" type="text/JavaScript">
function validaForm(pestana)
{
	var validarFormEsp = false;
	var validarCampos  = true;		
				
	if(Trim(frmAltaInteresado.txtTelf.value) == '' && Trim(frmAltaInteresado.txtMovil.value) == '')
	{
		validarFormEsp = false;
	}
	else
	{
		validarFormEsp = true;
	}
	
	validarCampos = validarFormEsp && 
				   sprytextfield1.validate()  & sprytextfield2.validate()  & sprytextfield3.validate() & spryselect1.validate()     &
				   sprytextfield4.validate()  & spryselect2.validate()     & spryselect3.validate()    & sprytextfield5.validate()  & 
				   sprytextfield6.validate()  & sprytextfield7.validate()  & sprytextfield8.validate() & sprytextfield9.validate()  &
				   sprytextfield10.validate() & sprytextfield11.validate() & sprytextfield12.validate()& sprytextfield13.validate() &
   			       spryselect4.validate()     & sprytextarea1.validate();
	
	if(validarCampos)
	{
		frmAltaInteresado.action = "../AltaNuevoInteresadoServlet?pestana=" + pestana;
		frmAltaInteresado.submit();
	}
	else
	{
		if(! validarFormEsp)
		{
			alert('Se Debe introducir un número de teléfono o un móvil');
		}
		else
		{
			alert('Valide al valor de los campos resaltados');
		}
		
	}
	
}

function cargaPaginaAltaEmpresa()
{
	document.getElementById("fraLogos").src="../empresas/altaEmpInter.jsp?urlProc=1";
	$("#nuevaEmpresa").dialog( "open" );
}

function cerrarDialogo(nuevaEmpresa)
{
	cargaComboEmpresas(nuevaEmpresa);
	$("#nuevaEmpresa").dialog( "close" );
}

function cargaComboEmpresas(idEmp)
{   
	$.ajax({
	  url: "../CargaComboEmpresaServlet?valSel=" + idEmp,
	  success: function(data) {
		$(document).ready(function() 
		{
			$("#selEmpresa").html(data);
		});
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
		$("#selNivFor").html(data);
		if(idNF != "undefined")
		{
			$("#selNivFor").val(idNF);
		}
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los niveles formativos");
	  }
	});
}

function cargaComboTipDoc(idTipDoc)
{   
	$.ajax({
	  url: "../CargaComboTipoDocumentoServlet",
	  success: function(data) {
		$("#selValTipDoc").html(data);
		if(idTipDoc != "undefined")
		{
			$("#selValTipDoc").val(idTipDoc);
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


function cargaCombos()
{
    $(document).ready(function() 
	{
		// put all your jQuery goodness in here.
		cargaComboTipDoc(0);
		
		<%if(aluVO.getNivForm() <= 0){%>
			cargaComboNF();
		<%}else{%>    
			cargaComboNF("<%=aluVO.getNivForm()%>");
		<%}%>  
		
		
		<%if("".equals(aluVO.getEmpAlu().trim())){%>
			cargaComboEmpresas(0);
		<%}else{%>    
			cargaComboEmpresas("<%=aluVO.getEmpAlu().trim()%>");
		<%}%>
		  
		cargaComboProv("<%=valDefCP%>"); 

	});
}



</script>

<%if(errCodAlta == -1){%>
<script>
    alert("El DNI introducido está duplicado");
</script>
<%}%>
<%if(errCodAlta == -2){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>


<div id="nuevaEmpresa" title="Crear Nueva Empresa">
   <iframe name="fraLogos"     id="fraLogos"     frameborder="0" src="../empresas/altaEmpInter.jsp?urlProc=1" style="width:100%" height="400" scrolling="no"> </iframe>
</div>

<form action="../AltaNuevoInteresadoServlet?pestana=1" method="post" name="frmAltaInteresado" target="_self" id="frmAltaInteresado">
  <table style="width:100%" border="0">
  <tr class="thDef">
    <th height="50" colspan="3" scope="col">Alta Interesado</th>
  </tr>
  <tr>
    <td style="width:38%">
      <span id="txtValNombre">
      <input name="txtNombre" type="text" class="tdDef" id="txtNombre" value="<%=aluVO.getNombre()%>" size="50" maxlength="50" />
      <span class="textfieldRequiredMsg">*.</span><span class="textfieldInvalidFormatMsg">*</span></span>
    </td>
    <td style="width:41%"><span id="txtValApe">
    <input name="txtApe" type="text" class="tdDef" id="txtApe" value="<%=aluVO.getAp1Alu()%>" size="50" maxlength="50" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td style="width:21%" class="tdDef"><span id="txtValFecNac">
    <label>Fecha Nac.
        <input name="txtFecNac" type="text" class="tdDef" id="txtFecNac" <%if(aluVO.getFecNac().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(aluVO.getFecNac())%>"<%}%> onchange="document.getElementById('hidFecNac').value = document.getElementById('txtFecNac').value;" />
    </label>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*.</span></span>
      <input type="hidden" name="hidFecNac" id="hidFecNac" /></td>
  </tr>
</table>
<br><br>
<table style="width:100%" border="0">
  <tr>
    <th class="tdDef" scope="col"><div id="TabbedPanels1" class="TabbedPanels">
      <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Datos Personales</span></li>
</ul>
      <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
        	<div class="colorFondoPrincipalPestana" id="fraPestanas" style="width:1024" height="800">
      <table style="width:90%" border="0" align="center" class="colorTextoBotPest">
        <tr>
          <td style="width:14%" class="tdDef" scope="col">Tipo Documento</td>
          <td style="width:19%" class="tdDef" scope="col"><span id="spryselect1">
            <select name="selValTipDoc" id="selValTipDoc"></select>
            <script>frmAltaInteresado.selValTipDoc.value="<%=aluVO.getTipDocAlu()%>";</script>
            <span class="selectRequiredMsg">*</span></span></td>
          <td style="width:10%" class="tdDef" scope="col">Número</td>
          <td style="width:35%" class="tdDef" scope="col"><span id="txtValNumDoc">
            <input name="txtNumDoc" type="text" id="txtNumDoc" value="<%=aluVO.getNumDocAlu()%>" size="25" maxlength="20" />
            <span class="textfieldRequiredMsg">*</span></span></td>
          <td colspan="2" class="tdDef" scope="col"><label>
            <input name="chkDesempleado" value="true" type="checkbox" id="chkDesempleado" <%if(aluVO.isDesemp()){%>checked="checked"<%}%> />
            Desempleado</label></td>
          </tr>
        <tr>
          <td colspan="4">&nbsp;</td>
          <td colspan="2" class="tdDef"><label>
            <input name="chkNoDeseado" value="true" type="checkbox" id="chkNoDeseado" <%if(aluVO.isAlND()){%>checked="checked"<%}%>/>
            No deseado</label></td>
          </tr>
        <tr>
          <td class="tdDef">Nivel Formativo</td>
          <td class="tdDef"><span id="selValNivFor">
            <select name="selNivFor" id="selNivFor"></select>
            <span class="selectRequiredMsg">*</span></span></td>
          <td class="tdDef">Empresa</td>
          <td colspan="2" class="tdDef"><span id="selValEmp">
            <select name="selEmpresa" id="selEmpresa"></select>
            <span class="selectRequiredMsg">*</span></span></td>
          <td style="width:10%" class="tdDef"><input class="cellBtnSub" type="button" onclick= "cargaPaginaAltaEmpresa();" value="Añadir" /></td></td>
          </tr>
      </table>
      <br>
      <table style="width:90%" border="0" align="center" class="colorFondoBlancoTablaPest" cellspacing="0">
        <tr>
          <th class="tdDef" scope="col">Datos de Contacto</th>
          <th colspan="3" class="colorFondoPrincipalPestana" scope="col">&nbsp;</th>
          </tr>
        <tr>
          <td style="width:17%" height="28" class="colorFondoBlancoTablaPest">Fijo</td>
          <td style="width:50%"><span id="txtValTelf">
          <input name="txtTelf" type="text" id="txtTelf" value="<%=aluVO.getFijAlu()%>" maxlength="9" />
          <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
          <td style="width:12%">Móvil</td>
          <td style="width:21%"><span id="txtValMovil">
          <input name="txtMovil" type="text" id="txtMovil" value="<%=aluVO.getMovAlu()%>" maxlength="9" />
<span class="textfieldInvalidFormatMsg">*</span></span></td>
        </tr>
        <tr>
          <td height="31">Correo electrónico</td>
          <td colspan="3"><span id="txtValMail">
          <input name="txtMail" type="text" id="txtMail" value="<%=aluVO.getEmail()%>" size="150" maxlength="150" />
<span class="textfieldInvalidFormatMsg">*</span></span></td>
          </tr>
        <tr>
          <td height="28">Dirección</td>
          <td><span id="txtValDirecc">
            <input name="txtDirec" type="text" id="txtDirec" value="<%=aluVO.getDirAlu()%>" size="100" maxlength="150" />
</span></td>
          <td>Código Postal</td>
          <td><span id="txtValCodPos">
            <input name="txtCodPos" type="text" id="txtCodPos" value="<%=aluVO.getCodPosAlu()%>" size="10" maxlength="5" />
            <span class="textfieldInvalidFormatMsg">*</span></span></td>
        </tr>
        <tr>
          <td height="31">Localidad</td>
          <td><span id="txtValLoc">
            <input name="txtLocal" type="text" id="txtLocal" value="<%=aluVO.getLocalAlu()%>" size="80" maxlength="80" />
</span></td>
          <td>Provincia</td>
          <td><span id="selValProv">
            <select name="selProv" id="selProv"></select>
</span></td>
        </tr>
        <tr>
          <td height="31">Madre/Padre/Resp.</td>
          <td><span id="valResponsable">
            <input name="txtResponsable" type="text" id="txtResponsable" value="<%=aluVO.getResponsable() %>" size="75" maxlength="50" />
</span></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </table>
      <br>
      <table style="width:90%" border="0" align="center" class="colorTextoBotPest">>
        <tr>
          <th style="width:14%" class="tdDef" scope="col">Colegio</th>
          <th style="width:48%" class="tdDef" scope="col"><span id="valColegio">
            <input name="txtColegio" type="text" id="txtColegio" value="<%=aluVO.getColegio() %>" size="75" maxlength="100" />
</span></th>
          <th style="width:11%" class="tdDef" scope="col">Curso</th>
          <th style="width:27%" class="tdDef" scope="col"><span id="valCurso">
            <input name="txtCurso" type="text" id="txtCurso" value="<%=aluVO.getCurso() %>" size="25" maxlength="35" />
</span></th>
        </tr>
      </table>
      <br>
      <table style="width:90%" align="center" border="0" class="colorTextoBotPest">
        <tr>
          <td style="width:48%">Observaciones</td>
          <td style="width:52%">LOPD</td>
        </tr>
        <tr>
          <td rowspan="2"><span id="txtValObs">
            <textarea name="txtObservaciones" id="txtObservaciones" cols="70" rows="5"><%=aluVO.getIntereses()%></textarea>
</span></td>
          <td height="39" class="tdDef"><input type="checkbox" name="chkAutCesDat" id="chkAutCesDat" value="true" <%if(aluVO.isAutCesDat()){%>checked="checked"<%}%>/>
            Autoriza Cesión Datos</td>
        </tr>
        <tr>
          <td class="tdDef"><input type="checkbox" name="chkAutComCom" id="chkAutComCom" value="true" <%if(aluVO.isAutComCom()){%>checked="checked"<%}%>/>
            Autoriza Comunicaciones Comerciales</td>
        </tr>
      </table>
      <table style="width:90%" border="0">
        <tr>
          <td class="cellBtnSub"><input name="btnGuardar" type="button" class="cellBtnSub" id="btnGuardar" value="Guardar" onclick="validaForm(1);" /></td>
        </tr>
      </table>
      <br>
      
    </div>
        </div>
</div>
    </div></th>
  </tr>
  <tr>
    <th align="left" scope="col">&nbsp;</th>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre", "none");
var sprytextfield2 = new Spry.Widget.ValidationTextField("txtValApe", "none");
var sprytextfield3 = new Spry.Widget.ValidationTextField("txtValFecNac", "date", {format:"dd/mm/yyyy", hint:"dd/mm/yyyy", isRequired:false});
var spryselect1 = new Spry.Widget.ValidationSelect("spryselect1", {isRequired:false});
var sprytextfield4 = new Spry.Widget.ValidationTextField("txtValNumDoc", "none", {isRequired:false});
var spryselect2 = new Spry.Widget.ValidationSelect("selValNivFor", {isRequired:false});
var spryselect3 = new Spry.Widget.ValidationSelect("selValEmp", {isRequired:false});
var sprytextfield5 = new Spry.Widget.ValidationTextField("txtValTelf", "phone_number", {format:"phone_custom", pattern:"000000000", isRequired:false});
var sprytextfield6 = new Spry.Widget.ValidationTextField("txtValMovil", "phone_number", {format:"phone_custom", pattern:"600000000", isRequired:false});
var sprytextfield7 = new Spry.Widget.ValidationTextField("txtValMail", "email", {isRequired:false});
var sprytextfield8 = new Spry.Widget.ValidationTextField("txtValDirecc", "none", {isRequired:false});
var sprytextfield9 = new Spry.Widget.ValidationTextField("txtValCodPos", "zip_code", {isRequired:false, format:"zip_custom", pattern:"00000"});
var sprytextfield10 = new Spry.Widget.ValidationTextField("txtValLoc", "none", {isRequired:false});
var spryselect4 = new Spry.Widget.ValidationSelect("selValProv", {isRequired:false});
var sprytextarea1 = new Spry.Widget.ValidationTextarea("txtValObs", {isRequired:false});
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
var sprytextfield11 = new Spry.Widget.ValidationTextField("valResponsable", "none", {isRequired:false});
var sprytextfield12 = new Spry.Widget.ValidationTextField("valColegio", "none", {isRequired:false});
var sprytextfield13 = new Spry.Widget.ValidationTextField("valCurso", "none", {isRequired:false});
</script>
</html>