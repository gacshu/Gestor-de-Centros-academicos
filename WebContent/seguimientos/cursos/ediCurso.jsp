<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<%@ page import = "java.util.Vector"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir Curso</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Control de caché 
response.setDateHeader ("Expires", -1); 
response.setHeader("Pragma","no-cache"); 
if(request.getProtocol().equals("HTTP/1.1")) 
 response.setHeader("Cache-Control","no-cache"); 


//Control de errores de alta
int         errCodEdi    = 0;
CursosVO    cursoVO      = new CursosVO();
CentrosVO   centrosVO    = new CentrosVO();
TipoCursoVO tipoCursosVO = new TipoCursoVO();

boolean     msgEdiCor    = false;
boolean     msgEdiCreada = false;


//Se cargan los datos del curso
if(request.getParameter("codCurso") != null)
{
    cursoVO = CursosGestion.devolverDatosCurso(request.getParameter("codCurso"));
}

//Se comprueba si vienen datos de el servlet de edicion de curso para recargar datos
//y los códigos de error
if(request.getParameter("errorEdiCode") != null)
{
    errCodEdi = new Integer(request.getParameter("errorEdiCode")).intValue();
}

if(request.getParameter("msgEdiCor") != null)
{
    msgEdiCor = true;
}

if(request.getParameter("msgEdiCreada") != null)
{
    msgEdiCreada = true;
}



if(session.getAttribute("ediCursoErr") != null)
{
    cursoVO = (CursosVO) session.getAttribute("ediCursoErr");
    session.removeAttribute("ediCursoErr");
}

%>


<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js"    type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryTabbedPanels.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>


</head>

<%if(errCodEdi == -1){%>
<script>
    alert("Se produjo un error en la edición del curso");
</script>
<%}%>
<%if(errCodEdi == -2){%>
<script>
    alert("Se produjo un error de acceso a la base de datos");
</script>
<%}%>

<%if(msgEdiCor){%>
<script>
    alert("El curso se editó correctamente");
</script>
<%}%>

<%if(msgEdiCreada){%>
<script>
    alert("La edición del curso se creo correctamente");
</script>
<%}%>

<!--Funciones jquery ui-->
<script>
	$(function() {
        $( "#lstAlumnos" ).dialog({
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
	
</script>

<script>
function validaForm()
{
	var validarCampos  = true;		
					
	validarCampos = sprytextfield1.validate() & spryselect1.validate()   &  
				    spryselect2.validate()    & sprytextarea1.validate() ;
				   
	if(validarCampos)
	{
		document.frmAltaCurso.action = "../EditarCursoServlet";	
		document.frmAltaCurso.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
	
}

function cargarComboTipoCurso(idTipCur)
{   
	$.ajax({
	  url: "../CargaTipoCursoServlet?valSel=" + idTipCur,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstTipoCurso").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de cursos");
	  }
	});
}

function cargarComboCentros(idCentro)
{
	$.ajax({
	  url: "../CargaComboCentrosServlet?valSel=" + idCentro,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstCentros").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los centros");
	  }
	});
		
}

function abrirListadoAluEdi(codEdi)
{
	document.getElementById("fraList").src="../ediciones/verAlumEdi.jsp?codEdi=" + codEdi;
	$("#lstAlumnos").dialog( "option" , "title" , "Listado Alumnos" );
	$("#lstAlumnos").dialog( "open" );
}

function abrirListadoAluBajEdi(codEdi)
{
	document.getElementById("fraList").src="../ediciones/verAlumBajEdi.jsp?codEdi=" + codEdi;
	$("#lstAlumnos").dialog( "option" , "title" , "Listado Alumnos Baja" );
	$("#lstAlumnos").dialog( "open" );
}

function cerrarListadoAluEdi()
{
	$("#lstAlumnos").dialog( "close" );
}


function abrirModEdi(codEdi)
{
	document.getElementById("fraMod").src="../ediciones/verModEd.jsp?codEdi=" + codEdi;
	$("#lstMod").dialog( "open" );
}

function cerrarModEdi()
{
	$("#lstMod").dialog( "close" );
}




function nuevaEdicion()
{
	//document.frmAltaCurso.action = "../ediciones/altaEdicion.jsp?codCurso=<%=cursoVO.getIdCur()%>" 
    //                                              + "&urlDes=1";
	/*
	document.frmAltaCurso.action = "../ediciones/listEdiCurso.jsp?codCur=<%=cursoVO.getIdCur()%>" 
                                                  + "&urlDes=1";											  
												  
	document.frmAltaCurso.submit();*/
}

</script>

<body class="fondoFormularios"  onload="cargarComboTipoCurso('<%=cursoVO.getTipCur()%>');cargarComboCentros('<%=cursoVO.getCenCurso()%>');">
<div id="lstAlumnos" title="Listado Alumnos">
   <iframe name="fraList"     id="fraList"     frameborder="0" src="" width="100%" height="500" scrolling="no"> </iframe>
</div>
<div id="lstMod" title="Listado Módulos">
   <iframe name="fraMod"     id="fraMod"     frameborder="0" src="" width="100%" height="500" scrolling="no"> </iframe>
</div>



<form  method="post" name="frmAltaCurso" id="frmAltaCurso">
<table width="90%" border="0">
  <tr>
    <td colspan="2" class="thDef">Datos Curso</td>
  </tr>
  <tr>
    <td width="13%" class="tdDef">Nombre</td>
    <td width="87%" class="tdDef"><span id="txtValNombre">
      <input name="txtNombre" type="text" id="txtNombre" tabindex="1" value="<%=cursoVO.getNomCur()%>" size="75" maxlength="150" />
      <span class="textfieldRequiredMsg">*</span></span>
      <input name="hidCodCurso" type="hidden" id="hidCodCurso" value="<%=cursoVO.getIdCur()%>" /></td>
    </tr>
  <tr>
    <td class="tdDef">Tipo</td>
    <td class="tdDef"><span id="valTipCurso">
      <select name="lstTipoCurso" id="lstTipoCurso" tabindex="2"></select>
      <span class="selectInvalidMsg">*</span></span></td>
    </tr>
  <tr>
    <td class="tdDef">Centro</td>
    <td class="tdDef"><span id="valCentro">
      <select name="lstCentros" id="lstCentros" tabindex="2"></select>
      <span class="selectInvalidMsg">*</span></span></td>
    </tr>
  <tr>
    <td valign="top" class="tdDef">Contenido</td>
    <td class="tdDef"><span id="valContenido">
      <textarea name="txtContenido" id="txtContenido" cols="75" rows="5" tabindex="4"><%=cursoVO.getContenido()%></textarea>
      <span class="textareaRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td colspan="2" align="center" valign="top">
      <input name="btnAceptar" type="button" class="cellBtnSub" id="btnAceptar" tabindex="7" value="Editar" onclick="validaForm();" />
   </td>
    </tr>
  </table>
  <div id="pestCurso" class="TabbedPanels">
    <ul class="TabbedPanelsTabGroup">
      <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Niveles</span></li>
      <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">M&oacute;dulos</span></li>
      <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Ediciones</span></li>
    </ul>
    <div class="TabbedPanelsContentGroup">
      <div class="TabbedPanelsContent"><iframe id="fraPestNiv" frameborder="0" src="gestionNiveles.jsp?codCurso=<%=cursoVO.getIdCur()%>" width="100%" height="500" > </iframe></div>
      <div class="TabbedPanelsContent">
        <iframe id="fraPestMod" frameborder="0" src="gestionModulos.jsp?codCurso=<%=cursoVO.getIdCur()%>" width="100%" height="500" > </iframe>
      </div>
      <div class="TabbedPanelsContent">
		<iframe id="fraPestMod" frameborder="0" src="../ediciones/listEdiCurso.jsp?codCur=<%=cursoVO.getIdCur()%>&urlDes=1" width="100%" height="500" > </iframe>     
      </div>
    </div>
  </div>
  <p>&nbsp;</p>
  
<p id="frmAltaCurso">&nbsp;</p>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre");
var spryselect1    = new Spry.Widget.ValidationSelect   ("valTipCurso", {isRequired:false, invalidValue:"-1"});
var spryselect2    = new Spry.Widget.ValidationSelect   ("valCentro", {invalidValue:"-1", isRequired:false});
var sprytextarea1  = new Spry.Widget.ValidationTextarea ("valContenido");
var TabbedPanels1  = new Spry.Widget.TabbedPanels("pestCurso");
</script>
</body>
</html>