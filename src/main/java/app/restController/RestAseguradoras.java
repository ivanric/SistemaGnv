package app.restController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.manager.ManejadorAseguradoras;
import app.models.Aseguradora;
import app.models.Persona;


@RequestMapping("/RestAseguradoras/")
@RestController
public class RestAseguradoras {
	@Autowired
	ManejadorAseguradoras manejadorAseguradoras;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<Aseguradora>> listarServicio(HttpServletRequest req,HttpServletResponse res){	
		List<Aseguradora> lista=this.manejadorAseguradoras.ListarAseguradoras(req);
		System.out.println("listaRoles: "+lista.toString());
		return new ResponseEntity<List<Aseguradora>>(lista,HttpStatus.OK);
	}
	
	@Transactional
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,Persona p,HttpServletResponse res){
//		HttpSession sesion=req.getSession(true);
//		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try{
			boolean consulta=this.manejadorAseguradoras.registrar(req,p);
			System.out.println("resp: "+consulta);
			respuesta.put("estado", consulta);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return respuesta;
	}
	
	@RequestMapping(value="datos-mod")
	public ResponseEntity<Aseguradora> datosMod(HttpServletRequest req){
		Aseguradora data=this.manejadorAseguradoras.datosModificar(req);
		
		System.out.println("Rol a Modificar:"+data);
		return new ResponseEntity<Aseguradora>(data,HttpStatus.OK);		
	}
	
	@Transactional
	@RequestMapping(value="modificar")
	public Map<String, Object> modificar(HttpServletRequest req,HttpServletResponse res,Persona p){
		Map<String, Object> respuesta=new HashMap<String, Object>();
//		HttpSession sesion=req.getSession(true);
//		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		String[] telefonos=req.getParameterValues("telefono[]");
		System.out.println("p: "+p.toString());
		try {
			boolean consulta=this.manejadorAseguradoras.modificar(req, p,telefonos);
			System.out.println(consulta);
			respuesta.put("estado", consulta);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return respuesta;
	}
	

	@RequestMapping(value="eliminar")
	public ResponseEntity<Map<String, Object>> elim(HttpServletRequest req){
		System.out.println("lego eliminar");
//		HttpSession sesion=req.getSession(true);
//		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		String idaseg=req.getParameter("idaseg");
		System.out.println("idaseg: "+idaseg);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try {
			boolean resp=this.manejadorAseguradoras.eliminar(Integer.parseInt(idaseg));
			respuesta.put("estado", resp);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.OK);	
	}
	@RequestMapping(value="habilitar")
	public ResponseEntity<Map<String, Object>> habil(HttpServletRequest req){
		System.out.println("lego eliminar");
//		HttpSession sesion=req.getSession(true);
//		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		String idaseg=req.getParameter("idaseg");
		System.out.println("idaseg: "+idaseg);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try {
			boolean resp=this.manejadorAseguradoras.habilitar(Integer.parseInt(idaseg));
			respuesta.put("estado", resp);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.OK);	
	}
}
