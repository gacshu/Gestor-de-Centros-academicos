<%@page import="es.jahernandez.gestion.CursosAluGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
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
<title>Cursos interesado</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Carga de datos del formulario de búsqueda
AlumnosVO    aluVO          = new AlumnosVO();
Vector       listCurAlu     = new Vector();
CursosAluVO  curAluVO       = null;
int          errAltaCurso   = 0;
int          errBajaCurso   = 0;
boolean      insCor         = false;


if(request.getParameter("codInt") != null)
{
    aluVO = AlumnosGestion.devolverDatosAlumno(request.getParameter("codInt"));
    listCurAlu = CursosAluGestion.devolverCursosAlu(aluVO.getIdAlu());
}

//Se cargan los posibles errores que se pueden producir dando de alta cursos
if(request.getParameter("errInsCur") != null)
{
    errAltaCurso = new Integer(request.getParameter("errInsCur")).intValue();
}

if(request.getParameter("insCor") != null)
{
     insCor = true;
	
}

%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Cursos.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Niveles.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/TipoCurso.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/engine.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/util.js'></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
    
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/base/jquery.ui.dialog.css"/>
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/base/jquery.ui.all.css"/>


<!--Funciones jquery ui-->
<script>
$(function() {
        $( "#datosCurso" ).dialog({
           autoOpen: false,
            height: 200,
            width: 300,
            modal: true,
            close: function() {
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
 
    });
    
 </script>

<script>  
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
	  url: "../CargaComboCursosServlet?codTipo=" + idTipCurso + "&valSel=" + idCurso,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstCursos").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los cursos");
	  }
	});
}


function cargarComboNiveles(idNivel)
{   
	
	var idCodCur = $("#lstCursos").val();
	
	$.ajax({
	  url: "../CargaComboNivelesServlet?codCur=" + idCodCur + "&valSel=" + idNivel,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstNiveles").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los niveles");
	  }
	});
}
      
function mostrarDatosCurso()
{   
	var codCurso = $("#lstCursos").val();
	
	$.ajax({
	  url: "../MuestraDatosCursoServlet?codCur=" + codCurso,
	  success: function(data) {
		$("#tablaDatosCursos").html(data);  
		$("#datosCurso" ).dialog( "open" );
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los  datos del curso");
	  }
	});
}

            
function insertarCurso()
{
	if(spryselect1.validate())
	{
		frmGestionaCursos.action = "../DarAltaCursoInteresAlumnoServlet?codInteresado=<%=aluVO.getIdAlu()%>";
		frmGestionaCursos.submit();
	}
	else
	{
		alert("Debe seleccionar un curso");	
	}
}			
						
</script>

<%if(errAltaCurso == -2){%>
<script>
    alert("El curso introducido está duplicado");
</script>
<%}
if(errAltaCurso == -1){%>
<script>
    alert("Se produjo un error al dar de alta el curso");
</script>
<%}if(errBajaCurso == -1){%>
<script>
    alert("Se produjo un error al dar de baja el curso");
</script>
<%}%>

<%if(insCor){%>
<script>
    alert("El curso fue introducido correctamente");
</script>
<%}%>




<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
</head>

<body onload="cargarComboTipoCurso();" class="fondoFormularios" >

<div id="datosCurso" title="Curso">
   <div id="infCurso">
   		<table id="tablaDatosCursos" class="tdDef">
        </table>
  </div>
</div>
<form action="./cursosFichaAlumno.jsp" method="post" name="frmGestionaCursos" target="_self" id="frmGestionaCursos">
  <table style="width:33%" border="0" align="center">
  		<tr>
  			<td colspan="3" class="thDef">Cursos Disponibles</td>
  		</tr>
  		<tr>
          	<td style="width:41%" class="tdDef">Tipo</td>
          	<td style="width:59%" colspan="2">
  				<select name="lstTipCurso" class="tdDef" id="lstTipCurso" style="width:250px" onchange="cargarComboCursos();"></select>
  		  	</td>
  		</tr>
  		<tr>
  			<td valign="top" class="tdDef">Cursos</td>
            <td colspan="2">
  				<span id="lstValCurso">
           		<select name="lstCursos" size="16" id="lstCursos" onchange="cargarComboNiveles();" style="width:353px"></select>
             	 	<span class="selectRequiredMsg">*.</span>
  				</span></td>
  		</tr>
  		<tr>
  				<td valign="top" class="tdDef">Niveles</td>
  				<td colspan="2">
  					<select name="lstNiveles" size="5" class="tdDef" id="lstNiveles" style="width:250px"></select></td>
  		</tr>
  </table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("lstValCurso");
</script>
</body>
</html>