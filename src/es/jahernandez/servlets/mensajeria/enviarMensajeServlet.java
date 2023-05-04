/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.mensajeria;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

//Paquetes de manejo de e-mail
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.InformacionConf;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class enviarMensajeServlet extends HttpServlet 
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
        
        HttpSession    sesion        = request.getSession();
        ServletContext sc            = null;
        
        String[]       dest          = null;
        String         asunto        = "";
        String         cuerpo        = "";
        String[]       listaArchivos = null;
        
        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Enviar mensaje" );
               
        }
        
        
        if (sesion.getAttribute("dest") != null)
        { 
            dest =  ((String) sesion.getAttribute("dest")).split(",");
        }
        
        if (request.getParameter("txtAsunto") != null)
        { 
            asunto = request.getParameter("txtAsunto");
        }
        
        if (request.getParameter("txtCuerpo") != null)
        { 
            cuerpo = request.getParameter("txtCuerpo");
        }
        
        if (request.getParameter("lstArchivos") != null &&
           !request.getParameter("lstArchivos").trim().equals(""))
        {
            listaArchivos = request.getParameter("lstArchivos").split(",");
        }
    
               
        //Se envia email
        String from = InformacionConf.mailEnvio;
        //String to = dest;// cliVO.getEmail();

        // Se obtienen las propiedades del sistema y se establece el servidor SMTP
        Properties props = System.getProperties();
        props.put("mail.smtp.host",InformacionConf.servSalida);
        props.put("mail.transport.protocol", InformacionConf.protTransp);
        props.put("mail.smtp.auth", InformacionConf.autorSmtp);
        props.put("mail.smtp.port",InformacionConf.puertoSmtp);
        props.put("mail.smtp.starttls.enable",InformacionConf.smtpStartTls);
        props.put("mail.user", InformacionConf.mailUser);
        props.put("mail.password", InformacionConf.mailPass);

        // Se obtiene una sesi�n con las propiedades anteriormente definidas
        Session sesionMail = Session.getDefaultInstance(props,new Authenticator()
                    {
                       protected PasswordAuthentication getPasswordAuthentication()
                       {
                           return new PasswordAuthentication(InformacionConf.mailUser, InformacionConf.mailPass);
                       }
                    });

        try
        {
            
            sc = request.getServletContext();
            // Se crea un mensaje vacío
            Message mensaje = new MimeMessage(sesionMail);
            // Se rellenan los atributos y el contenido
            // Asunto
            mensaje.setSubject(asunto );
            // Emisor del mensaje
            mensaje.setFrom(new InternetAddress(from));

            // Receptor del mensaje
            if(dest != null)
            {
               for(int ind =0 ; ind < dest.length ; ind++)
               {
                    mensaje.addRecipient( Message.RecipientType.TO,new InternetAddress(dest[ind]));
               }
            }
                //mensaje.addRecipient( Message.RecipientType.BCC,new InternetAddress("aguilar@hostalaguilar.com"));

            // Cuerpo del mensaje
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(cuerpo);

            //Se adjuntan los ficheros
            MimeBodyPart mbp2 = null;
            
            // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
            
             // attach the file to the message //bucle con todos lo ficheros
            if(listaArchivos != null)
            {
                for(int ind = 0; ind < listaArchivos.length ; ind ++)
                {
                    FileDataSource fds = new FileDataSource(sc.getRealPath("/" + "docAdj" + "/" + listaArchivos[ind]));
                    mbp2 = new MimeBodyPart();
                    mbp2.setDataHandler(new DataHandler(fds));
                    mbp2.setFileName(fds.getName());
                    mp.addBodyPart(mbp2);
                }
            }
            mensaje.setContent(mp);


            // Se envía el mensaje
            Transport.send(mensaje);
            
            
            //Limpiamos el directorio de archivos
             File directorio = new File(sc.getRealPath("/" + "docAdj"));
             File f;
             if (directorio.isDirectory()) 
             {
                String[] files = directorio.list();
                if (files.length > 0) 
                {
                    for (String archivo : files) 
                    {
                        f = new File(sc.getRealPath("/" + "docAdj" + "/" + archivo));

                        f.delete();
                        f.deleteOnExit();
                    }

                }
            }
            //fin limpieza directorio 
            
            response.sendRedirect("./mailing/mensaje.jsp");
        }
        catch (MessagingException e)
        {
            System.out.println(">---------------------------<");
            System.err.println(e.getMessage());
            response.sendRedirect("./mailing/mensajeErr.jsp");
        }
        catch (Exception ex)
        {
            response.sendRedirect("./mailing/mensajeErr.jsp");
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
        return "Mailing servlet";
    }// </editor-fold>
}
