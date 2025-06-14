document.addEventListener("DOMContentLoaded", function(){
    const boton_index = document.getElementById("index-button");
    boton_index.addEventListener("click", function(){
        const url3 = "{{url_for('index')}}"
        window.location.href = ROUTES.index;
    })
})

document.addEventListener("DOMContentLoaded", function(){
    const boton_index = document.getElementById("listActivities-button");
    boton_index.addEventListener("click", function(){
        const url3 = "{{url_for('list_route')}}"
        window.location.href = ROUTES.list_route;
    })
})

function cargarTabla(){
    var datos = JSON.parse(localStorage.getItem("datos_fila"))
    console.log(datos.region)
    if(datos){
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

//document.addEventListener('DOMContentLoaded', cargarTabla)

// Trabajo comentarios:
const validadorCom = (nombre, texto) => {
    //Validar longitud
    if (nombre.length > 80 || nombre.length < 1){
        return [false, "Nombre muy largo o muy corto"]
    }
    if (texto.length > 300 || texto.length < 2){
        return [false, "Texto muy largo o muy corto"]
    }
    //Tipo y nulidad
    if (nombre == null || texto == null || nombre.trim() === '' || texto.trim() === ''){
        return [false, "Campo vacio"]
    }

    return [true, "Todo bien"]
}

document.addEventListener('DOMContentLoaded', () =>{
    const submit_add_Com_Btn2 = document.getElementById("post-com-button");
    const statusMessageDiv = document.getElementById('status-message');
    const comDiv = document.getElementById('com-list-div3');

    submit_add_Com_Btn2.addEventListener('click', async () => {
        let nombre = document.getElementById("com_name_field").value;
        let texto = document.getElementById("com_text_field").value;
        let act_id = document.getElementById("com_act_id").value;
        let form = document.getElementById("com-form")
        let isValid = validadorCom(nombre,texto);
        let valid_msg = isValid[1];
        isValid = isValid[0]
        if (!isValid) {
            alert(`Errores en el formulario: ${valid_msg}`)
            return;
        }
        const postData = {
            "name": nombre,
            "comentario": texto,
            "act_id": act_id,
        }
        try {
            const response = await fetch(`${window.origin}/addComentario2`, {
                method: "POST",
                body: JSON.stringify(postData),
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            if (response.ok) { // codigo 200-299
                const result = await response.json();
                showStatusMessage('Comentario publicado!', 'success');
                form.reset();
            

            if (result && result.data){
                addComToDom(result.data)
            }
                
            }
            else {
                const ErrorData = await response.json()
                showStatusMessage(`Error: ${ErrorData.message || "Error al publicar el comentario."}`, 'error')
            }
        } catch (error) {
            console.error("Error enviando comentario:", error);
            showStatusMessage("Error enviando comentario. Intentelo de nuevo", error)
        } 
    })

    function showStatusMessage(message, type) {
        statusMessageDiv.textContent = message;
        statusMessageDiv.className = `mensaje ${type}`;
        statusMessageDiv.style.display = 'block';
    }

    function addComToDom(comentario){
        comentario.time_text = comentario.time_text.toLocaleString();
        nuevoCom = `
            <div style=" background-color: #A9A9A9; width: 94%; margin-left: 3%; margin-right: 3%;">
                <div style = "display: flex; align-items: center;">  
                    <h4 style = "margin-left: 1%; text-decoration: underline;">${comentario.name}</h4>
                    <h5 style = "margin-left: auto;">Ahora</h5>
                </div>
                <h5 style = "padding: 5px;">${comentario.text}</h5>
            </div>
            <br>
        `;
        comDiv.insertAdjacentHTML('afterbegin', nuevoCom);

    }
});