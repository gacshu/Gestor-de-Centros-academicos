/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.auxiliares;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import es.jahernandez.datos.HisRecVO;
import es.jahernandez.gestion.HisRecGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author JuanAlberto
 */
public class CargaComboRecPenAluServlet extends HttpServlet 
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
        String	    valSel     = null;
        Vector      vecRec     = null; 
        HisRecVO    histRecVO  = null;
      
        if(request.getParameter("codAlu") != null )
        {
            vecRec = HisRecGestion.devRecHisAluEdi("", "", "", request.getParameter("codAlu"), 2, 0, 0);
        }
        
        if(request.getParameter("valSel") != null)
        {
                valSel = request.getParameter("valSel");
        }
        
        try 
        {            
               
                out.printf("<option value=\"-1\">Seleccione...</option>");
                
                for (int ind = 0; ind<vecRec.size(); ind ++) 
                {                
                    histRecVO = (HisRecVO) vecRec.elementAt(ind);

                    if(valSel == null)
                    {
                            out.printf("<option value='%1s'>%2s</option>", histRecVO.getNumRec(), histRecVO.getNumRec());
                    }
                    else
                    {
                            if(valSel.trim().equals(histRecVO.getNumRec()))
                            {
                                out.printf("<option value='%1s' selected='selected'>%2s</option>", histRecVO.getNumRec(), histRecVO.getNumRec());
                            }
                            else
                            {
                                out.printf("<option value='%1s'>%2s</option>", histRecVO.getNumRec(), histRecVO.getNumRec());
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
        return "Servlet que carga combo recibos pendiente alumnos";
    }// </editor-fold>
}
