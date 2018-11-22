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

import app.manager.ManejadorBeneficiarios;
import app.manager.ManejadorInstalacionKit;
import app.manager.ManejadorServicios;
import app.manager.ManejadorSolicitudes;
import app.models.CombustibleVehiculo;
import app.models.DesmontajeKit;
import app.models.Documento;
import app.models.InstalacionCilindro;
import app.models.OrdenServicio;
import app.models.Persona;
import app.models.RegistroKit;
import app.models.Solicitud;
import app.models.Telefono;
import app.models.TransferenciaBeneficiario;
import app.models.TrasladoKitVehiculo;
import app.models.Vehiculo;

@RequestMapping("/RestTKitsVehiculos/")
@RestController
public class RestTKitVehiculo {
	@Autowired
	ManejadorInstalacionKit manejadorInstalacionKit;
	@Autowired
	ManejadorServicios manejadorServicios;
	@Autowired 
	ManejadorSolicitudes manejadorSolicitudes;
	@Autowired
	ManejadorBeneficiarios manejadorBeneficiarios;
	
	@RequestMapping(value="listar")
	public ResponseEntity<List<TrasladoKitVehiculo>> listarTBneneficiarios(HttpServletRequest req,HttpServletResponse res){	
		List<TrasladoKitVehiculo> Lista=this.manejadorInstalacionKit.ListarTrasladoKitVehiculo(req);
		System.out.println("Lista: "+Lista);
		for (int i = 0; i < Lista.size(); i++){
			Lista.get(i).getDesmontajeKit().getRegistroKit().setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(Lista.get(i).getDesmontajeKit().getRegistroKit().getIdordserv()));
			Lista.get(i).getDesmontajeKit().getRegistroKit().getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(Lista.get(i).getDesmontajeKit().getRegistroKit().getOrdenServicio().getIdordserv()));			
			Lista.get(i).setSolicitudNueva(this.manejadorSolicitudes.getSoltByTraslVeh(Lista.get(i).getIdtraslv()));
		}
		System.out.println("lista: "+Lista.toString());
		return new ResponseEntity<List<TrasladoKitVehiculo>>(Lista,HttpStatus.OK);
	}
	@RequestMapping(value="FiltroDesmontajeKit")
	public ResponseEntity<List<DesmontajeKit>> FiltroSolicitud(HttpServletRequest req,HttpServletResponse res){
		String texto=req.getParameter("texto");
		System.out.println("texto: "+texto);
		 
		List<DesmontajeKit> Lista=null;
		try {
			System.out.println("entro try");
			Lista=this.manejadorInstalacionKit.FiltroDesmontajeKit(req.getParameter("texto"));
			System.out.println("Lista: "+Lista.toString());
			for (int i = 0; i < Lista.size(); i++) {
//				Lista.get(i).setRegistroKit(this.manejadorInstalacionKit.getRegistroKitOP(ListaAR.get(i).getIdordserv()));
				Lista.get(i).getRegistroKit().setCilindros(this.manejadorInstalacionKit.ListaCilindro(Lista.get(i).getIdregistroKit()));
				Lista.get(i).getRegistroKit().setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(Lista.get(i).getRegistroKit().getIdordserv()));
				Lista.get(i).getRegistroKit().getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(Lista.get(i).getRegistroKit().getIdordserv()));		
			}
			System.out.println("TAM: "+Lista.size());
			System.out.println("Lista: "+Lista);
		} catch (Exception e) {
			System.out.println("entro catch");
			Lista=null;
		}
		return new ResponseEntity<List<DesmontajeKit>>(Lista,HttpStatus.OK);
	}
	@Transactional
	@RequestMapping(value="adicionar")
	public Map<String, Object> adicionar(HttpServletRequest req,HttpServletResponse res,Solicitud s,Vehiculo v,@RequestParam int [] combustible){
		System.out.println("SolicitudSolicitud: "+s.toString());
		System.out.println("VehiculoSolicitud: "+v.toString());
		
		HttpSession sesion=req.getSession(true);
		Persona xuser=(Persona) sesion.getAttribute("xusuario");
		int idacc=this.manejadorSolicitudes.getAccion();
		System.out.println("idacc: "+idacc);
		
		System.out.println("tamanio: "+combustible.length);
		Map<String, Object> respuesta=new HashMap<String, Object>();
		
		for (int i : combustible) {
			System.out.println("cod_combustible: "+i);
		}
		
		System.out.println("idben: "+req.getParameter("idben"));
		try {
		
			Object[] RespSolicitud=this.manejadorSolicitudes.registrar(req,xuser,v,s,combustible,idacc);
			respuesta.put("estado", RespSolicitud[0]);
			respuesta.put("idsolt", Integer.parseInt(RespSolicitud[1].toString()));
			Object[] RespTKitVehiculo=this.manejadorInstalacionKit.registrarTKitVehiculo(req, xuser,Integer.parseInt(RespSolicitud[1].toString()));
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			respuesta.put("estado",false);
		}
//		respuesta.put("estado",false);
		return respuesta;
	}
	@RequestMapping(value="Ver")
	public ResponseEntity<List<?>> VerSolicitud(HttpServletRequest req,HttpServletResponse res){
		List<Object> lista=new ArrayList<>();

		String idtraslv=req.getParameter("idtraslv");
		System.out.println("idtraslv: "+idtraslv);

		TrasladoKitVehiculo tk=this.manejadorInstalacionKit.verTrasladoKitVehiculo(Integer.parseInt(idtraslv));
		tk.getDesmontajeKit().getRegistroKit().setCilindros(this.manejadorInstalacionKit.ListaCilindro(tk.getDesmontajeKit().getIdregistroKit()));
		tk.getDesmontajeKit().getRegistroKit().setOrdenServicio(this.manejadorServicios.getOrdenServicioIK(tk.getDesmontajeKit().getRegistroKit().getIdordserv()));
		tk.getDesmontajeKit().getRegistroKit().getOrdenServicio().setSolicitud(this.manejadorSolicitudes.getSoltByOrdServ(tk.getDesmontajeKit().getRegistroKit().getOrdenServicio().getIdordserv()));			
		tk.setSolicitudNueva(this.manejadorSolicitudes.getSoltByTraslVeh(tk.getIdtraslv()));
		
		
		List<Documento> listaDoc=this.manejadorBeneficiarios.listaDocumentos();
		List<CombustibleVehiculo> listaComb=this.manejadorSolicitudes.listaCombustible();
		
		lista.add(tk);
		lista.add(listaDoc);
		lista.add(listaComb);
		//lista.add(this.manejadorServicios.ListaCilindros());
		return new ResponseEntity<List<?>>(lista,HttpStatus.OK);
	}
}
