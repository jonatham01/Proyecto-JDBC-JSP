package controlador;

import com.google.gson.Gson;
import servicio.categoriaHabitacionServicio;
import dto.CategoriaHabitacionDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/crearCategoria")
public class CrearCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoriaHabitacionDTO categoriaDTO = gson.fromJson(request.getReader(), CategoriaHabitacionDTO.class);
        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
        
        String nuevaCategoriaJson = gson.toJson(servicio.crearCategoriaHabitacion( categoriaDTO));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(nuevaCategoriaJson);
    }
}