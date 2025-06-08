document.addEventListener("DOMContentLoaded", function(){
    const boton_index = document.getElementById("index-button");
    boton_index.addEventListener("click", function(){
        const url3 = "{{url_for('index')}}"
        window.location.href = ROUTES.index;
    })
})
function crearGraficos(){
    var g1 = document.getElementById("graph_lin").getContext('2d')
    var g2 = document.getElementById("graph_cake").getContext('2d')
    var g3 = document.getElementById("graph_bar").getContext('2d')

    //Aqui mas adelante conseguiremos la data de la base de datos
    var days = ["Lun","Mar","Mie","Jue","Vie","Sab","Dom"]
    var count = [5,9,12,13,25,6,2]
    var types = ["Música","Deporte","Ciencias","Religión","Politica","Juegos","Baile","Comida","Otro"]
    var type_count = [2,3,10,1,2,20,5,3,3]
    var months = ["Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"]
    var day_noon_night = [[1,2,3,4,5,6,6,5,4,3,2,1],[2,3,4,5,6,7,7,6,5,4,3,2],[3,4,5,6,7,8,8,7,6,5,4,3]]
    new Chart(g1,{
        type: 'line',
        data: {
            labels: days,
            datasets:[{
                label: "Dias",
                data: count,
                backgroundColor: 'red',
            }]
        },
        options: {
            responsive: false,
            maintainAspectRatio: false
        }
    })

    new Chart(g2,{
        type: 'pie',
        data: {
            labels: types,
            datasets:[{
                label: "Tipos de Actividad",
                data: type_count,
                backgroundColor: ['rgba(255, 99, 132, 0.7)','rgba(54, 162, 235, 0.7)',
                    'rgba(255, 206, 86, 0.7)', 'rgba(75, 192, 192, 0.7)', 'rgba(153, 102, 255, 0.7)', 
                    'rgba(255, 159, 64, 0.7)', 'rgba(255, 99, 71, 0.7)',  'rgba(0, 204, 255, 0.7)',  'rgba(34, 193, 195, 0.7)'],
            }]
        },
        options: {
            responsive: false,
            maintainAspectRatio: false
        }
    })

    new Chart(g3,{
        type: 'bar',
        data: {
            labels: months,
            datasets:[
            {
                label: "Dia",
                data: day_noon_night[1],
                backgroundColor: 'rgba(255,180,0,1)',
            },
            {
                label: "Mediodia",
                data: day_noon_night[2],
                backgroundColor: 'rgba(0,255,180,1)',
            },
            {
                label: "Tarde",
                data: day_noon_night[0],
                backgroundColor: 'rgba(180,0,255,1)',
            }
            ]
        },
        options: {
            responsive: false,
            maintainAspectRatio: false
        }
    })

}
document.addEventListener('DOMContentLoaded', crearGraficos)


//Creamos el grafico 1
Highcharts.chart("container1", {
  chart: {
    type: "line",
  },
  title: {
    text: "Activiades por dia",
  },
  xAxis: {
    type: "datetime",
    dateTimeLabelFormats: {
      month: "%b %e, %Y",
    },
    title: {
      text: "Fecha",
    },
  },
  yAxis: {
    title: {
      text: "Numero de Actividades",
    },
  },
  legend: {
    align: "left",
    verticalAlign: "top",
    borderWidth: 0,
  },

  tooltip: {
    shared: true,
    crosshairs: true,
  },

  series: [
    {
      name: "Actividades",
      data: [],
      lineWidth: 1,
      marker: {
        enabled: true,
        radius: 4,
      },
      color: "#FC2865",
    },
  ],
});

fetch("http://127.0.0.1:5000/get-stats-data-1")
  .then((response) => response.json())
  .then((data) => {
    let parsedData = data.map((item) => {
      const [year, month, day] = item.date
        .split("-")
        .map((part) => parseInt(part, 10));
      return [
        Date.UTC(year, month - 1, day), // javascript month indices start from 0 !
        item.count,
      ];
    });

    // Get the chart by ID
    const chart = Highcharts.charts.find(
      (chart) => chart && chart.renderTo.id === "container1"
    );

    // Update the chart with new data
    chart.update({
      series: [
        {
          data: parsedData,
        },
      ],
    });
  })
  .catch((error) => console.error("Error:", error));