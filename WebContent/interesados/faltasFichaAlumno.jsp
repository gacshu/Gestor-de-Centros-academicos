<%@page import="es.jahernandez.gestion.ModulosGestion"%>
<%@page import="es.jahernandez.gestion.FaltasGestion"%>
<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import = "es.jahernandez.accesodatos.FaltasDAO"%>
<%@page import = "es.jahernandez.datos.FaltasVO"%>
<%@page import  = "java.util.Vector"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.jahernandez.accesodatos.CursosDAO"%>
<%@page import="es.jahernandez.accesodatos.EdicionesDAO"%>
<%@page import="es.jahernandez.accesodatos.AlumnosDAO"%>
<%@page import="es.jahernandez.accesodatos.ModulosDAO"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAut.jsp"%>
    
<%
   FaltasVO         faltaVO   = new FaltasVO();
   Vector           vecFaltas = new Vector(); 

   int              indInf    = 0;
   int              indSup    = 0;
   String           codAlu    = "";
   String           codEdi    = "";
   boolean          errAlta   = false;
   boolean          errEdi    = false;
   int              resBor    = -99;
     
   //Paginación de resultados
   if(request.getParameter("valInfFalta") != null)
   {
            indInf = new Integer(request.getParameter("valInfFalta")).intValue();
   }

   if(request.getParameter("codInt") != null) 
   {
        codAlu   =  request.getParameter("codInt").trim();
        if(request.getParameter("codEdi") != null)
		{
			codEdi   =  request.getParameter("codEdi").trim();
			vecFaltas = FaltasGestion.devolverFaltasAluEdi(codAlu, codEdi);
		}
   }
   
   if(request.getParameter("errorCode") != null)
   {
            errAlta = true;
   }
   
   if(request.getParameter("errorEdi") != null)
   {
            errEdi = true;
   }
   
   
   if(request.getParameter("resultBor") != null)
   {
            resBor = new Integer(request.getParameter("resultBor").trim()).intValue();
   }
         
   if(indInf < 0)
   {
		indInf = 0;
   }

   indSup = indInf + 8;
   if(indSup > vecFaltas.size()) 
   {
        indSup = vecFaltas.size();
   }

 %>   
<head>

<title>Faltas</title>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<script src="../js/validaGlobal.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>



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
			yearRange:       "c-10:c+10",
			showButtonPanel: true
        });
	});
</script>

<script>
function validaForm()
{
     var validarCampos  = true;		
		
     //Validar campos alta falta        
     validarCampos = spryselect1.validate() & sprytextfield1.validate() ;
	                 
     if(validarCampos)
     {
            document.frmFaltas.action = "../AltaFaltaServlet?valInfFalta=<%=indInf%>" +
														   "&pagPest=1";
            document.frmFaltas.submit();
     }
     else
     {
            alert("Valide el valor de los campos resaltados");
     }
	
}

function habilitaEdi(codFalta)
{
	eval("document.frmFaltas.chkJust"     + codFalta).disabled  = false;  
    eval("document.frmFaltas.btnEdiFalta" + codFalta).disabled  = false;       
}


function habilitaAlta()
{
    document.frmFaltas.lstNuevoMod  .style.visibility = "visible"; 
    document.frmFaltas.txtNuevaFecha.style.visibility = "visible";
    document.frmFaltas.chkNuevoJust .style.visibility = "visible";  
    document.frmFaltas.btnAnadir    .style.visibility = "visible"; 
}

function editaFalta(codFalta)
{
    var validaCampos = true;
	
	
	
    if(validaCampos)
    {    			
        document.frmFaltas.action = "../ModFaltaServlet?codEdi=<%=codEdi%>" + 
                                                     "&codInt=<%=codAlu%>" + 
                                                     "&codMod=" + eval("document.frmFaltas.hidMod"   + codFalta).value +
                                                     "&fecha="  + eval("document.frmFaltas.hidFecha" + codFalta).value +													
                                                     "&valInfFalta=<%=indInf%>" +
													 "&pagPest=1"   ;
                                                            
		document.frmFaltas.submit();
    }
    else
    {
        alert("Valide los valores introducidos");
    }
}

function bajaFalta(codFalta)
{
    if(confirm("¿Desea eliminar esta falta?"))
    {
		document.frmFaltas.action = "../BorrarFaltaServlet?codEdi=<%=codEdi%>" + 
                                                         "&codInt=<%=codAlu%>" + 
                                                         "&codMod=" + eval("document.frmFaltas.hidMod"   + codFalta).value +
                                                         "&fecha="  + eval("document.frmFaltas.hidFecha" + codFalta).value +													
                                                         "&valInfFalta=<%=indInf%>" +
														 "&pagPest=1"   ;
                                                     
		document.frmFaltas.submit();
    }
}

function cargaFaltas()
{
	if(spryselect2.validate())
	{
		window.open('./faltasFichaAlumno.jsp?codInt=<%=codAlu%>&codEdi=' + document.frmFaltas.lstEdi.value,'_self','');
	}
}


function cargarCombosModulosEdicion(idMod)
{   
	$.ajax({
	  url: "../CargaComboModulosEdiCalifServlet?codEdi=<%=codEdi%>&valSel=" + idMod,
	  success: function(data) {
		$(document).ready(function() {
                $("#lstNuevoMod").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los módulos");
	  }
	});
}

function cargarComboEdiAlu(idEdi)
{   
	$.ajax({
	  url: "../CargaComboMatriculasAluServlet?codAlu=<%=codAlu%>&valSel=" + idEdi,
	  success: function(data) {
		$(document).ready(function() {
                $("#lstEdi").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las ediciones");
	  }
	});
}

function cargarCombos()
{
	cargarCombosModulosEdicion();
	cargarComboEdiAlu();
}


</script>
</head>

<body class="colorFondoPrincipalPestana" onload="cargarCombos();">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta de la falta")</script>
<%}%>

<%if(errEdi){%>
<script>alert("Se produjo un error\n en la edición de la falta")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
<script>alert("La falta se borró correctamente ")</script>
    <%}else{%>
<script>alert("La falta no pudo ser borrada ")</script>
<%}}%>

<form action="" method="post" name="frmFaltas" id="frmFaltas">
<table style="width:85%" border="0" align="center" class="tablaListados">
  <tr class="tablaListados">
    <td colspan="11" class="tdDef"><span id="valFiltEdi">
      <span id="valFilMod">
      <select name="lstEdi" id="lstEdi">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span><span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span>
      <input type="button" name="btnFiltro" id="btnFiltro" value="Ver Faltas" 
      onclick="cargaFaltas();" /></td>
  </tr>
  <tr class="tablaListados">
      <td colspan="10" class="tdDef"><strong>
          <%if(!codEdi.trim().equals("")){%>
          <%="Faltas de la edición " + EdicionesGestion.devolverDatosEdi(codEdi).getDescripcion() 
          	+ " del curso " + CursosGestion.devolverDatosCurso(EdicionesGestion.devolverDatosEdi(codEdi).getIdCur()).getNomCur()%>
          <%}%>
          <input name="txtCodAlu" type="hidden" id="txtCodAlu" value="<%=codAlu%>" />
          <input name="txtCodEdi" type="hidden" id="txtCodEdi" value="<%=codEdi%>" /></strong></td>
    </tr>
  <tr class="tablaListados">
    <td style="width:326" class="tdDef"><strong>M&oacute;dulos</strong></td>
    <td colspan="2" class="tdDef"><strong>Fecha</strong></td>
    <td colspan="5" class="tdDef"><strong>Justificada</strong></td>
    <td colspan="2" class="tdDef">
    	<img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" style="width:30" height="30" onclick="window.open('../ImpFaltasEdiAluServlet?codInt=<%=codAlu%>&codEdi=<%=codEdi%>','_califAlu','');"/>
        <img src="../imagenes/newhab.png" style="width:30" height="30" onclick="habilitaAlta();" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      faltaVO = (FaltasVO) vecFaltas.elementAt(ind);
  %>
  <tr class="tdDef">
      <td class="tdDef"><%=ModulosGestion.devolverDatosModulo(faltaVO.getIdMod()).getNombre()%>
          <input type="hidden" name="hidMod<%=faltaVO.devolverClave()%>" id="hidMod<%=faltaVO.devolverClave()%>" value="<%=faltaVO.getIdMod()%>" />
      </td>
      <td colspan="2" class="tdDef"><%=new SimpleDateFormat("dd/MM/yyyy").format(faltaVO.getFecha())%>
          <input type="hidden" name="hidFecha<%=faltaVO.devolverClave()%>" id="hidFecha<%=faltaVO.devolverClave()%>" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(faltaVO.getFecha())%>" />
      </td>
      <td colspan="4">
          <input name="chkJust<%=faltaVO.devolverClave()%>" type="checkbox" disabled="disabled" id="chkJust<%=faltaVO.devolverClave()%>" value="true" <%if(faltaVO.isJustificada()){%>checked="checked"<%}%>/>
      </td>
      <td style="width:24" class="center">
          <input name="btnEdiFalta<%=faltaVO.devolverClave()%>" type="button"  id="btnEdiFalta<%=faltaVO.devolverClave()%>" value="E" onclick="editaFalta('<%=faltaVO.devolverClave()%>')" disabled="disabled"/>
      </td>
      <td style="width:32">
          <img src="../imagenes/editar.png" style="width:30" height="30" onclick="habilitaEdi('<%=faltaVO.devolverClave()%>');" style="cursor:pointer"/></td>
      <td style="width:95" class="center">
        <img src="../imagenes/papelera.png" style="width:30" height="30" onclick="bajaFalta('<%=faltaVO.devolverClave()%>');" style="cursor:pointer"/> 
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td class="tdDef"><span id="valMod">
        <select name="lstNuevoMod" id="lstNuevoMod" style="visibility:hidden">
        </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td height="26" colspan="2" class="tdDef">
      <span id="valFecha">
        <input class="claseForFecha" name="txtNuevaFecha" type="text" id="txtNuevaFecha" size="10" maxlength="10" style="visibility:hidden" onchange="hidNuevaFecha.value=txtNuevaFecha.value;"/>
        <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>      
      <input name="hidNuevaFecha" type="hidden" id="hidNuevaFecha"  /></td>
    <td colspan="4"><input name="chkNuevoJust" type="checkbox" id="chkNuevoJust" style="visibility:hidden" value="true"/>
      <label for="chkNuevoJust"></label></td>
    <td colspan="3"><span class="center">
      <input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Registro" style="visibility:hidden" onclick="validaForm();"/>
    </span></td>
    </tr>
  <tr>
    <td height="30" colspan="6" class="center">
      <%if( indInf >= 8){%>
      <img src="../imagenes/btnprev.png" style="width:35" height="35" onclick="window.open('gestionFaltas.jsp?codInt=<%=codAlu%>&codEdi=<%=codEdi%>&valInfFalta=<%=(indInf - 8)%>','_self','');">
      <%}%>
      </td>
    <td colspan="2" class="center">&nbsp;</td>
    <td colspan="2" class="center"><%if( indSup < vecFaltas.size()){%>
      <img src="../imagenes/btnsig.png" style="width:35" height="35" onclick="window.open('gestionFaltas.jsp?codInt=<%=codAlu%>&codEdi=<%=codEdi%>&valInfFalta=<%=(indInf + 8)%>','_self','');"/>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="10" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("valMod", {invalidValue:"-1"});
var sprytextfield1 = new Spry.Widget.ValidationTextField("valFecha", "date", {format:"dd/mm/yyyy"});
var spryselect2 = new Spry.Widget.ValidationSelect("valFilMod", {invalidValue:"-1"});
</script>
</body>
</html>