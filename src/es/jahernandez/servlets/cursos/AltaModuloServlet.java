/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import es.jahernandez.accesodatos.ModulosDAO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.ModulosVO;
import es.jahernandez.gestion.ModulosGestion;

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
public class AltaModuloServlet extends HttpServlet 
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
        
        HttpSession   sesion       = request.getSession();
        ModulosVO     modVO        = new ModulosVO();
        int           resultadoAlt = 0;
        String        indPrevio    = "";
        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta módulo" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtNuevoNombre") != null)
        {
            modVO.setNombre(request.getParameter("txtNuevoNombre").trim());
        }
       
        if(request.getParameter("txtNuevoDescrip") != null)
        {
            modVO.setDescripcion(request.getParameter("txtNuevoDescrip").trim());
        }

        if(request.getParameter("txtCodCurso") != null)
        {
            modVO.setCodCur(request.getParameter("txtCodCurso").trim());
        }      
        if(request.getParameter("lstNuevoArea") != null)
        {
            modVO.setCodArea(request.getParameter("lstNuevoArea").trim());
        } 
        
        if(request.getParameter("txtNuevoHoras") != null)
        {
            modVO.setNumHoras(new Integer(request.getParameter("txtNuevoHoras").trim()).intValue());
        } 
        
        if(request.getParameter("valInfMod") != null)
        {
            indPrevio = request.getParameter("valInfMod");
        } 
        
        resultadoAlt = ModulosGestion.guardarModulo(modVO);
        
        if(resultadoAlt <= 0)
        {
            
            //Redireccionar a gestión modulos
            response.sendRedirect("cursos/gestionModulos.jsp?codCurso="  + modVO.getCodCur() 
                                                         + "&errorCode=" + resultadoAlt
                                                         + "&ind="       + indPrevio );
        }
        else
        {            
            //Redireccionar a gestión modulos
            response.sendRedirect("cursos/gestionModulos.jsp?codCurso=" + modVO.getCodCur()
                                                         + "&ind="      + indPrevio );
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
        return "Alta Módulo servlet";
    }// </editor-fold>    
}
