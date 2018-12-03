package app.restController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.manager.ManejadorProgramaGnv;
import app.models.DocumentoBeneficiario;
import app.models.Persona;
import app.models.Solicitud;
import app.models.Vehiculo;

@RequestMapping("/RestProgramaGnv/")
@RestController
public class RestProgramaGnv {
	@Autowired
	ManejadorProgramaGnv manejadorGnv;
	
	@RequestMapping(value="datos-mod")
	public ResponseEntity<List<?>> datosMod(HttpServletRequest req){
		List<Object> lista=new ArrayList<Object>();
		Map<String,Object> institucion=this.manejadorGnv.getInstitucion(); 
		Map<String,Object> firmas=this.manejadorGnv.getFirmas(); 
		List<?> ListDepart=this.manejadorGnv.getDeparta(); 
		List<?> firmasPersonas=this.manejadorGnv.getPersonasFirmas(); 
		lista.add(institucion);
		lista.add(ListDepart);
		lista.add(firmas);
		lista.add(firmasPersonas);

		System.out.println("Persona a Modificar:"+lista);
		return new ResponseEntity<List<?>>(lista,HttpStatus.OK);		
	}
	@Transactional
	@RequestMapping(value="modificarInstitucion")
	public Map<String, Object> modificar(HttpServletRequest req,HttpServletResponse res){
		Map<String, Object> respuesta=new HashMap<String, Object>();

		
		try {
			Object[] resp=this.manejadorGnv.modificarInst(req);
			System.out.println(resp);
			respuesta.put("estado", resp[0]);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return respuesta;
	}
	@Transactional
	@RequestMapping(value="modificarFirmas")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res){
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		Map<String, Object> respuesta=new HashMap<String, Object>();
		
		try {
			Object[] RespSolicitud=this.manejadorGnv.registrarFirmas(req);
			respuesta.put("estado", RespSolicitud[0]);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}

		return respuesta;
	}
}
