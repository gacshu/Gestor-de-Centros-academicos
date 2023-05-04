/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.alumnos;

import java.io.IOException;
import java.util.GregorianCalendar;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.AlumnosGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author Alberto
 */
public class AltaNuevoInteresadoServlet extends HttpServlet
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
        AlumnosVO     aluAlta      = new AlumnosVO();
        String        pestana      = "";
        String        resultadoAlt = "";

        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta interesado" );
               
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

        if(request.getParameter("chkDesempleado") != null &&
           request.getParameter("chkDesempleado").equals("true"))
        {
            aluAlta.setDesemp(true);
        }
        

        if(request.getParameter("chkNoDeseado") != null &&
           request.getParameter("chkNoDeseado").equals("true"))
        {
            aluAlta.setAlND(true);
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
        
        if(request.getParameter("hidFecNac") != null &&
           ! request.getParameter("hidFecNac").equals(""))
        {
            String strFechaNac = request.getParameter("hidFecNac");
            aluAlta.setFecNac(new GregorianCalendar(new Integer(strFechaNac.substring(6,10)).intValue(),
                                                    new Integer(strFechaNac.substring(3,5)).intValue() - 1,
                                                    new Integer(strFechaNac.substring(0,2)).intValue()).getTime());
        }

        if(request.getParameter("chkAutCesDat") != null &&
		   request.getParameter("chkAutCesDat").equals("true"))
        {
            aluAlta.setAutCesDat(true);
        }

        if(request.getParameter("chkAutComCom") != null &&
           request.getParameter("chkAutComCom").equals("true"))
        {
            aluAlta.setAutComCom(true);
        }
        
        
        if(request.getParameter("txtResponsable") != null)
        {
            aluAlta.setResponsable(request.getParameter("txtResponsable").trim().toUpperCase());
        }
        
        if(request.getParameter("txtColegio") != null)
        {
            aluAlta.setColegio(request.getParameter("txtColegio").trim().toUpperCase());
        }
        
        if(request.getParameter("txtCurso") != null)
        {
            aluAlta.setCurso(request.getParameter("txtCurso").trim().toUpperCase());
        }
        
        

        //Opciones de carga de pestaña
        if(request.getParameter("pestana") != null)
        {
            pestana = request.getParameter("pestana").trim();
        }


        //aluAlta.setIdAlu(AlumnosDAO.generarNuevoCodAlu()); //Genera un nuevo código de alumno

        resultadoAlt = AlumnosGestion.insertaAlumno(aluAlta);
        
        if(resultadoAlt.equals("0") || resultadoAlt.equals("-1") || resultadoAlt.equals("-2"))
        {
            //Cargamos datos de nuevo alumno en sesión para cargar páginas siguientes
            sesion.setAttribute("nuevoAlumnoErr", aluAlta);

            //Redireccionar a alta de alumnos
            response.sendRedirect("interesados/altaInteresados.jsp?errorCode=" + resultadoAlt);
        }
        else
        {
            //Cargamos datos de nuevo alumno en sesión para cargar páginas de datos alumno
            aluAlta.setIdAlu(resultadoAlt);
            sesion.setAttribute("datosAlumnoAlta", aluAlta);

            //Redireccionar a página edición de alumnos
            response.sendRedirect("interesados/datosInteresados.jsp");
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
    throws ServletException, IOException
    {
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
    throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Alta interesados servlet";
    }// </editor-fold>

}
