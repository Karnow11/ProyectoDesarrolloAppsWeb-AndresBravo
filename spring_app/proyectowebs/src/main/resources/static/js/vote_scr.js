document.addEventListener("DOMContentLoaded", function(){
    const boton_index = document.getElementById("index-button");
    boton_index.addEventListener("click", function(){
        const url3 = "{{url_for('index')}}"
        window.location.href = ROUTES.index;
    })
})