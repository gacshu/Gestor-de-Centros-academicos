/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.profesores;

import java.io.IOException;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.ProfesoresVO;
import es.jahernandez.gestion.ProfesoresGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class AltaProfesorServlet extends HttpServlet  
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
        ProfesoresVO  profAlta     = new ProfesoresVO();
        String        resultadoAlt = "";

        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta profesor" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtNombre") != null)
        {
            profAlta.setNombre(request.getParameter("txtNombre").trim());
        }
        
        if(request.getParameter("txtApellidos") != null)
        {
            profAlta.setApellidos(request.getParameter("txtApellidos").trim());
        }
        
        if(request.getParameter("selTipDoc") != null)
        {
            profAlta.setCodDoc(new Integer(request.getParameter("selTipDoc").trim()).intValue());
        }
        
        if(request.getParameter("txtNumDoc") != null)
        {
            profAlta.setNumDoc(request.getParameter("txtNumDoc").trim());
        }
        
        if(request.getParameter("txtFecNac") != null &&
          !request.getParameter("txtFecNac").equals(""))
        {
            String strFechaNac = request.getParameter("txtFecNac");
            profAlta.setFecNac(new GregorianCalendar(new Integer(strFechaNac.substring(6,10)).intValue(),
                                                     new Integer(strFechaNac.substring(3,5)).intValue() - 1,
                                                     new Integer(strFechaNac.substring(0,2)).intValue()).getTime());
        }
        
        if(request.getParameter("txtDireccion") != null)
        {
            profAlta.setDireccion(request.getParameter("txtDireccion").trim());
        }
        
        if(request.getParameter("txtLocalidad") != null)
        {
            profAlta.setLocalidad(request.getParameter("txtLocalidad").trim());
        }
        
        if(request.getParameter("selProv") != null)
        {
            profAlta.setCodProv(request.getParameter("selProv").trim());
        }
        
        if(request.getParameter("txtCodPostal") != null)
        {
            profAlta.setCodPos(request.getParameter("txtCodPostal").trim());
        }
        
        if(request.getParameter("txtTelef") != null)
        {
            profAlta.setTelef(request.getParameter("txtTelef").trim());
        }
        
        if(request.getParameter("txtMovil") != null)
        {
            profAlta.setMov(request.getParameter("txtMovil").trim());
        }
        
        if(request.getParameter("txtEmail") != null)
        {
            profAlta.setEmail(request.getParameter("txtEmail").trim());
        }
        
        if(request.getParameter("txtObserv") != null)
        {
            profAlta.setObserv(request.getParameter("txtObserv").trim());
        }

        //aluAlta.setIdAlu(AlumnosDAO.generarNuevoCodAlu()); //Genera un nuevo código de alumno

        resultadoAlt = ProfesoresGestion.insertaProfesor(profAlta);
        
        if(resultadoAlt.equals("0") || resultadoAlt.equals("-1") || resultadoAlt.equals("-2"))
        {
            //Cargamos datos de nuevo alumno en sesión para cargar páginas siguientes
            sesion.setAttribute("nuevoProfesorErr", profAlta);

            //Redireccionar a alta de alumnos
            response.sendRedirect("profesores/altaProfesor.jsp?errorCode=" + resultadoAlt);
        }
        else
        {
            
            //Redireccionar a página edición de profesores
            response.sendRedirect("profesores/datosProfesor.jsp?codProf=" + profAlta.getIdProf());
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
        return "Alta Profesor Servlet";
    }// </editor-fold>
}
