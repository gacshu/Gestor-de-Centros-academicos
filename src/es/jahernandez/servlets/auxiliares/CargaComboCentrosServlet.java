/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.auxiliares;

import es.jahernandez.accesodatos.CentrosDAO;
import es.jahernandez.datos.CentrosVO;
import es.jahernandez.gestion.CentrosGestion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author JuanAlberto
 */
public class CargaComboCentrosServlet extends HttpServlet 
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
        // TODO Auto-generated method stub
        response.setContentType("text/html; charset=UTF-8");
        //Control de caché
        response.setDateHeader ("Expires", -1); 
        response.setHeader("Pragma","no-cache"); 
        if(request.getProtocol().equals("HTTP/1.1")) 
            response.setHeader("Cache-Control","no-cache"); 
        
        PrintWriter out        = response.getWriter();        
        int	    valSel     = -99;
        Vector      vecCentros = CentrosGestion.datComCentros();
        CentrosVO   cenVO      = null;
        
        if(request.getParameter("valSel") != null)
        {
            valSel = new Integer(request.getParameter("valSel")).intValue();
        }
        
        try 
        {            
            out.printf("<option value=\"-1\">Seleccione...</option>");
            
            for (int ind = 0; ind<vecCentros.size(); ind ++) 
            {                
                cenVO = (CentrosVO) vecCentros.elementAt(ind);

                if(valSel == -99)
                {
                        out.printf("<option value='%1s'>%2s</option>", cenVO.getIdCentro(), cenVO.getNombreCentro());
                }
                else
                {
                        if(valSel == cenVO.getIdCentro())
                        {
                            out.printf("<option value='%1s' selected='selected'>%2s</option>", cenVO.getIdCentro(), cenVO.getNombreCentro());
                        }
                        else
                        {
                            out.printf("<option value='%1s'>%2s</option>", cenVO.getIdCentro(), cenVO.getNombreCentro());
                        }
                }            
            }        
        } 
        finally 
        {            
                if (out!=null)
                {
                        out.flush();
                        out.close();        
                }
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
        return "Servlet que carga combo centros";
    }// </editor-fold>

}
