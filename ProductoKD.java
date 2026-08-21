package poo_s8;

public class ProductoKD {
    private String codigopro;
    private String nombrepro;
    private String descripcionpro;
    private double precioBase;
    private double precioVenta;
    private String categoria;
    private int cantidad;
    private int estado; // 1: Activo, 0: Eliminado lógicamente

    public ProductoKD() {
        this.estado = 1;
    }


    static ProductoKD desdeBaseDeDatos(String codigo, String nombre, String descripcion,
            double precioBase, double precioVenta, String categoria, int cantidad, int estado) {
        ProductoKD p = new ProductoKD();
        p.codigopro = codigo;
        p.nombrepro = nombre;
        p.descripcionpro = descripcion;
        p.precioBase = precioBase;
        p.precioVenta = precioVenta;
        p.categoria = categoria;
        p.cantidad = cantidad;
        p.estado = estado;
        return p;
    }

    public ProductoKD(String codigo, String nombre, String descripcion, double precioBase, double precioVenta, String categoria, int cantidad) {
        setCodigo(codigo);
        setNombre(nombre);
        this.descripcionpro = descripcion;
        setPrecioBase(precioBase);
        setPrecioVenta(precioVenta);
        this.categoria = categoria;
        setCantidad(cantidad);
        this.estado = 1;
    }

    public String getCodigo() {
        return codigopro;
    }

    public void setCodigo(String codigo) {
        String regexCodigo = "^PRODUCTO-[0-9]{4}$";
        if (codigo != null && codigo.matches(regexCodigo)) {
            this.codigopro = codigo;
        } else {
            throw new IllegalArgumentException("El codigo no cumple con el formato requerido (Ejemplo: PRODUCTO-1001).");
        }
    }

    public String getNombre() {
        return nombrepro;
    }

    public void setNombre(String nombre) {
        String regexNombre = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s\\-_./#&%+\"'()]{3,50}$";
        if (nombre != null && nombre.matches(regexNombre)) {
            this.nombrepro = nombre;
        } else {
            throw new IllegalArgumentException("El nombre del producto no es valido (debe tener entre 3 y 50 caracteres).");
        }
    }

    public String getDescripcion() {
        return descripcionpro;
    }

    public void setDescripcion(String descripcion) {
        this.descripcionpro = descripcion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        if (precioBase > 0) {
            this.precioBase = precioBase;
        } else {
            throw new IllegalArgumentException("El precio base debe ser mayor a 0.");
        }
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        if (precioVenta >= this.precioBase) {
            this.precioVenta = precioVenta;
        } else {
            throw new IllegalArgumentException("El precio de venta no puede ser menor al precio base.");
        }
    }

    public String getPrecioBaseFormateado() {
        return formatearPrecio(precioBase);
    }

    public String getPrecioVentaFormateado() {
        return formatearPrecio(precioVenta);
    }

    private String formatearPrecio(double valor) {
        return String.format("$%,.2f", valor);
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad >= 0) {
            this.cantidad = cantidad;
        } else {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}