
package prueba;
import entidad.CategoriaCama;
import java.util.List;
import servicio.CategoriaCamaServicio;

public class CategoriaCamaPrueba {
    

    public static void main(String[] args) {
        
        CategoriaCamaServicio servicio = new CategoriaCamaServicio();
        CategoriaCama categoria1 = new CategoriaCama(
                1,
                "Doble",
                "120 x 190",
                "url del hotel",
                "blanco"
        );
        CategoriaCama categoria2 = new CategoriaCama(
                1,
                "Sencilla",
                "120 x 190",
                "url del hotel",
                "Negro"
        );
       
        CategoriaCama categoria = servicio.crearCategoriaCama(categoria1);
        System.out.println("Se creo la siguiente categoria de cama: ");
        System.out.println("TIPO: " + categoria.getTipo() );  
        System.out.println("COLOR: " + categoria.getColor() );
        System.out.println("MEDIDAS: " + categoria.getMedidas() );
        System.out.println("IMAGEN URL: " + categoria.getFotoUrl());           
        System.out.println(servicio.mostrarCategoriaCama(categoria.getIdCategoriaCama()));
        int id = categoria.getIdCategoriaCama();
        categoria = servicio.modificarCategoriaCama(categoria2,id);
        System.out.println("Se modifico la siguiente categoria de cama: ");
        System.out.println("TIPO: " + categoria.getTipo() );  
        System.out.println("COLOR: " + categoria.getColor() );
        System.out.println("MEDIDAS: " + categoria.getMedidas() );
        System.out.println("IMAGEN URL: " + categoria.getFotoUrl());
        //borrar categoriaCama
        
        
       List<CategoriaCama> listado = servicio.mostrarCategoriaCamas();
       listado.forEach((CategoriaCama data) -> {
           System.out.print(data.toString());
        });
       
       System.out.println(servicio.eliminarCategoriaCama(categoria.getIdCategoriaCama()));
    }
    
    
}
