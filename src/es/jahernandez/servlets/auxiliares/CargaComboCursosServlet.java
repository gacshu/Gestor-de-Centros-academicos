/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.auxiliares;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import es.jahernandez.datos.CursosVO;
import es.jahernandez.gestion.CursosGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 *
 * @author JuanAlberto
 */
public class CargaComboCursosServlet extends HttpServlet 
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
        Vector      vecCursos  = null; 
        CursosVO    curVO      = null;
        boolean     mostrarSel = false;
 
        if(request.getParameter("codTipo") != null )
        {
            if(request.getParameter("codCentro") != null )
            {
                vecCursos = CursosGestion.devolverDatCurTipCen( new Integer(request.getParameter("codTipo")).intValue()  ,  new Integer(request.getParameter("codCentro")).intValue());
            }    
            else
            {
                vecCursos = CursosGestion.devolverDatCurTip(new Integer(request.getParameter("codTipo")).intValue());
            }
                
        }
        
        if(request.getParameter("valSel") != null)
        {
                valSel = request.getParameter("valSel");
        }
        
        if(request.getParameter("muestraSelec") != null &&
           request.getParameter("muestraSelec").trim().equals("s"))
        {
            mostrarSel = true;
        }
        
        try 
        {            
                if(mostrarSel)
                {
                    out.printf("<option value=\"-1\">Seleccione...</option>");
                }
                for (int ind = 0; ind<vecCursos.size(); ind ++) 
                {                
                    curVO = (CursosVO) vecCursos.elementAt(ind);

                    if(valSel == null)
                    {
                            out.printf("<option value='%1s'>%2s</option>", curVO.getIdCur(), curVO.getNomCur());
                    }
                    else
                    {
                            if(valSel.trim().equals(curVO.getIdCur()))
                            {
                                out.printf("<option value='%1s' selected='selected'>%2s</option>", curVO.getIdCur(), curVO.getNomCur());
                            }
                            else
                            {
                                out.printf("<option value='%1s'>%2s</option>", curVO.getIdCur(), curVO.getNomCur());
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
        return "Servlet que carga combo cursos";
    }// </editor-fold>
}
