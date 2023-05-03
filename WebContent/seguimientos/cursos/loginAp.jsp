<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Login Aplicación</title>
<style type="text/css">
@import url("css/disenno.css");


body {
	text-align: center;
	margin-top: 150px;
}
#frmLogin table {
	text-align: center;
}
</style>
<link href="SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<script src="SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
</head>

<body>
<script src="./js/validaGlobal.js"></script>

<script type="text/javascript">

function valForm()
{
	
  var validarCampos  = true;
  
  validarCampos = sprytextfield1.validate() & sprytextfield2.validate();
  
  if(validarCampos)
  { 
  	document.frmLogin.submit();
  }
  else
  {
	  alert("Valide el valor de los campos resaltados");
  }
  
  
 
}  
</script>

<form action="./ControlAccesoServlet" method="post" name="frmLogin" target="_self" id="frmLogin">
<table width="817" border="0" align="center">
  <tr>
    <th colspan="4" class="thDef"> Introduzca su Usuario y Password</th>
  </tr>
  <tr>
    <td width="247" rowspan="15"><img src="imagenes/login_lat1.jpg" width="233" height="320" alt="loginImg1" /></td>
    <td height="20" colspan="2">&nbsp;</td>
    <td width="270" rowspan="15"><img src="imagenes/login_lat2.jpg" width="233" height="320" alt="logImg2" /></td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td width="101" class="tdDef">Usuario</td>
    <td width="181"><span id="valUser">
      <input name="txtUser" type="text" id="txtUser" tabindex="1" size="15" maxlength="8" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td class="tdDef">Password</td>
    <td><span id="valPass">
      <input name="txtPass" type="password" id="txtPass" tabindex="2" size="15" maxlength="8" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
  <tr>
    <td colspan="2" class="cellBtnSub">
      <input type="button" name="btnAcept" id="btnAcept" value="Aceptar" onclick="valForm()"  tabindex="3" /></td>
  </tr>
  <tr>
    <td height="21" colspan="2">&nbsp;</td>
    </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("valUser");
var sprytextfield2 = new Spry.Widget.ValidationTextField("valPass");
</script>
</body>
</html>