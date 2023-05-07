<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Subir fichero</title>

<%@ include file="../controlAcceso/includeComAutProf.jsp"%>

<%
    String nomFich = "";

    if (request.getParameter("nomArc") != null)
    { 
        nomFich = request.getParameter("nomArc");
    }

%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

</head>

<body class="fondoFormularios" <%if(! nomFich.trim().equals("")){%> onload="parent.subirFichero('<%=nomFich%>');"<%}%> >
<form action="../SubirArchivoServlet" method="post" enctype="multipart/form-data" name="frmSubir" id="frmSubir">
<table style="width:401" border="0">
  <tr>
    <td style="width:49%" class="tdDef"><input class="cellBtnSub" type="file" name="txtFile" id="txtFile" /></td>
    <td style="width:51%" class="tdDef"><input class="cellBtnSub" type="submit" name="btnSubir" id="btnSubir" value="Subir" /></td>
  </tr>
</table>
</form>
</body>
</html>