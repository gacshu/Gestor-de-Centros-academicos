/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.CursosVO;
import es.jahernandez.datos.EdicionesVO;
import es.jahernandez.datos.FaltasVO;
import es.jahernandez.datos.InformacionConf;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.FaltasGestion;
import es.jahernandez.gestion.ListaCodPostGestion;
import es.jahernandez.gestion.ModulosGestion;
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
public class ImpFaltasEdiServlet extends HttpServlet
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

        HttpSession       sesion       = request.getSession();
        ServletContext    sc           = null;

        AlumnosVO         aluVO        = null;
        EdicionesVO       ediVO        = null;
        CursosVO          curVO        = null;

        FaltasVO          faltasVO     = null;

        String            codAluAux    = "";
        String            codEdi       = "";

        boolean           muestraCab   = false;

        ConUsuVO          conUsVO      = new ConUsuVO();

        Vector            vecFaltas    = new Vector();

        conUsVO =(ConUsuVO) sesion.getAttribute("usuario");

        Logger            log          = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsVO.getUsuario() + "               " ).substring(0,10) + "Imprimir Faltas edición" );
        }

        if(request.getParameter("codEdi") != null   )
        {
            codEdi =  request.getParameter("codEdi").trim();

            vecFaltas = FaltasGestion.devolverFaltasEdi(codEdi);
        }

        // step 1
        // need to write to memory first due to IE wanting
        // to know the length of the pdf beforehand
        Document document = new Document();

        //Instrucciones para meter los datos de concepto en una tabla

        PdfPTable tablaConcepto  = new PdfPTable(1);
        PdfPCell  cellConcepto   = null;
        Paragraph parTitConcep   = null;

        PdfPTable tablaDatAlu    = new PdfPTable(1);
        PdfPCell  cellDatosAlu   = null;
        Paragraph parTitDatAlu   = new Paragraph("ALUMNO");
        Paragraph parNomAlu      = null;
        Paragraph parDniAlu      = null;
        Paragraph parDirecAlu    = null;
        Paragraph parCPPobAlu    = null;
        Paragraph parProvAlu     = null;

        PdfPTable tablaDetFalta  = new PdfPTable(3);
        PdfPCell  cellTit        = null;
        PdfPCell  cellLinea      = null;
        Paragraph parTitDet      = null;
        Paragraph parMod         = null;
        Paragraph parFecha       = null;
        Paragraph parJustif      = null;

        Paragraph parIma         = new Paragraph();
        Image     logoImage      = null; // iTextSharp.text.Image.GetInstance(System.Web.HttpContext.Current.Server.MapPath("~/imagenes/logoEnTeST.jpg"));


        try
        {
            sc = getServletContext();

            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            // step 3
            document.setMargins(32, 32, 16, 0);
            document.open();

            ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
            curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());

            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
            logoImage.scaleAbsolute(150, 38);

            for(int ind=0; ind < vecFaltas.size() ; ind ++)
            {
                faltasVO = (FaltasVO) vecFaltas.elementAt(ind);

                //Formateamos el ancho de las tablas
                tablaConcepto  = new PdfPTable(1);
                tablaDatAlu    = new PdfPTable(1);
                tablaDetFalta  = new PdfPTable(3);

                parIma = new Paragraph();
                parIma.setAlignment(Element.ALIGN_LEFT);
                parIma.add(logoImage);

                //par14 = new Paragraph(InformacionConf.nombEmp + " " + InformacionConf.dirEmp + " CIF: " + InformacionConf.CIFEmp, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                if(! faltasVO.getIdAlu().equals(codAluAux) )
                {
                    codAluAux  = faltasVO.getIdAlu();
                    aluVO      = AlumnosGestion.devolverDatosAlumno(faltasVO.getIdAlu());
                    muestraCab = true;

                    if(ind > 0)
                    {
                        document.newPage();
                    }

                    parIma = new Paragraph();
                    parIma.setAlignment(Element.ALIGN_LEFT);
                    parIma.add(logoImage);

                    cellConcepto = new PdfPCell();
                    parTitConcep = new Paragraph("Faltas de la edición " + ediVO.getDescripcion() + " del curso " + curVO.getNomCur() );
                    parTitConcep.setAlignment(Element.ALIGN_LEFT);
                    cellConcepto.addElement(parTitConcep);
                    tablaConcepto.addCell(cellConcepto);

                    cellDatosAlu = new PdfPCell();
                    parNomAlu    = new Paragraph(aluVO.getNombre() + " " + aluVO.getAp1Alu() + " ");
                    parDniAlu    = new Paragraph(aluVO.getNumDocAlu());
                    parDirecAlu  = new Paragraph(aluVO.getDirAlu() + " ");
                    parCPPobAlu  = new Paragraph(aluVO.getCodPosAlu()+ " " + aluVO.getLocalAlu() + " ");
                    parProvAlu   = new Paragraph(ListaCodPostGestion.devuelveNombreProv(aluVO.getCodProvAlu())+ " " );
                    cellDatosAlu.addElement(parTitDatAlu);
                    cellDatosAlu.addElement(parNomAlu);
                    cellDatosAlu.addElement(parDniAlu);
                    cellDatosAlu.addElement(parDirecAlu);
                    cellDatosAlu.addElement(parCPPobAlu);
                    cellDatosAlu.addElement(parProvAlu);
                    tablaDatAlu.addCell(cellDatosAlu);

                    //Detalle calificaciones
                    //Añadimoe el detalle de las clases
                    cellTit = new PdfPCell();
                    parTitDet = new Paragraph("ASIGNATURA");
                    parTitDet.setAlignment(Element.ALIGN_CENTER);
                    parTitDet.font().setSize(10);
                    cellTit.addElement(parTitDet);
                    tablaDetFalta.addCell(cellTit);
                    cellTit = new PdfPCell();
                    parTitDet = new Paragraph("FECHA");
                    parTitDet.setAlignment(Element.ALIGN_CENTER);
                    parTitDet.font().setSize(10);
                    cellTit.addElement(parTitDet);
                    tablaDetFalta.addCell(cellTit);
                    cellTit = new PdfPCell();
                    parTitDet = new Paragraph("JUSTIFICADA");
                    parTitDet.setAlignment(Element.ALIGN_CENTER);
                    parTitDet.font().setSize(10);
                    cellTit.addElement(parTitDet );
                    tablaDetFalta.addCell(cellTit);
                }

                faltasVO = (FaltasVO) vecFaltas.elementAt(ind);

                cellLinea = new PdfPCell();
                parMod = new Paragraph(ModulosGestion.devolverDatosModulo(faltasVO.getIdMod()).getNombre());
                parMod.setAlignment(Element.ALIGN_LEFT);
                parMod.font().setSize(10);
                cellLinea.addElement(parMod);
                tablaDetFalta.addCell(cellLinea);
                cellLinea = new PdfPCell();
                parFecha = new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(faltasVO.getFecha()));
                parFecha.setAlignment(Element.ALIGN_CENTER);
                parFecha.font().setSize(10);
                cellLinea.addElement(parFecha);
                tablaDetFalta.addCell(cellLinea);
                cellLinea = new PdfPCell();
                if(faltasVO.isJustificada())
                {
                    parJustif = new Paragraph(" X ");
                }
                else
                {
                    parJustif = new Paragraph(" ");
                }
                parJustif.setAlignment(Element.ALIGN_CENTER);
                parJustif.font().setSize(10);
                cellLinea.addElement(parJustif);
                tablaDetFalta.addCell(cellLinea);

                //par14.font().setSize(8);
                parIma = new Paragraph();
                parIma.setAlignment(Element.ALIGN_LEFT);
                parIma.add(logoImage);

                //Formateamos el ancho de las tablas
                tablaConcepto.setWidthPercentage(100);
                tablaDatAlu.setWidthPercentage(100);
                tablaDetFalta.setWidthPercentage(100);

                // step 4

                if(muestraCab)
                {
                    document.add(parIma);
                    document.add(new Paragraph(" "));
                    document.add(tablaConcepto);
                    document.add(new Paragraph(" "));
                    document.add(tablaDatAlu);
                    document.add(new Paragraph(" "));
                    muestraCab = false;
                }

                document.add(tablaDetFalta);
            }

            if (vecFaltas.size() <=0)
            {
                document.add(new Paragraph("No hay faltas que generar"));
            }

        }
        catch (DocumentException ex)
        {
              ex.getCause();
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
        return "Imprimir faltas edicion servlet";
    }// </editor-fold>

}
