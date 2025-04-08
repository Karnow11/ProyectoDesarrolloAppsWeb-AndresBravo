function cargarTabla(){
    var datos = JSON.parse(localStorage.getItem("datos_fila"))
    console.log(datos.region)
    if(datos){
        console.log(datos.region)
        var contenido = [datos.region,datos.comuna,datos.sector,datos.nombre,datos.mail,datos.cel,datos.tipo,datos.inicio,
        datos.fin,datos.desc,datos.tema]
        var tabla = document.getElementById("tabla_agregadora_donde").getElementsByTagName('tbody')[0]
        var fila = tabla.insertRow()
        for(var i = 0; i < 3; i++){
            var celda = fila.insertCell();
            //contenido conseguido de la base de datos pero version juguete
            var content = contenido[i]
            celda.innerText = content
        }

        var tabla = document.getElementById("tabla_agregadora_quien")
        var fila = tabla.insertRow()
        for(var i = 3; i < 7; i++){
            var celda = fila.insertCell();
            //contenido conseguido de la base de datos pero version juguete
            var content = contenido[i]
            celda.innerText = content
        }
       
        var tabla = document.getElementById("tabla_agregadora_cuando")
        var fila = tabla.insertRow()
        for(var i = 7; i < 11; i++){
            var celda = fila.insertCell();
            //contenido conseguido de la base de datos pero version juguete
            var content = contenido[i]
            celda.innerText = content
        }
       
        var tabla = document.getElementById("tabla_agregadora_fotos")
        var fila = tabla.insertRow()
        var fotos = ["../media/memori.png","../media/JorgeisDead.png","../media/Dantetrabajen.png",
            "../media/tomdead.png","../media/nikagachadocomosiempre.png"]
        for(var i = 0; i < 3; i++){
            var celda = fila.insertCell();
            //contenido conseguido de la base de datos pero version juguete
            var content = document.createElement("img")
            content.src = fotos[i]
            content.width = 320
            content.height = 240
            celda.appendChild(content)
        }
        var tabla = document.getElementById("tabla_agregadora_fotos2")
        var fila = tabla.insertRow()
        for(var i = 3; i < 5; i++){
            var celda = fila.insertCell();
            //contenido conseguido de la base de datos pero version juguete
            var content = document.createElement("img")
            content.src = fotos[i]
            content.width = 320
            content.height = 240
            celda.appendChild(content)
        }
    }
}
document.addEventListener("DOMContentLoaded", function(){
    const boton_list = document.getElementById("listActivities-button");
    boton_list.addEventListener("click", function(){
        window.location.href = "list.html"
    })
    const boton_index = document.getElementById("index-button");
    boton_index.addEventListener("click", function(){
        window.location.href = "index.html"
    })
})
document.addEventListener('DOMContentLoaded', cargarTabla)