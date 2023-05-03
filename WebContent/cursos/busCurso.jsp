<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Búsqueda Aula</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
</head>
<%
//Control de caché
response.setDateHeader ("Expires", -1); 
response.setHeader("Pragma","no-cache"); 
if(request.getProtocol().equals("HTTP/1.1")) 
 response.setHeader("Cache-Control","no-cache"); 
%>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>

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


function irFormCurso()
{
	if(spryselect1.validate())
	{
		frmBusCurso.action = "./ediCurso.jsp?codCurso=" +  $("#lstCursos").val();	
		frmBusCurso.submit();
	} 
}

</script>

<body class="fondoFormularios" onload="cargarComboTipoCurso();">
<form method="post" id="frmBusCurso" action="ediCurso.jsp">
<table width="90%" border="0">
  <tr>
    <td colspan="2" class="thDef">Buscar Curso</td>
    </tr>
  <tr>
    <td width="15%" class="tdDef">Tipo Curso</td>
    <td width="85%"><select name="lstTipCurso" id="lstTipCurso" onchange="cargarComboCursos();" style="width:250px">
    </select></td>
  </tr>
  <tr>
    <td valign="top" class="tdDef">Curso</td>
    <td><span id="valCurso">
      <select name="lstCursos" size="16" id="lstCursos" style="width:353px">
      </select>
      <span class="selectInvalidMsg">*</span><span class="selectRequiredMsg">Seleccione un elemento.</span></span></td>
    </tr>
  <tr>
    <td colspan="2" class="cellBtnSub"><input name="btnAnadir" type="button" class="cellBtnSub" id="btnAnadir" onclick="irFormCurso();" value="Ir Curso" /></td>
  </tr>
    </table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("valCurso", {invalidValue:"-1"});
</script>
</body>
</html>