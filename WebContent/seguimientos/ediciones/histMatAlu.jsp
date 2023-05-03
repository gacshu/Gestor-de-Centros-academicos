<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
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
    
    Vector      listAlu   = new Vector();
    
    //Datos paginación
    int    valInf         = 0;
    int    valSup         = 0;
    int    valAnt         = 0;
    String cssFI          = "";
    
    CursosAluVO curAluVO  = new CursosAluVO();
    
    String      strTipCur = "";
    String      strCodCur = "";
    String      strNomCur = "";
    String      strDesCur = "";
    
    CursosVO    curVO     = null;

    Vector      listBusq  = new Vector();
    
    //cadenaconsulta = cadenaconsulta + "WHERE (TbCur.TipCur = " + Convert.ToInt32(lstTipCur.SelectedValue) + ")";


    //cadenaconsulta = cadenaconsulta + " ORDER BY TbCur.IdCur";
    listBusq = CursosGestion.devolverDatosHistMat();

    curVO = CursosGestion.devolverDatosCurso(((CursosAluVO)listBusq.elementAt(0)).getIdCur());

    //txtDesCur.Text = curVO.Contenido;

    //lblLitNomCur.Text = "";
    //lblLitTipCur.Text = TipoCursoGestion.devuelveNombreTipo(curVO.TipCur);

    session.setAttribute("busAluC", listBusq);
    
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

    if(valSup > listBusq.size()) valSup = listBusq.size();

%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />


</head>

<body class="fondoFormularios">
<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
                <th colspan="3" style="text-align: center; font-size: 14pt;">
                    Lista Alumnos</th>
            </tr>
             <tr>
                <td height="69" style="width: 374px">
                    <img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" width="64" height="64" onclick="window.open('../ImpHistMatServlet','_listaAlu','');"/>
                </td>
                <td style="text-align: right; margin-left: 80px">&nbsp;</td>
                <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>
                                                 
<table width="100%" class="tablaListadoExtensa"> 
            <tr>
                <td width="235"><span class="tablaCursos">
                Nombre</span></td>
                <td width="120"><span class="tablaCursos">
                Móvil</span></td>
                <td width="120"><span class="tablaCursos">
                Teléfono</span></td>
                <td width="285"><span class="tablaCursos">
                Correo Elect.</span></td>
  </tr>
      <%
        AlumnosVO datAlu    = null;
        CursosVO  datCur    = null;
        String    auxNomCur = "";
        if (session.getAttribute("busAluC") != null) listAlu  = (Vector) session.getAttribute("busAluC");        

        if (listAlu != null)
        {
            for (int ind = valInf; ind < valSup; ind++)
            {
                curAluVO = (CursosAluVO) listAlu.elementAt(ind);
                datAlu = AlumnosGestion.devolverDatosAlumno(curAluVO.getIdAlu());
                datCur = CursosGestion.devolverDatosCurso(curAluVO.getIdCur());
                cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";
                
                if (! datCur.getNomCur().equals(auxNomCur))
                {
                    auxNomCur = datCur.getNomCur();
       %>
            
                <tr class="encabezadoSeccionLista">
                    <td height="21" colspan="4" class="encabezadoSeccionLista"><%=auxNomCur%></td>
                </tr>
       <%}%>
            <tr id="fila<%=ind%>" class="<%=cssFI%>" onmouseover="this.className='filaresaltada';"  onmouseout="this.className='<%=cssFI%>';" onclick="location.href='../interesados/datosInteresados.jsp?pestana=1&codInt=<%=curAluVO.getIdAlu()%>&ind=<%=valInf%>'">
                <td height="21"><span class="tablaCursos"><%=datAlu.getAp1Alu()%> , <%=datAlu.getNombre() %></span></td>
                <td><span class="tablaCursos"><%=datAlu.getMovAlu()%></span></td>
                <td><span class="tablaCursos"><%=datAlu.getFijAlu()%></span></td>
                <td><span class="tablaCursos"><%=datAlu.getEmail()%></span></td>
            </tr>
<%}}%>
</table>
<table width="100%" border="0">
<tr>
        <td width="55%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./histMatAlu.jsp?ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" width="50" height="50" border="0" /></a><%}%></td>
        <td width="45%" class="cellBtnSub" scope="col"><%if(valSup < listBusq.size()){%><a href="./histMatAlu.jsp?ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" width="50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
    <p>&nbsp;</p>
</body>
</html>