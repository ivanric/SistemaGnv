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
	@Autowired
	ManejadorServicios manejadorServicios;

	
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
	    
		
//	    parameters.put("nit_param",oa.getNit());
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

	@RequestMapping(value="LRSolicitudF")
	public void  LRSolicitudF (HttpServletRequest req,HttpServletResponse res) {
		SimpleReportFiller simpleReportFiller=new SimpleReportFiller();
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getListBeneficiario.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");
	    String fecha_inicial=req.getParameter("fecha_inicial");
	    String fecha_final=req.getParameter("fecha_final");
		System.out.println("tipo: "+tipo+" fecha_inicial:"+fecha_inicial+" fecha_final:"+fecha_final);	
	    
		String fecha_inicialC=oa.convertFecha(fecha_inicial);
		String fecha_finalC=oa.convertFecha(fecha_final);
		System.out.println("fecha_inicialC:"+fecha_inicialC);
		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
//	    parameters.put("fInicial_param","15/10/2018");
	    parameters.put("fInicial_param",fecha_inicialC);
//	    parameters.put("fFinal_param","22/10/2018");
	    parameters.put("fFinal_param",fecha_finalC);
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
	@RequestMapping(value="LRSolicitudesAnuladas")
	public void  LRSolicitudesAnuladas (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListSolicitudesAnuladas.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");

		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
	    parameters.put("escudo_param",this.getClass().getResourceAsStream(escudo));

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
	@RequestMapping(value="LROServicioF")
	public void  LROServicioF (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListOrdenServicio.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");
	    String fecha_inicial=req.getParameter("fecha_inicial");
	    String fecha_final=req.getParameter("fecha_final");
		System.out.println("tipo: "+tipo+" fecha_inicial:"+fecha_inicial+" fecha_final:"+fecha_final);	
	    
		String fecha_inicialC=oa.convertFecha(fecha_inicial);
		String fecha_finalC=oa.convertFecha(fecha_final);
		System.out.println("fecha_inicialC:"+fecha_inicialC);
		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
//	    parameters.put("fInicial_param","15/10/2018");
	    parameters.put("fInicial_param",fecha_inicialC);
//	    parameters.put("fFinal_param","22/10/2018");
	    parameters.put("fFinal_param",fecha_finalC);
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
	@RequestMapping(value="LRVehiculos")
	public void  LRVehiculos (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListVehiculos.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");

		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
	    parameters.put("escudo_param",this.getClass().getResourceAsStream(escudo));

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
	@RequestMapping(value="LRBeneficiariosSinServicio")
	public void  LRBeneficiariosSinServicio (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListBeneficiariosSinSolt.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");

		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
	    parameters.put("escudo_param",this.getClass().getResourceAsStream(escudo));

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
	@RequestMapping(value="LRBeneficiariosIEnSolt")
	public void  getRListBeneficiariosIEnSolt (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListBeneficiariosIEnSolt.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");

		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
	    parameters.put("escudo_param",this.getClass().getResourceAsStream(escudo));

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
	@RequestMapping(value="LRBeneficiariosAEnSolt")
	public void  getRListBeneficiariosAEnSolt (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListBeneficiariosAEnSolt.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");

		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
	    parameters.put("escudo_param",this.getClass().getResourceAsStream(escudo));

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
	
	@RequestMapping(value="LRInstalacionKitF")
	public void  LRInstalacionKitF (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListInstalacionKit.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");
	    String fecha_inicial=req.getParameter("fecha_inicial");
	    String fecha_final=req.getParameter("fecha_final");
		System.out.println("tipo: "+tipo+" fecha_inicial:"+fecha_inicial+" fecha_final:"+fecha_final);	
	    
		String fecha_inicialC=oa.convertFecha(fecha_inicial);
		String fecha_finalC=oa.convertFecha(fecha_final);
		System.out.println("fecha_inicialC:"+fecha_inicialC);
		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
//	    parameters.put("fInicial_param","15/10/2018");
	    parameters.put("fInicial_param",fecha_inicialC);
//	    parameters.put("fFinal_param","22/10/2018");
	    parameters.put("fFinal_param",fecha_finalC);
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
	@RequestMapping(value="LRKitsPagadosF")
	public void  LRKitsPagadosF (HttpServletRequest req,HttpServletResponse res) {
		OpcionesAdicionales oa=new OpcionesAdicionales();
		URIS uris=new URIS();
		String url=uris.jasperReport+"getRListKitsPagados.jasper"; 
		String escudo=uris.imgJasperReport+"escudobolivia.png";  
	    Map<String, Object> parameters = new HashMap<>();
	    
	    String tipo=req.getParameter("tipo");
	    String fecha_inicial=req.getParameter("fecha_inicial");
	    String fecha_final=req.getParameter("fecha_final");
		System.out.println("tipo: "+tipo+" fecha_inicial:"+fecha_inicial+" fecha_final:"+fecha_final);	
	    
		String fecha_inicialC=oa.convertFecha(fecha_inicial);
		String fecha_finalC=oa.convertFecha(fecha_final);
		System.out.println("fecha_inicialC:"+fecha_inicialC);
		
		Map<String, Object> nitSQL=this.manejadorServicios.nitEmpresa(1); 
		String nit_patam=(String) nitSQL.get("nitInst");
		
	    parameters.put("nit_param",nit_patam);
//	    parameters.put("fInicial_param","15/10/2018");
	    parameters.put("fInicial_param",fecha_inicialC);
//	    parameters.put("fFinal_param","22/10/2018");
	    parameters.put("fFinal_param",fecha_finalC);
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
