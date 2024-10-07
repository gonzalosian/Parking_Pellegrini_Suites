/*
Proyecto: Parking Pellegrini Suites
Seminario de Práctica de Informática
Módulo 2: Proceso unificado de desarrollo
Profesora: Ana Carolina Ferreyra
Alumno: Gonzalo Sian
Leg.: VINF013776
*/

CREATE DATABASE `PARKING`
    CHARACTER SET 'utf8mb4'
    COLLATE 'utf8mb4_general_ci';

USE `PARKING`;

CREATE TABLE `Cargos` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `TiposVehiculos` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `EstadosTurnos` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `colorRGB` varchar(6) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Pisos` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `EstadosPuestosPlaya` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `colorRGB` varchar(6) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `DescuentosPorHora` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `cantidadDias` integer NOT NULL,
  `porcentajeDescuento` integer NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Matafuegos` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `descripcion` varchar(50) NULL,
  `idPiso` integer NULL,
  `fechaVencimiento` date NOT NULL,
  `fechaPreAviso` date NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_Pisos_id` (`idPiso`),
  CONSTRAINT `fk_Pisos_id` FOREIGN KEY (`idPiso`) REFERENCES `Pisos` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Usuarios` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `dni` integer NOT NULL,
  `idCargo` integer NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_Cargos_id` (`idCargo`),
  CONSTRAINT `fk_Cargos_id` FOREIGN KEY (`idCargo`) REFERENCES `Cargos` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Clientes` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `dni` integer NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `Vehiculos` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `patente` varchar(10) NOT NULL,
  `idCliente` integer NULL,
  `idTipoVehiculo` integer NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_Clientes_id` (`idCliente`),
  CONSTRAINT `fk_Clientes_id` FOREIGN KEY (`idCliente`) REFERENCES `Clientes` (`id`) ON UPDATE CASCADE,
  KEY `fk_TiposVehiculos_id` (`idTipoVehiculo`),
  CONSTRAINT `fk_TiposVehiculos_id` FOREIGN KEY (`idTipoVehiculo`) REFERENCES `TiposVehiculos` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `HistoricoPrecios` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `fechaVigencia` datetime NOT NULL,
  `fechaHoraCarga` datetime NOT NULL,
  `idUsuarioCarga` integer NOT NULL,
  `idTipoVehiculo` integer NOT NULL,
  `precioPorHora` DECIMAL(10, 2) NOT NULL,
  `precioPorDia` DECIMAL(10, 2) NOT NULL,
  `precioLavado` DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_HistoricoPrecios_Usuarios_id` (`idUsuarioCarga`),
  CONSTRAINT `fk_HistoricoPrecios_Usuarios_id` FOREIGN KEY (`idUsuarioCarga`) REFERENCES `Usuarios` (`id`) ON UPDATE CASCADE,
  KEY `fk_HistoricoPrecios_TiposVehiculos_id` (`idTipoVehiculo`),
  CONSTRAINT `fk_HistoricoPrecios_TiposVehiculos_id` FOREIGN KEY (`idTipoVehiculo`) REFERENCES `TiposVehiculos` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `PuestosDePlaya` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `numeroPuestoPlaya` integer NOT NULL,
  `idPiso` integer NOT NULL,
  `idTipoVehiculo` integer NOT NULL,
  `idEstadoPuestoPlaya` integer NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_PuestosDePlaya_Pisos_id` (`idPiso`),
  CONSTRAINT `fk_PuestosDePlaya_Pisos_id` FOREIGN KEY (`idPiso`) REFERENCES `Pisos` (`id`) ON UPDATE CASCADE,
  KEY `fk_PuestosDePlaya_TiposVehiculos_id` (`idTipoVehiculo`),
  CONSTRAINT `fk_PuestosDePlaya_TiposVehiculos_id` FOREIGN KEY (`idTipoVehiculo`) REFERENCES `TiposVehiculos` (`id`) ON UPDATE CASCADE,
  KEY `fk_EstadosPuestosPlaya_id` (`idEstadoPuestoPlaya`),
  CONSTRAINT `fk_EstadosPuestosPlaya_id` FOREIGN KEY (`idEstadoPuestoPlaya`) REFERENCES `EstadosPuestosPlaya` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `MovimientosPlaya` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `idPuestoDePlaya` integer NOT NULL,
  `idVehiculo` integer NOT NULL,
  `fechaHoraIngreso` datetime NOT NULL,
  `idUsuarioIngreso` integer NOT NULL,
  `fechaHoraEgreso` datetime NULL,
  `idUsuarioEgreso` integer NULL,
  `codigoBarraIn` integer NOT NULL,
  `codigoBarraOut` integer NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_PuestosDePlaya_id` (`idPuestoDePlaya`),
  CONSTRAINT `fk_PuestosDePlaya_id` FOREIGN KEY (`idPuestoDePlaya`) REFERENCES `PuestosDePlaya` (`id`) ON UPDATE CASCADE,
  KEY `fk_MovimientosPlaya_Vehiculos_id` (`idVehiculo`),
  CONSTRAINT `fk_MovimientosPlaya_Vehiculos_id` FOREIGN KEY (`idVehiculo`) REFERENCES `Vehiculos` (`id`) ON UPDATE CASCADE,
  KEY `fk_MovimientosPlaya_Usuarios_Ingreso_id` (`idUsuarioIngreso`),
  CONSTRAINT `fk_MovimientosPlaya_Usuarios_Ingreso_id` FOREIGN KEY (`idUsuarioIngreso`) REFERENCES `Usuarios` (`id`) ON UPDATE CASCADE,
  KEY `fk_MovimientosPlaya_Usuarios_Egreso_id` (`idUsuarioEgreso`),
  CONSTRAINT `fk_MovimientosPlaya_Usuarios_Egreso_id` FOREIGN KEY (`idUsuarioEgreso`) REFERENCES `Usuarios` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `TurnosLavado` (
  `id` integer AUTO_INCREMENT NOT NULL,
  `fechaHoraDesde` datetime NOT NULL,
  `fechaHoraHasta` datetime NOT NULL,
  `idMovimientoPlaya` integer NULL,
  `idEstadoTurno` integer NOT NULL,
  `fechaHoraAnulacion` datetime NULL,
  `idUsuario` integer NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_MovimientosPlaya_id` (`idMovimientoPlaya`),
  CONSTRAINT `fk_MovimientosPlaya_id` FOREIGN KEY (`idMovimientoPlaya`) REFERENCES `MovimientosPlaya` (`id`) ON UPDATE CASCADE,
  KEY `fk_EstadosTurnos_id` (`idEstadoTurno`),
  CONSTRAINT `fk_EstadosTurnos_id` FOREIGN KEY (`idEstadoTurno`) REFERENCES `EstadosTurnos` (`id`) ON UPDATE CASCADE,
  KEY `fk_Usuarios_id` (`idUsuario`),
  CONSTRAINT `fk_Usuarios_id` FOREIGN KEY (`idUsuario`) REFERENCES `Usuarios` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into Cargos (descripcion) values ('Empleado'),('Gerente');
insert into TiposVehiculos (descripcion) values ('Auto'),('Camioneta'),('Moto'),('Camión ligero');

insert into EstadosTurnos (descripcion, colorRGB) values ('Libre','a4ff92'),('Ocupado','92d2ff'),
('En curso','d692ff'),('Finalizado','ff929c'),('Anulado','89484e');

insert into Pisos (descripcion) values ('Planta baja'),('Piso uno'),('Piso dos'),('Piso tres'),('Piso cuatro');

insert into EstadosPuestosPlaya (descripcion, colorRGB) values 
('Libre','c2f898'),('Ocupado','f8b198'),('Reservado','a49894');

insert into DescuentosPorHora (cantidadDias, porcentajeDescuento) values (30, 10),(40, 15),(50,20);

insert into Matafuegos (idPiso, fechaVencimiento, fechaPreAviso) values
(1, '2024-10-31', '2024-10-21'),(1, '2024-11-24', '2024-11-14'),(2, '2025-10-31', '2025-10-21'),
(2, '2025-5-20', '2025-5-10'),(3, '2024-12-31', '2024-12-21'),(3, '2024-12-31', '2024-12-21'),
(4, '2025-8-20', '2025-8-10'),(4, '2025-2-20', '2025-2-10');

insert into Usuarios (apellido, nombre, dni, idCargo) values ('Perez', 'Juan', 30222888, 1),
('Gomez', 'Carlos', 30111333, 1),('Sian', 'Gonzalo', 31444999, 2);

insert into Clientes (apellido, nombre, dni) values ('Cabral', 'Daniel', 10222333), 
('Diaz', 'Leon', 15666777), ('Frete', 'Gregorio', 20333444);

insert into Vehiculos (patente, idCliente, idTipoVehiculo) values ('AAAA1234', 1, 1), 
('BBBB1234', null, 1), ('CCCC1234', null, 2), ('DDDD1234', null, 3);

insert into HistoricoPrecios (fechaVigencia,fechaHoraCarga,idUsuarioCarga,idTipoVehiculo,precioPorHora,
precioPorDia,precioLavado) values
('2024-08-05 12:00:00', '2024-07-01 12:00:00', 2, 1, 1000, 12000, 5000),
('2024-09-05 12:00:00', '2024-08-01 12:00:00', 2, 2, 1500, 15000, 7000),
('2024-10-05 12:00:00', '2024-09-01 12:00:00', 2, 3, 500, 6000, 1000);

insert into PuestosDePlaya (numeroPuestoPlaya, idPiso, idTipoVehiculo, idEstadoPuestoPlaya) values
(1,1,1,2),(2,1,1,2),(3,1,1,1),(4,1,1,1),(5,1,1,1),(6,1,1,1),(7,1,2,1),(8,1,3,1),(9,1,3,1),(10,1,3,1);

insert into MovimientosPlaya (idPuestoDePlaya,idVehiculo,fechaHoraIngreso,idUsuarioIngreso,
fechaHoraEgreso,idUsuarioEgreso,codigoBarraIn,codigoBarraOut) values
(1,1,'2024-10-05 11:00:00',2,'2024-10-05 14:00:00',2,1000000,1000001),
(2,2,'2024-10-05 13:20:00',2,null,null,1000002,null),
(1,1,current_timestamp(),2,null,null,1000003,null);

insert into TurnosLavado (fechaHoraDesde, fechaHoraHasta, idMovimientoPlaya, idEstadoTurno, 
fechaHoraAnulacion, idUsuario) values
('2024-10-05 12:00:00', '2024-10-05 13:00:00', 1, 4, null, 2),
('2024-10-05 13:00:00', '2024-10-05 14:00:00', null, 1, null, null),
('2024-10-05 14:00:00', '2024-10-05 15:00:00', 2, 4, null, 2),
('2024-10-05 15:00:00', '2024-10-05 16:00:00', null, 1, null, null);

/*
PRUEBAS DE CRUD REQUERIDAS

select m.id as idMovimiento, pp.numeroPuestoPlaya, v.patente, m.fechahoraingreso, m.fechahoraegreso, t.fechaHoraDesde as fechaHoraDesdeLavado, 
	t.fechaHoraHasta as fechaHoraHastaLavado, et.descripcion as estadoTurnoLavado, epp.descripcion as estadoPuesto, m.codigoBarraIn
from movimientosplaya m
left join vehiculos v on v.id=m.idvehiculo
left join TurnosLavado t on t.idMovimientoPlaya=m.id
left join estadosturnos et on et.id=t.idEstadoTurno
left join PuestosDePlaya pp on pp.id=m.idPuestoDePlaya
left join EstadosPuestosPlaya epp on epp.id=pp.idEstadoPuestoPlaya;

-- INGRESA UN VEHÍCULO ASIGNÁNDOLE UN PUESTO EN LA PLAYA. NO DESEA TURNO DE LAVADO.
insert into MovimientosPlaya (idPuestoDePlaya,idVehiculo,fechaHoraIngreso,idUsuarioIngreso,
fechaHoraEgreso,idUsuarioEgreso,codigoBarraIn,codigoBarraOut) values
(3,3,current_timestamp(),2,null,null,1000004,null);

-- SE ACTUALIZA EL ESTADO DEL PUESTO DE PLAYA
update PuestosDePlaya set idEstadoPuestoPlaya=2 where id=3;

-- SE HACE EL CHECK-OUT
update MovimientosPlaya set 
	fechaHoraEgreso=current_timestamp(),
    idUsuarioEgreso=2,
    codigoBarraOut=1000005
where codigoBarraIn=1000004;

-- SE ACTUALIZA EL PUESTO, LIBERANDO EL MISMO.
update PuestosDePlaya set idEstadoPuestoPlaya=1 where id=3;

-- SE ELIMINAN LOS REGISTROS DE PRUEBA
delete from MovimientosPlaya where codigoBarraIn=1000004;

*/