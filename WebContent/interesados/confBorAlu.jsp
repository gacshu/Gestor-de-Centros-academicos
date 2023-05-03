<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Confirmación borrado alumno</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
    int codErr = 0;
    
    // Se comprueba que se hayan pasado los parámetros y se inicializan valores
    if(request.getParameter("errorBorCode") != null)
    {
        codErr = new Integer(request.getParameter("errorBorCode").trim()).intValue();
    }
%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
</head>

<body class="colorFondoPrincipalPestana">
<script>
parent.frmFichaInteresado.btnDatPer.disabled      = true;
parent.frmFichaInteresado.btnCursos.disabled      = true;
parent.frmFichaInteresado.btnMatriculas.disabled  = true;
</script>


<%if(codErr >= 0){%>
    <p class="literalErrorGrande">El alumno fue dado de baja correctamente</p>
<%}
if(codErr == -2)
{%>
    <p class="literalErrorGrande">El alumno fue dado de baja,<br> pero no se pudieron borrar sus cursos ni sus seguimientos</p>
<%}
if(codErr == -4)
{%>
    <p class="literalErrorGrande">El alumno fue dado de baja,<br> pero no se pudieron borrar sus seguimientos</p>
<%}%>
</body>
</html>