/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.impresos;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import org.apache.log4j.Logger;

//Paquetes de manejo de pdf
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.EmpresasVO;
import es.jahernandez.datos.InformacionConf;
import es.jahernandez.gestion.EmpresasGestion;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class ImpLisAluServlet extends HttpServlet
{


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        HttpSession      sesion = request.getSession();
        ServletContext   sc     = null;
        Calendar         cal    = Calendar.getInstance();
        SimpleDateFormat forFec = new SimpleDateFormat("dd/MM/yyyy");
        String           txtFec = forFec.format(cal.getTime());


        Vector         alumnosBusqueda = new Vector();
        AlumnosVO      aluVO           = null;
        EmpresasVO     empVO           = null;

        String         strNombre       = "";
        String         strApe1         = "";
        String         strMov          = "";
        String         strFij          = "";
        String         strPob          = "";
        String         strMail         = "";
        String         strEmp          = "";
        String         ND              = "";

        String         muestraCond     = "";


        Logger      log      = null;
        ConUsuVO    conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir lista alumnos" );

        }

        if(sesion.getAttribute("muesCond") != null )
        {
            muestraCond = (String) sesion.getAttribute("muesCond");
        }

        int       cuentaLineas = 0;  //Cuenta lineas impresas para saber cuando hacer el salto de página

        PdfPTable tablaDatos   = new PdfPTable(1);
        PdfPCell  celda        = new PdfPCell();
        Font      fuenteDoc    = null;
        Font      fuenteCab    = null;
        alumnosBusqueda = (Vector) sesion.getAttribute("busAlu");

        // step 1
        Document document = new Document(PageSize.A4.rotate(),16,16,16,16);
        //iTextSharp.text.Image logoImage = iTextSharp.text.Image.GetInstance(System.web.HttpContext.Current.Server.MapPath());

        Image logoImage = null;

        try
        {
            sc = getServletContext();
            fuenteDoc = new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            fuenteCab = new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED));

            //logoImage = Image.getInstance("~/imagenes/logoEnS.jpg");

            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
            logoImage.scaleAbsolute(150, 38);

            tablaDatos.setWidthPercentage(100);
            tablaDatos.setSpacingBefore(10);

            celda.setMinimumHeight(20);
            fuenteDoc.setSize(8);

            fuenteCab.setColor(Color.WHITE);
            fuenteCab.setSize(8);

            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            // step 3
            document.open();

            // step 4

            //Mostramos cabecera
            logoImage.setAlignment(Element.ALIGN_LEFT);


            document.add(new Paragraph(""));
            document.add(logoImage);
            document.add(new Paragraph("FECHA " + txtFec + " LISTADO ALUMNOS " + muestraCond ));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));


            celda.addElement(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO                 MAIL                 EMPRESA                     ND", fuenteCab));

            celda.setBackgroundColor(Color.BLUE);


            tablaDatos.addCell(celda);

            for (int ind = 0; ind < alumnosBusqueda.size() ; ind++)
            {
                aluVO     = (AlumnosVO) alumnosBusqueda.elementAt(ind);

                strNombre = aluVO.getNombre().trim();
                strApe1   = aluVO.getAp1Alu().trim();
                strPob    = aluVO.getLocalAlu().trim();
                strMov    = aluVO.getMovAlu().trim();
                strFij    = aluVO.getFijAlu().trim();
                empVO     = EmpresasGestion.devolverDatEmp(aluVO.getEmpAlu());
                strMail   = aluVO.getEmail().trim();
                if (empVO != null)
                {
                    strEmp = empVO.getNombreEmpresa();
                }
                else
                {
                    strEmp = "                                           ";
                }

                if (aluVO.isAlND())
                {
                    ND = "X";
                }
                else
                {
                    ND = " ";
                }


                if (cuentaLineas > 21)
                {
                    cuentaLineas = 0;

                    //Hacemos salto de página
                    document.add(tablaDatos);
                    document.newPage();

                    //Reiniciamos la tabla
                    tablaDatos = new PdfPTable(1);
                    tablaDatos.setWidthPercentage(100);
                    tablaDatos.setSpacingBefore(10);

                    //Mostramos cabecera
                    document.add(logoImage);
                    document.add(new Paragraph("FECHA " + txtFec + " LISTADO ALUMNOS " + muestraCond));

                    document.add(new Paragraph(""));
                    document.add(new Paragraph(""));
                    celda = new PdfPCell();
                    celda.setMinimumHeight(20);

                    celda.setBackgroundColor(Color.BLUE);

                    celda.addElement(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO                 MAIL                 EMPRESA                     ND", fuenteCab));
                    //document.Add(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO     " , new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));
                    //document.Add(new Paragraph("------------------------------------------------------------------------------------------------------", new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));
                    tablaDatos.addCell(celda);
                }



                strNombre = strNombre + "                                  ";

                strApe1   = strApe1   + "                                  ";

                strPob    = strPob    + "                                  ";

                strMov    = strMov    + "                                  ";

                strFij    = strFij    + "                                  ";

                strEmp    = strEmp    + "                                  ";

                strMail   = strMail   + "                                      ";

                strNombre = strNombre.substring(0, 15);
                strApe1   = strApe1.substring(0, 25);
                strPob    = strPob.substring(0, 30);
                strMov    = strMov.substring(0, 9);
                strFij    = strFij.substring(0,9);
                strEmp    = strEmp.substring(0, 25);
                strMail   = strMail.substring(0, 35);

                celda = new PdfPCell();
                celda.setMinimumHeight(20);

                if(ind%2 != 0)
                {
                    celda.setBackgroundColor(Color.LIGHT_GRAY);
                }
                celda.addElement(new Paragraph("  " + strNombre + "    " + strApe1 + " " + strPob+ " " + strMov + "   " + strFij + "  " + strMail + "  "  + strEmp + " " + ND,fuenteDoc));
                //document.Add(new Paragraph("  " + strNombre + "    " + strApe1 + " " + strPob+ " " + strMov + "   " + strFij,new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

                tablaDatos.addCell(celda);

                cuentaLineas++;
            }
            //if (alumnosBusqueda.size() > 0 && alumnosBusqueda.size() <= 21)
            //{
            //    document.add(tablaDatos);
            //}
            document.add(tablaDatos);
        }
        catch (Exception ex)
        {
            ex.getCause();
        }
        // step 5: Close document
        document.close();

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

}
