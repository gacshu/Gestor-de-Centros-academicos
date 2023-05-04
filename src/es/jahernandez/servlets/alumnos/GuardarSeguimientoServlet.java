/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.alumnos;

import java.io.IOException;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.SeguimientosVO;
import es.jahernandez.gestion.SeguimientosGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class GuardarSeguimientoServlet extends HttpServlet
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
        
        HttpSession          sesion   = request.getSession();

        SeguimientosVO segVO  = new SeguimientosVO();
        int            resAlt = 0;
        String         ind    = "";

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Guardar seguimiento alumno" );
               
        }

        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("hidCodAlu") != null)
        {
            segVO.setIdAlu(request.getParameter("hidCodAlu").trim());
        }

        if(request.getParameter("txtUsuario") != null)
        {
            segVO.setUsuario(request.getParameter("txtUsuario").trim());
        }

        if(request.getParameter("txtFecha") != null &&
          ! request.getParameter("txtFecha").equals(""))
        {
            String strFechaNac = request.getParameter("txtFecha");
            segVO.setFecha(new GregorianCalendar(new Integer(strFechaNac.substring(6,10)).intValue(),
                                                    new Integer(strFechaNac.substring(3,5)).intValue() - 1,
                                                    new Integer(strFechaNac.substring(0,2)).intValue()).getTime());
        }
        
        if(request.getParameter("txtIncidencias") != null)
        {
            segVO.setIncidencias(request.getParameter("txtIncidencias").trim());
        }

        if(request.getParameter("lstCursos") != null)
        {
            segVO.setIdCur(request.getParameter("lstCursos").trim());
        }
        
        if(request.getParameter("ind") != null)
        {
            ind = request.getParameter("ind");
        }
        

        resAlt = SeguimientosGestion.guardarSeg(segVO);

        if(resAlt >= 0)
        {
            //Redireccionar a página de lista de seguimientos
            response.sendRedirect("seguimientos/verSegAlu.jsp?codInt=" + segVO.getIdAlu() + "&ind=" + ind);
        }
        else
        {
            //Volvemos a alta de de seguimientos
            response.sendRedirect("seguimientos/altaSeguimiento.jsp?codInt=" + segVO.getIdAlu()+ "&errorCode=-1&ind=" + ind);
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