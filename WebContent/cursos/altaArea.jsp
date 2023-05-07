<%@page import="es.jahernandez.datos.AreasVO"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.AreasVO"%>
<%@ page import = "es.jahernandez.accesodatos.AreasDAO"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Añadir tipo curso</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Control de errores de alta
int         errCodAlta = 0;
AreasVO     areaVO     = new AreasVO();

//Se comprueba si vienen datos de el servlet de alta de interesados para recargar datos
//y los códigos de error
if(request.getParameter("errorCode") != null)
{
    errCodAlta = new Integer(request.getParameter("errorCode")).intValue();
}


if(session.getAttribute("nuevoAreaCurErr") != null)
{
    areaVO = (AreasVO) session.getAttribute("nuevoAreaCurErr");
    session.removeAttribute("nuevoAreaCurErr");
}

%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/JavaScript">
function validaForm()
{		   
	if(sprytextfield1.validate())
	{
		frmAltaArea.action = "../AltaAreaServlet";
		frmAltaArea.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados")
	}
	
}
</script>

</head>

<%if(errCodAlta == -1){%>
<script>
    alert("Se produjo un error en el alta del área");
</script>
<%}%>
<%if(errCodAlta == -2){%>
<script>
    alert("Se produjo un error de acceso a la base da datos");
</script>
<%}%>


<body class="fondoFormularios">
<form id="frmAltaArea" name="frmAltaArea" method="post" action="">
<table style="width:90%" border="0">
  <tr>
    <td colspan="2" class="thDef">A&ntilde;adir &Aacute;rea</td>
  </tr>
  <tr class="tdDef">
    <td style="width:8%" class="tdDef">Nombre</td>
    <td class="tdDef">
      <span id="txtValNombre">
        <input name="txtNombre" type="text" id="txtNombre" value="<%=areaVO.getNomArea()%>" size="75" maxlength="100" />
        <span class="textfieldRequiredMsg">*</span></span>
    </td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="cellBtnSub"><input name="btnAlta" type="button" class="cellBtnSub" id="btnAlta" value="Guardar" onclick="validaForm();" /></td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre");
</script>
</body>
</html>