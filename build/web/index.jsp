<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidad.CategoriaHabitacion" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%@page import="servicio.categoriaHabitacionServicio" %>
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
                <label for="nombre">Nombre</label>
                <input type="text" name="nombre" id="nombre" placeholder="Ingrese el nombre de la categoria">
                <label for="precio">Precio por noche:</label>
                <input type="number" name="precio" id="precio" placeholder="Ingrese valor">
                <label for="url">Enlace url de la foto:</label>
                <input type="url" name="url" id="url" placeholder="Ingrese url">
                <button type="submit"> Crear</button>
            </form>

            <div class="listado">
                <div class="consulta"></div>

                <table>
                    <tr>
                        <th>Nombre</th>
                        <th>Precio por noche</th>
                        <th>Url de la foto</th>
                        <<th>Modificar</th>
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
                                    <<td> editar</td>
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
