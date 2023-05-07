<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>


<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Datos personales interesado</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Carga de datos del formulario de búsqueda
AlumnosVO aluVO          = new AlumnosVO();

//Control de errores
int       codErr         = 0;
int       codErrBor      = 0;

if(request.getParameter("codInt") != null)
{
    aluVO = AlumnosGestion.devolverDatosAlumno(request.getParameter("codInt"));
}


if(session.getAttribute("datAluAltEmp") != null)
{
    aluVO = (AlumnosVO) session.getAttribute("datAluAltEmp");
    session.removeAttribute("datAluAltEmp");
}

if(request.getParameter("errorCode") != null)
{
    codErr = new Integer(request.getParameter("errorCode")).intValue();
}

if(request.getParameter("errorBorCode") != null)
{
    codErrBor = new Integer(request.getParameter("errorBorCode")).intValue();
}



if(session.getAttribute("ediAlumnoErr") != null)
{
    aluVO = (AlumnosVO) session.getAttribute("ediAlumnoErr");
    session.removeAttribute("ediAlumnoErr");
}

//Si se viene de la pagina de carga de empresa, se carge el código de la empresa recien creada
if(request.getParameter("codEmp") != null)
{
    aluVO.setEmpAlu(request.getParameter("codEmp"));
}

%>

<script src="../js/validaGlobal.js"></script>
<script src="../SpryAssets/SpryValidationTextField.js"  type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js"     type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js"   type="text/javascript"></script>

<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css"    rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css"  rel="stylesheet" type="text/css" />
<link href="../css/disenno.css"                        rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>

</head>

<body class="colorFondoPrincipalPestana" onload="cargaCombos();">

<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	var validarFormEsp = true;
	var validarCampos  = true;		
		
	//Se le da valor a los campos ocultos
	frmDatosPersonales.hidNombre.value = parent.frmFichaInteresado.txtNombre.value;
	frmDatosPersonales.hidApe.value    = parent.frmFichaInteresado.txtApe.value;
	frmDatosPersonales.hidFecNac.value = parent.frmFichaInteresado.txtFecNac.value;		
		
	if(Trim(frmDatosPersonales.hidNombre.value) == '' || Trim(frmDatosPersonales.hidApe.value) == '')
	{
		validarFormEsp = false;
	} 
	
	if(Trim(frmDatosPersonales.txtTelf.value) == '' && Trim(frmDatosPersonales.txtMovil.value) == '')
	{
		validarFormEsp = validarFormEsp && false;
	}
	else
	{
		validarFormEsp = validarFormEsp && true;
	}		
	
	validarCampos = validarFormEsp && 
				   sprytextfield01.validate()  & sprytextfield02.validate() & sprytextfield03.validate() & sprytextfield04.validate() &
				   sprytextfield05.validate()  & sprytextfield06.validate() & sprytextfield07.validate() & sprytextfield08.validate() & 
				   sprytextfield09.validate()  & sprytextfield10.validate() & spryselect01.validate()    & spryselect02.validate()    & 
				   spryselect03.validate()     & spryselect04.validate()    & sprytextarea01.validate();
			
	if(validarCampos)
	{
		frmDatosPersonales.action = "../EditarInteresadoServlet";
		frmDatosPersonales.submit();
	}
	else
	{
		if(! validarFormEsp)
		{
			alert('Se Debe introducir un número de teléfono o un móvil\ny el nombre y los apellidos');
		}
		else
		{
			alert('Valide al valor de los campos resaltados');
		}
		
	}
	
}

function cargaComboEmpresas(nuevaEmpresa)
{   
	$.ajax({
	  url: "../CargaComboEmpresaServlet?valSel=" + nuevaEmpresa,
	  success: function(data) 
	  {
	   $(document).ready(function() {  
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
		<%if(aluVO.getTipDocAlu() <= 0){%>
			cargaComboTipDoc(0);
		<%}else{%>    
			cargaComboTipDoc("<%=aluVO.getTipDocAlu()%>");
		<%}%>  
		
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
		  
		 <%if("".equals(aluVO.getCodProvAlu().trim())){%>
			cargaComboProv("37"); 
		<%}else{%>    
			cargaComboProv("<%=aluVO.getCodProvAlu().trim()%>"); 
		<%}%> 
		  
	});
}

function cargaPaginaAltaEmpresa()
{
	
	frmDatosPersonales.action = "../CargaAltaEmpresasServlet?fichaAlumno=1";
	frmDatosPersonales.submit();
	
}

function confirmarBaja()
{
	var confEliAlu = false;
	confEluAlu = confirm("¿Está seguro que desea eliminar el alumno?");
	
	if(confEluAlu)
	{
		frmDatosPersonales.action="../BorrarAlumnoServlet?codInt=<%=aluVO.getIdAlu()%>";
		frmDatosPersonales.submit();
	}
	
}




</script>

<%if(codErr == -1){%>
<script>
    alert("El DNI introducido está duplicado");
</script>
<%}%>
<%if(codErr == -2){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>
<%if(codErrBor == -3){%>
<script>
    alert("No se puede dar de baja el alumno\n porque tiene matrículas asociadas");
</script>
<%}%>
<%if(codErrBor == -4){%>
<script>
    alert("No se puede dar de baja el alumno\n porque tiene clases individuales asociadas");
</script>
<%}%>
<%if(codErrBor == -1){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>

<form action="./datPerFichaAlumno.jsp" method="post" name="frmDatosPersonales" target="_self" id="frmDatosPersonales" class="colorFondoPrincipalPestana">
<input type="hidden" name="hidNombre" id="hidNombre" />
<input type="hidden" name="hidApe"    id="hidApe" />
<input type="hidden" name="hidFecNac" id="hidFecNac" />
<input name="hidCodInt" type="hidden" id="hidCodInt" value="<%=aluVO.getIdAlu()%>" />

  <table style="width:90%" border="0" align="center">
    <tr>
      <td style="width:14%" class="tdDef" scope="col">Tipo Documento</td>
      <td style="width:19%" class="tdDef" scope="col"><span id="spryselect1">
        <select name="selValTipDoc" id="selValTipDoc"></select>
        <script>frmDatosPersonales.selValTipDoc.value="<%=aluVO.getTipDocAlu()%>";</script>
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
      <td height="41" class="tdDef">Nivel Formativo</td>
      <td class="tdDef"><span id="selValNivFor">
        <select name="selNivFor" id="selNivFor"></select>
      <script>frmDatosPersonales.selNivFor.value="<%=aluVO.getNivForm()%>";</script>
        <span class="selectRequiredMsg">*</span></span></td>
      <td class="tdDef">Empresa</td>
      <td colspan="2" class="tdDef"><span id="selValEmp" style="width:300px">
        <select name="selEmpresa" id="selEmpresa"></select>
        <script>frmDatosPersonales.selEmpresa.value="<%=aluVO.getEmpAlu()%>";</script>
        <span class="selectRequiredMsg">*</span></span></td>
      <td style="width:10%" class="tdDef"><input class="cellBtnSub" type="button" onclick= "window.parent.cargaPaginaAltaEmpresa();" value="Añadir" /></td>
    </tr>
</table>
  <br />
  <table style="width:90%" border="0" align="center" class="colorFondoBlancoTablaPest" cellspacing="0">
    <tr>
      <th class="tdDef" scope="col">Datos de Contacto</th>
      <th colspan="3" class="colorFondoPrincipalPestana" scope="col">&nbsp;</th>
    </tr>
    <tr>
      <td style="width:17%" height="28" class="colorFondoBlanco">Fijo</td>
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
        <script>frmDatosPersonales.selProv.value="<%=aluVO.getCodProvAlu()%>";</script>
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
    </tr>
  </table>
  <br />
  <table style="width:90%" border="0" align="center">
    <tr>
        <td style="width:14%" class="tdDef" scope="col">Colegio</td>
          <td style="width:48%" class="tdDef" scope="col"><span id="valColegio">
            <input name="txtColegio" type="text" id="txtColegio" value="<%=aluVO.getColegio() %>" size="75" maxlength="100" />
</span></td>
          <td style="width:11%" class="tdDef" scope="col">Curso</td>
          <td style="width:27%" class="tdDef" scope="col"><span id="valCurso">
            <input name="txtCurso" type="text" id="txtCurso" value="<%=aluVO.getCurso() %>" size="25" maxlength="35" />
</span></td>
    </tr>
  </table>
  <br />
  <table style="width:90%" align="center" border="0">
    <tr class="tdDef">
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
      <th scope="col"><input name="btnEditar" type="button" class="cellBtnSub" id="btnEditar" value="Editar" onclick="validaForm();" />  
          <input name="btnBorrar" type="button" class="cellBtnSub" id="btnBorrar" value="Dar de baja" onclick="confirmarBaja();"/></th>
    </tr>
  </table>
  <br />
  <table style="width:90%" align="center" border="0">
    <tr> </tr>
  </table>
</form>

<script type="text/javascript">
var sprytextfield01 = new Spry.Widget.ValidationTextField("txtValNumDoc", "none", {isRequired:false});
var spryselect01    = new Spry.Widget.ValidationSelect("spryselect1", {isRequired:false});
var sprytextarea01  = new Spry.Widget.ValidationTextarea("txtValObs", {isRequired:false});
var sprytextfield02 = new Spry.Widget.ValidationTextField("txtValLoc", "none", {isRequired:false});
var spryselect02    = new Spry.Widget.ValidationSelect("selValProv", {isRequired:false});
var sprytextfield03 = new Spry.Widget.ValidationTextField("txtValDirecc", "none", {isRequired:false});
var sprytextfield04 = new Spry.Widget.ValidationTextField("txtValCodPos", "zip_code", {isRequired:false, format:"zip_custom", pattern:"00000"});
var sprytextfield05 = new Spry.Widget.ValidationTextField("txtValMail", "email", {isRequired:false});
var sprytextfield06 = new Spry.Widget.ValidationTextField("txtValMovil", "phone_number", {format:"phone_custom", pattern:"600000000", isRequired:false});
var sprytextfield07 = new Spry.Widget.ValidationTextField("txtValTelf", "phone_number", {format:"phone_custom", pattern:"000000000", isRequired:false});
var spryselect03    = new Spry.Widget.ValidationSelect("selValEmp", {isRequired:false});
var spryselect04    = new Spry.Widget.ValidationSelect("selValNivFor", {isRequired:false});
var sprytextfield08 = new Spry.Widget.ValidationTextField("valResponsable", "none", {isRequired:false});
var sprytextfield09 = new Spry.Widget.ValidationTextField("valColegio", "none", {isRequired:false});
var sprytextfield10 = new Spry.Widget.ValidationTextField("valCurso", "none", {isRequired:false})
</script>
</body>
</html>