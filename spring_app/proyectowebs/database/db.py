import pymysql
import json
from sqlalchemy import create_engine, Column, Integer, BigInteger, String, ForeignKey
from sqlalchemy.orm import sessionmaker, declarative_base, relationship
from bleach import clean 

DB_NAME = "tarea2"
DB_USERNAME = "cc5002" #cc5002
DB_PASSWORD = "programacionweb" #programacionweb
DB_HOST = "localhost"
DB_PORT = 3306
DB_CHARSET = "utf8"

DATABASE_URL = f"mysql+pymysql://{DB_USERNAME}:{DB_PASSWORD}@{DB_HOST}:{DB_PORT}/{DB_NAME}"

engine = create_engine(DATABASE_URL, echo=False, future=True)

SessionLocal = sessionmaker(bind=engine)

Base = declarative_base()

with open('database/querys.json', 'r') as querys:
	QUERY_DICT = json.load(querys)

#------------------------------------------------------------Models--------------------------------------------------------------------------
class Comentario(Base):
	__tablename__ = 'comentarios'

	id = Column(BigInteger, primary_key = True, autoincrement = True)
	com_text = Column(String(255), nullable = False)
	author_name = Column(String(255), nullable = False)
	actividad_id = Column(BigInteger, ForeignKey('Actividad.id'), nullable = False)

	actividad = relationship("Actividad", back_populates = "comentarios")


class Actividad(Base):
	__tablename__ = 'actividades'

	id = Column(BigInteger, primary_key = True, autoincrement = True)
	comuna = Column(String(255), nullable = False)
	sector = Column(String(255), nullable = False)
	nombre = Column(String(255), nullable = False)
	email = Column(String(255), nullable = False)
	celular = Column(String(255), nullable = False)
	fecha_inicio = Column(String(255), nullable = False)
	fecha_fin = Column(String(255), nullable = False)
	descripcion = Column(String(255), nullable = False)

	actividad = relationship("Comentario", back_populates = "actividades")

#--------------------------------------------------------------------------------------------------------------------------------------------
# -- conn ---

def get_conn():
	conn = pymysql.connect(
		db=DB_NAME,
		user=DB_USERNAME,
		passwd=DB_PASSWORD,
		host=DB_HOST,
		port=DB_PORT,
		charset=DB_CHARSET
	)
	return conn

# -- querys --

def get_user_by_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_user_by_id"], (id,))
	user = cursor.fetchone()
	return user

def get_user_by_email(email):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_user_by_email"], (email,))
	user = cursor.fetchone()
	return user

def get_user_by_username(username):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_user_by_username"], (username,))
	user = cursor.fetchone()
	return user

def create_user(username, password, email):
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["create_user"], (username, password, email))
	conn.commit()

def get_act(page_size):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_act"], (page_size,))
	act = cursor.fetchall()
	return act

def get_act_by_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_act_by_id"], (id,))
	act = cursor.fetchall()
	return act

def get_tema_by_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_tema_by_id"], (id,))
	tema = cursor.fetchall()
	return tema

def get_photo_by_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_photo_by_id"], (id,))
	tema = cursor.fetchall()
	return tema

def get_comuna_by_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_comuna_by_id"], (id,))
	comuna = cursor.fetchall()
	return comuna

def get_region_id_by_comuna(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_region_id_by_comuna"], (id,))
	region = cursor.fetchall()
	return region

def get_region_by_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_region_by_id"], (id,))
	region = cursor.fetchall()
	return region

def get_contacto_by_act_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_contacto_by_act_id"], (id,))
	contacto = cursor.fetchall()
	return contacto

def get_detalle_contacto_by_act_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_detalle_contacto_by_act_id"], (id,))
	contacto = cursor.fetchall()
	return contacto

def create_confession(conf_text, conf_img, user_id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["create_confession"], (conf_text, conf_img, user_id))
	conn.commit()

def get_temas():
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_temas"])
	temas = cursor.fetchall()
	return temas

def get_comentarios_by_id(id):
	session = SessionLocal()
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_comentarios_by_id"], (id))
	comentarios = cursor.fetchall()
	return comentarios

# -- db-related functions --
#Nuevo
def register_act(region, comuna, sector, name, email, celular, contact_detalle, inicio, fin, descripcion, tema):
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["register_act"], (comuna, sector, name, email, celular, inicio, fin, descripcion))
	cursor.execute(QUERY_DICT["get_id_by_every"], (comuna, sector, name, email, celular, inicio, fin, descripcion))
	id = cursor.fetchone()
	id = id[0]
	print(f'ESTE ES EL MALDITO ID: {id}')
	cursor.execute(QUERY_DICT["register_tema"], (tema.lower(), "Algo", id))
	for contact in contact_detalle:
		if contact == '1':
			cursor.execute(QUERY_DICT["register_contact"], ('whatsapp', contact_detalle[contact], id))
		if contact == '2':
			cursor.execute(QUERY_DICT["register_contact"], ('telegram', contact_detalle[contact], id))
		if contact == '3':
			cursor.execute(QUERY_DICT["register_contact"], ('X', contact_detalle[contact], id))
		if contact == '4':
			cursor.execute(QUERY_DICT["register_contact"], ('instagram', contact_detalle[contact], id))
		if contact == '5':
			cursor.execute(QUERY_DICT["register_contact"], ('tiktok', contact_detalle[contact], id))
		if contact == '6':
			cursor.execute(QUERY_DICT["register_contact"], ('otra', contact_detalle[contact], id))
	conn.commit()

def register_comentario(name, comentario, fecha, id):
	san_name = clean(name)
	san_com = clean(comentario)
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["register_comentario"], (name, comentario, fecha, id))
	conn.commit()
	return {
		"name": san_name,
		"time_text": fecha,
		"text": san_com,
	}



#-----------------------------------------------------------------------------------------------------------
def register_user(username, password, email):
	# 1. check the email is not in use
	_email_user = get_user_by_email(email)
	if _email_user is not None:
		return False, "El correo ya esta en uso."
	# 2. check the username is not in use
	_username_user = get_user_by_username(username)
	if _username_user is not None:
		return False, "El nombre de usuario esta en uso."
	# 3. create user
	create_user(username, password, email)
	return True, None

def login_user(username, password):
	a_user = get_user_by_username(username)
	if a_user is None:
		return False, "Usuario o contraseña incorrectos."

	a_user_passwd = a_user[3]
	if a_user_passwd != password:
		return False, "Usuario o contraseña incorrectos."
	return True, None

