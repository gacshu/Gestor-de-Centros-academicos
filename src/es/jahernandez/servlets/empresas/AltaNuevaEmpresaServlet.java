/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.empresas;

import java.io.IOException;
import java.util.GregorianCalendar;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.EmpresasVO;
import es.jahernandez.gestion.EmpresasGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class AltaNuevaEmpresaServlet extends HttpServlet
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

        HttpSession   sesion        = request.getSession();
        EmpresasVO    empAlta       = new EmpresasVO();
        String        resultadoAlt  = "";
        boolean       irFicha       = false;
        String        urlDestino    = "";
        String        urlDestinoErr = "";
        int           urlProc       = -1;    

        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta empresa" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtRazonSocial") != null)
        {
            empAlta.setNombreEmpresa(request.getParameter("txtRazonSocial").trim().toUpperCase());
        }

        if(request.getParameter("txtDirEmp") != null)
        {
            empAlta.setDirEmpresa(request.getParameter("txtDirEmp").trim().toUpperCase());
        }

        if(request.getParameter("txtLocEmp") != null)
        {
            empAlta.setPobEmpresa(request.getParameter("txtLocEmp").trim().toUpperCase());
        }

        if(request.getParameter("txtCarEmp") != null)
        {
            empAlta.setCar1(request.getParameter("txtCarEmp").trim().toUpperCase());
        }

        if(request.getParameter("txtRepEmp") != null)
        {
            empAlta.setResEmpresa(request.getParameter("txtRepEmp").trim().toUpperCase());
        }

        if(request.getParameter("selActEmp") != null)
        {
            empAlta.setCodAct(new Integer(request.getParameter("selActEmp").trim()).intValue()) ;
        }

        if(request.getParameter("txtCIF") != null)
        {
            empAlta.setCifEmpresa(request.getParameter("txtCIF").trim().toUpperCase());
        }

        if(request.getParameter("txtCodPosEmp") != null)
        {
            empAlta.setCodPostal(request.getParameter("txtCodPosEmp").trim());
        }

        if(request.getParameter("selProEmp") != null)
        {
            empAlta.setCodProv(request.getParameter("selProEmp").trim());
        }

        if(request.getParameter("txtCNAE") != null &&
          !request.getParameter("txtCNAE").trim().equals(""))
        {
            empAlta.setCnae(new Integer(request.getParameter("txtCNAE")).intValue());
        }
        
        if(request.getParameter("txtNomCom") != null)
        {
            empAlta.setNomComercial(request.getParameter("txtNomCom").trim().toUpperCase());
        }

        if(request.getParameter("txtDirCom") != null)
        {
            empAlta.setDirCom(request.getParameter("txtDirCom").trim().toUpperCase());
        }

        if(request.getParameter("txtLocCom") != null)
        {
            empAlta.setPobCom(request.getParameter("txtLocCom").trim().toUpperCase());
        }

        if(request.getParameter("txtTelf") != null)
        {
            empAlta.setTelEmpresa(request.getParameter("txtTelf").trim());
        }

        if(request.getParameter("txtEMail") != null)
        {
            empAlta.setMailEmpresa(request.getParameter("txtEMail").trim());
        }

        if(request.getParameter("txtRepCom") != null)
        {
            empAlta.setResEmp2(request.getParameter("txtRepCom").trim().toUpperCase());
        }

        if(request.getParameter("txtCodPosCom") != null)
        {
            empAlta.setCodPosCom(request.getParameter("txtCodPosCom").trim());
        }

        if(request.getParameter("selProCom") != null)
        {
            empAlta.setProCom(request.getParameter("selProCom").trim());
        }

        if(request.getParameter("txtTelf2") != null)
        {
            empAlta.setFaxEmpresa(request.getParameter("txtTelf2").trim());
        }

        if(request.getParameter("chkDatAct") != null &&
           request.getParameter("chkDatAct").equals("true"))
            {
                empAlta.setDatAct(true);
            }
        
        if(request.getParameter("txtNumEmp") != null &&
          !request.getParameter("txtNumEmp").trim().equals(""))
        {
            empAlta.setNumEmp(new Integer(request.getParameter("txtNumEmp")).intValue());
        }
        
        if(request.getParameter("chkAdCon") != null &&
           request.getParameter("chkAdCon").equals("true"))
        {
            empAlta.setConvenioAd(true);
        }
    
        if(request.getParameter("hidFecCon") != null &&
          !request.getParameter("hidFecCon").equals(""))
        {
            String strFechaCon = request.getParameter("hidFecCon");
            empAlta.setFecCon(new GregorianCalendar(new Integer(strFechaCon.substring(6,10)).intValue(),
                                                    new Integer(strFechaCon.substring(3,5)).intValue() - 1,
                                                    new Integer(strFechaCon.substring(0,2)).intValue()).getTime());
        }
   
        if(request.getParameter("selImpExp") != null)
        {
            empAlta.setImpExp(request.getParameter("selImpExp").trim());
        }

        if(request.getParameter("chkesCliente") != null &&
           request.getParameter("chkesCliente").equals("true"))
        {
            empAlta.setEsCliente(true);
        }

        if(request.getParameter("txtCuota") != null &&
          !request.getParameter("txtCuota").trim().equals(""))
        {
            empAlta.setCuota(new Integer(request.getParameter("txtCuota")).intValue());
        }
    
        if(request.getParameter("txtVolNeg") != null)
        {
            empAlta.setVolNeg(request.getParameter("txtVolNeg").trim().toUpperCase());
        }

        if(request.getParameter("chkAutCesDat") != null &&
           request.getParameter("chkAutCesDat").equals("true"))
        {
            empAlta.setAutCesDat(true);
        }
    
        if(request.getParameter("chkAcc") != null &&
           request.getParameter("chkAcc").equals("true"))
        {
            empAlta.setAccArco(true);
        }
    
        if(request.getParameter("hidFecAcc") != null &&
          !request.getParameter("hidFecAcc").equals(""))
        {
            String strFechaAcc = request.getParameter("hidFecAcc");
            empAlta.setFecAccArc(new GregorianCalendar(new Integer(strFechaAcc.substring(6,10)).intValue(),
                                                       new Integer(strFechaAcc.substring(3,5)).intValue() - 1,
                                                       new Integer(strFechaAcc.substring(0,2)).intValue()).getTime());
        }

        if(request.getParameter("chkRec") != null &&
           request.getParameter("chkRec").equals("true"))
        {
            empAlta.setRecArco(true);
        }
        
        if(request.getParameter("hidFecRec") != null &&
          !request.getParameter("hidFecRec").equals(""))
        {
            String strFechaRec = request.getParameter("hidFecRec");
            empAlta.setFecRecArc(new GregorianCalendar(new Integer(strFechaRec.substring(6,10)).intValue(),
                                                       new Integer(strFechaRec.substring(3,5)).intValue() - 1,
                                                       new Integer(strFechaRec.substring(0,2)).intValue()).getTime());
        }
    
        if(request.getParameter("chkCancel") != null &&
           request.getParameter("chkCancel").equals("true"))
        {
            empAlta.setCanArco(true);
        }

        if(request.getParameter("hidFecCan") != null &&
          !request.getParameter("hidFecCan").equals(""))
        {
            String strFechaCan = request.getParameter("hidFecCan");
            empAlta.setFecCanArc(new GregorianCalendar(new Integer(strFechaCan.substring(6,10)).intValue(),
                                                       new Integer(strFechaCan.substring(3,5)).intValue() - 1,
                                                       new Integer(strFechaCan.substring(0,2)).intValue()).getTime());
        }
        

        if(request.getParameter("chkOposic") != null &&
           request.getParameter("chkOposic").equals("true"))
        {
            empAlta.setOpoArco(true);
        }
    
        if(request.getParameter("hidFecOpo") != null &&
          !request.getParameter("hidFecOpo").equals(""))
        {
            String strFechaOpo = request.getParameter("hidFecOpo");
            empAlta.setFecOpoArc(new GregorianCalendar(new Integer(strFechaOpo.substring(6,10)).intValue(),
                                                       new Integer(strFechaOpo.substring(3,5)).intValue() -1 ,
                                                       new Integer(strFechaOpo.substring(0,2)).intValue()).getTime());
        }
    
        //Se comprueba si se llama desde la ficha de alumnos
        if(request.getParameter("fichaAlumno") != null &&
           request.getParameter("fichaAlumno").equals("1"))
        {
            irFicha = true;
        }
    
        if(request.getParameter("urlProc") != null)
        {
            urlProc = new Integer(request.getParameter("urlProc")).intValue();
            switch(urlProc)
            {
                case 1: urlDestino = "empresas/confAltaEmpresa.jsp?urlProc=1"; urlDestinoErr = "empresas/altaEmpInter.jsp"; break;
                case 2: urlDestino = "empresas/confAltaEmpresa.jsp?urlProc=2"; urlDestinoErr = "empresas/altaEmpInter.jsp"; break;
                case 3: urlDestino = "empresas/datEmpresa.jsp?urlProc=3";      urlDestinoErr = "empresas/altaEmp.jsp"; break;
            }
        }
        
        resultadoAlt = EmpresasGestion.guardaEmp(empAlta);

        if(resultadoAlt.equals("0") || resultadoAlt.equals("-1"))
        {
            //Cargamos datos de nueva empresa en sesión para cargar páginas siguientes
            sesion.setAttribute("nuevaEmpresaErr", empAlta);

            //Redireccionar a alta de empresas
            response.sendRedirect(urlDestinoErr + "?errorCode=" + resultadoAlt);
        }
        else
        {
            //Redireccionar a página en función de procedencia
            response.sendRedirect(urlDestino + "&codNuevaEmp=" + resultadoAlt + "&codEmp=" + resultadoAlt);
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
        return "Short description";
    }// </editor-fold>

}
