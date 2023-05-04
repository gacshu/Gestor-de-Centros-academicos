/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.CursosVO;
import es.jahernandez.datos.EdicionesVO;
import es.jahernandez.gestion.AluEdiGestion;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.CentrosGestion;
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
public class ImpContMatServlet extends HttpServlet {

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
        ServletContext sc     = null;
        HttpSession    sesion = request.getSession();
        
        AlumnosVO   aluVO  = null;
        EdicionesVO ediVO  = null;
        CursosVO    curVO  = null;
        AluEdiVO    alEvo  = null;
        boolean     hayPag = false;

        String      codEdi = "";
        String      matEnt = "";
        String      centro = "";
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir control materiales" );
               
        }
        
        
        if(request.getParameter("codEdi") != null)
        {
            codEdi = request.getParameter("codEdi");
        }
        
        if(request.getParameter("txtMatEnt") != null)
        {
            matEnt = request.getParameter("txtMatEnt");
            matEnt = matEnt.replace(',', '\n');
        }
        
        if(request.getParameter("txtCentro") != null)
        {
            centro =  CentrosGestion.nomCentro(request.getParameter("txtCentro"));
        }
       
        
        ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
        curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());

        //Se carga la lista de alumnos
        Vector listaAlu = AluEdiGestion.devAluMatEdi(codEdi);

        //Instrucciones para meter los datos de concepto en una tabla
        float[] widthsTablaAlu = {50f,50f};

        PdfPTable tablaLitCon = new PdfPTable(1);
        PdfPTable tablaLitMat = new PdfPTable(1);
        PdfPTable tablaDatAlu = new PdfPTable(widthsTablaAlu);


        tablaLitCon.setWidthPercentage(100);
        tablaLitMat.setWidthPercentage(100);
        tablaDatAlu.setWidthPercentage(100); 

        tablaLitCon.setSpacingAfter(10f);
        tablaLitCon.setSpacingAfter(10f);

        String strCentro = centro + "                                                          "; //F-11 Rev.: 1";
        String strF11    = " F-11 Rev.: 1";
        String strConMat = "CONTROL DE MATERIALES";
        String strDatCur = "CURSO:   " + curVO.getNomCur();
        String strFecCur = "FECHA INICIO: "  + new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecIn()) + "                                FECHA FIN:" + new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecFi());
        String strMatEnt = "MATERIAL ENTREGADO";
        String strNomAlu = "NOMBRE ALUMNO";
        String strFirAlu = "FIRMA ALUMNO";
        String strFecEnt = "Fecha de Entrega: " + new SimpleDateFormat("dd/MM/yyyy").format( new Date(System.currentTimeMillis())) + "                                                                                                                     Página __ de __";

        String strDatAlu = "";

        Chunk chunkF11 = new Chunk(strF11);
        chunkF11.font().setSize(8);

        Phrase fraCentro = new Phrase(strCentro);
        fraCentro.add(chunkF11);
        Phrase fraConMat = new Phrase(strConMat);
        Phrase fraDatCur = new Phrase(strDatCur);
        Phrase fraFecCur = new Phrase(strFecCur);
        Phrase fraMatEnt = new Phrase(strMatEnt);
        Phrase fraMatVac = new Phrase(matEnt);
        Phrase fraNomAlu = new Phrase(strNomAlu);
        Phrase fraFirAlu = new Phrase(strFirAlu);
        Phrase fraFecEnt = new Phrase(strFecEnt);
        Phrase fraDatAlu = null;

        //Se formatean los literales
        fraCentro.font().setSize(14);
        fraConMat.font().setSize(14);
        fraConMat.font().setStyle(Font.BOLD);
        fraDatCur.font().setSize(12);
        fraDatCur.font().setStyle(Font.BOLD);
        fraFecCur.font().setSize(12);
        fraFecCur.font().setStyle(Font.BOLD);
        fraMatEnt.font().setSize(10);
        fraMatEnt.font().setStyle(Font.BOLD);
        fraMatVac.font().setSize(10);
        fraNomAlu.font().setSize(10);
        fraNomAlu.font().setStyle(Font.BOLD);
        fraFirAlu.font().setSize(10);
        fraFirAlu.font().setStyle(Font.BOLD);
        fraFecEnt.font().setSize(10);
       
        PdfPCell cellConMat = new PdfPCell(fraConMat);
        PdfPCell cellMatLit = new PdfPCell(fraMatEnt);
        PdfPCell cellMatVac = new PdfPCell(fraMatVac);
        PdfPCell cellNomCab = new PdfPCell(fraNomAlu);
        PdfPCell cellFirCab = new PdfPCell(fraFirAlu);
        PdfPCell cellAluVac = new PdfPCell();
        PdfPCell cellFirVac = new PdfPCell();


        cellConMat.setHorizontalAlignment(Image.ALIGN_CENTER);
        cellMatLit.setHorizontalAlignment(Image.ALIGN_CENTER);
        cellNomCab.setHorizontalAlignment(Image.ALIGN_CENTER);
        cellFirCab.setHorizontalAlignment(Image.ALIGN_CENTER);

        cellConMat.setHorizontalAlignment(Image.ALIGN_CENTER);
        cellConMat.setGrayFill(0.90f);
        cellMatLit.setHorizontalAlignment(Image.ALIGN_CENTER);
        cellMatLit.setGrayFill(0.90f);
        cellMatVac.setMinimumHeight(100f);
        cellNomCab.setHorizontalAlignment (Image.ALIGN_CENTER);
        cellNomCab.setGrayFill(0.90f);
        cellFirCab.setHorizontalAlignment (Image.ALIGN_CENTER);
        cellFirCab.setGrayFill(0.90f);
        cellAluVac.setMinimumHeight(25f);
        cellFirVac.setMinimumHeight(25f);

        tablaLitCon.addCell(cellConMat);
        tablaLitMat.addCell(cellMatLit);
        tablaLitMat.addCell(cellMatVac);
        tablaDatAlu.addCell(cellNomCab);
        tablaDatAlu.addCell(cellFirCab);


        tablaLitCon.setSpacingBefore(15f);

        // step 1
        // need to write to memory first due to IE wanting
        // to know the length of the pdf beforehand
        Document document   = new Document();     
        
        //Image     logoImage = null; //iTextSharp.text.Image.GetInstance(System.Web.HttpContext.Current.Server.MapPath("~/imagenes/logoEnS.jpg"));

        try
        {
            sc = getServletContext();
            
            //logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + "logoEnS.jpg"));
            
            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            
            // step 3
            document.setPageSize(PageSize.A4);
            document.setMargins(16, 16, 16, 16);
            document.open();

            for (int ind = 0; ind < 20; ind++)
            { 
                // step 4

                //Mostramos datos alumno

                if(ind < listaAlu.size())
                {
                    aluVO = AlumnosGestion.devolverDatosAlumno(((AluEdiVO) listaAlu.elementAt(ind)).getIdAlu());
                    alEvo = AluEdiGestion.devDatAluEdi(aluVO.getIdAlu(), ediVO.getIdEdi());
                    hayPag = true;
                    strDatAlu = aluVO.getNombre() + " " + aluVO.getAp1Alu();
                }
                else
                {
                    strDatAlu = "";
                }
                
                fraDatAlu = new Phrase(strDatAlu);
                fraDatAlu.font().setSize(12);

                cellAluVac = new PdfPCell(fraDatAlu);
                cellAluVac.setVerticalAlignment(Image.ALIGN_MIDDLE);

                tablaDatAlu.addCell(cellAluVac);
                tablaDatAlu.addCell(cellFirVac);
   
            }

            document.add(new Paragraph(fraCentro));
            document.add(tablaLitCon);
            document.add(new Paragraph(fraDatCur));
            document.add(new Paragraph(fraFecCur));
            document.add(new Paragraph("   "));
            document.add(tablaLitMat);
            document.add(tablaDatAlu);
            document.add(new Paragraph("   "));
            document.add(new Paragraph(fraFecEnt));

            if (!hayPag)
            {
              document.add(new Paragraph("No existen alumnos para generar listado"));
            }


        }
        catch (DocumentException ex)
        {
        	System.out.println("Error generando el informe");
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
        return "Imprimir Control Materiales Servlet";
    }// </editor-fold>
}
