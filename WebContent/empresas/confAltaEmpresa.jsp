<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%
    String codNueEmp = "";

    if(request.getParameter("codNuevaEmp") != null)
    {
        codNueEmp = request.getParameter("codNuevaEmp");
    }
%>
<script>
function cargaEmpresa()
{
	window.parent.cerrarDialogo("<%=codNueEmp%>");
}
</script>

</head>

<body onload="cargaEmpresa();">
</body>
</html>