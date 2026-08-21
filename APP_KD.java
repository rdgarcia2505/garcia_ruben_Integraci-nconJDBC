package poo_s8;

import java.util.List;
import java.util.Scanner;

public class APP_KD {

    private static final ProductoDAO dao = new ProductoDAO();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        do {
            System.out.println("\n******************************************");
            System.out.println("   SISTEMA DE GESTION - KD-ELECTRONICS   ");
            System.out.println("******************************************");
            System.out.println("1. Registrar Producto");
            System.out.println("2. Consultar Producto por Codigo");
            System.out.println("3. Listado de productos");
            System.out.println("4. Actualizar Producto");
            System.out.println("5. Eliminar Producto - Borrado Logico");
            System.out.println("6. Reactivar - Habilitar Producto");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n >>> Debe digitar un numero entero valido <<< ");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarProducto();
                    break;
                case 2:
                    consultarPorCodigo();
                    break;
                case 3:
                    listarProductos();
                    break;
                case 4:
                    actualizarProducto();
                    break;
                case 5:
                    eliminarProductoLogico();
                    break;
                case 6:
                    reactivarProducto();
                    break;
                case 7:
                    System.out.println("\nGracias por utilizar nuestro sistema");
                    break;
                default:
                    System.out.println("\n >>> Opcion no valida. Intente nuevamente <<< ");
            }

        } while (opcion != 7);

        scanner.close();
    }

    private static void registrarProducto() {
        System.out.println("\n****** REGISTRAR PRODUCTO ******");
        try {
            System.out.print("Codigo (Ej: PRODUCTO-1001): ");
            String codigo = scanner.nextLine().trim();

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();

            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine().trim();

            double precioBase = leerDouble("Precio base (Ej: 20000): ");
            double precioVenta = leerDouble("Precio venta (Ej: 35000): ");

            System.out.print("Categoria: ");
            String categoria = scanner.nextLine().trim();

            int cantidad = leerInt("Cantidad disponible: ");

            ProductoKD nuevo = new ProductoKD(codigo, nombre, descripcion, precioBase, precioVenta, categoria, cantidad);

            if (dao.registrarProducto(nuevo)) {
                System.out.println("\n *** Producto registrado exitosamente ***");
            } else {
                System.out.println("\n >>> No se pudo guardar el producto <<<");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\n[ERROR DE VALIDACION] " + e.getMessage());
        }
    }

    private static void consultarPorCodigo() {
        System.out.println("\n ****** CONSULTAR POR CODIGO ******");
        System.out.print("Digite el codigo a buscar (Ej: PRODUCTO-1001): ");
        String codigo = scanner.nextLine().trim();

        if (!codigo.matches("^PRODUCTO-[0-9]{4}$")) {
            System.out.println("\n El formato del codigo no es valido.");
            return;
        }

        ProductoKD p = dao.buscarPorCodigo(codigo);
        if (p != null) {
            mostrarDetalleProducto(p);
        } else {
            System.out.println("\n No se encontro ningun producto activo con el codigo: " + codigo);
        }
    }

    private static void listarProductos() {
        List<ProductoKD> lista = dao.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("\n No hay productos activos en el inventario.");
            return;
        }

        System.out.println("\n ******INVENTARIO DE PRODUCTOS (" + lista.size() + ") ******");
        for (ProductoKD p : lista) {
            mostrarDetalleProducto(p);
        }
    }

    private static void actualizarProducto() {
        System.out.println("\n ****** ACTUALIZAR PRODUCTO ******");
        System.out.print("digite el codigo del producto a modificar: ");
        String codigo = scanner.nextLine().trim();

        ProductoKD actual = dao.buscarPorCodigo(codigo);
        if (actual == null) {
            System.out.println("\n *** El producto no existe o esta inactivo. ***");
            return;
        }

        System.out.println("\n Deje el campo vacio y presione ENTER para mantener el valor actual.");

        try {
            System.out.print("Nuevo Nombre [" + actual.getNombre() + "]: ");
            String nombre = scanner.nextLine().trim();
            if (!nombre.isEmpty()) actual.setNombre(nombre);

            System.out.print("Nueva Descripcion [" + actual.getDescripcion() + "]: ");
            String desc = scanner.nextLine().trim();
            if (!desc.isEmpty()) actual.setDescripcion(desc);

            System.out.print("Desea cambiar precios o cantidad? (s/n): ");
            String resp = scanner.nextLine().trim();
            if (resp.equalsIgnoreCase("s")) {
                double pBase = leerDouble("Nuevo Precio Base [" + actual.getPrecioBase() + "]: ");
                double pVenta = leerDouble("Nuevo Precio Venta [" + actual.getPrecioVenta() + "]: ");
                int cant = leerInt("Nueva Cantidad [" + actual.getCantidad() + "]: ");

                actual.setPrecioBase(pBase);
                actual.setPrecioVenta(pVenta);
                actual.setCantidad(cant);
            }

            System.out.print("Nueva Categoria [" + actual.getCategoria() + "]: ");
            String cat = scanner.nextLine().trim();
            if (!cat.isEmpty()) actual.setCategoria(cat);

            if (dao.actualizarProducto(actual)) {
                System.out.println("\n *** Producto actualizado exitosamente.***");
            } else {
                System.out.println("\n >>> No se pudo actualizar el producto.<<<");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\n[ERROR DE VALIDACION] " + e.getMessage());
        }
    }

    private static void eliminarProductoLogico() {
        System.out.println("\n ****** BORRADO LOGICO DE PRODUCTO ******");
        System.out.print("Digite el codigo del producto a eliminar: ");
        String codigo = scanner.nextLine().trim();

        ProductoKD p = dao.buscarPorCodigo(codigo);
        if (p == null) {
            System.out.println("\n *** No se encontro un producto en la BD ***");
            return;
        }

        System.out.print("Esta seguro que desea desactivar el producto '" + p.getNombre() + "'? (s/n): ");
        String confirmacion = scanner.nextLine().trim();

        if (confirmacion.equalsIgnoreCase("s")) {
            if (dao.eliminarLogico(codigo)) {
                System.out.println("\n *** El producto fue eliminado logicamente (desactivado) de la BD.***");
            } else {
                System.out.println("\n >>> No se pudo eliminar el producto.<<<");
            }
        } else {
            System.out.println("\n Operacion cancelada.");
        }
    }

    private static void reactivarProducto() {
        System.out.println("\n ****** REACTIVAR PRODUCTO ******");
        System.out.print("Digite el codigo del producto a habilitar: ");
        String codigo = scanner.nextLine().trim();

        ProductoKD p = dao.buscarPorCodigoGeneral(codigo);

        if (p == null) {
            System.out.println("\n *** No existe ningun producto registrado con el codigo: " + codigo + " ***");
            return;
        }

        if (p.getEstado() == 1) {
            System.out.println("\n *** El producto '" + p.getNombre() + "' ya se encuentra ACTIVO. ***");
            return;
        }

        System.out.print("Desea reactivar el producto '" + p.getNombre() + "'? (s/n): ");
        String confirmacion = scanner.nextLine().trim();

        if (confirmacion.equalsIgnoreCase("s")) {
            if (dao.rehabilitarProducto(codigo)) {
                System.out.println("\n *** El producto fue reactivado exitosamente en la BD. ***");
            } else {
                System.out.println("\n >>> No se pudo reactivar el producto. <<<");
            }
        } else {
            System.out.println("\n Operacion cancelada.");
        }
    }

    private static void mostrarDetalleProducto(ProductoKD p) {
        System.out.println("------------------------------------------");
        System.out.println("Codigo      : " + p.getCodigo());
        System.out.println("Nombre      : " + p.getNombre());
        System.out.println("Descripcion : " + p.getDescripcion());
        System.out.println("Precio Base : " + p.getPrecioBaseFormateado());
        System.out.println("Precio Venta: " + p.getPrecioVentaFormateado());
        System.out.println("Categoria   : " + p.getCategoria());
        System.out.println("Cantidad    : " + p.getCantidad());
        System.out.println("Estado      : " + (p.getEstado() == 1 ? "Activo" : "Inactivo (0)"));
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite un valor numerico decimal valido.");
            }
        }
    }

    private static int leerInt(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite un numero entero valido.");
            }
        }
    }
}
