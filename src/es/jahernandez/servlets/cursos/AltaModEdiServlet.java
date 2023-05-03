/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import es.jahernandez.accesodatos.EdiModProfAulaDAO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.EdiModProfAulaVO;
import es.jahernandez.gestion.EdiModProfAulaGestion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

/**
 *
 * @author JuanAlberto
 */
public class AltaModEdiServlet extends HttpServlet 
{
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
        
        HttpSession      sesion    = request.getSession();
        EdiModProfAulaVO empaVO    = new EdiModProfAulaVO();
        int              resAlt    = 0;
        String           indPrevio = "";
        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta módulo-edición" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("hidCodEdi") != null)
        {
            empaVO.setIdEdi(request.getParameter("hidCodEdi").trim());
        }
       
        if(request.getParameter("lstModulos") != null)
        {
            empaVO.setIdMod(request.getParameter("lstModulos").trim());
        }
        
        if(request.getParameter("lstProfesores") != null)
        {
            empaVO.setIdProf(request.getParameter("lstProfesores").trim());
        }
        
        if(request.getParameter("lstAulas") != null)
        {
            if(! request.getParameter("lstAulas").equals("-1") )
            {    
                empaVO.setIdAul(request.getParameter("lstAulas").trim());
            }
        }
        
        if(request.getParameter("hidFecIni") != null)
        {
             if (! request.getParameter("hidFecIni").equals(""))
            {
                String strFechaIni = request.getParameter("hidFecIni");
                empaVO.setFecIni(new GregorianCalendar(new Integer(strFechaIni.substring(6,10)).intValue(),
                                                       new Integer(strFechaIni.substring(3,5)).intValue() - 1,
                                                       new Integer(strFechaIni.substring(0,2)).intValue()).getTime());
            }
        }
        
        if(request.getParameter("hidFecFin") != null)
        {
             if (! request.getParameter("hidFecFin").equals(""))
            {
                String strFechaFin = request.getParameter("hidFecFin");
                empaVO.setFecFin(new GregorianCalendar(new Integer(strFechaFin.substring(6,10)).intValue(),
                                                       new Integer(strFechaFin.substring(3,5)).intValue() - 1,
                                                       new Integer(strFechaFin.substring(0,2)).intValue()).getTime());
            }
        }
        
        if(request.getParameter("txtHoraIni") != null)
        {
            empaVO.setHorIni(request.getParameter("txtHoraIni").trim());
        }
        
        if(request.getParameter("txtHoraFin") != null)
        {
            empaVO.setHorFin(request.getParameter("txtHoraFin").trim());
        }
        
        if(request.getParameter("chkLunes") != null)
        {
            if(request.getParameter("chkLunes").equals("true"))
            {
                empaVO.setHayLun(true);
            }
        }
        
        if(request.getParameter("chkMartes") != null)
        {
            if(request.getParameter("chkMartes").equals("true"))
            {
                empaVO.setHayMar(true);
            }
        }
        
        if(request.getParameter("chkMierc") != null)
        {
            if(request.getParameter("chkMierc").equals("true"))
            {
                empaVO.setHayMie(true);
            }
        }
        
        if(request.getParameter("chkJueves") != null)
        {
            if(request.getParameter("chkJueves").equals("true"))
            {
                empaVO.setHayJue(true);
            }
        }
        
        if(request.getParameter("chkViernes") != null)
        {
            if(request.getParameter("chkViernes").equals("true"))
            {
                empaVO.setHayVie(true);
            }
        }
        
        if(request.getParameter("chkSabado") != null)
        {
            if(request.getParameter("chkSabado").equals("true"))
            {
                empaVO.setHaySab(true);
            }
        }
        
        if(request.getParameter("ind") != null)
        {
            indPrevio = request.getParameter("ind").trim();
        }
        
        
        resAlt = EdiModProfAulaGestion.guardarEdiMod(empaVO);
        
        if(resAlt <= 0)
        {
            
            //Redireccionar a gestión niveles
            response.sendRedirect("ediciones/verModEd.jsp?codEdi="    + empaVO.getIdEdi()
                                                      + "&errorCode=" + resAlt
                                                      + "&ind="       + indPrevio );
        }
        else
        {            
            //Redireccionar a gestión niveles
            response.sendRedirect("ediciones/verModEd.jsp?codEdi="    + empaVO.getIdEdi()
                                                      + "&ind="       + indPrevio );
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
        return "Alta Módulo Edición servlet";
    }// </editor-fold>    
}
