/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.alumnos;

import java.io.IOException;
import java.util.GregorianCalendar;

//import org.apache.catalina.SessionEvent;
import org.apache.commons.lang.StringEscapeUtils;
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
public class EditarInteresadoServlet extends HttpServlet
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
        AlumnosVO     aluEdi       = new AlumnosVO();
        //String        pestana      = "1";
        int           resultadoEdi = 0;

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Editar interesado" );

        }


        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("selValTipDoc") != null)
        {
            aluEdi.setTipDocAlu(new Integer(request.getParameter("selValTipDoc").trim()).intValue());
        }

        if(request.getParameter("txtNumDoc") != null)
        {
            aluEdi.setNumDocAlu(request.getParameter("txtNumDoc").trim().toUpperCase());
        }

        if(request.getParameter("chkDesempleado") != null &&
           request.getParameter("chkDesempleado").equals("true"))
        {
            aluEdi.setDesemp(true);
        }


        if(request.getParameter("chkNoDeseado") != null &&
           request.getParameter("chkNoDeseado").equals("true"))
        {
            aluEdi.setAlND(true);
        }

        if(request.getParameter("hidCodInt") != null)
        {
            aluEdi.setIdAlu(request.getParameter("hidCodInt").trim());
        }

        if(request.getParameter("hidNombre") != null)
        {
            aluEdi.setNombre(request.getParameter("hidNombre").trim().toUpperCase());
        }

        if(request.getParameter("hidApe") != null)
        {
            aluEdi.setAp1Alu(request.getParameter("hidApe").trim().toUpperCase());
        }

        if(request.getParameter("selNivFor") != null)
        {
            aluEdi.setNivForm(new Integer(request.getParameter("selNivFor").trim()).intValue());
        }

        if(request.getParameter("selEmpresa") != null)
        {
            aluEdi.setEmpAlu(request.getParameter("selEmpresa").trim());
        }

        if(request.getParameter("txtTelf") != null)
        {
            aluEdi.setFijAlu(request.getParameter("txtTelf").trim());
        }

        if(request.getParameter("txtMovil") != null)
        {
            aluEdi.setMovAlu(request.getParameter("txtMovil").trim());
        }

        if(request.getParameter("txtMail") != null)
        {
            aluEdi.setEmail(request.getParameter("txtMail").trim());
        }

        if(request.getParameter("txtDirec") != null)
        {
            aluEdi.setDirAlu( StringEscapeUtils.unescapeHtml(request.getParameter("txtDirec").trim().toUpperCase()));
        }

        if(request.getParameter("txtCodPos") != null)
        {
            aluEdi.setCodPosAlu(request.getParameter("txtCodPos").trim());
        }

        if(request.getParameter("txtLocal") != null)
        {
            aluEdi.setLocalAlu(request.getParameter("txtLocal").trim().toUpperCase());
        }

        if(request.getParameter("selProv") != null)
        {
            aluEdi.setCodProvAlu(request.getParameter("selProv").trim());
        }

        if(request.getParameter("txtObservaciones") != null)
        {
            aluEdi.setIntereses(StringEscapeUtils.unescapeHtml( request.getParameter("txtObservaciones").trim().toUpperCase()));
        }

        aluEdi.setIdCen(1);  //Valor fijo que se mantiene por compatibilidad

        if(request.getParameter("hidFecNac") != null &&
          !request.getParameter("hidFecNac").equals(""))
        {
            String strFechaNac = request.getParameter("hidFecNac");
            aluEdi.setFecNac(new GregorianCalendar(new Integer(strFechaNac.substring(6,10)).intValue(),
                                                    new Integer(strFechaNac.substring(3,5)).intValue() - 1,
                                                    new Integer(strFechaNac.substring(0,2)).intValue()).getTime());
        }

        if(request.getParameter("chkAutCesDat") != null)
        {
            if(request.getParameter("chkAutCesDat").equals("true"))
            {
                aluEdi.setAutCesDat(true);
            }
        }

        if(request.getParameter("chkAutComCom") != null &&
           request.getParameter("chkAutComCom").equals("true"))
        {
            aluEdi.setAutComCom(true);
        }

        if(request.getParameter("txtResponsable") != null)
        {
            aluEdi.setResponsable(request.getParameter("txtResponsable").trim().toUpperCase());
        }

        if(request.getParameter("txtColegio") != null)
        {
            aluEdi.setColegio(request.getParameter("txtColegio").trim().toUpperCase());
        }

        if(request.getParameter("txtCurso") != null)
        {
            aluEdi.setCurso(request.getParameter("txtCurso").trim().toUpperCase());
        }


        resultadoEdi = AlumnosGestion.editaAlumno(aluEdi);

        if(resultadoEdi > 0 )
        {
            //Redireccionar a página edición de alumnos
            response.sendRedirect("interesados/datPerFichaAlumno.jsp?codInt=" + aluEdi.getIdAlu());
        }
        else
        {
            //Cargamos datos de nuevo alumno en sesión para cargar páginas siguientes
            sesion.setAttribute("ediAlumnoErr", aluEdi);

            //Redireccionar a alta de alumnos
            response.sendRedirect("interesados/datPerFichaAlumno.jsp?errorCode=" + resultadoEdi);
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
        return "Editar interesado servlet";
    }// </editor-fold>

}
