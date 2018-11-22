
INSERT INTO persona] (idper,nombres,ap,genero,direccion,estado,am,email,ci,foto,ciCod) VALUES ('1', 'IVAN', 'MAMANI', 'f', 'TEJAR', '1', 'CORZO', 'ivan_27_33_27@hotmail.com', '10585402', 'modify_codper-1.png', 'TJ');

INSERT INTO usuario(login,password,idper,estado) VALUES('admin','1MtuSdJ/7Fc=','1','1');

--Pais
INSERT INTO pais(idpais,codigo,nombre,estado) VALUES('1',NULL,'BOLIVIA','1');

--Departamento
INSERT INTO departamento(iddep,codigo,nombre,estado,idpais,abreviacion) VALUES('1',NULL,'TARIJA','1','1',NULL);

--Institucion
INSERT INTO institucion(idinst,nombreCompañia,nombreInstitucion,direccion,telefono,iddep,nitInst,fax,firma1,firma2,firma3) VALUES('1','GOB. DPTAL DE TARIJA','GOB. DPTAL DE TARIJA','AV. DOMINGO PAZ NRO 832','6111416-6111392','1','178928029','(591) 6111416','LESLY MELANI SIGLE VILLARRUBIA','JOSE NAVARRO ANTELO','ING. MARCO  ANTONIO ORTIZ TAPIA')
-- INSERT INTO institucion(idinst,nombreCompañia,nombreInstitucion,direccion,telefono,iddep,nitInst,firma1,firma2,firma3,fax) VALUES('1','GOB. DPTAL DE TARIJA','GOB. DPTAL DE TARIJA','AV. DOMINGO PAZ NRO 832','6111416-6111392','1','178928029','LESLY MELANI SIGLE VILLARRUBIA','JOSE NAVARRO ANTELO','ING. MARCO  ANTONIO ORTIZ TAPIA','(591) 6111416');


-- Acciones
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(1,'APROBADOR INSPECCION','ainsp',1,'a',1) 
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(2,'APROBADOR TECNICO','atec',2,'a',1) 
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(3,'APROBADOR LEGAL','aleg',3,'a',1) 
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(4,'APROBADOR FINAL','afin',4,'a',1)
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(5,'SOLICITUD','',1,'s',1)
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(6,'ORDEN DE SERVICIO','',1,'os',1)
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(7,'KIT INSTALADO','',1,'ki',1)
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(8,'ACTA DE RECEPCION','',1,'ar',1)
INSERT INTO accion(idacc,nombre,codigo,jerarquia,tipo,estado) VALUES(9,'ORDEN DE PAGO','',1,'op',1)
INSERT INTO accion(idacc,nombre,codigo,jerarquia,estado,tipo) VALUES(10,'APROBACION DE OPERACIONES', 'ao', '1', '1', 'tb');
INSERT INTO accion(idacc,nombre,codigo,jerarquia,estado,tipo) VALUES(11,'ELABORACION CONTRATO', 'ec', '2', '1', 'tb');
INSERT INTO accion(idacc,nombre,codigo,jerarquia,estado,tipo) VALUES(12,'APROBACION FINAL', 'af', '3', '1', 'tb');


--Opciones
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(1,'ADICIONAR','adicionar',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(2,'MODIFICAR','modificar',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(3,'ELIMINAR','eliminar',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(4,'HABILITAR','habilitar',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(5,'VER','ver',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(6,'ANULAR','anular',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(7,'APROBADOR INSPECCION','ainsp',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(8,'APROBADOR TECNICO','atec',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(9,'APROBADOR LEGAL','aleg',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(10,'APROBADOR FINAL','afin',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(11,'MODIFICAR VEHICULO SOLICITUD','mod-veh-solt',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(12,'ASIGNAR ROLES','asigRol',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(13,'REALIZAR REPORTES','addReport',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(14,'REGISTRAR PERMISOS','',1)

INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(15,'APRBACION DE OPERACIONES','ao',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(16,'ELABORACION CONTRATO','ec',1)
INSERT INTO opcion(idopc,nombre,codigo,estado) VALUES(17,'APROBACION FINAL','af',1)

--Modulos
INSERT INTO modulo(idmod,nombre,estado,icono) VALUES('1','REGISTROS','1','fa fa-pencil');
INSERT INTO modulo(idmod,nombre,estado,icono) VALUES('2','PROCESOS','1','fa fa-sun-o');
INSERT INTO modulo(idmod,nombre,estado,icono) VALUES('3','CONSULTAS','1','fa fa-neuter');
INSERT INTO modulo(idmod,nombre,estado,icono) VALUES('4','REPORTES','1','fa fa-print');
INSERT INTO modulo(idmod,nombre,estado,icono) VALUES('5','SISTEMA','1','fa fa-server');

--Procesos
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('1','ROLES','1','../Roles/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('2','TALLERES','1','../Talleres/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('3','USUARIOS','1','../Usuarios/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('4','PERMISOS','1','../Permisos/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('5','SOLICITUDES','1','../Solicitudes/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('6','APROBACIONES','1','../Aprobaciones/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('7','ORDEN SERVICIO','1','../OrdenServicio/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('8','SERVICIOS','1','../Servicios/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('9','ORDEN PAGO TALLER','1','../OrdenPago/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('10','TRANSFERENCIA BENEFICIARIO','1','../TBeneficiario/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('11','BENEFICIARIOS','1','../Beneficiarios/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('12','CONVERSIONES','1','../InstalacionKit/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('13','ACTAS RECEPCIÓN','1','../ActasRecepcion/Gestion',NULL);
INSERT INTO proceso(idproc,nombre,estado,enlace,icono) VALUES('14','REPORTES','1','../Reportes/Gestion',NULL);


--Procopc
INSERT INTO procopc(idproc,idopc) VALUES('1','1');
INSERT INTO procopc(idproc,idopc) VALUES('1','2');
INSERT INTO procopc(idproc,idopc) VALUES('1','3');
INSERT INTO procopc(idproc,idopc) VALUES('1','4');

INSERT INTO procopc(idproc,idopc) VALUES('2','1');
INSERT INTO procopc(idproc,idopc) VALUES('2','2');
INSERT INTO procopc(idproc,idopc) VALUES('2','3');
INSERT INTO procopc(idproc,idopc) VALUES('2','4');

INSERT INTO procopc(idproc,idopc) VALUES('3','1');
INSERT INTO procopc(idproc,idopc) VALUES('3','2');
INSERT INTO procopc(idproc,idopc) VALUES('3','3');
INSERT INTO procopc(idproc,idopc) VALUES('3','4');
INSERT INTO procopc(idproc,idopc) VALUES('3','12');

INSERT INTO procopc(idproc,idopc) VALUES('4','14');
INSERT INTO procopc(idproc,idopc) VALUES('5','1');
INSERT INTO procopc(idproc,idopc) VALUES('5','5');
INSERT INTO procopc(idproc,idopc) VALUES('5','6');

INSERT INTO procopc(idproc,idopc) VALUES('6','7');
INSERT INTO procopc(idproc,idopc) VALUES('6','8');
INSERT INTO procopc(idproc,idopc) VALUES('6','9');
INSERT INTO procopc(idproc,idopc) VALUES('6','10');

INSERT INTO procopc(idproc,idopc) VALUES('7','1');
INSERT INTO procopc(idproc,idopc) VALUES('7','5');

INSERT INTO procopc(idproc,idopc) VALUES('8','1');
INSERT INTO procopc(idproc,idopc) VALUES('8','3');
INSERT INTO procopc(idproc,idopc) VALUES('8','4');

INSERT INTO procopc(idproc,idopc) VALUES('9','1');
INSERT INTO procopc(idproc,idopc) VALUES('9','5');

INSERT INTO procopc(idproc,idopc) VALUES('10','1');
INSERT INTO procopc(idproc,idopc) VALUES('10','5');
INSERT INTO procopc(idproc,idopc) VALUES('10','15');
INSERT INTO procopc(idproc,idopc) VALUES('10','16');
INSERT INTO procopc(idproc,idopc) VALUES('10','17');

INSERT INTO procopc(idproc,idopc) VALUES('11','1');
INSERT INTO procopc(idproc,idopc) VALUES('11','2');
INSERT INTO procopc(idproc,idopc) VALUES('11','3');
INSERT INTO procopc(idproc,idopc) VALUES('11','4');

INSERT INTO procopc(idproc,idopc) VALUES('12','1');
INSERT INTO procopc(idproc,idopc) VALUES('12','5');

INSERT INTO procopc(idproc,idopc) VALUES('13','1');
INSERT INTO procopc(idproc,idopc) VALUES('13','5');

INSERT INTO procopc(idproc,idopc) VALUES('14','13');

INSERT INTO procopc(idproc,idopc) VALUES('15','1');
INSERT INTO procopc(idproc,idopc) VALUES('16','1');
INSERT INTO procopc(idproc,idopc) VALUES('16','5');



--Roles del Sistema
INSERT INTO rol(idrol,nombre,estado) VALUES('1', 'ADMINISTRADOR', '1');
INSERT INTO rol(idrol,nombre,estado) VALUES('2', 'APROBADOR INSPECCION', '1');
INSERT INTO rol(idrol,nombre,estado) VALUES('3', 'APROBADOR JEFATURA TECNICA', '1');
INSERT INTO rol(idrol,nombre,estado) VALUES('4', 'APROBADOR LEGAL', '1');
INSERT INTO rol(idrol,nombre,estado) VALUES('5', 'APROBADOR FINAL', '1');
INSERT INTO rol(idrol,nombre,estado) VALUES('6', 'APROBADOR TECNICO', '1');

--Usuario (Falta implementar porq se debe encriptar con el Sistema)

--RolUsu ,se asigna el rol administrador al usuario
-- INSERT INTO rolusu(idrol,login) VALUES ('1', 'admin');

--Permiso al administrador
INSERT INTO permiso(idrol,idmod,idproc,idopc) VALUES('1','1','4','14');



--*********************************KIT GNV

--Marca Cilindros
INSERT INTO marcaCilindro(idmarccil,nombre,estado) VALUES(1,'IMPROSIL',1)
INSERT INTO marcaCilindro(idmarccil,nombre,estado) VALUES(2,'RENAULT',1)

--Marca Reductor
INSERT INTO marcaReductor(idmarcred,nombre,estado) VALUES(1,'GASPETRO',1)
INSERT INTO marcaReductor(idmarcred,nombre,estado) VALUES(2,'CIDEGAS',1)
INSERT INTO marcaReductor(idmarcred,nombre,estado) VALUES(3,'LANDI',1)

--Cilindros
INSERT INTO cilindro(idcil,capacidad,serie,idmarccil,estado) VALUES(1,10,'',1,1)
INSERT INTO cilindro(idcil,capacidad,serie,idmarccil,estado) VALUES(2,15,'',1,1)
INSERT INTO cilindro(idcil,capacidad,serie,idmarccil,estado) VALUES(3,20,'',1,1)

--Reductores
INSERT INTO reductor(idreduc,tipoTecnologia,idmarcred,estado) VALUES(1,'3RA GENERACION',1,1)
INSERT INTO reductor(idreduc,tipoTecnologia,idmarcred,estado) VALUES(2,'5TA GENERACION',2,1)


--************************************DATOS BENEFICIARIO
--Documentos
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('1','CARNET PRIORIDAD','1','b');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('2','RUAT','1','b');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('3','INSTITUCIÓN PERTENECIENTE','1','b');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('4','PAGO IMPUESTOS','1','b');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('5','LICENCIA DE CONDUCIR','1','b');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('6','FOTOCOPIA DE CARNET DE NUEVO BENEFICIARIO','1','cb');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('7','FOTOCOPIA DE PODER O  TESTIMONIO','1','cb');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('8','FOTOCOPIA DE CARNET DE CONVERSIÓN DEL VEHÍCULO','1','cb');
INSERT INTO docBeneficiario(iddocb,nombre,estado,tipo) VALUES('9','FOTOCOPIA DEL ULTIMO PAGO DE LUZ O AGUA','1','cb');

--************************************VEHICULO
--ChipRom
INSERT INTO chipRom(idRom,nombreChip,fecha,bloqueado) VALUES('1','3A0000019FFE782D','2018-06-22 07:35:14.927','0');
INSERT INTO chipRom(idRom,nombreChip,fecha,bloqueado) VALUES('2','DC000001A037042D','2018-06-22 07:35:24.140','0');
INSERT INTO chipRom(idRom,nombreChip,fecha,bloqueado) VALUES('3','23000001A083752D','2018-06-22 07:35:33.430','0');
INSERT INTO chipRom(idRom,nombreChip,fecha,bloqueado) VALUES('4','920000019F8E2D2D','2018-06-22 07:35:42.477','0');
INSERT INTO chipRom(idRom,nombreChip,fecha,bloqueado) VALUES('5','52000001A0AAC92D','2018-06-22 07:35:53.573','0');

--Combustible
INSERT INTO Combustible(idcomb,nombre,detalle,estado) VALUES(1,'GASOLINA','',1)
INSERT INTO Combustible(idcomb,nombre,detalle,estado) VALUES(2,'DIESEL','',1)

--Marca Vehiculo
INSERT INTO marcaVehiculo(idmarcv,nombre,estado) VALUES('1','TOYOTA','1');
INSERT INTO marcaVehiculo(idmarcv,nombre,estado) VALUES('2','NISSAN','1');
INSERT INTO marcaVehiculo(idmarcv,nombre,estado) VALUES('3','COROLLA','1');

--Modelo Vehiculo
INSERT INTO modeloVehiculo(idmodv,nombre,estado) VALUES('1','2010','1');
INSERT INTO modeloVehiculo(idmodv,nombre,estado) VALUES('2','2000','1');
INSERT INTO modeloVehiculo(idmodv,nombre,estado) VALUES('3','1900','1');

--Tipo Vehiculo
INSERT INTO tipoVehiculo(idtipv,nombre,estado) VALUES('1','TAXI','1');
INSERT INTO tipoVehiculo(idtipv,nombre,estado) VALUES('2','CAMIONETA','1');
INSERT INTO tipoVehiculo(idtipv,nombre,estado) VALUES('3','MICRO','1');
 
--Tipo Motor
INSERT INTO tipoMotorVehiculo(idtipoMotorVeh,nombre,estado) VALUES('1','CARBURADOR','1');
INSERT INTO tipoMotorVehiculo(idtipoMotorVeh,nombre,estado) VALUES('2','INYECCION','1');

--Tipo Servicio
INSERT INTO tipoServicioVehiculo(idTipSv,nombre,estado) VALUES('1','PUBLICO','1');
INSERT INTO tipoServicioVehiculo(idTipSv,nombre,estado) VALUES('2','PRIVADO','1');
INSERT INTO tipoServicioVehiculo(idTipSv,nombre,estado) VALUES('3','ESCOLAR','1');



