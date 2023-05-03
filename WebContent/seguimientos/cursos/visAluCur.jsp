<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<title>Untitled Document</title>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>

</head>

<script>  
function cargarComboTipoCurso(idTipCur)
{
	$.ajax({
	  url: "../CargaTipoCursoServlet?valSel=" + idTipCur,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstTiposCurso").html(data);
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

function cargarComboCursos(idCurso)
{		
	var idTipCurso = $("#lstTiposCurso").val();
	var idCentro   = $("#lstCentros").val();
	
	//Limpiamos primero los select de curso y nivel
	$("#lstCursos").empty();
	$("#lstNiveles").empty();

	$.ajax({
	  url: "../CargaComboCursosServlet?codTipo=" + idTipCurso +  "&codCentro=" + idCentro + "&valSel=" + idCurso,
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

function buscaAlumnosTipoCurso()
{	
	if(spryselect1.validate())
	{
		frmAluCur.action="../BuscaAlumnosTipoCursoServlet";	
		frmAluCur.submit();		
	}
	else
	{
		alert("Debe seleccionar un Tipo de Curso");
	}
}            

function buscaAlumnosCurso()
{
	if(spryselect3.validate())
	{
		frmAluCur.action="../BuscaAlumnosCursoServlet";	
		frmAluCur.submit();		
	}
	else
	{
		alert("Debe seleccionar un Curso");
	}
}

</script>


<body class="fondoFormularios" onload="cargarComboTipoCurso();cargarComboCentros('-99');">
<form action="" method="post" name="frmAluCur" target="_self" id="frmAluCur">
<table width="100%" border="0" class="tdDef">
  <tr>
    <td colspan="5" class="thDef">Alumnos por curso</td>
  </tr>
  <tr>
    <td width="9%">&nbsp;</td>
    <td width="40%">&nbsp;</td>
    <td width="2%">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td>Tipo Curso</td>
    <td colspan="2"><span id="lstValTiposCurso">
      <select name="lstTiposCurso" id="lstTiposCurso" onchange="cargarComboCursos();">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">Please select an item.</span></span>
      <input name="btnVerAluTipCur" type="button" class="cellBtnSub" id="btnVerAluTipCur" onclick="buscaAlumnosTipoCurso();"  value="Ver Alumnos" /></td>
    <td width="5%">Centro</td>
    <td width="44%"><span id="lstValCentros">
      <select name="lstCentros" id="lstCentros"  onchange="cargarComboCursos();">
      </select>
      <span class="selectRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td>Curso</td>
    <td>&nbsp;</td>
    <td colspan="3">Niveles</td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td><span id="lstValCursos">
      <select name="lstCursos" size="5" id="lstCursos" onchange="cargarComboNiveles();" style="width:400px">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">*</span></span></td>
    <td>&nbsp;</td>
    <td colspan="2"><span id="lstValNiveles">
      <select name="lstNiveles" size="5" id="lstNiveles" style="width:200px">
      </select>
      <span class="selectRequiredMsg">*</span></span></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="5" class="cellBtnSub"><input name="btnVerAluCur" type="button" class="cellBtnSub" id="btnVerAluCur" onclick="buscaAlumnosCurso();" value="Buscar" /></td>
    </tr>
</table>


</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("lstValTiposCurso", {invalidValue:"-1"});
var spryselect2 = new Spry.Widget.ValidationSelect("lstValCentros");
var spryselect3 = new Spry.Widget.ValidationSelect("lstValCursos", {invalidValue:"-1"});
var spryselect4 = new Spry.Widget.ValidationSelect("lstValNiveles", {isRequired:false});
</script>
</body>
</html>