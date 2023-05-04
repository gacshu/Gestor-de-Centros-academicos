/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import java.io.IOException;
import java.util.GregorianCalendar;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.EdicionesVO;
import es.jahernandez.gestion.EdicionesGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author Alberto
 */
public class EdiEdicionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        
        HttpSession sesion       = request.getSession();
        EdicionesVO ediVO        = new EdicionesVO();
        int         indInfLista  = 0;
        int         resultadoEdi = 0;
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Editar Edición" );
               
        }
                              
        if(request.getParameter("codEdi") != null)
        {
            ediVO.setIdEdi(request.getParameter("codEdi").trim());
        }
        
        if(request.getParameter("datCodCur") != null)
        {
            ediVO.setIdCur(request.getParameter("datCodCur").trim());
        }
             
        if(request.getParameter("lstNivel") != null)
        {
            ediVO.setIdNiv(request.getParameter("lstNivel").trim());
        }
        
        if(request.getParameter("datCentro") != null)
        {
            ediVO.setCodCen(new Integer(request.getParameter("datCentro")).intValue());
        }
        
        
        if(request.getParameter("lstAula") != null)
        {
            ediVO.setIdAula(request.getParameter("lstAula").trim());
        }
        
        if(request.getParameter("txtFecInicio") != null &&
          !request.getParameter("txtFecInicio").equals(""))
        {
            String strFechaIni = request.getParameter("txtFecInicio");
            ediVO.setFecIn(new GregorianCalendar(new Integer(strFechaIni.substring(6,10)).intValue(),
                                                 new Integer(strFechaIni.substring(3,5)).intValue() - 1,
                                                 new Integer(strFechaIni.substring(0,2)).intValue()).getTime());
        }
        
        
        if(request.getParameter("txtFecFin") != null && 
          !request.getParameter("txtFecFin").equals(""))
            {
                String strFechaFin = request.getParameter("txtFecFin");
                ediVO.setFecFi(new GregorianCalendar(new Integer(strFechaFin.substring(6,10)).intValue(),
                                                     new Integer(strFechaFin.substring(3,5)).intValue() - 1,
                                                     new Integer(strFechaFin.substring(0,2)).intValue()).getTime());
            }
        
        if(request.getParameter("txtPlazas") != null)
        {
            ediVO.setNumPla(new Integer(request.getParameter("txtPlazas").trim()).intValue());
        }
                
        if(request.getParameter("txtHorPresen") != null)
        {
            ediVO.setNumHor(new Integer(request.getParameter("txtHorPresen").trim()).intValue());
        }
        
        if(request.getParameter("txtHorDist") != null)
        {
            ediVO.setHorDis(new Integer(request.getParameter("txtHorDist").trim()).intValue());
        }
        
        if(request.getParameter("txtTelef") != null)
        {
            ediVO.setHorTelef(new Integer(request.getParameter("txtTelef").trim()).intValue());
        }
        
        if(request.getParameter("lstHorInicio") != null)
        {
            ediVO.setHorIn(new Integer(request.getParameter("lstHorInicio").trim()).intValue());
        }
        
        if(request.getParameter("lstMinInicio") != null)
        {
            ediVO.setMinIn(new Integer(request.getParameter("lstMinInicio").trim()).intValue());
        }
        
        if(request.getParameter("lstHorFin") != null)
        {
            ediVO.setHorFi(new Integer(request.getParameter("lstHorFin").trim()).intValue());
        }
        
        if(request.getParameter("lstMinFin") != null)
        {
            ediVO.setMinFin(new Integer(request.getParameter("lstMinFin").trim()).intValue());
        }
        
        if(request.getParameter("txtPrecioMat") != null)
        {
            ediVO.setPrecioM(new Double(request.getParameter("txtPrecioMat").replace(',','.').trim()).doubleValue());            
        }
        
        if(request.getParameter("txtPrecioPlazo") != null)
        {
            ediVO.setPrecioR(new Double(request.getParameter("txtPrecioPlazo").replace(',','.').trim()).doubleValue());
        }
        
        if(request.getParameter("chkAplazado") != null &&
           request.getParameter("chkAplazado").equals("true"))
        {
            ediVO.setPlazos(true);
        }
        
        
        if(request.getParameter("chkLunes") != null &&
           request.getParameter("chkLunes").equals("true"))
        {
            ediVO.setHayLun(true);
        }
        
        
        if(request.getParameter("chkMartes") != null &&
           request.getParameter("chkMartes").equals("true"))
        {
            ediVO.setHayMar(true);
        }
    
        
        if(request.getParameter("chkMiercoles") != null &&
           request.getParameter("chkMiercoles").equals("true"))
        {
            ediVO.setHayMie(true);
        }
        
        if(request.getParameter("chkJueves") != null &&
           request.getParameter("chkJueves").equals("true"))
        {
            ediVO.setHayJue(true);
        }
            
        if(request.getParameter("chkViernes") != null &&
           request.getParameter("chkViernes").equals("true"))
        {
            ediVO.setHayVie(true);
        }
        
        if(request.getParameter("chkSabado") != null &&
           request.getParameter("chkSabado").equals("true"))
        {
            ediVO.setHaySab(true);
        }
            
        if(ediVO.isPlazos())
        {
        
            if(request.getParameter("chkEnero") != null &&
               request.getParameter("chkEnero").equals("true"))
            {
                ediVO.setEne(true);
            }
            
        
            if(request.getParameter("chkFebrero") != null &&
               request.getParameter("chkFebrero").equals("true"))
            {
                ediVO.setFeb(true);
            }
                
            if(request.getParameter("chkMarzo") != null &&
               request.getParameter("chkMarzo").equals("true"))
            {
                ediVO.setMar(true);
            }
                
            if(request.getParameter("chkAbril") != null &&
               request.getParameter("chkAbril").equals("true"))
            {
                ediVO.setAbr(true);
            }
            
            if(request.getParameter("chkMayo") != null &&
               request.getParameter("chkMayo").equals("true"))
            {
                ediVO.setMay(true);
            }
            
            if(request.getParameter("chkJunio") != null &&
               request.getParameter("chkJunio").equals("true"))
                {
                    ediVO.setJun(true);
                }
            
            if(request.getParameter("chkJulio") != null &&
               request.getParameter("chkJulio").equals("true"))
            {
                ediVO.setJul(true);
            }
                    
            if(request.getParameter("chkAgosto") != null &&
               request.getParameter("chkAgosto").equals("true"))
            {
                ediVO.setAgo(true);
            }            
            
            if(request.getParameter("chkSeptiembre") != null &&
               request.getParameter("chkSeptiembre").equals("true"))
            {
                ediVO.setSep(true);
            }
                   
            if(request.getParameter("chkOctubre") != null &&
               request.getParameter("chkOctubre").equals("true"))
            {
                ediVO.setOct(true);
            }
            
            if(request.getParameter("chkNoviembre") != null &&
               request.getParameter("chkNoviembre").equals("true"))
            {
                ediVO.setNov(true);
            }
                        
            if(request.getParameter("chkDiciembre") != null &&
               request.getParameter("chkDiciembre").equals("true"))
            {
                ediVO.setDic(true);
            }                                                
        }
        
        if(request.getParameter("lstBonificado") != null)
        {
            ediVO.setBonif(request.getParameter("lstBonificado").trim());
        }  
        
        
        if(request.getParameter("txtDescripcion") != null)
        {
            ediVO.setDescripcion(request.getParameter("txtDescripcion").trim());
        }        
        
        if(request.getParameter("lstProf") != null)
        {
            ediVO.setIdProf(request.getParameter("lstProf").trim());
        }    
        
        if(request.getParameter("indInf") != null)
        {
            indInfLista = new Integer(request.getParameter("indInf").trim()).intValue();
        }
        
        resultadoEdi = EdicionesGestion.editaEdicion(ediVO);
        
        if(resultadoEdi > 0)
        {
            response.sendRedirect("ediciones/listEdiCurso.jsp?codCur="    + ediVO.getIdCur()
                                                          + "&valInfEdi=" + indInfLista);
        }
        else
        {                   
             response.sendRedirect("ediciones/listEdiCurso.jsp?codCur="   + ediVO.getIdCur()
                                                          + "&valInfEdi=" + indInfLista
                                                          + "&msgModError=true");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Modificación edición servlet";
    }// </editor-fold>
}
