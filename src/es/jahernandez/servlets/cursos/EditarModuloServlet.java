/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import java.io.IOException;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.ModulosVO;
import es.jahernandez.gestion.ModulosGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 *
 * @author JuanAlberto
 */
public class EditarModuloServlet extends HttpServlet 
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
        ModulosVO   modEdi       = new ModulosVO();
        int         resultadoEdi = 0;
        String      indPrev      = "";
            
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Editar módulo" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtCodMod") != null)
        {
            modEdi.setCodMod(request.getParameter("txtCodMod").trim());
        }

        if(request.getParameter("txtNombre") != null)
        {
            modEdi.setNombre(request.getParameter("txtNombre").trim());
        }

        if(request.getParameter("txtDescrip") != null)
        {
            modEdi.setDescripcion(request.getParameter("txtDescrip").trim());
        }

        if(request.getParameter("txtHoras") != null)
        {
            modEdi.setNumHoras(new Integer(request.getParameter("txtHoras").trim()).intValue());
        }
       
        if(request.getParameter("txtArea") != null)
        {
            modEdi.setCodArea(request.getParameter("txtArea").trim());
        }        
                       
        if(request.getParameter("txtCodCur") != null)
        {
            modEdi.setCodCur(request.getParameter("txtCodCur").trim());
        }
        
        if(request.getParameter("valInfNiv") != null)
        {
            indPrev =  request.getParameter("valInfNiv").trim();
        }
        
        resultadoEdi = ModulosGestion.editaModulo(modEdi);

        if(resultadoEdi < 0 )
        {
            //Redireccionar a página edición de aulas
            response.sendRedirect("cursos/gestionModulos.jsp?codCurso=" + modEdi.getCodCur() + "&valInfMod" + indPrev +  "&errorEdi=" + resultadoEdi);
        }
        else
        {
            response.sendRedirect("cursos/gestionModulos.jsp?codCurso=" + modEdi.getCodCur() + "&valInfMod" + indPrev  );
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
        return "Editar modulo servlet";
    }// </editor-fold>
}
