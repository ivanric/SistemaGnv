package app.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.manager.ManejadorServicios;
import app.manager.ManejadorSolicitudes;
import app.models.Solicitud;
import app.utilidades.GeneradorReportes;
import app.utilidades.OpcionesAdicionales;
//import app.utilidades.JasperRerportsSimpleConfig;
import app.utilidades.SimpleReportExporter;
import app.utilidades.SimpleReportFiller;
import app.utilidades.URIS;

@RequestMapping("/RestResports/")
@RestController
public class RestReportes {
	
	@Autowired
	DataSource dataSource;
	@Autowired      
	ManejadorSolicitudes manejadorSolicitudes;

	public 	OpcionesAdicionales oa=new OpcionesAdicionales();
	
	@RequestMapping(value="allReport")
	public ResponseEntity<List<?>> listarBneneficiarios(HttpServletRequest req,HttpServletResponse res){	
		List<Map<String, Object>> reportes=this.manejadorSolicitudes.ListarReportes();
		
		System.out.println("listaSolt: "+reportes.toString());
		return new ResponseEntity<List<?>>(reportes,HttpStatus.OK);
	}
	@RequestMapping(value="listar")
	public void  reporte (HttpServletResponse res) {
		SimpleReportFiller simpleReportFiller=new SimpleReportFiller();

		URIS uris=new URIS();
		String url=uris.jasperReport+"getListBeneficiario.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";   
//		try {
//			simpleReportFiller.setReporte(getClass().getResource(url));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.getLocalizedMessage();
//		}
//	    
//	    
	    Map<String, Object> parameters = new HashMap<>();
	    
		
	    parameters.put("nit_param",oa.getNit());
	    parameters.put("fInicial_param","20181015");
	    parameters.put("fFinal_param","20181022");
	    parameters.put("escudo_param",this.getClass().getResourceAsStream(escudo));
//	    simpleReportFiller.setParameters(parameters);
//	    
//	    simpleReportFiller.setDataSource(dataSource);
//	     
//	    simpleReportFiller.fillReport();
//	    
//	    SimpleReportExporter simpleExporter = new SimpleReportExporter();
//        simpleExporter.setJasperPrint(simpleReportFiller.getJasperPrint());
//        
//        simpleExporter.exportToXlsx("employeeReport.xlsx", "Employee Data");
//        simpleExporter.exportToPdf("employeeReport.pdf", "baeldung");
//        
		GeneradorReportes g=new GeneradorReportes();
		try{
			
//			g.generarReporte(res, getClass().getResource(url), "xls", parameters, dataSource.getConnection(), "MinameReport", "inline");	
			g.generarReporte(res, getClass().getResource(url), "pdf", parameters, dataSource.getConnection(), "MinameReport", "inline");	

		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="solicitudF")
	public void  reporteExcel (HttpServletRequest req,HttpServletResponse res) {
		SimpleReportFiller simpleReportFiller=new SimpleReportFiller();

		URIS uris=new URIS();
		String url=uris.jasperReport+"getListBeneficiario.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");
		System.out.println("tipo: "+tipo);	
	    
//	    parameters.put("nit_param",oa.getNit());
	    parameters.put("fInicial_param","15/10/2018");
	    parameters.put("fFinal_param","22/10/2018");
	    parameters.put("escudo_param",this.getClass().getResourceAsStream(escudo));
//        
		GeneradorReportes g=new GeneradorReportes();
		try{
			if(tipo.equals("pdf")) {
				System.out.println("entro");
				g.generarReporte(res, getClass().getResource(url), "pdf", parameters, dataSource.getConnection(), "MinameReport", "inline");	
			}else {
				g.generarReporte(res, getClass().getResource(url), "xls", parameters, dataSource.getConnection(), "MinameReport", "inline");
			}
					

		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
