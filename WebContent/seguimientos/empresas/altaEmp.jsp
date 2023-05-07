<%@page import="es.jahernandez.gestion.ListaCodPostGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>

<%@ page import = "es.jahernandez.accesodatos.*"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.gestion.ActividadesGestion"%>

<html lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Alta Empresa</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
//Carga de datos empresa
EmpresasVO empVO          = new EmpresasVO();

Vector     datProv        = ListaCodPostGestion.devolverDatProv(); //Devuelve los datos de carga de combo de Provincias
Vector     datActi        = ActividadesGestion.devolverDatActividades(); //Devuelve los datos de carga de combo de actividades

//Contro de errores de alta
int        errCodAlta     = 0;

//Actualiza valores por defecto de los combos de provincia
String     valDefCP       = "";
String     valDefCPCom    = "";
String     urlDest        = "../AltaNuevaEmpresaServlet?urlProc=3";

if(request.getParameter("errorCode") != null)
{
    errCodAlta = new Integer(request.getParameter("errorCode")).intValue();
}


if(session.getAttribute("nuevaEmpresaErr") != null)
{
    empVO = (EmpresasVO) session.getAttribute("nuevaEmpresaErr");
    session.removeAttribute("nuevaEmpresaErr");
}

if(! empVO.getCodPostal().trim().equals(""))
{
    valDefCP = empVO.getCodPostal().substring(0, 2);
}
else
{
    valDefCP = "37";
}

if(! empVO.getCodPosCom().trim().equals(""))
{
    valDefCPCom = empVO.getCodPosCom().substring(0, 2);
}
else
{
    valDefCPCom = "37";
}


//Se compruba si se llama desde la ficha de alumnos
if(request.getParameter("fichaAlumno") != null)
{
	if(request.getParameter("fichaAlumno").equals("1"))
	{
		urlDest = "../AltaNuevaEmpresaServlet?fichaAlumno=1";
	}
}

%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
    
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

</head>

<body class="fondoFormularios" onload="cargarCombos();">


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
			yearRange:       "c-100:c+0",
			showButtonPanel: true
        });
	});
</script>


<script language="JavaScript" type="text/JavaScript">
function validarForm()
{
	var validarCampos  = true;
	
	validarCampos = validarCampos & 
				   sprytextfield01.validate() & sprytextfield02.validate() & sprytextfield03.validate() & sprytextfield04.validate() &
				   sprytextfield05.validate() & sprytextfield06.validate() & sprytextfield07.validate() & sprytextfield08.validate() &
				   sprytextfield09.validate() & sprytextfield10.validate() & sprytextfield11.validate() & sprytextfield12.validate() &
				   sprytextfield13.validate() & sprytextfield14.validate() & sprytextfield15.validate() & sprytextfield16.validate() &
				   sprytextfield17.validate() & sprytextfield18.validate() & sprytextfield19.validate() & sprytextfield20.validate() &
				   sprytextfield21.validate() & sprytextfield22.validate() & sprytextfield23.validate() & sprytextfield24.validate() &
				   spryselect01.validate()    & spryselect02.validate(); 
				   
				   
		   
				   	
	if(validarCampos)
	{	
		frmAltaEmpresa.action = "<%=urlDest%>";
		frmAltaEmpresa.submit();
	}
	else
	{		
		alert('Valide al valor de los campos resaltados');				
	}
}

function volcarDatosComerciales()
{
    frmAltaEmpresa.txtNomCom.value    = frmAltaEmpresa.txtRazonSocial.value;
    frmAltaEmpresa.txtDirCom.value    = frmAltaEmpresa.txtDirEmp.value;
    frmAltaEmpresa.txtLocCom.value    = frmAltaEmpresa.txtLocEmp.value;
    frmAltaEmpresa.txtCodPosCom.value = frmAltaEmpresa.txtCodPosEmp.value;
    frmAltaEmpresa.selProCom.value    = frmAltaEmpresa.selProEmp.value;  
}

function cargaComboProv()
{   
	$.ajax({
	  url: "../CargaComboProvinciasServlet",
	  success: function(data) {
		$("#selProCom").html(data);
		$("#selProEmp").html(data);
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando provincias");
	  }
	});
}

function cargaComboActividades()
{   
	$.ajax({
	  url: "../CargaComboActividadesServlet",
	  success: function(data) {
		$("#selActEmp").html(data);
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando actividades");
	  }
	});
}

function cargarCombos()
{
	cargaComboProv();
	cargaComboActividades();
}
</script>

<%if(errCodAlta == -1){%>
<script>
    alert("Se produjo un error de acceso a la base de datos");
</script>
<%}%>
<form action="" method="post" name="frmAltaEmpresa" target="_self" id="frmAltaEmpresa">
 <table style="width:100%" border="0">
  <tr class="thDef">
    <th height="50" colspan="3" scope="col">Alta Empresa</th>
  </tr>
</table>
<table style="width:100%" class="tablaApartadosEmpresa" >
	<tr class="tdDef">
	  <td colspan="6" class="tdDef"><strong>Datos Fiscales</strong></td>
    </tr>
	<tr class="tdDef">
	  <td>Razón Social</td>
	  <td><span id="txtValRazSoc">
	    <input name="txtRazonSocial" type="text" id="txtRazonSocial" value="<%=empVO.getNombreEmpresa()%>" size="75" maxlength="50" />
      <span class="textfieldRequiredMsg">*</span></span></td>
	  <td>CIF</td>
	  <td style="width:12%"><span id="txtValCIF">
	    <input name="txtCIF" type="text" id="txtCIF" value="<%=empVO.getCifEmpresa()%>" size="20" maxlength="10" />
</span></td>
	  <td style="width:6%">CNAE</td>
	  <td style="width:22%"><span id="txtValCNAE">
	    <input name="txtCNAE" type="text" id="txtCNAE" <%if(empVO.getCnae()>0){%>value="<%=empVO.getCnae()%><%}%>" size="15" maxlength="10" />
</span></td>
    </tr>
	<tr class="tdDef">
    	<td style="width:11%">Dirección          
        </td>
        <td style="width:41%"><span id="txtValDirEmp">
          <input name="txtDirEmp" type="text" id="txtDirEmp" value="<%=empVO.getDirEmpresa()%>" size="90" maxlength="150" />
</span>                                        
      </td>
        <td style="width:8%">Código Postal
        </td>
        <td colspan="3"><span id="txtValCodPosEmp">
        <input name="txtCodPosEmp" type="text" id="txtCodPosEmp" value="<%=empVO.getCodPostal()%>" size="10" maxlength="5" />
<span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
    <tr class="tdDef">
    	<td>Localidad                                        
        </td>
        <td><span id="txtValLocEmp">
        <input name="txtLocEmp" type="text" id="txtLocEmp" value="<%=empVO.getPobEmpresa()%>" size="90" maxlength="150" />
</span>
      </td>
        <td>Provincia</td>
        <td colspan="3"><span id="selValProEmp">
          <select name="selProEmp" id="selProEmp"></select>
</span>
      </td>
    </tr>
    <tr class="tdDef">
    	<td>Cargo        
        </td>
        <td colspan="5"><span id="txtValCarEmp">
        <input name="txtCarEmp" type="text" id="txtCarEmp" value="<%=empVO.getCar1()%>" size="90" maxlength="50" />
</span>                                        
      </td>
    </tr>
    <tr class="tdDef">  
        <td>Representante
        </td>
        <td colspan="5"><span id="txtValRepEmp">
        <input name="txtRepEmp" type="text" id="txtRepEmp" value="<%=empVO.getResEmpresa()%>" size="90" maxlength="50" />
        </span>
        </td>
	</tr>
    <tr class="tdDef">
        <td>Actividades
        </td>
        <td colspan="5"><span id="selValActEmp">
        <select name="selActEmp" id="selActEmp"></select>
        </span> 
        </td>
    </tr>
</table>
<br>
<table style="width:100%" class="tablaApartadosEmpresa" >
	<tr class="tdDef">
	  <td colspan="6" class="tdDef"><strong>Datos Comerciales</strong></td>
    </tr>
	<tr class="tdDef">
	  <td>Nombre comercial</td>
	  <td colspan="5"><span id="txtValRazSoc"><span id="txtValNomCom">
	    <input name="txtNomCom" type="text" id="txtNomCom" value="<%=empVO.getNomComercial()%>" size="75" maxlength="50" />
</span><span class="textfieldRequiredMsg">A value is required.</span></span></td>
    </tr>
	<tr class="tdDef">
    	<td style="width:11%">Dirección          
        </td>
        <td colspan="2"><span id="txtValDirCom">
        <input name="txtDirCom" type="text" id="txtDirCom" value="<%=empVO.getDirCom()%>" size="90" maxlength="150" />
      </span></td>
        <td style="width:8%">Código Postal
        </td>
        <td colspan="2"><span id="txtValCodPosEmp"><span class="textfieldInvalidFormatMsg">Invalid format.</span></span>                 
          <span id="txtValCodPosCom">
          <input name="txtCodPosCom" type="text" id="txtCodPosCom" value="<%=empVO.getCodPosCom()%>" size="10" maxlength="5" />
        <span class="textfieldInvalidFormatMsg">Invalid format.</span></span>        </td>
    </tr>
    <tr class="tdDef">
    	<td>Localidad                                        
        </td>
        <td colspan="2"><span id="txtValLocCom">
        <input name="txtLocCom" type="text" id="txtLocCom" value="<%=empVO.getPobCom()%>" size="90" maxlength="150" />
      </span></td>
        <td>Provincia</td>
        <td colspan="2"><span id="selValProEmp">
          <select name="selProCom" id="selProCom"></select>
</span>
      </td>
    </tr>
    <tr class="tdDef">
      <td>Teléfono</td>
      <td colspan="2"><span id="txtValTel">
      <input name="txtTelf" type="text" id="txtTelf" value="<%=empVO.getTelEmpresa()%>" size="15" maxlength="9" />
<span class="textfieldInvalidFormatMsg">Invalid format.</span></span></td>
      <td>Fax</td>
      <td colspan="2"><span id="txtValFax">
      <input name="txtTelf2" type="text" id="txtTelf2" value="<%=empVO.getFaxEmpresa()%>" size="15" maxlength="9" />
      <span class="textfieldInvalidFormatMsg">Invalid format.</span></span></td>
    </tr>
    <tr class="tdDef">
    	<td>Correo Electrónico</td>
        <td colspan="5"><span id="txtValMail">
        <input name="txtEMail" type="text" id="txtEMail" value="<%=empVO.getMailEmpresa()%>" size="90" maxlength="150" />
<span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
    <tr class="tdDef">  
        <td>Responsable
        </td>
        <td colspan="5"><span id="txtValResCom">
          <input name="txtRepCom" type="text" id="txtRepCom" value="<%=empVO.getResEmp2()%>" size="90" maxlength="100" />
</span></td>
	</tr>
    <tr class="tdDef">
        <td colspan="4">&nbsp;</td>
        <td style="width:17%"><input type="checkbox" name="chkDatAct" id="chkDatAct" value="true" <%if(empVO.isDatAct()){%>checked="checked"<%}%> />
        Datos Actualizados</td>
        <td style="width:23%" class="cellBtnSub"><input name="btnVolcarDatosFiscales" type="button" class="cellBtnSub" id="btnVolcarDatosFiscales" value="Volcar Datos  Fiscales" onclick="volcarDatosComerciales()";/></td>
    </tr>
</table>
<br>
<table style="width:100%" class="tdDef">
	<tr>
    	<td style="width:353">Nº Empleados<span id="txtValNumEmp">
        <input name="txtNumEmp" type="text" id="txtNumEmp" <%if(empVO.getNumEmp()>0){%>value="<%=empVO.getNumEmp()%><%}%>" size="15" maxlength="9" />
    <span class="textfieldInvalidFormatMsg">*</span></span></td>
        <td style="width:297"><input type="checkbox" name="chkAdCon" id="chkAdCon" value="true" <%if(empVO.isConvenioAd()){%>checked="checked"<%}%>/>
          Adherido a convenio</td>
        <td style="width:398">Fecha Convenio<span id="txtValFecNac"><span id="txtValFecCon">
          <label>
            <input class="claseForFecha" name="txtFecCon" type="text" id="txtFecCon" size="15" maxlength="10" <%if(empVO.getFecCon().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecCon())%>"<%}%> onchange="document.getElementById('hidFecCon').value = document.getElementById('txtFecCon').value;"/>
          <span class="textfieldInvalidFormatMsg">*</span></label>
</span><span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>
<input type="hidden" name="hidFecCon" id="hidFecCon" <%if(empVO.getFecCon().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecCon())%>"<%}%>/>
</td>
        <td style="width:310">Importa/Exporta
          <select name="selImpExp" id="selImpExp" tabindex="127">
          <option selected="selected" value="----">----</option>
          <option value="IMPORTA">IMPORTA</option>
          <option value="EXPORTA">EXPORTA</option>
          <option value="IMPORTA Y EXPORTA">IMPORTA Y EXPORTA</option>
        </select></td>
          <script>frmAltaEmpresa.selImpExp.value="<%=empVO.getImpExp()%>";</script>
    </tr>
    </table>
    <table style="width:100%" class="tdDef">
    <tr>
       <td style="width:26%"><input type="checkbox" name="chkesCliente" id="chkesCliente" value="true" <%if(empVO.isEsCliente()){%>checked="checked"<%}%>/>
         Cliente</td>
       <td style="width:22%">Cuota<span id="txtValCuota">
        <input name="txtCuota" type="text" id="txtCuota" <%if(empVO.getCuota()>0){%>value="<%=empVO.getCuota()%><%}%>" size="15" maxlength="9" />
      <span class="textfieldInvalidFormatMsg">*</span></span></td>
       <td style="width:52%">Volumen de Negocio<span id="txtValVolNeg">
        <input name="txtVolNeg" type="text" id="txtVolNeg" value="<%=empVO.getVolNeg()%>" size="75" maxlength="50" />
       </span>       </td>
      </tr>
</table>
<br>
<table style="width:100%" border="0">
  <tr class="tdDef">
    <td style="width:74%"><table style="width:100%" class="tablaApartadosEmpresa">
  <tr>
    <td colspan="5"><strong>LOPD</strong></td>
    </tr>
  <tr>
    <td style="width:25%"><input type="checkbox" name="chkAutCesDat" id="chkAutCesDat" value="true" <%if(empVO.isAutCesDat()){%>checked="checked"<%}%>/>
    Autoriza Cesión Datos</td>
    <td style="width:15%"><input type="checkbox" name="chkAcc" id="chkAcc" value="true" <%if(empVO.isAccArco()){%>checked="checked"<%}%>/>
      Acceso</td>
    <td style="width:17%"><span id="txtValFecAcc">
    <label>
      <input class="claseForFecha" name="txtFecAcc" type="text" id="txtFecAcc" size="15" maxlength="10" <%if(empVO.getFecAccArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecAccArc())%>"<%}%> onchange="document.getElementById('hidFecAcc').value = document.getElementById('txtFecAcc').value;"/>
      <span class="textfieldInvalidFormatMsg">*</span></label>
    </span>
      <input type="hidden" name="hidFecAcc" id="hidFecAcc" <%if(empVO.getFecAccArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecAccArc())%>"<%}%>/></td>
    <td style="width:22%"><input type="checkbox" name="chkCancel" id="chkCancel" value="true" <%if(empVO.isCanArco()){%>checked="checked"<%}%>/>
      Cancelación</td>
    <td style="width:21%"><span id="txtValFecCan">
    <label>
      <input class="claseForFecha" name="txtFecCan" type="text" id="txtFecCan" size="15" maxlength="10" <%if(empVO.getFecCanArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecCanArc())%>"<%}%> onchange="document.getElementById('hidFecCan').value = document.getElementById('txtFecCan').value;"/>
      <span class="textfieldInvalidFormatMsg">*</span></label>
    </span>
      <input type="hidden" name="hidFecCan" id="hidFecCan"  <%if(empVO.getFecCanArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecCanArc())%>"<%}%>/></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="checkbox" name="chkRec" id="chkRec" value="true" <%if(empVO.isRecArco()){%>checked="checked"<%}%>/>
      Rectificación</td>
    <td><span id="txtValFecRec">
    <label>
      <input class="claseForFecha" name="txtFecRec" type="text" id="txtFecRec" size="15" maxlength="10" <%if(empVO.getFecRecArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecRecArc())%>"<%}%> onchange="document.getElementById('hidFecRec').value = document.getElementById('txtFecRec').value;"/>
      <span class="textfieldInvalidFormatMsg">*</span></label>
    </span>
      <input type="hidden" name="hidFecRec" id="hidFecRec" <%if(empVO.getFecRecArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecRecArc())%>"<%}%>/></td>
    <td><input type="checkbox" name="chkOposic" id="chkOposic" value="true" <%if(empVO.isOpoArco()){%>checked="checked"<%}%>/>
      Oposición</td>
    <td><span id="txtValFecOpo">
    <label>
      <input class="claseForFecha" name="txtFecOpo" type="text" id="txtFecOpo" size="15" maxlength="10" <%if(empVO.getFecOpoArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecOpoArc())%>"<%}%> onchange="document.getElementById('hidFecOpo').value = document.getElementById('txtFecOpo').value;"/>
      <span class="textfieldInvalidFormatMsg">*</span></label>
    </span>
      <input type="hidden" name="hidFecOpo" id="hidFecOpo" <%if(empVO.getFecOpoArc().after(new GregorianCalendar(1900,0,1).getTime())){%>value="<%=new SimpleDateFormat("dd/MM/yyyy").format(empVO.getFecOpoArc())%>"<%}%>/></td>
  </tr>
    </table>

</td>
    <td style="width:26%"><table style="width:100%" border="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="cellBtnSub"><input name="btnAltaEmp" type="button" class="cellBtnSub" id="btnAltaEmp" value="Guardar" onclick="validarForm();" /></td>
  </tr>
</table>
</td>
  </tr>
</table>


 
</form>
<script type="text/javascript">
var sprytextfield01 = new Spry.Widget.ValidationTextField("txtValRazSoc");
var sprytextfield02 = new Spry.Widget.ValidationTextField("txtValCIF", "none", {isRequired:false});
var sprytextfield03 = new Spry.Widget.ValidationTextField("txtValCNAE", "none", {isRequired:false});
var sprytextfield04 = new Spry.Widget.ValidationTextField("txtValDirEmp", "none", {isRequired:false});
var sprytextfield05 = new Spry.Widget.ValidationTextField("txtValCodPosEmp", "zip_code", {format:"zip_custom", pattern:"00000", isRequired:false});
var sprytextfield06 = new Spry.Widget.ValidationTextField("txtValLocEmp", "none", {isRequired:false});
var spryselect01    = new Spry.Widget.ValidationSelect("selValProEmp", {isRequired:false});
var sprytextfield07 = new Spry.Widget.ValidationTextField("txtValCarEmp", "none", {isRequired:false});
var sprytextfield08 = new Spry.Widget.ValidationTextField("txtValRepEmp", "none", {isRequired:false});
var spryselect02    = new Spry.Widget.ValidationSelect("selValActEmp", {isRequired:false});
var sprytextfield09 = new Spry.Widget.ValidationTextField("txtValTel", "phone_number", {format:"phone_custom", pattern:"900000000", isRequired:false});
var sprytextfield10 = new Spry.Widget.ValidationTextField("txtValFax", "phone_number", {format:"phone_custom", pattern:"900000000", isRequired:false});
var sprytextfield11 = new Spry.Widget.ValidationTextField("txtValNomCom", "none", {isRequired:false});
var sprytextfield12 = new Spry.Widget.ValidationTextField("txtValCodPosCom", "zip_code", {format:"zip_custom", pattern:"00000", isRequired:false});
var sprytextfield13 = new Spry.Widget.ValidationTextField("txtValDirCom", "none", {isRequired:false});
var sprytextfield14 = new Spry.Widget.ValidationTextField("txtValLocCom", "none", {isRequired:false});
var sprytextfield15 = new Spry.Widget.ValidationTextField("txtValMail", "email", {isRequired:false});
var sprytextfield16 = new Spry.Widget.ValidationTextField("txtValResCom", "none", {isRequired:false});
var sprytextfield17 = new Spry.Widget.ValidationTextField("txtValFecCon", "date", {isRequired:false, format:"dd/mm/yyyy", hint:"dd/mm/yyyy"});
var sprytextfield18 = new Spry.Widget.ValidationTextField("txtValNumEmp", "integer", {isRequired:false});
var sprytextfield19 = new Spry.Widget.ValidationTextField("txtValCuota", "integer", {isRequired:false});
var sprytextfield20 = new Spry.Widget.ValidationTextField("txtValVolNeg", "none", {isRequired:false});
var sprytextfield21 = new Spry.Widget.ValidationTextField("txtValFecAcc", "date", {isRequired:false, format:"dd/mm/yyyy", hint:"dd/mm/yyyy"});
var sprytextfield22 = new Spry.Widget.ValidationTextField("txtValFecRec", "date", {isRequired:false, format:"dd/mm/yyyy", hint:"dd/mm/yyyy"});
var sprytextfield23 = new Spry.Widget.ValidationTextField("txtValFecCan", "date", {isRequired:false, format:"dd/mm/yyyy", hint:"dd/mm/yyyy"});
var sprytextfield24 = new Spry.Widget.ValidationTextField("txtValFecOpo", "date", {isRequired:false, format:"dd/mm/yyyy", hint:"dd/mm/yyyy"});
</script>
</body>
</html>
