<%@page import="es.jahernandez.gestion.ControlRecGestion"%>
<%@page import="es.jahernandez.accesodatos.ControlRecDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.jahernandez.datos.ConUsuVO"%>
<%@page import="es.jahernandez.datos.ControlRecVO"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<%@ include file="controlAcceso/includeComAut.jsp"%>
<%
    boolean      mostrarPendientes = false; //Indica si se muestran los recibos pendientes
    ControlRecVO contRecVO         = new ControlRecVO();
    String       mesActual         = new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()));   
    String       annoActual        = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));   
    ConUsuVO     conUsVO = new ConUsuVO();

    conUsVO = (ConUsuVO) session.getAttribute("usuario");  

    contRecVO.setFecha(annoActual + mesActual);

    mostrarPendientes = ControlRecGestion.generadosRecMes(contRecVO.getFecha(), conUsVO.getIdCentro());
%>
<title>Gestor de Centros Académicos</title>
<script src="SpryAssets/SpryCollapsiblePanel.js" type="text/javascript"></script>
<link href="SpryAssets/SpryCollapsiblePanel.css" rel="stylesheet" type="text/css" />

<link href="./css/disenno.css" rel="stylesheet" type="text/css" />

</head>

<body class="fondoFormularios">

<table style="width:" border="0">
  <tr>
   <td>
       <%//if (! mostrarPendientes){%>
				<input name="btnImpRec" type="button" onClick="window.open('./ImpRecAluServlet', 'rg','');" value="Imprimir Recibos" />
		<%//}else{%>
                <!--a href="./impRecPenAluServlet" target="rp">Imprimir Pendientes</a-->                
       <%//}%>
       
   </td>
  </tr>
</table>
<div id="menuAlumnos" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">Alumnos</div>
  <div class="CollapsiblePanelContent">
    <table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./interesados/busquedaInter.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Buscar</td>
      </tr>
    </table>
  </div>
</div>
<div id="menuAulas" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">Aulas</div>
  <div class="CollapsiblePanelContent"><table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./aulas/anaAula.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Añadir</td>
      </tr>
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./aulas/busAula.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Editar</td>
      </tr>
    </table>
  </div>
</div>
<div id="menuCursos" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">Cursos</div>
  <div class="CollapsiblePanelContent">
    <table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./cursos/altaCurso.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Añadir</td>
      </tr>
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./cursos/busCurso.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Editar</td>
      </tr>
      <!--
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./ediciones/altaEdicion.jsp?urlDes=0' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Crear Edición</td>
      </tr>
      -->
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./cursos/visAluCur.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Interesados</td>
      </tr>
       <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./ResumenEdicionesServlet' , '_rescur' , '')">
        <td>&nbsp;</td>
        <td>Resumen Ediciones</td>
      </tr>
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./ediciones/histMatAlu.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Histórico de Matrículas</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><div id="menuTipoCurso" class="CollapsiblePanel">
          <div class="CollapsiblePanelTab" tabindex="0">Tipo Curso</div>
          <div class="CollapsiblePanelContent"><table style="width:90%" border="0">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./cursos/altaTipCur.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Añadir</td>
      </tr>
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./cursos/ediTipCur.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Editar</td>
      </tr>
          </table>
          </div>
        </div></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
  </div>
</div>
<div id="menuModulos" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">&Aacute;reas</div>
  <div class="CollapsiblePanelContent">
    <table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./cursos/altaArea.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Añadir</td>
      </tr>
       <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./cursos/ediArea.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Editar</td>
      </tr>
    </table>
  </div>
</div>
<div id="menuProfesores" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">Profesores</div>
  <div class="CollapsiblePanelContent">
    <table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./profesores/altaProfesor.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Añadir</td>
      </tr>
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./profesores/busqProf.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Buscar</td>
      </tr>
    </table>
  </div>
</div>
<div id="menuEmpresas" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">Empresas</div>
  <div class="CollapsiblePanelContent">
    <table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./empresas/altaEmp.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Alta</td>
      </tr>
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./empresas/busqEmp.jsp' , 'fraPrincipal' , '')">
        <td>&nbsp;</td>
        <td>Buscar</td>
      </tr>
    </table>
  </div>
</div>
<div id="menuRecibos" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">Recibos</div>
  <div class="CollapsiblePanelContent">
    <table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./ediciones/busRec.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Histórico Recibos</td>
      </tr>
    </table>
  </div>
</div>
<br>
<%if(conUsVO.getNivelAcceso() >= 5){%> 
<div id="menCentros" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0">Centros</div>
  <div class="CollapsiblePanelContent">
  	<table style="width:90%" border="0" style="font-size:large">
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./centros/altaCentro.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Añadir</td>
      </tr>
      <tr onmouseover="this.className='filaresaltada';"  onmouseout="this.className='';" onclick="window.open('./centros/ediCentro.jsp' , 'fraPrincipal' , '')">
        <td style="width:5%">&nbsp;</td>
        <td style="width:95%">Editar</td>
      </tr>
    </table>
  </div>
</div>
<%}%>
<p><br>
</p>
<script type="text/javascript">
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("menuAlumnos", {contentIsOpen:false});
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("menuAulas", {contentIsOpen:false});
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("menuCursos", {contentIsOpen:false});
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("menuEmpresas", {contentIsOpen:false});
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("menuModulos", {contentIsOpen:false});
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("menuProfesores", {contentIsOpen:false});
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("menuRecibos", {contentIsOpen:false});
var CollapsiblePanel2 = new Spry.Widget.CollapsiblePanel("menuTipoCurso");
var CollapsiblePanel3 = new Spry.Widget.CollapsiblePanel("menCentros", {contentIsOpen:false});
</script>
</body>
</html>