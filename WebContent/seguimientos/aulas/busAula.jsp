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

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script>  
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

function cargarComboAulas(idAula)
{	
	var idCentro = $("#lstCentros").val();
	
	$.ajax({
	  url: "../CargarComboAulaServlet?codCentro=" + idCentro + "&valSel=" + idAula,
	  success: function(data) {
		$(document).ready(function() {
			$("#lstAula").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las aulas");
	  }
	});
}

function irFormAula()
{
	if(spryselect1.validate())
	{
		frmBusAula.submit();
	} 
}

</script>

<body class="fondoFormularios" onload="cargarComboCentros('-99');">
<form method="post" id="frmBusAula" action="ediAula.jsp">
<table width="90%" border="0">
  <tr>
    <td colspan="2" class="thDef">Buscar Aula</td>
    </tr>
  <tr>
    <td width="15%" class="tdDef">Centro</td>
    <td width="85%"><select name="lstCentros" id="lstCentros" onchange="cargarComboAulas();" style="width:250px">
    </select></td>
  </tr>
  <tr>
    <td class="tdDef">Aulas</td>
    <td><span id="lstValAula">
      <select name="lstAula" size="7" id="lstAula" style="width:353px">
      </select>
      <span class="selectRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td colspan="2" class="cellBtnSub"><input name="btnAnadir" type="button" class="cellBtnSub" id="btnAnadir" onclick="irFormAula();" value="Aula" /></td>
  </tr>
    </table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("lstValAula");
</script>
</body>
</html>