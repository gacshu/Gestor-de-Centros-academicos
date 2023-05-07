<%@page import="es.jahernandez.gestion.ModulosGestion"%>
<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.CalificacionesGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import = "es.jahernandez.accesodatos.CalificacionesDAO"%>
<%@page import = "es.jahernandez.datos.CalificacionesVO"%>
<%@page import  = "java.util.Vector"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.jahernandez.accesodatos.CursosDAO"%>
<%@page import="es.jahernandez.accesodatos.EdicionesDAO"%>
<%@page import="es.jahernandez.accesodatos.AlumnosDAO"%>
<%@page import="es.jahernandez.accesodatos.ModulosDAO"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAutProf.jsp"%>
    
<%
   CalificacionesVO califVO  = new CalificacionesVO();
   Vector           vecCalif = null; 

   int              indInf   = 0;
   int              indSup   = 0;
   String           codAlu   = "";
   String           codEdi   = "";
   boolean          errAlta  = false;
   boolean          errEdi   = false;
   int              resBor   = -99;
     
   //Paginación de resultados
   if(request.getParameter("valInfCalif") != null)
   {
            indInf = new Integer(request.getParameter("valInfCalif")).intValue();
   }

   if(request.getParameter("codInt") != null &&
      request.getParameter("codEdi") != null )
   {
        codAlu   =  request.getParameter("codInt").trim();
        codEdi   =  request.getParameter("codEdi").trim();
       
        vecCalif =  CalificacionesGestion.devolverCalificacionesAluEdi(codAlu, codEdi);
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
   if(indSup > vecCalif.size()) 
   {
        indSup = vecCalif.size();
   }

 %>   
<head>

<title>Clases individuales</title>
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
		
     //Validar campos alta nivel        
     validarCampos = sprytextfield1.validate() & spryselect1.validate() &
	                 spryselect2.validate()    & spryselect3.validate();    
                
     if(validarCampos)
     {
            document.frmCalif.action = "../AltaCalifServlet?valInfCalif=<%=indInf%>";
            document.frmCalif.submit();
     }
     else
     {
            alert("Valide el valor de los campos resaltados");
     }
	
}

function habilitaEdi(codCalif)
{
    eval("document.frmCalif.txtFecha"    + codCalif).disabled  = false; 
    eval("document.frmCalif.lstNota"     + codCalif).disabled  = false;  
	eval("document.frmCalif.btnEdiCalif" + codCalif).disabled  = false;       
}


function habilitaAlta()
{
    document.frmCalif.lstNuevoMod.style.visibility    = "visible"; 
    document.frmCalif.lstNuevaEva.style.visibility    = "visible"; 
    document.frmCalif.txtNuevaFecha.style.visibility  = "visible"; 
    document.frmCalif.lstNuevaNota.style.visibility   = "visible"; 
    document.frmCalif.btnAnadir.style.visibility      = "visible"; 
}

function editaCalif(codCalif)
{
    var validaCampos = true;
    var valActivo    = "";
		
    if(Trim(eval("document.frmCalif.txtFecha" + codCalif).value) == "")
    {
    	validaCampos = false;
    }
    
    if(Trim(eval("document.frmCalif.lstNota"   + codCalif).value) == "-1")
    {
    	validaCampos = false;
    }
	
	
	
    if(validaCampos)
    {    			
		document.frmCalif.action = "../ModCalifServlet?codEdi=<%=codEdi%>" + 
                                                     "&codInt=<%=codAlu%>" + 
                                                     "&codMod=" + eval("document.frmCalif.hidMod"   + codCalif).value +
                                                     "&eva="    + eval("document.frmCalif.hidEva"   + codCalif).value +													
													 "&valInfCalif=<%=indInf%>"   ;
                                                            

        document.frmCalif.submit();
    }
    else
    {
        alert("Valide los valores introducidos");
    }
}

function bajaCalif(codCalif)
{
    if(confirm("¿Desea eliminar esta calificación?"))
    {
		document.frmCalif.action = "../BajaCalifServlet?codEdi=<%=codEdi%>" + 
                                                      "&codInt=<%=codAlu%>" +
													  "&codMod=" + eval("document.frmCalif.hidMod"   + codCalif).value +
                                                      "&eva="    + eval("document.frmCalif.hidEva"   + codCalif).value +		 	
													  "&valInfCalif=<%=indInf%>"   ; 
                                                     
		document.frmCalif.submit();
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

function cargarCombos()
{
	cargarCombosModulosEdicion();
}


</script>
</head>

<body class="fondoFormularios" onload="cargarCombos();">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta de la calificación")</script>
<%}%>

<%if(errEdi){%>
<script>alert("Se produjo un error\n en la edición de la calificación")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
<script>alert("La calificación se borró correctamente ")</script>
    <%}else{%>
<script>alert("La calificación no pudo ser borrada ")</script>
<%}}%>

<form action="" method="post" name="frmCalif" id="frmCalif">
<table style="width:85%" border="0" align="center" class="tablaListados">
  <tr class="tablaListados">
      <td colspan="11" class="tdDef"><strong>
              <%="Calificaciones de " + AlumnosGestion.devolverDatosAlumno(codAlu).getNombre() + " " + AlumnosGestion.devolverDatosAlumno(codAlu).getAp1Alu() 
          + " de la edición " + EdicionesGestion.devolverDatosEdi(codEdi).getDescripcion() 
+ " del curso " + CursosGestion.devolverDatosCurso(EdicionesGestion.devolverDatosEdi(codEdi).getIdCur()).getNomCur()%>
          <input name="txtCodAlu" type="hidden" id="txtCodAlu" value="<%=codAlu%>" />
          <input name="txtCodEdi" type="hidden" id="txtCodEdi" value="<%=codEdi%>" /></strong></td>
    </tr>
  <tr class="tablaListados">
    <td style="width:326" class="tdDef"><strong>M&oacute;dulos</strong></td>
    <td style="width:190" class="tdDef"><strong>Evaluaci&oacute;n</strong></td>
    <td colspan="2" class="tdDef"><strong>Fecha</strong></td>
    <td colspan="5" class="tdDef"><strong>Nota</strong></td>
    <td colspan="2" class="tdDef">
    	<img src="../imagenes/newhab.png" style="width:30" height="30" onclick="habilitaAlta();" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      califVO = (CalificacionesVO) vecCalif.elementAt(ind);
  %>
  <tr class="tdDef">
      <td class="tdDef"><%=ModulosGestion.devolverDatosModulo(califVO.getIdMod()).getNombre()%>
          <input type="hidden" name="hidMod<%=califVO.devolverClave()%>" id="hidMod<%=califVO.devolverClave()%>" value="<%=califVO.getIdMod()%>" /></td>
    <td class="tdDef"><%=califVO.getEvaluacion() + "ª evaluación"%>
      <input type="hidden" name="hidEva<%=califVO.devolverClave()%>" id="hidEva<%=califVO.devolverClave()%>" value="<%=califVO.getEvaluacion()%>" /></td>
      <td colspan="2" class="tdDef"><input class="claseForFecha" name="txtFecha<%=califVO.devolverClave()%>" type="text" disabled="disabled" id="txtFecha<%=califVO.devolverClave()%>" size="10" maxlength="10" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(califVO.getFecha())%>" 
    onchange="document.frmCalif.hidFecha<%=califVO.devolverClave()%>.value=document.frmCalif.txtFecha<%=califVO.devolverClave()%>.value;" />
      <input name="hidFecha<%=califVO.devolverClave()%>" type="hidden" id="hidFecha<%=califVO.devolverClave()%>" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(califVO.getFecha())%>" /></td>
      <td colspan="4">
      <select name="lstNota<%=califVO.devolverClave()%>" id="lstNota<%=califVO.devolverClave()%>" disabled="disabled">
      	<option value="-1" selected="selected">Seleccione...</option>
        <option value="0">No Apto (0)</option>
        <option value="1">No Apto (1)</option>
        <option value="2">No Apto (2)</option>
        <option value="3">No Apto (3)</option>
        <option value="4">No Apto (4)</option>
        <option value="5">Aprobado</option>
        <option value="6">Bien</option>
        <option value="7">Notable (7)</option>
        <option value="8">Notable (8)</option>
        <option value="9">Sobresaliente (9)</option>
        <option value="10">Sobresaliente (10)</option>
      </select></td>
      <script>$("#lstNota<%=califVO.devolverClave()%>").val("<%=califVO.getNota()%>");</script>
      <td style="width:24" class="center"><input name="btnEdiCalif<%=califVO.devolverClave()%>" type="button"  id="btnEdiCalif<%=califVO.devolverClave()%>" value="E" onclick="editaCalif('<%=califVO.devolverClave()%>')" disabled="disabled"/></td>
      <td style="width:32"><img src="../imagenes/editar.png" style="width:30" height="30" onclick="habilitaEdi('<%=califVO.devolverClave()%>');" style="cursor:pointer"/></td>
      <td style="width:95" class="center">
        <img src="../imagenes/papelera.png" style="width:30" height="30" onclick="bajaCalif('<%=califVO.devolverClave()%>');" style="cursor:pointer"/> 
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td class="tdDef"><span id="valMod">
        <select name="lstNuevoMod" id="lstNuevoMod" style="visibility:hidden">
        </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td class="tdDef"><span id="valEva">
      <select name="lstNuevaEva" id="lstNuevaEva" style="visibility:hidden">
        <option value="-1" selected="selected">Seleccione...</option>
        <option value="1">1ª Evaluación</option>
        <option value="2">2ª Evaluación</option>
        <option value="3">3ª Evaluación</option>
        <option value="4">4ª Evaluación</option>
        <option value="5">5ª Evaluación</option>
        <option value="6">6ª Evaluación</option>
        <option value="7">7ª Evaluación</option>
        <option value="8">8ª Evaluación</option>
        <option value="9">9ª Evaluación</option>
        <option value="10">10ª Evaluación</option>
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td height="26" colspan="2" class="tdDef">
    <span id="valFecha">
            <input class="claseForFecha" name="txtNuevaFecha" type="text" id="txtNuevaFecha" size="10" maxlength="10" style="visibility:hidden" onchange=			"hidNuevaFecha.value=txtNuevaFecha.value;"/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>      
      <input name="hidNuevaFecha" type="hidden" id="hidNuevaFecha"  /></td>
    <td colspan="4"><span id="valNota">
      <select name="lstNuevaNota" id="lstNuevaNota" style="visibility:hidden" onchange="cargarComboCursos();">
        <option value="-1" selected="selected">Seleccione...</option>
        <option value="0">No Apto (0)</option>
        <option value="1">No Apto (1)</option>
        <option value="2">No Apto (2)</option>
        <option value="3">No Apto (3)</option>
        <option value="4">No Apto (4)</option>
        <option value="5">Aprobado</option>
        <option value="6">Bien</option>
        <option value="7">Notable (7)</option>
        <option value="8">Notable (8)</option>
        <option value="9">Sobresaliente (9)</option>
        <option value="10">Sobresaliente (10)</option>
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td colspan="3"><span class="center">
      <input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Registro" style="visibility:hidden" onclick="validaForm();"/>
    </span></td>
    </tr>
  <tr>
    <td height="30" colspan="7" class="center">
      <%if( indInf >= 8){%>
      <img src="../imagenes/btnprev.png" style="width:35" height="35" onclick="window.open('gestionCalifi.jsp?codInt=<%=codAlu%>&codEdi=<%=codEdi%>&valInfCalif=<%=(indInf - 8)%>','_self','');">
      <%}%>
      </td>
    <td colspan="2" class="center">&nbsp;</td>
    <td colspan="2" class="center"><%if( indSup < vecCalif.size()){%>
      <img src="../imagenes/btnsig.png" style="width:35" height="35" onclick="window.open('gestionCalifi.jsp?codInt=<%=codAlu%>&codEdi=<%=codEdi%>&valInfCalif=<%=(indInf + 8)%>','_self','');"/>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="11" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("valMod", {invalidValue:"-1"});
var sprytextfield1 = new Spry.Widget.ValidationTextField("valFecha", "date", {format:"dd/mm/yyyy"});
var spryselect2 = new Spry.Widget.ValidationSelect("valEva", {invalidValue:"-1"});
var spryselect3 = new Spry.Widget.ValidationSelect("valNota", {invalidValue:"-1"});
</script>
</body>
</html>