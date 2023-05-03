/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.datosauxiliares;

import es.jahernandez.accesodatos.*;
import es.jahernandez.datos.*;
import es.jahernandez.gestion.AulasGestion;

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
 * @author Alberto
 */
public class AltaAulaServlet extends HttpServlet
{
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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

        HttpSession   sesion       = request.getSession();
        AulasVO       aulAlta      = new AulasVO();
        String        resultadoAlt = "";

        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta aula" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtNombre") != null)
        {
            aulAlta.setNombre(request.getParameter("txtNombre").trim());
        }

        if(request.getParameter("lstCentros") != null)
        {
            aulAlta.setIdCen(new Integer(request.getParameter("lstCentros").trim()).intValue());
        }

        if(request.getParameter("txtPlazas") != null)
        {
            aulAlta.setNumPla(new Integer(request.getParameter("txtPlazas").trim()).intValue());
        }

        if(request.getParameter("txtDescripcion") != null)
        {
            aulAlta.setDescripcion(request.getParameter("txtDescripcion").trim());
        }

        if(request.getParameter("chkAulaInformatica") != null)
        {
            if(request.getParameter("chkAulaInformatica").equals("true"))
            {
                aulAlta.setEsAulInf(true);
            }
        }

        if(request.getParameter("chkTelevision") != null)
        {
            if(request.getParameter("chkTelevision").equals("true"))
            {
                aulAlta.setTieneTV(true);
            }
        }

        if(request.getParameter("chkImpresora") != null)
        {
            if(request.getParameter("chkImpresora").equals("true"))
            {
                aulAlta.setTieneImp(true);
            }
        }

        if(request.getParameter("chkProyector") != null)
        {
            if(request.getParameter("chkProyector").equals("true"))
            {
                aulAlta.setTieneProy(true);
            }
        }

        if(request.getParameter("chkAireAcond") != null)
        {
            if(request.getParameter("chkAireAcond").equals("true"))
            {
                aulAlta.setTieneAC(true);
            }
        }

        if(request.getParameter("chkInternet") != null)
        {
            if(request.getParameter("chkInternet").equals("true"))
            {
                aulAlta.setTieneInt(true);
            }
        }

        resultadoAlt = AulasGestion.guardarAula(aulAlta);

        if(resultadoAlt.equals("-1") || resultadoAlt.equals("-2"))
        {
            //Cargamos datos de nueva aula en sesión para cargar páginas siguientes
            sesion.setAttribute("nuevoAulaErr", aulAlta);

            //Redireccionar a alta de aula
            response.sendRedirect("aulas/anaAula.jsp?errorCode=" + resultadoAlt);
        }
        else
        {            
            //Redireccionar a página edición de aulas
            response.sendRedirect("aulas/ediAula.jsp?lstAula=" + resultadoAlt) ;
        }
        

    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
