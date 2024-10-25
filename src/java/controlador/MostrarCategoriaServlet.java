package controlador;

import com.google.gson.Gson;
import servicio.categoriaHabitacionServicio;
import entidad.CategoriaHabitacion;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mostrarCategoria")
public class MostrarCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
        CategoriaHabitacion categoria = servicio.mostarCategoriaHabitacion(id);
        
        String categoriaJson = gson.toJson(categoria);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(categoriaJson);
    }
}