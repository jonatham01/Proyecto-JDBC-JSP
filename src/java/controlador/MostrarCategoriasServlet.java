package controlador;

import com.google.gson.Gson;
import servicio.categoriaHabitacionServicio;
import entidad.CategoriaHabitacion;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mostrarCategorias")
public class MostrarCategoriasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
        List<CategoriaHabitacion> categorias = servicio.mostarCategoriaHabitaciones();
        
        String categoriasJson = gson.toJson(categorias);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(categoriasJson);
    }
}
