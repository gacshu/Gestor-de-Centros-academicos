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
import com.lowagie.text.Element;
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
public class ImpSegAlumServlet extends HttpServlet {

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
        String      nomMes  = "";
        String      nomCent = "";

        Logger               log      = null;
        ConUsuVO             conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir seguimiento alumnos" );

        }


        if(request.getParameter("codEdi") != null)
        {
            codEdi = request.getParameter("codEdi");
        }

        if(request.getParameter("txtMes") != null)
        {
            nomMes = request.getParameter("txtMes").toUpperCase();
        }

        if(request.getParameter("txtCent") != null)
        {
            nomCent = CentrosGestion.nomCentro(request.getParameter("txtCent"));
        }


        ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
        curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());

        //Se carga la lista de alumnos
        Vector listaAlu = AluEdiGestion.devAluMatEdi(codEdi);

        //Instrucciones para meter los datos de concepto en una tabla
        float[] widths = {3f, 35f, 7f, 7f,};

        PdfPTable tablaDatAlu = null;
        PdfPTable tablaDatSeg = new PdfPTable(1);

        String  cl1   =  nomCent + "                                                                                                         F-25";
        String  clSeg = "SEGUIMIENTO DEL ALUMNO";
        String  cl2   = "";
        String  cl3   = "CURSO: " + curVO.getNomCur();
        String  cl4   = "FECHA INICIO:" + new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecIn()) + "              FECHA FIN: " + new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecFi());

        String  litFec = "Fecha Hora";
        String  litObs = "Tema/Observaciones";
        String  litFPr = "Firma Profesor";
        String  litFAl = "Firma Alumno";

        String  litPag = "Página ___ de ___";

        Phrase  fra1   = new Phrase(cl1);
        Phrase  fra2   = new Phrase(clSeg);
        Phrase  fra3   = new Phrase(cl3);
        Phrase  fra4   = new Phrase(cl4);

        Phrase  fraFec = new Phrase(litFec);
        Phrase  fraObs = new Phrase(litObs);
        Phrase  fraFPr = new Phrase(litFPr);
        Phrase  fraFAl = new Phrase(litFAl);
        Phrase  fraSeg = new Phrase(clSeg);

        Paragraph parFin = new Paragraph(litPag);

        parFin.font().setSize(12);
        parFin.setAlignment(Element.ALIGN_CENTER);

        fra1.font().setSize(16);
        fra2.font().setSize(14);
        fra3.font().setSize(14);
        fra4.font().setSize(14);

        fraSeg.font().setSize(14);

        fraFec.font().setSize(12);
        fraObs.font().setSize(12);
        fraFPr.font().setSize(12);
        fraFAl.font().setSize(12);

        PdfPCell   cellSeg = null;
        PdfPCell   cellHor = null;
        PdfPCell   cellTem = null;
        PdfPCell   cellFPr = null;
        PdfPCell   cellFAl = null;


        // step 1
        Document document = new Document();


        try
        {
            sc = getServletContext();


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

                //Mostramos datos alumno

                cl2 ="NOMBRE: " + aluVO.getNombre()  + " " + aluVO.getAp1Alu();
                fra2 = new Phrase(cl2);
                fra2.font().setSize(fra3.font().size());

                fraFAl = new Phrase(litFAl);
                fraFAl.font().setSize(12);

                //Se inicializa las tablas
                tablaDatAlu = new PdfPTable(widths);
                tablaDatAlu.setWidthPercentage(100);

                tablaDatSeg = new PdfPTable(1);
                tablaDatSeg.setWidthPercentage(100);

                fraSeg = new Phrase(clSeg);
                fraSeg.font().setSize(14);

                cellSeg = new PdfPCell(fraSeg);
                cellSeg.setBackgroundColor(Color.LIGHT_GRAY);
                cellSeg.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablaDatSeg.setSpacingAfter(5f);
                tablaDatSeg.setSpacingBefore(5f);
                tablaDatSeg.addCell(cellSeg);

                tablaDatAlu.setSpacingAfter(5f);
                tablaDatAlu.setSpacingBefore(5f);

                //Se genera la cabecera de la tabla
                cellHor = new PdfPCell(fraFec);
                cellHor.setMinimumHeight(20);
                cellHor.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellHor);

                cellTem = new PdfPCell(fraObs);
                cellTem.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellTem);

                cellFPr = new PdfPCell(fraFPr);
                cellFPr.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellFPr);

                cellFAl = new PdfPCell(fraFAl);
                cellFAl.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellFAl);

                cellFAl = new PdfPCell(new Phrase(""));
                cellFAl.setMinimumHeight(350);

                tablaDatAlu.addCell(cellFAl);
                tablaDatAlu.addCell(cellFAl);
                tablaDatAlu.addCell(cellFAl);
                tablaDatAlu.addCell(cellFAl);

                document.add(new Paragraph(fra1));
                document.add(tablaDatSeg);
                document.add(new Paragraph(fra2));
                document.add(new Paragraph(fra3));
                document.add(new Paragraph(fra4));
                document.add(tablaDatAlu);
                document.add(new Paragraph("   "));
                document.add(parFin);

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
        return "Imprimir seguimientos servlets";
    }// </editor-fold>
}
