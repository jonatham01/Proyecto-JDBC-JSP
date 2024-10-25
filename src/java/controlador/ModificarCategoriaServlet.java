package controlador;

import com.google.gson.Gson;
import servicio.categoriaHabitacionServicio;
import dto.CategoriaHabitacionDTO;
import entidad.CategoriaHabitacion;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/modificarCategoria")
public class ModificarCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        CategoriaHabitacionDTO categoriaDTO = gson.fromJson(request.getReader(), CategoriaHabitacionDTO.class);

        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
        CategoriaHabitacion categoriaModificada = servicio.modificarCategoriaHabitacion(categoriaDTO, id);
        
        // Convertir a JSON y escribir la respuesta
        String categoriaModificadaJson = gson.toJson(categoriaModificada);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(categoriaModificadaJson);
    }
}
