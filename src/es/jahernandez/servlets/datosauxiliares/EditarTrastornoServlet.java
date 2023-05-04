/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.datosauxiliares;

import java.io.IOException;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.TrastornosVO;
import es.jahernandez.gestion.TrastornosGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class EditarTrastornoServlet extends HttpServlet 
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
        
        HttpSession  sesion       = request.getSession();
        TrastornosVO trastVO      = new TrastornosVO();
        int          resultadoEdi = 0;
        String       pagAdapTrast = "0";
        
        Logger       log          = null;
        ConUsuVO     conUsoVO     = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Edición trastorno" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtCodTrast") != null)
        {
            trastVO.setCodTrastorno(request.getParameter("txtCodTrast").trim());
        }
        
        if(request.getParameter("txtCodInt") != null)
        {
            trastVO.setIdAlu(request.getParameter("txtCodInt").trim());
        }
       
        if(request.getParameter("lstTipTras" + trastVO.getCodTrastorno()) != null)
        {
            trastVO.setCodTipoTrastorno(request.getParameter("lstTipTras" + trastVO.getCodTrastorno()).trim());
        }

        if(request.getParameter("chkMedicado" + trastVO.getCodTrastorno()) != null &&
           request.getParameter("chkMedicado" + trastVO.getCodTrastorno()).equals("true"))
        {
            trastVO.setMedicado(true);
        }
              
        if(request.getParameter("txtMedicacion" + trastVO.getCodTrastorno()) != null)
        {
            trastVO.setMedicacion(request.getParameter("txtMedicacion" + trastVO.getCodTrastorno()).trim());
        }  
        
        if(request.getParameter("valInfTrast") != null)
        {
            pagAdapTrast =  request.getParameter("valInfTrast");
        } 
        
        resultadoEdi = TrastornosGestion.editaTrastorno(trastVO);
        
        if(resultadoEdi <= 0)
        {
            
            //Redireccionar a trastornos
            response.sendRedirect("interesados/trastornosFichaAlumno.jsp?codInt="      + trastVO.getIdAlu()
                                                                     + "&valInfTrast=" + pagAdapTrast 
                                                                     + "&errorEdi="    + resultadoEdi );
        }
        else
        {            
            //Redireccionar a gestión niveles
            response.sendRedirect("interesados/trastornosFichaAlumno.jsp?codInt="       + trastVO.getIdAlu()
                                                                     + "&valInfTrast="  + pagAdapTrast);                                                                   
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
        return "Edición trastorno servlet";
    }// </editor-fold>


}
