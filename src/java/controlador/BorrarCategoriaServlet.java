package controlador;

import com.google.gson.Gson;
import servicio.categoriaHabitacionServicio;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/borrarCategoria")
public class BorrarCategoriaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
        boolean exito = servicio.borrarCategoriaHabitacion(id);

        // Enviar la respuesta JSON de éxito o fracaso
        String resultadoJson = gson.toJson(exito ? "Borrado exitoso" : "Error al borrar");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(resultadoJson);
    }
}
