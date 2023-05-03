/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.seguridad;

import es.jahernandez.accesodatos.ConUsoDAO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.ConUsoGestion;

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
public class EdiUserServlet extends HttpServlet {

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
        
        HttpSession   sesion       = request.getSession();
        ConUsuVO      userVO       = new ConUsuVO();
        int           resultadoEdi = 0;
        String        indPrevio    = "";
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Editar usuario" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtUser") != null)
        {
            userVO.setUsuario(request.getParameter("txtUser").trim());
        }
       
        if(request.getParameter("txtPass") != null)
        {
            userVO.setPassword(request.getParameter("txtPass").trim());
        }

        if(request.getParameter("lstNivAcc") != null)
        {
            userVO.setNivelAcceso(new Integer(request.getParameter("lstNivAcc").trim()).intValue());
        }
        
        if(request.getParameter("txtNombre") != null)
        {
            userVO.setNombre(request.getParameter("txtNombre").trim());
        }
        
        if(request.getParameter("lstCentros") != null)
        {
            userVO.setIdCentro(new Integer(request.getParameter("lstCentros").trim()).intValue());
        }
        
        if(request.getParameter("chkActivo") != null)
        {
            if(request.getParameter("chkActivo").equals("true"))
            {
                userVO.setActivo(true);
            }
        }
        
        if(request.getParameter("lstProf") != null)
        {
            userVO.setIdProf(request.getParameter("lstProf").trim());
        }
        
        if(request.getParameter("ind") != null)
        {
            indPrevio = request.getParameter("ind").trim();
        }
                        
        resultadoEdi = ConUsoGestion.editaUser(userVO,"");
        
        if(resultadoEdi <= 0)
        {
            
            //Redireccionar a gestión usuarios
            response.sendRedirect("control/listaUsers.jsp?ind="        + indPrevio 
                                                      + "&codErrEdi="  + resultadoEdi);
        }
        else
        {            
            //Redireccionar a gestión usuarios
            response.sendRedirect("control/listaUsers.jsp?ind=" + indPrevio);                                      
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
        return "Editar usuario servlet";
    }// </editor-fold>
}
