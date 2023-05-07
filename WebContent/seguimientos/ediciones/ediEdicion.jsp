<%@page import="es.jahernandez.gestion.NivelesGestion"%>
<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.CentrosGestion"%>
<%@page import="es.jahernandez.gestion.AulasGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<%@ page import = "java.text.DecimalFormat"%>
<%@ page import = "java.text.SimpleDateFormat"%>
<%@ page import = "java.util.Vector"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir Curso</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
    EdicionesVO   ediVO      = null;
    CursosVO      curVO      = null;
    NivelesVO     nivVO      = null;
    CentrosVO     cenVO      = null;
    AulasVO       aulVO      = null;
    
    Vector        vecNivCurs = null;
    Vector        vecCen     = null;   
    Vector        vecAul     = null;
    
    boolean       msgErr     = false;
	
    DecimalFormat df         = new DecimalFormat ("0.00");
    
    int           indInf     = 0;
        
    if(request.getParameter("codEdi")!= null)
    {
        ediVO = EdicionesGestion.devolverDatosEdi(request.getParameter("codEdi").trim());
	curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());
                   
        vecNivCurs = NivelesGestion.devolverNivCur(curVO.getIdCur());
        
        if(ediVO.getCodCen() == 0)
        {
            vecCen = CentrosGestion.datComCentros(); 
        }
        else
        {
            vecCen = new Vector();
            cenVO = CentrosGestion.datCentro(ediVO.getCodCen());
            vecCen.addElement(cenVO);
        }
        
        vecAul = AulasGestion.devolverAulasCentro(ediVO.getCodCen());            
    }
    
    if(request.getParameter("valInfEdi") != null)
    {
            indInf = new Integer(request.getParameter("valInfEdi")).intValue();
    }
    
    if(request.getParameter("msgError") != null)
    {
        msgErr = true;
    }
    
%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='../js/validaGlobal.js'></script>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
</head>

<%if(msgErr){%>
<script>
    alert("Se produjo un error en el alta de la edición");
</script>
<%}%>

<script>
//Funciones jquery
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
			yearRange:       "c-5:c+10",
			showButtonPanel: true,
			onClose: function( selectedDate ) {
						$( "#txtFecInicio" ).datepicker( "option", "maxDate", $( "#txtFecFin" ).datepicker( "getDate"));
						$( "#txtFecFin" ).datepicker( "option", "minDate", $( "#txtFecInicio" ).datepicker( "getDate")); 

      		}

    });


});
</script>

<script>
function validaForm()
{
	var correcto = true;
	
	if(Trim(document.frmDatEdicion.txtFecInicio.value) == "" || 
	   Trim(document.frmDatEdicion.txtFecFin.value)    == "" )
	{
		correcto = false;
	} 
	
	
	<%if(curVO == null){%>
		correcto =  correcto & spryselect1.validate();
	<%}%>
	
	correcto =  correcto & spryselect2.validate()    & spryselect3.validate()    & spryselect4.validate()    &
						   spryselect5.validate()    & spryselect6.validate()    & spryselect7.validate()    &  
						   spryselect8.validate()    & sprytextfield1.validate() & sprytextfield2.validate() & 
						   sprytextfield3.validate() & sprytextfield4.validate() & sprytextfield5.validate() & 
						   sprytextfield6.validate() & sprytextfield7.validate() ;
	if(correcto)
	{
		document.frmDatEdicion.action = "../EdiEdicionServlet";	
		document.frmDatEdicion.submit();
	}
	else
	{
		alert('Valide los campos resaltados y las fechas');
	}					   
	
}

function confirmarVolver() 
{
	if ( confirm("¿Desea cancelar la edición?"))
	{
		window.open("./listEdiCurso.jsp?codCur=<%=curVO.getIdCur()%>","_self");
	}
}

function cargarComboNiveles(idNivel)
{   
	
	var idCodCur = "<%=curVO.getIdCur()%>";
	
	$.ajax({
	  url: "../CargaComboNivelesServlet?codCur=" + idCodCur + "&muestraSelec=s&valSel=" + idNivel,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstNivel").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los niveles");
	  }
	});
}

function cargarComboCentrosCurso(idCentro)
{
	$.ajax({
	  url: "../CargaComboCentrosServlet?valSel=" + idCentro,
	  success: function(data) {
		$(document).ready(function() {
			$("#datCentro").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los centros");
	  }
	});
}

function cargarComboAulas(idCentro,idAula)
{
	$.ajax({
	  url: "../CargarComboAulaServlet?codCentro=" + idCentro + "&muestraSelec=s&valSel=" + idAula,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstAula").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las aulas");
	  }
	});
}

function cambiaCheckPago(origen)
{
	if(origen==0)
	{
		frmDatEdicion.chkAplazado.checked = !frmDatEdicion.chkContado.checked;
	}
	else
	{
		frmDatEdicion.chkContado.checked = ! frmDatEdicion.chkAplazado.checked;
	}
	
	if(frmDatEdicion.chkAplazado.checked)
	{
		document.getElementById("divMeses").style.visibility = "visible";
	}
	else
	{
		document.getElementById("divMeses").style.visibility = "hidden";
	}	
}

function cargarCombosProfesores(idProf)
{   
	$.ajax({
	  url: "../CargaComboProfesoresServlet?valSel=" + idProf,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstProf").html(data);
			if($("#lstNivAcc").val()==2)
			{
				document.frmEditUser.lstProf.style.visibility = "visible";
			}
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los profesores");
	  }
	});
}

function cargarCombos()
{
	cargarComboCentrosCurso("<%=ediVO.getCodCen()%>");
	cargarComboNiveles("<%=ediVO.getIdNiv()%>");
	cargarComboAulas("<%=ediVO.getCodCen()%>" , "<%=ediVO.getIdAula()%>" );
	cargarCombosProfesores("<%=ediVO.getIdProf()%>");
}

</script>


<body class="colorFondoPrincipalPestana" onload="cargarCombos()">
<form  method="post" name="frmDatEdicion" id="frmDatEdicion">
<table style="width:90%" border="0" class="tdDef">
  <tr>
    <td colspan="9" class="thDef">      <input name="codEdi" type="hidden" id="codEdi" value="<%=ediVO.getIdEdi()%>" />
      Datos Edici&oacute;n</td>
  </tr>
  <tr>
    <td style="width:10%" class="tdDef">Curso</td>
    <td colspan="3" class="tdDef">
      <input name="txtCurso" type="text" id="txtCurso" tabindex="1" size="55" value="<%=curVO.getNomCur()%>" readonly="readonly" />
      <input name="datCodCur" type="hidden" id="datCodCur" value="<%=ediVO.getIdCur()%>" /></td>
    <td colspan="2" class="tdDef">Nivel</td>
    <td colspan="3"><span id="valNivel">
      <select name="lstNivel" size="1" id="lstNivel" tabindex="3"></select>
      <script>frmDatEdicion.lstNivel.value="<%=ediVO.getIdNiv()%>";</script>
      <span class="selectInvalidMsg">*</span></span></td>
  </tr>
  <tr>
    <td height="25" class="tdDef">Centro</td>
    <td colspan="3" class="tdDef"><span id="valCentro">
      <select name="datCentro" size="1" id="datCentro" tabindex="4" onchange="cargarComboAulas($('#datCentro').val());"></select>
      <span class="selectInvalidMsg">*</span></span>   
    </td> 
    <td colspan="2" class="tdDef">Aula</td>
    <td colspan="3"><span id="valAula">
      <select name="lstAula" size="1" id="lstAula" tabindex="5"></select>
      <script>frmDatEdicion.lstAula.value="<%=ediVO.getIdAula()%>";</script>
      <span class="selectInvalidMsg">*</span></span>
    </td>
  </tr>
   <tr>
    <td class="tdDef">Descripci&oacute;n</td>
    <td colspan="8"><span id="valDescripcion">
      <input name="txtDescripcion" type="text" id="txtDescripcion" <%if(ediVO != null){%> value="<%=ediVO.getDescripcion()%>" <%}%> tabindex="6" size="100" maxlength="100" />
      <span class="textfieldRequiredMsg">Se necesita un valor.</span></span></td>
    </tr>
    <tr>
     <td class="tdDef">Tutor</td>
     <td colspan="8"><select name="lstProf" id="lstProf">
    </select></td>
   </tr>
   <tr>
    <td class="tdDef">Fecha Incio</td>
    <td colspan="3" class="tdDef">
        <input class="claseForFecha" type="text" tabindex="6" size="15" name="txtFecInicio" id="txtFecInicio" <%if(ediVO != null){%> value="<%=new SimpleDateFormat ("dd-MM-yyyy").format(ediVO.getFecIn())%>" <%}%> />
   </td>
    <td colspan="2" class="tdDef">Fecha Fin</td>
    <td colspan="3">
    <input class="claseForFecha" name="txtFecFin" type="text" id="txtFecFin" tabindex="7" size="15" maxlength="10" <%if(ediVO != null){%> value="<%=new SimpleDateFormat("dd-MM-yyyy").format(ediVO.getFecFi())%>"<%}%>/>
   </td>
  </tr>
  <tr>
    <td rowspan="2" class="tdDef">Plazas</td>
    <td colspan="3" rowspan="2"><span id="valPlazas">
      <input name="txtPlazas" type="text" id="txtPlazas" tabindex="8" size="8" maxlength="4" <%if(ediVO != null){%> value="<%=ediVO.getNumPla()%>" <%}%>/>
      <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td style="width:5%" rowspan="2" class="tdDef">Horas</td>
    <td style="width:9%" class="tdDef">Presenciales</td>
    <td colspan="3" class="tdDef"><span id="valHorPres">
    <input name="txtHorPresen" type="text" id="txtHorPresen" tabindex="9" size="8" maxlength="4"  <%if(ediVO != null){%> value="<%=ediVO.getNumHor()%>"<%}%> />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
  <tr>
    <td class="tdDef">Distancia</td>
    <td class="tdDef"><span id="valHorDis">
      <input name="txtHorDist" type="text" id="txtHorDist" tabindex="11" size="8" maxlength="4" <%if(ediVO != null){%> value="<%=ediVO.getHorDis()%>" <%}%>/>
      <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td class="tdDef">Telef&oacute;nicas</td>
    <td class="tdDef"><span id="valHorTel">
      <input name="txtTelef" type="text" id="txtTelef" tabindex="12" size="8" maxlength="4" <%if(ediVO != null){%> value="<%=ediVO.getHorTelef()%>" <%}%> />
      <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
  </tr>
  <tr>
    <td class="tdDef">Hora Inicio</td>
    <td colspan="3"><span id="valHorIni">
      <select name="lstHorInicio" id="lstHorInicio" tabindex="13">
        <option value="-1" selected="selected">--</option>
        <option value="0">00</option>
        <option value="1">01</option>
        <option value="2">02</option>
        <option value="3">03</option>
        <option value="4">04</option>
        <option value="5">05</option>
        <option value="6">06</option>
        <option value="7">07</option>
        <option value="8">08</option>
        <option value="9">09</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
        <option value="15">15</option>
        <option value="16">16</option>
        <option value="17">17</option>
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
        <option value="21">21</option>
        <option value="22">22</option>
        <option value="23">23</option>
        </select>
      <%if(ediVO != null){%><script>$("#lstHorInicio").val("<%=ediVO.getHorIn()%>");</script><%}%>      
      <span class="selectInvalidMsg">*</span></span>
      :
      <span id="valMinIni">
        <select name="lstMinInicio" id="lstMinInicio" tabindex="14">
          <option value="-1" selected="selected">--</option>
          <option value="0">00</option>
          <option value="5">05</option>
          <option value="10">10</option>
          <option value="15">15</option>
          <option value="20">20</option>
          <option value="25">25</option>
          <option value="30">30</option>
          <option value="35">35</option>
          <option value="40">40</option>
          <option value="45">45</option>
          <option value="50">50</option>
          <option value="55">55</option>
        </select>
        <%if(ediVO != null){%><script>$("#lstMinInicio").val("<%=ediVO.getMinIn()%>");</script><%}%>
      <span class="selectInvalidMsg">*</span></span></td>
    <td colspan="2" class="tdDef">Hora Fin</td>
    <td colspan="3"><span id="valHorFin">
      <select name="lstHorFin" id="lstHorFin" tabindex="15">
        <option value="-1" selected="selected">--</option>
        <option value="0">00</option>
        <option value="1">01</option>
        <option value="2">02</option>
        <option value="3">03</option>
        <option value="4">04</option>
        <option value="5">05</option>
        <option value="6">06</option>
        <option value="7">07</option>
        <option value="8">08</option>
        <option value="9">09</option>
        <option value="10">10</option>
        <option value="11">11</option>
        <option value="12">12</option>
        <option value="13">13</option>
        <option value="14">14</option>
        <option value="15">15</option>
        <option value="16">16</option>
        <option value="17">17</option>
        <option value="18">18</option>
        <option value="19">19</option>
        <option value="20">20</option>
        <option value="21">21</option>
        <option value="22">22</option>
        <option value="23">23</option>
        </select>
      <%if(ediVO != null){%><script>$("#lstHorFin").val("<%=ediVO.getHorFi()%>");</script><%}%>      
      <span class="selectInvalidMsg">*</span></span>
      :
      <span id="valMinFin">
        <select name="lstMinFin" id="lstMinFin" tabindex="16">
          <option value="-1" selected="selected">--</option>
          <option value="0">00</option>
          <option value="5">05</option>
          <option value="10">10</option>
          <option value="15">15</option>
          <option value="20">20</option>
          <option value="25">25</option>
          <option value="30">30</option>
          <option value="35">35</option>
          <option value="40">40</option>
          <option value="45">45</option>
          <option value="50">50</option>
          <option value="55">55</option>
        </select>
        <%if(ediVO != null){%><script>$("#lstMinFin").val("<%=ediVO.getMinFin()%>");</script><%}%>    
      <span class="selectInvalidMsg">*</span></span></td>
  </tr>
  <tr>
    <td colspan="4" class="tdDef">&nbsp;</td>
    <td colspan="4" bgcolor="#FFFFFF" class="cellBtnSub">D&iacute;as Clase</td>
    <td class="tdDef">&nbsp;</td>
    </tr>
  <tr>
    <td rowspan="2" class="tdDef">Precios</td>
    <td style="width:7%" class="tdDef">Matricula</td>
    <td style="width:10%"><span id="valPreMat">
            <input name="txtPrecioMat" type="text" id="txtPrecioMat" tabindex="17" size="10" maxlength="10" <%if(ediVO != null){%> value="<%=df.format(ediVO.getPrecioM()).replace('.','.')%>" <%}%>/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>&#8364;</td>
    <td style="width:22%" class="tdDef"><input name="chkContado" type="checkbox" id="chkContado" tabindex="17" onchange="cambiaCheckPago('0');" value="true" <%if(ediVO != null){if(! ediVO.isPlazos()){%>checked="checked"<%}}%>/>
      Contado o Mensual</td>
    <td colspan="2" bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkLunes" type="checkbox" id="chkLunes" tabindex="21"  value="true" <%if(ediVO != null){if(ediVO.isHayLun()){%>checked="checked"<%}}%> />
      Lunes</td>
    <td style="width:10%" bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkMartes" type="checkbox" id="chkMartes" tabindex="22" value="true" <%if(ediVO != null){if(ediVO.isHayMar()){%>checked="checked"<%}}%>/>
      Martes</td>
    <td style="width:13%" bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkMiercoles" type="checkbox" id="chkMiercoles" tabindex="23"  value="true" <%if(ediVO != null){if(ediVO.isHayMie()){%>checked="checked"<%}}%> />
      Mi&eacute;rcoles</td>
    <td style="width:14%" class="tdDef">&nbsp;</td>
  </tr>
  <tr>
    <td class="tdDef">Recibo</td>
    <td><span id="valPreRec">
    <input name="txtPrecioPlazo" type="text" id="txtPrecioPlazo" tabindex="20" size="10" maxlength="10" <%if(ediVO != null){%> value="<%=df.format(ediVO.getPrecioR()).replace('.','.')%>"<%}%>/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>&#8364;</td>
    <td class="tdDef"><input name="chkAplazado" type="checkbox" id="chkAplazado" value="true" onchange="cambiaCheckPago('1');" tabindex="20" <%if(ediVO != null){if(ediVO.isPlazos()){%>checked="checked"<%}}%>/>   
      Aplazado</td>
    <td colspan="2" bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkJueves" type="checkbox" id="chkJueves" tabindex="24"  value="true" <%if(ediVO != null){if(ediVO.isHayJue()){%>checked="checked"<%}}%> />
      Jueves</td>
    <td bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkViernes" type="checkbox" id="chkViernes" tabindex="25"  value="true" <%if(ediVO != null){if(ediVO.isHayVie()){%>checked="checked"<%}}%>/>
      Viernes</td>
    <td bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkSabado" type="checkbox" id="chkSabado" tabindex="26" value="true" <%if(ediVO != null){if(ediVO.isHaySab()){%>checked="checked"<%}}%>/>
      S&aacute;bado</td>
    <td class="tdDef">&nbsp;</td>
    </tr>
  <tr>
    <td class="tdDef">&nbsp;</td>
    <td colspan="3" class="tdDef">&nbsp;</td>
    <td colspan="5" class="tdDef">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="9" class="tdDef"><div id="divMeses" style="visibility:hidden">
        <table style="width:1105" border="0">
          <tr>
            <td style="width:120" rowspan="2" bgcolor="#FFFFFF">Plazos</td>
            <td style="width:179" bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkEnero" type="checkbox" id="chkEnero" tabindex="26"  value="true"  <%if(ediVO != null){if(ediVO.isEne()){%>checked="checked"<%}}%>/>
              Enero</td>
            <td style="width:152" bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkFebrero" type="checkbox" id="chkFebrero" tabindex="28"  value="true" <%if(ediVO != null){if(ediVO.isFeb()){%>checked="checked"<%}}%> />
              Febrero</td>
            <td style="width:158" bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkMarzo" type="checkbox" id="chkMarzo" tabindex="29"  value="true" <%if(ediVO != null){if(ediVO.isMar()){%>checked="checked"<%}}%>/>
              Marzo</td>
            <td style="width:152" bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkAbril" type="checkbox" id="chkAbril" tabindex="30"  value="true" <%if(ediVO != null){if(ediVO.isAbr()){%>checked="checked"<%}}%>/>
              Abril</td>
            <td style="width:156" bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkMayo" type="checkbox" id="chkMayo" tabindex="31" value="true" <%if(ediVO != null){if(ediVO.isMay()){%>checked="checked"<%}}%>/>
              Mayo</td>
            <td style="width:158" bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkJunio" type="checkbox" id="chkJunio" tabindex="32"  value="true" <%if(ediVO != null){if(ediVO.isJun()){%>checked="checked"<%}}%>/>
              Junio</td>
            </tr>
          <tr>
            <td bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkJulio" type="checkbox" id="chkJulio" tabindex="33"  value="true" <%if(ediVO != null){if(ediVO.isJul()){%>checked="checked"<%}}%>/>
              Julio</td>
            <td bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkAgosto" type="checkbox" id="chkAgosto" tabindex="34" value="true" <%if(ediVO != null){if(ediVO.isAgo()){%>checked="checked"<%}}%> />
              Agosto</td>
            <td bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkSeptiembre" type="checkbox" id="chkSeptiembre" tabindex="35" value="true" <%if(ediVO != null){if(ediVO.isSep()){%>checked="checked"<%}}%> />
              Septiembre</td>
            <td bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkOctubre" type="checkbox" id="chkOctubre" tabindex="36"  value="true" <%if(ediVO != null){if(ediVO.isOct()){%>checked="checked"<%}}%>/>
              Octubre</td>
            <td bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkNoviembre" type="checkbox" id="chkNoviembre" tabindex="37" value="true" <%if(ediVO != null){if(ediVO.isNov()){%>checked="checked"<%}}%>/>
              Noviembre</td>
            <td bgcolor="#FFFFFF"  style="cursor:pointer"><input name="chkDiciembre" type="checkbox" id="chkDiciembre" tabindex="38"  value="true" <%if(ediVO != null){if(ediVO.isDic()){%>checked="checked"<%}}%>/>
              Diciembre</td>
            </tr>
          </table>
      </div></td>
     <%if(ediVO != null){if(ediVO.isPlazos()){%><script>divMeses.style.visibility='visible';</script><%}}%>        
    </tr>
  <tr>
    <td colspan="9" class="tdDef"><input class="tdDef" type="button" name="btnModulos" id="btnModulos" value="Ver Módulos" onclick="parent.abrirModEdi('<%=ediVO.getIdEdi()%>');"/></td>
    </tr>
  <tr>
    <td class="tdDef">Bonificaci&oacute;n</td>
    <td colspan="8" class="tdDef"><select name="lstBonificado" id="lstBonificado" tabindex="39">
      <option value="NO BONIFICADO">NO BONIFICADO</option>
      <option value="BONIFICADO">BONIFICADO</option>
      </select></td>
      <%if(ediVO != null){%><script>$("#lstBonificado").val("<%=ediVO.getBonif()%>");</script><%}%>    
  </tr>
  <tr>
    <td colspan="9" class="cellBtnSub">
    	<input class="cellBtnSub" type="button" name="btnAceptar" id="btnAceptar" value="Aceptar" onclick="validaForm();"      tabindex="40"/>
      <input class="cellBtnSub" type="button" name="btnVolver"  id="btnVolver"  value="Volver"  onclick="confirmarVolver();" tabindex="41"/></td>
  </tr>
  
</table>
</form>
<script type="text/javascript">
var spryselect2 = new Spry.Widget.ValidationSelect("valCentro" , {isRequired:false  , invalidValue:"-1"});
var spryselect3 = new Spry.Widget.ValidationSelect("valNivel"  , {invalidValue:"-1" , isRequired:false});
var spryselect4 = new Spry.Widget.ValidationSelect("valAula"   , {isRequired:false  , invalidValue:"-1"});
var spryselect5 = new Spry.Widget.ValidationSelect("valHorIni" , {isRequired:false  , invalidValue:"-1"});
var spryselect6 = new Spry.Widget.ValidationSelect("valMinIni" , {isRequired:false  , invalidValue:"-1"});
var spryselect7 = new Spry.Widget.ValidationSelect("valHorFin" , {isRequired:false  , invalidValue:"-1"});
var spryselect8 = new Spry.Widget.ValidationSelect("valMinFin" , {isRequired:false  , invalidValue:"-1"});
var sprytextfield1 = new Spry.Widget.ValidationTextField("valPlazas",  "integer");
var sprytextfield2 = new Spry.Widget.ValidationTextField("valHorPres", "integer");
var sprytextfield3 = new Spry.Widget.ValidationTextField("valHorDis",  "integer");
var sprytextfield4 = new Spry.Widget.ValidationTextField("valHorTel",  "integer");
var sprytextfield5 = new Spry.Widget.ValidationTextField("valPreMat",  "currency", {format:"dot_comma"});
var sprytextfield6 = new Spry.Widget.ValidationTextField("valPreRec",  "currency", {format:"dot_comma"});
var sprytextfield7 = new Spry.Widget.ValidationTextField("valDescripcion");
</script>
</body>
</html>