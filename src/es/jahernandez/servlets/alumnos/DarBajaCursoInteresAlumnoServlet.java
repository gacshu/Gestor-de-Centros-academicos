/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.alumnos;

import java.io.IOException;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.CursosAluGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class DarBajaCursoInteresAlumnoServlet extends HttpServlet
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

        HttpSession sesion   = request.getSession();
        
        int         resultadoBor = 0;

        String      idNivel      = "";
        String      idAlu        = "";
        String      idCur        = "";
        String      indPrev      = ""; 
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Dar Baja curso interés alumno" );
               
        }

        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("codInteresado") != null)
        {
            idAlu = request.getParameter("codInteresado");
        }

        if(request.getParameter("codCurso") != null)
        {
            idCur = request.getParameter("codCurso");
        }

        if(request.getParameter("codNiv") != null)
        {

            idNivel = request.getParameter("codNiv");

        }
        
        if(request.getParameter("ind") != null)
        {

            indPrev = request.getParameter("ind");

        }

        resultadoBor = CursosAluGestion.eliminarCurAlu(idAlu, idCur, idNivel);

        if(resultadoBor > 0 )
        {
            //Redireccionar a página de ficha alumnos
            response.sendRedirect("interesados/cursosFichaAlumno.jsp?codInt=" + idAlu + "&ind=" + indPrev);
        }
        else
        {
            //Cargamos datos de nuevo alumno en sesión para cargar páginas siguientes
            response.sendRedirect("interesados/cursosFichaAlumno.jsp?codInt=" + idAlu + "&errBorCur=-1&ind=" + indPrev);
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
