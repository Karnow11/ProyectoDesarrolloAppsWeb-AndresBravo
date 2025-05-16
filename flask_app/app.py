from flask import Flask, request, render_template, redirect, url_for, session
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
        comuna = request.form.get("comuna")
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
        fin = request.form.get("fin")
        descripcion = request.form.get("desc")
        tema = request.form.get("tema")
        #Intentamos capturar las fotos
        fotos = {}
        recibidos = request.files.getlist("pic") # Una lista con los files
        for img in recibidos:
            if img and img.filename != "":
                img.save(f"./uploads/{img.filename}")

        #-----------------------------------------------------------------
        error = ""
        if validate_add_act(region, comuna, name, email, inicio, fin, tema, fotos):
            # try to register user
            status, msg = db.register_act(region, comuna, sector, name, email, celular, detalles, 
                                          inicio, fin, descripcion, tema)
        else:
            error += "Uno de los campos no es valido."

        return render_template("html/addAct.html", error=error)
    
    elif request.method == "GET":
        return render_template("html/addAct.html")
#-----------------------------------------------------------------------------------

@app.route("/index", methods=["GET", "POST"])
def login():
    if request.method == "POST":
        username = request.form.get("username")
        password = request.form.get("contrasenna")
        error = ""
        if validate_login_user(username, password):
            # try to login
            status, msg = db.login_user(username, password)
            if status:
                # set user field in session
                session["user"] = username
                return redirect(url_for("index"))
            error += msg
        else:
            error += "Uno de los campos no es valido."

        print(error)

        return render_template("html/index.html",error=error)
    
    elif request.method == "GET":
        if session.get("user", None):
            return redirect(url_for("index"))
        else:
            return render_template("html/index.html")

@app.route("/logout", methods=["GET"])
def logout():
    session.pop("user", None)
    return redirect(url_for("login"))



# --- Routes ---
@app.route("/", methods=["GET"])
def index():
    # get last confessions 
    data = []
    for act in db.get_act(page_size=5):
        id, sector, _, _, _, inicio, termino, _, _, comuna = act
        #tema = db.get_tema_by_id(id)
        #foto1 = db.get_photo_by_id(id)
        
        ### CHECKPOINT 

        #img_filename = f"uploads/{foto1}"
        data.append({
            "inicio": inicio,
            "termino": termino,
            "comuna": comuna,
            "sector": sector,
        #    "tema": tema,
        #    "path_image": url_for('static', filename=img_filename)
        })
    
    return render_template("html/index.html", data=data)

@app.route("/post-conf", methods=["POST"])
def post_conf():
    username = session.get("user", None)
    if username is None:
        return redirect(url_for("login"))

    conf_text = request.form.get("conf-text")
    conf_img = request.files.get("conf-img")

    if validate_confession(conf_text, conf_img):
        # 1. generate random name for img
        _filename = hashlib.sha256(
            secure_filename(conf_img.filename) # nombre del archivo
            .encode("utf-8") # encodear a bytes
            ).hexdigest()
        _extension = filetype.guess(conf_img).extension
        img_filename = f"{_filename}.{_extension}"

        # 2. save img as a file
        conf_img.save(os.path.join(app.config["UPLOAD_FOLDER"], img_filename))

        # 3. save confession in db
        user_id, _, _, _ = db.get_user_by_username(username)
        db.create_confession(conf_text, img_filename, user_id)

    return redirect(url_for("index"))


if __name__ == "__main__":
    app.run(debug=True)
