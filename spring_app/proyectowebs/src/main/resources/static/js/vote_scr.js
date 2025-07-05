document.addEventListener("DOMContentLoaded", function(){
    const boton_index = document.getElementById("index-button");
    boton_index.addEventListener("click", function(){
        const url3 = "{{url_for('index')}}"
        window.location.href = ROUTES.index;
    })
    document.querySelectorAll("button[id^='vote_button_']").forEach(function(btn) {
        btn.addEventListener("click", function() {
            const actId = btn.getAttribute("data-act-id");
            // Oculta todos los forms primero (opcional)
            document.querySelectorAll("td[id^='form_cell_']").forEach(function(cell) {
                cell.style.display = "none";
            });
            // Muestra solo el form correspondiente
            const formCell = document.getElementById("form_cell_" + actId);
            if (formCell) {
                formCell.style.display = "";
            }
            const titulo = document.getElementById("votar2-col");
            if (titulo) {
                titulo.style.display = "";
            }
            
        });
    });
    const botones_cancelar = document.getElementsByName("Cancel-button");
    if (botones_cancelar) {
        botones_cancelar.forEach(function(boton_cancelar) {
            boton_cancelar.addEventListener("click", function() {
                // Oculta todas las columnas con id "votar2-col"
                document.querySelectorAll("#votar2-col").forEach(function(col) {
                    col.style.display = "none";
                });
                // Oculta todas las filas/formularios que empiecen con id "form_cell_"
                document.querySelectorAll("[id^='form_cell_']").forEach(function(cell) {
                    cell.style.display = "none";
                });
            })
        });
    }
    //Función para el boton submit 
    document.querySelectorAll("form[id^='vote_form_']").forEach(function(form) {
        form.addEventListener("submit", function(event) {
            event.preventDefault(); // Evita el submit tradicional

            const formData = new FormData(form);
            const action = form.action;

            fetch(action, {
                method: "POST",
                body: formData
            })
            .then(response => response.text()) // o .json() según tu backend
            .then(data => {
                // Aquí puedes actualizar la UI dinámicamente, mostrar mensajes, etc.
                alert("¡El voto ha sido emitido!");
                return fetch('get-vote-data/' + formData.get('act_id'));
            })
            .then(response => response.json())
            .then(notas => {
                let suma = 0;
                notas.forEach(nota => {
                    suma += nota.nota;
                });
                let promedio = 0;
                if (notas.length > 0) {
                    promedio = suma / notas.length;
                }
                document.getElementById('nota_td_' + formData.get('act_id')).innerText = promedio.toFixed(2);
                document.querySelectorAll("#votar2-col").forEach(function(col) {
                    col.style.display = "none";
                });
                // Oculta todas las filas/formularios que empiecen con id "form_cell_"
                document.querySelectorAll("[id^='form_cell_']").forEach(function(cell) {
                    cell.style.display = "none";
                });
            })
            .catch(error => {
                alert("Error al emitir el voto o actualizar nota" + error);
            });
        });
    });
    
})
function votar(id){
    const filaVoto = document.getElementById(`voto-${id}`)
    if (filaVoto) {
        filaVoto.hidden = false;
    }
}

