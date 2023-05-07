<%@page import="es.jahernandez.gestion.NivelesGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.CursosAluGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>


<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<html lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Cursos interesado</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Carga de datos del formulario de búsqueda
AlumnosVO    aluVO          = new AlumnosVO();
Vector       listCurAlu     = new Vector();
CursosAluVO  curAluVO       = null;
int          errAltaCurso   = 0;
int          errBajaCurso   = 0;

//Datos paginación
int          valInf         = 0;
int          valSup         = 0;
int          valAnt         = 0;

String       cssFI          = "";

if(request.getParameter("codInt") != null)
{
    aluVO = AlumnosGestion.devolverDatosAlumno(request.getParameter("codInt"));
    listCurAlu = CursosAluGestion.devolverCursosAlu(aluVO.getIdAlu());
}

//Se cargan los posibles errores que se pueden producir dando de alta cursos
if(request.getParameter("errBorCur") != null)
{
    errAltaCurso = new Integer(request.getParameter("errBorCur")).intValue();
}


if(request.getParameter("ind") != null)
{
	valInf = new Integer(request.getParameter("ind")).intValue();
}
else
{
	valInf = 0;
}

valSup = valInf + 15 ;
valAnt = valInf - 15 ;

if(valSup > listCurAlu.size()) valSup = listCurAlu.size();

%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Cursos.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/Niveles.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/interface/TipoCurso.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/engine.js'></script>
<script type='text/javascript' src='/GestorCentrosAcademicos/dwr/util.js'></script><script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
    
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/base/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/base/jquery.ui.dialog.css"/>
<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/base/jquery.ui.all.css"/>

<%if(errBajaCurso == -1){%>
<script>
    alert("Se produjo un error al dar de baja el curso");
</script>
<%}%>
</head>

<body onload="cargarComboTipoCurso();" class="colorFondoPrincipalPestana" >

<form action="./cursosFichaAlumno.jsp" method="post" name="frmGestionaCursos" target="_self" id="frmGestionaCursos">
  	  <table style="width:90%" border="0" class="tablaCursos">
  	    <tr>
  	      <td style="width:35%"><strong>Curso</strong></td>
  	      <td style="width:37%"><strong>Nombre Nivel</strong></td>
  	      <td colspan="2" align="center"><input type="button" name="btnAnaCur" id="btnAnaCur" value="Añadir Curso" onclick="parent.cargaAltaCur('<%=aluVO.getIdAlu()%>')"/></td>
  	      </tr>
  	    
  	    <%for(int ind = valInf; ind < valSup; ind++)
              {
                  curAluVO = (CursosAluVO) listCurAlu.elementAt(ind);
				  
				  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListadoPestana";
              %>
  	    <tr class="<%=cssFI%>">
                <td style="width:35%"><%=CursosGestion.devolverDatosCurso(curAluVO.getIdCur()).getNomCur()%></td>
                <td style="width:37%"><%=NivelesGestion.devolverDatosNivel(curAluVO.getIdNiv()).getNomNiv()%></td>
  	      <td style="width:14%" align="center"><input type="button" name="btnMat<%=ind%>" id="btnMat<%=ind%>" value="Matricular" onclick="parent.cargaPaginaMat('<%=aluVO.getIdAlu()%>','<%=curAluVO.getIdCur()%>');" /></td>
  	      <td style="width:14%" align="center">
  	        <input type="button" name="btnEli<%=ind%>" id="btnEli<%=ind%>2" value="Eliminar" onclick="window.open('../DarBajaCursoInteresAlumnoServlet?codInteresado=<%=aluVO.getIdAlu()%>&codCurso=<%=curAluVO.getIdCur()%>&codNiv=<%=curAluVO.getIdNiv()%>&ind=<%=valInf%>','_self','')"/>
  	        </td>
  	      </tr>
  	    <%}%>
  	    </table>
        <table style="width:100%" border="0">
		<tr>
        	<td style="width:55%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./cursosFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>&ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" style="width:50" height="50" border="0" /></a><%}%></td>
        	<td style="width:45%" class="cellBtnSub" scope="col"><%if(valSup < listCurAlu.size()){%><a href="./cursosFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>&ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" style="width:50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
</form>
</body>
</html>