/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.profesores;

import es.jahernandez.accesodatos.ProfesoresDAO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.ProfesoresVO;
import es.jahernandez.gestion.ProfesoresGestion;

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
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author JuanAlberto
 */
public class EditarProfesorServlet extends HttpServlet 
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
        ProfesoresVO  profEdi      = new ProfesoresVO();
        int           resultadoEdi = -99;
        String        ind          = "";

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Editar profesor" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("hidCodProf") != null)
        {
            profEdi.setIdProf(request.getParameter("hidCodProf").trim());
        }
        
        if(request.getParameter("txtNombre") != null)
        {
            profEdi.setNombre(request.getParameter("txtNombre").trim());
        }
        
        if(request.getParameter("txtApellidos") != null)
        {
            profEdi.setApellidos(request.getParameter("txtApellidos").trim());
        }
        
        if(request.getParameter("selTipDoc") != null)
        {
            profEdi.setCodDoc(new Integer(request.getParameter("selTipDoc").trim()).intValue());
        }
        
        if(request.getParameter("txtNumDoc") != null)
        {
            profEdi.setNumDoc(request.getParameter("txtNumDoc").trim());
        }
        
        if(request.getParameter("txtFecNac") != null)
        {
            if (! request.getParameter("txtFecNac").equals(""))
            {
                String strFechaNac = request.getParameter("txtFecNac");
                profEdi.setFecNac(new GregorianCalendar(new Integer(strFechaNac.substring(6,10)).intValue(),
                                                         new Integer(strFechaNac.substring(3,5)).intValue() - 1,
                                                         new Integer(strFechaNac.substring(0,2)).intValue()).getTime());
            }
        }
        
        if(request.getParameter("txtDireccion") != null)
        {
            profEdi.setDireccion(request.getParameter("txtDireccion").trim());
        }
        
        if(request.getParameter("txtLocalidad") != null)
        {
            profEdi.setLocalidad(request.getParameter("txtLocalidad").trim());
        }
        
        if(request.getParameter("selProv") != null)
        {
            profEdi.setCodProv(request.getParameter("selProv").trim());
        }
        
        if(request.getParameter("txtCodPostal") != null)
        {
            profEdi.setCodPos(request.getParameter("txtCodPostal").trim());
        }
        
        if(request.getParameter("txtTelef") != null)
        {
            profEdi.setTelef(request.getParameter("txtTelef").trim());
        }
        
        if(request.getParameter("txtMovil") != null)
        {
            profEdi.setMov(request.getParameter("txtMovil").trim());
        }
        
        if(request.getParameter("txtEmail") != null)
        {
            profEdi.setEmail(request.getParameter("txtEmail").trim());
        }
        
        if(request.getParameter("txtObserv") != null)
        {
            profEdi.setObserv(request.getParameter("txtObserv").trim());
        }
        
        if(request.getParameter("ind") != null)
        {
            ind = request.getParameter("ind").trim();
        }
        
        if(request.getParameter("chkActivo") != null)
        {
            if( request.getParameter("chkActivo").equals("true"))
            {
                profEdi.setActivo(true);
            } 
        }

        //aluAlta.setIdAlu(AlumnosDAO.generarNuevoCodAlu()); //Genera un nuevo código de alumno

        resultadoEdi = ProfesoresGestion.editaProfesor(profEdi);
        
        if(resultadoEdi < 0 )
        {
            //Cargamos datos de nuevo alumno en sesión para cargar páginas siguientes
            sesion.setAttribute("edicionProfesorErr", profEdi);

            //Redireccionar a alta de profesores
            response.sendRedirect("profesores/datosProfesor.jsp?codProf=" + profEdi.getIdProf() + "&errorCode=" + resultadoEdi + "&ind=" + ind);
        }
        else
        {
            //Redireccionar a página edición de profesores
            response.sendRedirect("profesores/datosProfesor.jsp?codProf=" + profEdi.getIdProf() + "&ind=" + ind);
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
        return "Edición Profesor Servlet";
    }// </editor-fold>
}
