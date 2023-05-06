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
public class DarAltaCursoInteresAlumnoServlet extends HttpServlet
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

        int         resultadoIns = 0;

        String      idNivel      = "00000000";
        String      idAlu        = "";
        String      idCur        = "";

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta curso interes alumno" );

        }


        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("codInteresado") != null)
        {
            idAlu = request.getParameter("codInteresado");
        }

        if(request.getParameter("lstCursos") != null)
        {
            idCur = request.getParameter("lstCursos");
        }

        if(request.getParameter("lstNiveles") != null &&
          !request.getParameter("lstNiveles").equals(""))
        {
            idNivel = request.getParameter("lstNiveles");
        }

        resultadoIns = CursosAluGestion.guardarCursosInteres(idAlu, idCur, idNivel);

        if(resultadoIns > 0 )
        {
            //Redireccionar a página de ficha alumnos
            response.sendRedirect("interesados/anaCurAlu.jsp?codInt=" + idAlu + "&insCor=1");
        }
        else
        {
            //Cargamos datos de nuevo alumno en sesión para cargar páginas siguientes
            response.sendRedirect("interesados/anaCurAlu.jsp?codInt=" + idAlu + "&errInsCur=" + resultadoIns);
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
