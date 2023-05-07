<%@page import="java.text.DecimalFormat"%>
<%@page import="es.jahernandez.accesodatos.ControlRecDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.jahernandez.datos.ConUsuVO"%>
<%@page import="es.jahernandez.datos.ControlRecVO"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<%@ include file="controlAcceso/includeComAutProf.jsp"%>
<%
    ConUsuVO    usuario =  null;
    
    //Cargamos datos de usuario
    if(session.getAttribute("usuario")!= null)
    {
        usuario = (ConUsuVO) session.getAttribute("usuario");
    }
%>
<title>Gestor de Centros Académicos</title>
<script type="text/javascript" src="./js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="./js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="./js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
    
<link rel="stylesheet" type="text/css" href="./js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<link href="./css/disenno.css" rel="stylesheet" type="text/css" />


<!--Funciones jquery ui-->
<script>
    $(function() {
        $( "#menuPrincipal" ).accordion({
            collapsible: true,
			active: false,
			heightStyle: "content"
        });
    });
</script>

</head>

<body class="fondoFormularios">
<div id="menuPrincipal">
	<span class="tdDef">Tutor&iacute;a</span>
	<div>
  		<table style="width:90%" border="0" class="formatoMenu">
          <tr onmouseover="this.className='filaresaltada';" onmouseout="this.className='';" onclick="window.open('./ediciones/listEdiCursoProf.jsp' , 'fraPrincipal' , '')">
            <td style="width:5%">&nbsp;</td>
            <td style="width:95%">Gestión Cursos</td>
          </tr>
      </table>
  </div>  
    <span class="tdDef">Clases</span>
    <div>
    	<table style="width:90%" border="0" class="formatoMenu">
            <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./profesores/verModProf.jsp?codProf=<%=usuario.getIdProf()%>&tipBus=1' , 'fraPrincipal' , '')">
        		<td>&nbsp;</td>
        		<td>Histórico de Clases</td>
      		</tr>
      		<tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./profesores/verModProf.jsp?codProf=<%=usuario.getIdProf()%>&tipBus=2' , 'fraPrincipal' , '')">
        		<td>&nbsp;</td>
        		<td>Clases Pendientes</td>
      		</tr>
 			<tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./profesores/clasIndProf.jsp?codProf=<%=usuario.getIdProf()%>' , 'fraPrincipal' , '')">
                <td style="width:5%">&nbsp;</td>
                <td style="width:95%">Clases individuales</td>
 	        </tr>
      </table>
    </div>
</div>
</body>
</html>