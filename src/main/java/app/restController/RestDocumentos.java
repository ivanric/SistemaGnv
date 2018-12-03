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
import org.springframework.web.bind.annotation.RestController;

import app.manager.ManejadorDocumentos;

import app.models.DocumentoBeneficiario;
import app.models.Persona;

 
@RequestMapping("/RestDocumentos/")
@RestController
public class RestDocumentos{ 

	@Autowired
	ManejadorDocumentos manejadorDocumentos;


	      
	@RequestMapping(value="listarDoc")
	public ResponseEntity<List<DocumentoBeneficiario>> listarDoc(HttpServletRequest req,HttpServletResponse res){	
		List<DocumentoBeneficiario> lista=this.manejadorDocumentos.ListarDocumentos(req);
		System.out.println("listaDoc: "+lista.toString());
		return new ResponseEntity<List<DocumentoBeneficiario>>(lista,HttpStatus.OK);
	}
//	@RequestMapping(value="listar")
//	public ResponseEntity<List<Map<String, Object>>> listarDoc(HttpServletRequest req,HttpServletResponse res){	
//		List<Map<String, Object>> lista=this.manejadorDocumentos.ListarD(req);
//		System.out.println("lista: "+lista.toString());
//		return new ResponseEntity<List<Map<String, Object>>>(lista,HttpStatus.OK);
//	}
	
	@Transactional
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res){
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try{
			boolean consulta=this.manejadorDocumentos.registrar(req,xuser);
			System.out.println("resp: "+consulta);
			respuesta.put("estado", consulta);
		}catch (Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return respuesta;
	}
	@RequestMapping(value="datos-mod")
	public ResponseEntity<DocumentoBeneficiario> datosMod(HttpServletRequest req){
		DocumentoBeneficiario data=this.manejadorDocumentos.datosModificar(req);
		
		System.out.println("Doc a Modificar:"+data);
		return new ResponseEntity<DocumentoBeneficiario>(data,HttpStatus.OK);		
	}
	@Transactional
	@RequestMapping(value="modificar")
	public Map<String, Object> modificar(HttpServletRequest req,HttpServletResponse res){
		Map<String, Object> respuesta=new HashMap<String, Object>();
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		try {
			boolean consulta=this.manejadorDocumentos.modificar(req,xuser);
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
		String iddocb=req.getParameter("iddocb");
		System.out.println("iddocb: "+iddocb);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try {
			boolean resp=this.manejadorDocumentos.eliminar(Integer.parseInt(iddocb));
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
		String iddocb=req.getParameter("iddocb");
		System.out.println("iddocb: "+iddocb);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		try {
			boolean resp=this.manejadorDocumentos.habilitar(Integer.parseInt(iddocb));
			respuesta.put("estado", resp);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
		return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.OK);	
	}


	
}
