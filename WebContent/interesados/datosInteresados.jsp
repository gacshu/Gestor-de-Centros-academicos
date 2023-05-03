<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>


<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>

<html>

<head>
<title>Alta Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
//Carga de datos de session del alumno
AlumnosVO aluVO          = new AlumnosVO();
String    valIniList     = "0";

//Comprueba que si viene del servlet de alta de alumnos o de la lista de búsqueda de alumnos
if(session.getAttribute("datosAlumnoAlta") != null)
{
    aluVO = (AlumnosVO) session.getAttribute("datosAlumnoAlta");
    session.removeAttribute("datosAlumnoAlta");
}
else
{
    if(request.getParameter("codInt") != null)
    {
        aluVO = AlumnosGestion.devolverDatosAlumno(request.getParameter("codInt"));
    }
}

if(request.getParameter("ind") != null)
{
    valIniList = request.getParameter("ind");
}


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
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

<body class="fondoFormularios">
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
        $( "#matAlumno" ).dialog({
           autoOpen: false,
            height: 400,
            width: 850,
            modal: true,
           	buttons:[ {id: "btnMatAlu",text: "Matricular", click: function() 
				{
					fraMat.validarForm(); 
				}},
					{id: "btnCancelMat", text: "Cerrar", click: function() 
				{ 
					document.getElementById('fraDatMat').src = "./matriculasFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>";
					$( "#btnMatAlu" ).button( "option", "disabled", false );
					$( "#btnMatAlu" ).button( "refresh" );
					$(this).dialog("close"); 
				}
			}] ,
			
            close: function() {
				$( "#btnMatAlu" ).button( "option", "disabled", false );
				$( "#btnMatAlu" ).button( "refresh" );
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
	
	$(function() {
        $( "#bajMatAlumno" ).dialog({
           autoOpen: false,
            height: 375,
            width:  850,
            modal: true,
           	buttons:[ {id: "btnBajMatAlu",text: "Cambiar", click: function() 
				{
					fraBajMat.validarForm(); 
				}},
					{id: "btnCancelBajMat", text: "Cerrar", click: function() 
				{ 
					document.getElementById('fraDatMat').src = "./matriculasFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>";
					$( "#btnBajMatAlu" ).button( "option", "disabled", false );
					$( "#btnBajMatAlu" ).button( "refresh" );
					$(this).dialog("close"); 
				}
			}] ,
			
            close: function() {
				$( "#btnBajMatAlu" ).button( "option", "disabled", false );
				$( "#btnBajMatAlu" ).button( "refresh" );
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
	
	$(function() {
        $( "#liqRec" ).dialog({
           autoOpen: false,
            height: 375,
            width:  850,
            modal: true,
           	buttons:[ {id: "btnLiqRec",text: "Liquidar Recibo", click: function() 
				{
					fraLiqRec.validarForm(); 
				}},
					{id: "btnCancelLiqRec", text: "Cerrar", click: function() 
				{ 
					$( "#btnLiqRec" ).button( "option", "disabled", false );
					$( "#btnLiqRec" ).button( "refresh" );
					$(this).dialog("close"); 
				}
			}] ,
			
            close: function() {
				$( "#btnCancelLiqRec" ).button( "option", "disabled", false );
				$( "#btnCancelLiqRec" ).button( "refresh" );
                allFields.val( "" ).removeClass( "ui-state-error" );
            }
        });
    });
	
	$(function() {
        $( "#altaCurso" ).dialog({
           autoOpen: false,
            height: 700,
            width:  500,
            modal: true,
           	buttons:[ {id: "btnVerDatosCurso",text: "Datos Curso", click: function() 
				{
					fraAnaCur.mostrarDatosCurso(); 
				}},
					{id: "btnAnadir", text: "Añadir Curso", click: function() 
				{ 
					fraAnaCur.insertarCurso(); 
					
				}
			}] ,
			
            close: function() {
                document.getElementById('fraDatCursos').src = "./cursosFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>";
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
function cargaPaginaAltaEmpresa()
{
	document.getElementById("fraLogos").src="../empresas/altaEmpInter.jsp?urlProc=2";
	$("#nuevaEmpresa").dialog( "open" );
}

function cerrarDialogo(nuevaEmpresa)
{
	document.getElementById("fraDatPer").contentWindow.cargaComboEmpresas(nuevaEmpresa);
	$("#nuevaEmpresa").dialog( "close" );
}

function cargaPaginaMat(codAlu, codCurso)
{
	document.getElementById("fraMat").src="./matAlu.jsp?codAlu=" + codAlu + "&codCur=" + codCurso;
	$("#matAlumno").dialog( "open" );
}

function cerrarDialogoMat()
{
	//recargar pestaña matriculas
	$("#matAlumno").dialog( "close" );
}

function cargaPaginaBajMat(codAlu, codEdi)
{
	document.getElementById("fraBajMat").src="./bajMatAlu.jsp?codAlu=" + codAlu + "&codEdi=" + codEdi;
	$("#bajMatAlumno").dialog( "open" );
}

function cerrarDialogoBajMat()
{
	//recargar pestaña matriculas
	$("#bajMatAlumno").dialog( "close" );
}

function cargaPagRec(codAlu)
{
	document.getElementById("fraLiqRec").src="../ediciones/liqRec.jsp?codAlu=" + codAlu;
	$("#liqRec").dialog( "open" );
}

function cerrarDialogoPagRec()
{
	//recargar pestaña matriculas
	$("#liqRec").dialog( "close" );
}


function cargaAltaCur(codAlu)
{
	document.getElementById("fraAnaCur").src="./anaCurAlu.jsp?codInt=" + codAlu;
	$("#altaCurso").dialog( "open" );
}

function cerrarDialogoAltaCur()
{
	//recargar pestaña matriculas
	$("#altaCurso").dialog( "close" );
}



function desactivaBtnMat()
{
	$( "#btnMatAlu" ).button( "option", "disabled", true );
	$( "#btnMatAlu" ).button( "refresh" );
}


function desactBotBaja()
{
	$( "#btnBajMatAlu" ).button( "option", "disabled", true );
	$( "#btnBajMatAlu" ).button( "refresh" );
}

function desactBotRec()
{
	$( "#btnLiqRec" ).button( "option", "disabled", true );
	$( "#btnLiqRec" ).button( "refresh" );
}


</script>

<div id="nuevaEmpresa" title="Crear Nueva Empresa">
   <iframe name="fraLogos"     id="fraLogos"     frameborder="0" src="../empresas/altaEmpInter.jsp?urlProc=2" width="100%" height="700" scrolling="no"> </iframe>
</div>

<div id="matAlumno" title="Matricular Alumno">
   <iframe name="fraMat"     id="fraMat"     frameborder="0" src="" width="100%" height="300" scrolling="no"> </iframe>
</div>

<div id="bajMatAlumno" title="Baja Alumno Edición">
   <iframe name="fraBajMat"     id="fraBajMat"     frameborder="0" src="" width="100%" height="275" scrolling="no"> </iframe>
</div>

<div id="liqRec" title="Liquidar Recibos">
   <iframe name="fraLiqRec"     id="fraLiqRec"     frameborder="0" src="" width="100%" height="275" scrolling="no"> </iframe>
</div>

<div id="altaCurso" title="Añadir curso interes alumno">
   <iframe name="fraAnaCur"     id="fraAnaCur"     frameborder="0" src="" width="100%" height="600" scrolling="no"> </iframe>
</div>

<form action="datosInteresados.jsp" method="post" name="frmFichaInteresado" target="_self" id="frmFichaInteresado">
  <table width="100%" border="0">
  <tr class="thDef">
    <th height="50" colspan="3" scope="col">Ficha Interesado</th>
  </tr>
  <tr>
    <td width="38%">
      <span id="txtValNombre">
      <input name="txtNombre" type="text" class="tdDef" id="txtNombre" value="<%=aluVO.getNombre()%>" size="50" maxlength="50" />
      <span class="textfieldRequiredMsg">*.</span><span class="textfieldInvalidFormatMsg">*</span></span>
    </td>
    <td width="41%"><span id="txtValApe">
    <input name="txtApe" type="text" class="tdDef" id="txtApe" value="<%=aluVO.getAp1Alu()%>" size="50" maxlength="50" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td width="21%" class="tdDef"><span id="txtValFecNac">
    <label>Fecha Nac.
        <input name="txtFecNac" type="text" id="txtFecNac" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(aluVO.getFecNac())%>" />
    </label>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*.</span></span></td>
  </tr>
</table>
<br><br>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <th class="tdDef" scope="col"><div id="panelFichasAlu" class="TabbedPanels">
      <ul class="TabbedPanelsTabGroup">
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Datos Personales</span></li>
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Cursos</span></li>
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Matr&iacute;culas</span></li>
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Calificaciones</span></li>
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Clases individuales</span></li>
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Faltas</span></li>
<li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Adaptaci&oacute;n Curricular</span></li>
<li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Trastornos</span></li>
        <li class="TabbedPanelsTab" tabindex="0"><span class="colorTextoBotPest">Seguimientos</span></li>
</ul>
      <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent">
          <iframe class="colorFondoPrincipalPestana" id="fraDatPer" frameborder="0" src="datPerFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
        </div>
        <div class="TabbedPanelsContent">
          <iframe class="colorFondoPrincipalPestana" id="fraDatCursos" frameborder="0" src="cursosFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
        </div>
        <div class="TabbedPanelsContent">
          <iframe class="colorFondoPrincipalPestana" id="fraDatMat" frameborder="0" src="matriculasFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
        </div>
        <div class="TabbedPanelsContent">
          <iframe class="colorFondoPrincipalPestana" id="fraDatMat" frameborder="0" src="califFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
        </div>
        <div class="TabbedPanelsContent">
          <iframe class="colorFondoPrincipalPestana" id="fraClaInd" frameborder="0" src="clasIndFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
        </div>
        <div class="TabbedPanelsContent">
        <iframe class="colorFondoPrincipalPestana" id="fraClaInd" frameborder="0" src="faltasFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
        </div>
<div class="TabbedPanelsContent">
  <iframe class="colorFondoPrincipalPestana" id="fraDatAdapCur" frameborder="0" src="adapCurFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
      </div>
<div class="TabbedPanelsContent">
  <iframe class="colorFondoPrincipalPestana" id="fraDatTrastornos" frameborder="0" src="trastornosFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
    </div>
        <div class="TabbedPanelsContent">
          <iframe class="colorFondoPrincipalPestana" id="fraDatSeg" frameborder="0" src="../seguimientos/verSegAlu.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" > </iframe>
        </div>
</div>
</div>
  </th>
  </tr>
  <tr>
    <th align="left" scope="col">&nbsp;</th>
  </tr>
  <tr>
      <td align="left" class="cellBtnSub" scope="col">
        <input type="button" name="btnVolver" id="btnVolver" value="Volver Lista" onclick="window.open('./RBInteresado.jsp?ind=<%=valIniList%>','_self','')" />
      </td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre", "none");
var sprytextfield2 = new Spry.Widget.ValidationTextField("txtValApe", "none");
var sprytextfield3 = new Spry.Widget.ValidationTextField("txtValFecNac", "date", {format:"dd/mm/yyyy", hint:"dd/mm/yyyy", isRequired:false});
var TabbedPanels1  = new Spry.Widget.TabbedPanels("panelFichasAlu");
</script>
</body>
</html>