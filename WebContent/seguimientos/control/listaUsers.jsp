<%@page import="es.jahernandez.gestion.ConUsoGestion"%>
<%@page import="es.jahernandez.gestion.CentrosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>



<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Resultado Búsqueda Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
    //Se cargan los datos del servlet y de paginación
    Vector listaUsers   = new Vector();

    int       valInf = 0;
    int       valSup = 0;
    int       valAnt = 0;

    ConUsuVO  userVO = null;
    
    String    cssFI = "";
    boolean   errAlta = false;
    boolean   errEdi  = false;
    boolean   errBaj  = false;

    listaUsers =  ConUsoGestion.devTodUsuarios();

    if(listaUsers == null) listaUsers = new Vector();
    
    if(request.getParameter("ind") != null)
    {
        valInf = new Integer(request.getParameter("ind")).intValue();
    }
    else
    {
        valInf = 0;
    }

    if(request.getParameter("codErrAlta") != null)
    {
        errAlta = true;
    }
    
    if(request.getParameter("codErrEdi") != null)
    {
        errEdi = true;
    }
    
    if(request.getParameter("codErrBaja") != null)
    {
        errBaj = true;
    }
    
    valSup = valInf + 15 ;
    valAnt = valInf - 15 ;

    if(valSup > listaUsers.size()) valSup = listaUsers.size();
    //if(valAnt < 0) valAnt = 0;
         
%>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />


</head>

<%if(errAlta){%>
    <script>alert("Se produjo un error dando de alta el Usuario")</script>
<%}%>
<%if(errEdi){%>
    <script>alert("Se produjo un error editando el Usuario")</script>
<%}%>
<%if(errBaj){%>
    <script>alert("Se produjo un error borrando el Usuario")</script>
<%}%>

<body class="fondoFormularios">
<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
                <th colspan="3" style="text-align: center; font-size: 14pt;">
                    Lista Usuarios</th>
            </tr>
             <tr>
                <td height="69" style="width: 374px">
               	<img  onmouseover="this.src='../imagenes/anadeusrGr.png'" onmouseout="this.src='../imagenes/anadeusrPe.png'" src="../imagenes/anadeusrPe.png" width="64" height="64" onclick="window.open('./altaUser.jsp?ind=<%=valInf%>','_self','');"/></td>
                <td style="text-align: right; margin-left: 80px">&nbsp;</td>
                <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>
                                                 
<table width="100%" class="tablaListadoExtensa"> 
            <tr>
                <td width="328"><span class="tablaListadoExtensaCabecera">
                  Nombre</span></td>
                <td width="168"><span class="tablaListadoExtensaCabecera">
                  Usuario</span></td>
                <td width="168"><span class="tablaListadoExtensaCabecera">
                  Nivel Acceso</span></td>
                <td width="231"><span class="tablaListadoExtensaCabecera">Centro</span></td>
                <td width="83"><span class="tablaListadoExtensaCabecera">
                  Activo</span></td>
  </tr>
      <%for (int ind = valInf; ind < valSup; ind++)
              {
                  userVO = (ConUsuVO) listaUsers.elementAt(ind);
                  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";
            %>
     
            <tr id="fila<%=ind%>" class="<%=cssFI%>" onmouseover="this.className='filaresaltada';"  onmouseout="this.className='<%=cssFI%>';" onclick="location.href='./ediUser.jsp?user=<%=userVO.getUsuario()%>&ind=<%=valInf%>'">
                <td height="21"><%=userVO.getNombre()%></td>
                <td><%=userVO.getUsuario()%></td>
                <td><%=userVO.getNivelAcceso()%></td>
                <td><%=CentrosGestion.nomCentro(String.valueOf(userVO.getIdCentro()))%></td>
                <td><%if (userVO.isActivo()){%>
                  <img src="../imagenes/si.png" width="16" height="16" alt="X" />
                <%}%></td>
            </tr>
<%} %>
</table>
<table width="100%" border="0">
<tr>
        <td width="55%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./listaUsers.jsp?ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" width="50" height="50" border="0" /></a><%}%></td>
        <td width="45%" class="cellBtnSub" scope="col"><%if(valSup < listaUsers.size()){%><a href="./listaUsers.jsp?ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" width="50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
    <p>&nbsp;</p>
</body>
</html>