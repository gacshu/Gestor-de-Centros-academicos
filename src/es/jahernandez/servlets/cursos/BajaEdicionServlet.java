/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import es.jahernandez.accesodatos.EdicionesDAO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.EdicionesVO ;
import es.jahernandez.gestion.EdicionesGestion;

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
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;


/**
 *
 * @author JuanAlberto
 */
public class BajaEdicionServlet extends HttpServlet 
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

        HttpSession sesion       = request.getSession();
        int         resultadoBor = 0;

        String      idEdi        = "";
        String      idCur        = "";
        String      indInf       = "";
        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Baja edición" );
               
        }
       

        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("codEdi") != null)
        {
            idEdi = request.getParameter("codEdi");
        }
        
        if(request.getParameter("codCur") != null)
        {
            idCur  = request.getParameter("codCur");   
        }
        
        if(request.getParameter("valInfEdi") != null)
        {
            indInf = request.getParameter("valInfEdi");
        }
        
        
        resultadoBor = EdicionesGestion.eliminarDatosEdi(idEdi);

        
        //Redireccionar a página de ficha alumnos
        response.sendRedirect("ediciones/listEdiCurso.jsp?codCur="   + idCur 
                                                      + "&valInfEdi" + indInf);
        
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
        return "Baja edición servlet";
    }// </editor-fold>

}
