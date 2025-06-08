from flask import Flask, request, render_template, redirect, url_for, session, jsonify
from flask_cors import cross_origin
from utils.validations import validate_login_user, validate_register_user, validate_confession, validate_add_act
from database import db
from werkzeug.utils import secure_filename
import hashlib
import filetype
import os

UPLOAD_FOLDER = 'static/uploads'

app = Flask(__name__)
app.secret_key = "s3cr3t_k3y"
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
# app.config['MAX_CONTENT_LENGTH'] = 16 * 1000 * 1000

# --- Auth Routes ---
#--------------------------------------------------------------------------------------------------
#Nuevo
@app.route("/addAct", methods=["GET", "POST"])
def addAct():
    if request.method == "POST":
        region = request.form.get("region")
        print(f' ESTA ES LA REGION SELECCIONADA: {region}')
        comuna = request.form.get("comuna")
        print(f' ESTA ES LA COMUNA : {comuna}')
        sector = request.form.get("sector")
        name = request.form.get("name")
        email = request.form.get("email")
        celular = request.form.get("cel")
        #para el contacto revisar los seleccionados
        seleccionados = request.form.getlist('contactos') #['1','2','3','4','5','6']
        detalles = {}

        for opcion in seleccionados:
            detalle = request.form.get(f'{opcion}_input')
            detalles[opcion] = detalle
        #Ya tenemos los tipos de contacto jeje
        inicio = request.form.get("date")
        fin = request.form.get("end")
        descripcion = request.form.get("desc")
        tema = request.form.get("tema")
        #Intentamos capturar las fotos
        fotos = {}
        recibidos = request.files.getlist("pic") # Una lista con los files
        for img in recibidos:
            if img and img.filename != "":
                #img.save(f"./uploads/{img.filename}")
                # 1. generate random name for img
                #conf_img = request.files.get("conf-img")
                _filename = hashlib.sha256(
                    secure_filename(img.filename) # nombre del archivo
                    .encode("utf-8") # encodear a bytes
                    ).hexdigest()
                _extension = filetype.guess(img).extension
                img_filename = f"{_filename}.{_extension}"
                img.save(os.path.join(app.config["UPLOAD_FOLDER"], img_filename))

        #-----------------------------------------------------------------
        error = ""
        if validate_add_act(region, comuna, name, email, inicio, fin, tema, fotos):
            # try to register act
            db.register_act(region, comuna, sector, name, email, celular, detalles, 
                                          inicio, fin, descripcion, tema)
            return index()
        else:
            error += "Uno de los campos no es valido."
            return render_template("html/addAct.html", error=error)
        
    
    elif request.method == "GET":
        return render_template("html/addAct.html")
#-----------------------------------------------------------------------------------
# --- Routes ---
@app.route("/", methods=["GET"])
def index():
    # get last confessions 
    data = []
    for act in db.get_act(page_size=5):
        id, comuna, sector, _, _, _, inicio, termino, _ = act
        comuna = db.get_comuna_by_id(comuna)
        comuna = comuna[0][0]
        tema = db.get_tema_by_id(id)[0][0]
        foto1 = db.get_photo_by_id(id)
        ### CHECKPOINT 

        img_filename = f"uploads/{foto1}"
        data.append({
            "inicio": inicio,
            "termino": termino,
            "comuna": comuna,
            "sector": sector,
            "tema": tema,
            "path_image": url_for('static', filename=img_filename)
        })
    
    return render_template("html/index.html", data=data)
    
@app.route("/list", methods = ["GET"])
def list():
    if request.method == "GET":
        # get last confessions 
        data = []
        for act in db.get_act(page_size=10):
            id, comuna, sector, organizador, _, _, inicio, termino, _ = act
            comuna = db.get_comuna_by_id(comuna)
            comuna = comuna[0][0]
            tema = db.get_tema_by_id(id)[0][0]

            ### CHECKPOINT 
            data.append({
                "inicio": inicio,
                "termino": termino,
                "comuna": comuna,
                "sector": sector,
                "tema": tema,
                "organizador": organizador,
                "total_fotos": 1
            })
        return render_template("html/list.html", data = data)

@app.route("/stats", methods = ["GET"])
def stats():
    if request.method == "GET":
        return render_template("html/stats.html")

@app.route("/get-stats-data-1", methods = ["GET"])
@cross_origin(origin= "127.0.0.1", supports_credentials = True)
def get_stats_data_1():
    #Grafico 1
    data = {}
    for act in db.get_act(page_size=1000):
        id, comuna, sector, organizador, _, _, inicio, termino, _ = act
        inicio_str = inicio.date().strftime("%Y-%m-%d")
        if inicio_str in data:
            data[inicio_str] += 1
        else:
            data[inicio_str] = 1
    #Convertimos el diccionario
    data_2 = [{"date": fecha, "count": count} for fecha, count in sorted(data.items())]

    return jsonify(data_2)

if __name__ == "__main__":
    app.run(debug=True)
