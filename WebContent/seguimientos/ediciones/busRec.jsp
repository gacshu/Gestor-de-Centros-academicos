<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Busqueda Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios" onload="cargaCombos();">
<script src="../js/validaGlobal.js"></script>

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
                yearRange:       "c-25:c+0",
                showButtonPanel: true,
                onClose:         function( selectedDate ) {
                                    $( "#txtFechaDe" ).datepicker( "option", "maxDate", $( "#txtFechaA" ).datepicker( "getDate"));
                                    $( "#txtFechaA" ).datepicker( "option", "minDate", $( "#txtFechaDe" ).datepicker( "getDate")); 
                                }

        });


});
</script>

<script language="JavaScript" type="text/JavaScript">
function validaForm()
{
	formValido = true;
	
	//Realiza el submit si se valida el formulario
	if(formValido)
	{
		frmBusRec.submit();
	}
	
	
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

function cargarComboCentros(idCentro)
{
	$.ajax({
	  url: "../CargaComboCentrosServlet?valSel=" + idCentro,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstCentro").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los centros");
	  }
	});
}

function cargarComboAlumnos(idAlu)
{
	$.ajax({
	  url: "../CargarComboAlumnosServlet?valSel=" + idAlu,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstAlum").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los alumnos");
	  }
	});
}



function cargaCombos()
{
	$(document).ready(function() {
    // put all your jQuery goodness in here.
		cargarComboTipoCurso();
		cargarComboCentros("-99");
		cargarComboAlumnos();
	});
}
</script>


<form action="../ImpHistRecServlet" method="post" name="frmBusRec" target="new" id="frmBusRec">
<table width="100%" border="0">
  <tr>
    <th colspan="2" class="thDef" scope="col">Búsqueda Recibos</th>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td width="18%" height="21"><span class="tdDef">Tipo Curso</span></td>
    <td><select name="lstTipCurso" id="lstTipCurso" onchange="cargarComboCursos();" style="width:250px">
    </select></td>
    </tr>
  <tr>
    <td height="30"><span class="tdDef">
      <label for="chkBusCur"></label>
      B&uacute;squeda por Curso</span></td>
    <td><select name="lstCursos" id="lstCursos"  style="width:353px">
      </select></td>
    </tr>
  <tr>
    <td height="21"><span class="tdDef">Estado</span></td>
    <td><label for="lstEstado"></label>
      <select name="lstEstado" id="lstEstado">
        <option value="0" selected="selected">-----</option>
        <option value="2">No pagado</option>
        <option value="1">Pagado</option>
      </select></td>
    </tr>
  <tr>
    <td height="20"><span class="tdDef">Fecha de emisi&oacute;n</span></td>
    <td class="tdDef">de 
      <label for="txtFechaDe"></label>
      <input class="claseForFecha" name="txtFechaDe" type="text" id="txtFechaDe" size="15" maxlength="10" onchange="document.getElementById('hidFechaDe').value = document.getElementById('txtFechaDe').value;" />
      a
      <input class="claseForFecha" name="txtFechaA" type="text" id="txtFechaA" size="15" maxlength="10"  onchange="document.getElementById('hidFechaA').value = document.getElementById('txtFechaA').value;"/>
      <input type="hidden" name="hidFechaDe" id="hidFechaDe" />
      <input type="hidden" name="hidFechaA" id="hidFechaA" /></td>
    </tr>
  <tr>
    <td height="21"><span class="tdDef">
      B&uacute;squeda por alumno</span></td>
    <td><select name="lstAlum" id="lstAlum"  style="width:250px">
    </select></td>
    </tr>
  <tr>
    <td height="21"><span class="tdDef">
      B&uacute;squeda por centro</span></td>
    <td><select name="lstCentro" size="1" id="lstCentro" tabindex="4"></select></td>
    </tr>
  <tr>
    <td colspan="2" class="tdDef"><input name="chkDomic" type="checkbox" id="chkDomic" value="true" />
      Domiciliado</td>
    </tr>
  <tr>
    <td colspan="2" class="cellBtnSub"><input class="cellBtnSub" type="button" name="btnBuscar" id="btnBuscar" value="Buscar"  onclick="validaForm();" tabindex="9"/></td>
    </tr>
</table>
</form>
</body>
</html>