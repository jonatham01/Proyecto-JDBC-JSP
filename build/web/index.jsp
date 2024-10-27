<%@page import="java.lang.Integer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidad.CategoriaHabitacion" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="servicio.categoriaHabitacionServicio" %>
<%@page import="dto.CategoriaHabitacionDTO" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        main{
            display: flex;
            align-items: center;
            flex-direction: column;
            column-gap: 0px;
            gap: 0px;
        }
        .content{
            margin: 0;
            padding: 0;
            width: 1024px;
            display: grid;
            grid-template-columns: repeat(2, 50% [col-start]);
            gap: 40px;
        }
        form{
            display: flex;
            flex-direction: column;
            padding: 0 40px 0 0;
            gap: 12px;        
        }
        form > input, form > button {
            padding: 8px;
        }
        table{
            width: 100%;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td{
            padding: 8px 28px;
        }
    </style>
</head>
<body>
    <main>
        <div class="content">
            <H1>Modulo: Categoria de habitaciones</H1>
        </div>
        
        <div class="content">
            <form action="">
                <input type="hidden" name="idCategoriaHabitacion" id="id" >
                <label for="nombre">Nombre</label>
                <input type="text" name="nombre" id="nombre" placeholder="Ingrese el nombre de la categoria" required>
                <label for="precio">Precio por noche:</label>
                <input type="number" name="precio" id="precio" placeholder="Ingrese valor" required>
                <label for="url" >Enlace url de la foto:</label>
                <input type="url" name="url" id="url" placeholder="Ingrese url" required>
                <input type="hidden" name="accion" id="accion" value="enviarFormulario">
                <button type="submit" id="boton" name="boton"> Crear</button>
            </form>
            
            <%
                String idCategoriaParam = request.getParameter("idCategoriaHabitacion");
                int idEditar = -1;
                if( idCategoriaParam != null  && !idCategoriaParam.isEmpty()){
                    idEditar = Integer.parseInt(request.getParameter("idCategoriaHabitacion"));
                }
                String nombre = request.getParameter("nombre");
                String fotoUrl = request.getParameter("url");
                String accion = request.getParameter("accion");
                if ("enviarFormulario".equals(accion)) {
                    if (nombre != null && request.getParameter("precio") != null && fotoUrl != null){
                        double precio = Double.parseDouble(request.getParameter("precio"));
                        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
                        servicio.crearCategoriaHabitacion(new CategoriaHabitacionDTO(nombre,precio,fotoUrl));
                        out.println("<script>");
                            out.println("document.getElementById('nombre').value = '';");
                            out.println("document.getElementById('precio').value = '';");
                            out.println("document.getElementById('url').value = '';");
                            out.println("document.getElementById('accion').value = 'enviarFormulario';");
                        out.println("</script>");
                    }
                }
                else if("editarFormulario".equals(accion) && idEditar  != -1){
                    
                    if (nombre != null && request.getParameter("precio") != null && fotoUrl != null){
                        double precio = Double.parseDouble(request.getParameter("precio"));
                        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
                        servicio.modificarCategoriaHabitacion(new CategoriaHabitacionDTO(nombre,precio,fotoUrl), idEditar);
                        out.println("<script>");
                            out.println("document.getElementById('nombre').value = '';");
                            out.println("document.getElementById('precio').value = '';");
                            out.println("document.getElementById('url').value = '';");
                            out.println("document.getElementById('accion').value = 'enviarFormulario';");
                        out.println("</script>");
                }
                    }
                
             %>
            <div class="listado">
                <div class="consulta"></div>

                <table>
                    <tr>
                        <th>Nombre</th>
                        <th>Precio por noche</th>
                        <th>Url de la foto</th>
                        <th>Modificar</th>
                    </tr>
                    <%
                        categoriaHabitacionServicio servicio = new categoriaHabitacionServicio();
                        List<CategoriaHabitacion> listado = servicio.mostarCategoriaHabitaciones();
                        if (listado == null) {
                            out.println("<tr><td>------------</td><td>------------</td><td>------------</td><td>------------</td></tr>");
                        } else {
                            for (CategoriaHabitacion data : listado) {
                    %>
                                <tr>
                                    <td><%= data.getNombre() %></td>
                                    <td><%= data.getPrecioNoche() %></td>
                                    <td><%= data.getFotoUrl() %></td>
                                    <td> 
                                        <form action="">
                                            <input type="hidden" name=<%= data.getIdCategoriaHabitacion() %> value="enviarFormulario">
                                            <button type="submit">Editar</button>
                                            <%
                                                String accionEditar;
                                                accionEditar = request.getParameter(String.valueOf(data.getIdCategoriaHabitacion()));
                                                
                                                if ("enviarFormulario".equals(accionEditar)) {
                                                    
                                                    CategoriaHabitacion itemEditar = servicio.mostarCategoriaHabitacion(data.getIdCategoriaHabitacion());
                                                    if (itemEditar != null) {
                                                            
                                                            // Asignar valores de la categoría a los campos del formulario
                                                            int id = itemEditar.getIdCategoriaHabitacion();
                                                            String nombreEditar = itemEditar.getNombre();
                                                            double precioEditar = itemEditar.getPrecioNoche();
                                                            String fotoUrlEditar = itemEditar.getFotoUrl();
                                                            

                                                            // Cargar los datos en el formulario
                                                            out.println("<script>");
                                                            out.println("document.getElementById('nombre').value = '" + nombreEditar + "';");
                                                            out.println("document.getElementById('precio').value = '" + precioEditar + "';");
                                                            out.println("document.getElementById('url').value = '" + fotoUrlEditar + "';");
                                                            //out.println("document.getElementById('boton').textContent = '" + "editar / crear" + "';");
                                                            out.println("document.getElementsByName('accion')[0].value = 'editarFormulario';");
                                                            out.println("document.getElementsByName('idCategoriaHabitacion')[0].value = " + id + ";");
                                                            
                                                            out.println("</script>");
                                                            //out.println("<p>" + id + "</p>");
                                                      
                                                    }

                                                }

                                            %>
                                        </form>
                                        
                                    </td>
                                </tr>
                    <%
                            }
                        }
                    %>
                    
                </table>
            </div>
            
        </div>
    </main>
</body>
</html>
