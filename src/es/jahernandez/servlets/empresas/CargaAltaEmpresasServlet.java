/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.empresas;

import es.jahernandez.accesodatos.*;
import es.jahernandez.datos.*;

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
 * @author Alberto
 */
public class CargaAltaEmpresasServlet extends HttpServlet
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

        HttpSession sesion  = request.getSession();
        AlumnosVO   aluAlta = new AlumnosVO();

        String      urlDest = "empresas/altaEmp.jsp";
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Carga alta empresas" );
               
        }

        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("selValTipDoc") != null)
        {
            aluAlta.setTipDocAlu(new Integer(request.getParameter("selValTipDoc").trim()).intValue());
        }

        if(request.getParameter("txtNumDoc") != null)
        {
            aluAlta.setNumDocAlu(request.getParameter("txtNumDoc").trim().toUpperCase());
        }

        if(request.getParameter("chkDesempleado") != null)
        {
            if(request.getParameter("chkDesempleado").equals("true"))
            {
                aluAlta.setDesemp(true);
            }
        }

        if(request.getParameter("chkNoDeseado") != null)
        {
            if(request.getParameter("chkNoDeseado").equals("true"))
            {
                aluAlta.setAlND(true);
            }
        }

        if(request.getParameter("txtNombre") != null)
        {
            aluAlta.setNombre(request.getParameter("txtNombre").trim().toUpperCase());
        }

        if(request.getParameter("txtApe") != null)
        {
            aluAlta.setAp1Alu(request.getParameter("txtApe").trim().toUpperCase());
        }

        if(request.getParameter("selNivFor") != null)
        {
            aluAlta.setNivForm(new Integer(request.getParameter("selNivFor").trim()).intValue());
        }

        if(request.getParameter("selEmpresa") != null)
        {
            aluAlta.setEmpAlu(request.getParameter("selEmpresa").trim());
        }

        if(request.getParameter("txtTelf") != null)
        {
            aluAlta.setFijAlu(request.getParameter("txtTelf").trim());
        }

        if(request.getParameter("txtMovil") != null)
        {
            aluAlta.setMovAlu(request.getParameter("txtMovil").trim());
        }

        if(request.getParameter("txtMail") != null)
        {
            aluAlta.setEmail(request.getParameter("txtMail").trim());
        }

        if(request.getParameter("txtDirec") != null)
        {
            aluAlta.setDirAlu(request.getParameter("txtDirec").trim().toUpperCase());
        }

        if(request.getParameter("txtCodPos") != null)
        {
            aluAlta.setCodPosAlu(request.getParameter("txtCodPos").trim());
        }

        if(request.getParameter("txtLocal") != null)
        {
            aluAlta.setLocalAlu(request.getParameter("txtLocal").trim().toUpperCase());
        }

        if(request.getParameter("selProv") != null)
        {
            aluAlta.setCodProvAlu(request.getParameter("selProv").trim());
        }

        if(request.getParameter("txtObservaciones") != null)
        {
            aluAlta.setIntereses(request.getParameter("txtObservaciones").trim().toUpperCase());
        }

        aluAlta.setIdCen(1);  //Valor fijo para los nuevos interesados

        if(request.getParameter("txtFecNac") != null)
        {
            if (! request.getParameter("txtFecNac").equals(""))
            {
                String strFechaNac = request.getParameter("txtFecNac");
                aluAlta.setFecNac(new GregorianCalendar(new Integer(strFechaNac.substring(6,10)).intValue(),
                                                        new Integer(strFechaNac.substring(3,5)).intValue() - 1,
                                                        new Integer(strFechaNac.substring(0,2)).intValue()).getTime());
            }
        }

        if(request.getParameter("chkAutCesDat") != null)
        {
            if(request.getParameter("chkAutCesDat").equals("true"))
            {
                aluAlta.setAutCesDat(true);
            }
        }

        if(request.getParameter("chkAutComCom") != null)
        {
            if(request.getParameter("chkAutComCom").equals("true"))
            {
                aluAlta.setAutComCom(true);
            }
        }


        //Se compruba si se llama desde la ficha de alumnos
        if(request.getParameter("fichaAlumno") != null)
        {
            if(request.getParameter("fichaAlumno").equals("1"))
            {
                urlDest = "empresas/altaEmp.jsp?fichaAlumno=1";
            }
        }


        //Se introducen los datos del alumno en sesión y se carga la página de alta de empresa
        sesion.setAttribute("datAluAltEmp", aluAlta);
        response.sendRedirect(urlDest);

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
        return "CargaAltaEmpresasServlet";
    }// </editor-fold>

}
