/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.CursosVO;
import es.jahernandez.datos.EdicionesVO;
import es.jahernandez.datos.InformacionConf;
import es.jahernandez.gestion.AluEdiGestion;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdicionesGestion;
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
public class ImpCertificadosServlet extends HttpServlet {

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
     
        ServletContext sc     = null;
        HttpSession    sesion = request.getSession();
        
        AlumnosVO   aluVO   = null;
        EdicionesVO ediVO   = null;
        CursosVO    curVO   = null;
        AluEdiVO    alEvo   = null;
        boolean     hayPag  = false;

        String      codEdi  = "";
        
        String      nomProf = ""; 
        
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir certificados" );
               
        }
        
        if(request.getParameter("codEdi") != null)
        {
            codEdi = request.getParameter("codEdi");
        }
         
        if(request.getParameter("txtNomProf") != null)
        {
            nomProf = request.getParameter("txtNomProf");
        }
        
        
        ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
        curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());

        //Se carga la lista de alumnos
        Vector    listaAlu     = AluEdiGestion.devAluMatEdi(codEdi);
        
        String    certf        = "CERTIFICA";
        String    nomAlum      = "";
        String    datCur       = "";
        String    lugCur       = "";
        String    aprov        = "con total dedicación y aprovechamiento";
        String    litfirmas    = ("                  Director del Curso                                                                                                ").substring(0,75)   +                    "Director del Centro"; 
        String    nomFirmas    =  "                  ";
        
        Paragraph parCert      = null;
        Paragraph parNomAlum   = null;
        Paragraph parDatCur    = null;
        Paragraph parLugCur    = null;
        Paragraph parAprov     = null;
        Paragraph parLitfirmas = null;
        Paragraph parNomFirmas = null;    
        
        Image     logoImage   = null; 
        Image     imaFondo    = null;


        // step 1
        Document document = new Document();
        
        

        try
        {
            sc = getServletContext();
            
            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
            logoImage.scaleAbsolute(200, 51);
            logoImage.setAlignment(Image.ALIGN_CENTER);
            
            imaFondo = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + "diploback.png"));
            imaFondo.setAbsolutePosition(0, 0);
            imaFondo.scalePercent(83, 73);
            logoImage.setAlignment(Image.ALIGN_CENTER);
            
            
            
            
            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            
            // step 3
            document.setPageSize(PageSize.A4.rotate());
            document.setMargins(16, 16, 16, 16);
            document.open();
            for (int ind = 0; ind < listaAlu.size(); ind++)
            {
                aluVO = AlumnosGestion.devolverDatosAlumno( ((AluEdiVO) listaAlu.elementAt(ind)).getIdAlu());
                alEvo = AluEdiGestion.devDatAluEdi(aluVO.getIdAlu(), ediVO.getIdEdi());
                
                hayPag = true;

                // step 4

                //Mostramos datos del certificado
                //certf        = "CERTIFICA";
                nomAlum = "Que DON/DOÑA " + aluVO.getNombre() + " " + aluVO.getAp1Alu();
                datCur  = "ha realizado el curso de " + CursosGestion.devolverDatosCurso(ediVO.getIdCur()).getNomCur() + "\ncon una duración de " + (ediVO.getNumHor() + ediVO.getHorDis() + ediVO.getHorTelef()) + " horas";
                lugCur  = "celebrado en " + InformacionConf.pobEmp + " del " 
                                                   + new SimpleDateFormat("dd").format(ediVO.getFecIn()) + " de " 
                                                   + devuelveMes(new Integer(new SimpleDateFormat("MM").format(ediVO.getFecIn()))) + " " 
                                                   + new SimpleDateFormat("yyyy").format(ediVO.getFecIn()) + " a " 
                                                   + new SimpleDateFormat("dd").format(ediVO.getFecFi()) + " de " 
                                                   + devuelveMes(new Integer(new SimpleDateFormat("MM").format(ediVO.getFecFi()))) + " " 
                                                   + new SimpleDateFormat("yyyy").format(ediVO.getFecFi()); 
                        
                        
                //aprov        = "con total dedicación y aprovechamiento";
                //litfirmas    = "Director del Curso                                                                     Director del Centro"; 
                nomFirmas = "                  " +  (nomProf +"                                                                                                                 ").substring(0,50) +  
                            (InformacionConf.director + "                                                                ").substring(0,45) ;
        
                parCert      = new Paragraph(certf);
                parNomAlum   = new Paragraph(nomAlum);
                parDatCur    = new Paragraph(datCur);
                parLugCur    = new Paragraph(lugCur);
                parAprov     = new Paragraph(aprov);
                parLitfirmas = new Paragraph(litfirmas);
                parNomFirmas = new Paragraph(nomFirmas);

                parCert.font().setSize(30);
                parCert.font().setColor(Color.BLUE);
                parCert.setAlignment(Image.ALIGN_CENTER);
                
                parNomAlum.font().setSize(20);
                parNomAlum.setAlignment(Image.ALIGN_CENTER);
                parDatCur.font().setSize(20);    
                parDatCur.setAlignment(Image.ALIGN_CENTER);
                parLugCur.font().setSize(20);    
                parLugCur.setAlignment(Image.ALIGN_CENTER);
                parAprov.font().setSize(20);     
                parAprov.setAlignment(Image.ALIGN_CENTER);
                parLitfirmas.font().setSize(16); 
                parNomFirmas.font().setSize(16); 
                
                document.add(imaFondo);
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(logoImage);
                document.add(parCert);
                document.add(new Paragraph(" "));
                document.add(parNomAlum);
                document.add(new Paragraph(" "));
                document.add(parDatCur);
                document.add(new Paragraph(" "));
                document.add(parLugCur);
                document.add(new Paragraph(" "));
                document.add(parAprov);
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(parLitfirmas);
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));
                document.add(parNomFirmas);

                document.newPage();
            }
            
            if (!hayPag)
            {
                document.add(new Paragraph("No existen alumnos para generar listado"));
            }

        }
        catch (DocumentException ex)
        {
        	System.out.println("Error generando informe");
        }
        // step 5: Close document
        document.close();

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
        return "Imprimir certificado servlet";
    }// </editor-fold>
    
    private String devuelveMes(int mes)
    {
        switch (mes)
        {
            case 1:  return "Enero";
            case 2:  return "Febrero";
            case 3:  return "Marzo";
            case 4:  return "Abril";
            case 5:  return "Mayo";
            case 6:  return "Junio";
            case 7:  return "Julio";
            case 8:  return "Agosto";
            case 9:  return "Septiembre";
            case 10: return "Octubre";
            case 11: return "Noviembre";
            case 12: return "Diciembre";
            default: return null;
        }
    }
}
