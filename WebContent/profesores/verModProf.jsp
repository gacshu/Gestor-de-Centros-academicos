<%@page import="es.jahernandez.gestion.ModulosGestion"%>
<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.EdiModProfAulaGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.AulasGestion"%>
<%@page import="es.jahernandez.gestion.AreasGestion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Vector"%>
<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>



<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Resultado Búsqueda Interesados</title>
<%@ include file="../controlAcceso/includeComAutProf.jsp"%>

<%
    //Se cargan los datos del servlet y de paginación
    Vector           listaModProf = new Vector();
  
    int              valInf       = 0;
    int              valSup       = 0;
    int              valAnt       = 0;

    EdiModProfAulaVO empaVO       = null;
    
    String           cssFI        = "";
    String           codProf      = "";
    int              tipBus       = -99;
  
    if(request.getParameter("codProf") != null)
    {
        codProf =  request.getParameter("codProf");
        
        if(request.getParameter("tipBus") != null)
        {
            tipBus = new Integer(request.getParameter("tipBus")).intValue();
        }
        
        switch (tipBus) 
        {
            case 1: listaModProf = EdiModProfAulaGestion.devolverModProf(codProf); break;
            case 2: listaModProf = EdiModProfAulaGestion.devolverModProfPend(codProf); break;
            default: listaModProf = new Vector(); break;
        }
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

    if(valSup > listaModProf.size()) valSup = listaModProf.size();
    //if(valAnt < 0) valAnt = 0;


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />


</head>

<body class="fondoFormularios">

<script>
function bajaMod(codEdi, codMod, ind)
{
	window.open("../BorrarModEdiServlet?codEdi=" + codEdi 
	                                + "&codMod=" + codMod 
									+ "&ind="    + ind ,
				"_self","");
}

</script>
<table style="width: 100%; font-size: 10pt; font-family: tahoma;">
            <tr>
               <td height="66" style="width: 374px"><img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" style="width:64" height="64" onclick="window.open('../ImpCursosProfServlet?codProf=<%=codProf%>&tipBus=<%=tipBus%>','_listaAlu','');"/></td>
               <td style="text-align: right; margin-left: 80px">&nbsp;</td>
               <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>                        
<table style="width:100%" class="tablaListadoExtensa"> 
            <tr>
                <td style="width:184"><span class="tablaListadoExtensaCabecera">
                  M&oacute;dulo</span></td>
                <td style="width:275"><span class="tablaListadoExtensaCabecera">
                 Curso y Edici&oacute;n</span></td>
                <td style="width:117"><span class="tablaListadoExtensaCabecera">
                 D&iacute;as</span></td>
                <td style="width:217"><span class="tablaListadoExtensaCabecera">
                  Aula</span></td>
                <td style="width:180"><span class="tablaListadoExtensaCabecera">&Aacute;rea</span></td>
                <td style="width:117"><span class="tablaListadoExtensaCabecera"> Fecha Inicio</span></td>
                <td style="width:133"><span class="tablaListadoExtensaCabecera"> Fecha Fin</span></td>
                <td style="width:76"><span class="tablaListadoExtensaCabecera"> Hora Inicio</span></td>
                <td style="width:90"><span class="tablaListadoExtensaCabecera">Hora Fin</span></td>
                
  </tr>
      <%for (int ind = valInf; ind < valSup; ind++)
              {
                  empaVO = (EdiModProfAulaVO) listaModProf.elementAt(ind);
                  cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListado";

            %>
     
            <tr id="fila<%=ind%>" class="<%=cssFI%>">
                <td height="21"><%=ModulosGestion.devolverDatosModulo(empaVO.getIdMod()).getNombre()%></td>
                <td><%=CursosGestion.devolverDatosCurso(EdicionesGestion.devolverDatosEdi(empaVO.getIdEdi()).getIdCur()).getNomCur()+ " --- "  +   EdicionesGestion.devolverDatosEdi(empaVO.getIdEdi()).getDescripcion()%></td>
                <td><%=EdicionesGestion.devolverDatosEdi(empaVO.getIdEdi()).devuelveDiasClase()%></td>
                <td><%if(! empaVO.getIdAul().trim().equals("")){%> <%=AulasGestion.devolverDatosAula(empaVO.getIdAul()).getNombre()%> <%}%></td>
                <td><%=AreasGestion.devuelveNombreArea(ModulosGestion.devolverDatosModulo(empaVO.getIdMod()).getCodArea())%></td>
                <td><%=new SimpleDateFormat("dd/MM/yyyy").format(empaVO.getFecIni())%></td>
                <td><%=new SimpleDateFormat("dd/MM/yyyy").format(empaVO.getFecFin())%></td>
                <td><%=empaVO.getHorIni()%></td>
                <td><%=empaVO.getHorFin()%></td>
            </tr>
<%} %>
</table>
<table style="width:100%" border="0">
<tr>
        <td style="width:55%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./verModProf.jsp?codProf=<%=codProf%>&ind=<%=valAnt%>"><img src="../imagenes/btnprev.png" alt="&lt;---" style="width:50" height="50" border="0" /></a><%}%></td>
        <td style="width:45%" class="cellBtnSub" scope="col"><%if(valSup < listaModProf.size()){%><a href="./verModProf.jsp?codProf=<%=codProf%>&ind=<%=valSup%>"><img src="../imagenes/btnsig.png" alt="---&gt;" style="width:50" height="50" border="0" /></a><%}%></td>
      </tr>
</table>
    <p>&nbsp;</p>
</body>
</html>