<%@page import="es.jahernandez.gestion.TipoCursoGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<%@ page import = "java.text.DecimalFormat"%>
<%@ page import = "java.text.SimpleDateFormat"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir Curso</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
    CursosVO      curVO  = null;
    String        urlDes = "";
    EdicionesVO   ediVO  = null;
    boolean       msgErr = false;
	
    DecimalFormat df     = new DecimalFormat ("0.00");
        
    if(request.getParameter("codCurso")!= null)
    {
        curVO = CursosGestion.devolverDatosCurso(request.getParameter("codCurso").trim());
    }
	
	if(request.getParameter("urlDes")!= null)
    {
        urlDes = request.getParameter("urlDes").trim();
    }
    
    if(session.getAttribute("nuevaEdiErr") != null)
    {
        ediVO = (EdicionesVO) session.getAttribute("nuevaEdiErr");
        session.removeAttribute("nuevaEdiErr");
    }
    
    if(request.getParameter("msgError") != null)
    {
        msgErr = true;
    }
    
%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='../js/validaGlobal.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Centros.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Cursos.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/TipoCurso.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Aulas.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Niveles.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/engine.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/util.js'></script>
<script src="../jquery.ui-1.5.2/jquery-1.2.6.js" type="text/javascript"></script>
<script src="../jquery.ui-1.5.2/ui/ui.datepicker.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<link href="../jquery.ui-1.5.2/themes/ui.datepicker.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
</head>

<%if(msgErr){%>
<script>
    alert("Se produjo un error en el alta de la edición");
</script>
<%}%>


<script>
function validaForm()
{
	var correcto = true;
	
	if(Trim(document.frmAltaEdicion.txtFecInicio.value) == "" || 
	   Trim(document.frmAltaEdicion.txtFecFin.value)    == "" )
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
						   sprytextfield6.validate() & sprytextfield7.validate(); 
	if(correcto)
	{
		document.frmAltaEdicion.action = "../AltaEdicionServlet";	
		document.frmAltaEdicion.submit();
	}
	else
	{
		alert('Valide los campos resaltados y las fechas');
	}					   
	
}

function cargarComboTipoCurso()
{
	TipoCurso.devolverDatosTipCur(function(data)
	{ 
		dwr.util.removeAllOptions("datTipoCurso");
		dwr.util.addOptions("datTipoCurso", [{key:'-1', value:'-----'}], "key", "value");            
		dwr.util.addOptions("datTipoCurso", data, 'idTipoCurso', 'nomTipCurso');
	});
		
}

function cargarComboCursos(tipo)
{		
	Cursos.devolverDatCurTipCombo((tipo) , function(data)
	{ 		
		dwr.util.removeAllOptions("datCurso");
		dwr.util.addOptions("datCurso", [{key:'-1', value:'-----'}], "key", "value");                  
		dwr.util.addOptions("datCurso", data, 'idCur', 'nomCur');		
	}); 
}

function cargarComboNiveles(curso)
{		
	Niveles.devolverNivCur((curso) , function(data)
	{ 				
		dwr.util.removeAllOptions("lstNivel");            
		dwr.util.addOptions("lstNivel", [{key: null, value:'-----'}], "key", "value"); 
		dwr.util.addOptions("lstNivel", data, 'idNiv', 'nomNiv');		
	}); 
}       

function cargarComboCentros()
{
	Centros.datComCentros(function(data)
	{ 
		dwr.util.removeAllOptions("datCentro");
		dwr.util.addOptions("datCentro", [{key:'-1', value:'-----'}], "key", "value");            
		dwr.util.addOptions("datCentro", data, 'idCentro', 'nombreCentro');
	});
		
}

function cargarComboAulas(centro)
{
	Aulas.devolverAulasCentro((centro) , function(data)
	{ 		
		dwr.util.removeAllOptions("lstAula");
		dwr.util.addOptions("lstAula", [{key:'-1', value:'-----'}], "key", "value");             
		dwr.util.addOptions("lstAula", data, 'idAula', 'nombre');		
	}); 
}

function cargarComboCentrosCurso(curso)
{		
	Cursos.datCentrosCurso((curso) , function(data)
	{ 		
		dwr.util.removeAllOptions("datCentro");   
		dwr.util.addOptions("datCentro", data, 'idCentro', 'nombreCentro');		
	}); 
}

function cargarCentrosCurso()
{   
    Cursos.devolverDatosCurso((dwr.util.getValue('datCurso')) ,function (data)
            {
                if(data.cenCurso == 0)
                {
                    cargarComboCentros();
                    dwr.util.removeAllOptions("lstAula"); 
                }
                else
                {
                    cargarComboCentrosCurso(dwr.util.getValue('datCurso'));
                    cargarComboAulas(data.cenCurso);
                }
            });

}

function cambiaCheckPago(origen)
{
	if(origen==0)
	{
		frmAltaEdicion.chkAplazado.checked = !frmAltaEdicion.chkContado.checked;
	}
	else
	{
		frmAltaEdicion.chkContado.checked = ! frmAltaEdicion.chkAplazado.checked;
	}
	
	if(frmAltaEdicion.chkAplazado.checked)
	{
		document.getElementById("divMeses").style.visibility = "visible";
	}
	else
	{
		document.getElementById("divMeses").style.visibility = "hidden";
	}	
}



</script>


<body class="colorFondoPrincipalPestana" onload="cargarComboTipoCurso()">
<form  method="post" name="frmAltaEdicion" id="frmAltaEdicion">
<table width="90%" border="0">
  <tr>
    <td colspan="9" class="thDef"><input name="urlDes" type="hidden" id="urlDes" value="<%=urlDes%>" />
      Alta Edici&oacute;n</td>
  </tr>
  <tr>
    <td width="10%" class="tdDef">Tipo</td>
    <td colspan="3">
    <%if(curVO == null){%>    
        <select name="datTipoCurso" size="1" id="datTipoCurso" tabindex="1"  onchange="cargarComboCursos(dwr.util.getValue('datTipoCurso'));
                                                                                       dwr.util.removeAllOptions('lstNivel');
                                                                                       dwr.util.removeAllOptions('datCentro');" >
        </select>        
    <%}else{%>
    <input name="txtTipoCurso" type="text" id="datTipoCurso" tabindex="1" size="45" value="<%=TipoCursoGestion.devolverTipCur(curVO.getTipCur()).getNomTipCurso() %>" readonly="readonly" />
        <input type="hidden" name="datTipoCurso" id="datTipoCurso" value="<%=curVO.getIdCur()%>" />
    <%}%>
    </td>
    <td colspan="2" rowspan="2" class="tdDef">Nivel</td>
    <td colspan="3" rowspan="2"><span id="valNivel">
      <select name="lstNivel" size="1" id="lstNivel" tabindex="4">
      </select>
      <span class="selectInvalidMsg">*</span></span></td>
  </tr>
  <tr>
    <td class="tdDef">Curso</td>
    <td colspan="3">
    <%if(curVO == null){%>
    <span id="valCurso">
    <select name="datCurso" size="1" id="datCurso" tabindex="2" 
                onchange="cargarComboNiveles(dwr.util.getValue('datCurso'));
                          cargarCentrosCurso();">
      <option value="-1">-----</option>
    </select>
    <span class="selectInvalidMsg">*</span></span>
    <%}else{%>  
        <input name="txtCurso" type="text" id="txtCurso" tabindex="2" size="55" value="<%=curVO.getNomCur()%>" readonly="readonly" />
        <input type="hidden" name="datCurso" id="datCurso" value="<%=curVO.getIdCur()%>" /></td>
        <script>
            cargarComboNiveles('<%=curVO.getIdCur()%>');
            <%if(curVO.getCenCurso() == 0){%>
                cargarComboCentros();
            <%}else{%>
                cargarComboCentrosCurso('<%=curVO.getIdCur()%>');
                cargarComboAulas('<%=curVO.getCenCurso()%>');  
            <%}%>
        </script>
    <%}%>
    </tr>
  <tr>
    <td height="25" class="tdDef">Centro</td>
    <td colspan="3"><span class="tdDef">
      <%if(curVO == null){%>
      <span id="valCentro">
      <select name="datCentro" size="1" id="datCentro" tabindex="3" onchange="cargarComboAulas(dwr.util.getValue('datCentro'))">
      </select>
      <span class="selectInvalidMsg">*</span></span>
<%}else{
            if(curVO.getCenCurso() == 0 ){%>
        <script>cargarComboCentros();</script><span id="valCentro">
        <select name="datCentro" size="1" id="datCentro" tabindex="3" onchange="cargarComboAulas(dwr.util.getValue('datCentro'))">
        </select>
        <span class="selectInvalidMsg">*</span></span>
                <%}else{%>
                <span id="valCentro">
                <select name="datCentro" size="1" id="datCentro" tabindex="3">
                </select>
                <span class="selectRequiredMsg">*</span></span>
                <%}}%>
    </span></td>
    <td colspan="2" class="tdDef">Aula</td>
    <td colspan="3"><span id="valAula">
      <select name="lstAula" size="1" id="lstAula" tabindex="5">
      </select>
      <span class="selectRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td class="tdDef">Descripci&oacute;n</td>
    <td colspan="8"><span id="valDescripcion">
      <label for="txtDescripcion"></label>
      <input name="txtDescripcion" type="text" id="txtDescripcion" <%if(ediVO != null){%> value="<%=ediVO.getDescripcion()%>" <%}%>   tabindex="6" size="100" maxlength="100" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td class="tdDef">Fecha Incio</td>
    <td colspan="3">
        <input type="text" tabindex="7" size="15" name="txtFecInicio" id="txtFecInicio" <%if(ediVO != null){%> value="<%=new SimpleDateFormat ("dd-MM-yyyy").format(ediVO.getFecIn())%>" <%}%> />
    <script type="text/javascript">
		// BeginWebWidget jQuery_UI_Calendar: jQueryUICalendar1
		jQuery("#txtFecInicio").datepicker({ closeText: "Cerrar" ,
									      	 cleanText: "Limpiar"  ,
											 currentText: "Hoy"  ,
											 dateFormat: "dd-mm-yy" ,
											 dayNamesMin: ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
											 dayNames: ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"],
											 firstDay: 1 ,
											 monthNames: ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"],
											 monthNamesShort: ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"],
											 nextText: "Sig-->",
											 numberOfMonths: [1, 1],											 
											 prevText: "<--Ant" ,
											 showWeek: true ,
											 weekHeader: "Se" ,
											 onSelect: function()
													   {			
															if(jQuery("#txtFecFin").datepicker( "getDate") != null && 
															   jQuery("#txtFecInicio").datepicker( "getDate") > jQuery("#txtFecFin").datepicker( "getDate"))
															{
																jQuery("#txtFecInicio").datepicker( "setDate" , null);
																alert("La fecha de inicio \ndebe ser anterior o igual que la de finalización");
															}	
													   } });						 
											 
		// EndWebWidget jQuery_UI_Calendar: jQueryUICalendar1
      </script></td>
    <td colspan="2" class="tdDef">Fecha Fin</td>
    <td colspan="3">
    <input name="txtFecFin" type="text" id="txtFecFin" tabindex="8" size="15" maxlength="10" <%if(ediVO != null){%> value="<%=new SimpleDateFormat("dd-MM-yyyy").format(ediVO.getFecFi())%>"<%}%>/>
    <script type="text/javascript">
	  // BeginWebWidget jQuery_UI_Calendar: jQueryUICalendar2
	  jQuery("#txtFecFin").datepicker({ closeText: "Cerrar" ,
											 cleanText: "Limpiar"  ,
											 currentText: "Hoy"  ,
											 dateFormat: "dd-mm-yy" ,
											 dayNamesMin: ["Do", "Lu", "Ma", "Mi", "Ju", "Vi", "Sa"],
											 dayNames: ["Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"],
											 firstDay: 1 ,											 
											 monthNames: ["Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"],
											 monthNamesShort: ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"],
											 nextText: "Sig-->",
											 numberOfMonths: [1, 1],											 
											 prevText: "<--Ant" ,
											 showWeek: true ,
											 weekHeader: "Se" ,
											 onSelect: function()
													   {			
															if(jQuery("#txtFecInicio").datepicker( "getDate") != null && 
															   jQuery("#txtFecInicio").datepicker( "getDate") > jQuery("#txtFecFin").datepicker( "getDate") )
															{
																
																jQuery("#txtFecFin").datepicker( "setDate" , null);
																alert("La fecha de finalización \ndebe ser igual o posterior que la de inicio");
															}	
													   }  });

	  // EndWebWidget jQuery_UI_Calendar: jQueryUICalendar2
      </script></td>
  </tr>
  <tr>
    <td rowspan="3" class="tdDef">Plazas</td>
    <td colspan="3">&nbsp;</td>
    <td width="5%" rowspan="3" class="tdDef">Horas</td>
    <td width="9%" class="tdDef">Presenciales</td>
    <td colspan="3" class="tdDef"><span id="valHorPres">
    <input name="txtHorPresen" type="text" id="txtHorPresen" tabindex="10" size="8" maxlength="4"  <%if(ediVO != null){%> value="<%=ediVO.getNumHor()%>"<%}%> />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
  <tr>
    <td colspan="3"><span id="valPlazas">
    <input name="txtPlazas" type="text" id="txtPlazas" tabindex="9" size="8" maxlength="4" <%if(ediVO != null){%> value="<%=ediVO.getNumPla()%>" <%}%>/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td class="tdDef">Distancia</td>
    <td colspan="3" class="tdDef"><span id="valHorDis">
    <input name="txtHorDist" type="text" id="txtHorDist" tabindex="11" size="8" maxlength="4" <%if(ediVO != null){%> value="<%=ediVO.getHorDis()%>" <%}%>/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    </tr>
  <tr>
    <td colspan="3">&nbsp;</td>
    <td class="tdDef">Telef&oacute;nicas</td>
    <td colspan="3" class="tdDef"><span id="valHorTel">
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
      <%if(ediVO != null){%><script>dwr.util.setValue("lstHorInicio", "<%=ediVO.getHorIn()%>");</script><%}%>      
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
      <%if(ediVO != null){%><script>dwr.util.setValue("lstMinInicio", "<%=ediVO.getMinIn()%>");</script><%}%>
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
      <%if(ediVO != null){%><script>dwr.util.setValue("lstHorFin", "<%=ediVO.getHorFi()%>");</script><%}%>      
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
      <%if(ediVO != null){%><script>dwr.util.setValue("lstMinFin", "<%=ediVO.getMinFin()%>");</script><%}%>    
      <span class="selectInvalidMsg">*</span></span></td>
  </tr>
  <tr>
    <td colspan="4" class="tdDef">&nbsp;</td>
    <td colspan="4" bgcolor="#FFFFFF" class="cellBtnSub">D&iacute;as Clase</td>
    <td class="tdDef">&nbsp;</td>
    </tr>
  <tr>
    <td rowspan="2" class="tdDef">Precios</td>
    <td width="7%" class="tdDef">Matricula</td>
    <td width="10%"><span id="valPreMat">
            <input name="txtPrecioMat" type="text" id="txtPrecioMat" tabindex="17" size="10" maxlength="10" <%if(ediVO != null){%> value="<%=df.format(ediVO.getPrecioM()).replace('.','.')%>" <%}%>/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>&#8364;</td>
    <td width="22%" class="tdDef"><input name="chkContado" type="checkbox" id="chkContado" tabindex="19" onchange="cambiaCheckPago(0);" value="true" <%if(ediVO != null){if(! ediVO.isPlazos()){%>checked="checked"<%}}%>/>
      Contado o Mensual</td>
    <td colspan="2" bgcolor="#FFFFFF" class="tdDef" style="cursor:pointer"><input name="chkLunes" type="checkbox" id="chkLunes" tabindex="21" value="true" <%if(ediVO != null){if(ediVO.isHayLun()){%>checked="checked"<%}}%> />
      Lunes</td>
    <td width="13%" bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkMartes" type="checkbox" id="chkMartes" tabindex="22"  value="true" <%if(ediVO != null){if(ediVO.isHayMar()){%>checked="checked"<%}}%>/>
      Martes</td>
    <td width="12%" bgcolor="#FFFFFF" class="tdDef"  style="cursor:pointer"><input name="chkMiercoles" type="checkbox" id="chkMiercoles" tabindex="23"  value="true" <%if(ediVO != null){if(ediVO.isHayMie()){%>checked="checked"<%}}%> />
      Mi&eacute;rcoles</td>
    <td width="12%" class="tdDef">&nbsp;</td>
  </tr>
  <tr>
    <td class="tdDef">Recibo</td>
    <td><span id="valPreRec">
    <input name="txtPrecioPlazo" type="text" id="txtPrecioPlazo" tabindex="18" size="10" maxlength="10" <%if(ediVO != null){%> value="<%=df.format(ediVO.getPrecioR()).replace('.','.')%>"<%}%>/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>&#8364;</td>
    <td class="tdDef"><input name="chkAplazado" type="checkbox" id="chkAplazado" value="true" 
    				  onchange="cambiaCheckPago(1);" tabindex="20"
        <%if(ediVO != null){if(ediVO.isPlazos()){%>checked="checked"<%}}%>/>   
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
        <table width="1105" border="0">
          <tr>
            <td width="120" rowspan="2" bgcolor="#FFFFFF">Plazos</td>
            <td width="179" bgcolor="#FFFFFF" onclick="chkEnero.checked=!chkEnero.checked" style="cursor:pointer"><input name="chkEnero" type="checkbox" id="chkEnero" tabindex="27" onclick="chkEnero.checked=!chkEnero.checked" value="true"  <%if(ediVO != null){if(ediVO.isEne()){%>checked="checked"<%}}%>/>
              Enero</td>
            <td width="152" bgcolor="#FFFFFF" onclick="chkFebrero.checked=!chkFebrero.checked" style="cursor:pointer"><input name="chkFebrero" type="checkbox" id="chkFebrero" tabindex="28" onclick="chkFebrero.checked=!chkFebrero.checked" value="true" <%if(ediVO != null){if(ediVO.isFeb()){%>checked="checked"<%}}%> />
              Febrero</td>
            <td width="158" bgcolor="#FFFFFF" onclick="chkMarzo.checked=!chkMarzo.checked" style="cursor:pointer"><input name="chkMarzo" type="checkbox" id="chkMarzo" tabindex="29" onclick="chkMarzo.checked=!chkMarzo.checked" value="true" <%if(ediVO != null){if(ediVO.isMar()){%>checked="checked"<%}}%>/>
              Marzo</td>
            <td width="152" bgcolor="#FFFFFF" onclick="chkAbril.checked=!chkAbril.checked" style="cursor:pointer"><input name="chkAbril" type="checkbox" id="chkAbril" tabindex="30" onclick="chkAbril.checked=!chkAbril.checked" value="true" <%if(ediVO != null){if(ediVO.isAbr()){%>checked="checked"<%}}%>/>
              Abril</td>
            <td width="156" bgcolor="#FFFFFF" onclick="chkMayo.checked=!chkMayo.checked" style="cursor:pointer"><input name="chkMayo" type="checkbox" id="chkMayo" tabindex="31" onclick="chkMayo.checked=!chkMayo.checked" value="true" <%if(ediVO != null){if(ediVO.isMay()){%>checked="checked"<%}}%>/>
              Mayo</td>
            <td width="158" bgcolor="#FFFFFF" onclick="chkJunio.checked=!chkJunio.checked" style="cursor:pointer"><input name="chkJunio" type="checkbox" id="chkJunio" tabindex="32" onclick="chkJunio.checked=!chkJunio.checked" value="true" <%if(ediVO != null){if(ediVO.isJun()){%>checked="checked"<%}}%>/>
              Junio</td>
            </tr>
          <tr>
            <td bgcolor="#FFFFFF" onclick="chkJulio.checked=!chkJulio.checked" style="cursor:pointer"><input name="chkJulio" type="checkbox" id="chkJulio" tabindex="33" onclick="chkJulio.checked=!chkJulio.checked" value="true" <%if(ediVO != null){if(ediVO.isJul()){%>checked="checked"<%}}%>/>
              Julio</td>
            <td bgcolor="#FFFFFF" onclick="chkAgosto.checked=!chkAgosto.checked" style="cursor:pointer"><input name="chkAgosto" type="checkbox" id="chkAgosto" tabindex="34" onclick="chkAgosto.checked=!chkAgosto.checked" value="true" <%if(ediVO != null){if(ediVO.isAgo()){%>checked="checked"<%}}%> />
              Agosto</td>
            <td bgcolor="#FFFFFF" onclick="chkSeptiembre.checked=!chkSeptiembre.checked" style="cursor:pointer"><input name="chkSeptiembre" type="checkbox" id="chkSeptiembre" tabindex="35" onclick="chkSeptiembre.checked=!chkSeptiembre.checked" value="true" <%if(ediVO != null){if(ediVO.isSep()){%>checked="checked"<%}}%> />
              Septiembre</td>
            <td bgcolor="#FFFFFF" onclick="chkOctubre.checked=!chkOctubre.checked" style="cursor:pointer"><input name="chkOctubre" type="checkbox" id="chkOctubre" tabindex="36" onclick="chkOctubre.checked=!chkOctubre.checked" value="true" <%if(ediVO != null){if(ediVO.isOct()){%>checked="checked"<%}}%>/>
              Octubre</td>
            <td bgcolor="#FFFFFF" onclick="chkNoviembre.checked=!chkNoviembre.checked" style="cursor:pointer"><input name="chkNoviembre" type="checkbox" id="chkNoviembre" tabindex="37" onclick="chkNoviembre.checked=!chkNoviembre.checked" value="true" <%if(ediVO != null){if(ediVO.isNov()){%>checked="checked"<%}}%>/>
              Noviembre</td>
            <td bgcolor="#FFFFFF" onclick="chkDiciembre.checked=!chkDiciembre.checked" style="cursor:pointer"><input name="chkDiciembre" type="checkbox" id="chkDiciembre" tabindex="38" onclick="chkDiciembre.checked=!chkDiciembre.checked" value="true" <%if(ediVO != null){if(ediVO.isDic()){%>checked="checked"<%}}%>/>
              Diciembre</td>
            </tr>
          </table>
      </div></td>
     <%if(ediVO != null){if(ediVO.isPlazos()){%><script>divMeses.style.visibility='visible';</script><%}}%>        
    </tr>
  <tr>
    <td class="tdDef">&nbsp;</td>
    <td colspan="8" class="tdDef">&nbsp;</td>
  </tr>
  <tr>
    <td class="tdDef">Bonificaci&oacute;n</td>
    <td colspan="8" class="tdDef"><select name="lstBonificado" id="lstBonificado" tabindex="39">
      <option value="NO BONIFICADO">NO BONIFICADO</option>
      <option value="BONIFICADO">BONIFICADO</option>
      </select></td>
      <%if(ediVO != null){%><script>dwr.util.setValue("lstBonificado", "<%=ediVO.getBonif()%>");</script><%}%>    
  </tr>
  <tr>
    <td colspan="9" class="cellBtnSub"><input type="button" name="btnAceptar" id="btnAceptar" value="Aceptar" onclick="validaForm();" tabindex="40"/></td>
  </tr>
  
</table>
<p id="frmAltaCurso">&nbsp;</p>
</form>
<script type="text/javascript">
<%if(curVO == null){%>
var spryselect1 = new Spry.Widget.ValidationSelect("valCurso"  , {invalidValue:"-1" , isRequired:false});
<%}%>
var spryselect2 = new Spry.Widget.ValidationSelect("valCentro" , {isRequired:false  , invalidValue:"-1"});
var spryselect3 = new Spry.Widget.ValidationSelect("valNivel"  , {invalidValue:"-1" , isRequired:false});
var spryselect4 = new Spry.Widget.ValidationSelect("valAula"   );
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