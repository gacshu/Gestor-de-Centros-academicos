<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@page import="es.jahernandez.datos.ConUsuVO"%>
<%@ include file="controlAcceso/includeComAutProf.jsp"%>

<%
    ConUsuVO    usuario =  null;
    
    //Cargamos datos de usuario
    if(session.getAttribute("usuario")!= null)
    {
        usuario = (ConUsuVO) session.getAttribute("usuario");
    }
%>

<title>GESTION INTEGRAL DE CENTROS EDUCATIVOS</title>
</head>


<frameset rows="100,*" cols="*" frameborder="no" border="0" framespacing="0">
    <frame src="titleAplication.jsp" name="fraTitulo" scrolling="No" noresize="noresize" id="fraTitulo" title="fraTitulo">
    <frameset cols="250,*" frameborder="no" border="0" framespacing="0">
        <%if(usuario.getNivelAcceso()>=3){%>
        	<frame src="menuPrinc.jsp" name="fraMenu" scrolling="No" noresize="noresize" id="fraMenu" title="fraMenu">
        <%}else{%>
        	<frame src="menuProf.jsp" name="fraMenu" scrolling="No" noresize="noresize" id="fraMenu" title="fraMenu">
        <%}%>
        <frame src="pagPresentacion.jsp" name="fraPrincipal" id="fraPrincipal" title="fraPrincipal">
    </frameset>
</frameset>
<noframes>
    <body></body>
</noframes>
</html>
