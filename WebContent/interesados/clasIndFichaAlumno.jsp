<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.ClasesIndivGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "es.jahernandez.accesodatos.ClasesIndivDAO"%>
<%@ page import = "es.jahernandez.datos.ClasesIndivVO"%>
<%@ page import  = "java.util.Vector"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.jahernandez.accesodatos.CursosDAO"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAut.jsp"%>
    
<%
   ClasesIndivVO clasIndVO  = new ClasesIndivVO();
   Vector        vecClasInd = null; 

   int      indInf       = 0;
   int      indSup       = 0;
   String   codAlu       = "";
   boolean  errAlta      = false;
   boolean  errEdi       = false;
   int      resBor       = -99;
   int      annoAct      = new Integer(new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()) ));
   
   int      mesFiltro    = 0;
   int      annoFiltro   = 0;
   
   String   fecFilt      = ""; 
   boolean  mostrarTotal = false;
   
   String   tablaMeses[] = {"Enero" , "Febrero" , "Marzo" , "Abril", "Mayo" , "Junio" , "Julio" , "Agosto" , "Septiembre", "Octubre" , "Noviembre" , "Diciembre" };
   
   
   //Paginación de resultados
   if(request.getParameter("valInfClasInd") != null)
   {
            indInf = new Integer(request.getParameter("valInfClasInd")).intValue();
   }

   if(request.getParameter("strFecha") != null)
   {
            fecFilt = request.getParameter("strFecha").trim();
   }

   if(request.getParameter("codInt") != null)
   {
            codAlu     =  request.getParameter("codInt").trim();
            
            if(fecFilt.equals(""))
            {
                vecClasInd =  ClasesIndivGestion.devolverClasesIndAlu(codAlu);
            }
            else
            {
                vecClasInd = ClasesIndivGestion.devolverClasesIndAluMes(codAlu, fecFilt);
                mostrarTotal = true;
                mesFiltro = new Integer(fecFilt.substring(0,2)).intValue();
                annoFiltro = new Integer(fecFilt.substring(2,6)).intValue();
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

   indSup = indInf + 10;
   if(indSup > vecClasInd.size()) 
   {
        indSup = vecClasInd .size();
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
     validarCampos = sprytextfield1.validate() & sprytextfield2.validate() & spryselect1.validate();    
                
	if(validarCampos)
	{
		document.frmClasesInd.action = "../AltaClaseIndServlet?valInfClasInd=<%=indInf%>&strFecha=<%=fecFilt%>";
		document.frmClasesInd.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
	
}

function habilitaEdi(codClaseInd)
{
    eval("document.frmClasesInd.txtFecha"     + codClaseInd).disabled  = false; 
    eval("document.frmClasesInd.lstProf"      + codClaseInd).disabled  = false;       
    eval("document.frmClasesInd.txtTarifa"    + codClaseInd).disabled  = false;       
	eval("document.frmClasesInd.btnEdiClasInd"+ codClaseInd).disabled  = false;       
}


function habilitaAlta()
{
	document.frmClasesInd.lstTipCurso.style.visibility    = "visible"; 
	document.frmClasesInd.lstNuevoCurso.style.visibility  = "visible"; 
	document.frmClasesInd.txtNuevaFecha.style.visibility  = "visible"; 
	document.frmClasesInd.lstNuevoProf.style.visibility   = "visible"; 
	document.frmClasesInd.txtNuevaTarifa.style.visibility = "visible";
	document.frmClasesInd.btnAnadir.style.visibility      = "visible"; 
}

function editaClaseInd(codClaseInd)
{
    var validaCampos = true;
    var valActivo    = "";
	
	//Cambiamos la , por el . en tarifa
	eval("document.frmClasesInd.txtTarifa"   + codClaseInd).value = eval("document.frmClasesInd.txtTarifa"   + codClaseInd).value.replace(/,/g, '.');
	
    if(Trim(eval("document.frmClasesInd.txtFecha" + codClaseInd).value) == "")
    {
    	validaCampos = false;
    }
    
    if(Trim(eval("document.frmClasesInd.lstProf"   + codClaseInd).value) == "-1")
    {
    	validaCampos = false;
    }
	
	if(Trim(eval("document.frmClasesInd.txtTarifa"   + codClaseInd).value) == "")
    {   
		validaCampos = false;
    }
	if(isNaN(eval("document.frmClasesInd.txtTarifa"   + codClaseInd).value))
	{
		validaCampos = false;
	}
	
    if(validaCampos)
    {    			
		document.frmClasesInd.action = "../ModClaseIndServlet?txtCodClasInd=" + codClaseInd   + 
                                                            "&codInt=<%=codAlu%>"             + 
		            		                               	"&valInfClasInd=<%=indInf%>"      +
															"&strFecha=<%=fecFilt%>"          ;
		
		document.frmClasesInd.submit();
    }
    else
    {
        alert("Valide los valores introducidos");
    }
}

function bajaClaseInd(codClaseInd,valInfAdapCur ,codAlu)
{
    if(confirm("¿Desea eliminar esta clase individual?"))
    {
		document.frmClasesInd.action = "../BajaClaseIndServlet?codClaseInd="   + codClaseInd   +
                                                             "&codInt="        + codAlu        + 
                                                             "&valInfClasInd=" + valInfAdapCur +
														     "&strFecha=<%=fecFilt%>"          ;	
		document.frmClasesInd.submit();
    }
}


function cargarCombosProfesores(idProf)
{   
	$.ajax({
	  url: "../CargaComboProfesoresServlet?valSel=" + idProf,
	  success: function(data) {
		$(document).ready(function() {
			<%for(int ind=0; ind < vecClasInd.size(); ind++)
                          { ClasesIndivVO claIndAux = (ClasesIndivVO) vecClasInd.elementAt(ind);%>
                            $("#lstProf<%=claIndAux.getIdClaseInd()%>").html(data);
                            $("#lstProf<%=claIndAux.getIdClaseInd()%>").val("<%=claIndAux.getIdProf()%>");
			<%}%>
			$("#lstNuevoProf").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los profesores");
	  }
	});
}


function cargarComboTipoCurso(idTipCur)
{   
	$.ajax({
	  url: "../CargaTipoCursoServlet?valSel=" + idTipCur,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstTipCurso").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de cursos");
	  }
	});
}

function cargarComboCursos(idCurso)
{   
	
	var idTipCurso = $("#lstTipCurso").val();
	
	$.ajax({
	  url: "../CargaComboCursosServlet?codTipo=" + idTipCurso + "&muestraSelec=s&valSel=" + idCurso,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstNuevoCurso").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los cursos");
	  }
	});
}


function cargarCombos()
{
	cargarComboTipoCurso();
	cargarCombosProfesores();
}

function cargaFiltro()
{
	var fecEnvio = "";
	
	if(document.frmClasesInd.lstMesFilt.value  != "-1"  && 
	   document.frmClasesInd.lstAnnoFilt.value != "-1" )
	{
		fecEnvio = document.frmClasesInd.lstMesFilt.value + document.frmClasesInd.lstAnnoFilt.value;
	}
	
	window.open("clasIndFichaAlumno.jsp?codInt=<%=codAlu%>&strFecha=" + fecEnvio, "_self" ,"");
	
	
}



</script>
</head>

<body class="colorFondoPrincipalPestana" onload="cargarCombos();">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta de la clase")</script>
<%}%>

<%if(errEdi){%>
<script>alert("Se produjo un error\n en la edición de la clase")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
<script>alert("La clase se borró correctamente ")</script>
    <%}else{%>
<script>alert("La clase no pudo ser borrada ")</script>
<%}}%>

<form action="" method="post" name="frmClasesInd" id="frmClasesInd">
<table width="85%" border="0" align="center" class="tablaListados">
  <tr>
  	<td width="35%" class="tdDef"><select name="lstMesFilt" id="lstMesFilt" on onchange="lstAnnoFilt.value=<%=annoAct%>">
  	  <option value="-1">Seleccione...</option>
  	  <option value="01">Enero</option>
  	  <option value="02">Febrero</option>
  	  <option value="03">Marzo</option>
  	  <option value="04">Abril</option>
  	  <option value="05">Mayo</option>
  	  <option value="06">Junio</option>
  	  <option value="07">Julio</option>
  	  <option value="08">Agosto</option>
  	  <option value="09">Septiembre</option>
  	  <option value="10">Octubre</option>
  	  <option value="11">Noviembre</option>
  	  <option value="12">Diciembre</option>
	  </select>
  	  <select name="lstAnnoFilt" id="lstAnnoFilt">
  	    <option>Seleccione...</option>
            <%for(int ind = annoAct -50;ind < annoAct+50;ind++){%>
            <option value="<%=ind%>"><%=ind%></option>
            <%}%>
      </select></td>
    <td width="12%" class="tdDef">
    	<input type="button" name="btnVerRecibos" id="btnVerRecibos" value="Ver Clases Mes" onclick="cargaFiltro();"/>
    </td>
    <td width="21%" class="tdDef"></td>
    <td colspan="6" class="tdDef"><%if(mostrarTotal){%>
      <strong>Total mes de <%=tablaMeses[mesFiltro-1] + " de " + annoAct%></strong><%}%>
    </td>
    <td colspan="2" class="tdDef">
		<%if(mostrarTotal){%><input name="btnImpRecMesCIAlu" type="button" value="<%=ClasesIndivGestion.devolverImporteClaseAluMes(codAlu, fecFilt)%>" onclick="window.open('../ImpRecClasesIndivAlu?codInt=<%=codAlu%>&strFecha=<%=fecFilt%>','_recMesCI','');" /><%}%>
    </td>
  </tr>
  <tr class="tablaListados">
    <td width="35%" class="tdDef"><strong>Curso</strong></td>
    <td width="12%" class="tdDef"><strong>Fecha</strong></td>
    <td colspan="2" class="tdDef"><strong>Profesor</strong></td>
    <td colspan="5" class="tdDef"><strong>Tarifa</strong></td>
    <td colspan="2" class="tdDef"><img src="../imagenes/newhab.png" width="30" height="30" onclick="habilitaAlta();" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      clasIndVO = (ClasesIndivVO) vecClasInd.elementAt(ind);
  %>
  <tr class="tdDef">
      <td class="tdDef"><%=CursosGestion.devolverDatosCurso(clasIndVO.getIdCur()).getNomCur()%></td>
    <td class="tdDef"><input class="claseForFecha" name="txtFecha<%=clasIndVO.getIdClaseInd()%>" type="text" disabled="disabled" id="txtFecha<%=clasIndVO.getIdClaseInd()%>" size="10" maxlength="10" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(clasIndVO.getFecClase())%>" 
    onchange="document.frmClasesInd.hidFecha<%=clasIndVO.getIdClaseInd()%>.value=document.frmClasesInd.txtFecha<%=clasIndVO.getIdClaseInd()%>.value;" />
      <input name="hidFecha<%=clasIndVO.getIdClaseInd()%>" type="hidden" id="hidFecha<%=clasIndVO.getIdClaseInd()%>" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(clasIndVO.getFecClase())%>" /></td>
      <td colspan="2" class="tdDef"><select name="lstProf<%=clasIndVO.getIdClaseInd()%>" id="lstProf<%=clasIndVO.getIdClaseInd()%>" disabled="disabled">
    </select></td>
      <td colspan="4"><input name="txtTarifa<%=clasIndVO.getIdClaseInd()%>" type="text" disabled="disabled" id="txtTarifa<%=clasIndVO.getIdClaseInd()%>" value="<%=new DecimalFormat("#######.##").format(clasIndVO.getTarifa())%>" size="10" maxlength="10" /></td>
      <td width="2%" class="center"><input name="btnEdiClasInd<%=clasIndVO.getIdClaseInd()%>" type="button"  id="btnEdiClasInd<%=clasIndVO.getIdClaseInd()%>" value="E" onclick="editaClaseInd('<%=clasIndVO.getIdClaseInd()%>')" disabled="disabled"/></td>
      <td width="3%"><img src="../imagenes/editar.png" width="30" height="30" onclick="habilitaEdi('<%=clasIndVO.getIdClaseInd()%>');" style="cursor:pointer"/></td>
      <td width="8%" class="center">
        <img src="../imagenes/papelera.png" width="30" height="30" onclick="bajaClaseInd('<%=clasIndVO.getIdClaseInd()%>','<%=indInf%>','<%=clasIndVO.getIdAlu()%>');" style="cursor:pointer"/> 
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td class="tdDef">
      <select name="lstTipCurso" id="lstTipCurso" style="visibility:hidden" onchange="cargarComboCursos();">
        </select><br>
      <span id="valCurso">
        <select name="lstNuevoCurso" id="lstNuevoCurso" style="visibility:hidden">
        </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td class="tdDef"><span id="valFecha">
            <input class="claseForFecha" name="txtNuevaFecha" type="text" id="txtNuevaFecha" size="10" maxlength="10" style="visibility:hidden" onchange="hidNuevaFecha.value=txtNuevaFecha.value;"/>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>      
      <input name="hidNuevaFecha" type="hidden" id="hidNuevaFecha"  /></td>
    <td height="26" colspan="2" class="tdDef"><select name="lstNuevoProf" id="lstNuevoProf" style="visibility:hidden">
    </select></td>
    <td colspan="4"><span id="valTarifa">
      <input name="txtNuevaTarifa" type="text" id="txtNuevaTarifa" size="10" maxlength="10" style="visibility:hidden" onchange="this.value=this.value.replace(/,/g, '.');" />
      <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span>      
      <input name="txtCodAlu" type="hidden" id="txtCodAlu" value="<%=codAlu%>" /></td>
    <td colspan="3"><span class="center">
      <input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Registro" style="visibility:hidden" onclick="validaForm();"/>
    </span></td>
    </tr>
  <tr>
    <td height="30" colspan="7" class="center">
      <%if( indInf >= 10){%>
      <img src="../imagenes/btnprev.png" width="35" height="35" onclick="window.open('clasIndFichaAlumno.jsp?codInt=<%=codAlu%>&strFecha=<%=fecFilt%>&valInfClasInd=<%=(indInf - 10)%>','_self','');">
      <%}%>
      </td>
    <td colspan="2" class="center">&nbsp;</td>
    <td colspan="2" class="center"><%if( indSup < vecClasInd.size()){%>
      <img src="../imagenes/btnsig.png" width="35" height="35" onclick="window.open('clasIndFichaAlumno.jsp?codInt=<%=codAlu%>&strFecha=<%=fecFilt%>&valInfClasInd=<%=(indInf + 10)%>','_self','');"/>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="11" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("valCurso", {invalidValue:"-1"});
var sprytextfield1 = new Spry.Widget.ValidationTextField("valFecha", "date", {format:"dd/mm/yyyy"});
var sprytextfield2 = new Spry.Widget.ValidationTextField("valTarifa", "currency");
</script>
</body>
</html>