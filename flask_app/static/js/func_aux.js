document.addEventListener("DOMContentLoaded", function(){
    const boton_add = document.getElementById("addActivities-button");
    boton_add.addEventListener("click", function(){
        const url = "{{url_for('addAct')}}"
        window.location.href = ROUTES.addAct;
    })

    const boton_list = document.getElementById("listActivities-button");
    boton_list.addEventListener("click", function(){
        const url2 = "{{url_for('list')}}"
        window.location.href = ROUTES.list;
    })

    const boton_stats = document.getElementById("stats-button");
    boton_stats.addEventListener("click", function(){
        const url3 = "{{url_for('stats')}}"
        window.location.href = ROUTES.stats;
    })

    //const boton_index = document.getElementById("index-button");
    //boton_index.addEventListener("click", function(){
    //    const url4 = "{{url_for('index')}}"
    //    window.location.href = url4
    //})
})

function select_fila(fila){
    var datos_fila = {
        region : "Metropolitana",
        comuna: "Maipú",
        sector: "Mi casa",
        nombre: "Andrés Bravo",
        mail: "andres@gmail.com",
        cel: "+5691111111",
        tipo: "whatsapp",
        inicio: "2025-04-06 12:00" ,
        fin: "2025-04-06 15:00",
        desc: "Toca jugar R.E.P.O" ,
        tema:"Juegos"
    }
    localStorage.setItem("datos_fila", JSON.stringify(datos_fila))
    window.location.href = "template_full_info.html"
}