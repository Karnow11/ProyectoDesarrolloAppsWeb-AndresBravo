import pymysql
import json

DB_NAME = "tarea2"
DB_USERNAME = "cc5002" #cc5002
DB_PASSWORD = "programacionweb" #programacionweb
DB_HOST = "localhost"
DB_PORT = 3306
DB_CHARSET = "utf8"

with open('database/querys.json', 'r') as querys:
	QUERY_DICT = json.load(querys)

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
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_user_by_id"], (id,))
	user = cursor.fetchone()
	return user

def get_user_by_email(email):
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_user_by_email"], (email,))
	user = cursor.fetchone()
	return user

def get_user_by_username(username):
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
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_act"], (page_size,))
	act = cursor.fetchall()
	return act

def get_tema_by_id(id):
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["get_tema_by_id"], (id,))
	tema = cursor.fetchall()
	return tema

def create_confession(conf_text, conf_img, user_id):
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["create_confession"], (conf_text, conf_img, user_id))
	conn.commit()
	

# -- db-related functions --
#Nuevo
def register_act(region, comuna, sector, name, email, celular, contact_detalle, 
                                          inicio, fin, descripcion, tema):
	conn = get_conn()
	cursor = conn.cursor()
	cursor.execute(QUERY_DICT["register_act"], (name, email, celular, inicio, fin, descripcion, region, comuna, sector))
	cursor.execute(QUERY_DICT["register_tema"], (id, tema))
	for contact in contact_detalle[0]:
		if contact == '1':
			cursor.execute(QUERY_DICT["register_contact"], (id, 'Whatsapp', contact_detalle[contact]))
		if contact == '2':
			cursor.execute(QUERY_DICT["register_contact"], (id, 'Telegram', contact_detalle[contact]))
		if contact == '3':
			cursor.execute(QUERY_DICT["register_contact"], (id, 'X', contact_detalle[contact]))
		if contact == '4':
			cursor.execute(QUERY_DICT["register_contact"], (id, 'Instagram', contact_detalle[contact]))
		if contact == '5':
			cursor.execute(QUERY_DICT["register_contact"], (id, 'Tiktok', contact_detalle[contact]))
		if contact == '6':
			cursor.execute(QUERY_DICT["register_contact"], (id, 'Otro', contact_detalle[contact]))
	conn.commit()

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

