<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import = "es.jahernandez.datos.ConUsuVO"%>
<%@page import="es.jahernandez.datos.InformacionConf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Gestión Integral de Centros de Enseñanza</title>
<link href="css/disenno.css" rel="stylesheet" type="text/css" />


<%@ include file="controlAcceso/includeComAutProf.jsp"%>
<%
//Carga los datos del usuario logado
ConUsuVO conUsuVO = (ConUsuVO) session.getAttribute("usuario");
%>
</head>

<body class="paginaTitulo">

<table width="100%" border="0">
  <tr>
      <td width="20%"><img src="imagenes/<%=InformacionConf.logo%>" width="133" height="56" /></td>
    <th width="64%" class="thDef">Gestion Integral de Centros de Enseñanza</th>
    <td width="16%">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="tdDef"><%=conUsuVO.getNombre()%>&nbsp;<input name="btnCerrarSesion" type="button" value="Cerrar Sesión" onclick="window.open('./CerrarSesionServlet','_top','')"/></td>
    <td class="tdDef">
        <%if(conUsuVO.getNivelAcceso() >= 5){%> 
            <input name="btnUsers" type="button" value="Lista Usuarios" onclick="window.open('./control/listaUsers.jsp','fraPrincipal','')"/>
        <%}%>
    </td>
  </tr>
</table>
</body>
</html>