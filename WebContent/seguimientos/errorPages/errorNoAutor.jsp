<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>No autorizado</title>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

<style type="text/css">
@import url("css/disenno.css");


body {
	text-align: center;
	margin-top: 150px;
}
table {
	text-align: center;
}
</style>
</head>

<body>

<table style="width:600" border="0" align="center">
	  <tr>
	    <td height="600" background="../imagenes/noautor.jpg"> 
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p class="literalErrorGrande">&nbsp;</p>
        <p class="literalErrorGrande">&nbsp;</p>
        <p class="literalErrorGrande">&nbsp;</p>
        <p class="literalErrorGrande">NO ESTÁ AUTORIZADO <BR/>
        A VISUALIZAR ESTA PÁGINA</p>
        <p class="literalErrorGrande">Consulte al administrador del sistema</p>
        </td>
  </tr>
	  <tr>
	    <td class="cellBtnSub"><input name="btnVolver" type="button" value="Login" onclick="window.open('../loginAp.jsp','_top','')"/></td>
  </tr>
</table>
</body>
</html>