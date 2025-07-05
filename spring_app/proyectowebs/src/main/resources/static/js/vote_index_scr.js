
document.addEventListener("DOMContentLoaded", function(){
    const boton_cancelar = document.getElementById("Cancel-button");
    boton_cancelar.addEventListener("click", function(){
        window.location.href = ROUTES.vote;
    })

    const boton_index = document.getElementById("index-button");
    boton_index.addEventListener("click", function(){
        window.location.href = ROUTES.index;
    })

});