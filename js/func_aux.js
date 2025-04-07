document.addEventListener("DOMContentLoaded", function(){
    const boton_add = document.getElementById("addActivities-button");
    boton_add.addEventListener("click", function(){
        window.location.href = "addAct.html"
    })
    const boton_list = document.getElementById("listActivities-button");
    boton_list.addEventListener("click", function(){
        window.location.href = "list.html"
    })
    const boton_stats = document.getElementById("stats-button");
    boton_stats.addEventListener("click", function(){
        window.location.href = "stats.html"
    })
})